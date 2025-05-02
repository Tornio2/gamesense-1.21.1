package com.gamesense.api.event.events;

import com.gamesense.api.event.GameSenseEvent;
import net.minecraft.util.Arm;

public class TransformSideFirstPersonEvent extends GameSenseEvent {

    private final Arm arm;

    public TransformSideFirstPersonEvent(Arm arm) {
        this.arm = arm;
    }

    public Arm getArm() {
        return this.arm;
    }
}