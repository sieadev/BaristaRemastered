package dev.siea.barista.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.vitacraft.jmjda.api.commands.PrimitiveCommand;

public class LetMeGoogleThatForYouCommand implements PrimitiveCommand {
    @Override
    public String getName() {
        return "letmegooglethatforyou";
    }

    @Override
    public String getDescription() {
        return "Was it really that hard?";
    }

    @Override
    public Permission[] getNeededPermissions() {
        return new Permission[]{Permission.MESSAGE_SEND};
    }

    @Override
    public Channel[] getPermittedChannels() {
        return new Channel[0];
    }

    @Override
    public void execute(MessageReceivedEvent event, String[] args) {
        Message originalMessage = event.getMessage();
        Message referencedMessage = originalMessage.getReferencedMessage();

        if (referencedMessage == null) {
            originalMessage.reply("For this command to work you need to be replying to a message!").queue();
            return;
        }

        StringBuilder search = new StringBuilder().append("https://letmegooglethat.com/?q=");
        String raw = referencedMessage.getContentRaw();
        raw = raw.replace(" ", "+").replace("?", "");
        search.append(raw);

        EmbedBuilder embedBuilder = new EmbedBuilder()
                .setTitle("Let me google that for you real quick")
                .setThumbnail("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTCWKGr_E3qM7B-B-_xwIZyF12n3sK3eM1q5w&s")
                .setDescription(search.toString())
                .setColor(0xFF964F);
        MessageEmbed embed = embedBuilder.build();
        referencedMessage.replyEmbeds(embed).queue();
    }

    @Override
    public void missingPermissions(MessageReceivedEvent messageReceivedEvent, String[] strings) {

    }
}
