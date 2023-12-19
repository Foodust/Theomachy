package org.Theomachy.Handler.Handler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Message.AbilityCoolTimeMesage;

public class SkillHandler
{
	public static void Use(Player player, Material material, AbilityCase abilityCase, int stack, int coolTime)
	{
		player.getInventory().removeItem(new ItemStack(material, stack));
		if (coolTime>0)
            switch (abilityCase) {
				case COMMON -> CoolTimeTimer.commonSkillCoolTime.put(player.getName(), coolTime);
				case NORMAL -> CoolTimeTimer.normalSkillCoolTime.put(player.getName(), coolTime);
				case RARE -> CoolTimeTimer.rareSkillCoolTime.put(player.getName(), coolTime);
            }
		AbilityCoolTimeMesage.Skill_Used(player, abilityCase);
	}
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
