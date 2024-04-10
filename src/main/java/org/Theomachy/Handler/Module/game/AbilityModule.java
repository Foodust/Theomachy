package org.Theomachy.Handler.Module.game;

import org.Theomachy.Ability.Ability;
import org.Theomachy.Ability.GOD.*;
import org.Theomachy.Ability.HUMAN.*;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Itadori;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Jogo;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Satoru;
import org.Theomachy.Ability.JUJUTSU_KAISEN.Sukuna;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Giyu;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Rengoku;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Tanjiro;
import org.Theomachy.Ability.KIMETSU_NO_YAIBA.Zenitsu;
import org.Theomachy.Checker.PermissionChecker;
import org.Theomachy.Data.AbilityData;
import org.Theomachy.Data.GameData;
import org.Theomachy.Enum.AbilityInfo;
import org.Theomachy.Enum.AbilityRank;

import org.Theomachy.Handler.Handler.RandomSkillHandler;
import org.Theomachy.Handler.Module.source.BlacklistModule;
import org.Theomachy.Handler.Module.source.MessageModule;
import org.Theomachy.Message.TheomachyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class AbilityModule  {
    private final RandomSkillHandler randomskillHandler = new RandomSkillHandler();
    private final PermissionChecker permissionChecker = new PermissionChecker();
    private final MessageModule messageModule = new MessageModule();
    public AbilityModule(){};
    public void listOfAbilityPlayer(CommandSender sender) {
        if (permissionChecker.Player(sender)) {
            if (!GameData.playerAbility.isEmpty()) {
                Collection<Ability> ability = GameData.playerAbility.values();
                for (Ability e : ability)
                    messageModule.sendPlayer(sender,ChatColor.WHITE + e.playerName + "  :  " + ChatColor.YELLOW + e.abilityName);
            } else {
                messageModule.sendPlayer(sender,TheomachyMessage.ERROR_DOES_NOT_HAVE_ABILITY_ALL_PLAYER.getMessage());
            }
        }
    }

    public void openAbilityHelpInventory(Player player) {
        Ability ability = GameData.playerAbility.get(player.getName());
        if (ability != null) {
            int inventorySize = 2 * 9;
            Inventory inventory = Bukkit.createInventory(null, inventorySize, TheomachyMessage.SETTING_ABILITY_INFO.getMessage());

            ItemStack abilityName = new ItemStack(Material.ITEM_FRAME);
            ItemStack abilityDescription = new ItemStack(Material.BOOK);
            setInfoItem(
                    ability, abilityName, Objects.requireNonNull(abilityName.getItemMeta()),
                    abilityDescription, Objects.requireNonNull(abilityDescription.getItemMeta()));

            ItemStack abilityRank = setRankItem(ability.rank);

            inventory.setItem(0, abilityDescription);
            inventory.setItem(4, abilityName);
            inventory.setItem(8, abilityRank);

            ItemStack abilityPassiveCoolTimeItem = new ItemStack(Material.CLOCK);
            ItemStack abilityNormalCoolTimeItem = new ItemStack(Material.CLOCK);
            ItemStack abilityRareCoolTimeItem = new ItemStack(Material.CLOCK);

            ItemMeta abilityPassiveCoolTimeItemMeta = abilityNormalCoolTimeItem.getItemMeta();
            ItemMeta abilityNormalCoolTimeItemMeta = abilityNormalCoolTimeItem.getItemMeta();
            ItemMeta abilityRareCoolTimeItemMeta = abilityRareCoolTimeItem.getItemMeta();

            if (ability.activeType) {
                assert abilityNormalCoolTimeItemMeta != null;
                setCoolTimeItem(
                        abilityNormalCoolTimeItem, abilityNormalCoolTimeItemMeta, TheomachyMessage.EXPLAIN_ABILITY_NORMAL.getMessage(),
                        ability.normalSkillCoolTime, ability.normalSkillStack
                );
                if (ability.rareSkillCoolTime != -1) {
                    assert abilityRareCoolTimeItemMeta != null;
                    setCoolTimeItem(
                            abilityRareCoolTimeItem, abilityRareCoolTimeItemMeta, TheomachyMessage.EXPLAIN_ABILITY_RARE.getMessage(),
                            ability.rareSkillCoolTime, ability.rareSkillStack
                    );
                    inventory.setItem(9, abilityNormalCoolTimeItem);
                    inventory.setItem(17, abilityRareCoolTimeItem);

                } else {
                    inventory.setItem(13, abilityNormalCoolTimeItem);
                }
            }
            if (!ability.activeType && ability.passiveType) {
                assert abilityPassiveCoolTimeItemMeta != null;
                setPassiveItem(
                        abilityPassiveCoolTimeItem, abilityPassiveCoolTimeItemMeta, TheomachyMessage.EXPLAIN_ABILITY_PASSIVE.getMessage()
                );
                abilityPassiveCoolTimeItem.setItemMeta(abilityPassiveCoolTimeItemMeta);

                inventory.setItem(13, abilityPassiveCoolTimeItem);
            }
            player.openInventory(inventory);
        } else
            messageModule.sendPlayer(player,TheomachyMessage.ERROR_DOES_NOT_HAVE_ABILITY.getMessage());

    }

    public void setCoolTimeItem(ItemStack coolTimeItem, ItemMeta coolTimeItemMeta, String Message, int coolTime, int stack) {
        List<String> skillExplain = new ArrayList<String>();
        coolTimeItemMeta.setDisplayName(Message);
        skillExplain.add(TheomachyMessage.EXPLAIN_COOL_TIME.getMessage() + coolTime + TheomachyMessage.EXPLAIN_COOL_DOWN.getMessage());
        skillExplain.add(TheomachyMessage.EXPLAIN_COBBLE_STONE.getMessage() + stack + TheomachyMessage.EXPLAIN_SPEND_COUNT.getMessage());
        coolTimeItemMeta.setLore(skillExplain);
        coolTimeItem.setItemMeta(coolTimeItemMeta);
    }

    public void setPassiveItem(ItemStack coolTimeItem, ItemMeta coolTimeItemMeta, String Message) {
        List<String> skillExplain = new ArrayList<String>();
        coolTimeItemMeta.setDisplayName(Message);
        skillExplain.add(TheomachyMessage.EXPLAIN_CHECK_THE_ABILITY_DESCRIPTION.getMessage());
        coolTimeItemMeta.setLore(skillExplain);
        coolTimeItem.setItemMeta(coolTimeItemMeta);
    }

    public void setInfoItem(Ability ability, ItemStack itemNameStack, ItemMeta itemNameMeta, ItemStack itemDesStack, ItemMeta itemDesMeta) {
        itemNameMeta.setDisplayName(TheomachyMessage.EXPLAIN_ABILITY_NAME.getMessage() + ChatColor.WHITE + ability.abilityName);
        itemDesMeta.setDisplayName(TheomachyMessage.EXPLAIN_ABILITY_EXPLAIN.getMessage());

        itemDesMeta.setLore(Arrays.stream(ability.description)
                .map(des -> ChatColor.WHITE + des)
                .collect(Collectors.toList()));

        itemNameStack.setItemMeta(itemNameMeta);
        itemDesStack.setItemMeta(itemDesMeta);
    }

    public ItemStack setRankItem(AbilityRank rank) {
        ItemStack rankItemStack = new ItemStack(Material.AIR);
        ItemMeta itemMeta;
        switch (rank) {
            case F -> {
                rankItemStack = new ItemStack(Material.ROTTEN_FLESH);
                itemMeta = rankItemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(TheomachyMessage.EXPLAIN_RANK.getMessage() + ChatColor.GRAY + AbilityRank.F.getRank());
                rankItemStack.setItemMeta(itemMeta);
            }
            case C -> {
                rankItemStack = new ItemStack(Material.IRON_INGOT);
                itemMeta = rankItemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(TheomachyMessage.EXPLAIN_RANK.getMessage() + ChatColor.WHITE + AbilityRank.C.getRank());
                rankItemStack.setItemMeta(itemMeta);
            }
            case B -> {
                rankItemStack = new ItemStack(Material.GOLD_INGOT);
                itemMeta = rankItemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(TheomachyMessage.EXPLAIN_RANK.getMessage() + ChatColor.GREEN + AbilityRank.B.getRank());
                rankItemStack.setItemMeta(itemMeta);
            }
            case A -> {
                rankItemStack = new ItemStack(Material.EMERALD);
                itemMeta = rankItemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(TheomachyMessage.EXPLAIN_RANK.getMessage() + ChatColor.AQUA + AbilityRank.A.getRank());
                rankItemStack.setItemMeta(itemMeta);
            }
            case S -> {
                rankItemStack = new ItemStack(Material.DIAMOND);
                itemMeta = rankItemStack.getItemMeta();
                assert itemMeta != null;
                itemMeta.setDisplayName(TheomachyMessage.EXPLAIN_RANK.getMessage() + ChatColor.YELLOW + AbilityRank.S.getRank());
                rankItemStack.setItemMeta(itemMeta);
            }
        }
        return rankItemStack;
    }

    public void explainCommand(CommandSender sender) {
        sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_LIST_HELP.getMessage());
        sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_SET_RANDOM.getMessage());
        sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_REMOVE_PLAYER.getMessage());
        sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_RESET.getMessage());
        sender.sendMessage(TheomachyMessage.EXPLAIN_ABILITY_SET.getMessage());
    }

    public void showCode(CommandSender sender) {

        messageModule.sendPlayer(sender,TheomachyMessage.INFO_GOD.getMessage());
        for (AbilityInfo abilityInfo : AbilityData.godEnum) {
            messageModule.sendPlayer(sender,ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        messageModule.sendPlayer(sender,TheomachyMessage.INFO_HUMAN.getMessage());
        for (AbilityInfo abilityInfo : AbilityData.humanEnum) {
            messageModule.sendPlayer(sender,ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        messageModule.sendPlayer(sender,TheomachyMessage.INFO_JUJUTSU_KAISEN.getMessage());
        for (AbilityInfo abilityInfo : AbilityData.jujutsuKaisenEnum) {
            messageModule.sendPlayer(sender,ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
        messageModule.sendPlayer(sender,TheomachyMessage.INFO_KIMETSU_NO_YAIBA.getMessage());
        for (AbilityInfo abilityInfo : AbilityData.kimetsuNoYaibaEnum) {
            messageModule.sendPlayer(sender,ChatColor.YELLOW + "【 " + abilityInfo.getIndex() + " 】" + ChatColor.WHITE + abilityInfo.getName());
        }
    }

    public void errorMessage(CommandSender sender) {
        messageModule.sendPlayer(sender,TheomachyMessage.ERROR_WRONG_COMMAND.getMessage());
        messageModule.sendPlayer(sender,TheomachyMessage.ERROR_CHECK_T_A_COMMAND.getMessage());
    }

    public void resetAbility() {
        GameData.playerAbility.clear();
        messageModule.broadcastMessage(TheomachyMessage.INFO_REMOVE_ALL_PLAYER_ABILITY.getMessage());
    }

    public void remove(CommandSender sender, String playerName) {
        if (playerName != null) {
            Ability ability = GameData.playerAbility.get(playerName);
            if (ability != null) {
                GameData.playerAbility.remove(playerName);
                messageModule.sendPlayer(sender,playerName + TheomachyMessage.INFO_REMOVE_PLAYER_ABILITY.getMessage());
            } else
                messageModule.sendPlayer(sender,playerName + TheomachyMessage.ERROR_DOES_NOT_WHO_HAVE_ABILITY.getMessage());
        } else {
            messageModule.sendPlayer(sender,TheomachyMessage.ERROR_SET_REMOVE_PLAYER_NAME.getMessage());
        }
    }

    public void randomAbilityAllPlayer(CommandSender sender) {
        if (!GameData.playerAbility.isEmpty()) {
            messageModule.broadcastMessage(TheomachyMessage.INFO_RESET_AND_RANDOM_ABILITY.getMessage());
            GameData.playerAbility.clear();
        }
        List<Player> playerlist = new ArrayList<>(Bukkit.getOnlinePlayers());
        messageModule.broadcastMessage(TheomachyMessage.INFO_AVAILABLE_PLAYERS.getMessage());
        for (Player enemy : playerlist)
            messageModule.broadcastMessage(ChatColor.GOLD + "  " + enemy.getName());
        List<Integer> rn = randomskillHandler.makeRandomAbilityList();
        int length;
        length = Math.min(playerlist.size(), BlacklistModule.availableList);
        int i = 0;
        for (Player player : playerlist) {
            String playerName = player.getName();
            abilityAssignment(rn.get(i++), playerName, (Player) sender);
        }

        messageModule.broadcastMessage(TheomachyMessage.INFO_SET_ALL_PLAYER_ABILITY.getMessage());
        messageModule.broadcastMessage(TheomachyMessage.EXPLAIN_CHECK_ABILITY.getMessage());
        if (length != playerlist.size())
            messageModule.broadcastMessage(TheomachyMessage.ERROR_TOO_MANY_PLAYER.getMessage());
    }

    public void forceAssignment(CommandSender sender, String[] data) {
        for (int i = 2; i < data.length; i++) {
            String abilityName = data[1];
            String playerName = data[i];
            if (GameData.onlinePlayer.containsKey(playerName)) {
                try {
                    int abilityCode = Integer.parseInt(abilityName);
                    abilityAssignment(abilityCode, playerName, sender);
                    Player player = GameData.onlinePlayer.get(playerName);
                    messageModule.broadcastMessage("관리자가 " + ChatColor.RED + playerName + ChatColor.WHITE + " 에게 능력을 할당하였습니다.");

                    messageModule.sendPlayer(player,TheomachyMessage.INFO_SET_PLAYER_ABILITY.getMessage());
                    messageModule.sendPlayer(player, TheomachyMessage.EXPLAIN_CHECK_ABILITY.getMessage());

                } catch (NumberFormatException e) {
                    messageModule.sendPlayer(sender,TheomachyMessage.ERROR_ABILITY_CODE_IS_INTEGER.getMessage());
                }
            } else
                messageModule.sendPlayer(sender, playerName + TheomachyMessage.ERROR_DOES_NOT_EXIST_PLAYER_NAME.getMessage());
        }
    }
    public void abilityAssignment(int abilityNumber, String playerName, CommandSender sender) {
        AbilityInfo abilityInfo = AbilityInfo.getAbilityByIndex(abilityNumber);
        switch (abilityInfo) {
            case Zeus -> GameData.playerAbility.put(playerName, new Zeus(playerName));
            case Poseidon -> GameData.playerAbility.put(playerName, new Poseidon(playerName));
            case Hades -> GameData.playerAbility.put(playerName, new Hades(playerName));
            case Demeter -> GameData.playerAbility.put(playerName, new Demeter(playerName));
            case Athena -> GameData.playerAbility.put(playerName, new Athena(playerName));
            case Apollon -> GameData.playerAbility.put(playerName, new Apollon(playerName));
            case Artemis -> GameData.playerAbility.put(playerName, new Artemis(playerName));
            case Ares -> GameData.playerAbility.put(playerName, new Ares(playerName));
            case Hephastus -> GameData.playerAbility.put(playerName, new Hephaestus(playerName));
            case Asclepius -> GameData.playerAbility.put(playerName, new Asclepius(playerName));
            case Hermes -> GameData.playerAbility.put(playerName, new Hermes(playerName));
            case Dionysus -> GameData.playerAbility.put(playerName, new Dionysus(playerName));
            case Aprodite -> GameData.playerAbility.put(playerName, new Aprodite(playerName));
            case Eris -> GameData.playerAbility.put(playerName, new Eris(playerName));
            case Morpious -> GameData.playerAbility.put(playerName, new Morpious(playerName));
            case Aeolus -> GameData.playerAbility.put(playerName, new Aeolus(playerName));
            case Akasha -> GameData.playerAbility.put(playerName, new Akasha(playerName));
            case Horeundal -> GameData.playerAbility.put(playerName, new Horeundal(playerName));
            case Sans -> GameData.playerAbility.put(playerName, new Sans(playerName));

            case Archer -> GameData.playerAbility.put(playerName, new Archer(playerName));
            case Miner -> GameData.playerAbility.put(playerName, new Miner(playerName));
            case Stance -> GameData.playerAbility.put(playerName, new Stance(playerName));
            case Teleporter -> GameData.playerAbility.put(playerName, new Teleporter(playerName));
            case Bomber -> GameData.playerAbility.put(playerName, new Bomber(playerName));
            case Creeper -> GameData.playerAbility.put(playerName, new Creeper(playerName));
            case Wizard -> GameData.playerAbility.put(playerName, new Wizard(playerName));
            case Assasin -> GameData.playerAbility.put(playerName, new Assasin(playerName));
            case Reflection -> GameData.playerAbility.put(playerName, new Reflection(playerName));
            case Blinder -> GameData.playerAbility.put(playerName, new Blinder(playerName));
            case Invincibility -> GameData.playerAbility.put(playerName, new Invincibility(playerName));
            case Clocking -> GameData.playerAbility.put(playerName, new Clocking(playerName));
            case BlackSmith -> GameData.playerAbility.put(playerName, new Blacksmith(playerName));
            case Boxer -> GameData.playerAbility.put(playerName, new Boxer(playerName));
            case Priest -> GameData.playerAbility.put(playerName, new Priest(playerName));
            case Witch -> GameData.playerAbility.put(playerName, new Witch(playerName));
            case Meteor -> GameData.playerAbility.put(playerName, new Meteor(playerName));
            case Sniper -> GameData.playerAbility.put(playerName, new Sniper(playerName));
            case Voodoo -> GameData.playerAbility.put(playerName, new Voodoo(playerName));
            case Anorexia -> GameData.playerAbility.put(playerName, new Anorexia(playerName));
            case Bulter -> GameData.playerAbility.put(playerName, new Bulter(playerName));
            case Midoriya -> GameData.playerAbility.put(playerName, new Midoriya(playerName));
            case Goldspoon -> GameData.playerAbility.put(playerName, new Goldspoon(playerName));
            case Bee -> GameData.playerAbility.put(playerName, new Bee(playerName));
            case Snow -> GameData.playerAbility.put(playerName, new Snow(playerName));
            case Tajja -> GameData.playerAbility.put(playerName, new Tajja(playerName));
            case AGirl -> GameData.playerAbility.put(playerName, new AGirl(playerName));
            case PokeGo -> GameData.playerAbility.put(playerName, new PokeGo(playerName));
            case Tanker -> GameData.playerAbility.put(playerName, new Tanker(playerName));
            case Gasolin -> GameData.playerAbility.put(playerName, new Gasolin(playerName));
            case Zet -> GameData.playerAbility.put(playerName, new Zet(playerName));

            case Itarodi -> GameData.playerAbility.put(playerName, new Itadori(playerName));
            case Jogo -> GameData.playerAbility.put(playerName, new Jogo(playerName));
            case Sukuna -> GameData.playerAbility.put(playerName, new Sukuna(playerName));
            case Satoru -> GameData.playerAbility.put(playerName, new Satoru(playerName));

            case Zenitsu -> GameData.playerAbility.put(playerName, new Zenitsu(playerName));
            case Rengoku -> GameData.playerAbility.put(playerName, new Rengoku(playerName));
            case Tanjiro -> GameData.playerAbility.put(playerName, new Tanjiro(playerName));
            case Giyu -> GameData.playerAbility.put(playerName, new Giyu(playerName));
            default -> {
                sender.sendMessage(TheomachyMessage.ERROR_WRONG_ABILITY_NUMBER_OR_NAME.getMessage());
                sender.sendMessage(TheomachyMessage.EXPLAIN_CHECK_ABILITY_CODE.getMessage());
            }
        }
    }
}
