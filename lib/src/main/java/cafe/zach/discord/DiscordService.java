package cafe.zach.discord;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.exceptions.InvalidTokenException;
import net.dv8tion.jda.api.requests.GatewayIntent;

import cafe.zach.discord.api.config.ConfigHandler;
import cafe.zach.discord.api.exceptions.InvalidDiscordConfigurationException;
import cafe.zach.discord.listeners.OnMessageReceived;
import cafe.zach.discord.listeners.ReadyListener;

class DiscordService {

    private JDA api;

    public DiscordService(ConfigHandler config) throws InterruptedException, InvalidDiscordConfigurationException {
        try {
            initialize(config);
        } catch (InvalidTokenException | IllegalArgumentException e) {
            throw new InvalidDiscordConfigurationException("provided token is invalid");
        }
    }

    public void initialize(ConfigHandler config)
        throws InterruptedException, InvalidTokenException, IllegalArgumentException {
        api = JDABuilder
            .createLight(
                config.discordToken,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS)
            .addEventListeners(new ReadyListener(), new OnMessageReceived())
            .build()
            .awaitReady();
    }

    public void destroy() {
        if (api != null) {
            api.shutdown();
            api = null;
        }
    }

    public void sendMessage(String channelId, String message) {
        TextChannel channel = api.getTextChannelById(channelId);
        if (channel == null) return;
        channel.sendMessage(message)
            .queue();
    }
}
