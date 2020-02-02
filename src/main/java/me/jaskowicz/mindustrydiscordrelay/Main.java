package me.jaskowicz.mindustrydiscordrelay;

import arc.*;
import arc.net.Server;
import arc.util.*;
import me.jaskowicz.mindustrydiscordrelay.Utils.DiscordUtils;
import me.jaskowicz.mindustrydiscordrelay.Utils.FormatUtils;
import me.jaskowicz.mindustrydiscordrelay.Utils.User;
import mindustry.*;
import mindustry.game.EventType;
import mindustry.mod.Mods;
import mindustry.plugin.Plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Main extends Plugin {

    // This work is licensed under the Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License.
    // To view a copy of this license,
    // visit http://creativecommons.org/licenses/by-nc-sa/4.0/ or send a letter to Creative Commons, PO Box 1866, Mountain View, CA 94042, USA.

    // Created by: Archie Jaskowicz.

    private long serverTimeStart;
    private long serverTimeFinish;

    // Replace "<INSERT_DISCORD_WEBHOOK_HERE>" with your discord webhook url.
    private String discordWebhook = "<INSERT_DISCORD_WEBHOOK_HERE>";

    public static HashMap<String, User> USERS = new HashMap<>();

    //register event handlers and create variables in the constructor
    public Main(){

        //readConfigFile();

        // Ignore the yellow on this line. It's just a fail safe but because discordWebhook is never changed and don't include that part of the string,
        // it shows that this will always be ran. If you change it then it won't.
        if(!discordWebhook.startsWith("https://discordapp.com/api/webhooks/")) {
            System.out.println("Invalid discord webhookb!\n\nThis plugin will throw a lot of errors in the console if you don't attempt to fix this!");
        }

        Events.on(EventType.ServerLoadEvent.class, event -> {
            DiscordUtils.sendMessage(discordWebhook, "The console has been activated! This does not mean the server is up, continue to head into the console and load the server up.");
        });

        Events.on(EventType.PlayerJoin.class, event -> {
            DiscordUtils.sendMessage(discordWebhook, event.player.name + " has joined the game!");
            USERS.put(event.player.uuid, new User(event.player));
        });

        Events.on(EventType.PlayerLeave.class, event -> {
            DiscordUtils.sendMessage(discordWebhook, event.player.name + " has left the game!");
            USERS.remove(event.player.uuid);
        });

        Events.on(EventType.WorldLoadEvent.class, event -> {
            serverTimeStart = System.currentTimeMillis();
            DiscordUtils.sendMessage(discordWebhook, "A new game has started! Be sure to join and contribute to your fellow players! There are " + USERS.values().size() + " user(s) online.");
        });

        Events.on(EventType.GameOverEvent.class, event -> {
            serverTimeFinish = System.currentTimeMillis();

            long x = serverTimeFinish - serverTimeStart;
            String time = FormatUtils.formatTime(x);
            DiscordUtils.sendMessage(discordWebhook, "The current game has ended! The game ended at wave " + Vars.state.wave + " after " + time + " on the map '" + Vars.world.getMap().name() + "'!");
        });

        Events.on(EventType.PlayerChatEvent.class, event -> {
            if(!event.message.startsWith("/")) {
                if (event.player.isAdmin) {
                    DiscordUtils.sendMessage(discordWebhook, event.player.name + " [ADMIN]: " + event.message);
                } else {
                    DiscordUtils.sendMessage(discordWebhook, event.player.name + ": " + event.message);
                }
            }
        });
    }

    //register commands that run on the server
    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("senddiscordmessage", "<text...>", "Send a message to your server's discord via webhook.", (args) -> {
            StringBuilder message = new StringBuilder();
            for(String str : args) {
                message.append(str).append(" ");
            }
            Log.debug("Sending " + message + " to discord...");
            DiscordUtils.sendMessage(discordWebhook, message.toString());
            Log.debug("Sent the message to discord!");
        });

        handler.register("setdiscordwebhook", "<text>", "Set the webhook for this plugin!", (args) -> {
           if(args.length == 0) {
               Log.debug("Insufficient arguments.");
           } else if(args.length == 1) {
               discordWebhook = args[0];
               Log.debug("Updated the discord webhook!");
           } else {
               Log.debug("Too many arguments.");
           }
        });
    }

    //register commands that player can invoke in-game
    //@Override
    //public void registerClientCommands(CommandHandler handler){
    //}

    /*

    // This just throws some stuff about how it failed to create the file because it's just not doing it. No idea why but oh well.
    // In my opinion, this is really dumb that this doesn't work and just makes everything worse for people so I may end up making a video
    // on how to get this setup for people.

    private void readConfigFile() {
        File file = new File("/MindustryDiscordRelayConfig/config/DiscordStuff.txt");

        if(file.exists()) {
            System.out.println("Config found! Reading config...");

            Scanner scanner = null;

            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException exception) {
                System.out.println("Config not found... generating then disabling plugin. You will need to change the config and restart the server.");
                return;
            }

            if(!scanner.hasNextLine()) {
                System.out.println("Nothing found inside the config. Preventing plugin from running as plugin will cause errors.");

                for(Mods.LoadedMod loadedMod : Vars.mods.list()) {
                    if(loadedMod.name.equals("Mindustry-Discord-Relay")) {
                        Vars.mods.setEnabled(loadedMod, false);
                    }
                }

                return;
            }

            while (scanner.hasNextLine()) {
                String text = scanner.nextLine();
                String[] args = text.split(":");

                if(args.length != 0) {
                    if(args.length == 2) {
                        discordWebhook = args[1];
                    } else if (args.length < 2) {
                        System.out.println("Line has too little arguments (colons) on it. Ignoring line.");
                    } else {
                        System.out.println("Line has too many arguments (colons) on it. Ignoring line.");
                    }
                } else {
                    System.out.println("Config does not have any information.");
                    return;
                }
            }

            if(discordWebhook == null) {
                System.out.println("Could not find a discord webhook. Disabling plugin.");

                for(Mods.LoadedMod loadedMod : Vars.mods.list()) {
                    if(loadedMod.name.equals("Mindustry-Discord-Relay")) {
                        Vars.mods.setEnabled(loadedMod, false);
                    }
                }

                return;
            }

            System.out.println("Finished file loading.");

        } else {

            System.out.println("Config not found... generating then disabling plugin. You will need to change the config and restart the server.");

            try {
                //file.mkdirs();
                boolean created = file.createNewFile();

                if(created) {
                    System.out.println("File created.");
                } else {
                    System.out.println("File failed to be created.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for(Mods.LoadedMod loadedMod : Vars.mods.list()) {
                if(loadedMod.name.equals("Mindustry-Discord-Relay")) {
                    Vars.mods.setEnabled(loadedMod, false);
                }
            }

            return;
        }
    }

     */
}
