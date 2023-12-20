package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Command.StartStopCommand;
import org.Theomachy.Handler.Module.GameModule;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class ExplodeEvent implements Listener {

    @EventHandler
    public static void onEntityExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (GameModule.Start && entity.getType() == EntityType.FIREBALL)
            event.blockList().clear();

        if (GameModule.Start) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Ability ability = GameData.playerAbility.get(p.getName());
                if (ability != null && ability.abilityCode == AbilityInfo.Bulter.getIndex()) {
                    ability.passiveSkill(event);
                }
            }
        }
    }
}
