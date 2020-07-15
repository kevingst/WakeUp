package fr.kriiox.wakeup;

import fr.kriiox.wakeup.listeners.BedListeners;
import fr.kriiox.wakeup.task.AccelerateNightTask;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class WakeUpMain extends JavaPlugin {

    private final List<Player> playerSpleep = new ArrayList<>();
    public List<Player> playerPlayed = new ArrayList<>();
    public BossBar sleepingBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID, new BarFlag[0]);


    @Override
    public void onEnable() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new BedListeners(this), this);
    }

    public void skipNight(){
        AccelerateNightTask accelerateNightTask = new AccelerateNightTask();
        accelerateNightTask.runTaskTimer(this, 1, 1);
    }

    public double getSleepingValue(){
        float valueInPercentage = (float) this.getPlayerSpleep().size() / getPlayerPlayed().size()*100;
        return (double) Math.round((valueInPercentage/100) * 100) / 100;
    }

    public void updateBossBar(){
        sleepingBar.setTitle("Passer la nuit [ "+getPlayerSpleep().size() + " / " + getPlayerPlayed().size()+" ]");
        sleepingBar.setProgress(getSleepingValue());

        if(getPlayerSpleep().size() == 0){
            sleepingBar.removeAll();
        }
    }

    public List<Player> getPlayerSpleep() {
        return playerSpleep;
    }

    public List<Player> getPlayerPlayed() {
        return playerPlayed;
    }
}
