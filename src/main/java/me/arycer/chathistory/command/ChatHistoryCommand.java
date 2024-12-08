package me.arycer.chathistory.command;

import me.arycer.chathistory.io.ChatStorage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public class ChatHistoryCommand implements CommandExecutor {
    public static final ChatHistoryCommand INSTANCE = new ChatHistoryCommand();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("chathistory") || args.length != 1) {
            return false;
        }

        String playerName = args[0];
        Optional<List<String>> chatHistory = ChatStorage.INSTANCE.getChatHistory(playerName);
        if (!chatHistory.isPresent()) {
            commandSender.sendMessage("No chat history found for " + playerName);
            return true;
        }

        List<String> messages = chatHistory.get();
        commandSender.sendMessage("Chat history for " + playerName + ":");

        for (String message : messages) {
            commandSender.sendMessage(" - " + message);
        }

        return true;
    }
}
