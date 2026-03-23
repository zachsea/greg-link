package cafe.zach.discord.api.action.common.minecraft;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.ChannelMapping;
import cafe.zach.discord.api.config.ChannelMinecraftConfig;
import cafe.zach.discord.api.config.ConfigHandler;

public class RelayCommandToDiscord {

    public static IMinecraftAction create() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getChannelsForDimension(ChannelMinecraftConfig.WILDCARD);

            for (ChannelMapping mapping : mappings) {
                for (String channelId : mapping.discord.channelIds) {
                    if (mapping.discord.chatsUseEmbeds) {
                        DiscordBridge.sendEmbedWithAvatar(
                            channelId,
                            MessageFormatter.embedChatCommand(context),
                            MessageFormatter.fetchAvatar(context));
                    } else {
                        DiscordBridge.sendMessage(channelId, MessageFormatter.formatChatCommand(context));
                    }
                }
            }
        };
    }
}
