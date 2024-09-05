package me.yurito.anticheatbase.checks.impl.groundspoof;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class GroundSpoofA extends Check {
    public GroundSpoofA(Profile profile) {
        super(profile, CheckType.GROUNDSPOOF, "A", "Checks for GroundSpoof.");
    }

    @Override
    public void handle(Packet packet) {

        final double deltaY = profile.getMovementData().getDeltaY();

        final boolean ground = profile.getMovementData().isOnGround();
        final boolean math = profile.getMovementData().getDeltaY() % 0.015625 == 0.0;
        final boolean step = profile.getMovementData().getDeltaY() % 0.015625 == 0.0 && deltaY > 0.0;

        if (!step && deltaY > 1.0) {
            fail();
        }

        if (math != ground) {
            if (increaseBuffer() > 4) {
                fail();
            }
        } else {
            decreaseBufferBy(0.25F);
            fail();
        }
    }
}