package cafe.zach.discord.api.action.common.minecraft;

import java.util.List;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.format.MessageFormatter;
import cafe.zach.discord.api.config.ChannelMapping;
import cafe.zach.discord.api.config.ConfigHandler;

public class RelayServerStoppedToDiscord {

    public static IMinecraftAction create() {
        return context -> {
            List<ChannelMapping> mappings = ConfigHandler.getInstance()
                .getRelayChannels();

            for (ChannelMapping mapping : mappings) {
                for (String channelId : mapping.discord.channelIds) {
                    if (mapping.discord.eventsUseEmbeds) {
                        DiscordBridge.sendEmbed(channelId, MessageFormatter.embedServerStopped(context));
                    } else {
                        DiscordBridge.sendMessage(channelId, MessageFormatter.formatServerStopped(context));
                    }
                }
            }
        };
    }
}
