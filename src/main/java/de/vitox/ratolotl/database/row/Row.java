package de.vitox.ratolotl.database.row;

import lombok.Getter;

import java.util.Map;
import java.util.Optional;

public class Row {

    @Getter
    private Map<String, Object> values;

    public Row(Map<String, Object> results) {
        this.values = results;
    }

    public String getString(String columnName) {
        return this.get(columnName, String.class, "");
    }

    public int getInt(String columnName) {
        return this.get(columnName, Integer.class, -1);
    }

    public double getDouble(String columnName) {
        return this.get(columnName, Double.class, -1D);
    }

    public long getLong(String columnName) {
        return this.get(columnName, Long.class, -1L);
    }

    public boolean getBoolean(String columnName) {
        return this.get(columnName, Boolean.class, false);
    }

    public <T> T get(String columnName, Class<T> clazz, T defaultValue) {
        Optional<Object> valueOptional = this.values.entrySet().stream().filter(e -> e.getKey().equalsIgnoreCase(columnName)).map(Map.Entry::getValue).findFirst();

        if (!valueOptional.isPresent()) {
            return defaultValue;
        }

        if (clazz.isInstance(valueOptional.get())) {
            return clazz.cast(valueOptional.get());
        }

        return null;
    }
}
