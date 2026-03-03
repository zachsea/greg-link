package cafe.zach.discord.api.action;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.config.DiscordServiceConfig;

public class CommonActions {

    public interface ChatSender {

        void send(String message);
    }

    public interface DiscordSender {

        void send(String channelId, String message);
    }

    public static IDiscordAction filtered(DiscordServiceConfig config, IDiscordAction action) {
        return context -> {
            if (config.listenChannels.contains(context.channelId)) {
                action.execute(context);
            }
        };
    }

    public static IDiscordAction broadcastToChat(DiscordServiceConfig config, ChatSender sender) {
        return filtered(
            config,
            context -> sender.send(String.format("[Discord] %s: %s", context.username, context.content)));
    }

    public static IDiscordAction broadcastToChat(DiscordServiceConfig config, ChatSender sender, String prefix) {
        return filtered(
            config,
            context -> sender.send(String.format("[%s] %s: %s", prefix, context.username, context.content)));
    }

    public static IMinecraftAction relayToDiscord(DiscordServiceConfig config) {
        return context -> DiscordBridge.sendMessage(
            config.sendChannel,
            String.format("**%s** (%s): %s", context.username, context.worldName, context.content));
    }
}
