package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;
import me.yurito.anticheatbase.processors.Packet;

public class AimA extends Check {
    public AimA(Profile profile) {
        super(profile, CheckType.AIM, "A", "Checks for sample size");
    }

    @Override
    public void handle(Packet packet) {
        if (!packet.isRotation() || profile.isExempt().aim()) return;

        RotationData data = profile.getRotationData();

        final float deltaYaw = data.getDeltaYaw();
        final float deltaPitch = data.getDeltaPitch();

        final boolean invaildPitch = deltaPitch > 0F && deltaPitch < .005F && deltaYaw > 2.5F;
        final boolean invaildYaw = deltaYaw > 0F && deltaYaw < .005F && deltaPitch > 2.5F;

        if (invaildPitch || invaildYaw) {
            if (increaseBuffer() > 5) {
                fail();
            }

        } else {
            decreaseBufferBy(0.25);
            fail();
        }
    }
}