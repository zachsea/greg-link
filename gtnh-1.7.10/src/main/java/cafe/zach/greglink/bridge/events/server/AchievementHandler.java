package cafe.zach.greglink.bridge.events.server;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatisticsFile;
import net.minecraftforge.event.entity.player.AchievementEvent;

import cafe.zach.discord.api.action.context.MinecraftContext;
import cafe.zach.discord.api.action.registry.ActionRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class AchievementHandler {

    @SubscribeEvent
    public void onAchievement(AchievementEvent event) {
        if (!(event.entityPlayer instanceof EntityPlayerMP)) return;

        EntityPlayerMP player = (EntityPlayerMP) event.entityPlayer;
        Achievement achievement = event.achievement;

        try {
            // Access the private StatisticsFile field
            java.lang.reflect.Field statsField = EntityPlayerMP.class.getDeclaredField("field_147103_bO");
            statsField.setAccessible(true);
            StatisticsFile stats = (StatisticsFile) statsField.get(player);

            // Only fire if the player can actually unlock this achievement
            if (!stats.canUnlockAchievement(achievement)) return;

            // Also skip if already unlocked
            if (stats.hasAchievementUnlocked(achievement)) return;

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String username = player.getDisplayName();
        String uuid = player.getUniqueID()
            .toString();
        String achievementName = achievement.func_150951_e()
            .getUnformattedText();
        String worldName = player.worldObj.getWorldInfo()
            .getWorldName();
        String dimensionId = String.valueOf(player.worldObj.provider.dimensionId);

        ActionRegistry.fireMinecraft(
            ActionRegistry.ON_MINECRAFT_ACHIEVEMENT,
            MinecraftContext.forAchievement(username, uuid, achievementName, worldName, dimensionId));
    }
}
