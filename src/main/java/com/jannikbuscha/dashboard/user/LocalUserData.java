package com.jannikbuscha.dashboard.user;

import com.jannikbuscha.dashboard.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class LocalUserData {

    private static final String TEMP_PATH = System.getProperty("java.io.tmpdir");

    private static final File FOLDER = new File(TEMP_PATH + "/" + Main.getInstance().getName()),
            PROPERTIES_FILE = new File(FOLDER, "data.properties");

    private static Properties properties = null;

    public static boolean existsFolder() {
        return FOLDER.exists();
    }

    public static Optional<String> getProperty(String key) {
        initProperties();

        if (!properties.containsKey(key)) {
            return Optional.empty();
        }

        return Optional.of(properties.getProperty(key));
    }

    public static void setProperty(String key, String value) {
        initProperties();

        properties.setProperty(key, value);

        try {
            properties.store(new FileOutputStream(PROPERTIES_FILE), "Do not change anything here!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void initProperties() {
        if (properties != null) return;

        properties = new Properties();

        if (!existsFolder()) {
            FOLDER.mkdir();
        }

        if (!PROPERTIES_FILE.exists()) {
            try {
                properties.store(new FileOutputStream(PROPERTIES_FILE), "Do not change anything here!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            properties.load(new FileInputStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}