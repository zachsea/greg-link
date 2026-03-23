package cafe.zach.discord.api.config.channels.discord.filters;

import com.google.gson.JsonObject;

public class DiscordConfigFiltersEntry {

    public final boolean ignoreBots;
    public static final String IGNORE_BOTS_KEY = "ignoreBots";

    public DiscordConfigFiltersEntry(boolean ignoreBots) {
        this.ignoreBots = ignoreBots;
    }

    public static DiscordConfigFiltersEntry fromJson(JsonObject json) {
        return new DiscordConfigFiltersEntry(
            !json.has(IGNORE_BOTS_KEY) || json.get(IGNORE_BOTS_KEY)
                .getAsBoolean());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(IGNORE_BOTS_KEY, ignoreBots);
        return json;
    }
}
