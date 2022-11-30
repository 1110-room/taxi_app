package spring.taxi.app.ride.models;


import java.util.Arrays;

public enum RideStatus {
    OPEN,
    FULL,
    MOVE,
    FINISH;

    public static boolean isRideStatus(String status) {
        return Arrays.stream(RideStatus.values()).anyMatch(
                rideStatus -> rideStatus.name().equals(status)
        );
    }
}
