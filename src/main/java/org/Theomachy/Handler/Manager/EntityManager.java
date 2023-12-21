package org.Theomachy.Handler.Manager;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.Random;

public class EntityManager {
    public void spawnRandomFirework(Firework firework) {
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
