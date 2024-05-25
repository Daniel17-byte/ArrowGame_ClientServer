package org.arrowgame.client.utils;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;

public class LanguageManager {
    private static JSONObject currentLanguage;

    public static CustomLocale fromStringToLocale(String language) {
        return switch (language) {
            case "ENGLISH" -> CustomLocale.ENGLISH;
            case "ROMANIAN" -> CustomLocale.ROMANIAN;
            case "DEUTSCH" -> CustomLocale.DEUTSCH;
            case "ITALIAN" -> CustomLocale.ITALIAN;
            case "FRENCH" -> CustomLocale.FRENCH;
            default -> null;
        };
    }

    public static void loadLanguage(CustomLocale locale) {
        try {
            InputStream is = LanguageManager.class.getResourceAsStream("/languages/" + locale.language() + ".json");
            if (is == null) {
                is = LanguageManager.class.getResourceAsStream("/languages/en.json");
            }
            JSONTokener token;
            if (is != null) {
                token = new JSONTokener(is);
                currentLanguage = new JSONObject(token);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            currentLanguage = new JSONObject();
        }
    }

    public static String getString(String key) {
        return currentLanguage.optString(key, "Key not found: " + key);
    }

    public static String getStringForKey(String parentKey, String childKey) {
        JSONObject parentObject = currentLanguage.optJSONObject(parentKey);

        if (parentObject == null) {
            return "Parent key not found: " + parentKey;
        }

        Object value = parentObject.opt(childKey);

        if (value == null) {
            return "Child key not found: " + childKey;
        } else if (!(value instanceof String)) {
            return "Invalid value for child key: " + childKey;
        }

        return (String) value;
    }

}