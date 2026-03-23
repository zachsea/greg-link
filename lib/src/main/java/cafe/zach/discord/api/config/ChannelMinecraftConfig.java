package cafe.zach.discord.api.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ChannelMinecraftConfig {

    public final List<String> dimensions;
    public static final String WILDCARD = "*";
    public static final String DIMENSIONS_KEY = "dimensions";

    public ChannelMinecraftConfig(List<String> dimensions) {
        this.dimensions = Collections.unmodifiableList(dimensions);
    }

    public boolean matchesDimension(String dimension) {
        return dimensions.contains(WILDCARD) || dimensions.contains(dimension) || dimension.equals(WILDCARD);
    }

    public static ChannelMinecraftConfig fromJson(JsonObject json) {
        List<String> dimensions = new ArrayList<>();
        if (json.has(DIMENSIONS_KEY)) {
            for (JsonElement el : json.getAsJsonArray(DIMENSIONS_KEY)) dimensions.add(el.getAsString());
        } else {
            dimensions.add(WILDCARD);
        }

        return new ChannelMinecraftConfig(dimensions);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonArray dims = new JsonArray();
        for (String d : dimensions) dims.add(new JsonPrimitive(d));
        json.add(DIMENSIONS_KEY, dims);

        return json;
    }
}
