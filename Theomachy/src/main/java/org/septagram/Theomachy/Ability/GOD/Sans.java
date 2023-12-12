package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.*;

import java.util.Random;

public class Sans extends Ability {
    private final static String[] des= {
            "와 샌즈",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.GREEN+"독 속성",
            "패시브 능력으로 대상 공격시 5초간 독에 중독 시킵니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"뼈 Ⅰ",
            "뼈를 던져 피해를 입힙니다.",
            ChatColor.RED+"【고급】 "+ChatColor.AQUA+"가스트 블래스터 Ⅱ",
            "가스트 블래스터를 발사합니다"};

    public Sans(String playerName){
        super(playerName, "샌즈", 19, true, true, true, des);
        Theomachy.log.info(playerName+abilityName);
        this.cool1=10;
        this.cool2=120;
        this.sta1=3;
        this.sta2=30;

        this.rank=4;
    }
    public void T_Active(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
        {
            switch (EventFilter.PlayerInteract(event)) {
                case 0, 1 -> leftAction(player);
                case 2, 3 -> rightAction(player);
            }
        }
    }
    public void T_Passive(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player victim) {
            int durationInSeconds = 10; // 독 효과 지속 시간 (초 단위)
            int amplifier = 1; // 독 효과 강도
            PotionEffect poisonEffect = new PotionEffect(PotionEffectType.POISON, durationInSeconds, amplifier);
            victim.addPotionEffect(poisonEffect);
        }

    }
    private void leftAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 1)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
        {
            // 플레이어가 뼈(여기서는 Snowball)를 생성하여 던집니다.
            Snowball snowball = player.launchProjectile(Snowball.class);
            // 던져진 뼈의 속도와 방향을 설정합니다. 예시로 30 블록 앞으로 던지도록 설정했습니다.
            snowball.setVelocity(player.getLocation().getDirection().multiply(1.5)); // 조절 가능한 속도
            // 뼈가 맞은 대상에게 피해를 입히는 이벤트 처리
            snowball.addScoreboardTag("boneAttack"); // 뼈 공격을 식별하기 위한 태그 추가
        }
    }
    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 2)&& PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
        {
            Location startLocation = player.getEyeLocation();
            World world = player.getWorld();
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 1);
            for (int i = 0; i < 50; i++) {
                Vector direction = startLocation.getDirection().multiply(i * 0.1);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.WHITE_ASH, particleLocation, 1, dustOptions);
            }
            // 2초 뒤에 생성된 레이저 파티클 모두 제거
            Bukkit.getScheduler().runTaskLater((Plugin) this, () -> {
                for (int i = 0; i < 50; i++) {
                    Vector direction = startLocation.getDirection().multiply(i * 0.1);
                    Location particleLocation = startLocation.clone().add(direction);
                    world.spawnParticle(Particle.WHITE_ASH, particleLocation, 1, dustOptions);
                }
            }, 40L); // 40 틱 (2초)
//            Location startLocation = player.getEyeLocation();
//            World world = player.getWorld();
//            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.WHITE, 1);
//            for (int i = 0; i < 50; i++) {
//                Vector direction = startLocation.getDirection().multiply(i * 0.1);
//                Location particleLocation = startLocation.clone().add(direction);
//                world.spawnParticle(Particle.WHITE_ASH, particleLocation, 1, dustOptions);
//                // 레이저 파티클 위치 주변의 플레이어를 확인하고 데미지를 입히는 로직
//                for (Player target : Bukkit.getOnlinePlayers()) {
//                    if (target.equals(player)) continue; // 레이저를 발사한 플레이어는 데미지를 받지 않도록 제외
//                    Location targetLocation = target.getLocation();
//                    double distance = particleLocation.distance(targetLocation);
//                    if (distance < 1.0) { // 파티클과 플레이어의 거리가 1 블록 이내라면 데미지 입히기
//                        target.damage(10.0); // 원하는 데미지 값 설정
//                    }
//                }
//            }
        }
    }
}
