package me.yurito.anticheatbase.checks.impl.badpackets;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class BadPacketsA extends Check {
    public BadPacketsA(Profile profile) {
        super(profile, CheckType.BADPACKETS, "A", "Checks if the player pitch is an impossible value.");
    }

    @Override
    public void handle(Packet packet) {
        final float pitch = profile.getRotationData().getPitch();
        if (Math.abs(pitch) > 90F || Math.abs(pitch) < -90F) {
            fail();
        }
    }
}