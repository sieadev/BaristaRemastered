package dev.siea.barista.games.counting;


import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.vitacraft.jmjda.api.config.ConfigUtil;
import net.vitacraft.jmjda.api.config.Filetype;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Counting extends ListenerAdapter {
    private final String CountingChannel = ConfigUtil.getConfig("config.yml", Filetype.YAML).getString("coutingChannel");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> task;

    public void onMessageReceived(MessageReceivedEvent event){
        if (!event.getChannel().getId().equals(CountingChannel)){
            return;
        };

        if (event.getAuthor().isBot()) return;

        String msg = event.getMessage().getContentRaw();

        int number;

        try {
            number = Integer.parseInt(msg);
        }
        catch (NumberFormatException e) {
            event.getMessage().delete().queue();
            return;
        }


        event.getChannel().getHistory().retrievePast(2)
                .map(messages -> messages.get(1))
                .queue(message -> {
                    String lastSender = (message.getAuthor().getId());
                    if (Integer.parseInt(message.getContentRaw()) != number - 1 || lastSender.equals(event.getAuthor().getId())){
                        event.getMessage().delete().queue();
                        return;
                    }
                    TextChannel channel = (TextChannel) event.getChannel();
                    start(channel);
                });
    }

    private void start(TextChannel channel){
        if (task != null){
            task.cancel(true);
        }
        loop(channel);
    }

    private void loop(TextChannel channel) {
        Random random = new Random();
        Runnable sendMessageTask = () -> {
            channel.getHistory().retrievePast(2)
                    .map(messages -> messages.get(0))
                    .queue(message -> {
                        if (message.getAuthor().isBot()) {
                            task.cancel(true);
                            return;
                        }
                        int randomNumber = random.nextInt(10) + 1;
                        if (randomNumber == 1) {
                            int number = Integer.parseInt(message.getContentRaw()) + 1;
                            channel.sendMessage("" + number).queue();
                        }
                    });
        };
        task = scheduler.scheduleAtFixedRate(sendMessageTask, 80, 30, TimeUnit.SECONDS);
    }
}
