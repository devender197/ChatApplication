package com.example.a3logics.chatapplication.Common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import com.securepreferences.SecurePreferences;

import java.util.ArrayList;

public class PreferenceManager {

    private static String preferenceFile = "VCSharedPrefFile";
    private static String securedPreferenceFile = "VCSecuredSharedPrefFile.xml";
    private static SecurePreferences securePrefs = null;

    private PreferenceManager() {
        securePrefs = new SecurePreferences(VendorCrushApplication.getInstances(), "", securedPreferenceFile);
    }

    public static void remove(String key, Context activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.remove(key);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static void clear(Context activity){
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.clear();
            editor.apply();
            editor.commit();
        } catch (Exception e) {
        }
    }

    public static void putOnSharedPreference(String key, boolean value, Activity activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static void putOnSharedPreference(String key, boolean value, Context activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putBoolean(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static void putOnSharedPreference(String key, String value, Activity activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static void putOnSharedPreference(String key, String value, Context activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }

    public static void putOnSharedPreference(String key, int value, Activity activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }
    public static void putOnSharedPreference(String key, int value, Context activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(key, value);
            editor.apply();
        } catch (Exception e) {
        }
    }
    //saving data in arraylist
    public static void putOnSharedPreference(String key, ArrayList<String> value, Activity activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, ObjectSerializer.serialize(value));
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //saving data in arraylist
    public static void putOnSharedPreference(String key, ArrayList<String> value, Context activity) {
        try {
            if (activity == null)
                return;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(key, ObjectSerializer.serialize(value));
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //getting saved data in arraylist
    public static ArrayList<String> getArrayListSharedPreference(String key, Context activity) {
        try {
            if (activity == null)
                return null;
            SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);
            return (ArrayList<String>) ObjectSerializer.deserialize(sharedPref.getString(key, ObjectSerializer.serialize(new ArrayList())));
        }catch (Exception e){
            e.printStackTrace();
            return  null;
        }
    }

    public static boolean getBooleanFromSharedPreferences(String key, Activity activity) {
        boolean defaultValue = false;

        if (activity == null)
            return defaultValue;

        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, defaultValue);
    }

    public static boolean getBooleanFromSharedPreferences(String key, Context activity) {
        boolean defaultValue = false;

        if (activity == null)
            return defaultValue;

        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        return sharedPref.getBoolean(key, defaultValue);
    }

    public static String getStringFromSharedPreferences(String key, Activity activity) {
        String defaultValue = "";

        if (activity == null)
            return defaultValue;

        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        return sharedPref.getString(key, defaultValue);
    }

    public static String getStringFromSharedPreferences(String key, Context activity) {
        String defaultValue = "";


        if (activity == null)
            return defaultValue;

        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        return sharedPref.getString(key, defaultValue);
    }

    public static int getIntFromSharedPreferences(String key, Context activity) {
        int defaultValue = 0;

        if (activity == null)
            return defaultValue;

        SharedPreferences sharedPref = activity.getSharedPreferences(preferenceFile, Context.MODE_PRIVATE);

        return sharedPref.getInt(key, defaultValue);
    }

    // Secured Shared preferences

    public static void remove(String key, Context activity, boolean secured) {
        if (secured) {
            try {
                if (null == securePrefs) {
                    securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
                }
                SecurePreferences.Editor editor = securePrefs.edit();
                editor.remove(key);
                editor.apply();
            } catch (Exception e) {
            }
        } else {
            remove(key, activity);
        }
    }

    public static void putOnSharedPreference(String key, boolean value, Activity activity, boolean secured) {
        if (secured) {
            try {
                if (activity == null)
                    return;
                if (null == securePrefs) {
                    securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
                }
                SecurePreferences.Editor editor = securePrefs.edit();
                editor.putBoolean(key, value);
                editor.apply();
            } catch (Exception e) {
            }
        } else {
            putOnSharedPreference(key, value, activity);
        }
    }

    public static void putOnSharedPreference(String key, String value, Activity activity, boolean secured) {
        if (secured) {
            try {
                if (activity == null)
                    return;
                if (null == securePrefs) {
                    securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
                }
                SecurePreferences.Editor editor = securePrefs.edit();
                editor.putString(key, value);
                editor.apply();
            } catch (Exception e) {
            }
        } else {
            putOnSharedPreference(key, value, activity);
        }
    }

    public static void putOnSharedPreference(String key, String value, Context context, boolean secured) {
        if (secured) {
            try {
                if (context == null)
                    return;
                if (null == securePrefs) {
                    securePrefs = new SecurePreferences(context, "", securedPreferenceFile);
                }
                SecurePreferences.Editor editor = securePrefs.edit();
                editor.putString(key, value);
                editor.apply();
            } catch (Exception e) {
            }
        } else {
            putOnSharedPreference(key, value, context);
        }
    }

    public static void putOnSharedPreference(String key, int value, Activity activity, boolean secured) {
        if (secured) {
            try {
                if (activity == null)
                    return;
                if (null == securePrefs) {
                    securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
                }
                SecurePreferences.Editor editor = securePrefs.edit();
                editor.putInt(key, value);
                editor.apply();
            } catch (Exception e) {
            }
        } else {
            putOnSharedPreference(key, value, activity);
        }
    }

    public static boolean getBooleanFromSharedPreferences(String key, Activity activity, boolean secured) {
        if (secured) {
            if (activity == null)
                return false;
            if (null == securePrefs) {
                securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
            }
            return securePrefs.getBoolean(key, false);
        } else {
            return getBooleanFromSharedPreferences(key, activity);
        }
    }

    public static String getStringFromSharedPreferences(String key, Activity activity, boolean secured) {
        if (secured) {
            if (activity == null)
                return null;
            if (null == securePrefs) {
                securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
            }
            return securePrefs.getString(key, "");
        } else {
            return getStringFromSharedPreferences(key, activity);
        }
    }

    public static String getStringFromSharedPreferences(String key, Context activity, boolean secured) {
        if (secured) {
            if (activity == null)
                return null;
            if (null == securePrefs) {
                securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
            }
            return securePrefs.getString(key, "");
        } else {
            return getStringFromSharedPreferences(key, activity);
        }
    }

    public static int getIntFromSharedPreferences(String key, Activity activity, boolean secured) {
        if (secured) {
            if (activity == null)
                return 0;
            if (null == securePrefs) {
                securePrefs = new SecurePreferences(activity, "", securedPreferenceFile);
            }
            return securePrefs.getInt(key, 0);
        } else {
            return getIntFromSharedPreferences(key, activity);
        }
    }
}
