package com.example.a3logics.chatapplication.firebase_chat.firebase_login;

import android.os.Bundle;

public class ApiConstant {
        public static final String API_BASE_URL="http://10.10.10.66:1337";//Deepak
        //public static final String API_BASE_URL="http://10.10.15.235:1337";//Pratul
        public static final String API_BASE_PATH            =   "/rest/";
        public static final String GOOGLE_API               =   "AIzaSyA3IQTRXopFWvKhSsrkKAPyPGiKIYDzaMk";
        public static final String BUILD_SERVER             =   " Local";
        public static final String FIREBASE_STORAGE_PATH    =   "gs://chatapplication-947d8.appspot.com";
        public static final String MERCHANT_ID              =   "r8mtsk7czkmk9rwm";
        public static final String PUBLIC_KEY               =   "4rhdbrtdmh9vrpp4";
        public static final String PRIVATE_KEY              =   "423ff53ea39df6c878070c8219b702ff";
        public static final String FORGOT_PASSWORD_PATH=API_BASE_URL+"/forgot-password";
        public static       String SECRET_KEY               =   "sk_test_1blD3OnlQS4CsSWkDgLwt9l4";
        public static       String PUBLISHABLE_KEY          =   "pk_test_OPI4Ps5NIyb6YavQCsznOPJT";
        //FIREBASE CONSTANT
        //public static String FIREBASE_STORAGE_PATH        = "gs://vendercrushdemo.appspot.com";
        public static String FIREBASE_SENDOR_NAME           = "senderName";
        public static String FIREBASE_RECEIVER_NAME         = "receiverName";
        public static String FIREBASE_READ_STATUS           = "readStatus";
        public static String USER_NAME                      = "userName";
        public static String MESSAGE_TIME_STAMP             = "messageTimeStamp";
        public static String LATEST_MESSAGE                 = "latestMessage";
        public static String FIREBASE_SENDOR_ID             = "senderId";
        public static String FIREBASE_UID_NAME              = "uid";
        public static String FIREBASE_CREATED_DATE          = "createdDate";
        public static String FIREBASE_TIMESTAMP             = "timeStamp";
        public static String FIREBASE_MEMBERS               = "members";
        public static String FIREBASE_MEMBERS_UNREAD_COUNT  = "unread_count";
        public static String FIREBASE_TEXT                  = "text" ;
        public static String FIREBASE_IMAGE_URLA            = "imageUrlA";
        public static String FIREBASE_AUDIO_URLA           = "audioURL_a";
        public static String FIREBASE_PDF_URLA             = "pdfUrlA";
        public static String FIREBASE_IMAGE_URLI            = "fileUrlI";
        public static String FIREBASE_USER_ROLE             = "userRole";
        public static String FIREBASE_SYSTEM_TEXT           = "isSystemText";
        public static String FIREBASE_USERNAME_KEY          = "name";
        public static String FIREBASE_JOINING_DATE_KEY      = "joining_date";
        public static int    REQUEST_IMAGE_CAPTURE          = 5;
        public static int    PICK_IMAGE                     = 6;
        public static Bundle TICKET_BUNDLE ;
        public static Bundle CHAT_BUNDLE ;


        public static String FIREBASE_NOT_SET               = "NOT SET";
        public static String FIREBASE_TICKETS_KEY           = "tickets/";
        public static String FIREBASE_CHATBOX               = "chatBox/";
        public static String FIREBASE_TICKETS_NAME          = "tickets";
        public static String FIREBASE_MESSAGE_KEY           = "/messages";
        public static String FIREBASE_MEMBER_KEY            = "/members";
        public static String STR_DEFAULT_BASE64             = "default";
        public static String SERVER_DATE_TIMEZONE           = "UTC";

}
