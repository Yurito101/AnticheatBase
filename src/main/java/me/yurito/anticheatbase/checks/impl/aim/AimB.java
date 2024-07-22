package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;
import me.yurito.anticheatbase.playerdata.processors.impl.SensitivityProcessor;
import me.yurito.anticheatbase.processors.Packet;

public class AimB extends Check {
    public AimB(Profile profile) {
        super(profile, CheckType.AIM, "B", "Checks for invaild");
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

        final double divisorX = deltaYaw % constantYaw;
        final double divisorY = deltaPitch % constantPitch;

        final boolean invaildX = deltaYaw > 0F && !Double.isFinite(divisorX);
        final boolean invaildY = deltaPitch > 0F && !Double.isFinite(divisorY);

        if (invaildX || invaildY) fail();
    }
}
