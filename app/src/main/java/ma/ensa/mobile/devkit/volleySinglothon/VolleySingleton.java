package ma.ensa.mobile.devkit.volleySinglothon;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {

    private static VolleySingleton instance ;
    private RequestQueue requestQueue ;
    private static Context context ;

    private VolleySingleton(Context context) {
        this.context = context ;

    }

    public static synchronized  VolleySingleton getInstance(Context context){
        if(instance == null){
            instance = new VolleySingleton(context);

        }
        return instance ;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null )
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public <T> void  addRequestQueue(Request<T> request){
        getRequestQueue().add(request);
    }


}
