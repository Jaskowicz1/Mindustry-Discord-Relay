# Mindustry-Discord-Relay
Mindustry-Discord-Relay (MDDR) is a plugin for Mindustry which allows you to connect your server to discord (using a webhook) and relay information from Mindustry to Discord.


---


# Installation

### Method A:

Download the source and edit the Main class (Probably by cloning the source and opening it in an IDE so you can export the plugin easily). On line 32 you should see ```private String discordWebhook = "<INSERT_DISCORD_WEBHOOK_HERE>";```, change `<INSERT_DISCORD_WEBHOOK_HERE>` to your webhook. Once you've changed that, you can export the plugin!

Once exported, grab the jar file (located in build/libs) and place it into config/mods which is located in your server's directory (folder) and you should be good to run your server! 


### Method B:

Head on over to the releases tab and download the recent .jar file. Place it in config/mods which is located in your server's directory (folder) and run your server.

Once the server has booted up, you will get "An error occured when trying to send a message via Discord Webhooks.", don't panic about this. In your console do ```setdiscordwebhook <INSERT_DISCORD_WEBHOOK_HERE>``` (of course, you don't include the "<>" in there) and you should be good to go!

Every time you boot up the server, you will need to do ```setdiscordwebhook <INSERT_DISCORD_WEBHOOK_HERE>``` unless you use Method A.


---


# Usage


The plugin will automatically handle server start ups, game over events, new map events, players leaving and joining, and the chat event. If any of these events are fired, information will be relayed to discord via your webhook. This can allow for a great console system for Mindustry within discord!

You can, however, send custom messages as well! Doing ```senddiscordmessage <message>``` will send a message to your discord server via your webhook! Example: ```senddiscordmessage I am a cool dude!```.

---


# Licence


<a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc-sa/4.0/88x31.png" /></a><br />This work is licensed under a <a rel="license" href="http://creativecommons.org/licenses/by-nc-sa/4.0/">Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License</a>.
