package cafe.zach.discord.api.config.channels;

import java.util.Collections;

import com.google.gson.JsonObject;

import cafe.zach.discord.api.config.channels.discord.DiscordConfigEntry;
import cafe.zach.discord.api.config.channels.discord.filters.DiscordConfigFiltersEntry;
import cafe.zach.discord.api.config.channels.minecraft.MinecraftConfigEntry;
import cafe.zach.discord.api.config.channels.minecraft.filters.MinecraftConfigFiltersEntry;

public class ChannelConfigEntry {

    public final String displayName;
    public final DiscordConfigEntry discord;
    public final MinecraftConfigEntry minecraft;
    public static final String DISPLAY_NAME_KEY = "display_name";
    public static final String DISCORD_KEY = "discord";
    public static final String MINECRAFT_KEY = "minecraft";

    public ChannelConfigEntry(String displayName, DiscordConfigEntry discord, MinecraftConfigEntry minecraft) {
        this.displayName = displayName;
        this.discord = discord;
        this.minecraft = minecraft;
    }

    public boolean matchesDimension(String dimension) {
        return minecraft.filters.matchesDimension(dimension);
    }

    public static ChannelConfigEntry fromJson(JsonObject json) {
        return new ChannelConfigEntry(
            json.has(DISPLAY_NAME_KEY) ? json.get(DISPLAY_NAME_KEY)
                .getAsString() : "unnamed",
            json.has(DISCORD_KEY) ? DiscordConfigEntry.fromJson(json.getAsJsonObject(DISCORD_KEY))
                : new DiscordConfigEntry(Collections.emptyList(), true, false, new DiscordConfigFiltersEntry(false)),
            json.has(MINECRAFT_KEY) ? MinecraftConfigEntry.fromJson(json.getAsJsonObject(MINECRAFT_KEY))
                : new MinecraftConfigEntry(
                    new MinecraftConfigFiltersEntry(Collections.singletonList(MinecraftConfigFiltersEntry.WILDCARD))));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        json.addProperty(DISPLAY_NAME_KEY, displayName);
        json.add(DISCORD_KEY, discord.toJson());
        json.add(MINECRAFT_KEY, minecraft.toJson());
        return json;
    }
}
