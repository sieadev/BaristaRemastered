package dev.siea.barista.misc;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.vitacraft.jmjda.api.config.ConfigUtil;
import net.vitacraft.jmjda.api.config.Filetype;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.time.Instant;

public class JoinMessenger extends ListenerAdapter {
    private final TextChannel welcomeChannel;
    private final TextChannel logChannel;

    public JoinMessenger(ShardManager shardManager) {
        String countingChannelID = ConfigUtil.getConfig("config.yml", Filetype.YAML).getString("channels.counting");
        String logChannelID = ConfigUtil.getConfig("config.yml", Filetype.YAML).getString("channels.log");
        welcomeChannel = shardManager.getTextChannelById(countingChannelID);
        logChannel = shardManager.getTextChannelById(logChannelID);
    }

    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        if (event.getUser().isBot()){
            return;
        }

        Guild guild = event.getGuild();
        User user = event.getUser();

        EmbedBuilder embed = new EmbedBuilder()
        .setAuthor("Welcome " + event.getUser().getName(), event.getJDA().getSelfUser().getAvatarUrl())
        .setThumbnail(user.getAvatarUrl())
        .setDescription(event.getMember().getAsMention() + " joined the server!\n Total Members: " + guild.getMemberCount() + ". Member!")
        .setColor(new Color(16, 255, 0))
        .setTimestamp(Instant.now());
        logChannel.sendMessageEmbeds(embed.build()).queue();
        welcomeChannel.sendMessage(user.getAsMention()+ " Welcome to SIEA Cafe! We hope you have a great time here. :)").queue();
    }

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        EmbedBuilder embed = new EmbedBuilder()
                .setDescription("Someone left the server!")
                .setColor(Color.red)
                .setTimestamp(Instant.now());
        logChannel.sendMessageEmbeds(embed.build()).queue();
    }
}
