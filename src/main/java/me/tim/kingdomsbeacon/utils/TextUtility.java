package me.tim.kingdomsbeacon.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class TextUtility {

    public void log(Level lvl, String message) {
        Bukkit.getLogger().log(lvl, message);
    }

    public void message(CommandSender sender, List<String> message, Placeholder... placeholders) {

        message = colorize(message, placeholders);

        message.forEach(sender::sendMessage);
    }

    public void message(CommandSender sender, String message, Placeholder... placeholders) {
        sender.sendMessage(colorize(message, placeholders));
    }

    public String capitalizeFirstLetter(String original) {

        if (original == null || original.length() == 0) return original;

        return original.substring(0, 1).toUpperCase() + original.substring(1);
    }

    public static String capitalizeEachWord(String input) {
        String[] words = input.split("\\s+");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                char firstChar = Character.toUpperCase(word.charAt(0));
                String restOfWord = word.substring(1).toLowerCase();
                result.append(firstChar).append(restOfWord).append(" ");
            }
        }

        return result.toString().trim();
    }

    public static String strip(String string, Placeholder... placeHolders) {

        String message = string;

        for (Placeholder placeHolder : placeHolders) {
            message = message.replace(placeHolder.getKey(), placeHolder.getValue());
        }

        return ChatColor.stripColor(message);
    }

    public String replaceAll(String text, Collection<Placeholder> placeholders) {
        for (Placeholder placeholder : placeholders) {
            text = text.replace(placeholder.getKey(), placeholder.getValue());
        }
        return text;
    }

    public static String colorize(String text, Collection<Placeholder> placeholders) {

        text = ChatColor.translateAlternateColorCodes('&', text);

        for (Placeholder placeholder : placeholders) {
            text = colorize(text, placeholder);
        }

        return text;
    }

    public static String colorize(String text, Placeholder... placeholders) {

        text = ChatColor.translateAlternateColorCodes('&', text);

        for (Placeholder placeholder : placeholders) {
            text = ChatColor.translateAlternateColorCodes('&', text.replace(placeholder.getKey(), placeholder.getValue()));
        }

        return text;
    }

    public List<String> colorize(Collection<String> list, Collection<Placeholder> placeholders) {
        List<String> colored = new ArrayList<>();

        list.forEach(text -> {

            text = colorize(text);

            for (Placeholder placeholder : placeholders) {
                text = colorize(text.replace(placeholder.getKey(), placeholder.getValue()));
            }

            colored.add(text);
        });

        return colored;
    }


    public List<String> colorize(Collection<String> list, Placeholder... placeholders) {

        List<String> colored = new ArrayList<>();

        list.forEach(text -> {

            text = colorize(text);

            for (Placeholder placeholder : placeholders) {
                text = colorize(text.replace(placeholder.getKey(), placeholder.getValue()));
            }

            colored.add(text);
        });

        return colored;
    }

    public String getProgressBar(int current, int max, int totalBars, String symbol, String notSymbol){

        float percent = (float) current / max;

        int progressBars = (int) (totalBars * percent);

        int leftOver = (totalBars - progressBars);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < progressBars; i++) sb.append(symbol);
        for (int i = 0; i < leftOver; i++) sb.append(notSymbol);

        return sb.toString();

    }

    public String getDurationBreakdown(long millis) {

        if (millis < 0) return "0";

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);

        if (days != 0) {
            sb.append(days);
            sb.append(" D, ");
        }

        if (hours != 0) {
            sb.append(hours);
            sb.append(" Hr, ");
        }

        if (minutes != 0) {
            sb.append(minutes);
            sb.append(" Min, ");
        }

        if (seconds != 0) {
            sb.append(seconds);
            sb.append(" Sec");
        } else {
            sb.append(millis);
            sb.append(" Ms");
        }

        return sb.toString();
    }
}
