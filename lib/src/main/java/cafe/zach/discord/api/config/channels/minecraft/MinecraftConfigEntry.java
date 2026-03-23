package cafe.zach.discord.api.config.channels.minecraft;

import java.util.Collections;

import com.google.gson.JsonObject;

import cafe.zach.discord.api.config.channels.minecraft.filters.MinecraftConfigFiltersEntry;

public class MinecraftConfigEntry {

    public final MinecraftConfigFiltersEntry filters;
    public static final String FILTERS_KEY = "filters";

    public MinecraftConfigEntry(MinecraftConfigFiltersEntry filters) {
        this.filters = filters;
    }

    public static MinecraftConfigEntry fromJson(JsonObject json) {
        return new MinecraftConfigEntry(
            json.has(FILTERS_KEY) ? MinecraftConfigFiltersEntry.fromJson(json.getAsJsonObject(FILTERS_KEY))
                : new MinecraftConfigFiltersEntry(Collections.singletonList(MinecraftConfigFiltersEntry.WILDCARD)));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.add(FILTERS_KEY, filters.toJson());

        return json;
    }
}
