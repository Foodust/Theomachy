package org.Theomachy.Utility.Checker;

import org.bukkit.entity.Player;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Message.AbilityCoolTimeMesage;

public class CoolTimeChecker
{
	public static boolean Check(Player player, AbilityCase abilityCase)
	{
		String key=player.getName();
		
		if (abilityCase == AbilityCase.COMMON)
		{
			if (CoolTimeTimer.commonSkillCoolTime.containsKey(key))
			{
				int cool = CoolTimeTimer.commonSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.NORMAL)
		{
			if (CoolTimeTimer.normalSkillCoolTime.containsKey(key))
			{
				int cool = CoolTimeTimer.normalSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else if (abilityCase == AbilityCase.RARE)
		{
			if (CoolTimeTimer.rareSkillCoolTime.containsKey(key))
			{
				int cool = CoolTimeTimer.rareSkillCoolTime.get(key);
				AbilityCoolTimeMesage.CoolTimeTeller(player, abilityCase, cool);
				return false;
			}
			else
				return true;
		}
		else
			return false;
	}
}
