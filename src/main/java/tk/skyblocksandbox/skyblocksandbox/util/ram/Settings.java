package tk.skyblocksandbox.skyblocksandbox.util.ram;

import java.util.HashMap;
import java.util.Map;

public final class Settings {

    private final Map<FieldType, Map<String, Object>> settings = new HashMap<>();

    public Settings() {
        settings.put(FieldType.STRING, new HashMap<>());
        settings.put(FieldType.BOOLEAN, new HashMap<>());
        settings.put(FieldType.INTEGER, new HashMap<>());
        settings.put(FieldType.LONG, new HashMap<>());

        settings.put(FieldType.CLASS, new HashMap<>());
        settings.put(FieldType.ENUM, new HashMap<>());
    }

    /*
     * Of Field
     */

    public Map<String, Object> getSettingsOfType(FieldType fieldType) {
        return settings.get(fieldType);
    }

    public void replaceSettingsOfType(FieldType fieldType, Map<String, Object> replaceWith) {
        settings.put(fieldType, replaceWith);
    }

    /*
     * Get in Field, of Key
     */

    public Object getSettingOfKey(FieldType fieldType, String key) {
        return settings.get(fieldType).get(key);
    }

    /*
     * Set in Field, of Key
     */

    public void setSettingOfKey(String key, String value) {
        settings.get(FieldType.STRING).put(key, value);
    }

    public void setSettingOfKey(String key, boolean value) {
        settings.get(FieldType.BOOLEAN).put(key, value);
    }

    public void setSettingOfKey(String key, int value) {
        settings.get(FieldType.INTEGER).put(key, value);
    }

    public void setSettingOfKey(String key, long value) {
        settings.get(FieldType.LONG).put(key, value);
    }

    public void setSettingOfKey(String key, Class<?> value) {
        settings.get(FieldType.CLASS).put(key, value);
    }

    public void setSettingOfKey(String key, Enum<?> value) {
        settings.get(FieldType.ENUM).put(key, value);
    }

    public enum FieldType {
        STRING,
        BOOLEAN,
        INTEGER,
        LONG,

        CLASS,
        ENUM
    }

}
