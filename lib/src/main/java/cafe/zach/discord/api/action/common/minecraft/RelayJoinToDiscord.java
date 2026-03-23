package cafe.zach.discord.api.action.common.minecraft;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.channels.ChannelConfigEntry;
import cafe.zach.discord.api.config.channels.ChannelMinecraftConfig;
import cafe.zach.discord.api.config.ConfigHandler;

public class RelayJoinToDiscord {

    public static IMinecraftAction create() {
        return context -> {
            List<ChannelConfigEntry> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelConfigEntry mapping : mappings) {
                for (String channelId : mapping.discord.channelIds) {
                    if (mapping.discord.chatsUseEmbeds) {
                        DiscordBridge.sendEmbedWithAvatar(
                            channelId,
                            MessageFormatter.embedJoin(context),
                            MessageFormatter.fetchAvatar(context));
                    } else {
                        DiscordBridge.sendMessage(channelId, MessageFormatter.formatJoin(context));
                    }
                }
            }
        };
    }
}
