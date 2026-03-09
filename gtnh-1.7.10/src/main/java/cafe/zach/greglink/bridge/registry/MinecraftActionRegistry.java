package cafe.zach.greglink.bridge.registry;

import cafe.zach.discord.api.action.common.minecraft.RelayChatToDiscord;
import cafe.zach.discord.api.action.common.minecraft.RelayJoinToDiscord;
import cafe.zach.discord.api.action.common.minecraft.RelayLeaveToDiscord;
import cafe.zach.discord.api.action.registry.ActionRegistry;

public class MinecraftActionRegistry {

    public static void register() {
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_CHAT, RelayChatToDiscord.create());
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_PLAYER_JOIN, RelayJoinToDiscord.create());
        ActionRegistry.register(ActionRegistry.ON_MINECRAFT_PLAYER_LEAVE, RelayLeaveToDiscord.create());
    }
}
