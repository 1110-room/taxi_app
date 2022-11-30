package spring.taxi.app.ride.util;

import com.google.gson.Gson;
import spring.taxi.app.ride.util.taxi.TaxiSerivce;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class TaxiParser {
    public static TaxiModel getOptimalServiceInfo(double[][] coords) throws IOException, NoSuchElementException {
        return getTotalInfoByServices(coords).stream().min(
                Comparator.comparing(taxi -> taxi.getEconom().getPrice())
        ).orElseThrow();
    }

    public static List<TaxiModel> getTotalInfoByServices(double[][] coords) throws IOException {
        List<TaxiModel> taxiInfo = new ArrayList<>();
        for (TaxiSerivce taxiSerivce : TaxiSerivce.values()) {
            taxiInfo.add(parseInfo(taxiSerivce, coords));
        }
        return taxiInfo;
    }

    public static TaxiModel parseInfo(TaxiSerivce taxiService, double[][] coords) throws IOException {
        Formatter formatter = new Formatter(Locale.US);
        String command = formatter.format(
                "python3 python/parse.py %s %f %f %f %f",
                taxiService.name(), coords[0][0], coords[0][1], coords[1][0], coords[1][1]
        ).toString();
        Process p = Runtime.getRuntime().exec(command);

        InputStream stdout = p.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdout);
        BufferedReader br = new BufferedReader(isr);

        StringBuilder builder = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            builder.append(line);
        }

        System.out.println(builder);

        Gson gson = new Gson();

        TaxiModel taxiData = gson.fromJson(builder.toString(), TaxiModel.class);
        System.out.println(taxiData.getSerivce());
        System.out.println(taxiData.getEconom().getDistance());
        return taxiData;
    }
}
