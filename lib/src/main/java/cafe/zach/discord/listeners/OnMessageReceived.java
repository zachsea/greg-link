package cafe.zach.discord.listeners;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jetbrains.annotations.NotNull;

import cafe.zach.discord.api.action.IDiscordAction;
import cafe.zach.discord.api.action.context.DiscordMessageContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;

public class OnMessageReceived extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (
            event.getAuthor()
                .isBot()
        ) return;

        DiscordMessageContext context = new DiscordMessageContext(
            event.getAuthor()
                .getName(),
            event.getMessage()
                .getContentDisplay(),
            event.getChannel()
                .getId(),
            event.getGuild()
                .getId());

        for (IDiscordAction action : ActionRegistry.getDiscordActions(ActionRegistry.ON_DISCORD_MESSAGE)) {
            action.execute(context);
        }
    }
}
