package cafe.zach.discord.api.action;

import cafe.zach.discord.api.action.context.DiscordMessageContext;

public interface IDiscordAction {

    void execute(DiscordMessageContext context);
}
