package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimJ extends Check {
    public AimJ(Profile profile) {
        super(profile, CheckType.AIM, "J", "Checks for impossible ratio.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {

            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float deltaPitch = profile.getRotationData().getDeltaPitch();

            final boolean invalid = deltaYaw > .5F && deltaPitch < .0001 && deltaPitch > 0;

            if (invalid) {
                if (increaseBuffer() > 4) {
                    fail("deltaYaw=" + deltaYaw + " deltaPitch=" + deltaPitch);
                }
            } else {
                decreaseBufferBy(0.25);
            }
        }
    }
}