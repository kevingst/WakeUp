package fr.kriiox.wakeup.listeners;

import fr.kriiox.wakeup.WakeUpMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class BedListeners implements Listener {

    private final WakeUpMain main;
    public BedListeners(WakeUpMain main){
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(main.getPlayerSpleep().size() != 0){
            main.updateBossBar();
            main.sleepingBar.addPlayer(player);
        }
        main.playerMove.put(player.getUniqueId(), System.currentTimeMillis());
        main.updatePlayerCanSleep();
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(main.getPlayerSpleep().size() != 0){
            main.updateBossBar();
            main.sleepingBar.removePlayer(player);
        }
        main.playerAFK.remove(player);
        main.updatePlayerCanSleep();
    }

    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent event){
        main.updatePlayerCanSleep();
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        main.playerMove.put(player.getUniqueId(), System.currentTimeMillis());
        if(main.playerAFK.contains(player)){
            main.playerAFK.remove(player);
            main.playerCanSleep.add(player);
            player.setPlayerListName(player.getName());
            Bukkit.broadcastMessage("§b" + player.getName() + " §fn'est plus AFK.");
        }
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event){
        Player playerEvent = event.getPlayer();

        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        main.updatePlayerCanSleep();

        if(!main.getPlayerSpleep().contains(playerEvent) && main.getPlayerCanSleep().contains(playerEvent)) main.getPlayerSpleep().add(playerEvent);

        main.updateBossBar();
        for (Player players : Bukkit.getOnlinePlayers()) {
            main.sleepingBar.addPlayer(players);
            players.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText("§b"+playerEvent.getName() + " §fest dans un lit."));
        }

        if(main.getSleepingValue() >= 0.5){
            main.skipNight();
        }
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event){
        Player player = event.getPlayer();

        main.getPlayerSpleep().remove(player);
        main.updatePlayerCanSleep();
        main.updateBossBar();
    }
}
