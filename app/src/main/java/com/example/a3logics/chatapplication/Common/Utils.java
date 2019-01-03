package com.example.a3logics.chatapplication.Common;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Surface;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.a3logics.chatapplication.R;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.ApiConstant;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Created by Rajeev on 4/12/17.
 * Utility class for VendorCrush application
 */

public class Utils {

    static android.app.AlertDialog mAlertDialog;
    private static final String TAG = Utils.class.getSimpleName();
    private static MaterialDialog mProgressDialog;
    private static String checked="0";

    /** -- Show Progress Dialog for webservice request calling
     *
     * @param context       context object of activity
     * @param content       content to show on dialog
     * @return MaterialDialog object
     */
    public static MaterialDialog showProgressDialog(Context context, String content) {
        return new MaterialDialog.Builder(new ContextThemeWrapper(context,R.style.DialogTheme))
                .content(content)
                .progress(true, 0)
                .canceledOnTouchOutside(false)
                .cancelable(false)
                .show();
    }

    public static void showCustomAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton(android.R.string.ok, null);
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>", Html.FROM_HTML_MODE_LEGACY));
        }  else {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
        }
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void hideKeyBoard(Context mContext, EditText etTextBox) {
        InputMethodManager inputManager = (InputMethodManager) mContext
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager.isActive())
            inputManager.hideSoftInputFromWindow(etTextBox.getWindowToken(), 0);
    }

    public static void showCustomAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton(android.R.string.ok, onClickListener);
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
        }
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void showCustomAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener onPostiiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton(android.R.string.ok, onPostiiveClickListener);
        builder.setNegativeButton(android.R.string.cancel,onNegativeClickListener);
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
        }
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static void showCustomAlertDialog(Context context, String title, String message,
                String positiveBtnText , DialogInterface.OnClickListener onPostiiveClickListener,
                String negativeBtnText , DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AppCompatAlertDialogStyle);
        builder.setPositiveButton(positiveBtnText, onPostiiveClickListener);
        builder.setNegativeButton(negativeBtnText, onNegativeClickListener);
        builder.setCancelable(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>", Html.FROM_HTML_MODE_LEGACY));
        } else {
            builder.setTitle(Html.fromHtml("<b>" + title + "</b>"));
        }
        builder.setMessage(message);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public static void displayProgressDialog(Context context, String title){
        if(mProgressDialog==null || !mProgressDialog.isShowing())
            mProgressDialog = showProgressDialog(context, title);
    }

    public static void dismissProgressDialog(Context context){
        if(!((Activity)context).isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }




   

    
    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static boolean isNetworkAvailable(Context context) {
        if (null == context)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d(TAG, "***Available***");
            return true;
        }
        Log.d(TAG, "***Not Available***");
        return false;
    }

    public static boolean isCheckNetworkAvailable(Context context, View view) {
        if (null == context)
            return false;
        if (isNetworkAvailable(context)) {
            return true;
        } else {
            Snackbar mSnackBar = Snackbar.make(view, context.getString(R.string.network_error_message), Snackbar.LENGTH_SHORT);
            View mView = mSnackBar.getView();
            TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setTextColor(ContextCompat.getColor(context, R.color.alert_dialog_text_color));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackBar.show();
            return false;
        }
    }

    public static void showInternalServerError(int errorCode, View view, Context context) {

        String message;

        switch (errorCode) {
            case 400:
                message = context.getString(R.string.err_msg_400);
                break;
            case 401:
                message = context.getString(R.string.err_msg_401);
                break;
            case 404:
                message = context.getString(R.string.err_msg_404);
                break;
            case 405:
                message = context.getString(R.string.err_msg_405);
                break;
            case 415:
                message = context.getString(R.string.err_msg_415);
                break;
            case 500:
                message = context.getString(R.string.err_msg_500);
                break;
            case -1:
                message = context.getString(R.string.host_not_found);
                break;
            default:
                message = context.getString(R.string.err_msg_default);
                break;
        }
        if (view != null) {
            Snackbar mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
            View mView = mSnackBar.getView();
            TextView mTextView = (TextView) mView.findViewById(android.support.design.R.id.snackbar_text);
            mTextView.setTextColor(ContextCompat.getColor(context, R.color.color_white));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
                mTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            else
                mTextView.setGravity(Gravity.CENTER_HORIZONTAL);
            mSnackBar.show();
        }
    }

    


    /**
     * Following function is used to show error message on snack bar
     * @param view
     * @param errorText text to be displayed on the snack bar on error
     */
    public static void requestFocus(View view, String errorText) {
        Snackbar snackbar = Snackbar.make(view, errorText, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static File getOutputImageUri(Context context) throws IOException {
        String mCurrentPhotoPath;

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir =  context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */".jpg",         /* suffix */
                storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static File getOutputImageUri(Context context,String downloadUrl) throws IOException {

        String imageFileName =  downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.indexOf("?"));
        File storageDir =  context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        String ext = imageFileName.substring(imageFileName.indexOf("."));
        String fileName = imageFileName.substring(0,imageFileName.indexOf("."));
        File image = new File(storageDir+"/"+imageFileName);
        return image;
    }

    public static File getOutputPdfUri(Context context,String fileName) throws IOException {
        File storageDir =  context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File image = new File(storageDir+"/"+fileName);
        return image;
    }

    public static File getOutputFileUri(Context context,String downloadUrl) throws IOException {

        String imageFileName =  downloadUrl.substring(downloadUrl.lastIndexOf("/") + 1, downloadUrl.indexOf("?"));
        File storageDir =  context.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        String ext = imageFileName.substring(imageFileName.indexOf("."));
        String fileName = imageFileName.substring(0,imageFileName.indexOf("."));
        Long tsLong = System.currentTimeMillis()/1000;
        String newFileName = fileName+ ""+ tsLong;
        File image = new File(storageDir+"/"+newFileName+"."+ext
        );
        return image;
    }

    /**
     * This function is used to perform media scan. so that i will available in gallery
     */
    static private void galleryAddPic(Context context,String mCurrentPhotoPath) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }


    public static void datePickerDialog(Context context,DatePickerDialog.OnDateSetListener myDateListener,long milisec){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, myDateListener,mYear,mMonth,mDay);
        if(milisec!=0) {
            datePickerDialog.getDatePicker().setMinDate(milisec);
        }
        datePickerDialog.show();
    }


    public static void datePickerDialogSetMax(Context context,DatePickerDialog.OnDateSetListener myDateListener,long milisec){
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR)-13;
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH)-1;

        calendar.set(mYear,mMonth,mDay);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, myDateListener,mYear,mMonth,mDay);
        if(milisec!=0) {
            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
        }
        datePickerDialog.show();
    }


    /**
     * following function is used to check the permission at run time for device above android M.
     * @param context
     * @param permissions
     * @return
     */
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * following function is created to display the alert box . if user click on negative button , it will close the
     * application
     *
     * @param context AppCompatActivity Context
     * @param title Title to be displayed on alert box
     * @param message Messages to be displayed on alert box
     * @param postiveClickListener onclickListner on click of positive(ok) button
     */
    public static void showAlertDialogBox(final Context context, String title, String message, DialogInterface.OnClickListener postiveClickListener){
        android.app.AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.app.AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
            mAlertDialog = builder.create();
        } else {
            builder = new android.app.AlertDialog.Builder(context);
        }
        mAlertDialog = builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, postiveClickListener)
                .setNegativeButton("Quit Application", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //following code is used to close the application
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .create();
        mAlertDialog.show();
    }


    /**
     * Date format conversion from yyyy-MM-dd'T'HH:mm:ss.SSSZ to dd/MM/yyyy
     */
    public static String dateFormatConversion(String dateString){

        Date date  = null ;
        String returnDateString = "";
        SimpleDateFormat sourceSimpleDateFormat         = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat destinationSimpleDateFormat    = new SimpleDateFormat("MMM dd, yyyy");
        sourceSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(ApiConstant.SERVER_DATE_TIMEZONE));
        destinationSimpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            date = sourceSimpleDateFormat.parse(dateString);
            returnDateString = destinationSimpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDateString;

    }

    /**
     * Date format conversion from yyyy-MM-dd'T'HH:mm:ss.SSSZ to Jul 25, 2018
     */
    public static String dateFormatConversion(String dateString,boolean bool){

        Date date  = null ;
        String returnDateString = "";
        SimpleDateFormat sourceSimpleDateFormat         = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat destinationSimpleDateFormat    = new SimpleDateFormat("MMM dd, yyyy");
        sourceSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(ApiConstant.SERVER_DATE_TIMEZONE));
        destinationSimpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
             date = sourceSimpleDateFormat.parse(dateString);
            returnDateString = destinationSimpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDateString;
    }


    /**
     * Date format conversion from yyyy-MM-dd'T'HH:mm:ss.SSSZ to Jul 25, 2018 // 7:20 AM/PM, 12 Mar, 2018
     */
    public static String dateFormatConversionLogs(String dateString,boolean bool){

        Date date  = null ;
        String returnDateString = "";
        SimpleDateFormat sourceSimpleDateFormat         = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
        SimpleDateFormat destinationSimpleDateFormat    = new SimpleDateFormat("HH:mm a, MMM dd, yyyy");
        sourceSimpleDateFormat.setTimeZone(TimeZone.getTimeZone(ApiConstant.SERVER_DATE_TIMEZONE));
        destinationSimpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
             date = sourceSimpleDateFormat.parse(dateString);
             returnDateString = destinationSimpleDateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnDateString;
    }

    /**
     * Date format conversion from yyyy-MM-dd'T'HH:mm:ss.SSSZ to Jul 25, 2018 // 7:20 AM/PM, 12 Mar, 2018
     */
    public static String dateToString(Date mDate){

        SimpleDateFormat dateFacDB = new SimpleDateFormat("MM/dd/yyyy");
        String returnDateString=null;
        try {
            returnDateString  =dateFacDB.format(mDate);
            Log.i(returnDateString,TAG);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returnDateString;
    }

    public static String setFirstCharacterCapital(String str) {
        if (null != str && str.length() > 0) {
            String[] strArray = str.trim().split(" ");
            String returnString = "";
            for (String string : strArray) {
                try {
                    if(string.length()>0) {
                        returnString = returnString + Character.toUpperCase(string.charAt(0)) + string.substring(1) + " ";
                    }
                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
            return returnString;
        }else{
            return str;
        }

    }
    private static DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
    private static DateFormat timeFormat = new SimpleDateFormat("K:mma");

    public static String getCurrentTime() {

        Date today = Calendar.getInstance().getTime();
        return timeFormat.format(today);
    }

    public static String getCurrentDate() {

        Date today = Calendar.getInstance().getTime();
        return dateFormat.format(today);

    }

    public static void callActionDial(String phoneNumber,Context context){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phoneNumber));
        context.startActivity(intent);
    }

    public static void callActionMail(String emailAddress,Context context){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailAddress});
        try {
            context.startActivity(Intent.createChooser(intent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            showCustomAlertDialog(context,context.getString(R.string.dilog_tilte_alert),"There is no email client installed");
        }
    }

    //returning value at 3 decimal point
    public static String getFormatedValue(double amount){
        String formatedValue="0.00";
        if(amount>0){
            DecimalFormat formatter = new DecimalFormat(".00");
            formatter.setRoundingMode(RoundingMode.DOWN);
            formatedValue = formatter.format(amount);
        }
        return formatedValue;
    }

    public static double parseDouble(String value){
        double returnValue = 0.0;
        if(value!=null){
            try {
                returnValue = Double.parseDouble(value);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return returnValue;
    }

    public static int getCorrectCameraOrientation(Camera.CameraInfo info, Activity context) {

        int rotation = context.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        System.out.println("rotation-->"+rotation);

        switch(rotation){
            case Surface.ROTATION_0:
                degrees = 0;
                break;

            case Surface.ROTATION_90:
                degrees = 90;
                break;

            case Surface.ROTATION_180:
                degrees = 180;
                break;

            case Surface.ROTATION_270:
                degrees = 270;
                break;

        }

        int result;
        if(info.facing== Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;
        }else{
            result = (info.orientation - degrees + 360) % 360;
        }

        return result;
    }


    public static void hideSoftKeyboard(Activity activity) {
        if (activity != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null && inputManager != null) {
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                inputManager.hideSoftInputFromInputMethod(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }


    public static void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null) {
                inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
   

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.MATCH_PARENT
                        : (int)(targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation()
        {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    v.setVisibility(View.GONE);
                }else{
                    v.getLayoutParams().height = initialHeight - (int)(initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int)(initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }


    /*public static void addFragment(final int containerViewId, final Fragment fragment, final String backStackName, final boolean replace) {
        final FragmentManager fragmentManager = getSupportFragmentManager();

        if (replace) {
            while (fragmentManager.getBackStackEntryCount() > 0) {
                final int last = fragmentManager.getBackStackEntryCount() - 1;
                final FragmentManager.BackStackEntry entry =
                        fragmentManager.getBackStackEntryAt(last);
                if (!TextUtils.equals(entry.getName(), backStackName)) {
                    break;
                }

                fragmentManager.popBackStackImmediate();
            }
        }

        fragmentManager
                .beginTransaction()
                .replace(containerViewId, fragment)
                .addToBackStack(backStackName)
                .commit();
        fragmentManager.executePendingTransactions();
    }*/



    public static byte[] CustomSerializedObjectToByte(Object obj){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        byte[] data = null;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(obj);
            out.flush();
            data = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }


    public static Map<String, String> splitQuery(URL url) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new LinkedHashMap<String, String>();
        String query = url.getQuery();
        String[] pairs = query.split("&");
        for (String pair : pairs) {
            int idx = pair.indexOf("=");
            query_pairs.put(URLDecoder.decode(pair.substring(0, idx), "UTF-8"), URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
        }
        return query_pairs;
    }

    /**
     * calculating  maintenance ticket enddate behalf of selected repeat frequency
     * @param context               Activity context
     * @param myDateListener        datepicker listener object
     * @param milisec               initi
     * @param repeat
     * @return
     */
    public static int setEndDateAsFrequency(Context context,DatePickerDialog.OnDateSetListener myDateListener,long milisec,int repeat){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milisec);
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        switch (repeat){
            case 0:
                mDay = calendar.get(Calendar.DAY_OF_MONTH)+7;
                break;
            case 1:
                mDay = calendar.get(Calendar.DAY_OF_MONTH)+14;
                break;
            case 2:
                mMonth = calendar.get(Calendar.MONTH)+1;
                break;
            /*case 3:
                mMonth = calendar.get(Calendar.MONTH)+3;
                break;*/
            case 3:
                mMonth = calendar.get(Calendar.MONTH)+6;
                break;
            case 4:
                mYear = calendar.get(Calendar.YEAR)+1;
                break;
        }
        calendar.set(mYear,mMonth,mDay);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, myDateListener,mYear,mMonth,mDay);
        if(milisec!=0) {
            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
        }
        datePickerDialog.show();
        return 0;
    }

    
}
