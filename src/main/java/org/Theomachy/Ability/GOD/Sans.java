package org.Theomachy.Ability.GOD;


import com.ticxo.modelengine.api.ModelEngineAPI;
import de.slikey.effectlib.effect.ColoredImageEffect;
import de.slikey.effectlib.effect.ImageEffect;
import org.bukkit.ChatColor;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;
import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityTag;
import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;
import org.Theomachy.Theomachy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class Sans extends Ability {
    private final static String[] des = {
            "와 " + AbilityInfo.Sans.getName(),
            ChatColor.RED + "【고급】 " + ChatColor.AQUA + "가스트 블래스터 Ⅱ",
            "가스트 블래스터를 발사합니다"};

    public Sans(String playerName) {
        super(playerName, AbilityInfo.Sans, true, false, true, des);
        messageModule.logInfo(playerName + abilityName);
        this.normalSkillCoolTime = 1;
        this.normalSkillStack = 3;

        this.rareSkillCoolTime = 120;
        this.rareSkillStack = 30;
        this.rareDamage = 10;

        this.passiveDuration = 3;
        this.rareDistance = 800;
        this.rank = AbilityRank.S;
    }

    public void activeSkill(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (playerModule.InHandItemCheck(player, skillMaterial)) {
            switch (event.getAction()) {
                case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
                case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> rightAction(player);
            }
        }
    }

    private void leftAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.NORMAL) && playerModule.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
            ColoredImageEffect imageEffect = new ColoredImageEffect(effectManage);
            imageEffect.loadFile(new File("image3.png"));
            imageEffect.frameDelay = 5 * 20;
//            imageEffect.stepX = 15;
//            imageEffect.stepY = 15;
            imageEffect.visibleRange = 100f;
            imageEffect.setLocation(player.getLocation().add(player.getLocation().getDirection().multiply(3).add(new Vector(0, 5, 0))));
            imageEffect.enableRotation = false;
            imageEffect.start();

            // 이미지 지도 그리기
//            ItemStack item = new ItemStack(Material.FILLED_MAP, 1);
//            MapView map = Bukkit.getServer().createMap(Bukkit.getServer().getWorlds().get(0));
//            for (MapRenderer render : map.getRenderers()) {
//                map.removeRenderer(render);
//            }
//            map.addRenderer(new MapRenderer() {
//                @Override
//                public void render(MapView map, MapCanvas canvas, Player player) {
//                    try {
//                        File file = new File("C:\\Users\\Admin\\Desktop\\게임\\server1.20.2\\plugins\\Theomachy\\image2.png");
//                        Image image = ImageIO.read(file);
////                        BufferedImage photo = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
//                        BufferedImage photo = new BufferedImage(1920,1080, BufferedImage.TYPE_INT_ARGB);
//                        photo.getGraphics().drawImage(image, 0, 0, null);
//                        canvas.drawImage(0, 0, image);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//            });
//
//            MapMeta meta = ((MapMeta) item.getItemMeta());
//            assert meta != null;
//            meta.setMapView(map);
//            item.setItemMeta(meta);
//            player.getInventory().addItem(item);
        }
    }

    private void rightAction(Player player) {
        if (skillHandler.Check(player, AbilityCase.RARE) && playerModule.ItemCheck(player, Material.COBBLESTONE, rareSkillStack)) {
            skillHandler.Use(player, Material.COBBLESTONE, AbilityCase.RARE, rareSkillStack, rareSkillCoolTime);
            Location startLocation = player.getEyeLocation(); // 플레이어의 눈 위치 가져오기
            World world = player.getWorld();
            for (double distance = 0; distance < rareDistance; distance += 0.5) {
                Vector direction = startLocation.getDirection().multiply(distance);
                Location particleLocation = startLocation.clone().add(direction);
                world.spawnParticle(Particle.DRAGON_BREATH, particleLocation, 500);
                playerModule.damageNearEntity(player, particleLocation, rareDamage, 12, 12, 12);
            }
        }
    }
}
