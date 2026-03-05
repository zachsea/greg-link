package cafe.zach.greglink.registry;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

import cafe.zach.discord.api.action.CommonActions;
import cafe.zach.discord.api.action.registry.ActionRegistry;

public class DiscordActionRegistry {

    public static void register() {
        CommonActions.ChatSender sender = msg -> MinecraftServer.getServer()
            .getConfigurationManager()
            .sendChatMsg(new ChatComponentText(msg));

        ActionRegistry.register(ActionRegistry.ON_DISCORD_MESSAGE, CommonActions.broadcastToChat(sender));
    }
}
