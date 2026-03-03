package cafe.zach.greglink.config;

import static cafe.zach.greglink.GregLink.LOG;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import cpw.mods.fml.common.Loader;

public class ConfigHandler {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting()
        .create();
    private static final JsonParser PARSER = new JsonParser();

    private static final File CONFIG_FILE = new File(
        Loader.instance()
            .getConfigDir(),
        "greglink" + File.separatorChar + "config.json");

    // Config values with defaults
    public static String discordToken = "";
    public static String serverName = "Minecraft";
    public static String sendChannel = "";
    public static List<String> listenChannels = new ArrayList<>();

    public static void load() {
        if (!Files.exists(CONFIG_FILE.toPath())) {
            LOG.warn("No config found, generating default config.");
            save();
            return;
        }

        try {
            Reader reader = new InputStreamReader(new FileInputStream(CONFIG_FILE), StandardCharsets.UTF_8);
            JsonElement element = PARSER.parse(new JsonReader(reader));
            reader.close();

            if (!element.isJsonObject()) {
                LOG.error("Config is malformed!");
                return;
            }

            JsonObject json = element.getAsJsonObject();

            if (json.has("discordToken")) {
                discordToken = json.get("discordToken")
                    .getAsString();
            }
            if (json.has("serverName")) serverName = json.get("serverName")
                .getAsString();
            if (json.has("sendChannel")) sendChannel = json.get("sendChannel")
                .getAsString();
            if (json.has("listenChannels")) {
                listenChannels = new ArrayList<>();
                for (JsonElement ch : json.getAsJsonArray("listenChannels")) listenChannels.add(ch.getAsString());
            }

        } catch (Exception e) {
            LOG.error("Failed to load config!", e);
        }
    }

    public static void save() {
        try {
            CONFIG_FILE.getParentFile()
                .mkdirs();

            JsonObject json = new JsonObject();
            json.addProperty("discordToken", discordToken);
            json.addProperty("serverName", serverName);
            json.addProperty("sendChannel", sendChannel);
            JsonArray listenArray = new JsonArray();
            for (String ch : listenChannels) listenArray.add(new JsonPrimitive(ch));
            json.add("listenChannels", listenArray);

            Writer writer = new OutputStreamWriter(new FileOutputStream(CONFIG_FILE), StandardCharsets.UTF_8);
            writer.write(GSON.toJson(json));
            writer.close();

        } catch (Exception e) {
            LOG.error("Failed to save config!", e);
        }
    }
}
