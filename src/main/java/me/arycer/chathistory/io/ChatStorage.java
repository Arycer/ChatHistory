package me.arycer.chathistory.io;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.arycer.chathistory.ChatHistoryPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ChatStorage {
    public static final ChatStorage INSTANCE = new ChatStorage();
    private static final Gson GSON = new Gson();
    private static final int MAX_SAVED_MESSAGES = 100;
    private static final String DIRECTORY_NAME = "chat_history";
    private static final String FILE_NAME = "chat.json";

    private ConcurrentHashMap<String, List<String>> chatHistory = new ConcurrentHashMap<>();

    public void addChatMessage(String player, String message) {
        List<String> messages;
        if (!chatHistory.containsKey(player)) {
            messages = new ArrayList<>();
        } else {
            messages = chatHistory.get(player);
            if (messages.size() >= MAX_SAVED_MESSAGES) {
                messages.remove(0);
            }
        }

        messages.add(message);
        chatHistory.put(player, messages);
    }

    public Optional<List<String>> getChatHistory(String player) {
        return Optional.ofNullable(chatHistory.get(player));
    }

    public void save() {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, FILE_NAME);
        if (file.exists()) {
            file.delete();
        }

        String json = GSON.toJson(chatHistory);
        try {
            Files.write(file.toPath(), json.getBytes());
        } catch (IOException e) {
            ChatHistoryPlugin.INSTANCE.getLogger().warning("Failed to save chat history: " + e.getMessage());
        }
    }

    public void load() {
        File directory = new File(DIRECTORY_NAME);
        if (!directory.exists()) {
            return;
        }

        File file = new File(directory, FILE_NAME);
        if (!file.exists()) {
            return;
        }

        try {
            String json = new String(Files.readAllBytes(file.toPath()));
            JsonObject jsonObject = GSON.fromJson(json, JsonObject.class);

            chatHistory.clear();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                List<String> messages = new ArrayList<>();
                for (JsonElement element : entry.getValue().getAsJsonArray()) {
                    messages.add(element.getAsString());
                }

                chatHistory.put(entry.getKey(), messages);
            }
        } catch (Exception e) {
            ChatHistoryPlugin.INSTANCE.getLogger().warning("Failed to load chat history: " + e.getMessage());
        }
    }
}
