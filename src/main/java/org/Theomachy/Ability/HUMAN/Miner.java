package org.Theomachy.Ability.HUMAN;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Ability.Ability;
import org.Theomachy.ENUM.AbilityInfo;
import org.Theomachy.ENUM.AbilityRank;
import org.Theomachy.Theomachy;

public class Miner extends Ability {

    private final static String[] des = {
            AbilityInfo.Miner.getName() + "는 곡괭이를 능숙하게 다룰 수 있습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "효율적 광업",
            "코블스톤을 캘 때 일정 3% 확률로 한 번에 10개를 얻을 수 있습니다.",
            "금곡괭이를 제외한 곡괭이들의 데미지가 4로 고정됩니다."};

    public Miner(String playerName) {
        super(playerName, AbilityInfo.Miner, false, true, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.COBBLESTONE) {
            Location location = block.getLocation();
            World world = event.getPlayer().getWorld();
            Random random = new Random();
            if (random.nextInt(33) == 0) {
                Player player = event.getPlayer();
                player.sendMessage("손맛이 느껴집니다!");
                world.dropItemNaturally(location, new ItemStack(Material.COBBLESTONE, 9));
            }
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player p = (Player) event.getDamager();
        if (p.getName().equals(playerName)) {
            Material m = p.getInventory().getItemInMainHand().getType();
            if (m.equals(Material.WOODEN_PICKAXE) || m.equals(Material.STONE_PICKAXE) || m.equals(Material.IRON_PICKAXE) || m.equals(Material.DIAMOND_PICKAXE)) {
                event.setDamage(4);
            }
        }
    }
}
