package com.gamesense.api.event;

import me.zero.alpine.event.Cancellable;


public class GameSenseEvent implements Cancellable {

    private boolean cancelled = false;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }
    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

}
