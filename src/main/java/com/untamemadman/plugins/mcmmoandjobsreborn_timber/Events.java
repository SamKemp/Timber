package com.untamemadman.plugins.mcmmoandjobsreborn_timber;

import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.LinkedList;
import java.util.List;

import static com.gamingmesh.jobs.Jobs.getPlayerManager;
import static com.gmail.nossr50.api.ExperienceAPI.addRawXP;

public class Events implements Listener
{

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player p = event.getPlayer();
        Location l = event.getBlock().getLocation();
        TimberState state = new TimberState(event.getBlock(), p);

        if(
                // If the user has timber.use
                !(p.hasPermission("timber.use") || p.isOp()) ||
                // Check if block is a log
                !mcMMOandJobsReborn_Timber.breakable.contains(event.getBlock().getType()) ||
                // Check if player is holding an axe
                !isHoldingAxe(p) ||
                // Check if block below is accepted
                !mcMMOandJobsReborn_Timber.surroundable.contains(l.subtract(0.0, 1.0, 0.0).getBlock().getType()) ||
                // Check if block Above is accepted
                !mcMMOandJobsReborn_Timber.surroundable.contains(l.add(0.0, 1.0, 0.0).getBlock().getType()) ||
                // Check if player is survival
                !p.getGameMode().equals(GameMode.SURVIVAL) ||
                // Check if player is crouching
                p.isSneaking()
        )
        {
            return;
        }

        //p.sendMessage("You broke a valid block");

        int durabilityCost = chopTree(p, l);

        int XPGain = durabilityCost*70;

        // if mcMMO is enabled
        if (mcMMOandJobsReborn_Timber.Enabled_mcMMO)
        {
            // Add XP to player
            addRawXP(p, "Woodcutting", XPGain, "UNKNOWN");
        }

        // if Jobs is enabled
        if (mcMMOandJobsReborn_Timber.Enabled_Jobs)
        {
            PlayerManager pm = getPlayerManager();
            if(pm.getJobsPlayer(p).isInJob(Jobs.getJob("Woodcutter")))
            {
                pm.addExperience(pm.getJobsPlayer(p), Jobs.getJob("Woodcutter"), XPGain);
            }
        }

        ItemStack item = p.getInventory().getItemInMainHand();

        short newDurability = (short)(item.getDurability() + durabilityCost);

        if(newDurability < maxDurability(item.getType()))
        {
            item.setDurability(newDurability);
        }
        else
        {
            item.setAmount(0);
            p.getInventory().setItemInMainHand(null);
        }

    }

    private int chopTree(Player p, Location location)
    {
        List<Block> blocksToChop = new LinkedList<Block>();
        int durabilityCost = 1;

        checkAxis(location, blocksToChop, 2);

        // Break all blocks in the Y axis
        for (int i = location.getBlockY(); i < getHighest(location); i++)
        {
            Location upperBlock = location.add(0, 1, 0);
            Material blockType = upperBlock.getBlock().getType();

            if(mcMMOandJobsReborn_Timber.breakable.contains(blockType))
            {
                blocksToChop.add(upperBlock.getBlock());

                checkAxis(location, blocksToChop, 3);
            } else break;
        }


        for (Block block : blocksToChop)
        {
            block.breakNaturally(new ItemStack(Material.DIAMOND_AXE));
            if(mcMMOandJobsReborn_Timber.countToDurability.contains(block.getType())) durabilityCost++;
        }

        return durabilityCost;
    }

    private static void checkAxis(Location location, List<Block> blocksToChop, int Recursive)
    {
        Block block = location.getBlock();
        Recursive--;

        // North
        Block North = block.getRelative(BlockFace.NORTH);
        if(mcMMOandJobsReborn_Timber.breakable.contains(North.getType()))
        {
            blocksToChop.add(North);
            if (Recursive != 0) checkAxis(North.getLocation(), blocksToChop, Recursive);
        }
        // NorthEast
        Block NorthEast = block.getRelative(BlockFace.NORTH_EAST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(NorthEast.getType()))
        {
            blocksToChop.add(NorthEast);
            if (Recursive != 0) checkAxis(NorthEast.getLocation(), blocksToChop, Recursive);
        }
        // NorthWest
        Block NorthWest = block.getRelative(BlockFace.NORTH_WEST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(NorthWest.getType()))
        {
            blocksToChop.add(NorthWest);
            if (Recursive != 0) checkAxis(NorthWest.getLocation(), blocksToChop, Recursive);
        }
        // East
        Block East = block.getRelative(BlockFace.EAST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(East.getType()))
        {
            blocksToChop.add(East);
            if (Recursive != 0) checkAxis(East.getLocation(), blocksToChop, Recursive);
        }
        // South
        Block South = block.getRelative(BlockFace.SOUTH);
        if(mcMMOandJobsReborn_Timber.breakable.contains(South.getType()))
        {
            blocksToChop.add(South);
            if (Recursive != 0) checkAxis(South.getLocation(), blocksToChop, Recursive);
        }
        // SouthEast
        Block SouthEast = block.getRelative(BlockFace.SOUTH_EAST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(SouthEast.getType()))
        {
            blocksToChop.add(SouthEast);
            if (Recursive != 0) checkAxis(SouthEast.getLocation(), blocksToChop, Recursive);
        }
        // SouthWest
        Block SouthWest = block.getRelative(BlockFace.SOUTH_WEST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(SouthWest.getType()))
        {
            blocksToChop.add(SouthWest);
            if (Recursive != 0) checkAxis(SouthWest.getLocation(), blocksToChop, Recursive);
        }
        // West
        Block West = block.getRelative(BlockFace.WEST);
        if(mcMMOandJobsReborn_Timber.breakable.contains(West.getType()))
        {
            blocksToChop.add(West);
            if (Recursive != 0) checkAxis(West.getLocation(), blocksToChop, Recursive);
        }
    }

    private static int getHighest(final Location location)
    {
        World world = location.getWorld();

        if(world == null) return -1;

        return location.getWorld().getHighestBlockYAt(location.getBlockX(), location.getBlockZ());
    }

    private boolean isHoldingAxe(Player p)
    {
        Material held = p.getInventory().getItemInMainHand().getType();

        switch (held) {
            case WOODEN_AXE:
            case STONE_AXE:
            case IRON_AXE:
            case GOLDEN_AXE:
            case DIAMOND_AXE:
                return true;
            default:
                return false;
        }
    }

    private short maxDurability(Material m)
    {
        short durability;

        switch (m)
        {
            case GOLDEN_AXE:
                durability = 33;
                break;
            case WOODEN_AXE:
                durability = 60;
                break;
            case STONE_AXE:
                durability = 132;
                break;
            case IRON_AXE:
                durability = 251;
                break;
            case DIAMOND_AXE:
                durability = 1562;
                break;
            default:
                durability = 0;
                break;
        }

        return durability;
    }
}
