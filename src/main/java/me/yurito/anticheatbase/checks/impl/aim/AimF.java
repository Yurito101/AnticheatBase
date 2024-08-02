package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimF extends Check {
    public AimF(Profile profile) {
        super(profile, CheckType.AIM, "F", "Too large yaw change.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float deltaPitch = profile.getRotationData().getDeltaPitch();
            final float pitch = profile.getRotationData().getPitch();
            final boolean invalid = deltaYaw > 15.0F && deltaPitch < 0.1 && Math.abs(pitch) < 65.0F;
            if (invalid) {
                if (increaseBuffer() > 5) {
                    fail("deltaYaw=" + deltaYaw + " deltaPitch=" + deltaPitch);
                }
            } else {
                decreaseBufferBy(0.75);
            }
        }
    }
}
