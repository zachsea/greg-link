package cafe.zach.greglink.events;

import net.minecraftforge.event.ServerChatEvent;

import cafe.zach.discord.api.action.IMinecraftAction;
import cafe.zach.discord.api.action.context.MinecraftContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerEventHandler {

    @SubscribeEvent
    public void onServerChat(ServerChatEvent event) {
        MinecraftContext context = MinecraftContext.forChat(
            event.player.getCommandSenderName(),
            event.message,
            event.player.worldObj.getWorldInfo()
                .getWorldName(),
            "server");

        for (IMinecraftAction action : ActionRegistry.getMinecraftActions(ActionRegistry.ON_MINECRAFT_CHAT)) {
            action.execute(context);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        MinecraftContext context = MinecraftContext
            .forPlayerJoin(event.player.getDisplayName(), event.player.isBurning() ? "burning" : "not burning");

        for (IMinecraftAction action : ActionRegistry.getMinecraftActions(ActionRegistry.ON_MINECRAFT_PLAYER_JOIN)) {
            action.execute(context);
        }
    }
}
