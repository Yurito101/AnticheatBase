package me.yurito.anticheatbase.utils.custom;

import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.playerdata.data.impl.MovementData;
import me.yurito.anticheatbase.playerdata.data.impl.RotationData;

/**
 * A simple class that we'll be using for exempting some checks, We'll cache the booleans every tick to
 * Save up some perfomance except for the ones that get updated by the server.
 * <p>
 * This is similar to Elevated's Exempt method however instead of using Predicates
 * We're caching the booleans as soon as we receive a packet for maximum perfomance.
 * <p>
 * This is a LOT faster especially when having a lot of checks, Using cached booleans instead of
 * Checking for example (player.getAllowFlight()) every single tick on every check.
 */
public class Exempt {

    private final Profile profile;

    public Exempt(Profile profile) {
        this.profile = profile;
    }

    private boolean aim, autoclicker, cinematic, elytra, jesus, movement, velocity, vehicle;

    public void handleExempts(long timeStamp) {

        MovementData movementData = profile.getMovementData();
        RotationData rotationData = profile.getRotationData();

        //Example
        this.movement = movementData.getDeltaXZ() == 0D && movementData.getDeltaY() == 0D;

        this.cinematic = rotationData.getCinematicProcessor().isCinematic();
    }

    public boolean aim() {
        return this.aim;
    }

    public boolean autoclicker() {
        return this.autoclicker;
    }

    public boolean cinematic() {
        return this.cinematic;
    }

    public boolean elytra() {
        return this.elytra;
    }

    public boolean jesus() {
        return this.jesus;
    }

    public boolean movement() {
        return this.movement;
    }

    public boolean velocity() {
        return this.velocity;
    }

    public boolean vehicle() {
        return this.vehicle;
    }
}