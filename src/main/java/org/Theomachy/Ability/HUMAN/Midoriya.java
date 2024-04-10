package org.Theomachy.Ability.HUMAN;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;


public class Midoriya extends Ability {

    public final static String[] des = {
            AbilityInfo.Midoriya.getName() + "는 UA에 재학 중입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "원 포 올",
            "능력 사용 후 상대를 가격하면 원 포 올을 쓸 수 있습니다.",
            "원 포 올을 쓰고 난 뒤에는 각종 디버프에 시달립니다."};

    private boolean skillReady;

    public Midoriya(String playerName) {
        super(playerName, AbilityInfo.Midoriya, true, false, false, des);
        this.rank = AbilityRank.S;
        this.normalSkillCoolTime = 350;
        this.normalSkillStack = 64;
        this.skillReady = false;
        this.normalDuration = 10;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack) && !skillReady) {
            skillReady = true;
            messageModule.sendPlayer(player,ChatColor.YELLOW + "원" + ChatColor.BLUE + " 포 " + ChatColor.DARK_PURPLE + "올" + ChatColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            messageModule.sendPlayer(player,ChatColor.RED + "원" + ChatColor.GRAY + " 포 " + ChatColor.LIGHT_PURPLE + "올" + ChatColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            messageModule.sendPlayer(player,ChatColor.AQUA + "원" + ChatColor.GOLD + " 포 " + ChatColor.DARK_GRAY + "올" + ChatColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            messageModule.sendPlayer(player,ChatColor.WHITE + "원" + ChatColor.BLACK + " 포 " + ChatColor.GREEN + "올" + ChatColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        Player enemy = (Player) event.getEntity();

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getName().equals(this.playerName)) {
            if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
                if (skillReady) {
                    messageModule.broadcastMessage(ChatColor.YELLOW + "원" + ChatColor.GREEN + " 포 " + ChatColor.AQUA + "올" + ChatColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    messageModule.broadcastMessage(ChatColor.RED + "원" + ChatColor.GRAY + " 포 " + ChatColor.LIGHT_PURPLE + "올" + ChatColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    messageModule.broadcastMessage(ChatColor.AQUA + "원" + ChatColor.GOLD + " 포 " + ChatColor.DARK_GRAY + "올" + ChatColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    messageModule.broadcastMessage(ChatColor.WHITE + "원" + ChatColor.BLACK + " 포 " + ChatColor.DARK_BLUE + "올" + ChatColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    enemy.damage(200);
                    player.getWorld().strikeLightningEffect(enemy.getLocation());

                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, normalDuration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, normalDuration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, normalDuration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, normalDuration * 20, 0));

                    skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                    skillReady = false;
                } else {
                    messageModule.sendPlayer(player,"아직 원 포 올의 준비가 되어있지 않군요.");
                }
            }
        }

    }

}
