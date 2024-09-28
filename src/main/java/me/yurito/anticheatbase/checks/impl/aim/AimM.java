package me.yurito.anticheatbase.checks.impl.aim;

import me.yurito.anticheatbase.checks.enums.CheckType;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;
import me.yurito.anticheatbase.utils.MathUtils;

public class AimM extends Check {
    public AimM(Profile profile) {
        super(profile, CheckType.AIM, "M", "");
    }

    private static final double MODULO_THRESHOLD = 90.0;
    private static final double LINEAR_THRESHOLD = 0.10000000149011612;

    @Override
    public void handle(Packet packet) {
        if (packet.isRotation()) {
            final float yaw = profile.getRotationData().getYaw();
            final float pitch = profile.getRotationData().getPitch();
            final float deltaYaw = profile.getRotationData().getDeltaYaw();
            final float lastDeltaYaw = profile.getRotationData().getLastDeltaYaw();
            final float deltaPitch = profile.getRotationData().getDeltaPitch();
            final float lastDeltaPitch = profile.getRotationData().getLastDeltaPitch();
            final double divX = MathUtils.getAbsoluteGcd(deltaPitch, lastDeltaPitch);
            final double divY = MathUtils.getAbsoluteGcd(deltaYaw, lastDeltaYaw);
            final double deltaX = deltaYaw / divX;
            final double deltaY = deltaPitch / divY;
            final double lastDeltaX = lastDeltaYaw / divX;
            final double lastDeltaY = lastDeltaPitch / divY;
            if (profile.getRotationData().getCinematicProcessor().isCinematic()) return;
            if (deltaYaw > 0.0 && deltaPitch > 0.0 && deltaYaw < 20.0F && deltaPitch < 20.0F) {
                final double moduloX = deltaX % lastDeltaX;
                final double moduloY = deltaY % lastDeltaY;
                final double floorModuloX = Math.abs(Math.floor(moduloX) - moduloX);
                final double floorModuloY = Math.abs(Math.floor(moduloY) - moduloY);
                final boolean invalidX = moduloX > 90.0 && floorModuloX > 0.10000000149011612;
                final boolean invalidY = moduloY > 90.0 && floorModuloY > 0.10000000149011612;
                if (invalidX && invalidY) {
                    if (increaseBuffer() > 8) {
                        fail();
                    }
                } else {
                    decreaseBuffer();
                }
            }
        }
    }
}
