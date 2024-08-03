package me.yurito.anticheatbase.checks.impl.flight;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class FlightB extends Check {
    public FlightB(Profile profile) {
        super(profile, CheckType.FLIGHT, "B", "Checks for YPort");
    }

    private double lastDeltaY;

    @Override
    public void handle(Packet packet) {
        if (packet.isMovement() || packet.isRotation()) {
            if (profile.getMovementData().getDeltaY() <= 0 && lastDeltaY > 0.11 && !shouldExempt(profile)) {
                debug("YPort");
            }
            lastDeltaY = profile.getMovementData().getDeltaY();
        }
    }

    public boolean shouldExempt(Profile profile) {
        return profile.getMovementData().isOnGround() && lastDeltaY == 0.5926045976350593;
    }
}
