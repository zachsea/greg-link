package cafe.zach.discord.api.action.registry;

import java.util.*;

import cafe.zach.discord.api.action.IDiscordAction;
import cafe.zach.discord.api.action.IMinecraftAction;

public class ActionRegistry {

    // Built-in event keys
    public static final String ON_DISCORD_MESSAGE = "on_discord_message";
    public static final String ON_DISCORD_MEMBER_JOIN = "on_discord_member_join";
    public static final String ON_DISCORD_MEMBER_LEAVE = "on_discord_member_leave";

    public static final String ON_MINECRAFT_CHAT = "on_minecraft_chat";
    public static final String ON_MINECRAFT_PLAYER_JOIN = "on_minecraft_player_join";
    public static final String ON_MINECRAFT_PLAYER_LEAVE = "on_minecraft_player_leave";

    private static final Map<String, List<IDiscordAction>> discordActions = new LinkedHashMap<>();
    private static final Map<String, List<IMinecraftAction>> minecraftActions = new LinkedHashMap<>();

    // Discord -> Minecraft
    public static void register(String event, IDiscordAction action) {
        discordActions.computeIfAbsent(event, k -> new ArrayList<>())
            .add(action);
    }

    public static List<IDiscordAction> getDiscordActions(String event) {
        return discordActions.getOrDefault(event, Collections.emptyList());
    }

    // Minecraft -> Discord
    public static void register(String event, IMinecraftAction action) {
        minecraftActions.computeIfAbsent(event, k -> new ArrayList<>())
            .add(action);
    }

    public static List<IMinecraftAction> getMinecraftActions(String event) {
        return minecraftActions.getOrDefault(event, Collections.emptyList());
    }

    public static void clear() {
        discordActions.clear();
        minecraftActions.clear();
    }
}
