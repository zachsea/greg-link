package cafe.zach.greglink.proxy;

import static cafe.zach.greglink.GregLink.LOG;

import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.greglink.Tags;
import cafe.zach.greglink.bridge.BridgeManager;
import cafe.zach.greglink.config.GregLinkConfig;
import cpw.mods.fml.common.event.*;

public class ServerProxy implements IProxy {

    public void preInit(FMLPreInitializationEvent event) {
        GregLinkConfig config = new GregLinkConfig();
        ConfigHandler.setInstance(config);
        config.load();
        // pulse a save to update new defaults in the config
        config.save();

        BridgeManager.registerEventHandlers();

        LOG.info("Greg Link at version " + Tags.VERSION);
    }

    public void init(FMLInitializationEvent event) {
        BridgeManager.registerActions();
    }

    public void postInit(FMLPostInitializationEvent event) {}

    public void serverStarting(FMLServerStartingEvent event) {
        BridgeManager.connect();
    }

    public void onServerStopping(FMLServerStoppingEvent event) {
        BridgeManager.disconnect();
    }
}
