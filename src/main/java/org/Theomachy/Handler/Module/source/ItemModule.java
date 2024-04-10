package org.Theomachy.Handler.Module.source;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemModule {
    public final String errorWool = Material.WHITE_WOOL.name();
    public final String errorRotten = Material.ROTTEN_FLESH.name();
    public ItemStack setItem(Material material, String title,int amount) {
        ItemStack item = new ItemStack(material, amount);
        if(material == Material.AIR){item = new ItemStack(Material.ROTTEN_FLESH, amount);}
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(title);
        item.setItemMeta(itemMeta);
        if(material == Material.AIR){item.setType(Material.AIR);}
        return item;
    }
    public  ItemStack setItem(Material material,String title, List<String> lore, int amount){
        ItemStack item = new ItemStack(material, 1);
        if(material == Material.AIR){item = new ItemStack(Material.ROTTEN_FLESH, amount);}
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(title);
        itemMeta.setLore(lore);
        item.setItemMeta(itemMeta);
        if(material == Material.AIR){item.setType(Material.AIR);}
        return item;
    }
    public ItemStack setCustomItem(Material material,String title, List<String> lore, int code, int amount){
        ItemStack item = new ItemStack(material,amount);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(title);
        itemMeta.setLore(lore);
        itemMeta.setCustomModelData(code);
        item.setItemMeta(itemMeta);
        return item;
    }
    public ItemStack setCustomItem(Material material, String title, int code, int amount){
        ItemStack item = new ItemStack(material,amount);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.setDisplayName(title);
        itemMeta.setCustomModelData(code);
        item.setItemMeta(itemMeta);
        return item;
    }
}
