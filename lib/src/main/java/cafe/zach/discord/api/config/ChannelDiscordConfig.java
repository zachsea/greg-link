package cafe.zach.discord.api.config;

import com.google.gson.JsonObject;

public class ChannelDiscordConfig {

    public final boolean eventsUseEmbeds;
    public final boolean chatsUseEmbeds;
    public static final String EVENTS_USE_EMBEDS = "useEmbedsForEvents";
    public static final String CHATS_USE_EMBEDS = "useEmbedsForChats";

    public ChannelDiscordConfig(boolean eventsUseEmbeds, boolean chatsUseEmbeds) {
        this.eventsUseEmbeds = eventsUseEmbeds;
        this.chatsUseEmbeds = chatsUseEmbeds;
    }

    public static ChannelDiscordConfig fromJson(JsonObject json) {
        return new ChannelDiscordConfig(
            json.has(EVENTS_USE_EMBEDS) && json.get(EVENTS_USE_EMBEDS)
                .getAsBoolean(),
            json.has(CHATS_USE_EMBEDS) && json.get(CHATS_USE_EMBEDS)
                .getAsBoolean());
    }

    public JsonObject toJson() {
        JsonObject json = new JsonObject();

        json.addProperty(EVENTS_USE_EMBEDS, eventsUseEmbeds);
        json.addProperty(CHATS_USE_EMBEDS, chatsUseEmbeds);

        return json;
    }
}
