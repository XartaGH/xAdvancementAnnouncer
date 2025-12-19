package me.xarta.xadvancementannouncer;

import com.mojang.logging.LogUtils;
import me.xarta.xadvancementannouncer.config.ConfigHandler;
import me.xarta.xadvancementannouncer.event.AdvancementAnnouncementHandler;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(XAdvancementAnnouncer.MODID) // Declare this class as mod's main class
public class XAdvancementAnnouncer {
    public static final String MODID = "xadvancementannouncer"; // Define modification's ID
    public static final Logger LOGGER = LogUtils.getLogger(); // Create logger

    public XAdvancementAnnouncer(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("xAdvancementAnnouncer initializing..."); // Print initialization message

        // Register config for the mod
        modContainer.registerConfig(
                ModConfig.Type.SERVER,
                ConfigHandler.SPEC,
                "xadvancementannouncer.toml"
        );

        NeoForge.EVENT_BUS.register(new AdvancementAnnouncementHandler()); // Register event handler
        
        LOGGER.info("xAdvancementAnnouncer is on."); // Print success message
    }
}