package cafe.zach.discord.api.action.context;

public class MinecraftContext {

    public final String username;
    public final String content;
    public final String worldName;
    public final String dimensionId;

    private MinecraftContext(Builder builder) {
        this.username = builder.username;
        this.content = builder.content;
        this.worldName = builder.worldName;
        this.dimensionId = builder.dimensionId;
    }

    public static MinecraftContext forChat(String username, String content, String worldName, String dimensionId) {
        return new Builder().username(username)
            .content(content)
            .worldName(worldName)
            .dimensionId(dimensionId)
            .build();
    }

    public static MinecraftContext forPlayerJoin(String username) {
        return new Builder().username(username)
            .build();
    }

    public static MinecraftContext forPlayerLeave(String username) {
        return new Builder().username(username)
            .build();
    }

    private static class Builder {

        private String username;
        private String content;
        private String worldName;
        private String dimensionId;

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

        private Builder dimensionId(String dimensionId) {
            this.dimensionId = dimensionId;
            return this;
        }

        private MinecraftContext build() {
            return new MinecraftContext(this);
        }
    }
}
