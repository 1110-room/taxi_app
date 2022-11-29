package spring.taxi.app.ride.util;


import lombok.Getter;
import spring.taxi.app.ride.util.taxi.ComfortTaxi;
import spring.taxi.app.ride.util.taxi.EconomTaxi;

@Getter
public class TaxiModel {
    private EconomTaxi econom;
    private ComfortTaxi comfort;
}
