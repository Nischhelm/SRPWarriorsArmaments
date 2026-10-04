package com.sweeblyn.srpwarriorsarmaments.items;

import java.util.UUID;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemBlazesteelArmor extends ItemArmor {

	private final AttributeModifier modifier;
	private final IAttribute type;

	public ItemBlazesteelArmor(String regName, ArmorMaterial material, EntityEquipmentSlot equipmentSlot) {
		super(material, 0, equipmentSlot);

		this.setRegistryName(regName);
		this.setTranslationKey(regName);

		this.setCreativeTab(SRPWarriorsArmaments.tab);

		modifier = new AttributeModifier(UUID.fromString("57d7eee7-28d3-495a-a24d-fc5e3f5effe6"), "blazesteelarmor", 10,
				0);
		type = SharedMonsterAttributes.MAX_HEALTH;
	}

	@SubscribeEvent
	public static void onLivingHurt(final LivingHurtEvent e) {
		if (e.getEntityLiving() instanceof EntityPlayer&&e.getSource().getTrueSource() instanceof EntityParasiteBase) {
			int numPieces = getTotalPieces((EntityPlayer) e.getEntityLiving());
			
			if (numPieces == 4) {
				System.out.println("reducing " +e.getAmount()+ " to " + (e.getAmount()*0.4f));
				e.setAmount(e.getAmount()*0.4f);
			}
		}
	}

	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
		if (world.isRemote) {
			return;
		}
		IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(type);
		int numPieces = getTotalPieces(player);

		if (numPieces == 4) {
			if (!attr.hasModifier(modifier)) {
				attr.applyModifier(modifier);
			}
		} else {
			if (attr.hasModifier(modifier)) {
				attr.removeModifier(modifier);
			}
		}

	}

	public static int getTotalPieces(EntityPlayer player) {
		int amount = 0;
		for (ItemStack armorPiece : player.getArmorInventoryList()) {
			if (armorPiece.getItem() instanceof ItemBlazesteelArmor) {
				amount++;
			}
		}

		return amount;
	}
}
