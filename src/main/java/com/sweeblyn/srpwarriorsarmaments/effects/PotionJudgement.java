package com.sweeblyn.srpwarriorsarmaments.effects;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPMalleable;
import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.world.SRPSaveData;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;

import com.sweeblyn.srpwarriorsarmaments.mixin.EntityPMalleableAccessor;
import com.sweeblyn.srpwarriorsarmaments.mixin.EntityParasiteBaseAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionJudgement extends Potion {
	private final ResourceLocation potionIcon;

	public PotionJudgement() {
		super(false, 0xffcb00);
		this.setBeneficial();
		this.setRegistryName("judgement");
		this.setPotionName("effect.judgement.name");

		potionIcon = new ResourceLocation(SRPWarriorsArmaments.MOD_ID + ":textures/potions/judgement.png");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public void performEffect(EntityLivingBase entityLivingBaseIn, int amplifier) {
		if (entityLivingBaseIn instanceof EntityParasiteBase) {
			float atkSpeed = 0.75f * (amplifier + 1.0f) + 0.75f;
			final SRPSaveData dataS = SRPSaveData.get(entityLivingBaseIn.world, 82);
			final int id = entityLivingBaseIn.world.provider.getDimension();
			if (entityLivingBaseIn.isPotionActive(SRPPotions.PIVOT_E)) {
				atkSpeed = (0.5f*amplifier)+1.0f;
			}

			EntityParasiteBaseAccessor paraAcc = (EntityParasiteBaseAccessor) entityLivingBaseIn;
			if (entityLivingBaseIn instanceof EntityPMalleable) {
				EntityPMalleableAccessor mallAcc = (EntityPMalleableAccessor) entityLivingBaseIn;

				((EntityParasiteBase) entityLivingBaseIn).applyGene(
						new boolean[]{
								paraAcc.getGeneMindam(),
								paraAcc.getGeneDamcap(),
								paraAcc.getGeneLookwall(),
								paraAcc.getGeneSprinting(),
								paraAcc.getGeneWaterleap(),
								paraAcc.getGeneSpecialmove(),

								mallAcc.getGeneAdaptation(),
								mallAcc.getGeneBlocksearch(),
								mallAcc.getGeneResidue(),
								mallAcc.getGeneOrbbox()
						},

						new float[]{
								paraAcc.getGenePoisonHealing(),
								paraAcc.getGeneMobHealing(),
								entityLivingBaseIn.getActivePotionEffect(this).getDuration() > 1 ? atkSpeed : dataS.getGeneModi2(id)[2]
						}
				);
			} else {
				((EntityParasiteBase) entityLivingBaseIn).applyGene(
						new boolean[]{
								paraAcc.getGeneMindam(),
								paraAcc.getGeneDamcap(),
								paraAcc.getGeneLookwall(),
								paraAcc.getGeneSprinting(),
								paraAcc.getGeneWaterleap(),
								paraAcc.getGeneSpecialmove()
						},

						new float[]{
								paraAcc.getGenePoisonHealing(),
								paraAcc.getGeneMobHealing(),
								entityLivingBaseIn.getActivePotionEffect(this).getDuration() > 1 ? atkSpeed : dataS.getGeneModi2(id)[2]
						});
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

}
