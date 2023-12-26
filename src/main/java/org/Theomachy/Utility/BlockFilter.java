package org.Theomachy.Utility;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.Theomachy.Enum.TargetType;
import org.Theomachy.Message.AbilityCoolTimeMessage;
import org.checkerframework.checker.units.qual.A;


public class BlockFilter
{
	private final AbilityCoolTimeMessage abilityCoolTimeMessage = new AbilityCoolTimeMessage();
	public boolean AirToFar(Player player, Block block)
	{
		if (block.getType() != Material.AIR)
			return true;
		else
		{
			abilityCoolTimeMessage.TooFarError(player, TargetType.TARGET_TOO_FAR);
			return false;
		}
	}
}
