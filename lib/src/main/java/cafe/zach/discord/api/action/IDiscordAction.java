package cafe.zach.discord.api.action;

import cafe.zach.discord.api.action.context.DiscordContext;

public interface IDiscordAction {

    void execute(DiscordContext context);
}
