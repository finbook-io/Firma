package io.finbook.controller;

public class MessageConstructor {

    public static String toJSON(String identifier, byte[] textSigned) {
        return "{" + getJsonFromString("id", identifier) + "," + getJsonFromArray("sign", textSigned) + "}";
    }

    private static String getJsonFromString(String id, String element) {
        return "\"" + id + "\": \"" + element + "\"";
    }

    private static String getJsonFromArray(String id, byte[] element) {
        return "\"" + id + "\": [" + getJsonElementsBy(element) + "]";
    }

    private static String getJsonElementsBy(byte[] elements) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < elements.length - 1; i++) {
            sb.append("\"").append(elements[i]).append("\",");
        }
        sb.append("\"").append(elements[elements.length - 1]).append("\"");
        return sb.toString();
    }
}
