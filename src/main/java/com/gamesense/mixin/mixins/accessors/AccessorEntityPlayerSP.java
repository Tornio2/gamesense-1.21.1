package com.gamesense.mixin.mixins.accessors;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientPlayerEntity.class)
public interface AccessorEntityPlayerSP {
    @Accessor("activeHand")
    void gsSetActiveHand(Hand value);

    @Accessor("activeHand")
    Hand gsGetActiveHand();

}