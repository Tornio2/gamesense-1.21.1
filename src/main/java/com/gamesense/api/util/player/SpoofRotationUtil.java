package com.gamesense.api.util.player;

import com.gamesense.api.event.events.PacketEvent;
import com.gamesense.api.listener.EventHandler;
import com.gamesense.api.listener.Listenable;
import com.gamesense.api.util.world.EntityUtil;
import com.gamesense.client.GameSense;
import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import com.gamesense.mixin.mixins.accessors.IPlayerMoveC2SPacket;

public class SpoofRotationUtil implements Listenable, Subscriber {

    private static final MinecraftClient mc = MinecraftClient.getInstance();
    public static final SpoofRotationUtil ROTATION_UTIL = new SpoofRotationUtil();

    private int rotationConnections = 0;

    private boolean shouldSpoofAngles;
    private boolean isSpoofingAngles;
    private double yaw;
    private double pitch;

    // Forces only ever one
    private SpoofRotationUtil() {

    }

    public void onEnable() {
        rotationConnections++;
        if (rotationConnections == 1)
            GameSense.EVENT_BUS.subscribe(this);
    }

    public void onDisable() {
        rotationConnections--;
        if (rotationConnections == 0)
            GameSense.EVENT_BUS.unsubscribe(this);
    }

    public void lookAtPacket(double px, double py, double pz, PlayerEntity me) {
        double[] v = EntityUtil.calculateLookAt(px, py, pz, me);
        this.setYawAndPitch((float) v[0], (float) v[1]);
    }

    public void setYawAndPitch(float yaw1, float pitch1) {
        yaw = yaw1;
        pitch = pitch1;
        isSpoofingAngles = true;
    }

    public void resetRotation() {
        if (isSpoofingAngles) {
            yaw = mc.player.getYaw(); // Updated from rotationYaw
            pitch = mc.player.getPitch(); // Updated from rotationPitch
            isSpoofingAngles = false;
        }
    }

    public void shouldSpoofAngles(boolean e) {
        shouldSpoofAngles = e;
    }

    public boolean isSpoofingAngles() {
        return isSpoofingAngles;
    }

    @EventHandler
    private final Listener<PacketEvent.Send> packetSendListener = new Listener<>(event -> {
        Object packet = event.getPacket();
        if (packet instanceof PlayerMoveC2SPacket && shouldSpoofAngles) {
            if (isSpoofingAngles) {
                // Use mixin accessor to maintain original logic
                ((IPlayerMoveC2SPacket) packet).setYaw((float) yaw);
                ((IPlayerMoveC2SPacket) packet).setPitch((float) pitch);
            }
        }
    });
}