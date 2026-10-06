package com.sweeblyn.srpwarriorsarmaments.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import com.dhanantry.scapeandrunparasites.entity.EntityOrbScary;
import com.llamalad7.mixinextras.sugar.Local;
import com.sweeblyn.srpwarriorsarmaments.items.ItemBlazesteelArmor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

@Mixin(EntityOrbScary.class)
public abstract class MixinParasiteOrbDamageImmunity {
	@ModifyConstant(method = "selfExplode()V",  constant = @Constant(floatValue = 5.0F), remap = false)
	private float srpwarriorsarmaments_orbDamageImmunity(float value, @Local(ordinal = 0) EntityLivingBase mob) {
		if (mob instanceof EntityPlayer) {
			EntityPlayer p = (EntityPlayer) mob;
			return ItemBlazesteelArmor.getTotalPieces(p) == 4 ? 0 : 5.0f ;
		}
		return 5.0f;
	}
}



