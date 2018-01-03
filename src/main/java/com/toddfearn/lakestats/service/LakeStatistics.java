package com.toddfearn.lakestats.service;

import com.google.gson.Gson;
import com.toddfearn.lakestats.model.LakeData;
import com.toddfearn.lakestats.model.LakeName;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.springframework.http.HttpHeaders.USER_AGENT;

@Component
public class LakeStatistics {
    private final String url = "http://www.weather.gov/btv/recreation";
    private final String searchString = "USGS gage at Burlington VT";

    public String get(LakeName lakeName) {
        try {
            String pageHtml = sendGet();
            LakeData lakeData = parseGet(pageHtml);
            Gson gson = new Gson();
            return gson.toJson(lakeData);
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String sendGet() throws IOException {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private LakeData parseGet(String html) {
        StringBuffer data = new StringBuffer();

        int position = html.indexOf(searchString);
        if(position == -1)
            return null;

        while(true) {
            char value = html.charAt(position);
            data.append(value);
            if(data.toString().contains("degrees"))
                break;
            position++;
        }

        String[] tokens = data.toString().split("    ");
        if(tokens.length != 3)
            return null;

        Double feet =  Double.parseDouble(tokens[1].trim().split(" ")[0]);
        Double degrees = Double.parseDouble(tokens[2].trim().split(" ")[0]);

        return new LakeData(LakeName.LAKE_CHAMPLAIN, tokens[0], degrees, feet);
    }
}
