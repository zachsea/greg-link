package cafe.zach.greglink.bridge.events.server;

import net.minecraftforge.event.ServerChatEvent;

import cafe.zach.discord.api.action.context.MinecraftContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ServerChatEventHandler {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        ActionRegistry.fireMinecraft(
            ActionRegistry.ON_MINECRAFT_CHAT,
            MinecraftContext.forChat(
                event.player.getCommandSenderName(),
                event.player.getUniqueID()
                    .toString(),
                event.message,
                event.player.worldObj.getWorldInfo()
                    .getWorldName(),
                String.valueOf(event.player.worldObj.provider.dimensionId)));
    }
}
