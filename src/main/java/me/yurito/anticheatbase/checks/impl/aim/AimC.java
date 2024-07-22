package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;
import me.yurito.anticheatbase.processors.Packet;
import me.yurito.anticheatbase.utils.custom.aim.RotationHeuristics;

public class AimC extends Check {
    public AimC(Profile profile) {
        super(profile, CheckType.AIM, "C", "Checks for analysis");
    }

    private final RotationHeuristics deltaYawHeuristics = new RotationHeuristics(100, 1.25F, 7.5F);
    private final RotationHeuristics deltaPitchHeuristics = new RotationHeuristics(100, 1.25F, 7.5F);

    @Override
    public void handle(Packet packet) {
        if (!packet.isRotation() || profile.isExempt().aim()) return;

        RotationData data = profile.getRotationData();

        this.deltaYawHeuristics.process(data.getDeltaYaw());
        this.deltaPitchHeuristics.process(data.getDeltaPitch());

        final RotationHeuristics.HeuristicsResult deltaYawResult = this.deltaYawHeuristics.getResult();
        final RotationHeuristics.HeuristicsResult deltaPitchResult = this.deltaPitchHeuristics.getResult();

        if (deltaYawResult == null || deltaPitchResult == null)
            return;

        final float deltaYawAverage = deltaYawResult.getAverage();
        final float deltaYawMin = deltaYawResult.getMin();
        final float deltaYawMax = deltaYawResult.getMax();
        final int deltaYawLowCount = deltaYawResult.getLowCount();
        final int deltaYawHighCount = deltaYawResult.getHighCount();
        final int deltaYawDuplicates = deltaYawResult.getDuplicates();
        final int deltaYawRoundedCount = deltaYawResult.getRoundedCount();

        final float deltaPitchAverage = deltaPitchResult.getAverage();
        final float deltaPitchMin = deltaPitchResult.getMin();
        final float deltaPitchMax = deltaPitchResult.getMax();
        final int deltaPitchLowCount = deltaPitchResult.getLowCount();
        final int deltaPitchHighCount = deltaPitchResult.getHighCount();
        final int deltaPitchDuplicates = deltaPitchResult.getDuplicates();
        final int deltaPitchRoundedCount = deltaPitchResult.getRoundedCount();
    }
}