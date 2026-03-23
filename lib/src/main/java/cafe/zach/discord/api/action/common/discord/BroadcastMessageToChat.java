package cafe.zach.discord.api.action.common.discord;

import cafe.zach.discord.api.action.ChatSender;
import cafe.zach.discord.api.action.IDiscordAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.ChannelFilters;
import cafe.zach.discord.api.config.ChannelMapping;
import cafe.zach.discord.api.config.ConfigHandler;

public class BroadcastMessageToChat {

    public static IDiscordAction create(ChatSender sender) {
        return context -> {
            for (ChannelMapping mapping : ConfigHandler.getInstance().channels) {
                if (!mapping.discord.channelIds.contains(context.channelId)) continue;

                ChannelFilters filters = mapping.filters;
                if (filters.ignoreBots && context.isBot) continue;

                sender.send(MessageFormatter.formatDiscordMessage(context));
            }
        };
    }
}
