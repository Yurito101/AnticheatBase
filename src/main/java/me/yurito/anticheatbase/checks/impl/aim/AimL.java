package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimL extends Check {
    public AimL(Profile profile) {
        super(profile, CheckType.AIM, "L", "");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float pitch = profile.getRotationData().getPitch();
            if (pitch > 89.0F || pitch < 1.0F) return;
        }
        final float deltaPitch = profile.getRotationData().getDeltaPitch();
        final float deltaYaw = profile.getRotationData().getDeltaYaw();
        final boolean invaild = deltaPitch == 0.0F && deltaYaw >= 15.6F;
        if (invaild) {
            if (increaseBuffer() > 6) {
                fail();
            }
        } else {
            decreaseBufferBy(0.5);
        }
    }
}
