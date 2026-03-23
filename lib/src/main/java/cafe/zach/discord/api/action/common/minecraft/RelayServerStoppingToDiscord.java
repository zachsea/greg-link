package cafe.zach.discord.api.action.common.minecraft;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.channels.ChannelConfigEntry;
import cafe.zach.discord.api.config.ConfigHandler;

public class RelayServerStoppingToDiscord {

    public static IMinecraftAction create() {
        return context -> {
            List<ChannelConfigEntry> mappings = ConfigHandler.getInstance().channels;

            for (ChannelConfigEntry mapping : mappings) {
                for (String channelId : mapping.discord.channelIds) {
                    if (mapping.discord.eventsUseEmbeds) {
                        DiscordBridge.sendEmbed(channelId, MessageFormatter.embedServerStopping(context));
                    } else {
                        DiscordBridge.sendMessage(channelId, MessageFormatter.formatServerStopping(context));
                    }
                }
            }
        };
    }
}
