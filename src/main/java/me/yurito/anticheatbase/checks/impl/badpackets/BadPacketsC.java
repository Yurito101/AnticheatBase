package me.yurito.anticheatbase.checks.impl.badpackets;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class BadPacketsC extends Check {
    public BadPacketsC(Profile profile) {
        super(profile, CheckType.BADPACKETS, "C", "Checks for no position packet in 20 ticks.");
    }

    private int streak;

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            if (packet.isMovement() || profile.getPlayer().isInsideVehicle()) {
                streak = 0;
                return;
            }

            if (++streak > 20) {
                fail();
            }
        } else if (packet.is(Packet.Type.STEER_VEHICLE)) {
            streak = 0;
        }
    }
}
