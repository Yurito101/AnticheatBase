package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AimI extends Check {
    public AimI(Profile profile) {
        super(profile, CheckType.AIM, "I", "Checks for constant rots.");
    }

    @Override
    public void handle(Packet packet) {
        if(packet.isRotation()) {
            final float deltaYaw = Math.abs(profile.getRotationData().getDeltaYaw() % 360F);
            final float deltaPitch = Math.abs(profile.getRotationData().getDeltaPitch() % 360F);

            debug("Yaw:" + deltaYaw + " round: " + Math.round(deltaYaw) + " Pitch: " + deltaPitch + " round: " + Math.round(deltaPitch));
            if (deltaYaw > 0.09) {
                if (deltaYaw == Math.round(deltaYaw)) {
                    if (increaseBuffer() > 4) {
                        fail(deltaYaw + " round: " + Math.round(deltaYaw));
                    }
                } else {
                    decreaseBufferBy(3);
                }
            }
        }
    }
}