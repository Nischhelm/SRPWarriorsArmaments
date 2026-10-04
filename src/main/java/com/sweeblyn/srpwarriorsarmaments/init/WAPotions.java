 package com.sweeblyn.srpwarriorsarmaments.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.sweeblyn.srpwarriorsarmaments.effects.PotionConsecration;
import com.sweeblyn.srpwarriorsarmaments.effects.PotionDivineProtection;
import com.sweeblyn.srpwarriorsarmaments.effects.PotionFeeble;
import com.sweeblyn.srpwarriorsarmaments.effects.PotionJudgement;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WAPotions {
	public static final Potion DIVINE = new PotionDivineProtection();
	public static final Potion CONSECRATION = new PotionConsecration();
	public static final Potion JUDGEMENT = new PotionJudgement();//mino prim
	public static final Potion FEEBLE = new PotionFeeble();
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Potion> e) {
		for (Field f : WAPotions.class.getDeclaredFields()) {
			try {
				if (Modifier.isStatic(f.getModifiers()) && f.get(null) instanceof Potion) {
					Potion pot = (Potion) f.get(null);

					e.getRegistry().register(pot);
				}
			} catch (IllegalAccessException e1) {
			}
		}
	}
}
