package cafe.zach.greglink.bridge;

import net.minecraftforge.common.MinecraftForge;

import cafe.zach.discord.DiscordBridge;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;
import cafe.zach.greglink.GregLink;
import cafe.zach.greglink.bridge.events.server.ServerChatEventHandler;
import cafe.zach.greglink.bridge.events.server.ServerPlayerJoinEventHandler;
import cafe.zach.greglink.bridge.events.server.ServerPlayerLeaveEventHandler;
import cafe.zach.greglink.bridge.registry.DiscordActionRegistry;
import cafe.zach.greglink.bridge.registry.MinecraftActionRegistry;
import cpw.mods.fml.common.FMLCommonHandler;

public class BridgeManager {

    public static void registerEventHandlers() {
        MinecraftForge.EVENT_BUS.register(new ServerChatEventHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new ServerPlayerJoinEventHandler());
        FMLCommonHandler.instance()
            .bus()
            .register(new ServerPlayerLeaveEventHandler());
    }

    public static void registerActions() {
        MinecraftActionRegistry.register();
        DiscordActionRegistry.register();
    }

    public static void connect() {
        try {
            DiscordBridge.createConnection();
        } catch (InvalidDiscordConfigurationException | InterruptedException e) {
            GregLink.LOG.fatal("Could not connect to Discord!", e);
        }
    }

    public static void disconnect() {
        ActionRegistry.clear();
        DiscordBridge.shutdown();
    }
}
