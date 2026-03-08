package cafe.zach.discord.api.config;

import com.google.gson.JsonObject;

public class ChannelFilters {

    public final boolean ignoreBots;

    public ChannelFilters(boolean ignoreBots) {
        this.ignoreBots = ignoreBots;
    }

    public static ChannelFilters fromJson(JsonObject json) {
        return new ChannelFilters(
            !json.has("ignoreBots") || json.get("ignoreBots")
                .getAsBoolean());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("ignoreBots", ignoreBots);
        return json;
    }
}
