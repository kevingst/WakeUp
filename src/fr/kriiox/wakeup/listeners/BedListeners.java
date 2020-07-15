package fr.kriiox.wakeup.listeners;

import fr.kriiox.wakeup.WakeUpMain;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
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

        if(player.getGameMode() == GameMode.SURVIVAL){
            main.playerPlayed.add(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(main.getPlayerSpleep().size() != 0){
            main.updateBossBar();
            main.sleepingBar.removePlayer(player);
        }

        if(main.getPlayerPlayed().contains(player)){
            main.playerPlayed.remove(player);
        }
    }

    @EventHandler
    public void onChangeGameMode(PlayerGameModeChangeEvent event){
        Player player = event.getPlayer();
        GameMode gm = event.getNewGameMode();

        if(gm.equals(GameMode.SURVIVAL)){
            main.playerPlayed.add(player);
        } else {
            if(main.getPlayerPlayed().contains(player)){
                main.playerPlayed.remove(player);
            }
        }

    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event){
        Player playerEvent = event.getPlayer();

        if (event.getBedEnterResult() != PlayerBedEnterEvent.BedEnterResult.OK) return;

        if(!playerEvent.getGameMode().equals(GameMode.SURVIVAL)) return;

        if(!main.getPlayerSpleep().contains(playerEvent)) main.getPlayerSpleep().add(playerEvent);

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

        if(!player.getGameMode().equals(GameMode.SURVIVAL)) return;

        main.getPlayerSpleep().remove(player);
        main.updateBossBar();
    }
}
