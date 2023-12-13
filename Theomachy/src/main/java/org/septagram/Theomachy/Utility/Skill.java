package org.septagram.Theomachy.Utility;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Timer.CoolTime;
import org.septagram.Theomachy.Message.T_Message;

public class Skill
{
	public static void Use(Player player, Material material, AbilityCase abilityCase, int stack, int coolTime)
	{
		player.getInventory().removeItem(new ItemStack(material, stack));
		if (coolTime>0)
            switch (abilityCase) {
				case COMMON -> CoolTime.COOL0.put(player.getName(), coolTime);
				case NORMAL -> CoolTime.COOL1.put(player.getName(), coolTime);
				case RARE -> CoolTime.COOL2.put(player.getName(), coolTime);
            }
		T_Message.Skill_Used(player, abilityCase);
	}
}
