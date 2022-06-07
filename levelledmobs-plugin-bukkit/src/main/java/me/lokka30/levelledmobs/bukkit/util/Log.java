package me.lokka30.levelledmobs.bukkit.util;

import me.lokka30.levelledmobs.bukkit.LevelledMobs;

@SuppressWarnings("unused")
public final class Log extends UtilityClass {

    private Log() {
        super();
    }

    public static void inf(final String msg) {
        LevelledMobs.getInstance().getLogger().info(msg);
    }

    public static void war(final String msg, final boolean suggestSupport) {
        LevelledMobs.getInstance().getLogger().warning(msg + (!suggestSupport ? "" :
            " If (despite multiple attempts) you are unable to fix this issue, feel free to " +
                "contact our support team for assistance."
        ));
    }

    public static void sev(final String msg, final boolean suggestSupport) {
        LevelledMobs.getInstance().getLogger().severe(msg + (!suggestSupport ? "" :
            " Feel free to contact our support team if assistance is required."
        ));
    }

}