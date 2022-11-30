package spring.taxi.app.ride.util;


import com.google.gson.annotations.SerializedName;
import lombok.Data;
import spring.taxi.app.ride.util.taxi.ComfortTaxi;
import spring.taxi.app.ride.util.taxi.EconomTaxi;
import spring.taxi.app.ride.util.taxi.TaxiSerivce;

@Data
public class TaxiModel {
    @SerializedName("service")
    private TaxiSerivce serivce;

    private EconomTaxi econom;

    private ComfortTaxi comfort;
}
