package org.septagram.Theomachy.Ability.HUMAN;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Midoriya extends Ability {

    public final static String[] des = {
            AbilityInfo.Midoriya.getName() + "는 UA에 재학 중입니다.",
            NamedTextColor.AQUA + "【일반】 " + NamedTextColor.WHITE + "원 포 올",
            "능력 사용 후 상대를 가격하면 원 포 올을 쓸 수 있습니다.",
            "원 포 올을 쓰고 난 뒤에는 각종 디버프에 시달립니다."};

    private boolean Ready;
    private final int duration;

    public Midoriya(String playerName) {
        super(playerName, AbilityInfo.Midoriya, true, false, false, des);
        this.rank = AbilityRank.S;
        this.normalSkillCoolTime = 350;
        this.normalSkillStack = 64;
        this.Ready = false;
        this.duration = 10;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack) && !Ready) {
            Ready = true;
            player.sendMessage(NamedTextColor.YELLOW + "원" + NamedTextColor.BLUE + " 포 " + NamedTextColor.DARK_PURPLE + "올" + NamedTextColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            player.sendMessage(NamedTextColor.RED + "원" + NamedTextColor.GRAY + " 포 " + NamedTextColor.LIGHT_PURPLE + "올" + NamedTextColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            player.sendMessage(NamedTextColor.AQUA + "원" + NamedTextColor.GOLD + " 포 " + NamedTextColor.DARK_GRAY + "올" + NamedTextColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
            player.sendMessage(NamedTextColor.WHITE + "원" + NamedTextColor.BLACK + " 포 " + NamedTextColor.GREEN + "올" + NamedTextColor.WHITE + "이 준비되었습니다아!!!!!!!!!");
        }
    }

    public void passiveSkill(EntityDamageByEntityEvent event) {
        Player player = (Player) event.getDamager();
        Player d = (Player) event.getEntity();

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR && player.getName().equals(this.playerName)) {
            if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
                if (Ready) {
                    player.sendMessage(NamedTextColor.YELLOW + "원" + NamedTextColor.GREEN + " 포 " + NamedTextColor.AQUA + "올" + NamedTextColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    player.sendMessage(NamedTextColor.RED + "원" + NamedTextColor.GRAY + " 포 " + NamedTextColor.LIGHT_PURPLE + "올" + NamedTextColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    player.sendMessage(NamedTextColor.AQUA + "원" + NamedTextColor.GOLD + " 포 " + NamedTextColor.DARK_GRAY + "올" + NamedTextColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    player.sendMessage(NamedTextColor.WHITE + "원" + NamedTextColor.BLACK + " 포 " + NamedTextColor.DARK_BLUE + "올" + NamedTextColor.WHITE + "이 가동되었습니다아!!!!!!!!!");
                    d.damage(200);
                    player.getWorld().strikeLightningEffect(d.getLocation());

                    player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, duration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, duration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, duration * 20, 0));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, duration * 20, 0));

                    Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
                    Ready = false;
                } else {
                    player.sendMessage("아직 원 포 올의 준비가 되어있지 않군요.");
                }
            }
        }

    }

}
