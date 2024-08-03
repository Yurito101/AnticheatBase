package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimG extends Check {
    public AimG(Profile profile) {
        super(profile, CheckType.AIM, "G", "Switching directions too quickly.");
    }

    private float lastDeltaPitch;
    private int ticksSinceSwitchedDirection;

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float pitch = profile.getRotationData().getPitch();
            final float lastPitch = profile.getRotationData().getLastPitch();
            final float deltaPitch = pitch - lastPitch;
            if ((deltaPitch < 0.0F && lastDeltaPitch > 0.0F) || (deltaPitch > 0.0F && lastDeltaPitch < 0.0F)) {
                ticksSinceSwitchedDirection = 0;
            } else {
                ++ticksSinceSwitchedDirection;
            }
            final boolean invalid = ticksSinceSwitchedDirection == 0 && Math.abs(deltaPitch) > 5.0F;
            if (invalid) {
                if (increaseBuffer() > 10) {
                    fail("deltaPitch=" + deltaPitch);
                }
            } else {
                resetBuffer();
            }
            lastDeltaPitch = deltaPitch;
        }
    }
}