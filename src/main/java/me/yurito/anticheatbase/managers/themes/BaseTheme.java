package me.yurito.anticheatbase.managers.themes;

import me.yurito.anticheatbase.Anticheat;
import me.yurito.anticheatbase.utils.MiscUtils;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.IOException;

public abstract class BaseTheme {

    private final FileConfiguration data;

    public BaseTheme(Anticheat plugin, String themeName) {

        File file = new File(plugin.getDataFolder() + "/themes", themeName + ".yml");

        try {

            file.createNewFile();

        } catch (IOException ignored) {
        }

        this.data = MiscUtils.loadConfigurationUTF_8(file);

        create();

        get().options().copyDefaults(true);

        try {

            this.data.save(file);

        } catch (IOException ignored) {
        }
    }

    protected FileConfiguration get() {
        return this.data;
    }

    public abstract void create();
}