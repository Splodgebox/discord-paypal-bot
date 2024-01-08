package net.splodgebox.discordpaypalbot.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PayPalApiClientTest {

    private PayPalApiClient payPalApiClient;

    @BeforeEach
    public void init() {
        payPalApiClient = new PayPalApiClient();
    }

    @Test
    void isValidPurchase() {
        // assertTrue(payPalApiClient.isValidPurchase("5RU31088X43359101"));
    }
}