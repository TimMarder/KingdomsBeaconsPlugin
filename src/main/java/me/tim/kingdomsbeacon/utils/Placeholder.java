package me.tim.kingdomsbeacon.utils;

public class Placeholder {
    private final String key;
    private final String value;

    public Placeholder(String placeHolder, String replace) {
        this.key = placeHolder;
        this.value = replace;
    }

    public Placeholder(String placeHolder, boolean bool) {
        this.value = String.valueOf(bool);
        this.key = placeHolder;
    }

    public Placeholder(String placeHolder, int i) {
        this.value = String.valueOf(i);
        this.key = placeHolder;
    }

    public Placeholder(String placeHolder, double i) {
        this.value = String.valueOf(i);
        this.key = placeHolder;
    }

    public Placeholder(String placeHolder, long i) {
        this.value = String.valueOf(i);
        this.key = placeHolder;
    }

    public Placeholder(String placeHolder, float i) {
        this.value = String.valueOf(i);
        this.key = placeHolder;
    }

    public String getKey() {
        return this.key;
    }

    public String getValue() {
        return this.value;
    }
}
