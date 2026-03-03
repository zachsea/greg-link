package cafe.zach.discord.api.action.context;

public class MinecraftChatContext {

    public final String username;
    public final String content;
    public final String worldName;
    public final String server;

    public MinecraftChatContext(String username, String content, String worldName, String server) {
        this.username = username;
        this.content = content;
        this.worldName = worldName;
        this.server = server;
    }
}
