package net.splodgebox.discordpaypalbot.utils;

import net.splodgebox.discordpaypalbot.data.Role;

import javax.json.*;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigReader {

    public static JsonObject readConfig(String fileName) {
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            JsonReader jsonReader = Json.createReader(inputStream);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            return jsonObject;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, Role> getRoles(JsonObject jsonObject, String prefix) {
        Map<String, Role> rolesMap = new HashMap<>();

        Set<Map.Entry<String, JsonValue>> entrySet = jsonObject.entrySet();

        for (Map.Entry<String, JsonValue> entry : entrySet) {
            String currentKey = prefix.isEmpty() ? entry.getKey() : prefix + "." + entry.getKey();

            if (entry.getValue().getValueType() == JsonValue.ValueType.OBJECT) {
                rolesMap.putAll(getRoles((JsonObject) entry.getValue(), currentKey));
            } else {
                if (currentKey.endsWith(".display")) {
                    String roleName = currentKey.substring(0, currentKey.lastIndexOf(".display"));
                    Role role = rolesMap.computeIfAbsent(roleName, k -> new Role());
                    role.setDisplay(entry.getValue().toString());
                } else if (currentKey.endsWith(".id")) {
                    String roleName = currentKey.substring(0, currentKey.lastIndexOf(".id"));
                    Role role = rolesMap.computeIfAbsent(roleName, k -> new Role());
                    role.setId(entry.getValue().toString());
                }
            }
        }

        return rolesMap;
    }

}
