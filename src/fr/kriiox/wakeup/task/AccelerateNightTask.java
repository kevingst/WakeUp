package fr.kriiox.wakeup.task;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class AccelerateNightTask extends BukkitRunnable {

    @Override
    public void run() {
        World world = Bukkit.getWorld("world");
        long timeWorld = world.getTime();
        double timeRate = 70;

        if(timeWorld >= (1200 - timeRate * 1.5) && timeWorld <= 1200) {
            if(world.hasStorm()){
                world.setStorm(false);
            }
            this.cancel();
        } else {
            world.setTime(timeWorld + (int) timeRate);
        }
    }
}
