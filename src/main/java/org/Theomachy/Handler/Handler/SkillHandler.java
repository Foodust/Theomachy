package org.Theomachy.Handler.Handler;

import org.Theomachy.Theomachy;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.Theomachy.Enum.AbilityCase;
import org.Theomachy.Timer.CoolTimeTimer;
import org.Theomachy.Message.AbilityCoolTimeMessage;

public class SkillHandler
{
	private final AbilityCoolTimeMessage abilityCoolTimeMessage = new AbilityCoolTimeMessage();
	public void Use(Player player, Material material, AbilityCase abilityCase, int stack, int coolTime)
	{
        if(Theomachy.DEBUG)
            return;
		player.getInventory().removeItem(new ItemStack(material, stack));
		if (coolTime>0)
            switch (abilityCase) {
				case COMMON -> CoolTimeTimer.commonSkillCoolTime.put(player.getName(), coolTime);
				case NORMAL -> CoolTimeTimer.normalSkillCoolTime.put(player.getName(), coolTime);
				case RARE -> CoolTimeTimer.rareSkillCoolTime.put(player.getName(), coolTime);
            }
		abilityCoolTimeMessage.SkillUsed(player, abilityCase);
	}
	public boolean Check(Player player, AbilityCase abilityCase)
	{
		String key=player.getName();
        if(Theomachy.DEBUG)
            return true;
        switch (abilityCase) {
            case COMMON -> {
                if (CoolTimeTimer.commonSkillCoolTime.containsKey(key)) {
                    int cool = CoolTimeTimer.commonSkillCoolTime.get(key);
                    abilityCoolTimeMessage.CoolTimeTeller(player, abilityCase, cool);
                    return false;
                } else
                    return true;
            }
            case NORMAL -> {
                if (CoolTimeTimer.normalSkillCoolTime.containsKey(key)) {
                    int cool = CoolTimeTimer.normalSkillCoolTime.get(key);
                    abilityCoolTimeMessage.CoolTimeTeller(player, abilityCase, cool);
                    return false;
                } else
                    return true;
            }
            case RARE -> {
                if (CoolTimeTimer.rareSkillCoolTime.containsKey(key)) {
                    int cool = CoolTimeTimer.rareSkillCoolTime.get(key);
                    abilityCoolTimeMessage.CoolTimeTeller(player, abilityCase, cool);
                    return false;
                } else
                    return true;
            }
            default -> {
                return false;
            }
        }
	}
}
