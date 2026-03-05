package cafe.zach.greglink.registry;

import cafe.zach.discord.api.action.CommonActions;
import cafe.zach.discord.api.action.registry.ActionRegistry;

public class MinecraftActionRegistry {

    public static void register() {
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_CHAT, CommonActions.relayChatToDiscord());
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_PLAYER_JOIN, CommonActions.relayJoinToDiscord());
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_PLAYER_LEAVE, CommonActions.relayLeaveToDiscord());
    }
}
