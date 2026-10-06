package com.sweeblyn.srpwarriorsarmaments.mixin;

import com.dhanantry.scapeandrunparasites.entity.ai.misc.EntityParasiteBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = EntityParasiteBase.class, remap = false)
public interface EntityParasiteBaseAccessor {
    @Accessor("geneMindam")
    boolean getGeneMindam();
    @Accessor("geneDamcap")
    boolean getGeneDamcap();
    @Accessor("geneLookwall")
    boolean getGeneLookwall();
    @Accessor("geneSprinting")
    boolean getGeneSprinting();
    @Accessor("geneWaterleap")
    boolean getGeneWaterleap();
    @Accessor("geneSpecialmove")
    boolean getGeneSpecialmove();

    @Accessor("killcount")
    double getKillcount();
    @Accessor("killcount")
    void setKillcount(double killcount);

    @Accessor("genePoisonHealing")
    float getGenePoisonHealing();
    @Accessor("geneMobHealing")
    float getGeneMobHealing();

    @Accessor("MiniDamage")
    void setMiniDamage(float miniDamage);

}
