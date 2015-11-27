package com.sigac.firefighter.model;

import android.os.Environment;
import android.util.Log;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
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

    static String STATION_PATH;
    final static String IP_ADDRESS_FILE = "ip.txt";

    private static class InstanceHolder {
        private static final ApiModelManager INSTANCE = new ApiModelManager();
    }

    public static ApiModelManager getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private ApiModelManager() {
        /* Prevents outside instantiation */
        String ipAddress = readIpFile();

        if (ipAddress == null || ipAddress.equals("")) {
            STATION_PATH = "http://192.168.1.2:1880/";
            writeIpFile(STATION_PATH);
        } else {
            STATION_PATH = ipAddress;
        }
    }

    private String readIpFile() {
        String ipAddress = null;

        File sdcard = Environment.getExternalStorageDirectory();
        File file = new File(sdcard, IP_ADDRESS_FILE);
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        ipAddress = sb.toString();
        return ipAddress;
    }

    private void writeIpFile(String ipAddress) {
        try {
            File sdcard = Environment.getExternalStorageDirectory();
            File file = new File(sdcard, IP_ADDRESS_FILE);
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(ipAddress.toCharArray());
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteVictim(String id) {
        postQuery(STATION_PATH, "delete", null);
        notifyObservers();
    }

    @Override
    public List<Victim> getVictims() throws Exception  {
        String payload = getQuery(STATION_PATH, "victims");
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Victim>>(){}.getType();
        List<Victim> victims = gson.fromJson(payload, listType);

        return victims;
    }

    @Override
    public String getTag() {
        String responseJson = getQuery(STATION_PATH, "gettag");

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

    @Override
    public void persistVictim(Victim victim) {
        String victimObject = new Gson().<Victim>toJson(victim);
        postQuery(STATION_PATH, "victim", victimObject);
        notifyObservers();
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
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();

            return sb.toString();
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
            Log.e("SIGAC", e.getMessage());
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

            int httpResult = connection.getResponseCode();
            if (httpResult != 200) {
                Log.e("SIGAC", "Request not sent successfully");
            }
        } catch (IOException e) {
            Log.e("SIGAC", e.getMessage());
            e.printStackTrace();
        }
    }
}
