package com.example.study;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CommunicationService {
    private static final String TAG = "CommunicationService";

    public String getJsonString(String urlRequest)throws IOException{ //отправка запроса
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(urlRequest).build();
        Response response = client.newCall(request).execute();

        String result = response.body().string();
        return result;
    }

    public List<Controller> fetchItems(){
        List<Controller> controllerItems = new ArrayList<>();
        try {
            String urlRequest = Uri.parse("http://"+"192.168.100.11" + ":" + 5050).buildUpon()
                    .appendQueryParameter("method","getPinsStatus").build().toString();
            String jsonString = getJsonString(urlRequest);
            JSONObject jsonBody = new JSONObject(jsonString);
            parseItems(controllerItems,jsonBody);
        }catch (IOException ioe){
            Log.i(TAG, "Ошибка загрузки  данных",ioe);
        }catch (JSONException joe){
            Log.i(TAG,"Ошибка парсинга JSON",joe);
        }
        return controllerItems;
    }

    private void parseItems(List<Controller> controllers, JSONObject jsonObject) throws IOException,JSONException{
        JSONObject controllersJsonObject = jsonObject.getJSONObject("controllers");
        JSONArray controllerJsonArray = controllersJsonObject.getJSONArray("controller");

        for (int i = 0 ; i < controllerJsonArray.length(); i++){
            JSONObject controllerJsonObject = controllersJsonObject.getJSONObject(" i");
            Controller controller = new Controller();
            controller.setID(controllerJsonObject.getInt("ID"));
            controller.setName(controllerJsonObject.getString("Name"));
            controller.setGPIO(controllerJsonObject.getString("GPIO"));
            controller.setStatus(controllerJsonObject.getBoolean("Status"));
            controllers.add(controller);
        }
    }

}
