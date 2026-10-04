package com.sweeblyn.srpwarriorsarmaments.items;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemRancidStomach extends Item {

	public ItemRancidStomach() {
		super();
		this.setRegistryName("rancid_stomach");
		this.setTranslationKey("rancid_stomach");
		this.setMaxStackSize(16);
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!world.isRemote) {
			if (Math.random() < 0.25f) {
				final ItemStack stackW = new ItemStack(SRPItems.hive_scrap, 1);
				final EntityItem entityitem = new EntityItem(world, player.posX, player.posY, player.posZ, stackW);
				entityitem.setNoPickupDelay();
				world.spawnEntity((Entity) entityitem);
			}
			stack.shrink(1);
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 0.7F, 1.0F);
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.PLAYERS, 0.7F, 2.0F);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
}
