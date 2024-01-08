package net.splodgebox.discordpaypalbot.config;

import net.splodgebox.discordpaypalbot.utils.ConfigReader;

import javax.json.JsonObject;

public class PayPalConfig {

    private static final String CONFIG_FILE = "paypal.json";
    private static final JsonObject config;

    static {
        config = ConfigReader.readConfig(CONFIG_FILE);
    }

    public static String getAccessToken() {
        return config.getString("accessToken");
    }

}
