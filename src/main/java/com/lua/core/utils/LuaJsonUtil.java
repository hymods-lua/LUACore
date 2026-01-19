package com.lua.core.utils;

import org.bson.*;

public class LuaJsonUtil {

    public static BsonValue getValueByPath(BsonDocument doc, String path) {
        String[] parts = path.split("\\.");
        BsonValue current = doc;

        for (String part : parts) {
            if (current instanceof BsonDocument) {
                current = ((BsonDocument) current).get(part);
            } else {
                return null;
            }
            if (current == null) return null;
        }
        return current;
    }
    /**
     * Cambia un valor en un BsonDocument usando una ruta (ej: "Messages.Welcome")
     */
    public static void setValueByPath(BsonDocument doc, String path, String newValue) {
        String[] parts = path.split("\\.");
        BsonDocument current = doc;

        for (int i = 0; i < parts.length - 1; i++) {
            if (!current.containsKey(parts[i]) || !current.get(parts[i]).isDocument()) {
                current.put(parts[i], new BsonDocument());
            }
            current = current.getDocument(parts[i]);
        }

        // Intentamos parsear el valor (Si es número, booleano, o string)
        current.put(parts[parts.length - 1], parseValue(newValue));
    }

    private static BsonValue parseValue(String val) {
        if (val.equalsIgnoreCase("true")) return new BsonBoolean(true);
        if (val.equalsIgnoreCase("false")) return new BsonBoolean(false);
        try {
            if (val.contains(".")) return new BsonDouble(Double.parseDouble(val));
            return new BsonInt32(Integer.parseInt(val));
        } catch (NumberFormatException e) {
            return new BsonString(val);
        }
    }

    /**
     * Formatea el BsonValue para que se vea bonito en el chat de Hytale
     */
    public static String formatBsonValue(BsonValue value) {
        if (value.isString()) return value.asString().getValue();
        if (value.isNumber()) return String.valueOf(value.asNumber().doubleValue());
        if (value.isBoolean()) return value.asBoolean().getValue() ? "§atrue" : "§cfalse";
        if (value.isDocument()) return "§7{...}";
        if (value.isArray()) return "§7[...]";
        return value.toString();
    }
}
