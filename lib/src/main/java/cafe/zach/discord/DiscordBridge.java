package cafe.zach.discord;

import cafe.zach.discord.api.config.DiscordServiceConfig;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;

public class DiscordBridge {

    static DiscordService service;
    public static DiscordServiceConfig config;

    public static void createConnection(DiscordServiceConfig config)
        throws InvalidDiscordConfigurationException, InterruptedException {
        DiscordBridge.config = config;
        service = new DiscordService(config);
    }

    public static void sendMessage(String channelId, String message) {
        if (service == null) return;
        service.sendMessage(channelId, message);
    }
}
