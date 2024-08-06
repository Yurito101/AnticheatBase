package me.yurito.anticheatbase.checks.impl.killaura;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

public class KillAuraA extends Check {
    public KillAuraA(Profile profile) {
        super(profile, CheckType.KILLAURA, "A", "Checks for KillAura.");
    }

    private long lastFlying, lastFlyingDelay;
    private double average = 50;
    private int hits;

    @Override
    public void handle(Packet packet) {
        if (packet.is(Packet.Type.USE_ENTITY)) {

            final long delta = Math.abs(System.currentTimeMillis() - this.lastFlying);

            average = ((average * 14) + delta) / 15;

            debug("elapsed=" + delta + " current=" + System.currentTimeMillis() + " last=" + lastFlying);

            if (lastFlyingDelay > 10L && lastFlyingDelay < 90L) {
                if (average < 5 && hits++ > 10) {
                    fail("delta=" + average);
                    average = 5;
                }
            }
        } else if (packet.isFlying()) {
            this.lastFlyingDelay = System.currentTimeMillis() - lastFlying;
            this.lastFlying = System.currentTimeMillis();
        }
    }
}