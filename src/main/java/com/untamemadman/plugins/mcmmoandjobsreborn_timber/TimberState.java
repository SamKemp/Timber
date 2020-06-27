package com.untamemadman.plugins.mcmmoandjobsreborn_timber;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TimberState
{
    protected final Material id;
    //protected final byte meta;
    protected final Location origin;
    protected final Player player;
    protected final ItemStack heldItem;
    //protected final int heldItemUnbreaking;

    protected int totalFallen = 0;

    public TimberState(Block block, Player player)
    {
        this.id = block.getType();
        //this.meta = block.getData();
        this.origin = block.getLocation();
        this.player = player;
        this.heldItem = player.getInventory().getItemInMainHand();
        //this.heldItemUnbreaking = heldItem.getEnchantmentLevel(Enchantment.DURABILITY);
    }

    public boolean isSameTree(Block block)
    {
        return true;
    }
}
