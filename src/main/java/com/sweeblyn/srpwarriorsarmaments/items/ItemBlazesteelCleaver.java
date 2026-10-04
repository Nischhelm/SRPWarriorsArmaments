package com.sweeblyn.srpwarriorsarmaments.items;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.google.common.collect.Multimap;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;
import com.sweeblyn.srpwarriorsarmaments.misc.WAToolMaterials;
import com.sweeblyn.srpwarriorsarmaments.misc.damagesources.WADamageSources;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;

public class ItemBlazesteelCleaver extends ItemSword {

	public ItemBlazesteelCleaver() {
		super(WAToolMaterials.BLAZESTEEL);
		this.setRegistryName("blazesteel_cleaver");
		this.setTranslationKey("blazesteel_cleaver");
	}

	public ItemBlazesteelCleaver(ToolMaterial material) {
		super(material);
		this.setRegistryName("blazesteel_cleaver");
		this.setTranslationKey("blazesteel_cleaver");
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}
	
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity targetEntity) {
		if (shouldApply(attacker)&&targetEntity instanceof EntityLivingBase) {
			EntityLivingBase target = (EntityLivingBase) targetEntity;
			float proc = 0.5f;
			if (target.isPotionActive(WAPotions.CONSECRATION)) {
				int amp = target.getActivePotionEffect(WAPotions.CONSECRATION).getAmplifier();
				
				final int hurtResistantTime = target.hurtResistantTime;
				for (int i=0; i<3;i++) {
					target.hurtResistantTime = 0;
					target.attackEntityFrom(WADamageSources.CONSECRATION, (amp+1)*3);
					target.hurtResistantTime = hurtResistantTime;
				}
				target.world.createExplosion(attacker, target.posX, target.posY + 1.0, target.posZ, 0.5f, false);
				target.world.playSound(null, attacker.posX, attacker.posY, attacker.posZ, SoundEvents.ENTITY_PLAYER_HURT_ON_FIRE, SoundCategory.PLAYERS, 0.7F, 0.5F);
				target.removePotionEffect(WAPotions.CONSECRATION);
				proc = proc*1.5f;
			}
			
			if (Math.random() < proc) {
				target.addPotionEffect(new PotionEffect(WAPotions.CONSECRATION, 200, 0, true, true));
			}
			

		}
		return super.onLeftClickEntity(stack, attacker, targetEntity);
	}
	
	private boolean shouldApply(EntityLivingBase player) {
		return player instanceof EntityPlayer && ((EntityPlayer) player).getCooledAttackStrength(1f) > 0.9;
	}
	
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slotIn, stack);
 
        if (slotIn == EntityEquipmentSlot.MAINHAND) 
      	{
            replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, -2.9);
            replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 16);
        }
      
        return modifiers;
    }
 
    private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double value)
    {
        // Get the modifiers for the specified attribute
        final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getName());

        // Find the modifier with the specified ID, if any
        final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();
 
        if (modifierOptional.isPresent()) 
      	{
            final AttributeModifier modifier = modifierOptional.get();
      
            modifiers.remove(modifier); // Remove it
            modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), value, 0));
        }
    }
}
