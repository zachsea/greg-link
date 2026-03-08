package cafe.zach.discord.api.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ChannelMinecraftConfig {

    public static final String WILDCARD = "*";

    public final List<String> dimensions;

    public ChannelMinecraftConfig(List<String> dimensions) {
        this.dimensions = Collections.unmodifiableList(dimensions);
    }

    public boolean matchesDimension(String dimension) {
        return dimensions.contains(WILDCARD) || dimensions.contains(dimension) || dimension.equals(WILDCARD);
    }

    public static ChannelMinecraftConfig fromJson(JsonObject json) {
        List<String> dimensions = new ArrayList<>();
        if (json.has("dimensions")) {
            for (JsonElement el : json.getAsJsonArray("dimensions")) dimensions.add(el.getAsString());
        } else {
            dimensions.add(WILDCARD);
        }

        return new ChannelMinecraftConfig(dimensions);
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonArray dims = new JsonArray();
        for (String d : dimensions) dims.add(new JsonPrimitive(d));
        json.add("dimensions", dims);

        return json;
    }
}
