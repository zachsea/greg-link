package cafe.zach.greglink.events;

import net.minecraftforge.event.ServerChatEvent;

import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.context.MinecraftChatContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerEventHandler {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        MinecraftChatContext context = new MinecraftChatContext(
            event.player.getCommandSenderName(),
            event.message,
            event.player.worldObj.getWorldInfo()
                .getWorldName(),
            "server");

        for (IMinecraftAction action : ActionRegistry.getMinecraftActions(ActionRegistry.ON_MINECRAFT_CHAT)) {
            action.execute(context);
        }
    }
}
