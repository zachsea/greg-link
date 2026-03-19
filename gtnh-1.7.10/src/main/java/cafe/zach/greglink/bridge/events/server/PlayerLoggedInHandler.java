package cafe.zach.greglink.bridge.events.server;

import cafe.zach.discord.api.action.context.MinecraftContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

public class PlayerLoggedInHandler {

    @SubscribeEvent
    public void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
        ActionRegistry.fireMinecraft(
            ActionRegistry.ON_MINECRAFT_PLAYER_JOIN,
            MinecraftContext.forPlayerJoin(
                event.player.getCommandSenderName(),
                event.player.getUniqueID()
                    .toString()));
    }
}
