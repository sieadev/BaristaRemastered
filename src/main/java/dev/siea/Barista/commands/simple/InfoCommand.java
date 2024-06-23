package dev.siea.Barista.commands.simple;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.channel.Channel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.vitacraft.jmjda.api.commands.PrimitiveCommand;
import net.vitacraft.jmjda.api.config.ConfigUtil;
import net.vitacraft.jmjda.api.config.Filetype;
import net.vitacraft.jmjda.api.config.YAMLConfig;

public class InfoCommand implements PrimitiveCommand {
    private final YAMLConfig config = (YAMLConfig) ConfigUtil.getConfig("config.yml", Filetype.YAML);

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getDescription() {
        return "Basic information about this bot.";
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
        CommandEmbedResponse response = new CommandEmbedResponse("Barista - Info", "Version: " + config.getString("version"), 0xFF964F);
        response.addField("Ownership" , "Created and maintained by **@sieadev** and other (**Contributors**)[https://github.com/sieadev/BaristaRemastered/graphs/contributors]");
        response.reply(event.getMessage());
    }

    @Override
    public void missingPermissions(MessageReceivedEvent messageReceivedEvent, String[] strings) {

    }
}
