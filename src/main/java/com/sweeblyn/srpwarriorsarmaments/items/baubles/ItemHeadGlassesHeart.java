package com.sweeblyn.srpwarriorsarmaments.items.baubles;

import java.util.UUID;

import javax.annotation.Nonnull;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;

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
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemHeadGlassesHeart extends Item implements IBauble {

	public static final Item RING = null;
	private final AttributeModifier modifier;
	private final IAttribute type;

	public ItemHeadGlassesHeart(String name) {

		super();
		this.setRegistryName(name);
		this.setTranslationKey(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(SRPWarriorsArmaments.tab);
		modifier = new AttributeModifier(UUID.fromString("e6663988-bf2c-49be-bbef-50fe97b9573b"), "heartglasses", 0.25, 1);
		type = SharedMonsterAttributes.MAX_HEALTH;
	}

	@Override
	public BaubleType getBaubleType(ItemStack item) {
		return BaubleType.HEAD;
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
