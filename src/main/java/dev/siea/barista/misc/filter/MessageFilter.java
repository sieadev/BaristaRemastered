package dev.siea.barista.misc.filter;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFilter extends ListenerAdapter {
    private final List<String> blockedWords = new ArrayList<>();
    private final List<String> blockedDomains = new ArrayList<>();

    public MessageFilter() {
        blockedWords.addAll(loadStringsFromConfig("/filter/word-filter.yml", "blacklisted"));
        blockedDomains.addAll(loadStringsFromConfig("/filter/link-filter.yml", "blacklisted"));
    }

    private List<String> loadStringsFromConfig(String path, String identifier) {
        List<String> strings = new ArrayList<>();
        try (InputStream input = MessageFilter.class.getResourceAsStream(path)) {
            if (input != null) {
                Yaml yaml = new Yaml();
                Map<String, List<String>> filterConfig = yaml.load(input);
                if (filterConfig != null && filterConfig.containsKey(identifier)) {
                    strings.addAll(filterConfig.get(identifier));
                }
            }
        } catch (Exception ignored) {
        }
        return strings;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        Message message = event.getMessage();
        if (containsBadWord(message)){
            message.reply(event.getAuthor().getAsMention() + ", please refrain from using that kind of language!").queue();
            message.delete().queue();
        }

        if (containsBadLink(message)){
            message.reply(event.getAuthor().getAsMention()+ ", please refrain from sending not Whitelisted Links!").queue();
            message.delete().queue();
        }
    }

    private boolean containsBadWord(Message message) {
        for (String word : blockedWords){
            if (message.getContentRaw().toLowerCase().contains(word)) return true;
        }
        return false;
    }

    private boolean containsBadLink(Message message) {
        List<String> links = extractDomains(message.getContentRaw());
        for (String goodDomain : blockedDomains){
            String goodDomainLower = goodDomain.toLowerCase();
            links.removeIf(link -> link.toLowerCase().contains(goodDomainLower));
        }
        return !links.isEmpty();
    }

    private List<String> extractDomains(String input) {
        List<String> domains = new ArrayList<>();
        // Regular expression for matching URLs
        String regex = "\\b(?:https?|ftp):\\/\\/(?:www\\.)?([a-zA-Z0-9.-]+(?:\\.[a-zA-Z]{2,}))\\b";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        // Find all domains in the input string
        while (matcher.find()) {
            domains.add(matcher.group(1));
        }
        return domains;
    }
}
