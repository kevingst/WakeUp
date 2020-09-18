# WakeUp 
> Version 1.5.1

Download [Here](https://www.spigotmc.org/resources/wakeup.83815/)

This plugin offers you the possibility to skip the night with a visual animation.
Developed for survival servers but can be used on any type of servers.
If 50% of the connected players are in a bed, the night will be accelerated until sunrise.
The phantoms stat will be reset and their spawn will be avoided.

**The plugin was made for the spigot-1.16.1.**

## ►AFK
This plugin put the player, after 5 minutes of inactivity, in an “AFK” state and he will not be counted to skip the night.

## ►GameMode
If a player is connected in another gamemode than “survival” he will not be counted to skip the night.

## ►Message
When a player enters a bed, a boss bar will appear indicating the number of players in a bed
The boss bar disappear if no one is in a bed or after the night skip

![](https://www.spigotmc.org/attachments/img_bossbar-png.555153/)

A message in the Actionbar appear when someone enter a bed and will disappear after few second.

![](https://www.spigotmc.org/attachments/img_actionbar-png.555152/)

## ►Fully configurable
You can personalize the different message and the time required to be “AFK”.
```yml
# This is a message when the player gets into a bed.
bossbar-message: "Passer la nuit"
actionbar-message: "est dans un lit."

afk:
 # This is a message when player is AFK.
  isAfk: "est maintenant AFK."
  # This is a message when the player is no longer AFK.
  noLongerAfk: "n'est plus AFK."
  # After how long the player becomes an AFK player (ms).
  time: 300000
```

Inspired by the plugin “Harbor” by [TechToolbox](https://www.spigotmc.org/resources/authors/techtoolbox.266535/).
If you have a bug with the plugin contact me on discord (French or English): Kriiox#8797