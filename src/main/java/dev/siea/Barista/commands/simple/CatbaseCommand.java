package dev.siea.Barista.commands.simple;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.vitacraft.jmjda.api.commands.PrimitiveCommand;

public class CatbaseCommand implements PrimitiveCommand {
    @Override
    public String getName() {
        return "cats";
    }

    @Override
    public String getDescription() {
        return "Sends the Catbase Link";
    }

    @Override
    public Permission[] getNeededPermissions() {
        return new Permission[0];
    }

    @Override
    public Channel[] getPermittedChannels() {
        return new Channel[0];
    }

    @Override
    public void execute(MessageReceivedEvent messageReceivedEvent, String[] strings) {
        CommandEmbedResponse response = new CommandEmbedResponse("The Catbase","The Catbase can be found at https://catbase.siea.dev");
        response.reply(messageReceivedEvent.getMessage());
    }

    @Override
    public void missingPermissions(MessageReceivedEvent messageReceivedEvent, String[] strings) {

    }
}
