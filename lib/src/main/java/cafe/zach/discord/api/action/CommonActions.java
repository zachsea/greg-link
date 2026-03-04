package cafe.zach.discord.api.action;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.config.ConfigHandler;

public class CommonActions {

    public interface ChatSender {

        void send(String message);
    }

    public interface DiscordSender {

        void send(String channelId, String message);
    }

    public static IDiscordAction filtered(IDiscordAction action) {
        return context -> {
            if (ConfigHandler.getInstance().listenChannels.contains(context.channelId)) {
                action.execute(context);
            }
        };
    }

    public static IDiscordAction broadcastToChat(ChatSender sender) {
        return filtered(context -> sender.send(String.format("[Discord] %s: %s", context.username, context.content)));
    }

    public static IMinecraftAction relayToDiscord() {
        return context -> DiscordBridge.sendMessage(
            ConfigHandler.getInstance().sendChannel,
            String.format("**%s** (%s): %s", context.username, context.worldName, context.content));
    }
}
