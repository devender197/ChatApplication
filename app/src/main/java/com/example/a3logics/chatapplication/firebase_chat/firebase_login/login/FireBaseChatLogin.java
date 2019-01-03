package com.example.a3logics.chatapplication.firebase_chat.firebase_login.login;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.a3logics.chatapplication.Common.PreferenceManager;
import com.example.a3logics.chatapplication.Common.Utils;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.ApiConstant;
import com.example.a3logics.chatapplication.firebase_chat.firebase_login.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by a3logics on 19/3/18.
 */

public class FireBaseChatLogin {

    private static String TAG = FireBaseChatLogin.class.getSimpleName();
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;
    private boolean firstTimeAccess;
    private FireBaseChatLoginInterFace mFireBaseChatLoginInterFace;
    private static FireBaseChatLogin mFireBaseChatLoginInstance;
    private boolean isNewUser;


    public FirebaseUser getUser() {
        return user;
    }

    public FireBaseChatLogin(FireBaseChatLoginInterFace fireBaseChatLoginInterFace){
        mFireBaseChatLoginInterFace = fireBaseChatLoginInterFace;
        initFirebase();
        firstTimeAccess = true;
    }


    /**
     * Firebase initialization
     */
    private void initFirebase() {
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    if (firstTimeAccess) {
                       // TO - DO for user first time.....................
                    }
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                firstTimeAccess = false;
            }
        };
    }


    /**
     * Action on Add Authorization
     */
    public void addAuthStateListener(){
        mAuth.addAuthStateListener(mAuthListener);
    }


    /**
     *Action on remove Authorization
     */
    public void removeAuthStateListener(){
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void isUserExist(final String email, final String password){
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("users").child(email.substring(0,email.indexOf("@"))).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    signIn(email, password);
                    isNewUser = false;
                } else {
                    // User does not exist. NOW call createUserWithEmailAndPassword
                    createUser(email,password);
                    isNewUser = true;
                    // Your previous code here.

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Action register
     * @param email
     * @param password
     */
   public void createUser(final String email, final String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            mFireBaseChatLoginInterFace.onUnSuccessFulCreateUser(new UnSuccessfulException());
                        } else {
                            signIn(email, password);
                            initNewUserInfo(task.getResult().getUser());
                            mFireBaseChatLoginInterFace.onSuccessFulCreateUser();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        mFireBaseChatLoginInterFace.onUnSuccessFulCreateUser(e);
                    }
                });
    }




    public void signIn(String email, String password){
        initFirebase();
       if(null!=mAuth) {
           mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                   try {
                       FirebaseUser firebaseUser = task.getResult().getUser();
                       user = firebaseUser;
                       String email = firebaseUser.getEmail();
                       //initNewUserInfo(task.getResult().getUser());
                       if(user.isEmailVerified()) {
                           mFireBaseChatLoginInterFace.onSuccessFulLogin();
                       }else{
                            mFireBaseChatLoginInterFace.sendEmailVerification();
                            mFireBaseChatLoginInterFace.onUnAuthorizedAccess();
                       }
                       /*if(!isNewUser) {
                           FirebaseDatabase.getInstance().getReference().child("user/" + email.substring(0, email.indexOf("@")) + "/status/isOnline").setValue(true);
                       }else{
                           initNewUserInfo(task.getResult().getUser());
                       }*/
                   }catch (Exception e){
                       //mFireBaseChatLoginInterFace.onUnSuccessFulLogin(e);
                   }

               }
           }).addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   mFireBaseChatLoginInterFace.onUnSuccessFulLogin(e);
               }
           });
       }else{
           initFirebase();
           if(null!=mAuth) {
               mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                       mFireBaseChatLoginInterFace.onSuccessFulLogin();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       mFireBaseChatLoginInterFace.onSuccessFulLogin();
                   }
               });
           }
       }
    }

    /**
     * Action reset password
     * @param email
     */
    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mFireBaseChatLoginInterFace.onSuccessFulPasswdReset();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mFireBaseChatLoginInterFace.onUnSuccessFulPasswdReset();
            }
        });
    }


    /**
     *
     */
    public void initNewUserInfo(final FirebaseUser user) {
        final User newUser = new User();
        newUser.email = user.getEmail();
        newUser.name = user.getEmail().substring(0, user.getEmail().indexOf("@"));
        newUser.avata = ApiConstant.STR_DEFAULT_BASE64;
        newUser.status.timestamp = System.currentTimeMillis();
        FirebaseDatabase.getInstance().getReference().child("user/" + newUser.email.substring(0,newUser.email.indexOf("@"))).setValue(newUser).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                FirebaseDatabase.getInstance().getReference().child("user/" + user.getEmail().substring(0, user.getEmail().indexOf("@")) + "/status/isOnline").setValue(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }



    /**
     * save user information
     */
    public void saveUserInfo() {
        /*FirebaseDatabase.getInstance().getReference().child("user/" + AppConstants.UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap hashUser = (HashMap) dataSnapshot.getValue();
                User userInfo = new User();
                userInfo.email = (String) hashUser.get("email");
                userInfo.avata = (String) hashUser.get("avata");
                mFireBaseChatLoginInterFace.saveValue(userInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG,databaseError.getMessage());
            }
        });*/
    }

    public void signOut(){
        if(user != null) {
            FirebaseDatabase.getInstance().getReference().child("user/" + user.getUid() + "/status/isOnline").setValue(false);
            mAuth.signOut();
        }
    }


    /**
     * function to get message from firebase
     * @param connectionPath where to look for message in firebase database "message/" + roomId...
     */
    public void getFirebaseMessage(String connectionPath){
        FirebaseDatabase.getInstance().getReference().child(connectionPath)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
