package me.arycer.chathistory.event;

import me.arycer.chathistory.io.ChatStorage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.world.WorldSaveEvent;

public class EventListener implements Listener {
    public static final EventListener INSTANCE = new EventListener();

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        ChatStorage.INSTANCE.addChatMessage(event.getPlayer().getName(), event.getMessage());
    }

    @EventHandler
    public void onWorldSave(WorldSaveEvent event) {
        ChatStorage.INSTANCE.save();
    }
}
