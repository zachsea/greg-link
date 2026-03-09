package cafe.zach.discord.api.action.common.discord;

import cafe.zach.discord.api.action.ChatSender;
import cafe.zach.discord.api.action.IDiscordAction;
import cafe.zach.discord.api.action.format.MessageFormatter;

public class BroadcastReadyToChat {

    public static IDiscordAction create(ChatSender sender) {
        return context -> sender.send(MessageFormatter.formatDiscordReady(context));
    }
}
