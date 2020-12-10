package fr.kriiox.wakeup;

import fr.kriiox.wakeup.listeners.BedListeners;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class WakeUpMain extends JavaPlugin {

    public List<Player> playerSpleep = new ArrayList<>();
    public List<Player> playerCanSleep = new ArrayList<>();
    public Map<UUID, Long> playerMove = new HashMap<>();
    public List<Player> playerAFK = new ArrayList<>();
    public BossBar sleepingBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID, new BarFlag[0]);

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BedListeners(this), this);

        playerAFK.clear();

        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                checkPlayerMove(player);
            }
        }, 0, 20);
    }

    private void checkPlayerMove(Player player) {
        if(playerMove.containsKey(player.getUniqueId())) {
            long ms = playerMove.get(player.getUniqueId());
            if (System.currentTimeMillis() - ms >= getConfig().getInt("afk.time")) {
                if(!playerAFK.contains(player)) {
                    playerAFK.add(player);
                    playerCanSleep.remove(player);
                    player.setPlayerListName("§7[AFK] "+player.getName());
                    Bukkit.broadcastMessage("§b" + player.getName() + " §f"+getConfig().getString("afk.isAfk"));
                }
            }
        }
    }

    public void skipNight(){
        //AccelerateNightTask accelerateNightTask = new AccelerateNightTask();
        //accelerateNightTask.runTaskTimer(this, 1, 1);
    }

    public double getSleepingValue(){
        float valueInPercentage = (float) this.getPlayerSpleep().size() / getPlayerCanSleep().size()*100;
        return (double) Math.round((valueInPercentage/100) * 100) / 100;
    }

    public void updateBossBar(){
        sleepingBar.setTitle(getConfig().getString("bossbar-message") + " [" +getPlayerSpleep().size() + " / " + getPlayerCanSleep().size()+"]");
        sleepingBar.setProgress(getSleepingValue());

        if(getPlayerSpleep().size() == 0){ sleepingBar.removeAll(); }
    }

    public List<Player> getPlayerSpleep() { return playerSpleep; }

    public List<Player> getPlayerCanSleep() {
        return playerCanSleep;
    }

    public void updatePlayerCanSleep(){
        World world = Bukkit.getWorld("world");
        playerCanSleep.clear();

        for(Player player : world.getPlayers()) {
            playerCanSleep.add(player);
            if(player.getGameMode() != GameMode.SURVIVAL) {
                playerCanSleep.remove(player);
            }
            if (playerAFK.contains(player)){
                playerCanSleep.remove(player);
            }
        }
    }

}
