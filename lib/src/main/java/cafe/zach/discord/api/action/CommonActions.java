package cafe.zach.discord.api.action;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.ChannelFilters;
import cafe.zach.discord.api.config.ChannelMapping;
import cafe.zach.discord.api.config.ChannelMinecraftConfig;
import cafe.zach.discord.api.config.ConfigHandler;

public class CommonActions {

    public interface ChatSender {

        void send(String message);
    }

    public static IDiscordAction broadcastReadyToChat(ChatSender sender) {
        return context -> sender.send(MessageFormatter.formatDiscordReady(context));
    }

    public static IDiscordAction broadcastMessageToChat(ChatSender sender) {
        return context -> {
            for (ChannelMapping mapping : ConfigHandler.getInstance()
                .getListenChannels()) {
                if (!mapping.discordChannelId.equals(context.channelId)) continue;

                ChannelFilters filters = mapping.filters;
                if (filters.ignoreBots && context.isBot) continue;

                sender.send(MessageFormatter.formatDiscordMessage(context));
            }
        };
    }

    public static IMinecraftAction relayChatToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(context.dimensionId);

            for (ChannelMapping mapping : mappings) {
                if (mapping.discord.chatsUseEmbeds) {
                    DiscordBridge.sendEmbedWithAvatar(
                        mapping.discordChannelId,
                        MessageFormatter.embedChat(context),
                        MessageFormatter.fetchAvatar(context));
                } else {
                    DiscordBridge.sendMessage(mapping.discordChannelId, MessageFormatter.formatChat(context));
                }
            }
        };
    }

    public static IMinecraftAction relayJoinToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelMapping mapping : mappings) {
                if (mapping.discord.eventsUseEmbeds) {
                    DiscordBridge.sendEmbedWithAvatar(
                        mapping.discordChannelId,
                        MessageFormatter.embedJoin(context),
                        MessageFormatter.fetchAvatar(context));
                } else {
                    DiscordBridge.sendMessage(mapping.discordChannelId, MessageFormatter.formatJoin(context));
                }
            }
        };
    }

    public static IMinecraftAction relayLeaveToDiscord() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelMapping mapping : mappings) {
                if (mapping.discord.eventsUseEmbeds) {
                    DiscordBridge.sendEmbedWithAvatar(
                        mapping.discordChannelId,
                        MessageFormatter.embedLeave(context),
                        MessageFormatter.fetchAvatar(context));
                } else {
                    DiscordBridge.sendMessage(mapping.discordChannelId, MessageFormatter.formatLeave(context));
                }
            }
        };
    }
}
