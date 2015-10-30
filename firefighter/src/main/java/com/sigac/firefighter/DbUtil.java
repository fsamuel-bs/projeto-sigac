package com.sigac.firefighter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class DbUtil {
    public static void delete_victim (String id) throws Exception {
        http_query("delete%20from%20victims%20where%20id=" + id);
    }

    public static List<Victim> get_victims () throws Exception  {
        String lookupQuery = "select%20*%20from%20victims";
        String ret;

        ret = http_query(lookupQuery);

        Gson gson = new Gson();
        Type listType = new TypeToken<List<Victim>>(){}.getType();
        List<Victim> victims = gson.fromJson(ret, listType);

        return victims;
    }

    public static void persist_victim (Victim victim) throws Exception {
        //insert into victims values(id, state, sex, age, name, occurrence_id);
        String insertQuery = String.format("insert into victims (id, state, sex, age, name, occurrence_id) " +
                        "values (%d, %d, %d, %d, \'%s\', %d);",
                victim.getId(), victim.getState().ordinal(), victim.getSex().ordinal(),
                victim.getAge().ordinal(), victim.getName(), victim.getOccurrenceId());

        //  Change these to avoid http505 error
        insertQuery = insertQuery.replaceAll("'", "%27");
        insertQuery = insertQuery.replaceAll(" ", "%20");

        String ret = http_query(insertQuery);
    }

    public static String http_query (String query) throws  Exception {
        URL yahoo = new URL("http://powerful-forest-9086.herokuapp.com/?q=" + query);
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;
        inputLine = in.readLine();
        in.close();

        // Heroku db app is said to return any query as a single string
        return inputLine;
    }
}
