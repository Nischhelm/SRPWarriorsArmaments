package com.sweeblyn.srpwarriorsarmaments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sweeblyn.srpwarriorsarmaments.handlers.DisplayTooltipsHandler;
import com.sweeblyn.srpwarriorsarmaments.init.WABaubles;
import com.sweeblyn.srpwarriorsarmaments.init.WAItems;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotionTypes;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;
import com.sweeblyn.srpwarriorsarmaments.init.WARecipes;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = SRPWarriorsArmaments.MOD_ID, dependencies = SRPWarriorsArmaments.DEPENDANCY)
public class SRPWarriorsArmaments {
	
    public static final CreativeTabs tab;
    public static SRPWarriorsArmaments instance;
    public static final Logger LOGGER;
	
    public static final String MOD_ID = "srpwarriorsarmaments";
    
    public static final String DEPENDANCY = "required-after:srparasites";

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(WAItems.class);
        MinecraftForge.EVENT_BUS.register(WAPotions.class);
        MinecraftForge.EVENT_BUS.register(WAPotionTypes.class);
        MinecraftForge.EVENT_BUS.register(WARecipes.class);
        MinecraftForge.EVENT_BUS.register(DisplayTooltipsHandler.class);
        
        if (Loader.isModLoaded("baubles")) {
			MinecraftForge.EVENT_BUS.register(WABaubles.class);
		}
    }
    
    static {
        SRPWarriorsArmaments.instance = null;
        LOGGER = LogManager.getLogger("srpwarriorsarmaments");
        tab = new CreativeTabs("srpwarriorsarmaments.name") {
            public ItemStack createIcon() {
                return new ItemStack(WAItems.semi_organic_sword);
            }
        };
    }
}
