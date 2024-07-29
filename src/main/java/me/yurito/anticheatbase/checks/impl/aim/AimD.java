package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimD extends Check {
    public AimD(Profile profile) {
        super(profile, CheckType.AIM, "D", "Invalid yaw change.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final boolean invalid = deltaYaw > 0.0F && (deltaYaw % 0.25 == 0.0 || deltaYaw % 0.1 == 0.0);
            if (invalid) {
                if (increaseBuffer() > 6) {
                    fail();
                }
            } else {
                decreaseBufferBy(0.65);
            }
        }
    }
}
