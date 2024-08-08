package me.yurito.anticheatbase.checks.impl.killaura;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class KillAuraB extends Check {
    public KillAuraB(Profile profile) {
        super(profile, CheckType.KILLAURA, "B", "Invalid acceleration.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isMovement()) {
            final double acceleration = profile.getMovementData().getAccelXZ();
            final double deltaXZ = profile.getMovementData().getDeltaXZ();
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float deltaPitch = profile.getRotationData().getDeltaPitch();
            final boolean validTarget = profile.getPlayer().isValid();
            final boolean invalid = acceleration < 0.001 && deltaYaw > 10.0F && deltaPitch > 26.5 && validTarget && deltaXZ > 0.0;
            if (invalid) {
                if (increaseBuffer() > 2) {
                    fail("accel=" + acceleration + " deltaYaw=" + deltaYaw);
                }
            } else {
                decreaseBufferBy(0.25);
            }
        }
    }
}
