package com.gamesense.client;

import net.fabricmc.api.ClientModInitializer;

public class GamesenseClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This code runs in client modloader
        // You can use this to register client-side features
        // like rendering, key bindings, etc.
        System.out.println("Fabric client modloader initialized.");
    }
}
