package cn.fanrunqi.materiallogin.Activity.Instance;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * Created by Akanksha on 3/28/2018.
 */

public class Utils {


    public static void savePref(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("login",Context.MODE_PRIVATE);
    }

    public static String volleyError(VolleyError error){

        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            return "Please Connect To Proper Internet";
        } else if (error instanceof AuthFailureError) {
            return "Credentials Did Not Match";
        } else if (error instanceof ServerError) {
            return "Server Not Responding. Kindly Contact Admin";
        } else if (error instanceof NetworkError) {
            return "Server Not Responding. Kindly Contact Admin";
        } else if (error instanceof ParseError) {
            return "Server Not Responding. Kindly Contact Admin";
        }

        return "";

    }


}
