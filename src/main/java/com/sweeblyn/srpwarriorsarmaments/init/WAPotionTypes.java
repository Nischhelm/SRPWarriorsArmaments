 package com.sweeblyn.srpwarriorsarmaments.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WAPotionTypes {	
	public static final PotionType CONSECRATION_T = (PotionType)new PotionType(new PotionEffect[] { new PotionEffect(WAPotions.CONSECRATION, 3600, 0) }).setRegistryName("consecration");
	public static final PotionType CONSECRATION_TL = (PotionType)new PotionType(new PotionEffect[] { new PotionEffect(WAPotions.CONSECRATION, 9600, 0) }).setRegistryName("consecration_long");
	public static final PotionType CONSECRATION_TA = (PotionType)new PotionType(new PotionEffect[] { new PotionEffect(WAPotions.CONSECRATION, 3600, 1) }).setRegistryName("consecration_amplified");
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<PotionType> e) {
		registerPotionMixes();
		for (Field f : WAPotionTypes.class.getDeclaredFields()) {
			try {
				if (Modifier.isStatic(f.getModifiers()) && f.get(null) instanceof PotionType) {
					PotionType pot = (PotionType) f.get(null);

					e.getRegistry().register(pot);
				}
			} catch (IllegalAccessException e1) {
			}
		}
	}
	
	private static void registerPotionMixes() {
		PotionHelper.addMix(PotionTypes.THICK, WAItems.quench_super, CONSECRATION_T);
		PotionHelper.addMix(CONSECRATION_T, Items.REDSTONE, CONSECRATION_TL);
		PotionHelper.addMix(CONSECRATION_T, Items.GLOWSTONE_DUST, CONSECRATION_TA);
	}
}
