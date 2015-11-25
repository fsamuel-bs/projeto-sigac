package com.sigac.firefighter.model;

import android.util.Log;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sigac.firefighter.Victim;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class ApiModelManager extends BaseModelManager {

    final static String StationPath = "http://192.168.1.2:1880/";
    final static String DbPath = "http://powerful-forest-9086.herokuapp.com/?q= ";

    private static class InstanceHolder {
        private static final ApiModelManager INSTANCE = new ApiModelManager();
    }

    public static ApiModelManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private ApiModelManager() {
       /* Prevents outside instantiation */
    }

    @Override
    public void deleteVictim(String id) {
        String query = "delete%20from%20victims%20where%20id=" + id;
        getQuery(DbPath, query);
        notifyObservers();
    }

    @Override
    public List<Victim> getVictims() throws Exception  {
        String query = "select%20*%20from%20victims";
        String ret = getQuery(DbPath, query);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Victim>>(){}.getType();
        List<Victim> victims = gson.fromJson(ret, listType);

        return victims;
    }

    @Override
    public void persistVictim(Victim victim) {
        Gson gson = new GsonBuilder().registerTypeAdapter(Victim.class, new Victim.Serializer()).create();
        String victimObject = gson.toJson(victim);
        Log.e("VICTIM", victimObject);
        postQuery(StationPath, "victim", victimObject);
        notifyObservers();
    }

    @Override
    public String getTag() {
        String responseJson = getQuery(StationPath, "gettag");

        if (responseJson == null || responseJson.isEmpty()) {
            return "";
        }

        try {
            JSONObject tagObject = new JSONObject(responseJson);
            if (tagObject.has("tag")) {
                return tagObject.getString("tag");
            }
            return null;
        } catch (JSONException e) {
            Throwables.propagate(e);
        }

        return null;
    }

    private String getQuery(String route, String query) {
        BufferedReader in = null;
        try {
            URL url = new URL(route + query);;
            URLConnection connection = url.openConnection();
            in = new BufferedReader(
                    new InputStreamReader(
                            connection.getInputStream()));
        } catch (IOException e) {
            /* Silently fail if the server is not online */
            e.printStackTrace();
            return null;
        }

        try {
            String inputLine = in.readLine();
            in.close();

            return inputLine;
        }  catch (IOException e) {
            Throwables.propagate(e);
            return null;
        }
    }

    private void postQuery(String route, String query, String payload) {
        URL url = null;
        try {
            url = new URL(route + query);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestMethod("POST");

            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write(payload);
            wr.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
