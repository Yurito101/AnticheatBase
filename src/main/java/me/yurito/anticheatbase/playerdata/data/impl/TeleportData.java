package me.yurito.anticheatbase.playerdata.data.impl;

import me.yurito.anticheatbase.playerdata.data.Data;
import me.yurito.anticheatbase.processors.Packet;

public class TeleportData implements Data {

    private int teleportTicks;

    @Override
    public void process(Packet packet) {
        /*
        Handle the packet
         */
    }

    public int getTeleportTicks() {
        return teleportTicks;
    }
}