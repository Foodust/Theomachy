package org.Theomachy.Ability.JUJUTSU_KAISEN;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Ability.ENUM.AbilityInfo;
import org.Theomachy.Ability.ENUM.AbilityRank;
import org.Theomachy.Theomachy;
import org.Theomachy.Utility.CoolTimeChecker;
import org.Theomachy.Utility.EventFilter;
import org.Theomachy.Utility.PlayerInventory;
import org.Theomachy.Utility.Skill;

import java.util.HashMap;
import java.util.Map;

public class Jogo extends Ability {
    private final static String[] des = {
            AbilityInfo.Jogo.getName(),
            ChatColor.YELLOW + "【패시브】 " + ChatColor.GREEN + "불 속성",
            "불 데미지에 면역입니다.",
            ChatColor.AQUA + "【일반】 " + ChatColor.WHITE + "화력충",
            "전방에 화력충(화염구)를 보냅니다.",
            ChatColor.RED + "【고급】 " + ChatColor.WHITE + "영역 전개 | 개관철위산 (蓋棺鉄囲山)",
            "영역에 상대방을 가두고 불에 태웁니다. 자신은 불에 면역입니다."};

    private final int normalDamage;
    private final int rareDistance;
    private final int rareDuration;
    private boolean rareIs;

    public Jogo(String playerName) {
        super(playerName, AbilityInfo.Jogo, true, true, false, des);
        Theomachy.log.info(playerName + abilityName);
        this.normalSkillCoolTime = 30;
        this.normalSkillStack = 15;
        this.normalDamage = 3;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 60;
        this.rareDistance = 15;
        this.rareDuration = 10;
        this.rank = AbilityRank.A;
    }

    public void passiveSkill(EntityDamageEvent event) {
            Player player = (Player) event.getEntity();
            EntityDamageEvent.DamageCause dc = event.getCause();
            if (dc.equals(EntityDamageEvent.DamageCause.LAVA) ||
                    dc.equals(EntityDamageEvent.DamageCause.FIRE) ||
                    dc.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
                event.setCancelled(true);
                player.setFireTicks(0);
            }
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD)) {
            switch (EventFilter.PlayerInteract(event)) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.NORMAL) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            Fireball fireball = player.launchProjectile(Fireball.class, player.getEyeLocation().getDirection());
            fireball.setShooter(player);
            fireball.setYield(normalDamage); // 화염구의 피해량 설정 (원하는 값으로 변경 가능)
            fireball.setIsIncendiary(false); // 대상을 불태우는 여부 설정
        }
    }

    // 화뢰신 만들다가 포기함
    private void rightAction(Player player) {
        if (CoolTimeChecker.Check(player, AbilityCase.RARE) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            Skill.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location centerLocation = player.getLocation().add(0, -1, 0); // 발밑 기준으로 블록을 생성할 위치
            Map<Location, Material> originalBlocks = new HashMap<>();

            // 벽 생성
            for (int x = -rareDistance; x <= rareDistance; x++) {
                for (int z = -rareDistance; z <= rareDistance; z++) {
                    for (int y = 1; y <= rareDistance; y++) {
                        Location blockLocation = centerLocation.clone().add(x, y, z);
                        Block block = blockLocation.getBlock();
                        originalBlocks.put(blockLocation, block.getType());
                        if (Math.abs(x) == rareDistance || Math.abs(z) == rareDistance  || y == rareDistance) {
                            // 상자의 외부면에 해당하는 블록을 네더 블록으로 설정
                            block.setType(Material.MAGMA_BLOCK);
                        } else {
                            // 상자 내부를 비움
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
            // 바닥 생성
            for (int x = -rareDistance+1; x <= rareDistance-1; x++) {
                for (int z = -rareDistance+1; z <= rareDistance-1; z++) {
                    Location blockLocation = centerLocation.clone().add(x, 0, z);
                    Location blockLocationFire = centerLocation.clone().add(x, 1, z);
                    Block block = blockLocation.getBlock();
                    originalBlocks.put(blockLocation, block.getType());
                    blockLocation.getBlock().setType(Material.NETHERRACK);
                    blockLocationFire.getBlock().setType(Material.FIRE);
                }
            }


            Bukkit.getScheduler().runTaskLater(Theomachy.getPlugin(), () -> {
                for (Map.Entry<Location, Material> entry : originalBlocks.entrySet()) {
                    Location loc = entry.getKey();
                    Material originalType = entry.getValue();
                    loc.getBlock().setType(originalType); // 원래의 블록 타입으로 되돌림
                }
            }, rareDuration * 20L); // 10초 후 (20틱 = 1초 * 10초 = 200틱)
        }
    }
}
