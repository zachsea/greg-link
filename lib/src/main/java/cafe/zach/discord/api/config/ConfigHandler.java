package cafe.zach.discord.api.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import cafe.zach.discord.api.config.bot.BotConfigEntry;
import cafe.zach.discord.api.config.channels.ChannelConfigEntry;
import cafe.zach.discord.api.config.channels.ChannelDiscordConfig;
import cafe.zach.discord.api.config.channels.ChannelMinecraftConfig;

public abstract class ConfigHandler {

    private static ConfigHandler INSTANCE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
        .create();
    private static final JsonParser PARSER = new JsonParser();

    // bot
    public static final String BOT_KEY = "bot";
    // defaults
    public BotConfigEntry bot = new BotConfigEntry("");

    // channels
    public static final String CHANNELS_KEY = "channels";
    // defaults
    public List<ChannelConfigEntry> channels = new ArrayList<>(
        Collections.singletonList(
            new ChannelConfigEntry(
                "Example",
                new ChannelDiscordConfig(Collections.singletonList("0000000000000000"), true, false),
                new ChannelMinecraftConfig(Collections.singletonList("*")),
                new ChannelFilters(true))));

    public static void setInstance(ConfigHandler instance) {
        INSTANCE = instance;
    }

    public static ConfigHandler getInstance() {
        return INSTANCE;
    }

    protected abstract File getConfigFile();

    protected void onLoad(JsonObject json) {}

    protected void onSave(JsonObject json) {}

    public final void load() {
        File file = getConfigFile();
        if (!Files.exists(file.toPath())) {
            save();
            return;
        }

        try {
            Reader reader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            JsonElement element = PARSER.parse(new JsonReader(reader));
            reader.close();

            if (!element.isJsonObject()) return;
            JsonObject root = element.getAsJsonObject();

            if (root.has(BOT_KEY)) {
                bot = BotConfigEntry.fromJson(root.getAsJsonObject(BOT_KEY));
            }

            if (root.has(CHANNELS_KEY)) {
                channels = new ArrayList<>();
                for (JsonElement el : root.getAsJsonArray(CHANNELS_KEY))
                    channels.add(ChannelConfigEntry.fromJson(el.getAsJsonObject()));
            }

            onLoad(root);

        } catch (Exception e) {
            // lib has no logger, let subclass handle if needed
        }
    }

    public final void save() {
        File file = getConfigFile();
        try {
            File parent = file.getParentFile();
            if (!parent.exists() && !parent.mkdirs()) {
                throw new IOException("Unable to create directory");
            }

            JsonObject root = new JsonObject();

            root.add(BOT_KEY, bot.toJson());

            JsonArray channelArray = new JsonArray();
            for (ChannelConfigEntry mapping : channels) channelArray.add(mapping.toJson());
            root.add(CHANNELS_KEY, channelArray);

            onSave(root);

            Writer writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8);
            writer.write(GSON.toJson(root));
            writer.close();

        } catch (Exception e) {
            // let subclass handle if needed
        }
    }

    public List<ChannelConfigEntry> getChannelsForDimension(String dimension) {
        return channels.stream()
            .filter(m -> m.matchesDimension(dimension))
            .collect(Collectors.toList());
    }

    public String getDiscordToken() {
        return bot.botToken;
    }
}
