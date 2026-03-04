package cafe.zach.greglink.proxy;

import static cafe.zach.greglink.GregLink.LOG;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.common.MinecraftForge;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.CommonActions;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;
import cafe.zach.greglink.Tags;
import cafe.zach.greglink.config.GregLinkConfig;
import cafe.zach.greglink.events.ServerEventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

public class ServerProxy implements IProxy {

    // preInit "Run before anything else. Read your config, create blocks, items, etc, and register them with the
    // GameRegistry." (Remove if not needed)
    public void preInit(FMLPreInitializationEvent event) {
        GregLinkConfig config = new GregLinkConfig();
        ConfigHandler.setInstance(config);
        config.load();

        MinecraftForge.EVENT_BUS.register(new ServerEventHandler());
        LOG.info("Greg Link at version " + Tags.VERSION);
    }

    // load "Do your mod setup. Build whatever data structures you care about. Register recipes." (Remove if not needed)
    public void init(FMLInitializationEvent event) {}

    // postInit "Handle interaction with other mods, complete your setup based on this." (Remove if not needed)
    public void postInit(FMLPostInitializationEvent event) {}

    // register server commands in this event handler (Remove if not needed)
    public void serverStarting(FMLServerStartingEvent event) {
        try {
            DiscordBridge.createConnection();
        } catch (InvalidDiscordConfigurationException | InterruptedException e) {
            LOG.fatal("Could not connect to the Discord server!", e);
            // stop loading, make sure to allow config reload when that functionality is added
            return;
        }

        CommonActions.ChatSender sender = msg -> MinecraftServer.getServer()
            .getConfigurationManager()
            .sendChatMsg(new ChatComponentText(msg));

        ActionRegistry.register(ActionRegistry.ON_DISCORD_MESSAGE, CommonActions.broadcastToChat(sender));
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_CHAT, CommonActions.relayToDiscord());
    }
}
