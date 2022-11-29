package spring.taxi.app.ride.util;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TaxiParser {
    public static TaxiModel getInfo() throws IOException {
        Process p = Runtime.getRuntime().exec("python3 python/parse.py");

        InputStream stdout = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdout);
        BufferedReader br = new BufferedReader(isr);

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            builder.append(line);
        }

        Gson gson = new Gson();

        TaxiModel taxiData = gson.fromJson(builder.toString(), TaxiModel.class);
        return taxiData;
    }
}
