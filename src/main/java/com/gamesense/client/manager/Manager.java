package com.gamesense.client.manager;

//import me.zero.alpine.listener.Listenable;
import com.gamesense.api.listener.Listenable;
import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.ClientPlayerEntity;
//import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.world.ClientWorld;
//import net.minecraft.profiler.Profiler;
import net.minecraft.util.profiler.Profiler;


public interface Manager extends Listenable {

    default MinecraftClient getMinecraft() {
        return MinecraftClient.getInstance();
    }

    default ClientPlayerEntity getPlayer() {
        return getMinecraft().player;
    }

    default ClientWorld getWorld() {
        return getMinecraft().world;
    }

    default Profiler getProfiler() {
        return getMinecraft().getProfiler();
    }
}