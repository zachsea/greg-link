package cafe.zach.discord.api.action;

import cafe.zach.discord.api.action.context.MinecraftChatContext;

public interface IMinecraftAction {

    void execute(MinecraftChatContext context);
}
