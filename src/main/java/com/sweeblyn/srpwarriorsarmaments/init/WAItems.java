package com.sweeblyn.srpwarriorsarmaments.init;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.sweeblyn.srpwarriorsarmaments.ModelHelper;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelArmor;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelCleaver;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelGladiatorChestplate;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelKnife;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelMultitool;
import com.sweeblyn.srpwarriorsarmaments.items.ItemGildedHiveBane;
import com.sweeblyn.srpwarriorsarmaments.items.ItemGlinted;
import com.sweeblyn.srpwarriorsarmaments.items.ItemRancidStomach;
import com.sweeblyn.srpwarriorsarmaments.items.ItemSemiOrganicArmor;
import com.sweeblyn.srpwarriorsarmaments.items.ItemSemiOrganicSword;
import com.sweeblyn.srpwarriorsarmaments.items.ItemThrongler;
import com.sweeblyn.srpwarriorsarmaments.items.ItemWA;
import com.sweeblyn.srpwarriorsarmaments.misc.WAToolMaterials;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class WAItems {
	public static final Item semi_organic_sword = (Item) new ItemSemiOrganicSword(WAToolMaterials.SEMI_ORGANIC_SWORD);
	public static final Item hive_bane = (Item) new ItemGildedHiveBane(WAToolMaterials.GILDED_HIVE_BANE);
	public static final Item throngler = (Item) new ItemThrongler(WAToolMaterials.THRONGLER);

	public static final Item semi_organic_helmet = (Item) new ItemSemiOrganicArmor("semi_organic_helmet",
			WAToolMaterials.SEMI_ORGANIC_ARMOR, EntityEquipmentSlot.HEAD);
	public static final Item semi_organic_chestplate = (Item) new ItemSemiOrganicArmor("semi_organic_chestplate",
			WAToolMaterials.SEMI_ORGANIC_ARMOR, EntityEquipmentSlot.CHEST);
	public static final Item semi_organic_leggings = (Item) new ItemSemiOrganicArmor("semi_organic_leggings",
			WAToolMaterials.SEMI_ORGANIC_ARMOR, EntityEquipmentSlot.LEGS);
	public static final Item semi_organic_boots = (Item) new ItemSemiOrganicArmor("semi_organic_boots",
			WAToolMaterials.SEMI_ORGANIC_ARMOR, EntityEquipmentSlot.FEET);
	
	public static final Item blazesteel_helmet = (Item) new ItemBlazesteelArmor("blazesteel_helmet",
			WAToolMaterials.BLAZESTEEL_ARMOR, EntityEquipmentSlot.HEAD);
	public static final Item blazesteel_chestplate = (Item) new ItemBlazesteelArmor("blazesteel_paladin_chestplate",
			WAToolMaterials.BLAZESTEEL_ARMOR, EntityEquipmentSlot.CHEST);
	public static final Item blazesteel_gladiator_chestplate = (Item) new ItemBlazesteelGladiatorChestplate("blazesteel_gladiator_chestplate",
			WAToolMaterials.BLAZESTEEL_ARMOR_GLADIATOR, EntityEquipmentSlot.CHEST);
	public static final Item blazesteel_leggings = (Item) new ItemBlazesteelArmor("blazesteel_leggings",
			WAToolMaterials.BLAZESTEEL_ARMOR, EntityEquipmentSlot.LEGS);
	public static final Item blazesteel_boots = (Item) new ItemBlazesteelArmor("blazesteel_boots",
			WAToolMaterials.BLAZESTEEL_ARMOR, EntityEquipmentSlot.FEET);

	public static final Item blazesteel_dormant = (Item) new ItemWA("blazesteel_dormant");
	public static final Item blazesteel = (Item) new ItemWA("blazesteel");
	public static final Item blazesteel_nugget = (Item) new ItemWA("blazesteel_nugget");

	public static final Item purified_nether_star = (Item) new ItemGlinted("purified_nether_star");

	public static final Item blazesteel_knife = (Item) new ItemBlazesteelKnife(WAToolMaterials.BLAZESTEEL);
	public static final Item blazesteel_cleaver = (Item) new ItemBlazesteelCleaver(WAToolMaterials.BLAZESTEEL);
	public static final Item blazesteel_multitool = (Item) new ItemBlazesteelMultitool(WAToolMaterials.BLAZESTEEL);
	
	public static final Item quench_super = (Item) new ItemWA("quench_super");
	
	public static final Item rancid_stomach = (Item) new ItemRancidStomach();
	
	public static final Item hivesilver = (Item) new ItemWA("hivesilver");
	public static final Item hivesilver_tainted = (Item) new ItemWA("hivesilver_tainted");
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> e) {
		for (Field f : WAItems.class.getDeclaredFields()) {
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
