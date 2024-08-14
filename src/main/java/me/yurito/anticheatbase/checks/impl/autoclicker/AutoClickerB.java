package me.yurito.anticheatbase.checks.impl.autoclicker;

import com.google.common.collect.Lists;
import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;
import me.yurito.anticheatbase.utils.MathUtils;

import java.util.Deque;

public class AutoClickerB extends Check {

    private final Deque<Long> samples;
    private long lastSwing;

    public AutoClickerB(Profile profile) {
        super(profile, CheckType.AUTOCLICKER, "B", "Left clicking too quickly.");
        samples = Lists.newLinkedList();
        lastSwing = 0L;
    }

    public void handle(Packet packet) {
        if (packet.is(Packet.Type.ARM_ANIMATION) && profile.isExempt().autoclicker()) {
            final long now = System.currentTimeMillis();
            final long delay = now - lastSwing;
            samples.add(delay);
            if (samples.size() == 20) {
                final double cps = MathUtils.getCps(samples);
                if (cps > 25) {
                    fail("cps=" + cps);
                }
                samples.clear();
            }
            lastSwing = now;
        }
    }
}
