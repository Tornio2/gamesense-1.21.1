package com.gamesense.mixin.mixins.accessors;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;
import net.minecraft.client.render.RenderTickCounter;

public class TimerAccessor {
    public static RenderTickCounter renderTickCounter;
    private static FloatUnaryOperator originalOperator;
    private static float timerSpeed = 1.0f;

    public static void setTimerSpeed(float speed) {
        timerSpeed = speed;

        if (renderTickCounter != null && renderTickCounter instanceof RenderTickCounter.Dynamic) {
            ITimer timer = (ITimer) renderTickCounter;

            // Store original operator if not yet stored
            if (originalOperator == null) {
                originalOperator = timer.getTargetMillisPerTick();
            }

            // Create a new operator that applies the speed modifier
            FloatUnaryOperator newOperator = value -> originalOperator.apply(value) / speed;
            timer.setTargetMillisPerTick(newOperator);
        }
    }

    public static void resetTimer() {
        if (renderTickCounter != null && renderTickCounter instanceof RenderTickCounter.Dynamic && originalOperator != null) {
            ((ITimer) renderTickCounter).setTargetMillisPerTick(originalOperator);
            timerSpeed = 1.0f;
        }
    }

    public static float getTimerSpeed() {
        return timerSpeed;
    }
}