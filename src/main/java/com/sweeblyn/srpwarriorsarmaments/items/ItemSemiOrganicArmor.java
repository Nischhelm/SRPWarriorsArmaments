package com.sweeblyn.srpwarriorsarmaments.items;

import com.sweeblyn.srpwarriorsarmaments.SRPWarriorsArmaments;
import com.sweeblyn.srpwarriorsarmaments.init.WAPotions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSemiOrganicArmor extends ItemArmor {
	public ItemSemiOrganicArmor(String regName, ArmorMaterial material, EntityEquipmentSlot equipmentSlot) {
		super(material, 0, equipmentSlot);
		
		this.setRegistryName(regName);
		this.setTranslationKey(regName);
		
		this.setCreativeTab(SRPWarriorsArmaments.tab);
	}
	
	
    public void onArmorTick(final World world, final EntityPlayer player, final ItemStack stack) {
        if (world.isRemote) {
            return;
        }
        int numPieces = getTotalPieces(player);
        if (player.ticksExisted % 600 == 0) {
        	if (numPieces > 0) {
        		player.addPotionEffect(new PotionEffect(WAPotions.DIVINE, 595 , player.isPotionActive(WAPotions.DIVINE) ? numPieces-1+player.getActivePotionEffect(WAPotions.DIVINE).getAmplifier() : numPieces-1  , false, true));
        	}
        }
    }
    
    
    private int getTotalPieces(EntityPlayer player)
    {
        int amount = 0;
        for (ItemStack armorPiece : player.getArmorInventoryList())
        {
            if (armorPiece.getItem() instanceof ItemSemiOrganicArmor) {
            	amount++;
            }
        }

        return amount;
    }
}
