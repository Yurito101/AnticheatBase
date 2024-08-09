package me.yurito.anticheatbase.checks.enums;

/**
 * A checktype enumerations class that we'll use on our checks
 */
public enum CheckType {
    AIM("Aim", CheckCategory.COMBAT),
    AUTOCLICKER("AutoClicker", CheckCategory.COMBAT),
    BADPACKETS("BadPackets", CheckCategory.WORLD),
    FLY("Fly", CheckCategory.MOVEMENT),
    FLIGHT("Flight", CheckCategory.MOVEMENT),
    GROUND("Ground", CheckCategory.WORLD),
    GROUNDSPOOF("GroundSpoof", CheckCategory.MOVEMENT),
    KILLAURA("KillAura", CheckCategory.COMBAT),
    SCAFFOLD("Scaffold", CheckCategory.WORLD),
    STEP("Step", CheckCategory.MOVEMENT),
    SPEED("Speed", CheckCategory.MOVEMENT),
    MOTION("Motion", CheckCategory.MOVEMENT),
    NOFALL("NoFall", CheckCategory.MOVEMENT),
    JESUS("Jesus", CheckCategory.MOVEMENT),
    VEHICLE("Vehicle", CheckCategory.MOVEMENT),
    ELYTRA("Elytra", CheckCategory.MOVEMENT),
    TIMER("Timer", CheckCategory.WORLD),
    OMNISPRINT("OmniSprint", CheckCategory.MOVEMENT),
    NOSLOW("NoSlow", CheckCategory.MOVEMENT),
    REACH("Reach", CheckCategory.COMBAT),
    VELOCITY("Velocity", CheckCategory.COMBAT),
    INVENTORY("Inventory", CheckCategory.WORLD),
    INTERACT("Interact", CheckCategory.WORLD),
    FASTCLIMB("FastClimb", CheckCategory.MOVEMENT),
    HITBOX("Hitbox", CheckCategory.COMBAT);

    private final String checkName;
    private final CheckCategory checkCategory;

    CheckType(String checkName, CheckCategory checkCategory) {
        this.checkName = checkName;
        this.checkCategory = checkCategory;
    }

    public String getCheckName() {
        return checkName;
    }

    public CheckCategory getCheckCategory() {
        return checkCategory;
    }
}