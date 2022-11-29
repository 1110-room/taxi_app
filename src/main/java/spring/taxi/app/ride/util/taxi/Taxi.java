package spring.taxi.app.ride.util.taxi;

import lombok.Getter;

@Getter
public abstract class Taxi {
    protected double price;
    protected double dist;
    protected int time;
}
