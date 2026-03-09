package cafe.zach.discord.api.action.format;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import cafe.zach.discord.api.action.context.DiscordContext;
import cafe.zach.discord.api.action.context.MinecraftContext;

public class MessageFormatter {

    public static byte[] fetchAvatar(MinecraftContext context) {
        return AvatarUtil.fetchPaddedAvatar(context.username);
    }

    // Minecraft -> Discord

    public static String formatChat(MinecraftContext context) {
        return String.format("**%s** » %s", context.username, context.content);
    }

    public static MessageEmbed embedChat(MinecraftContext context) {
        return new EmbedBuilder().setAuthor(context.username, null, "attachment://author.png")
            .setDescription(context.content)
            .setColor(EmbedColors.CHAT)
            .build();
    }

    public static String formatJoin(MinecraftContext context) {
        return String.format("**%s** joined", context.username);
    }

    public static MessageEmbed embedJoin(MinecraftContext context) {
        return new EmbedBuilder()
            .setAuthor(String.format("%s joined the server", context.username), null, "attachment://author.png")
            .setColor(EmbedColors.JOIN)
            .build();
    }

    public static String formatLeave(MinecraftContext context) {
        return String.format("**%s** left", context.username);
    }

    public static MessageEmbed embedLeave(MinecraftContext context) {
        return new EmbedBuilder()
            .setAuthor(String.format("%s left the server", context.username), null, "attachment://author.png")
            .setColor(EmbedColors.LEAVE)
            .build();
    }

    // Discord -> Minecraft

    public static String formatDiscordMessage(DiscordContext context) {
        return String.format("[Discord] %s: %s", context.username, context.content);
    }

    public static String formatDiscordReady(DiscordContext context) {
        return String
            .format("[Discord] Ready on %s %s!", context.guildCount, context.guildCount == 1 ? "guild" : "guilds");
    }
}
