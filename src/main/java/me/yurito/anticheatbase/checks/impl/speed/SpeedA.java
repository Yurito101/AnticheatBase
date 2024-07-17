package me.yurito.anticheatbase.checks.impl.speed;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.MovementData;
import me.yurito.anticheatbase.processors.Packet;

public class SpeedA extends Check {
    public SpeedA(Profile profile) {
        super(profile, CheckType.SPEED, "A", "Checks for speed");
    }

    @Override
    public void handle(Packet packet) {
        if (!packet.isRotation()) return;
        MovementData data = profile.getMovementData();

        final float deltaYaw = profile.getRotationData().getDeltaYaw();

        final double deltaXZ = data.getDeltaXZ();
        final double lastDeltaXZ = data.getLastDeltaXZ();
        final double accel = Math.abs(deltaXZ - lastDeltaXZ);
        final double squaredAccel = accel * 100;

        if (deltaYaw > 1.5F && deltaXZ > .15D && squaredAccel < 1.0E-5) {
            fail();
        }
    }
}