package cafe.zach.discord.api.action.context;

public class MinecraftContext {

    public final String username;
    public final String content;
    public final String worldName;
    public final String serverName;

    private MinecraftContext(Builder builder) {
        this.username = builder.username;
        this.content = builder.content;
        this.worldName = builder.worldName;
        this.serverName = builder.serverName;
    }

    public static MinecraftContext forChat(String username, String content, String worldName, String serverName) {
        return new Builder().username(username)
            .content(content)
            .worldName(worldName)
            .serverName(serverName)
            .build();
    }

    public static MinecraftContext forPlayerJoin(String username, String content) {
        return new Builder().username(username)
            .content(content)
            .build();
    }

    private static class Builder {

        private String username;
        private String content;
        private String worldName;
        private String serverName;

        private Builder username(String username) {
            this.username = username;
            return this;
        }

        private Builder content(String content) {
            this.content = content;
            return this;
        }

        private Builder worldName(String worldName) {
            this.worldName = worldName;
            return this;
        }

        private Builder serverName(String serverName) {
            this.serverName = serverName;
            return this;
        }

        private MinecraftContext build() {
            return new MinecraftContext(this);
        }
    }
}
