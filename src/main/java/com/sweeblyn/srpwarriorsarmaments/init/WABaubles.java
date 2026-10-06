package com.sweeblyn.srpwarriorsarmaments.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.sweeblyn.srpwarriorsarmaments.ModelHelper;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemAmuletJustice;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemAmuletJusticeEye;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemBeltModule;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemBodyPlateRegen;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmBleed;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmCorrosion;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmEmblem;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmImmunity;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmViral;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemCharmVision;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemHeadGlassesHeart;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemHeadMask;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemRingConsecrate;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemRingSmite;
import com.sweeblyn.srpwarriorsarmaments.items.baubles.ItemTrinketBeckonPermit;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WABaubles {
	public static final Item amulet_justice = (Item) new ItemAmuletJustice("amulet_justice");
	public static final Item amulet_justice_eye = (Item) new ItemAmuletJusticeEye("amulet_justice_eye");

	public static final Item belt_module = (Item) new ItemBeltModule("belt_module");
	
	public static final Item ring_smite = (Item) new ItemRingSmite("ring_smite");
	public static final Item ring_consecrate = (Item) new ItemRingConsecrate("ring_consecrate");
	
	public static final Item head_mask = (Item) new ItemHeadMask("head_mask");
	public static final Item head_glasses_heart = (Item) new ItemHeadGlassesHeart("head_glasses_heart");
	
	public static final Item body_plate_regen = (Item) new ItemBodyPlateRegen("body_plate_regen");
	
	public static final Item charm_emblem = (Item) new ItemCharmEmblem("charm_emblem");
	public static final Item charm_immunity = (Item) new ItemCharmImmunity("charm_immunity");
	public static final Item charm_viral = (Item) new ItemCharmViral("charm_viral");
	public static final Item charm_corrosion = (Item) new ItemCharmCorrosion("charm_corrosion");
	public static final Item charm_bleed = (Item) new ItemCharmBleed("charm_bleed");
	public static final Item charm_vision = (Item) new ItemCharmVision("charm_vision");
	
	public static final Item trinket_beckon_permit = (Item) new ItemTrinketBeckonPermit("trinket_beckon_permit");

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		for (Field f : WABaubles.class.getDeclaredFields()) {
			try {
				if (Modifier.isStatic(f.getModifiers()) && f.get(null) instanceof Item) {
					Item item = (Item) f.get(null);

					if (item.getTranslationKey().equals("item.null")) {
						ResourceLocation regName = item.getRegistryName();
						item.setTranslationKey(regName.getNamespace() + "." + regName.getPath());
					}

					e.getRegistry().register(item);
					ModelHelper.registerItemModel(item);
				}
			} catch (IllegalAccessException e1) {
			}
		}
	}
}
