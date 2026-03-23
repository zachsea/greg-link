package cafe.zach.discord.api.config.channels.discord;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import cafe.zach.discord.api.config.channels.discord.filters.DiscordConfigFiltersEntry;

public class DiscordConfigEntry {

    public final List<String> channelIds;
    public final boolean eventsUseEmbeds;
    public final boolean chatsUseEmbeds;
    public final DiscordConfigFiltersEntry filters;
    public static final String CHANNEL_IDS_KEY = "channelIDs";
    public static final String EVENTS_USE_EMBEDS_KEY = "useEmbedsForEvents";
    public static final String CHATS_USE_EMBEDS_KEY = "useEmbedsForChats";
    public static final String FILTERS_KEY = "filters";

    public DiscordConfigEntry(List<String> channelIds, boolean eventsUseEmbeds, boolean chatsUseEmbeds,
        DiscordConfigFiltersEntry filters) {
        this.channelIds = channelIds;
        this.eventsUseEmbeds = eventsUseEmbeds;
        this.chatsUseEmbeds = chatsUseEmbeds;
        this.filters = filters;
    }

    public static DiscordConfigEntry fromJson(JsonObject json) {
        List<String> channelIds = new ArrayList<>();
        if (json.has(CHANNEL_IDS_KEY)) {
            for (JsonElement el : json.get(CHANNEL_IDS_KEY)
                .getAsJsonArray()) channelIds.add(el.getAsString());
        }
        return new DiscordConfigEntry(
            channelIds,
            json.has(EVENTS_USE_EMBEDS_KEY) && json.get(EVENTS_USE_EMBEDS_KEY)
                .getAsBoolean(),
            json.has(CHATS_USE_EMBEDS_KEY) && json.get(CHATS_USE_EMBEDS_KEY)
                .getAsBoolean(),
            json.has(FILTERS_KEY) ? DiscordConfigFiltersEntry.fromJson(json.getAsJsonObject(FILTERS_KEY))
                : new DiscordConfigFiltersEntry(true));
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonArray channelIdArray = new JsonArray();

        for (String id : channelIds) channelIdArray.add(new JsonPrimitive(id));

        json.add(CHANNEL_IDS_KEY, channelIdArray);
        json.addProperty(EVENTS_USE_EMBEDS_KEY, eventsUseEmbeds);
        json.addProperty(CHATS_USE_EMBEDS_KEY, chatsUseEmbeds);
        json.add(FILTERS_KEY, filters.toJson());

        return json;
    }
}
