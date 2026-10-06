package com.sweeblyn.srpwarriorsarmaments.effects;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WABaubles;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;

import baubles.api.BaublesApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionDivineProtection  extends Potion {
	private final ResourceLocation potionIcon;

	public PotionDivineProtection() {
		super(false, 0xfad64a);
		this.setBeneficial();
		this.setRegistryName("divine");
		this.setPotionName("effect.divine.name");

		potionIcon = new ResourceLocation(SRPWarriorsArmaments.MOD_ID + ":textures/potions/divine.png");
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@SubscribeEvent
	public void onHurt(LivingHurtEvent event) {
		if (event.getEntityLiving().isPotionActive(this)) {
			EntityLivingBase ent = event.getEntityLiving();
			int amp = ent.getActivePotionEffect(this).getAmplifier();
			int dura = ent.getActivePotionEffect(this).getDuration();
			
			if (event.getSource().getTrueSource() instanceof EntityParasiteBase) {
				float abs = ent.getAbsorptionAmount()+(event.getAmount()/4);
				//BaublesApi.isBaubleEquipped((EntityPlayer) e.getSource().getTrueSource(), WABaubles.ring_smite) != -1
				int absCap = 20;
				
				if (Loader.isModLoaded("baubles")&&ent instanceof EntityPlayer&&BaublesApi.isBaubleEquipped((EntityPlayer) ent, WABaubles.charm_emblem) != -1) {
					absCap += 10;
				}
				
				if (abs > absCap) {
					ent.setAbsorptionAmount(absCap);
				} else {
					ent.setAbsorptionAmount(abs);
				}
				event.setAmount(0);
				if(amp==0) {
					for(int i=0;i<5;i++) {
						ent.world.playSound(null, ent.posX, ent.posY, ent.posZ, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.PLAYERS, 2.5F, 1.5F);
					}
				} else {
					ent.world.playSound(null, ent.posX, ent.posY, ent.posZ, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.PLAYERS, 2.5F, Math.min(2, 0.75f+(0.25f*(amp+1))));
				}
				ent.removePotionEffect(this);
				if (amp-1 >= 0) {
					ent.addPotionEffect(new PotionEffect(WAPotions.DIVINE, dura, amp-1  , false, true));
				}
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
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

}
