package br.com.pedrohbhrj.infra.payment;

import br.com.pedrohbhrj.models.Payment;
import br.com.pedrohbhrj.models.User;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class StripePaymentService {

    public HashMap<String,String> processPayment(Long amountInCents, String userEmail) throws StripeException {

        HashMap<String,String> values = new HashMap<>();

        PaymentIntentCreateParams params = PaymentIntentCreateParams
                .builder()
                .setAmount(amountInCents)
                .setCurrency("brl")
                .setReceiptEmail(userEmail)
                .build();

        PaymentIntent intent = PaymentIntent.create(params);

        values.put("transactionId",intent.getId());
        values.put("clientSecret",intent.getClientSecret());

        return values;
    }
}
