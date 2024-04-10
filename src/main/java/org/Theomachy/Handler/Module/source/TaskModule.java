package org.Theomachy.Handler.Module.source;

import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TaskModule {

    public BukkitTask runBukkitTaskLater(Runnable task, Long delay){
        return Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), task, delay);
    }
    public BukkitTask runBukkitTaskLater(Runnable task, double delay){
        return Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), task, (long)delay);
    }
    public BukkitTask runBukkitTaskLater(Runnable task, float delay){
        return Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), task, (long)delay);
    }
    public void runBukkitTask(Runnable task){
        Bukkit.getScheduler().runTask(Theomachy.getPlugin(), task);
    }
    public BukkitTask runBukkitTaskTimer(Runnable task, Long delay, Long tick){
        return Bukkit.getScheduler().runTaskTimer(Theomachy.getPlugin(), task, delay,tick);
    }
    public void cancelBukkitTask(BukkitTask bukkitTask){
        Bukkit.getScheduler().cancelTask(bukkitTask.getTaskId());
    }
}
