package de.fanta.playtime;

import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    public static long playtime = 0;

    static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve("playtime.properties");

    static void serialize() {
        Properties prop = new Properties();
        prop.setProperty("playtime", String.valueOf(playtime));
        try {
            OutputStream s = Files.newOutputStream(configPath);
            prop.store(s, "PlayTime Config");
            s.close();
        } catch (IOException e) {
            PlaytimeClient.LOGGER.warn("Failed to write config!");
        }
    }

    public static void deserialize() {
        Properties prop = new Properties();
        try {
            InputStream s = Files.newInputStream(configPath);
            prop.load(s);
            playtime = Integer.parseInt(prop.getProperty("playtime", String.valueOf(playtime)));
        } catch (IOException e) {
            PlaytimeClient.LOGGER.warn("Failed to read config!");
        }
        Config.serialize();
    }
}
