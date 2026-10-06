package com.sweeblyn.srpwarriorsarmaments.effects;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.mixin.EntityParasiteBaseAccessor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionFeeble extends Potion {
	private final ResourceLocation potionIcon;
	
	public PotionFeeble() {
		super(false, 0xffcb00);
		this.setBeneficial();
		this.setRegistryName("feeble");
		this.setPotionName("effect.feeble.name");

		potionIcon = new ResourceLocation(SRPWarriorsArmaments.MOD_ID + ":textures/potions/feeble.png");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn instanceof EntityParasiteBase) {
			
			final NBTTagCompound tags = entityLivingBaseIn.getEntityData();

			EntityParasiteBase para = (EntityParasiteBase) entityLivingBaseIn;
			EntityParasiteBaseAccessor paraAcc = (EntityParasiteBaseAccessor) entityLivingBaseIn;

			if (entityLivingBaseIn.getActivePotionEffect(this).getDuration() <= 2) {
				paraAcc.setMiniDamage(tags.getFloat("srpwa_storedmindamage"));
			}
			else if (!tags.hasKey("srpwa_storedmindamage")) {
				tags.setFloat("srpwa_storedmindamage", para.getMiniDamage());
			} else {
				paraAcc.setMiniDamage(Math.max(tags.getFloat("srpwa_storedmindamage")*(1-(0.2f*(amplifier+1))), 0.05f));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return false;
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	public boolean shouldRenderInvText(PotionEffect effect) {
		return true;
	}

	@Override
	public boolean shouldRenderHUD(PotionEffect effect) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		if (mc.currentScreen != null) {
			mc.getTextureManager().bindTexture(potionIcon);
			Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(potionIcon);
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}

//	private float getTypeMinDmg(EntityParasiteBase par) {
//		if (par.getParasiteType().equals()) {
//			
//		}
//		return
//	}
}
