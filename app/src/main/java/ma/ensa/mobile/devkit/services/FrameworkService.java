package ma.ensa.mobile.devkit.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ma.ensa.mobile.devkit.beans.Framework;
import ma.ensa.mobile.devkit.dao.IDao;
import ma.ensa.mobile.devkit.volleySinglothon.VolleySingleton;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class FrameworkService implements IDao<Framework> {

    private static FrameworkService instance ;
    private List<Framework> frameworks;
    private Context context;
    private RequestQueue requestQueue;
    private String urlString = "http://10.0.2.2/DevKit/ws/";

    private Gson gson = new Gson();


    private FrameworkService(Context context) {
        this.context = context;
        this.frameworks = new ArrayList<>();
        this.requestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }

    public static FrameworkService getInstance(Context context) {
        if(instance == null)
            instance = new FrameworkService(context);
        return instance;
    }

    //  ********************** ADD METHOD ***********************  //

    public interface AddFrameworkCallback {
        void onSuccess(String message);
        void onError(String error);
    }

    @Override
    public boolean addFramework(Framework obj , AddFrameworkCallback callback) {
        String url = urlString + "createFramework.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("ResponseAdd", "onResponse: " + response);
                        try {
                            // Assurez-vous que le backend renvoie une réponse attendue ici
                            callback.onSuccess("Framework Added Successfully!!");
                        } catch (JsonSyntaxException e) {
                            callback.onError("Error parsing response: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("ErrorAdd", "onResponse: " + volleyError.getMessage());
                        callback.onError("Error: " + volleyError.getMessage());
                    }
                }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("name", obj.getName());
                params.put("description", obj.getDescreption());
                params.put("domain", obj.getDomain());
                params.put("dependencies", obj.getDependencies());
                params.put("image_path", encodeImageToBase64(obj.getImage_path())); // Encodez l'image en base64 ici
                return params;
            }
        };

        requestQueue.add(request);
        return true;
    }

    // Ajoutez cette méthode pour encoder l'image en base64
    private String encodeImageToBase64(String imagePath) {
        File imgFile = new File(imagePath);
        if (imgFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT);
        }
        return null;
    }


    //  ********************** DELETE METHOD ***********************  //

    public interface DeleteFrameworkCallback{
        void onSuccess(String message);
        void onError(String error);
    }


    @Override
    public boolean deleteFramework(int id , DeleteFrameworkCallback callback) {

        String url = urlString +"deleteFramework.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("RspDelete" , "onResponse: "+s);

                        try{
                            callback.onSuccess("Framework deleted Successfully!!");

                        }catch (JsonSyntaxException e){
                            callback.onError("Error : "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("ErrorDelete" , "onResponse Error: "+volleyError);

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> params = new HashMap<>();
                params.put("id", String.valueOf(id));
                return params ;
            }
        };



        requestQueue.add(request);
        return true;
    }


    //  ********************** UPDATE METHOD ***********************  //

    public interface UpdateFrameworkCallback{
        void onSuccess(String message);
        void onError(String error);
    }
    @Override
    public boolean updateFramework(Framework obj , UpdateFrameworkCallback callback) {
        String url = urlString+"updateFramework.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("RspUpdate", "onResponse: "+s);
                        try{
                          callback.onSuccess("Framework updated successfully !");
                        } catch (JsonSyntaxException e){
                            callback.onError("Error parsing response : "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("ErrorUpdate", "onResponseError: " + volleyError.getMessage());
                callback.onError(volleyError.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String , String> params = new HashMap<>();
                params.put("id",String.valueOf(obj.getId()));
                params.put("name", obj.getName());
                params.put("descreption", obj.getDescreption());
                params.put("domain", obj.getDomain());
                params.put("dependencies", obj.getDependencies());
                params.put("image_path", obj.getImage_path());

                return params ;
            }
        };

        requestQueue.add(request);
        return true;
    }


    //  ********************** FIND BY ID METHOD ***********************  //
    @Override
    public Framework findFrameworkById(int id) {
        return null;
    }


    //  ********************** FIND ALL METHOD ***********************  //

    public interface FrameworkCallback {
        void onFrameworksLoaded(List<Framework> frameworks);

        void onError(String message);
    }

    @Override
    public void findAll(FrameworkCallback callback) {
        String url = "http://10.0.2.2/DevKit/ws/loadFramework.php";
        Log.d("findall", "findAll: dkhlt");

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.d("RspFindAll", "onResponse: rsp " + s);
                        Type ftype = new TypeToken<List<Framework>>() {
                        }.getType();
                        frameworks = gson.fromJson(s, ftype);

                        try {
                            callback.onFrameworksLoaded(frameworks);
                        } catch (JsonSyntaxException e) {
                            Log.e("JSON Parsing ", "Error Parsing JSON : " + e.getMessage());
                            callback.onError(e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("ErrorFindAll", "onResponseError: walo " + volleyError.getMessage());
                        callback.onError(volleyError.getMessage());
                    }
                }
        );

        requestQueue.add(request);
        Log.d("findall", "findAll: khrjt");
    }




    // ************  Get Framework At Position ************************  //

    public Framework  getFrameworkAtPosition(int position){

        for(Framework f : this.frameworks )
            if(f.getId() == position)
                return f;
        return null;
}



}