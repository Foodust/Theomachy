package org.Theomachy.Manager;

import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import javax.xml.stream.Location;
import java.util.Random;

public class EntityManager {
    public static void spawnRandomFirework(Firework firework) {
        Random random = new Random();
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        FireworkEffect.Type type = FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
        FireworkEffect effect = FireworkEffect.builder()
                .flicker(random.nextBoolean())
                .withColor(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .withFade(Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256)))
                .with(type)
                .trail(random.nextBoolean())
                .build();
        fireworkMeta.addEffect(effect);
        fireworkMeta.setPower(3); // 폭죽의 세기 설정 (1 ~ 3)
        firework.setFireworkMeta(fireworkMeta);
    }

}
