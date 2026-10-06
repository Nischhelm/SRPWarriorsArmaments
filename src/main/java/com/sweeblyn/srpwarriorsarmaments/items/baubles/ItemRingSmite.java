package com.sweeblyn.srpwarriorsarmaments.items.baubles;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WABaubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemRingSmite extends Item implements IBauble {

	public static final Item RING = null;

	public ItemRingSmite(String name) {

		super();
		this.setRegistryName(name);
		this.setTranslationKey(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}

	@Override
	public BaubleType getBaubleType(ItemStack item) {
		return BaubleType.RING;
	}

	@SubscribeEvent
	public static void onLivingAttack(final LivingDamageEvent e) {
		if ((e.getEntityLiving() instanceof EntityParasiteBase)
				&& ((e.getSource().getTrueSource() instanceof EntityPlayer))
				&& (BaublesApi.isBaubleEquipped((EntityPlayer) e.getSource().getTrueSource(), WABaubles.ring_smite) != -1)) {
			
			EntityLivingBase target = e.getEntityLiving();
			final int hurtResistantTime = target.hurtResistantTime;

			target.hurtResistantTime = 0;
			target.attackEntityFrom(DamageSource.MAGIC, 4);
			target.hurtResistantTime = hurtResistantTime;
		} 
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
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
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}
}
