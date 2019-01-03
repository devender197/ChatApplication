package com.example.a3logics.chatapplication.chatApplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a3logics.chatapplication.Adapter.ChatAdapter;
import com.example.a3logics.chatapplication.Adapter.UserListAdapter;
import com.example.a3logics.chatapplication.AudioRecordingButton.RecordingButton;
import com.example.a3logics.chatapplication.AudioRecordingButton.ResultResponse;
import com.example.a3logics.chatapplication.BaseActivity;
import com.example.a3logics.chatapplication.Common.PreferenceManager;
import com.example.a3logics.chatapplication.Common.Utils;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.ApiConstant;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat.FirebaseChat;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.chat.FirebaseChatInterface;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.login.SignIn;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.Messages;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.storage.UploadTask;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ChatActivity extends BaseActivity implements
        FirebaseChatInterface, View.OnClickListener, View.OnTouchListener, TextWatcher, AdapterView.OnItemClickListener {

    private EditText mETxtMessage;
    private ImageView btnPredefinedMessages;
    private ImageView mAttachment;
    private ImageView mWallpaperBackground;
    private ImageView sendButton;
    private RecordingButton mRecordingButton;
    private LinearLayout lnrAttachment;
    private ListView lvPredefinedMessages;
    private TextView mTypingIndicator;
    private RecyclerView mRecyclerViewChat;
    private LinearLayout ll_mssges;
    private Context mContext;
    private String mFileUri;
    private Uri mFileURI;
    private File cameraFile;
    private String TAG = ChatActivity.class.getSimpleName();
    private FirebaseChat firebaseChat;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICKFILE_REQUEST_CODE = 3;
    static final int FILE_SELECT_CODE = 3;
    private final int PICK_IMAGE = 2;
    private ChatAdapter messagesAdapter;
   // private ChatAdapter mChatAdapter;
    final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 0;
    private String receiverUserName ;
    private String senderUserName;
    private String roomId;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Bundle bundle = getIntent().getExtras();
        mContext = this;
        if(bundle != null){
            String str1 = bundle.getString(UserListAdapter.USERNAME);
            String str2 = bundle.getString(UserListAdapter.ROOM_ID);
            String user = PreferenceManager.getStringFromSharedPreferences(SignIn.USERNAME, mContext);
            senderUserName = user.substring(0, user.indexOf("@"));
            if(str2 != null) {
                receiverUserName = str1;
                roomId = str2;
            }else{
                receiverUserName = str1;
                roomId = senderUserName + "_" + receiverUserName;
            }
        }

        bindViews();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void bindViews() {
        mRecyclerViewChat = findViewById(R.id.recycler_view_chat);
        mETxtMessage = findViewById(R.id.edit_text_message);
        mAttachment = findViewById(R.id.attachment);
        mWallpaperBackground = findViewById(R.id.wallpaper_backgroud);
        mTypingIndicator = findViewById(R.id.typingIndicator);
        lvPredefinedMessages = findViewById(R.id.lvPredefinedMessages);
        btnPredefinedMessages = findViewById(R.id.btnPredefinedMessages);
        ll_mssges = findViewById(R.id.ll_mssges);
        mRecordingButton = findViewById(R.id.btnAudioMessage);
        sendButton = findViewById(R.id.btnSendMessage);
        lnrAttachment = findViewById(R.id.layout_attachment);
        firebaseChat = new FirebaseChat(this);
        firebaseChat.setReceiver(receiverUserName);
        firebaseChat.setSender(senderUserName);
        firebaseChat.setRoomID(roomId);
        messagesAdapter = new ChatAdapter(new ArrayList<Messages>(),this,mRecyclerViewChat);
        mRecyclerViewChat.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewChat.setAdapter(messagesAdapter);
        mRecordingButton.setClickResultListener(new ResultResponse() {
            @Override
            public void responseResult(String fileName) {
                //mAudioFile = fileName;
                firebaseChat.uploadAudioFile(fileName);
            }
        });

        sendButton.setOnClickListener(this);
        btnPredefinedMessages.setOnClickListener(this);
        lvPredefinedMessages.setOnItemClickListener(this);
        lvPredefinedMessages.setOnTouchListener(this);
        mETxtMessage.addTextChangedListener(this);
        mAttachment.setOnClickListener(this);
        firebaseChat.getFirebaseMessage("ChatBox/",roomId);
    }

    public void updateMessageFirebase(String childValue,String messageKey,String childKey){
        System.out.println("childValue: "+childValue +" "+"childKey: "+childKey+"messageKey: "+messageKey);
        String firebaseConnectionPath = "ChatBox/"+roomId+"/"+messageKey;
        if(messageKey!=null) firebaseChat.updateMessageWithKeyFirebase(firebaseConnectionPath,childValue,childKey);
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        int audioPermissionIndex = Arrays.asList(permissions).indexOf(Manifest.permission.RECORD_AUDIO);
        if (audioPermissionIndex > -1) {
            if (grantResults[audioPermissionIndex] == PackageManager.PERMISSION_GRANTED) {
                mRecordingButton.performClick();
            } else {
                boolean showRationale = shouldShowRequestPermissionRationale(Manifest.permission.RECORD_AUDIO);
                if (!showRationale) {
                    Utils.showCustomAlertDialog(this, getString(R.string.app_name), getString(R.string.permission_audio_must_include_text), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                } else {
                    Utils.showCustomAlertDialog(this, getString(R.string.app_name), getString(R.string.permission_audio_disallow));
                }
            }


        }


    }

    @Override
    public void activityActionOnFirebaseChildAdded(DataSnapshot dataSnapshot, String s) {
        if(null!=dataSnapshot) {
            HashMap mapMessage = (HashMap) dataSnapshot.getValue();
            String messageKey = dataSnapshot.getKey();
            Messages newMessage = new Messages();
            String imageUrl = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA) : null);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                newMessage.setImageUrlA((String) mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA));
            }

            String msgText = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_TEXT) : null);
            if (null != msgText && !msgText.isEmpty()) {
                assert mapMessage != null;
                newMessage.setText((String) mapMessage.get(ApiConstant.FIREBASE_TEXT));
            }
            if (null != mapMessage.get(ApiConstant.FIREBASE_SYSTEM_TEXT)) {
                newMessage.setIsSystemText((Boolean) mapMessage.get(ApiConstant.FIREBASE_SYSTEM_TEXT));
            }
            newMessage.setUserRole((String) mapMessage.get(ApiConstant.FIREBASE_USER_ROLE));
            newMessage.setUid((String) mapMessage.get(ApiConstant.FIREBASE_UID_NAME));
            newMessage.setCreatedDate((String) mapMessage.get(ApiConstant.FIREBASE_CREATED_DATE));
            newMessage.setTimeStamp(Long.parseLong(mapMessage.get(ApiConstant.FIREBASE_TIMESTAMP).toString()));
            if(mapMessage.get(ApiConstant.FIREBASE_SENDOR_ID) != null){
                newMessage.setSenderId(mapMessage.get(ApiConstant.FIREBASE_SENDOR_ID).toString());
            }
            newMessage.setSenderName((String) mapMessage.get(ApiConstant.FIREBASE_SENDOR_NAME));
            newMessage.setImageUrlA((String) mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA));
            newMessage.setAudioURL_a((String) mapMessage.get(ApiConstant.FIREBASE_AUDIO_URLA));
            newMessage.setPdfUrlA((String) mapMessage.get(ApiConstant.FIREBASE_PDF_URLA));
            newMessage.setReceiverName((String) mapMessage.get(ApiConstant.FIREBASE_RECEIVER_NAME));
            newMessage.setReadStatus((String) mapMessage.get(ApiConstant.FIREBASE_READ_STATUS));
            System.out.println("Message:"+newMessage.getText());
            messagesAdapter.add(newMessage, messageKey);
        }else{
            //mMessageEdt.setEnabled(true);
        }
    }

    @Override
    public void activityActionOnFirebaseChildChanged(DataSnapshot dataSnapshot, String s) {
        if(null!=dataSnapshot) {
            HashMap mapMessage = (HashMap) dataSnapshot.getValue();
            String messageKey = dataSnapshot.getKey();
            Messages newMessage = new Messages();
            String imageUrl = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA) : null);
            if (imageUrl != null && !imageUrl.isEmpty()) {
                newMessage.setImageUrlA((String) mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA));
            }

            String msgText = (String) (mapMessage != null ? mapMessage.get(ApiConstant.FIREBASE_TEXT) : null);
            if (null != msgText && !msgText.isEmpty()) {
                assert mapMessage != null;
                newMessage.setText((String) mapMessage.get(ApiConstant.FIREBASE_TEXT));
            }
            if (null != mapMessage.get(ApiConstant.FIREBASE_SYSTEM_TEXT)) {
                newMessage.setIsSystemText((Boolean) mapMessage.get(ApiConstant.FIREBASE_SYSTEM_TEXT));
            }
            newMessage.setUserRole((String) mapMessage.get(ApiConstant.FIREBASE_USER_ROLE));
            newMessage.setUid((String) mapMessage.get(ApiConstant.FIREBASE_UID_NAME));
            newMessage.setCreatedDate((String) mapMessage.get(ApiConstant.FIREBASE_CREATED_DATE));
            newMessage.setTimeStamp(Long.parseLong(mapMessage.get(ApiConstant.FIREBASE_TIMESTAMP).toString()));
            if(mapMessage.get(ApiConstant.FIREBASE_SENDOR_ID) !=  null){
                newMessage.setSenderId(mapMessage.get(ApiConstant.FIREBASE_SENDOR_ID).toString());
            }
            newMessage.setSenderName((String) mapMessage.get(ApiConstant.FIREBASE_SENDOR_NAME));
            newMessage.setImageUrlA((String) mapMessage.get(ApiConstant.FIREBASE_IMAGE_URLA));
            newMessage.setAudioURL_a((String) mapMessage.get(ApiConstant.FIREBASE_AUDIO_URLA));
            newMessage.setPdfUrlA((String) mapMessage.get(ApiConstant.FIREBASE_PDF_URLA));
            newMessage.setReceiverName((String) mapMessage.get(ApiConstant.FIREBASE_RECEIVER_NAME));
            newMessage.setReadStatus((String) mapMessage.get(ApiConstant.FIREBASE_READ_STATUS));
            System.out.println("Message:"+newMessage.getText());
            messagesAdapter.update(newMessage);
        }else{
            //mMessageEdt.setEnabled(true);
        }
    }

    @Override
    public void activityActionOnFirebaseChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void activityActionOnFirebaseChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void activityActionOnFirebaseCancelled(DatabaseError databaseError) {

    }

    @Override
    public void activityActionOnFirebaseFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {
        String imageUrl = taskSnapshot.getDownloadUrl()!=null?taskSnapshot.getDownloadUrl().toString():null;
        String storageUrl = taskSnapshot.getStorage().toString()!=null?taskSnapshot.getStorage().toString():null;
        updateFirebaseMessage(prepareMessage(imageUrl,storageUrl,null,false),messageKey);
    }

    @Override
    public void activityActionOnFirebaseFileUploadNew(UploadTask.TaskSnapshot taskSnapshot, String localUrl) {
        sendFirebaseMessage(prepareMessage(ApiConstant.FIREBASE_NOT_SET, ApiConstant.FIREBASE_NOT_SET, null,false));
    }

    @Override
    public void activityActionOnFirebaseMemberChangeListener(String count, String ticketId, String UID) {

    }

    @Override
    public void activityActionOnFirebaseAudioFileUpload() {
        sendFirebaseMessage(prepareMessageAudio(ApiConstant.FIREBASE_NOT_SET, ApiConstant.FIREBASE_NOT_SET, null,false));
    }

    @Override
    public void activityActionOnFirebaseAudioFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {
        String imageUrl = taskSnapshot.getDownloadUrl()!=null?taskSnapshot.getDownloadUrl().toString():null;
        String storageUrl = taskSnapshot.getStorage().toString()!=null?taskSnapshot.getStorage().toString():null;
        updateFirebaseMessage(prepareMessageAudio(imageUrl, storageUrl, null,false),messageKey);
    }

    @Override
    public void activityActionOnFirebasePDFFileUpload() {
        sendFirebaseMessage(prepareMessagePDF(ApiConstant.FIREBASE_NOT_SET, ApiConstant.FIREBASE_NOT_SET, null,false));
    }

    @Override
    public void activityActionOnFirebasePDFFileUpload(UploadTask.TaskSnapshot taskSnapshot, String messageKey) {
        String imageUrl = taskSnapshot.getDownloadUrl()!=null?taskSnapshot.getDownloadUrl().toString():null;
        String storageUrl = taskSnapshot.getStorage().toString()!=null?taskSnapshot.getStorage().toString():null;
        updateFirebaseMessage(prepareMessagePDF(imageUrl, storageUrl, null,false),messageKey);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attachment:
                initiatePopupWindow();
                //showFileChooser();
                break;
            case R.id.btnPredefinedMessages:
                //browseDocuments();
                Intent intentPDF = new Intent(Intent.ACTION_GET_CONTENT);
                intentPDF.setType("application/pdf");
                intentPDF.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intentPDF,FILE_SELECT_CODE);
                break;
            case R.id.btnSendMessage:
                String timeStamp = System.currentTimeMillis()+"";
                Messages messages = new Messages();
                messages.setTimeStamp(System.currentTimeMillis());
                messages.setCreatedDate(System.currentTimeMillis()+"");
                messages.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
                messages.setMessageType("Text");
                messages.setSenderName("Devender198");
                messages.setReceiverName("Devender197");
                messages.setText(mETxtMessage.getText().toString());
                firebaseChat.writeMessageFirebase("chat_box",messages);
                mETxtMessage.setText("");
                 break;
        }
    }

    private Messages prepareMessage(String imageURL, String storageURL, String text,boolean isSystemMsg) {
        Messages message = new Messages();
        //message.setCreatedDate(new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        long timestampValue=System.currentTimeMillis();
        message.setCreatedDate(timestampValue+"");
        message.setTimeStamp(timestampValue);
        message.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        message.setSenderName("");
        if(imageURL!=null && !imageURL.isEmpty()) {
            message.setImageUrlA(imageURL);
        }else {
            message.setImageUrlA("");
        }

        if(storageURL!=null && !storageURL.isEmpty()) {
            message.setImageUrlI(storageURL);
        }else {
            message.setImageUrlI("");

        }
        message.setText(text);

        return message;
    }

    private Messages prepareMessageAudio(String audioURL, String audiostorageURL, String text,boolean isSystemMsg) {
        Messages message = new Messages();
        //message.setCreatedDate(new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        long timestampValue=System.currentTimeMillis();
        message.setCreatedDate(timestampValue+"");
        message.setTimeStamp(timestampValue);
        message.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        message.setSenderName("");
        if(audioURL!=null && !audioURL.isEmpty()) {
            message.setAudioURL_a(audioURL);
        }else {
            message.setAudioURL_a("");
        }

        if(audiostorageURL!=null && !audiostorageURL.isEmpty()) {
            message.setAudioURL_i(audiostorageURL);
        }else {
            message.setImageUrlI("");

        }
        message.setText(text);

        return message;
    }

    private Messages prepareMessagePDF(String audioURL, String audiostorageURL, String text,boolean isSystemMsg) {
        Messages message = new Messages();
        //message.setCreatedDate(new SimpleDateFormat("MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        long timestampValue=System.currentTimeMillis();
        message.setCreatedDate(timestampValue+"");
        message.setTimeStamp(timestampValue);
        message.setSenderId(FirebaseAuth.getInstance().getCurrentUser().getUid());
        message.setSenderName("");
        if(audioURL!=null && !audioURL.isEmpty()) {
            message.setPdfUrlA(audioURL);
        }else {
            message.setPdfUrlA("");
        }

        if(audiostorageURL!=null && !audiostorageURL.isEmpty()) {
            message.setPdfUrlI(audiostorageURL);
        }else {
            message.setPdfUrlI("");

        }
        message.setText(text);

        return message;
    }

    private void sendFirebaseMessage(Messages messages){
        firebaseChat.writeMessageFirebase("", messages);
    }

    private void updateFirebaseMessage(Messages messages,String messageKey){
        firebaseChat.updateMessageFirebase("", messages,messageKey);
    }

    public void initiatePopupWindow() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(mContext);
        LayoutInflater inflater = ((ChatActivity) mContext).getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.popup, null);

        final android.app.AlertDialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        dialog.setView(dialogLayout, 0, 0, 0, 0);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        WindowManager.LayoutParams wlmp = dialog.getWindow().getAttributes();
        wlmp.gravity = Gravity.BOTTOM;

        ImageView mGalleryOption = dialogLayout.findViewById(R.id.gallery_img);
        ImageView mCameraOption = dialogLayout.findViewById(R.id.camera_img);

        mGalleryOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dispatchGalleryPictureIntent();
            }
        });
        mCameraOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                dispatchTakePictureIntent();
            }
        });
        builder.setView(dialogLayout);
        dialog.show();
    }

    private void dispatchGalleryPictureIntent() {

        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        ((ChatActivity) mContext).startActivityForResult(intent, PICK_IMAGE);
    }

    /**
     * After android 8 oreo image process for getting image from camera has been changed.
     * follow the below code {@Link:https://developer.android.com/training/camera/photobasics.html}
     */
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = Utils.getOutputImageUri(mContext);
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mContext, "com.example.a3logics.chatapplication.fileprovider", photoFile);
                mFileUri = photoFile.getAbsolutePath();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       mFileURI = null;
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == ((ChatActivity) mContext).RESULT_OK) {
            File photoFile = null;
            try {
                cameraFile = Utils.getOutputImageUri(mContext);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mFileURI = Uri.fromFile(cameraFile);
            if(mFileURI != null) {
                UCrop.of(Uri.fromFile(new File(mFileUri)), mFileURI).withAspectRatio(16, 9).start(ChatActivity.this);
            }

        } else if (requestCode == PICK_IMAGE && resultCode == ((ChatActivity) mContext).RESULT_OK) {
            try {
                File photoFile = Utils.getOutputImageUri(mContext);
                mFileURI = Uri.fromFile(photoFile);
                Uri sourceUri = data.getData();
                if (sourceUri != null) {
                    UCrop.of(sourceUri, mFileURI)
                            .withAspectRatio(16, 9)
                            .start(ChatActivity.this);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            firebaseChat.uploadImageFileFirebase(imageBitmap, "JPEG", mFileUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        } else if (requestCode == 69 && resultCode == 0) {
            File file = new File(mFileURI.getPath());
            file.delete();
        }else if(requestCode == FILE_SELECT_CODE && resultCode == ((ChatActivity) mContext).RESULT_OK){
            final Uri resultUri = data.getData();
            if(resultUri != null) {
                File file = new File(resultUri.getPath());
                firebaseChat.uploadFile(resultUri);
            }

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = mContext.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap modifyOrientation(Bitmap bitmap, String image_absolute_path) throws IOException {
        ExifInterface ei = new ExifInterface(image_absolute_path);
        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotate(bitmap, 90);

            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotate(bitmap, 180);

            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotate(bitmap, 270);

            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                return flip(bitmap, true, false);

            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                return flip(bitmap, false, true);

            default:
                return bitmap;
        }
    }

    public static Bitmap rotate(Bitmap bitmap, float degrees) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degrees);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static Bitmap flip(Bitmap bitmap, boolean horizontal, boolean vertical) {
        Matrix matrix = new Matrix();
        matrix.preScale(horizontal ? -1 : 1, vertical ? -1 : 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            sendButton.setVisibility(View.VISIBLE);
            mRecordingButton.setVisibility(View.GONE);
            lnrAttachment.setVisibility(View.GONE);
        } else {
            sendButton.setVisibility(View.GONE);
            mRecordingButton.setVisibility(View.VISIBLE);
            lnrAttachment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (ll_mssges.getVisibility() == View.VISIBLE) {
            ll_mssges.setVisibility(View.GONE);
        }
        if (!android.text.TextUtils.isEmpty(s.toString()) && s.toString().trim().length() == 1) {

            setTypingIndicator(true);

        } else if (s.toString().trim().length() == 0) {

            setTypingIndicator(false);

        }
    }

    void setTypingIndicator(boolean isTyping) {
        /*
        Chat chat = new Chat();
        chat.setSenderId(firebaseUid);
        chat.setReceiverUid(receiverUid);
        String roomName= getRoomNameInFragment(firebaseUid,receiverUid);
        */
       /* String receiverUid = getArguments().getString(Constants.ARG_RECEIVER_UID);
        String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String receiverNumber =getArguments().getString(Constants.ARG_RECEIVER_PHONE_NUMBER);
        String senderNumber =mSenderNumber ;
        Chat chat = new Chat();
        chat.setSenderNumber(senderNumber);
        chat.setReceiverNumber(receiverNumber);
        chat.setSenderId(firebaseUid);
        chat.setReceiverUid(receiverUid);
        String roomName= getRoomNameByNumberInFragment(senderNumber,receiverNumber);
        mChatPresenter.setTypingIndicator(getActivity(), chat, isTyping,roomName);*/

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        Utils.hideKeyBoard(mContext, mETxtMessage);
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void browseDocuments(){

        String[] mimeTypes =
                {"application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                        "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                        "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                        "text/plain",
                        "application/pdf",
                        "application/zip"};

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            intent.setType(mimeTypes.length == 1 ? mimeTypes[0] : "*/*");
            if (mimeTypes.length > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
            }
        } else {
            String mimeTypesStr = "";
            for (String mimeType : mimeTypes) {
                mimeTypesStr += mimeType + "|";
            }
            intent.setType(mimeTypesStr.substring(0,mimeTypesStr.length() - 1));
        }
        startActivityForResult(Intent.createChooser(intent,"ChooseFile"), FILE_SELECT_CODE);

    }

    public void setPDFIntent(String url, int position){
        ChatActivity.AsyncTaskRunner asyncTaskRunner
                = new ChatActivity.AsyncTaskRunner(url,position);
        asyncTaskRunner.execute();
    }

    public class AsyncTaskRunner extends AsyncTask<String, String, String> {
        String mFileName;
        boolean isSuccess;
        String downloadUrl;
        int mPosition;
        public AsyncTaskRunner(String url,int position){
            downloadUrl = url;
            mPosition = position;
        }

        @Override
        protected String doInBackground(String... params) {
            InputStream input = null;
            OutputStream output = null;
            HttpURLConnection connection = null;
            try {
                URL url = new URL(downloadUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                // expect HTTP 200 OK, so we don't mistakenly save error report
                // instead of the file
                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return "Server returned HTTP " + connection.getResponseCode()
                            + " " + connection.getResponseMessage();
                }

                // this will be useful to display download percentage
                // might be -1: server did not report the length
                int fileLength = connection.getContentLength();

                // download the file
                input = connection.getInputStream();
                mFileName = Utils.getOutputPdfUri(mContext,"dev.pdf").getAbsolutePath();
                output = new FileOutputStream(mFileName);

                byte data[] = new byte[4096];
                int count;
                while ((count = input.read(data)) != -1) {
                    // allow canceling with back button
                    if (isCancelled()) {
                        input.close();
                        return null;
                    }
                    output.write(data, 0, count);
                    isSuccess = true;
                }
            } catch (Exception e) {
                isSuccess = false;
                e.printStackTrace();
                return e.toString();

            } finally {
                try {
                    if (output != null)
                        output.close();
                    if (input != null)
                        input.close();
                } catch (IOException ignored) {
                }

                if (connection != null)
                    connection.disconnect();
            }
            return null;

        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            if (isSuccess) {
//                setMusicFile(mFileName, mPosition, mFragmentPlayerInterface);
                File file = new File(mFileName);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(FileProvider.getUriForFile(mContext,"com.example.a3logics.chatapplication.fileprovider", file ), "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(intent);
            }
        }

        @Override
        protected void onPreExecute() {

        }

    }
}
