package org.Theomachy.Ability.HUMAN;



import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Ability.Ability;

public class Creeper extends Ability {
    private boolean plasma = false;
    private final static String[] des = {
            AbilityInfo.Creeper.getName() + "는 몬스터형 능력입니다.",
            "블레이즈 로드를 통해 능력을 사용하면",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "펑!",
            "크리퍼와 같은 폭발력의 폭발을 일으킵니다.",
            "번개를 맞은 적이 있다면 폭발력이 두 배로 증가합니다.",
            "번개 카운팅은 사망 시 초기화됩니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "크리퍼 강화",
            "자신에게 번개를 발동합니다.",
            "번개 카운팅에 포함됩니다."};

    public Creeper(String playerName) {
        super(playerName, AbilityInfo.Creeper, true, false, false, des);
        messageModule.logInfo(playerName + abilityName);

        this.normalSkillCoolTime = 60;
        this.normalSkillStack = 20;
        this.normalDamage  = 5;
        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 50;

        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player,skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_BLOCK, LEFT_CLICK_AIR -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            World world = player.getWorld();
            Location location = player.getLocation();
            float power = plasma ? normalDamage * 2 : normalDamage;
            player.setHealth(0);
            world.createExplosion(location, power);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            World world = player.getWorld();
            Location location = player.getLocation();
            world.strikeLightning(location);
        }
    }

    public void passiveSkill(EntityDamageEvent event) {
        if (event.getCause() == DamageCause.LIGHTNING) {
            this.plasma = true;
            ((Player) event.getEntity()).sendMessage(ChatColor.AQUA + "플라즈마 크리퍼 모드 활성화!");
        }
    }

    public void passiveSkill(PlayerDeathEvent event) {
        this.plasma = false;
    }
}
