package org.septagram.Theomachy.Handler.CommandModule;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.septagram.Theomachy.Theomachy;
import org.septagram.Theomachy.Utility.PermissionChecker;

import java.util.Objects;

public class GUISetting {

    public static void Module(CommandSender sender) {

        Player p = (Player) sender;

        if (PermissionChecker.Sender(sender)) {

            p.openInventory(gui());

        }
    }

    private static Inventory gui() {

        Inventory gui = Bukkit.createInventory(null, 18, Component.text(NamedTextColor.BLACK + ":::::: 설정 ::::::"));

        final int n = 10;

        ItemStack[] wool = new ItemStack[n];
        ItemMeta[] meta = new ItemMeta[n];
        int[] dura = new int[n];

        dura[0] = Theomachy.INVENTORY_CLEAR ? 5 : 14;
        dura[1] = Theomachy.GIVE_ITEM ? 5 : 14;
        dura[2] = Theomachy.ENTITIES_REMOVE ? 5 : 14;
        dura[3] = Theomachy.IGNORE_BED ? 5 : 14;
        dura[4] = Theomachy.FAST_START ? 5 : 14;
        dura[5] = Theomachy.AUTO_SAVE ? 5 : 14;
        dura[6] = Theomachy.ANIMAL ? 5 : 14;
        dura[7] = Theomachy.MONSTER ? 5 : 14;
        dura[8] = Theomachy.GAMBLING ? 5 : 14;

        for (int i = 0; i < n; i++) {
            wool[i] = new ItemStack(Material.WHITE_WOOL);
            meta[i] = wool[i].getItemMeta();
        }
        //def
        meta[0].displayName(Component.text(NamedTextColor.WHITE + "게임 시작 시 인벤토리 클리어"));
        meta[1].displayName(Component.text(NamedTextColor.WHITE + "게임 시작 시 스카이블럭 아이템 지급"));
        meta[2].displayName(Component.text(NamedTextColor.WHITE + "게임 시작 시 엔티티 제거"));
        meta[3].displayName(Component.text(NamedTextColor.WHITE + "침대 무시"));
        meta[4].displayName(Component.text(NamedTextColor.WHITE + "빠른 시작"));
        meta[5].displayName(Component.text(NamedTextColor.WHITE + "서버 자동 저장"));
        meta[6].displayName(Component.text(NamedTextColor.WHITE + "동물 스폰"));
        meta[7].displayName(Component.text(NamedTextColor.WHITE + "몬스터 스폰"));
        meta[8].displayName(Component.text(NamedTextColor.WHITE + "도박 허용"));

        for (int i = 0; i < n; i++) {
            wool[i].setDurability((short) dura[i]);
            wool[i].setItemMeta(meta[i]);
        }

        gui.setItem(0, wool[0]);
        gui.setItem(2, wool[1]);
        gui.setItem(4, wool[2]);
        gui.setItem(6, wool[3]);
        gui.setItem(8, wool[4]);
        gui.setItem(9, wool[5]);
        gui.setItem(11, wool[6]);
        gui.setItem(13, wool[7]);
        gui.setItem(15, wool[8]);

        return gui;
    }

    public static void guiListener(ItemStack wool) {

        switch (NamedTextColor.stripColor(Objects.requireNonNull(wool.getItemMeta().displayName()).toString())) {
            case "게임 시작 시 인벤토리 클리어" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.INVENTORY_CLEAR = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.INVENTORY_CLEAR = true;
                    wool.setDurability((short) 5);
                }
            }
            case "게임 시작 시 스카이블럭 아이텝 지급" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.GIVE_ITEM = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.GIVE_ITEM = true;
                    wool.setDurability((short) 5);
                }
            }
            case "게임 시작 시 엔티티 제거" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.ENTITIES_REMOVE = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.ENTITIES_REMOVE = true;
                    wool.setDurability((short) 5);
                }
            }
            case "침대 무시" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.IGNORE_BED = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.IGNORE_BED = true;
                    wool.setDurability((short) 5);
                }
            }
            case "빠른 시작" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.FAST_START = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.FAST_START = true;
                    wool.setDurability((short) 5);
                }
            }
            case "서버 자동 저장" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.AUTO_SAVE = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.AUTO_SAVE = true;
                    wool.setDurability((short) 5);
                }
            }
            case "동물 스폰" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.ANIMAL = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.ANIMAL = true;
                    wool.setDurability((short) 5);
                }
            }
            case "몬스터 스폰" -> {
                if (wool.getDurability() == 5) {
                    Theomachy.MONSTER = false;
                    wool.setDurability((short) 14);
                } else {
                    Theomachy.MONSTER = true;
                    wool.setDurability((short) 5);
                }
            }
        }
    }

}
