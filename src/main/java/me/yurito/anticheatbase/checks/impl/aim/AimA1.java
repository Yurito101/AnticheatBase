package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;
import me.yurito.anticheatbase.processors.Packet;
import me.yurito.anticheatbase.utils.BetterStream;
import me.yurito.anticheatbase.utils.custom.SampleList;

public class AimA1 extends Check {
    public AimA1(Profile profile) {
        super(profile, CheckType.AIM, "A1", "Checks for extra sample size");
    }

    private final SampleList<Double> samples = new SampleList<>(50);

    @Override
    public void handle(Packet packet) {
        if (!packet.isRotation() || profile.isExempt().aim()) return;

        RotationData data = profile.getRotationData();

        final float deltaYaw = data.getDeltaYaw();
        final float deltaPitch = data.getDeltaPitch();

        if (deltaYaw < .5F || deltaPitch < .5F) return;

        final float yawAccel = data.getYawAccel();
        final float pitchAccel = data.getPitchAccel();

        final double delta = Math.abs(yawAccel - pitchAccel);

        this.samples.add(delta);

        if (!this.samples.isCollected()) return;

        final int duplicates = BetterStream.getDuplicates(this.samples);

        if (duplicates > 35) fail();
    }
}
