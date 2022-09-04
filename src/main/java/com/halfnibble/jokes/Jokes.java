package com.halfnibble.jokes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class Jokes extends JavaPlugin {
    public static String errorMessage = "Jokes on you, there was an error retreiving a joke.";
    private Logger LOG = getLogger();

    @Override
    public void onEnable() {
        // Plugin startup logic
        LOG.info("Reticulating the funny bones.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        LOG.info("Disabling yonder shields.");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player && label.equalsIgnoreCase("joke")) {
            makeFunny((Player) sender);
            return true;
        }
        return false;
    }

    public static String getJoke() {
        try {
            URL url = new URL("https://icanhazdadjoke.com/");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.addRequestProperty("Accept", "application/json");
            conn.addRequestProperty("User-Agent", "Mozilla/4.0");
            InputStream input = conn.getInputStream();
            Scanner scanner = new Scanner(input, "UTF-8");
            scanner.useDelimiter("\\A");
            String response = scanner.next();
            scanner.close();
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();
            String joke = jsonObject.getAsJsonPrimitive("joke").getAsString();
            return joke;
        } catch (IOException e) {
            return Jokes.errorMessage;
        }
    }

    private void makeFunny(Player player) {
        String name = player.getName();
        String joke = Jokes.getJoke();
        String formatJoke = ChatColor.GREEN + "[" + name + "'s dad] " + ChatColor.YELLOW + joke;
        LOG.info(formatJoke);
        Bukkit.broadcastMessage(formatJoke);
    }
}
