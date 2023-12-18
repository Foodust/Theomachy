package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Command.StartStopCommand;
import org.Theomachy.Manager.EntityManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockEvent implements Listener {

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (StartStopCommand.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.PlayerAbility.get(playerName);
            if (ability != null)
                ability.passiveSkill(event);

            Block block = event.getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Bukkit.broadcastMessage(ChatColor.GREEN + event.getPlayer().getName() + ChatColor.WHITE + "에 의해" + "다이아몬드 블럭이 부서졌습니다!");
                Firework firework = event.getPlayer().getWorld().spawn(event.getBlock().getLocation(), Firework.class);
                EntityManager.spawnRandomFirework(firework);
            }
        }
    }


    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (StartStopCommand.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (StartStopCommand.Start) {
            Ability ability = GameData.PlayerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

}
