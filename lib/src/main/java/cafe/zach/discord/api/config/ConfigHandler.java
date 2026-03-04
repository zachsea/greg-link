package cafe.zach.discord.api.config;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

public abstract class ConfigHandler {

    private static ConfigHandler INSTANCE;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
        .create();
    private static final JsonParser PARSER = new JsonParser();

    public String discordToken = "";
    public String serverName = "Minecraft";
    public String sendChannel = "";
    public List<String> listenChannels = new ArrayList<>();

    public static void setInstance(ConfigHandler instance) {
        INSTANCE = instance;
    }

    public static ConfigHandler getInstance() {
        return INSTANCE;
    }

    protected abstract File getConfigFile();

    // overridable for custom fields beyond base library ones
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

            if (!element.isJsonObject()) {
                return;
            }

            JsonObject json = element.getAsJsonObject();

            if (json.has("discordToken")) discordToken = json.get("discordToken")
                .getAsString();
            if (json.has("serverName")) serverName = json.get("serverName")
                .getAsString();
            if (json.has("sendChannel")) sendChannel = json.get("sendChannel")
                .getAsString();
            if (json.has("listenChannels")) {
                listenChannels = new ArrayList<>();
                for (JsonElement ch : json.getAsJsonArray("listenChannels")) listenChannels.add(ch.getAsString());
            }

            onLoad(json);

        } catch (Exception e) {
            // lib has no logger, let subclass handle if needed
        }
    }

    public final void save() {
        File file = getConfigFile();
        try {
            file.getParentFile()
                .mkdirs();

            JsonObject json = new JsonObject();
            json.addProperty("token", discordToken);
            json.addProperty("serverName", serverName);
            json.addProperty("sendChannel", sendChannel);

            JsonArray channels = new JsonArray();
            for (String ch : listenChannels) channels.add(new JsonPrimitive(ch));
            json.add("listenChannels", channels);

            onSave(json);

            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            writer.write(GSON.toJson(json));
            writer.close();

        } catch (Exception e) {
            // let subclass handle if needed
        }
    }
}
