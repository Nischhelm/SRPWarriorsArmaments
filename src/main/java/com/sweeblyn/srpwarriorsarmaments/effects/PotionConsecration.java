package com.sweeblyn.srpwarriorsarmaments.effects;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.misc.damagesources.WADamageSources;

import com.sweeblyn.srpwarriorsarmaments.mixin.EntityParasiteBaseAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionConsecration extends Potion {
	private final ResourceLocation potionIcon;

	public PotionConsecration() {
		super(false, 0xffcb00);
		this.setBeneficial();
		this.setRegistryName("consecration");
		this.setPotionName("effect.consecration.name");

		potionIcon = new ResourceLocation(SRPWarriorsArmaments.MOD_ID + ":textures/potions/consecration.png");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		EntityLivingBase ent = event.getEntityLiving();
		if (event.getEntityLiving().isPotionActive(this) && ent instanceof EntityParasiteBase
				&& !event.getEntityLiving().isPotionActive(MobEffects.FIRE_RESISTANCE)) {
			int amp = ent.getActivePotionEffect(this).getAmplifier();
			if (event.getSource().equals(DamageSource.IN_FIRE) || event.getSource().equals(DamageSource.ON_FIRE)
					|| event.getSource().equals(DamageSource.LAVA)) {
				event.setAmount(event.getAmount() / 4);
				return;
			} else if (event.getSource().equals(WADamageSources.CONSECRATION)) {
				return;
			}
			// event.setAmount(event.getAmount() * (1 + (0.2f * (amp + 1))));

			final int hurtResistantTime = ent.hurtResistantTime;
			ent.hurtResistantTime = 0;
			ent.attackEntityFrom(WADamageSources.CONSECRATION, event.getAmount() * ((0.2f * (amp + 1))));
			ent.hurtResistantTime = hurtResistantTime;
		}
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn instanceof EntityParasiteBase
				&& !entityLivingBaseIn.isPotionActive(MobEffects.FIRE_RESISTANCE)) {
			EntityParasiteBaseAccessor para = (EntityParasiteBaseAccessor) entityLivingBaseIn;
			para.setKillcount(Math.max(0, para.getKillcount() - 1));
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return false;
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

}
