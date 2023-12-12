package org.septagram.Theomachy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class UpdateChecker {
	
	public static void check(String thisVersion){
		try{
			URL u=new URL("https://raw.githubusercontent.com/plo-delta/PLODELTA/master/README.md");
			InputStreamReader isr=new InputStreamReader(u.openStream());
			BufferedReader br=new BufferedReader(isr);

			String Line;

			while((Line=br.readLine())!=null){
				if(Line.startsWith("Theomachy:")){
					Bukkit.getServer().getConsoleSender().sendMessage("[신들의 전쟁] "+ChatColor.AQUA+"최신 버전은 "+Line.replace("Theomachy:", "")+"입니다.");
					if(Line.replace("Theomachy:", "").equals(thisVersion)){
						Bukkit.getServer().getConsoleSender().sendMessage("[신들의 전쟁] "+ChatColor.AQUA+"최신 버전입니다!");
					}else{
						Bukkit.getServer().getConsoleSender().sendMessage("[신들의 전쟁] "+ChatColor.RED+"구 버전입니다. 개발자 블로그에서 최신 버전을 다운 받으세요.");
						Bukkit.getConsoleSender().sendMessage("[신들의 전쟁] 개발자 블로그: http://blog.naver.com/humint2003");
					}
				}
			}
		}catch(Exception e){
			Bukkit.getServer().getConsoleSender().sendMessage("[신들의 전쟁] "+ChatColor.RED+"업데이트 체크에 실패했습니다...");
		}
	}	
}