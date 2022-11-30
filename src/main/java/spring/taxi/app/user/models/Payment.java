package spring.taxi.app.user.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    private String name;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private long amount;
    private long quantity;
}
