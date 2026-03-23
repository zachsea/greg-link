package cafe.zach.discord.api.config.bot;

import com.google.gson.JsonObject;

public class BotConfigEntry {

    public static final String BOT_TOKEN_KEY = "token";
    public String botToken = "";

    public BotConfigEntry(String botToken) {
        this.botToken = botToken;
    }

    public static BotConfigEntry fromJson(JsonObject json) {
        return new BotConfigEntry(
            json.has(BOT_TOKEN_KEY) ? json.get(BOT_TOKEN_KEY)
                .getAsString() : "unnamed");
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(BOT_TOKEN_KEY, botToken);
        return json;
    }
}
