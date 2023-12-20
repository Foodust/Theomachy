package org.Theomachy.Handler.Event;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Display;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.util.Transformation;
import org.bukkit.util.Vector;
import org.joml.AxisAngle4f;

import java.util.Objects;

public class HealthBarEvent implements Listener {

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;
        TextDisplay display = (TextDisplay) entity.getWorld().spawn(entity.getLocation(), TextDisplay.class);
        display.addScoreboardTag("health_bar");
        display.setBillboard(Display.Billboard.CENTER);
        display.setText("§a⬛".repeat((int) entity.getHealth()));
        display.setTransformation(new Transformation(
                new Vector(0.0, 1.0, 0.0).toVector3f(),
                new AxisAngle4f(0.0F, 0.0F, 0.0F, 0.0F),
                new Vector(1.0, 1.0, 1.0).toVector3f(),
                new AxisAngle4f(0.0F, 0.0F, 0.0F, 0.0F)
        ));
        entity.addPassenger(display);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof LivingEntity entity))
            return;
        if (entity.getPassengers().isEmpty())
            return;
        for (Entity passenger : entity.getPassengers()) {
            if (passenger instanceof TextDisplay display) {
                if (entity.getHealth() - event.getDamage() <= 0) {
                    display.remove();
                    return;
                }
                int health = (int) entity.getHealth();
                display.setText("§a⬛".repeat(health - (int) event.getDamage())
                        .concat("§c⬛".repeat(
                                (int) Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue() - (health - (int) event.getDamage()))));
            }
        }
    }
    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity.getScoreboardTags().contains("health_bar") && entity.getVehicle() == null) {
                entity.remove();
            }
        }
    }
}
