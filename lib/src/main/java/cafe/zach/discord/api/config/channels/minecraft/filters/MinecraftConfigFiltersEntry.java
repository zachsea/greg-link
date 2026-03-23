package cafe.zach.discord.api.config.channels.minecraft.filters;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class MinecraftConfigFiltersEntry {

    public final List<String> dimensions;
    public static final String WILDCARD = "*";
    public static final String DIMENSIONS_KEY = "dimensions";

    public MinecraftConfigFiltersEntry(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public static MinecraftConfigFiltersEntry fromJson(JsonObject json) {
        List<String> dimensions = new ArrayList<>();
        if (json.has(DIMENSIONS_KEY)) {
            for (JsonElement el : json.getAsJsonArray(DIMENSIONS_KEY)) dimensions.add(el.getAsString());
        } else {
            dimensions.add(WILDCARD);
        }
        return new MinecraftConfigFiltersEntry(dimensions);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonArray dims = new JsonArray();
        for (String d : dimensions) dims.add(new JsonPrimitive(d));
        json.add(DIMENSIONS_KEY, dims);

        return json;
    }

    public boolean matchesDimension(String dimension) {
        return dimensions.contains(WILDCARD) || dimensions.contains(dimension) || dimension.equals(WILDCARD);
    }
}
