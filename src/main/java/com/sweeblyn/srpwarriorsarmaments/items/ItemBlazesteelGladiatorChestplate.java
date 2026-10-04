package com.sweeblyn.srpwarriorsarmaments.items;

import com.dhanantry.scapeandrunparasites.entity.EntityOrbScary;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.misc.damagesources.WADamageSources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class ItemBlazesteelGladiatorChestplate extends ItemArmor {

	public ItemBlazesteelGladiatorChestplate(String regName, ArmorMaterial material,
			EntityEquipmentSlot equipmentSlot) {
		super(material, 0, equipmentSlot);

		this.setRegistryName(regName);
		this.setTranslationKey(regName);

		this.setCreativeTab(SRPWarriorsArmaments.tab);

	}

	@SubscribeEvent
	public static void onLivingHurt(final LivingHurtEvent e) {
		if (e.getEntityLiving() instanceof EntityPlayer
				&& e.getSource().getTrueSource() instanceof EntityParasiteBase) {
			int numPieces = getTotalPieces((EntityPlayer) e.getEntityLiving());

			if (numPieces == 3) {
				e.setAmount(e.getAmount() * 0.8f);
			}
		}
	}

	@SubscribeEvent
	public static void onLivingAttack(final LivingDamageEvent e) {
		if ((e.getEntityLiving() instanceof EntityParasiteBase)
				&& ((e.getSource().getTrueSource() instanceof EntityPlayer))) {
			EntityPlayer attacker = (EntityPlayer) e.getSource().getTrueSource();
			
			if (isWearingGladiator(attacker)) {
				int numPieces = getTotalPieces(attacker);
				EntityLivingBase target = e.getEntityLiving();
				final int hurtResistantTime = target.hurtResistantTime;

				target.hurtResistantTime = 0;
				target.attackEntityFrom(WADamageSources.CONSECRATION, e.getAmount());
				target.hurtResistantTime = hurtResistantTime;
			}
		}

	}

	public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
		if (world.isRemote) {
			return;
		}

		int numPieces = getTotalPieces(player);

		if (numPieces == 3) {
			if (player.isPotionActive(MobEffects.SLOWNESS)) {
				player.removePotionEffect(MobEffects.SLOWNESS);
			}
		}

		if (player.ticksExisted % 10 == 0) {
			Vec3d center = player.getPositionVector();
			AxisAlignedBB box = new AxisAlignedBB(center.x, center.y, center.z, center.x, center.y, center.z).grow(1);
			for (Entity entity : player.world.getEntitiesWithinAABB(Entity.class, box,
					e -> !(e instanceof EntityPlayer))) {
				if (entity instanceof EntityOrbScary) {
					if (player.isPotionActive(SRPPotions.RAGE_E)) {
						player.addPotionEffect(new PotionEffect(SRPPotions.RAGE_E, 200,
								player.getActivePotionEffect(SRPPotions.RAGE_E).getAmplifier() + 1, false, true));
					} else {
						player.addPotionEffect(new PotionEffect(SRPPotions.RAGE_E, 200, 0, false, true));
					}
				}

			}
		}
	}

	private static int getTotalPieces(EntityPlayer player) {
		int amount = 0;
		for (ItemStack armorPiece : player.getArmorInventoryList()) {
			if (armorPiece.getItem() instanceof ItemBlazesteelArmor) {
				amount++;
			}
		}

		return amount;
	}

	private static boolean isWearingGladiator(EntityPlayer player) {
		for (ItemStack armorPiece : player.getArmorInventoryList()) {
			if (armorPiece.getItem() instanceof ItemBlazesteelGladiatorChestplate) {
				return true;
			}
		}

		return false;
	}
}
