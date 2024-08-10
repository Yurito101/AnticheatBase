package me.yurito.anticheatbase.checks.impl.autoclicker;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class AutoClickerA extends Check {
    public AutoClickerA(Profile profile) {
        super(profile, CheckType.AUTOCLICKER, "A", "Checks for consistent click pattern");
    }

    private double avgSpeed, avgDeviation;
    private int ticks;
    
    @Override
    public void handle(Packet packet) {
        if (packet.is(Packet.Type.ARM_ANIMATION) && !profile.isExempt().autoclicker()) {
            if (ticks > 10 || ticks == 0) {
                return;
            }
            double speed = ticks * 50;
            avgSpeed = ((avgSpeed * 14) + speed) / 15;

            double deviation = Math.abs(speed - avgSpeed);
            avgDeviation = ((avgDeviation * 9) + deviation) / 10;

            if (avgDeviation < 10) {
                if (increaseBuffer() > 8) {
                    fail("dev=" + deviation);
                }
            } else {
                decreaseBufferBy(0.75);
            }
            ticks = 0;
        } else if (packet.isFlying()) {
            ticks++;
        }
    }
}