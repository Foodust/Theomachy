package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Command.StartStopCommand;
import org.Theomachy.Handler.Module.CommonModule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockEvent implements Listener {

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        if (StartStopCommand.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.playerAbility.get(playerName);
            if (ability != null)
                ability.passiveSkill(event);

            Block block = event.getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                CommonModule.breakDiamond(event);
            }
        }
    }
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (StartStopCommand.Start) {
            Ability ability = GameData.playerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (StartStopCommand.Start) {
            Ability ability = GameData.playerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

}
