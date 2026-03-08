package cafe.zach.discord;

import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;

public class DiscordBridge {

    private static DiscordService service;

    public static void createConnection() throws InvalidDiscordConfigurationException, InterruptedException {
        service = new DiscordService(ConfigHandler.getInstance());
    }

    public static void restart() throws InvalidDiscordConfigurationException, InterruptedException {
        if (service == null) {
            createConnection();
            return;
        }
        service.restart(ConfigHandler.getInstance());
    }

    public static void shutdown() {
        if (service != null) {
            service.destroy();
            service = null;
        }
    }

    public static void sendMessage(String channelId, String message) {
        if (service == null || !service.isRunning()) return;
        service.sendMessage(channelId, message);
    }

    public static boolean isConnected() {
        return service != null && service.isRunning();
    }
}
