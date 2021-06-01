package tk.skyblocksandbox.skyblocksandbox.util.ram;

import java.util.*;

public final class FieldInstance {

    private final Map<Value, Object> values = new HashMap<>();

    /**
     * Initialize all values with null.
     */
    public FieldInstance() {
        values.put(Value.VALUE_0x1a, null);
        values.put(Value.VALUE_0x1b, null);
        values.put(Value.VALUE_0x1c, null);
        values.put(Value.VALUE_0x1d, null);
        values.put(Value.VALUE_0x1e, null);

        values.put(Value.VALUE_0x2a, null);
        values.put(Value.VALUE_0x2b, null);
        values.put(Value.VALUE_0x2c, null);
        values.put(Value.VALUE_0x2d, null);
        values.put(Value.VALUE_0x2e, null);
    }

    /**
     * @param field The value to write to.
     * @param data The data to write to the value.
     * @param serialize Should the object be serialized?
     * @return The previous data.
     */
    public Object writeValue(Value field, Object data, boolean serialize) {
        if(serialize) {

        }

        return values.put(field, data);
    }

    /**
     * @param field The value to write to.
     * @param data The data to write to the value.
     * @return The previous data.
     */
    public Object writeValue(Value field, Object... data) {
        List<Object> listedData = new ArrayList<>(Arrays.asList(data));
        return values.put(field, listedData);
    }

    /**
     * @param field The value to read from.
     * @return The object in the field. Returns null if there is nothing.
     */
    public Object readValue(Value field) {
        return values.get(field);
    }

    public enum Value {
        VALUE_0x1a,
        VALUE_0x1b,
        VALUE_0x1c,
        VALUE_0x1d,
        VALUE_0x1e,

        VALUE_0x2a,
        VALUE_0x2b,
        VALUE_0x2c,
        VALUE_0x2d,
        VALUE_0x2e
    }

}
