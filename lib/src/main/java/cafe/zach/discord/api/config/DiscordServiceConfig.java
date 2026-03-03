package cafe.zach.discord.api.config;

import java.util.Collections;
import java.util.List;

public class DiscordServiceConfig {

    public final String token;
    public final String serverName;
    public final String sendChannel;
    public final List<String> listenChannels;

    public DiscordServiceConfig(String token, String serverName, String sendChannel, List<String> listenChannels) {
        this.token = token;
        this.serverName = serverName;
        this.sendChannel = sendChannel;
        this.listenChannels = Collections.unmodifiableList(listenChannels);
    }
}
