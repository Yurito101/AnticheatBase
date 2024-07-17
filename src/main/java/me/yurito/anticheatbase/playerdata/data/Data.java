package me.yurito.anticheatbase.playerdata.data;

import me.yurito.anticheatbase.processors.Packet;

public interface Data {
    void process(Packet packet);
}