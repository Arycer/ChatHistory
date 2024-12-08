package me.arycer.chathistory;

import me.arycer.chathistory.command.ChatHistoryCommand;
import me.arycer.chathistory.event.EventListener;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatHistoryPlugin extends JavaPlugin {
    public static final String PLUGIN_NAME = "ChatHistory";
    public static ChatHistoryPlugin INSTANCE;

    @Override
    public void onEnable() {
        INSTANCE = this;

        getLogger().info(String.format("%s has been enabled!", PLUGIN_NAME));
        getServer().getPluginManager().registerEvents(EventListener.INSTANCE, this);
        getCommand("chathistory").setExecutor(ChatHistoryCommand.INSTANCE);
    }

    @Override
    public void onDisable() {
        getLogger().info(String.format("%s has been disabled!", PLUGIN_NAME));
    }
}
