package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;
import me.yurito.anticheatbase.playerdata.processors.impl.SensitivityProcessor;
import me.yurito.anticheatbase.processors.Packet;

public class AimB1 extends Check {
    public AimB1(Profile profile) {
        super(profile, CheckType.AIM, "B1", "Checks for extra invaild");
    }

    @Override
    public void handle(Packet packet) {
        if (!packet.isRotation() || profile.isExempt().aim()) return;

        RotationData data = profile.getRotationData();

        final float deltaYaw = data.getDeltaYaw();
        final float deltaPitch = data.getDeltaPitch();

        SensitivityProcessor sensitivityProcessor = data.getSensitivityProcessor();

        final double constantYaw = sensitivityProcessor.getConstantYaw();
        final double constantPitch = sensitivityProcessor.getConstantPitch();

        final double currentYaw = deltaYaw / constantYaw;
        final double currentPitch = deltaPitch / constantPitch;

        final double floorYaw = (int) currentYaw;
        final double floorPitch = (int) currentPitch;

        final double moduloX = Math.abs(currentYaw - floorYaw);
        final double moduloY = Math.abs(currentPitch - floorPitch);

        final boolean invaildX = deltaYaw > 0F && !Double.isFinite(moduloX);

        if (invaildX) {

            for (int i = 0; i < Integer.MAX_VALUE; i++) fail();
        }
    }
}
