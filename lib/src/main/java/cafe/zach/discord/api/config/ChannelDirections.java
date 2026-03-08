package cafe.zach.discord.api.config;

import com.google.gson.JsonObject;

public class ChannelDirections {

    public final boolean discordToMinecraft;
    public final boolean minecraftToDiscord;

    public ChannelDirections(boolean discordToMinecraft, boolean minecraftToDiscord) {
        this.discordToMinecraft = discordToMinecraft;
        this.minecraftToDiscord = minecraftToDiscord;
    }

    public static ChannelDirections fromJson(JsonObject json) {
        return new ChannelDirections(
            json.has("discordToMinecraft") && json.get("discordToMinecraft")
                .getAsBoolean(),
            json.has("minecraftToDiscord") && json.get("minecraftToDiscord")
                .getAsBoolean());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("discordToMinecraft", discordToMinecraft);
        json.addProperty("minecraftToDiscord", minecraftToDiscord);
        return json;
    }
}
