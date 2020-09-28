package com.example.emu.jni;

public class PropertiesGet {

    static {
        System.loadLibrary("property_get");
    }

    private static native String native_get(String key);

    private static native String native_get(String key, String def);

    public static String getString(String key) {
        return native_get(key);
    }

    public static String getString(String key, String def) {
        return native_get(key, def);
    }

}
