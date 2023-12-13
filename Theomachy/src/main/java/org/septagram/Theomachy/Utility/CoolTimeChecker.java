package org.septagram.Theomachy.Utility;

import org.bukkit.entity.Player;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Timer.CoolTime;
import org.septagram.Theomachy.Message.T_Message;

public class CoolTimeChecker
{
	public static boolean Check(Player player, AbilityCase abilityCase)
	{
		String key=player.getName();
		
		if (abilityCase == AbilityCase.COMMON)
		{
			if (CoolTime.COOL0.containsKey(key))
			{
				int cool = CoolTime.COOL0.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.NORMAL)
		{
			if (CoolTime.COOL1.containsKey(key))
			{
				int cool = CoolTime.COOL1.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.RARE)
		{
			if (CoolTime.COOL2.containsKey(key))
			{
				int cool = CoolTime.COOL2.get(key);
				T_Message.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else
			return false;
	}
}
