package com.github.farbfuchs.hoerfuchs;

public class GlossaryEntry {
    private String key;
    private String value;

    public GlossaryEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static GlossaryEntry create(String line) throws IllegalStateException {
        int separatorPos = line.indexOf(":");
        if (separatorPos < 0) {
            throw new IllegalStateException("Key-value separator ':' missing in line: " + line);
        }
        return new GlossaryEntry(line.substring(0, separatorPos).trim(), line.substring(separatorPos+1).trim());
    }

    public static String normalizeKey(String unnormalizedKey) {
        return unnormalizedKey.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    public String getKey() {
        return key;
    }

    public String getNormalizedKey() {
        return normalizeKey(key);
    }

    public String toString() {
        return "Key: " + key + "\nValue: " + value;
    }
}
