package me.yurito.anticheatbase.checks.impl.speed;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class SpeedB extends Check {
    public SpeedB(Profile profile) {
        super(profile, CheckType.SPEED, "B", "Checks for flaws in speeds by checking packets.");
    }

    @Override
    public void handle(Packet packet) {
        if (packet.isFlying()) {
            final double deltaY = profile.getMovementData().getDeltaY();
            final int groundTicks = profile.getMovementData().getClientGroundTicks();
            final int airTicks = profile.getMovementData().getAirTicks();
            final boolean exempt = profile.isExempt().slime();
            final boolean invalid = deltaY == 0.0 && groundTicks == 1 && airTicks == 0;
            if (invalid && !exempt) {
                if (increaseBuffer() > 5) {
                    fail();
                }
            }
            else {
                resetBuffer();
            }
        }
    }
}