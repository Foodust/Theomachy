package org.septagram.Theomachy.Manager;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Handler.CommandHandler;

import java.util.Objects;


public class CommandManager implements CommandExecutor
{
	public CommandManager(Theomachy t)
	{
		Objects.requireNonNull(t.getCommand("t")).setExecutor(this);
		Objects.requireNonNull(t.getCommand("x")).setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label,String[] data) {

		if (label.equals("t"))
		{
			if (data.length==0) //설명 보기
			{
				sender.sendMessage(NamedTextColor.AQUA+("【        "+NamedTextColor.WHITE+"신들의 전쟁 명령어 일람"+NamedTextColor.AQUA+"        】"));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  start    ")+NamedTextColor.WHITE+("게임을 시작합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  stop     ")+NamedTextColor.WHITE+("게임을 중지합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  ability(a)     ")+NamedTextColor.WHITE+("능력을 설정합니다"));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  alist ")+NamedTextColor.WHITE+("적용된 능력을 확인합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  help     ")+NamedTextColor.WHITE+("자신의 능력을 확인합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  spawn(s) ")+NamedTextColor.AQUA+("<TeamName>   ")+NamedTextColor.WHITE+("해당 팀의 스폰지역을 설정합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  team(t)  ")+NamedTextColor.AQUA+("<TeamName>  ")+NamedTextColor.RED+("<Player>  ")+NamedTextColor.WHITE+("플레이어를 팀에 등록합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  info(i)  ")+NamedTextColor.AQUA+("<TeamName>     ")+NamedTextColor.WHITE+("해당 팀의 멤버를 확인합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  clear(c) ")+NamedTextColor.WHITE+("쿨타임을 초기화합니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  black    ")+NamedTextColor.WHITE+("블랙리스트 시스템을 엽니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  set       ")+NamedTextColor.WHITE+("GUI 설정 시스템을 엽니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/t  tip       ")+NamedTextColor.WHITE+("게임에 필요한 TIP을 봅니다."));
				sender.sendMessage(NamedTextColor.YELLOW+("/x  ")+NamedTextColor.RED+("<Player>     ")+NamedTextColor.WHITE+("해당 플레이어를 자신의 타겟으로 등록합니다"));
				sender.sendMessage(NamedTextColor.AQUA+"【  안내  】"+NamedTextColor.WHITE+"/t help, /t con, /t tip 이외의 명령은 모두 OP 전용입니다.");
			}
			else
				CommandHandler.tCommandHandler(sender, command, label, data);
		}
		else if (label.equalsIgnoreCase("x"))
		{
			if (data.length==0) //설명 보기
				sender.sendMessage(NamedTextColor.YELLOW+("/x  ")+NamedTextColor.RED+("<Player>     ")+NamedTextColor.WHITE+("해당 플레이어를 자신의 타겟으로 등록합니다"));
			else
				CommandHandler.xCommandHandler(sender, command, label, data);
		}
		return true;
	}
}
