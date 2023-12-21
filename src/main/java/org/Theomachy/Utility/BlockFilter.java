package org.Theomachy.Utility;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import org.Theomachy.Enum.TargetType;
import org.Theomachy.Message.AbilityCoolTimeMessage;


public class BlockFilter
{
	public static boolean AirToFar(Player player, Block block)
	{
		if (block.getType() != Material.AIR)
			return true;
		else
		{
			AbilityCoolTimeMessage.TooFarError(player, TargetType.TARGET_TOO_FAR);
			return false;
		}
	}
}
