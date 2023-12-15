package org.septagram.Theomachy.Ability.GOD;

import java.util.List;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import org.septagram.Theomachy.Ability.ENUM.AbilityCase;
import org.septagram.Theomachy.Ability.ENUM.AbilityInfo;
import org.septagram.Theomachy.Ability.ENUM.AbilityRank;
import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Ability.Ability;
import org.septagram.Theomachy.Utility.CoolTimeChecker;
import org.septagram.Theomachy.Utility.EventFilter;
import org.septagram.Theomachy.Utility.GetPlayerList;
import org.septagram.Theomachy.Utility.PlayerInventory;
import org.septagram.Theomachy.Utility.Skill;

public class Aprodite extends Ability{
	
	private final static String[] des= {
			AbilityInfo.Aprodite.getName() +"는 미의 신입니다.",
			  NamedTextColor.AQUA+"【일반】 "+NamedTextColor.WHITE+"매혹",
			   "20블록 이내에 있는 사람들을 끌어올 수 있습니다.",
			   "자신이 블록 위에 서 있고 웅크리지 않아야 발동합니다."};
	
	public Aprodite(String playerName)
	{
		super(playerName, AbilityInfo.Aprodite, true, false, false, des);
		Theomachy.log.info(playerName+abilityName);
		
		this.normalSkillCoolTime =500;
		this.normalSkillStack =64;
		
		this.rank= AbilityRank.S;
	}
	
	public void activeSkill(PlayerInteractEvent event)
	{
		Player player = event.getPlayer();
		if (PlayerInventory.InHandItemCheck(player, Material.BLAZE_ROD))
		{
            switch (EventFilter.PlayerInteract(event)) {
				case LEFT_CLICK_AIR, LEFT_CLICK_BLOCK -> leftAction(player);
            }
		}
	}

	private void leftAction(Player player) {
		if (CoolTimeChecker.Check(player, AbilityCase.NORMAL)&&PlayerInventory.ItemCheck(player, Material.COBBLESTONE, normalSkillStack)) {
			if(!player.isSneaking() && !player.getLocation().add(0, -1, 0).getBlock().getType().equals(Material.AIR)) {
				Skill.Use(player, Material.COBBLESTONE,  AbilityCase.NORMAL, normalSkillStack, normalSkillCoolTime);
				try {
					List<Player> list=GetPlayerList.getNearByNotTeamMembers(player, 20, 20, 20);
					for(Player e:list) {
						Bukkit.getScheduler().runTask(Theomachy.getPlugin(),()->{e.teleport(player);});
						e.sendMessage(NamedTextColor.YELLOW+"미의 여신에게 이끌려갑니다!");
					}
				}catch(Exception ignored) {}
				player.sendMessage("미(美)로 다른 사람들을 홀렸습니다.");
			}else {
				player.sendMessage(NamedTextColor.RED+"웅크리고 있거나 발 밑의 블록이 없어 능력이 발동되지 않았습니다.");
			}
		}

	}
	
}
