package cafe.zach.discord.api.config.channels;

import java.util.Collections;

import cafe.zach.discord.api.config.ChannelFilters;
import com.google.gson.JsonObject;

public class ChannelConfigEntry {

    public final String displayName;
    public final ChannelDiscordConfig discord;
    public final ChannelMinecraftConfig minecraft;
    public final ChannelFilters filters;
    public static final String DISPLAY_NAME_KEY = "display_name";
    public static final String DISCORD_KEY = "discord";
    public static final String MINECRAFT_KEY = "minecraft";
    public static final String FILTERS_KEY = "filters";

    public ChannelConfigEntry(String displayName, ChannelDiscordConfig discord, ChannelMinecraftConfig minecraft,
                              ChannelFilters filters) {
        this.displayName = displayName;
        this.discord = discord;
        this.minecraft = minecraft;
        this.filters = filters;
    }

    public boolean matchesDimension(String dimension) {
        return minecraft.matchesDimension(dimension);
    }

    public static ChannelConfigEntry fromJson(JsonObject json) {
        return new ChannelConfigEntry(
            json.has(DISPLAY_NAME_KEY) ? json.get(DISPLAY_NAME_KEY)
                .getAsString() : "unnamed",
            json.has(DISCORD_KEY) ? ChannelDiscordConfig.fromJson(json.getAsJsonObject(DISCORD_KEY))
                : new ChannelDiscordConfig(Collections.emptyList(), true, false),
            json.has(MINECRAFT_KEY) ? ChannelMinecraftConfig.fromJson(json.getAsJsonObject(MINECRAFT_KEY))
                : new ChannelMinecraftConfig(java.util.Collections.singletonList(ChannelMinecraftConfig.WILDCARD)),
            json.has(FILTERS_KEY) ? ChannelFilters.fromJson(json.getAsJsonObject(FILTERS_KEY))
                : new ChannelFilters(true));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(DISPLAY_NAME_KEY, displayName);
        json.add(DISCORD_KEY, discord.toJson());
        json.add(MINECRAFT_KEY, minecraft.toJson());
        json.add(FILTERS_KEY, filters.toJson());
        return json;
    }
}
