package me.yurito.anticheatbase.files;

import me.yurito.anticheatbase.Anticheat;
import me.yurito.anticheatbase.files.commentedfiles.CommentedFileConfiguration;
import me.yurito.anticheatbase.managers.Initializer;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Checks implements Initializer {

    private static final String[] HEADER = new String[]{
            "+----------------------------------------------------------------------------------------------+",
            "|                                                                                              |",
            "|                                          Anticheat                                           |",
            "|                                                                                              |",
            "|                          Github: https://github.com/Yurito101/AnticheatBase                  |",
            "|                                                                                              |",
            "|                                         Author: Yurito                                       |",
            "|                                                                                              |",
            "+----------------------------------------------------------------------------------------------+"
    };

    private final JavaPlugin plugin;
    private CommentedFileConfiguration configuration;
    private static boolean exists;

    public Checks(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * @return the config.yml as a CommentedFileConfiguration
     */
    public CommentedFileConfiguration getConfig() {
        return this.configuration;
    }

    @Override
    public void initialize() {

        File configFile = new File(this.plugin.getDataFolder(), "checks.yml");

        exists = configFile.exists();

        boolean setHeaderFooter = !exists;

        boolean changed = setHeaderFooter;

        this.configuration = CommentedFileConfiguration.loadConfiguration(this.plugin, configFile);

        if (setHeaderFooter) this.configuration.addComments(HEADER);

        for (Setting setting : Setting.values()) {

            setting.reset();

            changed |= setting.setIfNotExists(this.configuration);
        }

        if (changed) this.configuration.save();
    }

    @Override
    public void shutdown() {
        for (Setting setting : Setting.values()) setting.reset();
    }

    public enum Setting {
        AIM("aim", "", "Aim Check"),
        AIM_A("aim.a", true, "Should we enable this module?"),
        AIM_A1("aim.a1", true, "Should we enable this module?"),
        AIM_B("aim.b", true, "Should we enable this module?"),
        AIM_B1("aim.b1", true, "Should we enable this module?"),
        AIM_C("aim.c", true, "Should we enable this module?"),
        AIM_D("aim.d", true, "Should we enable this module?"),
        AIM_E("aim.e", true, "Should we enable this module?"),
        AIM_F("aim.f", true, "Should we enable this module?"),
        AIM_G("aim.g", true, "Should we enable this module?"),
        AIM_MAX_VL("aim.max_vl", 10, "The maximum violation amount a player needs to reach in order to get punished"),
        AIM_COMMANDS("aim.commands", Collections.singletonList("kick %player% Unfair Advantage"), "The commands that will get executed once a player reaches the maximum violation amount"),

        BADPACKETS("badpackets", "", "BadPackets Check"),
        BADPACKETS_A("badpackets.a", true, "Should we enable this module?"),
        BADPACKETS_B("badpackets.b", true, "Should we enable this module?"),
        BADPACKETS_MAX_VL("badpackets.max_vl", 10, "The maximum violation amount a player needs to reach in order to get punished"),
        BADPACKETS_COMMANDS("badpackets.commands", Collections.singletonList("kick %player% Unfair Advantage"), "The commands that will get executed once a player reaches the maximum violation amount"),

        FLIGHT("flight", "", "Flight Check"),
        FLIGHT_A("flight.a", true, "Should we enable this module?"),
        FLIGHT_B("flight.b", true, "Should we enable this module?"),
        FLIGHT_MAX_VL("flight.max_vl", 10, "The maximum violation amount a player needs to reach in order to get punished"),
        FLIGHT_COMMANDS("flight.commands", Collections.singletonList("kick %player% Unfair Advantage"), "The commands that will get executed once a player reaches the maximum violation amount"),

        KILLAURA("killaura", "", "KillAura Check"),
        KILLAURA_A("killaura.a", true, "Should we enable this module?"),
        KILLAURA_MAX_VL("killaura.max_vl", 10, "The maximum violation amount a player needs to reach in order to get punished"),
        KILLAURA_COMMANDS("killaura.commands", Collections.singletonList("kick %player% Unfair Advantage"), "The commands that will get executed once a player reaches the maximum violation amount"),

        SPEED("speed", "", "Speed Check"),
        SPEED_A("speed.a", true, "Should we enable this module?"),
        SPEED_MAX_VL("speed.max_vl", 10, "The maximum violation amount a player needs to reach in order to get punished"),
        SPEED_COMMANDS("speed.commands", Collections.singletonList("kick %player% Unfair Advantage"), "The commands that will get executed once a player reaches the maximum violation amount");

        private final String key;
        private final Object defaultValue;
        private boolean excluded;
        private final String[] comments;
        private Object value = null;

        Setting(String key, Object defaultValue, String... comments) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.comments = comments != null ? comments : new String[0];
        }

        Setting(String key, Object defaultValue, boolean excluded, String... comments) {
            this.key = key;
            this.defaultValue = defaultValue;
            this.comments = comments != null ? comments : new String[0];
            this.excluded = excluded;
        }

        /**
         * Gets the setting as a boolean
         *
         * @return The setting as a boolean
         */
        public boolean getBoolean() {
            this.loadValue();
            return (boolean) this.value;
        }

        public String getKey() {
            return this.key;
        }

        /**
         * @return the setting as an int
         */
        public int getInt() {
            this.loadValue();
            return (int) this.getNumber();
        }

        /**
         * @return the setting as a long
         */
        public long getLong() {
            this.loadValue();
            return (long) this.getNumber();
        }

        /**
         * @return the setting as a double
         */
        public double getDouble() {
            this.loadValue();
            return this.getNumber();
        }

        /**
         * @return the setting as a float
         */
        public float getFloat() {
            this.loadValue();
            return (float) this.getNumber();
        }

        /**
         * @return the setting as a String
         */
        public String getString() {
            this.loadValue();
            return String.valueOf(this.value);
        }

        private double getNumber() {
            if (this.value instanceof Integer) {
                return (int) this.value;
            } else if (this.value instanceof Short) {
                return (short) this.value;
            } else if (this.value instanceof Byte) {
                return (byte) this.value;
            } else if (this.value instanceof Float) {
                return (float) this.value;
            }

            return (double) this.value;
        }

        /**
         * @return the setting as a string list
         */
        @SuppressWarnings("unchecked")
        public List<String> getStringList() {
            this.loadValue();
            return (List<String>) this.value;
        }

        private boolean setIfNotExists(CommentedFileConfiguration fileConfiguration) {
            this.loadValue();

            if (exists && this.excluded) return false;

            if (fileConfiguration.get(this.key) == null) {
                List<String> comments = Stream.of(this.comments).collect(Collectors.toList());
                if (this.defaultValue != null) {
                    fileConfiguration.set(this.key, this.defaultValue, comments.toArray(new String[0]));
                } else {
                    fileConfiguration.addComments(comments.toArray(new String[0]));
                }

                return true;
            }

            return false;
        }

        /**
         * Resets the cached value
         */
        public void reset() {
            this.value = null;
        }

        /**
         * @return true if this setting is only a section and doesn't contain an actual value
         */
        public boolean isSection() {
            return this.defaultValue == null;
        }

        /**
         * Loads the value from the config and caches it if it isn't set yet
         */
        private void loadValue() {
            if (this.value != null) return;
            this.value = Anticheat.getInstance().getConfiguration().get(this.key);
        }
    }
}