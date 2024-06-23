package dev.siea.barista;

import dev.siea.barista.commands.LetMeGoogleThatForYouCommand;
import dev.siea.barista.commands.TryAndSeeCommand;
import dev.siea.barista.commands.administration.BanCommand;
import dev.siea.barista.commands.administration.KickCommand;
import dev.siea.barista.commands.simple.CatbaseCommand;
import dev.siea.barista.commands.simple.InfoCommand;
import dev.siea.barista.games.counting.Counting;
import dev.siea.barista.misc.JoinMessenger;
import dev.siea.barista.misc.filter.MessageFilter;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.ShardManager;
import net.vitacraft.jmjda.JMBot;
import net.vitacraft.jmjda.api.commands.PrimitiveCommandsManager;
import net.vitacraft.jmjda.api.config.ConfigUtil;
import net.vitacraft.jmjda.api.config.Filetype;

public class Barista {


    public Barista(){
        String token = ConfigUtil.getConfig("config.yml", Filetype.YAML).getString("token");
        JMBot bot = new JMBot(token);
        bot.setActivity(Activity.watching("You!"));
        PrimitiveCommandsManager manager = bot.getPrimitiveCommandsManager();
        manager.registerCommand(new BanCommand());
        manager.registerCommand(new KickCommand());
        manager.registerCommand(new CatbaseCommand());
        manager.registerCommand(new InfoCommand());
        manager.registerCommand(new LetMeGoogleThatForYouCommand());
        manager.registerCommand(new TryAndSeeCommand());

        ShardManager shardManager = bot.getShardManager();
        shardManager.addEventListener(new Counting());
        shardManager.addEventListener(new JoinMessenger(shardManager));
        shardManager.addEventListener(new MessageFilter());
    }

    public static void main(String[] args) {
        Barista barista = new Barista();
    }
}
