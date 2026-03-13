package cafe.zach.greglink.proxy;

import cpw.mods.fml.common.event.*;

public interface IProxy {

    void preInit(FMLPreInitializationEvent event);

    void init(FMLInitializationEvent event);

    void postInit(FMLPostInitializationEvent event);

    void serverStarting(FMLServerStartingEvent event);

    void serverStarted(FMLServerStartedEvent event);

    void serverStopping(FMLServerStoppingEvent event);

    void serverStopped(FMLServerStoppedEvent event);
}
