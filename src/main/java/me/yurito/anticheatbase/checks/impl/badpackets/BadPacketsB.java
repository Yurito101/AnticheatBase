package me.yurito.anticheatbase.checks.impl.badpackets;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class BadPacketsB extends Check {
    public BadPacketsB(Profile profile) {
        super(profile, CheckType.BADPACKETS, "B", "Checks for disablers.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            final float yaw = profile.getRotationData().getYaw();
            if (yaw > 1200.0F && (yaw % 360.0F > 1200.0F)) {
                fail("deltaYaw: " + yaw);
            }
        }
    }
}
