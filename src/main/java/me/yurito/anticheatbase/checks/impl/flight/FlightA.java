package me.yurito.anticheatbase.checks.impl.flight;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class FlightA extends Check {
    public FlightA(Profile profile) {
        super(profile, CheckType.FLIGHT, "B", "Checks for gravity/motion");
    }

    private int threshold;
    private double lastDeltaY;

    @Override
    public void handle(Packet packet) {
        if (packet.isMovement()) {
            double deltaY = profile.getMovementData().getDeltaY();

            if (!profile.getMovementData().isOnGround()) {
                double prediction = (lastDeltaY - 0.08) * 0.9800000190734863;

                if (!(deltaY - prediction < 1E-13) && lastDeltaY > 0 && deltaY != 0) {
                    if (threshold++ >= 4) {
                        debug("Gravity/Motion detected");
                    }
                }
            } else {
                threshold -= 0.05;
            }

            lastDeltaY = deltaY;
        }
    }
}