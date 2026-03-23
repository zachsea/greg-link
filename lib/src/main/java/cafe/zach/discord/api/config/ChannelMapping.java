package cafe.zach.discord.api.config;

import java.util.Collections;

import com.google.gson.JsonObject;

public class ChannelMapping {

    public final String id;
    public final ChannelDirections directions;
    public final ChannelDiscordConfig discord;
    public final ChannelMinecraftConfig minecraft;
    public final ChannelFilters filters;

    public ChannelMapping(String id, ChannelDirections directions, ChannelDiscordConfig discord,
        ChannelMinecraftConfig minecraft, ChannelFilters filters) {
        this.id = id;
        this.directions = directions;
        this.discord = discord;
        this.minecraft = minecraft;
        this.filters = filters;
    }

    public boolean acceptsFromDiscord() {
        return directions.discordToMinecraft;
    }

    public boolean acceptsFromMinecraft() {
        return directions.minecraftToDiscord;
    }

    public boolean matchesDimension(String dimension) {
        return minecraft.matchesDimension(dimension);
    }

    public static ChannelMapping fromJson(JsonObject json) {
        return new ChannelMapping(
            json.has("id") ? json.get("id")
                .getAsString() : "unnamed",
            json.has("directions") ? ChannelDirections.fromJson(json.getAsJsonObject("directions"))
                : new ChannelDirections(true, true),
            json.has("discord") ? ChannelDiscordConfig.fromJson(json.getAsJsonObject("discord"))
                : new ChannelDiscordConfig(Collections.emptyList(), true, false),
            json.has("minecraft") ? ChannelMinecraftConfig.fromJson(json.getAsJsonObject("minecraft"))
                : new ChannelMinecraftConfig(java.util.Collections.singletonList("*")),
            json.has("filters") ? ChannelFilters.fromJson(json.getAsJsonObject("filters")) : new ChannelFilters(true));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("id", id);
        json.add("directions", directions.toJson());
        json.add("discord", discord.toJson());
        json.add("minecraft", minecraft.toJson());
        json.add("filters", filters.toJson());
        return json;
    }
}
