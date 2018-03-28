package cn.fanrunqi.materiallogin.Activity.Instance;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by AHS on 12/27/2017.
 */

public class VolleyInstance {

    private static VolleyInstance mInstance;
    private RequestQueue requestQueue;
    private static Context mctx;

    private VolleyInstance(Context context)
    {
        mctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyInstance getInstance(Context context)
    {
        if(mInstance==null)
        {
            mInstance = new VolleyInstance(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue()
    {
        if(requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mctx.getApplicationContext());

        }
        return  requestQueue;
    }

    public <T>void  addToRequestQue (Request<T> request)
    {
        requestQueue.add(request);
    }

}
