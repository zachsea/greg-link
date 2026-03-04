package cafe.zach.discord;

import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;

public class DiscordBridge {

    private static DiscordService service;

    public static void createConnection() throws InvalidDiscordConfigurationException, InterruptedException {
        ConfigHandler config = ConfigHandler.getInstance();
        service = new DiscordService(config);
    }

    public static void sendMessage(String channelId, String message) {
        if (service == null) return;
        service.sendMessage(channelId, message);
    }
}
