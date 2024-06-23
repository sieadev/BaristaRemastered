package dev.siea.Barista.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.vitacraft.jmjda.api.commands.PrimitiveCommand;

public class TryAndSeeCommand implements PrimitiveCommand {
    @Override
    public String getName() {
        return "tas";
    }

    @Override
    public String getDescription() {
        return "Try and see";
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
        messageReceivedEvent.getMessage().reply("https://tryitands.ee/").queue();
    }

    @Override
    public void missingPermissions(MessageReceivedEvent messageReceivedEvent, String[] strings) {

    }
}
