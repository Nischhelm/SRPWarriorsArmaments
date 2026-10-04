package com.sweeblyn.srpwarriorsarmaments.items;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import com.google.common.collect.Multimap;
import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;
import com.sweeblyn.srpwarriorsarmaments.misc.WAToolMaterials;

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
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;

public class ItemBlazesteelKnife extends ItemSword {

	public ItemBlazesteelKnife() {
		super(WAToolMaterials.BLAZESTEEL);
		this.setRegistryName("blazesteel_knife");
		this.setTranslationKey("blazesteel_knife");
	}

	public ItemBlazesteelKnife(ToolMaterial material) {
		super(material);
		this.setRegistryName("blazesteel_knife");
		this.setTranslationKey("blazesteel_knife");
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer attacker, Entity targetEntity) {
		if (shouldApply(attacker)&&targetEntity instanceof EntityParasiteBase) {
			EntityLivingBase target = (EntityLivingBase) targetEntity;
			if (target.isPotionActive(WAPotions.CONSECRATION)) {
				int amp = target.getActivePotionEffect(WAPotions.CONSECRATION).getAmplifier();
				if (amp >= 0 && amp <= 9 && (Math.random() < 0.66)) {
					target.addPotionEffect(new PotionEffect(WAPotions.CONSECRATION, 200, amp + 1, true, true));
				}
			} else {
				target.addPotionEffect(new PotionEffect(WAPotions.CONSECRATION, 200, 0, true, true));
			}

		}
		return super.onLeftClickEntity(stack, attacker, targetEntity);
	}
	
	@Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slotIn, ItemStack stack)
    {
        final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slotIn, stack);
 
        if (slotIn == EntityEquipmentSlot.MAINHAND) 
      	{
            replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, -2.0);
            replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_DAMAGE, ATTACK_DAMAGE_MODIFIER, 9);
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
    
    private boolean shouldApply(EntityLivingBase player) {
		return player instanceof EntityPlayer && ((EntityPlayer) player).getCooledAttackStrength(1f) > 0.9;
	}
}
