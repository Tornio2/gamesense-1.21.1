package com.gamesense.mixin.mixins.accessors;

import it.unimi.dsi.fastutil.floats.FloatUnaryOperator;

public interface ITimer {
    float getTickTime();
    void setTickTime(float tickTime);
    FloatUnaryOperator getTargetMillisPerTick();
    void setTargetMillisPerTick(FloatUnaryOperator operator);
}