package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Ability.AttackTag;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.*;

public class Sans extends Ability {
    private final static String[] des= {
            "와 샌즈",
            ChatColor.YELLOW+"【패시브】 "+ChatColor.GREEN+"독 속성",
            "패시브 능력으로 대상 공격시 3초간 위더에 중독 시킵니다.",
            ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"뼈 Ⅰ",
            "뼈를 던져 피해를 입힙니다.",
            ChatColor.RED+"【고급】 "+ChatColor.AQUA+"가스트 블래스터 Ⅱ",
            "가스트 블래스터를 발사합니다"};

    public Sans(String playerName){
        super(playerName, "샌즈", 19, true, true, true, des);
        Theomachy.log.info(playerName+abilityName);
        this.cool1=1;
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
        if (event.getEntity() instanceof LivingEntity victim) {
            int durationInSeconds = 3 * 20; // 위더 효과 지속 시간 (틱 단위로 20으로 나누어야됨)
            int amplifier = 1; // 위더 효과 강도
            PotionEffect poisonEffect = new PotionEffect(PotionEffectType.WITHER, durationInSeconds, amplifier);
            victim.addPotionEffect(poisonEffect);
        }
    }
    private void leftAction(Player player)
    {
        if ( CoolTimeChecker.Check(player, 1) && PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta1))
        {
            Snowball snowball = player.launchProjectile(Snowball.class);
            snowball.setVelocity(player.getLocation().getDirection().multiply(1.5)); // 조절 가능한 속도
            snowball.addScoreboardTag(AttackTag.BONEATTACK.getTag()); // 뼈 공격을 식별하기 위한 태그 추가
            Skill.Use(player, Material.COBBLESTONE, sta1, 1, cool1);
        }
    }
    private void rightAction(Player player)
    {
        if (CoolTimeChecker.Check(player, 2)&& PlayerInventory.ItemCheck(player, Material.COBBLESTONE, sta2))
        {
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < 800; distance += 0.5) {
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 50);
                for(Entity entity : world.getNearbyEntities(particleLocation,12,12,12)){
                    if (entity instanceof LivingEntity  && !entity.equals(player)){
                        ((LivingEntity)entity).damage(10, player);
                    }
                }
            }
            Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
        }
    }
}
