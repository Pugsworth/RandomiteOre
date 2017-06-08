package com.brendanmint.randomore.blocks;

import java.util.Random;

import com.brendanmint.randomore.ConfigHandler;
import com.brendanmint.randomore.Reference;
import com.brendanmint.randomore.Util;
import java.util.regex.Matcher;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockRandomite extends Block {

    private static String oreDrop = "None";
    // private static String oreHold = "coal";
    private static int oreMeta;
    private static int dropMin;
    private static int dropMax;

    public BlockRandomite() 
    {
        super(Material.ROCK);

        setHarvestLevel("pickaxe", 2);
        setHardness(3.0f);
        setResistance(5f);
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);

        setUnlocalizedName(Reference.randOreBlocks.RANDOMITEORE.getUnlocalisedName());
        setRegistryName(Reference.randOreBlocks.RANDOMITEORE.getRegistryName());
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) 
    {
        setDrop();
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
        oreDrop = "minecraft:coal";
        dropMax = 3;
        dropMin = 2;
        super.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        if(ConfigHandler.randomiteStable) return Item.getByNameOrId(oreDrop);
        /*
        else 
        {
            oreDrop = ConfigHandler.randomiteUnstableWhiteList[RANDOM.nextInt(ConfigHandler.randomiteUnstableWhiteList.length)];
            oreMeta = 0;
            if(oreDrop.contains("."))
            {
                oreHold = oreDrop.split("\\.");
                oreMeta = Integer.parseInt(oreHold[1]);
                return Item.getByNameOrId(oreHold[0]);
            }
            else
            {
                oreMeta = 0;
                return Item.getByNameOrId(oreDrop);
            }
        }
        */
        
        return Item.getByNameOrId("minecraft:cobblestone");
    }

    @Override
    public int damageDropped(IBlockState state) 
    {
        return oreMeta == 0 ? 0 : oreMeta;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random random)
    {
        if(ConfigHandler.randomiteStable)
        {
            if(dropMax - dropMin > 0) return dropMin + RANDOM.nextInt(dropMax - dropMin);
            else return 0;
        }
        else return ConfigHandler.randomiteDropsUnstableBase + RANDOM.nextInt(ConfigHandler.randomiteDropsUnstableChance+1);
        // else return 0;
    } 

    public static void setDrop()
    {
        if(ConfigHandler.randomiteStable)
        {
            oreDrop = ConfigHandler.randomiteStableWhiteList[RANDOM.nextInt(ConfigHandler.randomiteStableWhiteList.length)];

            Matcher match = ConfigHandler.oreConfigPattern.matcher(oreDrop);
            match.find();

            try {
                System.out.println(String.format("%s/%s - %s,%s", match.group(1), match.group(2), match.group(3), match.group(4)));

                oreDrop = match.group(1);
                oreMeta = Util.parseIntDef(match.group(2), 0);
                dropMin = Util.parseIntDef(match.group(3), 1);
                dropMax = Util.parseIntDef(match.group(4), 1);
            }
            catch(NumberFormatException e) {
                oreDrop = "minecraft:coal";
                oreMeta = 0;
                dropMin = 1;
                dropMax = 1;
                e.printStackTrace();
            }

            /*
            if(oreDrop.contains("."))
            {
                oreHold = oreDrop.split("[.+-]");
                System.out.println("oreHold: " + oreHold.length);
                dropMax = Integer.parseInt(oreHold[3]);
                dropMin = Integer.parseInt(oreHold[2]);
                oreMeta = Integer.parseInt(oreHold[1]);
                oreDrop = oreHold[0];
            }
            else
            {
                oreHold = oreDrop.split("[.+-]");
                System.out.println("oreHold: " + oreHold.length);
                dropMax = Integer.parseInt(oreHold[2]);
                dropMin = Integer.parseInt(oreHold[1]);
                oreMeta = 0;
                oreDrop = oreHold[0];
            }
            */
        }
    }
}
