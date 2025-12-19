package me.xarta.xadvancementannouncer.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class ConfigHandler {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    public static final ModConfigSpec SPEC;

    public static final ModConfigSpec.ConfigValue<String> ADVANCEMENT_FORMAT;
    public static final ModConfigSpec.ConfigValue<String> CHALLENGE_FORMAT;
    public static final ModConfigSpec.ConfigValue<String> GOAL_FORMAT;

    static {
        BUILDER.push("Announcement Formats");
        BUILDER.comment("Use %player% for player name and %advancement%/%challenge%/%goal% for advancement title",
                "Color codes: &0-9, &a-f for colors, &l for bold, &o for italic, &r for reset");

        ADVANCEMENT_FORMAT = BUILDER
                .comment("Format for regular task advancements")
                .define("advancement-format", "&a%player% &7achieved advancement &a%advancement%");

        CHALLENGE_FORMAT = BUILDER
                .comment("Format for challenge advancements")
                .define("challenge-format", "&5%player% &7completed challenge &5%challenge%");

        GOAL_FORMAT = BUILDER
                .comment("Format for goal advancements")
                .define("goal-format", "&e%player% &7reached goal &e%goal%");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}