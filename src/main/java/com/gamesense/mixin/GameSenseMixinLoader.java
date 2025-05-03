package com.gamesense.mixin;

import com.gamesense.client.GameSense;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class GameSenseMixinLoader {

    @Shadow private Window window;

    @Inject(method = "run", at = @At("HEAD"))
    private void onRun(CallbackInfo ci) {
        window.setTitle(GameSense.MODNAME + " " + GameSense.MODVER);
    }
}