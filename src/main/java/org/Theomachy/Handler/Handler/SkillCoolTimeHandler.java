package org.Theomachy.Handler.Handler;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Ability.ENUM.AbilityCase;
import org.Theomachy.Timer.CoolTime;
import org.Theomachy.Message.T_Message;

public class SkillCoolTimeHandler
{
	public static void Use(Player player, Material material, AbilityCase abilityCase, int stack, int coolTime)
	{
		player.getInventory().removeItem(new ItemStack(material, stack));
		if (coolTime>0)
            switch (abilityCase) {
				case COMMON -> CoolTime.commonSkillCoolTime.put(player.getName(), coolTime);
				case NORMAL -> CoolTime.normalSkillCoolTime.put(player.getName(), coolTime);
				case RARE -> CoolTime.rareSkillCoolTime.put(player.getName(), coolTime);
            }
		T_Message.Skill_Used(player, abilityCase);
	}
}
