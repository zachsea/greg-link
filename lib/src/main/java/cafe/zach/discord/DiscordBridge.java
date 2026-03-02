package cafe.zach.discord;

import cafe.zach.discord.api.config.DiscordServiceConfig;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;

public class DiscordBridge {

    static DiscordService service;

    public static void createConnection(DiscordServiceConfig config)
        throws InvalidDiscordConfigurationException, InterruptedException {
        service = new DiscordService(config);
    }
}
