package org.Theomachy.Handler.Module.game;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.BlockDisplay;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Transformation;

public class DisplayModule {

    public BlockDisplay makeBlockDisplay(Player player , Location location, Material material,Double size){
        BlockDisplay blockDisplay = player.getWorld().spawn(location, BlockDisplay.class);
        blockDisplay.setBlock(material.createBlockData());
        Transformation transformation = blockDisplay.getTransformation();
        transformation.getScale().set(size);
        blockDisplay.setTransformation(transformation);
        return blockDisplay;
    }
    public TextDisplay makeTextDisplay(Player player , Location location, String text, Double size){
        TextDisplay textDisplay = player.getWorld().spawn(location, TextDisplay.class);
        textDisplay.setText(text);
        Transformation transformation = textDisplay.getTransformation();
        transformation.getScale().set(size);
        textDisplay.setTransformation(transformation);
        return textDisplay;
    }
}
