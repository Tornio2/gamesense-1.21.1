package com.gamesense.mixin.mixins;

import com.gamesense.mixin.mixins.accessors.TimerAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinTimer {

    @Shadow private RenderTickCounter renderTickCounter;

    // This allows us to access the timer from outside the mixin
    @Inject(method = "<init>*", at = @At("RETURN"))
    private void onInit(CallbackInfo ci) {
        TimerAccessor.renderTickCounter = this.renderTickCounter;
    }
}