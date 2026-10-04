package com.sweeblyn.srpwarriorsarmaments.items.baubles;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ItemBodyPlateRegen extends Item implements IBauble {

	private final AttributeModifier modifier;
	private final IAttribute type;

	public ItemBodyPlateRegen(String name) {
		super();
		this.setRegistryName(name);
		this.setTranslationKey(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(SRPWarriorsArmaments.tab);
		modifier = new AttributeModifier(UUID.fromString("ecf6e40e-591b-43e9-974b-bb3deabade0e"), "regenplate", 5, 0);
		type = SharedMonsterAttributes.ARMOR;
	}

	@Override
	public BaubleType getBaubleType(ItemStack item) {
		return BaubleType.BODY;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if (!player.world.isRemote && player.ticksExisted % 50 == 0) {
			player.getEquipmentAndArmor().forEach(equip -> {
				if (equip.getItem() instanceof ItemArmor && Math.random() > 0.5f) {
					int newDurability = equip.getItemDamage() - 1;
					equip.setItemDamage(newDurability);
				}
			});
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(type);

		if (!attr.hasModifier(modifier)) {
			attr.applyModifier(modifier);
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(type);

		if (attr.hasModifier(modifier)) {
			attr.removeModifier(modifier);
		}
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player,
			@Nonnull EnumHand hand) {
		if (!world.isRemote) {
			IBaublesItemHandler baubles = BaublesApi.getBaublesHandler(player);
			for (int i = 0; i < baubles.getSlots(); i++)
				if ((baubles.getStackInSlot(i) == null || baubles.getStackInSlot(i).isEmpty())
						&& baubles.isItemValidForSlot(i, player.getHeldItem(hand), player)) {
					baubles.setStackInSlot(i, player.getHeldItem(hand).copy());
					if (!player.capabilities.isCreativeMode) {
						player.inventory.setInventorySlotContents(player.inventory.currentItem, ItemStack.EMPTY);
					}
					onEquipped(player.getHeldItem(hand), player);
					break;
				}
		}

		return super.onItemRightClick(world, player, hand);
	}

}