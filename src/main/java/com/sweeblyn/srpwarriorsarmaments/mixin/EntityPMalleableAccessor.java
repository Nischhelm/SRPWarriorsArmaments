package com.sweeblyn.srpwarriorsarmaments.mixin;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityPMalleable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityPMalleable.class, remap = false)
public interface EntityPMalleableAccessor {
    @Accessor("geneAdaptation")
    boolean getGeneAdaptation();
    @Accessor("geneBlocksearch")
    boolean getGeneBlocksearch();
    @Accessor("geneResidue")
    boolean getGeneResidue();
    @Accessor("geneOrbbox")
    boolean getGeneOrbbox();
}
