package net.splodgebox.discordpaypalbot.config;

import net.splodgebox.discordpaypalbot.data.Role;
import net.splodgebox.discordpaypalbot.utils.ConfigReader;

import javax.json.JsonObject;
import java.util.Map;

public class BotConfig {

    private static final String CONFIG_FILE = "bot.json";
    private static final JsonObject config;

    static {
        config = ConfigReader.readConfig(CONFIG_FILE);
    }

    public static String getBotToken() {
        return config.getString("token");
    }

    public static String getLogChannel() {
        return config.getString("logChannel");
    }

    public static Map<String, Role> getRoles() {
        return ConfigReader.getRoles(config, "roles");
    }

    public static Role getRole(String role) {
        return getRoles().get(role);
    }

}
