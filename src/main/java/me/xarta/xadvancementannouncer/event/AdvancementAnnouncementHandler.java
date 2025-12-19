package me.xarta.xadvancementannouncer.event;

import me.xarta.xadvancementannouncer.config.ConfigHandler;
import me.xarta.xadvancementannouncer.util.MessageFormatter;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.AdvancementEvent;

public class AdvancementAnnouncementHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onAdvancementEarned(AdvancementEvent.AdvancementEarnEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) {
            return;
        }

        AdvancementHolder advancement = event.getAdvancement();
        DisplayInfo display = advancement.value().display().orElse(null);

        if (display == null) {
            return;
        }

        // Get the appropriate format based on advancement type
        String format = switch (display.getType()) {
            case TASK -> ConfigHandler.ADVANCEMENT_FORMAT.get();
            case CHALLENGE -> ConfigHandler.CHALLENGE_FORMAT.get();
            case GOAL -> ConfigHandler.GOAL_FORMAT.get();
        };

        String playerName = player.getName().getString();

        // Get the translatable title component (not converted to string yet)
        Component advancementTitle = display.getTitle();

        // Send custom message to each player
        player.server.getPlayerList().getPlayers().forEach(targetPlayer -> {
            // Build the message with the translatable component
            Component customMessage = MessageFormatter.buildComponent(format, playerName, advancementTitle);
            targetPlayer.sendSystemMessage(customMessage);
        });
    }
}