package org.Theomachy.Handler.Command;

import org.Theomachy.Ability.JUJUTSU_KAISEN.Sukuna;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Rengoku;
import org.Theomachy.Handler.Module.BlacklistModule;
import org.Theomachy.Enum.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Ability.GOD.*;
import org.Theomachy.Ability.HUMAN.*;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Itadori;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Jogo;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Zenitsu;
import org.Theomachy.Data.GameData;
import org.Theomachy.Checker.PermissionChecker;
import org.Theomachy.Handler.Handler.RandomSkillHandler;

import java.util.ArrayList;
import java.util.List;

public class AbilitySetCommand {
    public static void Module(CommandSender sender, Command command, String label, String[] data) {
        if (PermissionChecker.Sender(sender)) {
            if (!StartStopCommand.Ready) {
                if (data.length <= 1) {
                    sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_LIST_HELP.getMessage());
                    sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_SET_RANDOM.getMessage());
                    sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_REMOVE_PLAYER.getMessage());
                    sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_RESET.getMessage());
                    sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_SET.getMessage());
                } else if (data[1].equalsIgnoreCase(TheomachyMessage.COMMAND_HELP.getMessage()))
                    AbilityHelpCommand.ShowAbilityCodeNumber(sender);
                else if (data[1].equalsIgnoreCase(TheomachyMessage.COMMAND_REMOVE.getMessage())) //삭제
                {
                    if (data[2] != null)
                        Remove(sender, data[2]);
                    else
                        sender.sendMessage(TheomachyMessage.ERROR_SET_REMOVE_PLAYER_NAME.getMessage());
                } else if (data[1].equalsIgnoreCase(TheomachyMessage.COMMAND_RESET.getMessage()))//리셋
                    Reset();
                else if (data[1].equalsIgnoreCase(TheomachyMessage.COMMAND_RANDOM.getMessage()))//랜덤
                    RandomAssignment(sender);
                else if (data.length >= 3)
                    forceAssignment(sender, data);
                else {
                    sender.sendMessage(TheomachyMessage.ERROR_WRONG_COMMAND.getMessage());
                    sender.sendMessage(TheomachyMessage.ERROR_CHECK_T_A_COMMAND.getMessage());
                }
            } else
                sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_CHANGE_ABILITY_IN_GAME.getMessage());
        }
    }

    public static void Remove(CommandSender sender, String playerName) {
        Ability ability = GameData.PlayerAbility.get(playerName);
        if (ability != null) {
            GameData.PlayerAbility.remove(playerName);
            sender.sendMessage(TheomachyMessage.INFO_REMOVE_PLAYER_ABILITY.getMessage());
        } else
            sender.sendMessage(TheomachyMessage.ERROR_DOES_NOT_HAVE_ABILITY.getMessage());
    }

    public static void Reset() {
        GameData.PlayerAbility.clear();
        Bukkit.broadcastMessage(TheomachyMessage.INFO_REMOVE_ALL_PLAYER_ABILITY.getMessage());
    }

    private static void RandomAssignment(CommandSender sender) {

        if (!GameData.PlayerAbility.isEmpty()) {
            Bukkit.broadcastMessage(TheomachyMessage.INFO_RESET_AND_RANDOM_ABILITY.getMessage());
            GameData.PlayerAbility.clear();
        }
        List<Player> playerlist = new ArrayList<>(Bukkit.getOnlinePlayers());
        Bukkit.broadcastMessage(TheomachyMessage.INFO_AVAILABLE_PLAYERS.getMessage());
        for (Player e : playerlist)
            Bukkit.broadcastMessage(ChatColor.GOLD + "  " + e.getName());
        int[] rn = RandomSkillHandler.nonDuplicate();
        int length;
        length = Math.min(playerlist.size(), BlacklistModule.availableList);
        int i = 0;
        for (Player player : playerlist) {
            String playerName = player.getName();
            abilityAssignment(rn[i++], playerName, (Player) sender);
        }

        Bukkit.broadcastMessage(TheomachyMessage.INFO_SET_ALL_PLAYER_ABILITY.getMessage());
        Bukkit.broadcastMessage(TheomachyMessage.INFO_CHECK_ABILITY.getMessage());
        if (length != playerlist.size())
            Bukkit.broadcastMessage(TheomachyMessage.ERROR_TOO_MANY_PLAYER.getMessage());
    }

    private static void forceAssignment(CommandSender sender, String[] data) {

        Player p = (Player) sender;

        for (int i = 2; i < data.length; i++) {
            String abilityName = data[1];
            String playerName = data[i];
            if (GameData.OnlinePlayer.containsKey(playerName)) {
                try {
                    int abilityCode = Integer.parseInt(abilityName);
                    abilityAssignment(abilityCode, playerName, p);
                    Player player = GameData.OnlinePlayer.get(playerName);
                    Bukkit.broadcastMessage("관리자가 " + ChatColor.RED + playerName + ChatColor.WHITE + " 에게 능력을 할당하였습니다.");

                    player.sendMessage(TheomachyMessage.INFO_SET_PLAYER_ABILITY.getMessage());
                    player.sendMessage(TheomachyMessage.INFO_CHECK_ABILITY.getMessage());

                } catch (NumberFormatException e) {
                    sender.sendMessage(TheomachyMessage.ERROR_ABILITY_CODE_IS_INTEGER.getMessage());
                }
            } else
                sender.sendMessage(playerName + TheomachyMessage.ERROR_DOES_NOT_EXIST_PLAYER_NAME.getMessage());
        }
    }
    public static void abilityAssignment(int abilityNumber, String playerName, CommandSender p)
    {

        if (abilityNumber == AbilityInfo.Zeus.getIndex())
            GameData.PlayerAbility.put(playerName, new Zeus(playerName));
        else if (abilityNumber == AbilityInfo.Poseidon.getIndex())
            GameData.PlayerAbility.put(playerName, new Poseidon(playerName));
        else if (abilityNumber == AbilityInfo.Hades.getIndex())
            GameData.PlayerAbility.put(playerName, new Hades(playerName));
        else if (abilityNumber == AbilityInfo.Demeter.getIndex())
            GameData.PlayerAbility.put(playerName, new Demeter(playerName));
        else if (abilityNumber == AbilityInfo.Athena.getIndex())
            GameData.PlayerAbility.put(playerName, new Athena(playerName));
        else if (abilityNumber == AbilityInfo.Apollon.getIndex())
            GameData.PlayerAbility.put(playerName, new Apollon(playerName));
        else if (abilityNumber == AbilityInfo.Artemis.getIndex())
            GameData.PlayerAbility.put(playerName, new Artemis(playerName));
        else if (abilityNumber == AbilityInfo.Ares.getIndex())
            GameData.PlayerAbility.put(playerName, new Ares(playerName));
        else if (abilityNumber == AbilityInfo.Hephastus.getIndex())
            GameData.PlayerAbility.put(playerName, new Hephaestus(playerName));
        else if (abilityNumber == AbilityInfo.Asclepius.getIndex())
            GameData.PlayerAbility.put(playerName, new Asclepius(playerName));
        else if (abilityNumber == AbilityInfo.Hermes.getIndex())
            GameData.PlayerAbility.put(playerName, new Hermes(playerName));
        else if (abilityNumber == AbilityInfo.Dionysus.getIndex())
            GameData.PlayerAbility.put(playerName, new Dionysus(playerName));
        else if (abilityNumber == AbilityInfo.Aprodite.getIndex())
            GameData.PlayerAbility.put(playerName, new Aprodite(playerName));
        else if (abilityNumber == AbilityInfo.Eris.getIndex())
            GameData.PlayerAbility.put(playerName, new Eris(playerName));
        else if (abilityNumber == AbilityInfo.Morpious.getIndex())
            GameData.PlayerAbility.put(playerName, new Morpious(playerName));
        else if (abilityNumber == AbilityInfo.Aeolus.getIndex())
            GameData.PlayerAbility.put(playerName, new Aeolus(playerName));
        else if (abilityNumber == AbilityInfo.Akasha.getIndex())
            GameData.PlayerAbility.put(playerName, new Akasha(playerName));
        else if (abilityNumber == AbilityInfo.Horeundal.getIndex())
            GameData.PlayerAbility.put(playerName, new Horeundal(playerName));
        else if (abilityNumber == AbilityInfo.Sans.getIndex())
            GameData.PlayerAbility.put(playerName, new Sans(playerName));

        else if (abilityNumber == AbilityInfo.Archer.getIndex())
            GameData.PlayerAbility.put(playerName, new Archer(playerName));
        else if (abilityNumber == AbilityInfo.Miner.getIndex())
            GameData.PlayerAbility.put(playerName, new Miner(playerName));
        else if (abilityNumber == AbilityInfo.Stance.getIndex())
            GameData.PlayerAbility.put(playerName, new Stance(playerName));
        else if (abilityNumber == AbilityInfo.Teleporter.getIndex())
            GameData.PlayerAbility.put(playerName, new Teleporter(playerName));
        else if (abilityNumber == AbilityInfo.Bomber.getIndex())
            GameData.PlayerAbility.put(playerName, new Bomber(playerName));
        else if (abilityNumber == AbilityInfo.Creeper.getIndex())
            GameData.PlayerAbility.put(playerName, new Creeper(playerName));
        else if (abilityNumber == AbilityInfo.Wizard.getIndex())
            GameData.PlayerAbility.put(playerName, new Wizard(playerName));
        else if (abilityNumber == AbilityInfo.Assasin.getIndex())
            GameData.PlayerAbility.put(playerName, new Assasin(playerName));
        else if (abilityNumber == AbilityInfo.Reflection.getIndex())
            GameData.PlayerAbility.put(playerName, new Reflection(playerName));
        else if (abilityNumber == AbilityInfo.Blinder.getIndex())
            GameData.PlayerAbility.put(playerName, new Blinder(playerName));
        else if (abilityNumber == AbilityInfo.Invincibility.getIndex())
            GameData.PlayerAbility.put(playerName, new Invincibility(playerName));
        else if (abilityNumber == AbilityInfo.Clocking.getIndex())
            GameData.PlayerAbility.put(playerName, new Clocking(playerName));
        else if (abilityNumber == AbilityInfo.BlackSmith.getIndex())
            GameData.PlayerAbility.put(playerName, new Blacksmith(playerName));
        else if (abilityNumber == AbilityInfo.Boxer.getIndex())
            GameData.PlayerAbility.put(playerName, new Boxer(playerName));
        else if (abilityNumber == AbilityInfo.Priest.getIndex())
            GameData.PlayerAbility.put(playerName, new Priest(playerName));
        else if (abilityNumber == AbilityInfo.Witch.getIndex())
            GameData.PlayerAbility.put(playerName, new Witch(playerName));
        else if (abilityNumber == AbilityInfo.Meteor.getIndex())
            GameData.PlayerAbility.put(playerName, new Meteor(playerName));
        else if (abilityNumber == AbilityInfo.Sniper.getIndex())
            GameData.PlayerAbility.put(playerName, new Sniper(playerName));
        else if (abilityNumber == AbilityInfo.Voodoo.getIndex())
            GameData.PlayerAbility.put(playerName, new Voodoo(playerName));
        else if (abilityNumber == AbilityInfo.Anorexia.getIndex())
            GameData.PlayerAbility.put(playerName, new Anorexia(playerName));
        else if (abilityNumber == AbilityInfo.Bulter.getIndex())
            GameData.PlayerAbility.put(playerName, new Bulter(playerName));
        else if (abilityNumber == AbilityInfo.Midoriya.getIndex())
            GameData.PlayerAbility.put(playerName, new Midoriya(playerName));
        else if (abilityNumber == AbilityInfo.Goldspoon.getIndex())
            GameData.PlayerAbility.put(playerName, new Goldspoon(playerName));
        else if (abilityNumber == AbilityInfo.Bee.getIndex())
            GameData.PlayerAbility.put(playerName, new Bee(playerName));
        else if (abilityNumber == AbilityInfo.Snow.getIndex())
            GameData.PlayerAbility.put(playerName, new Snow(playerName));
        else if (abilityNumber == AbilityInfo.Tajja.getIndex())
            GameData.PlayerAbility.put(playerName, new Tajja(playerName));
        else if (abilityNumber == AbilityInfo.AGirl.getIndex())
            GameData.PlayerAbility.put(playerName, new AGirl(playerName));
        else if (abilityNumber == AbilityInfo.PokeGo.getIndex())
            GameData.PlayerAbility.put(playerName, new PokeGo(playerName));
        else if (abilityNumber == AbilityInfo.Tanker.getIndex())
            GameData.PlayerAbility.put(playerName, new Tanker(playerName));
        else if (abilityNumber == AbilityInfo.Gasolin.getIndex())
            GameData.PlayerAbility.put(playerName, new Gasolin(playerName));
        else if (abilityNumber == AbilityInfo.Zet.getIndex())
            GameData.PlayerAbility.put(playerName, new Zet(playerName));


        else if (abilityNumber == AbilityInfo.Itarodi.getIndex())
            GameData.PlayerAbility.put(playerName,new Itadori(playerName));
        else if (abilityNumber == AbilityInfo.Jogo.getIndex())
            GameData.PlayerAbility.put(playerName,new Jogo(playerName));
        else if (abilityNumber == AbilityInfo.Sukuna.getIndex())
            GameData.PlayerAbility.put(playerName,new Sukuna(playerName));

        else if (abilityNumber == AbilityInfo.Zenitsu.getIndex())
            GameData.PlayerAbility.put(playerName,new Zenitsu(playerName));
        else if(abilityNumber == AbilityInfo.Rengoku.getIndex())
            GameData.PlayerAbility.put(playerName,new Rengoku(playerName));
        else
        {
            p.sendMessage(TheomachyMessage.ERROR_WRONG_ABILITY_NUMBER_OR_NAME.getMessage());
            p.sendMessage(TheomachyMessage.INFO_ALL_ABILITY.getMessage());
        }
    }
    public static void abilityAssignment(AbilityInfo abilityInfo, String playerName, CommandSender p) {
        switch (abilityInfo) {
            case Zeus -> GameData.PlayerAbility.put(playerName, new Zeus(playerName));
            case Poseidon -> GameData.PlayerAbility.put(playerName, new Poseidon(playerName));
            case Hades -> GameData.PlayerAbility.put(playerName, new Hades(playerName));
            case Demeter -> GameData.PlayerAbility.put(playerName, new Demeter(playerName));
            case Athena -> GameData.PlayerAbility.put(playerName, new Athena(playerName));
            case Apollon -> GameData.PlayerAbility.put(playerName, new Apollon(playerName));
            case Artemis -> GameData.PlayerAbility.put(playerName, new Artemis(playerName));
            case Ares -> GameData.PlayerAbility.put(playerName, new Ares(playerName));
            case Hephastus -> GameData.PlayerAbility.put(playerName, new Hephaestus(playerName));
            case Asclepius -> GameData.PlayerAbility.put(playerName, new Asclepius(playerName));
            case Hermes -> GameData.PlayerAbility.put(playerName, new Hermes(playerName));
            case Dionysus -> GameData.PlayerAbility.put(playerName, new Dionysus(playerName));
            case Aprodite -> GameData.PlayerAbility.put(playerName, new Aprodite(playerName));
            case Eris -> GameData.PlayerAbility.put(playerName, new Eris(playerName));
            case Morpious -> GameData.PlayerAbility.put(playerName, new Morpious(playerName));
            case Aeolus -> GameData.PlayerAbility.put(playerName, new Aeolus(playerName));
            case Akasha -> GameData.PlayerAbility.put(playerName, new Akasha(playerName));
            case Horeundal -> GameData.PlayerAbility.put(playerName, new Horeundal(playerName));
            case Sans -> GameData.PlayerAbility.put(playerName, new Sans(playerName));
            case Archer -> GameData.PlayerAbility.put(playerName, new Archer(playerName));
            case Miner -> GameData.PlayerAbility.put(playerName, new Miner(playerName));
            case Stance -> GameData.PlayerAbility.put(playerName, new Stance(playerName));
            case Teleporter -> GameData.PlayerAbility.put(playerName, new Teleporter(playerName));
            case Bomber -> GameData.PlayerAbility.put(playerName, new Bomber(playerName));
            case Creeper -> GameData.PlayerAbility.put(playerName, new Creeper(playerName));
            case Wizard -> GameData.PlayerAbility.put(playerName, new Wizard(playerName));
            case Assasin -> GameData.PlayerAbility.put(playerName, new Assasin(playerName));
            case Reflection -> GameData.PlayerAbility.put(playerName, new Reflection(playerName));
            case Blinder -> GameData.PlayerAbility.put(playerName, new Blinder(playerName));
            case Invincibility -> GameData.PlayerAbility.put(playerName, new Invincibility(playerName));
            case Clocking -> GameData.PlayerAbility.put(playerName, new Clocking(playerName));
            case BlackSmith -> GameData.PlayerAbility.put(playerName, new Blacksmith(playerName));
            case Boxer -> GameData.PlayerAbility.put(playerName, new Boxer(playerName));
            case Priest -> GameData.PlayerAbility.put(playerName, new Priest(playerName));
            case Witch -> GameData.PlayerAbility.put(playerName, new Witch(playerName));
            case Meteor -> GameData.PlayerAbility.put(playerName, new Meteor(playerName));
            case Sniper -> GameData.PlayerAbility.put(playerName, new Sniper(playerName));
            case Voodoo -> GameData.PlayerAbility.put(playerName, new Voodoo(playerName));
            case Anorexia -> GameData.PlayerAbility.put(playerName, new Anorexia(playerName));
            case Bulter -> GameData.PlayerAbility.put(playerName, new Bulter(playerName));
            case Midoriya -> GameData.PlayerAbility.put(playerName, new Midoriya(playerName));
            case Goldspoon -> GameData.PlayerAbility.put(playerName, new Goldspoon(playerName));
            case Bee -> GameData.PlayerAbility.put(playerName, new Bee(playerName));
            case Snow -> GameData.PlayerAbility.put(playerName, new Snow(playerName));
            case Tajja -> GameData.PlayerAbility.put(playerName, new Tajja(playerName));
            case AGirl -> GameData.PlayerAbility.put(playerName, new AGirl(playerName));
            case PokeGo -> GameData.PlayerAbility.put(playerName, new PokeGo(playerName));
            case Tanker -> GameData.PlayerAbility.put(playerName, new Tanker(playerName));
            case Gasolin -> GameData.PlayerAbility.put(playerName, new Gasolin(playerName));
            case Zet -> GameData.PlayerAbility.put(playerName, new Zet(playerName));
            case Itarodi -> GameData.PlayerAbility.put(playerName, new Itadori(playerName));
            case Jogo -> GameData.PlayerAbility.put(playerName, new Jogo(playerName));
            case Sukuna -> GameData.PlayerAbility.put(playerName, new Sukuna(playerName));
            case Zenitsu -> GameData.PlayerAbility.put(playerName, new Zenitsu(playerName));
            case Rengoku -> GameData.PlayerAbility.put(playerName, new Rengoku(playerName));
            default -> {
                p.sendMessage(TheomachyMessage.ERROR_WRONG_ABILITY_NUMBER_OR_NAME.getMessage());
                p.sendMessage(TheomachyMessage.INFO_ALL_ABILITY.getMessage());
            }
        }
    }
}