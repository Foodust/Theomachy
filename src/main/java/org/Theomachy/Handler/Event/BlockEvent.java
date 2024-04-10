package org.Theomachy.Handler.Event;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Handler.Module.source.CommonModule;
import org.Theomachy.Handler.Module.game.GameModule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockEvent  implements Listener {
    private final CommonModule commonModule = new CommonModule();
    private final GameModule gameModule = new GameModule();
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (GameModule.Start) {
            String playerName = event.getPlayer().getName();
            Ability ability = GameData.playerAbility.get(playerName);
            if (ability != null)
                ability.passiveSkill(event);

            Block block = event.getBlock();
            if (block.getType() == Material.DIAMOND_BLOCK) {
                commonModule.breakDiamond(event);
                gameModule.stopGame();
            }
        }
    }
    @EventHandler
    public void onSignChange(SignChangeEvent event) {
        if (GameModule.Start) {
            Ability ability = GameData.playerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (GameModule.Start) {
            Ability ability = GameData.playerAbility.get(event.getPlayer().getName());
            if (ability != null && ability.abilityCode == AbilityInfo.Voodoo.getIndex())
                ability.passiveSkill(event);
        }
    }
}
