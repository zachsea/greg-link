package cafe.zach.discord.api.action.common.discord;

import cafe.zach.discord.api.action.ChatSender;
import cafe.zach.discord.api.action.IDiscordAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.discord.api.config.channels.ChannelConfigEntry;
import cafe.zach.discord.api.config.channels.discord.filters.DiscordConfigFiltersEntry;

public class BroadcastMessageToChat {

    public static IDiscordAction create(ChatSender sender) {
        return context -> {
            for (ChannelConfigEntry mapping : ConfigHandler.getInstance().channels) {
                if (!mapping.discord.channelIds.contains(context.channelId)) continue;

                DiscordConfigFiltersEntry filters = mapping.discord.filters;
                if (filters.ignoreBots && context.isBot) continue;

                sender.send(MessageFormatter.formatDiscordMessage(context));
            }
        };
    }
}
