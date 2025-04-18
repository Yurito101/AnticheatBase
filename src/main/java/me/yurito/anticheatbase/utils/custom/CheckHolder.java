package me.yurito.anticheatbase.utils.custom;

import me.yurito.anticheatbase.checks.annotations.Testing;
import me.yurito.anticheatbase.checks.impl.aim.*;
import me.yurito.anticheatbase.checks.impl.autoclicker.AutoClickerA;
import me.yurito.anticheatbase.checks.impl.autoclicker.AutoClickerB;
import me.yurito.anticheatbase.checks.impl.badpackets.BadPacketsA;
import me.yurito.anticheatbase.checks.impl.badpackets.BadPacketsB;
import me.yurito.anticheatbase.checks.impl.badpackets.BadPacketsC;
import me.yurito.anticheatbase.checks.impl.flight.FlightA;
import me.yurito.anticheatbase.checks.impl.flight.FlightB;
import me.yurito.anticheatbase.checks.impl.groundspoof.GroundSpoofA;
import me.yurito.anticheatbase.checks.impl.killaura.KillAuraA;
import me.yurito.anticheatbase.checks.impl.killaura.KillAuraB;
import me.yurito.anticheatbase.checks.impl.speed.SpeedA;
import me.yurito.anticheatbase.checks.impl.speed.SpeedB;
import me.yurito.anticheatbase.checks.types.Check;
import me.yurito.anticheatbase.managers.profile.Profile;
import me.yurito.anticheatbase.processors.Packet;

import java.util.Arrays;

public class CheckHolder {

    private final Profile profile;
    private Check[] checks;
    private int checksSize;
    private boolean testing; //Used for testing new checks

    public CheckHolder(Profile profile) {
        this.profile = profile;
    }

    public void runChecks(Packet packet) {
        /*
        Fastest way to loop through many objects, If you think this is stupid
        Then benchmark the long term perfomance yourself with many profilers and java articles.
         */
        for (int i = 0; i < this.checksSize; i++) this.checks[i].handle(packet);
    }

    public void registerAll() {

        /*
         * Check initialization
         */
        addChecks(

                /*
                AimAssist
                 */
                new AimA(this.profile),
                new AimA1(this.profile),
                new AimB(this.profile),
                new AimB1(this.profile),
                new AimC(this.profile),
                new AimD(this.profile),
                new AimE(this.profile),
                new AimF(this.profile),
                new AimG(this.profile),
                new AimH(this.profile),
                new AimI(this.profile),
                new AimJ(this.profile),
                new AimK(this.profile),
                new AimL(this.profile),
                new AimM(this.profile),

                /*
                AutoClicker
                 */
                new AutoClickerA(this.profile),
                new AutoClickerB(this.profile),

                /*
                BadPackets
                 */
                new BadPacketsA(this.profile),
                new BadPacketsB(this.profile),
                new BadPacketsC(this.profile),

                /*
                Flight
                 */
                new FlightA(this.profile),
                new FlightB(this.profile),

                /*
                GroundSpoof
                 */
                new GroundSpoofA(this.profile),

                /*
                KillAura
                 */
                new KillAuraA(this.profile),
                new KillAuraB(this.profile),

                /*
                Speed
                 */
                new SpeedA(this.profile),
                new SpeedB(this.profile)
        );

        /*
        Remove checks if a testing check is present.
         */
        testing:
        {

            /*
            Testing check not present, break.
             */
            if (!this.testing) break testing;

            /*
            Remove the rest of the checks since a testing check is present.
             */
            this.checks = Arrays.stream(this.checks)
                    .filter(check -> check.getClass().isAnnotationPresent(Testing.class))
                    .toArray(Check[]::new);

            /*
            Update the size since we're only going to be running one check.
             */
            this.checksSize = 1;
        }
    }

    private void addChecks(Check... checks) {

        /*
        Create a new check array to account for reloads.
         */
        this.checks = new Check[0];

        /*
        Reset the check size to account for reloads
         */
        this.checksSize = 0;

        /*
        Loop through the input checks
         */
        for (Check check : checks) {

            /*
            Check if this is being used by a GUI, where we put null as the profile
            Or a check with the @Testing annotation is present or disabled.
             */
            if (this.profile != null && (!check.isEnabled() || isTesting(check))) continue;

            /*
            Copy the original array and increment the size just like an ArrayList.
             */
            this.checks = Arrays.copyOf(this.checks, this.checksSize + 1);

            /*
            Update the check.
             */
            this.checks[this.checksSize] = check;

            /*
            Update the check size variable for improved looping perfomance
             */
            this.checksSize++;
        }
    }

    /**
     * If a check with the testing annotation is present, It'll set the testing boolean to true, load it and then
     * Prevent any other checks from registering.
     */
    private boolean isTesting(Check check) {

        if (this.testing) return true;

        /*
        Update the variable and return false in order to register this check
        But not the next ones.
         */
        if (check.getClass().isAnnotationPresent(Testing.class)) this.testing = true;

        return false;
    }

    public Check[] getChecks() {
        return checks;
    }
}