package dev.siea.barista.commands.administration;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.vitacraft.jmjda.api.commands.PrimitiveCommand;

public class KickCommand implements PrimitiveCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick a user";
    }

    @Override
    public Permission[] getNeededPermissions() {
        return new Permission[]{Permission.KICK_MEMBERS};
    }

    @Override
    public Channel[] getPermittedChannels() {
        return new Channel[0];
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        if (args.length == 0) {
            event.getMessage().reply("Please define a user").queue();
            return;
        }

        Member target = event.getMessage().getMentions().getMembers().isEmpty() ? null : event.getMessage().getMentions().getMembers().get(0);
        if (target == null) {
            event.getMessage().reply("User not found").queue();
            return;
        }

        event.getGuild().kick(target).queue(
                success -> {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setTitle("Kicked user " + target.getEffectiveName())
                            .setDescription("Successfully kicked " + target.getAsMention())
                            .setColor(0xFF964F);
                    MessageEmbed embed = embedBuilder.build();
                    event.getChannel().sendMessageEmbeds(embed).queue();
                },
                error -> {
                    event.getMessage().reply("Failed to kick user").queue();
                }
        );
    }

    @Override
    public void missingPermissions(MessageReceivedEvent event, String[] strings) {
        event.getMessage().reply("You can't do this.").queue();
    }
}
