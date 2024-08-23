package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimH extends Check {
    public AimH(Profile profile) {
        super(profile, CheckType.AIM, "H", "Too small pitch change.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float deltaPitch = profile.getRotationData().getDeltaPitch();
            final boolean invalid = deltaPitch < 0.05 && deltaPitch > 0.0F && deltaYaw == 0.0F;
            if (invalid) {
                if (increaseBuffer() > 5) {
                    fail("deltaPitch=" + deltaPitch + " deltaYaw=" + deltaYaw);
                }
            }
            else {
                decreaseBufferBy(0.25);
            }
        }
    }
}
