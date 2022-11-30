package spring.taxi.app.user.controllers;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.taxi.app.user.models.Payment;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StripeController {
    private Gson gson = new Gson();

    private static void init() {
        Stripe.apiKey = "sk_test_51M9rLLHebm4EbEJh18DkNZHyiilokLN4n94r0IhawGqAcKuWY6akI3gXXWpobWfAOFX2A95S1O3v5XGI5NOq1QlJ005MoXqzrn";
    }

    @PostMapping("/payment")
    public String paymentWithCheckoutPage(@RequestBody Payment payment) throws StripeException{
        init();
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                .builder().setName(payment.getName()).build()
                                ).build()
                        ).build()
                ).build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return gson.toJson(responseData);
    }

}
