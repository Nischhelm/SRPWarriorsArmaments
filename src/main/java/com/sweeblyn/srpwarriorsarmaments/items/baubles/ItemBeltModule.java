package com.sweeblyn.srpwarriorsarmaments.items.baubles;

import javax.annotation.Nonnull;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPAdapted;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPAncient;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPAssimara;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPBeckon;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPCrude;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPDerived;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPDispatcher;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPFeral;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPHijacked;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPInfected;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPPreeminent;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPPrimitive;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPPure;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPRooter;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPStationary;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityAta;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityButhol;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityGothol;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityKol;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityLodo;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityMor;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityMudo;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityNuuh;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityRathol;
import com.dhanantry.scapeandrunparasites.entity.monster.inborn.EntityViin;
import com.dhanantry.scapeandrunparasites.item.ItemModule;
import com.dhanantry.scapeandrunparasites.item.ItemModule.Kind;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WABaubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemBeltModule extends Item implements IBauble {

	public ItemBeltModule(String name) {
		super();
		this.setRegistryName(name);
		this.setTranslationKey(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}

	@Override
	public BaubleType getBaubleType(ItemStack item) {
		return BaubleType.BELT;
	}

	@SubscribeEvent
	public static void onLivingHurt(LivingHurtEvent event) {
		if (!event.getEntity().world.isRemote && event.getEntityLiving() instanceof EntityPlayer && BaublesApi.isBaubleEquipped((EntityPlayer) event.getEntityLiving(), WABaubles.belt_module) != -1) {
			Entity atkr = event.getSource().getTrueSource();
			ItemStack stack = ((BaublesApi.getBaublesHandler((EntityPlayer) event.getEntityLiving()))
					.getStackInSlot(3));
			if (atkr instanceof EntityParasiteBase && !stack.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).isEmpty()) {
				Item moduleStack = new ItemStack((NBTTagCompound) stack.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).getTag("beltmodule")).getItem();
				ItemModule module = (ItemModule) moduleStack;
				Kind k = module.getKind();

				System.out.println(shouldApple(k, atkr));
				if (shouldApple(k, atkr)) { // free will
					System.out.println("setting " + event.getAmount() + " to " + event.getAmount() / 2);
					event.setAmount(event.getAmount() / 2);
					
				} else if (k.equals(Kind.VECTORS)) {

				} else if (k.equals(Kind.PHASE)) {

				} else if (k.equals(Kind.DISLODGEMENT)) {

				}
			}
		}
	}

	@Override
	@Nonnull
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player,
			@Nonnull EnumHand hand) {
		ItemStack stackMain = player.getHeldItem(hand);
		ItemStack stackOther = player.getHeldItem(hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
		if (player.isSneaking()) {
			if (!world.isRemote) {
				if (!stackMain.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).isEmpty()) {
					ItemStack moduleReturn = new ItemStack((NBTTagCompound) stackMain
							.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).getTag("beltmodule"));
					final EntityItem entityItem = new EntityItem(world, player.posX, player.posY, player.posZ,
							moduleReturn);
					entityItem.setNoPickupDelay();
					world.spawnEntity((Entity) entityItem);
					stackMain.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).removeTag("beltmodule");

					player.world.playSound(null, player.posX, player.posY, player.posZ,
							SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.PLAYERS, 0.7F, 1.0F);
				}

				if (stackOther.getItem() instanceof ItemModule) {
					Item moduleStack = stackOther.getItem();
					ItemModule moduleM = (ItemModule) moduleStack;

					Kind k = moduleM.getKind();

					if (k.equals(Kind.VECTORS) || k.equals(Kind.PHASE) || k.equals(Kind.DISLODGEMENT)) {
						player.world.playSound(null, player.posX, player.posY, player.posZ,
								SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.PLAYERS, 0.7F, 0.5F);
						return super.onItemRightClick(world, player, hand);
					}

					NBTTagCompound module = stackOther.serializeNBT();
					stackMain.getOrCreateSubCompound(SRPWarriorsArmaments.MOD_ID).setTag("beltmodule", module);
					stackOther.shrink(1);

					player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_PISTON_EXTEND,
							SoundCategory.PLAYERS, 0.7F, 1.0F);

				}
			}
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
		} else if (!world.isRemote) {
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

	public static boolean shouldApple(Kind kind, Entity target) {
		if (kind.equals(Kind.INBORN) && isInborn(target)) {
			return true;
		} else if (kind.equals(Kind.ADAPTED) && target instanceof EntityPAdapted) {
			return true;
		} else if (kind.equals(Kind.ANCIENT) && target instanceof EntityPAncient) {
			return true;
		} else if (kind.equals(Kind.ASSIMARA) && target instanceof EntityPAssimara) {
			return true;
		} else if (kind.equals(Kind.NEXUS) && (target instanceof EntityPBeckon || target instanceof EntityPDispatcher
				|| target instanceof EntityPRooter)) {
			return true;
		} else if (kind.equals(Kind.CRUDE) && target instanceof EntityPCrude) {
			return true;
		} else if (kind.equals(Kind.DERIVED) && target instanceof EntityPDerived) {
			return true;
		} else if (kind.equals(Kind.FERAL) && target instanceof EntityPFeral) {
			return true;
		} else if (kind.equals(Kind.HIJACKED) && target instanceof EntityPHijacked) {
			return true;
		} else if (kind.equals(Kind.ASSIMILATED) && target instanceof EntityPInfected) {
			return true;
		} else if (kind.equals(Kind.PREEMINENT) && target instanceof EntityPPreeminent) {
			return true;
		} else if (kind.equals(Kind.PRIMITIVE) && target instanceof EntityPPrimitive) {
			return true;
		} else if (kind.equals(Kind.PURE) && target instanceof EntityPPure) {
			return true;
		} else if (kind.equals(Kind.DETERRENT) && target instanceof EntityPStationary) {
			return true;
		} else if (kind.equals(Kind.DESMOID) && (isInborn(target) || target instanceof EntityPInfected
				|| target instanceof EntityPAssimara || target instanceof EntityPHijacked)) {
			return true;
		} else if (kind.equals(Kind.ESCHAR) && (target instanceof EntityPFeral || target instanceof EntityPCrude
				|| target instanceof EntityPPrimitive)) {
			return true;
		} else if (kind.equals(Kind.RESISTANCE) && (target instanceof EntityPAdapted || target instanceof EntityPBeckon
				|| target instanceof EntityPDispatcher || target instanceof EntityPRooter
				|| target instanceof EntityPStationary)) {
			return true;
		} else if (kind.equals(Kind.IDEAL) && (target instanceof EntityPPure || target instanceof EntityPPreeminent
				|| target instanceof EntityPDerived || target instanceof EntityPAncient)) {
			return true;
		} else if (kind.equals(Kind.ORIGIN) && target instanceof EntityParasiteBase) {
			return true;
		}

		return false;
	}

	public static boolean isInborn(Entity target) {
		if (target instanceof EntityAta || target instanceof EntityButhol || target instanceof EntityGothol
				|| target instanceof EntityKol || target instanceof EntityLodo || target instanceof EntityMor
				|| target instanceof EntityMudo || target instanceof EntityNuuh || target instanceof EntityRathol
				|| target instanceof EntityViin) {
			return true;
		}
		return false;
	}

//	public boolean apple(EntityLivingBase target) {
//		final int hurtResistantTime = target.hurtResistantTime;
//		target.hurtResistantTime = 0;
//		target.attackEntityFrom(DamageSource.MAGIC, 15.5f);
//		target.hurtResistantTime = hurtResistantTime;
//		return true;
//	}
}
