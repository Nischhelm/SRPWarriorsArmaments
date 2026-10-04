package com.sweeblyn.srpwarriorsarmaments.event;

import java.util.Random;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPPure;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.sweeblyn.srpwarriorsarmaments.init.WAItems;

import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler { //thanks eevee <3
	public static final Random RAND;

	@SubscribeEvent
	public static void onLootCalc(final LivingDropsEvent e) {
		if (!e.getEntity().world.getGameRules().getBoolean("doMobLoot")) {
			return;
		}
		if (e.getEntity() instanceof EntityParasiteBase) {
			final EntityParasiteBase parasite = (EntityParasiteBase) e.getEntity();

			if (parasite instanceof EntityPPure && EventHandler.RAND.nextInt(4) == 0 && !parasite.isPotionActive(SRPPotions.DEBAR_E)) {
				parasite.dropItem(WAItems.rancid_stomach, 1);
			}
		}
	}
	
	static {
        RAND = new Random();
    }
}
