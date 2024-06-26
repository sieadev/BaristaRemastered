package dev.siea.barista.commands.simple;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class CommandEmbedResponse {
    private final EmbedBuilder embedBuilder;

    public CommandEmbedResponse(String title, String description) {
        embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(Color.GREEN);
    }

    public CommandEmbedResponse(String title, String description, int color) {
         embedBuilder = new EmbedBuilder()
                .setTitle(title)
                .setDescription(description)
                .setColor(color);
    }

    public void addField(String title, String value) {
        embedBuilder.addField(title,value,false);
    }

    public void addField(String title, String value, boolean inline) {
        embedBuilder.addField(title,value,inline);
    }

    public void reply(Message message){
        MessageEmbed embed =  embedBuilder.build();
        message.replyEmbeds(embed).queue();
    }
}
