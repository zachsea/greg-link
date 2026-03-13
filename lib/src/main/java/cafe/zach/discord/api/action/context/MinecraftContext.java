package cafe.zach.discord.api.action.context;

public class MinecraftContext {

    public final String username;
    public final String uuid;
    public final String content;
    public final String worldName;
    public final String dimensionId;

    private MinecraftContext(Builder builder) {
        this.username = builder.username;
        this.uuid = builder.uuid;
        this.content = builder.content;
        this.worldName = builder.worldName;
        this.dimensionId = builder.dimensionId;
    }

    public static MinecraftContext forChat(String username, String uuid, String content, String worldName,
        String dimensionId) {
        return new Builder().username(username)
            .uuid(uuid)
            .content(content)
            .worldName(worldName)
            .dimensionId(dimensionId)
            .build();
    }

    public static MinecraftContext forPlayerJoin(String username, String uuid) {
        return new Builder().username(username)
            .uuid(uuid)
            .build();
    }

    public static MinecraftContext forPlayerLeave(String username, String uuid) {
        return new Builder().username(username)
            .uuid(uuid)
            .build();
    }

    public static MinecraftContext forEmpty() {
        return new Builder().build();
    }

    private static class Builder {

        private String username;
        private String uuid;
        private String content;
        private String worldName;
        private String dimensionId;

        private Builder username(String username) {
            this.username = username;
            return this;
        }

        private Builder uuid(String uuid) {
            this.uuid = uuid;
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
