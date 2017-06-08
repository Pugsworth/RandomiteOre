package com.brendanmint.randomore;

import java.io.File;
import java.util.regex.Pattern;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigHandler
{
    public static Configuration config;

    public static boolean randomiteStable; //Stable - Random amount of 1 item. Unstable - Random amount of random items.
    //Overworld Gen
    public static int randomiteSpawnTries; //How many times per chunk generator attempts to spawn ore
    public static int randomiteSpawnSize; //How Large clumps are
    public static int randomiteSpawnMin; //Lowest height
    public static int randomiteSpawnMax; //Highest height
    //Unstable Item Spawn Values
    public static String[] randomiteUnstableWhiteList = new String[]{};
    public static String[] randomiteUnstableWhiteListDef = new String[]{"minecraft:coal","minecraft:iron_ore","minecraft:diamond","minecraft:gold_ore","minecraft:dye.4","minecraft:redstone","minecraft:ender_pearl"};
    public static int randomiteDropsUnstableBase; //When Unstable this is the minimum amount of items ore will drop.
    public static int randomiteDropsUnstableChance; //When Unstable this random number is the amount of items the ore can drop in addition to the base number.
    //Stable Item Spawns
    public static String[] randomiteStableWhiteList = new String[]{};
    public static String[] randomiteStableWhiteListDef = new String[]{"minecraft:coal-3+6","minecraft:iron_ore-3+5","minecraft:diamond-2+5","minecraft:gold_ore-2+4","minecraft:dye.4-5+12","minecraft:redstone-5+12","minecraft:ender_pearl-1+2"};

    public static boolean randomiteStableNether; //Same as Above
    //Nether Gen
    public static int randomiteSpawnTriesNether; //How many times per chunk generator attempts to spawn nether ore
    public static int randomiteSpawnSizeNether; //How Large clumps are
    public static int randomiteSpawnMinNether; //Lowest height
    public static int randomiteSpawnMaxNether; //Highest height
    //Nether Item Spawn Values
    public static String[] randomiteUnstableWhiteListNether = new String[]{};
    public static String[] randomiteUnstableWhiteListDefNether = new String[]{"minecraft:coal","minecraft:iron_ore","minecraft:diamond","minecraft:gold_ore","minecraft:quartz","minecraft:ender_pearl"};
    public static int randomiteDropsUnstableBaseNether; //When Unstable this is the minimum amount of items ore will drop.
    public static int randomiteDropsUnstableChanceNether; //When Unstable this random number is the amount of items the ore can drop in addition to the base number.
    //Stable Item Spawns
    public static String[] randomiteStableWhiteListNether = new String[]{};
    public static String[] randomiteStableWhiteListDefNether = new String[]{"minecraft:coal-5+9","minecraft:iron_ore-4+8","minecraft:diamond-3+6","minecraft:gold_ore-2+6","minecraft:quartz-5+10","minecraft:ender_pearl-2+3"};

    public static Pattern oreConfigPattern = Pattern.compile("^((?:[^:]+):(?:\\w+))(?:\\.(\\d+))?\\-(\\d+)\\+(\\d+)$");
        
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) 
    {
        if (event.getModID().equals(Reference.MOD_ID))
            syncConfig();
    }
	
    public static void init(File file) 
    {
        config = new Configuration(file);
        syncConfig();
    }
	
    public static void syncConfig() 
    {
        String cat; //Category
        
        cat = "behavioroverworld";
        config.addCustomCategoryComment(cat, "Overworld Randomite Ore Settings");
        randomiteStable = config.getBoolean("randomiteStable", cat, true, "True - Stable Ore: Randomite Ore will drop random amount of 1 item type.\nFalse - Unstable Ore: Randomite Ore will drop random amount of random item types.");
        //Chance Settings
        randomiteDropsUnstableBase = config.getInt("randomiteDropsUnstableBase", cat, 3, 1, 20, "The base amount of random items Ore will drop when Unstable.");
        randomiteDropsUnstableChance = config.getInt("randomiteDropsUnstableChance", cat, 3, 0, 20, "The maximum amount of additional items the Ore may drop.\n(Final Ore drop is Base + (0-Chance))");
        randomiteUnstableWhiteList = config.getStringList("randomiteUnstableWhiteList", cat, randomiteUnstableWhiteListDef, "White list of Items/Blocks Randomite will drop when Unstable.\n");
        randomiteStableWhiteList = config.getStringList("randomiteStableWhiteList", cat, randomiteStableWhiteListDef, "Whitelist of Items/Blocks and their drop amounts when Randomite Ore is Stable.\n");
        
        cat = "behaviornether";
        config.addCustomCategoryComment(cat, "Nether Randomite Ore Settings");
        randomiteStableNether = config.getBoolean("randomiteStableNether", cat, true, "True - Stable Ore: Nether Randomite Ore will drop random amount of 1 item type.\nFalse - Unstable Ore: Nether Randomite Ore will drop random amount of random item types.");
        //Chance Settings
        randomiteDropsUnstableBaseNether = config.getInt("randomiteDropsUnstableBaseNether", cat, 3, 1, 20, "The base amount of random items Ore will drop when Unstable.");
        randomiteDropsUnstableChanceNether = config.getInt("randomiteDropsUnstableChanceNether", cat, 4, 0, 20, "The maximum amount of additional items the Ore may drop.\n(Final Ore drop is Base + (0-Chance))");
        randomiteUnstableWhiteListNether = config.getStringList("randomiteUnstableWhiteListNether", cat, randomiteUnstableWhiteListDefNether, "White list of Items/Blocks Nether Randomite will drop when Unstable.\n");
        randomiteStableWhiteListNether = config.getStringList("randomiteStableWhiteListNether", cat, randomiteStableWhiteListDefNether, "Whitelist of Items/Blocks and their drop amounts when Nether Randomite Ore is Stable.\n");

        cat = "overworldgen";
        config.addCustomCategoryComment(cat, "Overworld Ore Generation Settings");
        randomiteSpawnTries = config.getInt("randomiteSpawnTries", cat, 17, 1, Integer.MAX_VALUE, "How many tries per chuck the generator should try to place Randomite Ore.");
        randomiteSpawnSize = config.getInt("randomiteSpawnSize", cat, 5, 1, 50, "Maximum amount of Ore that should spawn together.\n('Veins' will be between 0 and this number)\n(To be replaced with VeinSize)\n");
        randomiteSpawnMin = config.getInt("randomiteSpawnMin", cat, 0, 0, Integer.MAX_VALUE, "Lowest Height Ore should spawn in Overworld.");
        randomiteSpawnMax = config.getInt("randomiteSpawnMax", cat, 90, 0, Integer.MAX_VALUE, "Highest Height Ore should spawn in Overworld.");
        
        cat = "netherworldgen";
        config.addCustomCategoryComment(cat, "Nether Ore Generation Settings");
        randomiteSpawnTriesNether = config.getInt("randomiteSpawnTriesNether", cat, 25, 1, Integer.MAX_VALUE, "How many tries per chuck the generator should try to place Nether Randomite Ore.");
        randomiteSpawnSizeNether = config.getInt("randomiteSpawnSizeNether", cat, 10, 1, 50, "Maximum amount of Ore that should spawn together.\n('Veins' will be between 0 and this number)\n(To be replaced with VeinSize)\n");
        randomiteSpawnMinNether = config.getInt("randomiteSpawnMinNether", cat, 0, 0, Integer.MAX_VALUE, "Lowest Height Ore should spawn in Nether.");
        randomiteSpawnMaxNether = config.getInt("randomiteSpawnMaxNether", cat, 250, 0, Integer.MAX_VALUE, "Highest Height Ore should spawn in Nether.");
        
        
        config.save();
    }
}
