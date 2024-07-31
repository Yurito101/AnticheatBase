package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimE extends Check {
    public AimE(Profile profile) {
        super(profile, CheckType.AIM, "E", "Repeated yaw values.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float lastDeltaYaw = profile.getRotationData().getLastDeltaYaw();
            final boolean invaild =  deltaYaw > 1.25F && lastDeltaYaw > 1.25F && deltaYaw == lastDeltaYaw;
            if (invaild) {
                if (increaseBuffer() > 5) {
                    fail();
                }
            } else {
                decreaseBufferBy(0.75);
            }
        }
    }
}
