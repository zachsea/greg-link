package cafe.zach.discord.api.config;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class ChannelDiscordConfig {

    public final List<String> channelIds;
    public final boolean eventsUseEmbeds;
    public final boolean chatsUseEmbeds;
    public static final String CHANNEL_IDS = "channelIDs";
    public static final String EVENTS_USE_EMBEDS = "useEmbedsForEvents";
    public static final String CHATS_USE_EMBEDS = "useEmbedsForChats";

    public ChannelDiscordConfig(List<String> channelIds, boolean eventsUseEmbeds, boolean chatsUseEmbeds) {
        this.channelIds = channelIds;
        this.eventsUseEmbeds = eventsUseEmbeds;
        this.chatsUseEmbeds = chatsUseEmbeds;
    }

    public static ChannelDiscordConfig fromJson(JsonObject json) {
        List<String> channelIds = new ArrayList<>();
        if (json.has(CHANNEL_IDS)) {
            for (JsonElement el : json.get(CHANNEL_IDS)
                .getAsJsonArray()) channelIds.add(el.getAsString());
        }
        return new ChannelDiscordConfig(
            channelIds,
            json.has(EVENTS_USE_EMBEDS) && json.get(EVENTS_USE_EMBEDS)
                .getAsBoolean(),
            json.has(CHATS_USE_EMBEDS) && json.get(CHATS_USE_EMBEDS)
                .getAsBoolean());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        JsonArray channelIdArray = new JsonArray();

        for (String id : channelIds) channelIdArray.add(new JsonPrimitive(id));

        json.add(CHANNEL_IDS, channelIdArray);
        json.addProperty(EVENTS_USE_EMBEDS, eventsUseEmbeds);
        json.addProperty(CHATS_USE_EMBEDS, chatsUseEmbeds);

        return json;
    }
}
