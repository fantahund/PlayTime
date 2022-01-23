package de.fanta.playtime;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Environment(EnvType.CLIENT)
public class PlaytimeClient implements ClientModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("PlayTime");

    private static long playtime;
    private long countingSinceTimestamp;

    @Override
    public void onInitializeClient() {
        Config.deserialize();
        playtime = Config.playtime;

        startTimer();
        startSaveTimer();
    }

    private void startTimer() {
        countingSinceTimestamp = System.currentTimeMillis();
        Thread timer = new Thread(() -> {
            try {
                while (true) {
                    playtime += (System.currentTimeMillis() - countingSinceTimestamp);
                    countingSinceTimestamp = System.currentTimeMillis();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        timer.start();
    }

    private void startSaveTimer() {
        Thread saveTimer = new Thread(() -> {
            try {
                while (true) {
                    Config.playtime = playtime;
                    Config.serialize();
                    Thread.sleep(60 * 1000); // 1 minutes
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        saveTimer.start();
    }

    public static long getPlaytime() {
        return playtime;
    }
}
