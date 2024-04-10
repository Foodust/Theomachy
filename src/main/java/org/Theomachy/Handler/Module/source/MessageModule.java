package org.Theomachy.Handler.Module.source;

import org.Theomachy.Message.TheomachyMessage;
import org.Theomachy.Theomachy;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

// message 모듈
// String + String 하기 귀찮아서
// 그냥 합쳤음
// 중간에 Josa 는 명사의 조사를 붙이기 위함
public class MessageModule {
    private final HangulModule hangulModule = new HangulModule();

    public void logInfo(String arg){
        Theomachy.log.info(arg);
    }
    public void sendPlayer(Player player, String... arg) {
        player.sendMessage(makeString(arg));
    }

    public void sendPlayer(CommandSender player, String... arg) {
        player.sendMessage(makeString(arg));
    }

    public void sendPlayer(Player player, String word, HangulModule.Josa josa, String... arg) {
        player.sendMessage(hangulModule.getJosa(word, josa) + makeString(arg));
    }

    public void sendTitle(Player player, String main, String sub, Integer fadeIn, Integer duration, Integer fadeOut) {
        player.sendTitle(main, sub, fadeIn, duration, fadeOut);
    }

    public void sendTitle(Player player, String main, Integer fadeIn, Integer duration, Integer fadeOut) {
        player.sendTitle(main, null, fadeIn, duration, fadeOut);
    }

    public void broadcastMessage(String... arg) {
        Bukkit.broadcastMessage(makeString(arg));
    }

    public void broadcastMessage(HangulModule.Josa josa, String... arg) {
        Bukkit.broadcastMessage(makeString(arg));
    }

    public void clearChat(Player player) {
        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }
    }

    public String makeString(String... arg) {
        StringBuilder temp = new StringBuilder();
        for (String string : arg) {
            temp.append(" ").append(string);
        }
        return temp.toString();
    }
}
