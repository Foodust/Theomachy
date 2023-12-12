package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
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
        this.cool1=5;
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
        Bukkit.broadcastMessage("하이?");
        if (event.getEntity() instanceof LivingEntity victim) {
            Bukkit.broadcastMessage("바이?");
            int durationInSeconds = 150; // 독 효과 지속 시간 (초 단위가 아닌거 같음)
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
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < 160; distance += 0.5) {
                // 플레이어가 바라보는 방향으로 레이저 생성
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 50);
                for(Entity entity : world.getNearbyEntities(particleLocation,10,10,10)){
                    if (entity instanceof LivingEntity  && !entity.equals(player)){
                        ((LivingEntity)entity).damage(5, player);
                    }
                }
            }
        }
    }
}
