package spring.taxi.app.ride.util.taxi;

import lombok.Getter;

@Getter
public abstract class Taxi {
    protected float price;
    protected float distance;
    protected int time;
}
