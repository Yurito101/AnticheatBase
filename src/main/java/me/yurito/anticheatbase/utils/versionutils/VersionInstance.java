package me.yurito.anticheatbase.utils.versionutils;

import org.bukkit.entity.Player;

public interface VersionInstance {
    ClientVersion getClientVersion(Player player);
}