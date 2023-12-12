package org.septagram.Theomachy.Ability.GOD;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.septagram.Theomachy.Ability.Ability;
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
            switch(EventFilter.PlayerInteract(event))
            {
                case 0:case 1:
                leftAction(player);
                break;
                case 2:case 3:
                rightAction(player);
                break;
            }
        }
    }
    public void T_Passive(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();
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
            Block block = player.getTargetBlock(null, 30);
            if (BlockFilter.AirToFar(player, block))
            {
                Skill.Use(player, Material.COBBLESTONE, sta2, 2, cool2);
                World world = player.getWorld();
                Location location = block.getLocation();
                Random random = new Random();
                for (int i=0; i<5; i++)
                {
                    int X = random.nextInt(11)-5;
                    int Z = random.nextInt(11)-5;
                    location.add(X, 0, Z);
                    world.strikeLightning(location);
                    location.add(-X, 0, -Z);
                }
            }
        }
    }
}
