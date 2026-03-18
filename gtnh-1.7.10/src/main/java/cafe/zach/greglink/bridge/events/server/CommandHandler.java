package cafe.zach.greglink.bridge.events.server;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.CommandEvent;

import cafe.zach.discord.api.action.context.MinecraftContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class CommandHandler {

    @SubscribeEvent
    public void onCommand(CommandEvent event) {
        String uuid = null;

        if (event.sender instanceof EntityPlayer) {
            uuid = ((EntityPlayer) event.sender).getUniqueID()
                .toString();
        }

        String fullCommand = "/" + event.command.getCommandName()
            + (event.parameters.length > 0 ? " " + String.join(" ", event.parameters) : "");

        ActionRegistry.fireMinecraft(
            ActionRegistry.ON_MINECRAFT_COMMAND,
            MinecraftContext.forChatCommand(event.sender.getCommandSenderName(), uuid, fullCommand));
    }
}
