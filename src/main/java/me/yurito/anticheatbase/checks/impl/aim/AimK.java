package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimK extends Check {
    public AimK(Profile profile) {
        super(profile, CheckType.AIM, "K", "Checks for invalid deltas.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float deltaPitch = profile.getRotationData().getDeltaPitch();
            final float deltaYaw = profile.getRotationData().getDeltaYaw();

            final boolean invalid = deltaYaw == 0.0F && deltaPitch >= 20.0F;

            debug("deltaPitch: " + deltaPitch + "deltaYaw: " +deltaYaw);

            if (invalid) {
                if (increaseBuffer() > 3) {
                    fail();
                }
            } else {
                decreaseBufferBy(0.05);
            }
        }
    }
}