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
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

public class Miner extends Ability {

    private final static String[] des = {
            AbilityInfo.Miner.getName() + "는 곡괭이를 능숙하게 다룰 수 있습니다.",
            ChatColor.YELLOW + "【패시브】 " + ChatColor.WHITE + "효율적 광업",
            "코블스톤을 캘 때 일정 3% 확률로 한 번에 10개를 얻을 수 있습니다.",
            "금곡괭이를 제외한 곡괭이들의 데미지가 4로 고정됩니다."};

    public Miner(String playerName) {
        super(playerName, AbilityInfo.Miner, false, true, false, des);
        messageModule.logInfo(playerName + abilityName);
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
                messageModule.sendPlayer(player,"손맛이 느껴집니다!");
                world.dropItemNaturally(location, new ItemStack(Material.COBBLESTONE, 9));
            }
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        if (player.getName().equals(playerName)) {
            Material m = player.getInventory().getItemInMainHand().getType();
            if (m.equals(Material.WOODEN_PICKAXE) || m.equals(Material.STONE_PICKAXE) || m.equals(Material.IRON_PICKAXE) || m.equals(Material.DIAMOND_PICKAXE)) {
                event.setDamage(4);
            }
        }
    }
}
