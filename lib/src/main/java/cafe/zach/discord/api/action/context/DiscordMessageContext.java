package cafe.zach.discord.api.action.context;

public class DiscordMessageContext {

    public final String username;
    public final String content;
    public final String channelId;
    public final String guildId;

    public DiscordMessageContext(String username, String content, String channelId, String guildId) {
        this.username = username;
        this.content = content;
        this.channelId = channelId;
        this.guildId = guildId;
    }
}
