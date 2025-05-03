package com.gamesense.api.util.misc;

import com.gamesense.client.module.ModuleManager;
import com.gamesense.client.module.modules.hud.Notifications;
//import com.mojang.realmsclient.gui.ChatFormatting;
//import net.minecraft.network.message.LastSeenMessageList;
import net.minecraft.util.Formatting;

//import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftClient;

//import net.minecraft.network.play.client.CPacketChatMessage;
//import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;

//import net.minecraft.util.text.TextComponentString;
import net.minecraft.text.Text;

import java.time.Instant;


/**
 * @author Hoosiers
 * @since 11/04/2020
 */

public class MessageBus {

    public static String watermark = Formatting.GRAY + "[" + Formatting.WHITE + "Game" + Formatting.GREEN + "Sense" + Formatting.GRAY + "] " + Formatting.RESET;
    public static Formatting messageFormatting = Formatting.GRAY;

    protected static final MinecraftClient mc = MinecraftClient.getInstance(); //instead of getClient()

    /**
     * Sends a client-sided message WITH the client prefix
     **/
    public static void sendClientPrefixMessage(String message) {
//        Text string1 = new Text(watermark + messageFormatting + message);
        Text string1 = Text.literal(watermark + messageFormatting + message);
//        Text string2 = new Text(messageFormatting + message);
        Text string2 = Text.literal(messageFormatting + message);

        Notifications notifications = ModuleManager.getModule(Notifications.class);
        notifications.addMessage(string2);
        if (notifications.isEnabled() && notifications.disableChat.getValue()) {
            return;
        }
        mc.player.sendMessage(string1);
    }

    /**
     * Command-oriented message, with the nature of commands we don't want them being a notification
     **/
    public static void sendCommandMessage(String message, boolean prefix) {
        String watermark1 = prefix ? watermark : "";
//        Text string = new Text(watermark1 + messageFormatting + message);
        Text string = Text.literal(watermark1 + messageFormatting + message);

        mc.player.sendMessage(string);
    }

    /**
     * @Unused Sends a client-sided message WITHOUT the client prefix
     **/
    public static void sendClientRawMessage(String message) {
//        Text string = new Text(messageFormatting + message);
        Text string = Text.literal(messageFormatting + message);

        Notifications notifications = ModuleManager.getModule(Notifications.class);
        notifications.addMessage(string);
        if (ModuleManager.isModuleEnabled(Notifications.class) && notifications.disableChat.getValue()) {
            return;
        }
        mc.player.sendMessage(string);
    }

    /**
     * Sends a server-sided message
     **/
    public static void sendServerMessage(String message) {
        // I HAVE NO FUCKING IDEA THIS IS MAKING ME MAD
        //mc.player.networkHandler.sendPacket(new ChatMessageC2SPacket(message, Instant.now(), 1, null, new LastSeenMessageList.Acknowledgment(LastSeenMessageList.EMPTY)));
        mc.getNetworkHandler().sendChatMessage(message);
    }
}