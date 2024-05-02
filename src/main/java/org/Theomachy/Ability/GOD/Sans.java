package org.Theomachy.Ability.GOD;

import com.ticxo.modelengine.api.ModelEngineAPI;
import com.ticxo.modelengine.api.animation.handler.AnimationHandler;
import com.ticxo.modelengine.api.animation.handler.IStateMachineHandler;
import com.ticxo.modelengine.api.entity.Dummy;
import com.ticxo.modelengine.api.model.ActiveModel;
import com.ticxo.modelengine.api.model.ModeledEntity;
import com.ticxo.modelengine.api.model.bone.BoneBehaviorTypes;
import com.ticxo.modelengine.api.model.bone.behavior.BoneBehaviorType;
import com.ticxo.modelengine.api.model.bone.type.Head;
import com.ticxo.modelengine.api.model.bone.type.HeldItem;
import com.ticxo.modelengine.api.mount.controller.MountControllerType;
import com.ticxo.modelengine.api.mount.controller.MountControllerTypes;
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
import org.bukkit.util.Transformation;
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
            // effect lib
//            ColoredImageEffect imageEffect = new ColoredImageEffect(effectManage);
//            imageEffect.loadFile(new File("image.png"));
//            imageEffect.frameDelay = 5 * 20;
//            imageEffect.visibleRange = 100f;
//            imageEffect.setLocation(player.getLocation().add(player.getLocation().getDirection().multiply(3).add(new Vector(0, 5, 0))));
//            imageEffect.enableRotation = false;
//            imageEffect.start();

            // model engine
            Pig pig = player.getWorld().spawn(player.getLocation(), Pig.class);
            ModeledEntity modeledEntity = ModelEngineAPI.createModeledEntity(pig);
            ActiveModel activeModel = ModelEngineAPI.createActiveModel("test_model");
            modeledEntity.addModel(activeModel, true);
            AnimationHandler animationHandler = activeModel.getAnimationHandler();
            animationHandler.playAnimation("attack", 0.3, 0.3, 1, true);

            //https://www.youtube.com/watch?v=8fKEG2Pj1vQ
            //display entity
            ItemDisplay item = player.getWorld().spawn(player.getLocation(), ItemDisplay.class);
            Transformation transformation = item.getTransformation();
            transformation.getScale().set(1);
            item.setItemStack(new ItemStack(Material.DIAMOND));
            item.setTransformation(transformation);
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
