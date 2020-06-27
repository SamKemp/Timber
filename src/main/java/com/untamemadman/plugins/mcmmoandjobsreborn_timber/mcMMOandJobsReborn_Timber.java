package com.untamemadman.plugins.mcmmoandjobsreborn_timber;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class mcMMOandJobsReborn_Timber extends JavaPlugin
{
    PluginManager pm = getServer().getPluginManager();

    public static List<?> breakable = Arrays.asList(
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.LEGACY_LOG,
            Material.LEGACY_LOG_2,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.ACACIA_LEAVES,
            Material.BIRCH_LEAVES,
            Material.DARK_OAK_LEAVES,
            Material.JUNGLE_LEAVES,
            Material.LEGACY_LEAVES,
            Material.LEGACY_LEAVES_2,
            Material.OAK_LEAVES,
            Material.SPRUCE_LEAVES);

    public static List<?> countToDurability = Arrays.asList(
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.LEGACY_LOG,
            Material.LEGACY_LOG_2,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG);

    public static List<?> surroundable = Arrays.asList(
            Material.ACACIA_LOG,
            Material.BIRCH_LOG,
            Material.DARK_OAK_LOG,
            Material.JUNGLE_LOG,
            Material.LEGACY_LOG,
            Material.LEGACY_LOG_2,
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.DIRT,
            Material.COARSE_DIRT,
            Material.GRASS_BLOCK,
            Material.PODZOL);

    public static boolean Enabled_mcMMO = false;
    public static boolean Enabled_Jobs = false;

    @Override
    public void onEnable()
    {
        // Check for mcMMO
        if(pm.getPlugin("mcMMO") != null)
        {
            getLogger().info("mcMMO enabled");
            Enabled_mcMMO = true;
        }

        // Check for JobsReborn
        if(pm.getPlugin("Jobs") != null)
        {
            getLogger().info("Jobs enabled");
            Enabled_Jobs = true;
        }

        // Register Events
        pm.registerEvents(new Events(), this);
    }
}
