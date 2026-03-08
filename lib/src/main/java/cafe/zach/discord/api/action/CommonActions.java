package cafe.zach.discord.api.action;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.config.ChannelFilters;
import cafe.zach.discord.api.config.ChannelMapping;
import cafe.zach.discord.api.config.ChannelMinecraftConfig;
import cafe.zach.discord.api.config.ConfigHandler;

public class CommonActions {

    public interface ChatSender {

        void send(String message);
    }

    public static IDiscordAction broadcastReadyToChat(ChatSender sender) {
        return context -> {
            sender.send(
                String.format(
                    "[Discord] Ready on %s %s!",
                    context.guildCount,
                    context.guildCount == 1 ? "guild" : "guilds"));
        };
    }

    public static IDiscordAction broadcastMessageToChat(ChatSender sender) {
        return context -> {
            ConfigHandler config = ConfigHandler.getInstance();

            for (ChannelMapping mapping : config.getListenChannels()) {
                if (!mapping.discordChannelId.equals(context.channelId)) continue;

                ChannelFilters filters = mapping.filters;
                if (filters.ignoreBots && context.isBot) continue;

                sender.send(String.format("[Discord] %s: %s", context.username, context.content));
            }
        };
    }

    public static IMinecraftAction relayChatToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(context.dimensionId);

            for (ChannelMapping mapping : mappings) {
                DiscordBridge.sendMessage(
                    mapping.discordChannelId,
                    String.format("**%s**: %s", context.username, context.content));
            }
        };
    }

    public static IMinecraftAction relayJoinToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelMapping mapping : mappings) {
                DiscordBridge.sendMessage(mapping.discordChannelId, String.format("**%s** joined", context.username));
            }
        };
    }

    public static IMinecraftAction relayLeaveToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelMapping mapping : mappings) {
                DiscordBridge.sendMessage(mapping.discordChannelId, String.format("**%s** left", context.username));
            }
        };
    }
}
