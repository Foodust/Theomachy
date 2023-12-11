package septagram.Theomachy.Ability.HUMAN;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import septagram.Theomachy.Ability.Ability;
import septagram.Theomachy.DB.GameData;
import septagram.Theomachy.Utility.CoolTimeChecker;
import septagram.Theomachy.Utility.PlayerInventory;
import septagram.Theomachy.Utility.Skill;

public class Voodoo extends Ability
{
	private final int coolTime0=180;
	private final Material material=Material.COBBLESTONE;
	private final int stack0=5;
	private String targetName=null;
	private Block postSign=null;
	private final static String[] des= {
			   "부두술사는 팻말을 이용해서 상대를 타격할 수 있는 능력입니다.",
			   ChatColor.AQUA+"【일반】 "+ChatColor.WHITE+"부두술",
			   "팻말에 타격할 상대의 이름을 적으면 ",
			   "그 아이디를 가진 플레이어는 팻말과 연결되며" ,
			   "팻말을 두들겨 팰시 대상 플레이어가 데미지를 입습니다." ,
			   "설치후 7초동안 효과가 지속되며 7초 후에 자동으로 팻말이 부숴집니다." ,
			   "데미지는 무기의 영향을 받지 않습니다." ,
			   "쿨타임은 팻말을 든 채 좌클릭하면 좀 더 쉽게 확인 할 수 있습니다."};
	
	public Voodoo(String playerName)
	{
		super(playerName, "부두술사", 119, true, true, false, des);
		this.cool1=180;
		this.sta1=20;
		
		this.rank=3;
	}

	public void T_Passive(BlockPlaceEvent event)
	{
		Material material1 = event.getBlock().getType();
		if (material1 == Material.OAK_SIGN || material1 == Material.OAK_HANGING_SIGN || material1 == Material.OAK_WALL_HANGING_SIGN || material1 == Material.OAK_WALL_SIGN)
		{
			Player player = event.getPlayer();

			if (!(CoolTimeChecker.Check(player, 0)&&PlayerInventory.ItemCheck(player, material, stack0)))
				event.setCancelled(true);
		}
	}
	
	public void T_Active(PlayerInteractEvent event)
	{
		if (this.postSign != null)
		{
			if (event.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				Block block = event.getClickedBlock();
                assert block != null;
                if ((block.getType() == Material.OAK_SIGN || block.getType()  == Material.OAK_HANGING_SIGN || block.getType()  == Material.OAK_WALL_HANGING_SIGN || block.getType()  == Material.OAK_WALL_SIGN) &&
						this.postSign.getState().equals(block.getState()))
				{
					Player player = GameData.OnlinePlayer.get(targetName);
					if (player != null)
						player.damage(1, event.getPlayer());

				}
			}
		}
		else if (event.getPlayer().getItemInHand().getType() == Material.OAK_SIGN)
		{
			Action action = event.getAction();
			if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK)
			{
				Player player = event.getPlayer();
				if (CoolTimeChecker.Check(player, 0) && PlayerInventory.ItemCheck(player, material, stack0))
					player.sendMessage("스킬을 사용 할 수 있습니다.");
			}
		}
	}
	
	public void T_Passive(SignChangeEvent event)
	{
		Player player = event.getPlayer();
		String targetName = event.getLine(0);
		Player target = GameData.OnlinePlayer.get(targetName);
		if (target != null)
		{
			Skill.Use(player, material, stack0, 0, coolTime0);
			this.targetName=targetName;
			this.postSign=event.getBlock();
			player.sendMessage(ChatColor.RED+targetName+ChatColor.WHITE+" 를(을) 팻말과 연결시켰습니다.");
			target.sendMessage(ChatColor.RED+"부두술사가 당신을 위협합니다.");
			Timer t = new Timer();
			t.schedule(new Duration(), 7000);
		}
		else
			player.sendMessage(ChatColor.RED+targetName+ChatColor.WHITE+" 그런 플레이어는 없는데요...");
	}

	
	private class Duration extends TimerTask
	{
		@Override
		public void run()
		{
			targetName=null;
			postSign.breakNaturally();
			postSign=null;
		}
		
	}
}
