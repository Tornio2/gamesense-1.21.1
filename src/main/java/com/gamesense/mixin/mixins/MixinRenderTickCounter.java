package com.gamesense.mixin.mixins;

import com.gamesense.mixin.mixins.accessors.ITimer;
import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(RenderTickCounter.Dynamic.class)
public class MixinRenderTickCounter implements ITimer {

    @Shadow @Final @Mutable
    private float tickTime;

    @Shadow @Final @Mutable
    private FloatUnaryOperator targetMillisPerTick;

    @Override
    public float getTickTime() {
        return this.tickTime;
    }

    @Override
    public void setTickTime(float tickTime) {
        this.tickTime = tickTime;
    }

    @Override
    public FloatUnaryOperator getTargetMillisPerTick() {
        return this.targetMillisPerTick;
    }

    @Override
    public void setTargetMillisPerTick(FloatUnaryOperator operator) {
        this.targetMillisPerTick = operator;
    }
}