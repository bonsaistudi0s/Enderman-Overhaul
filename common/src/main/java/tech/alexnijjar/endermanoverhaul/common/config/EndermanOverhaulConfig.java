package tech.alexnijjar.endermanoverhaul.common.config;

import com.teamresourceful.resourcefulconfig.common.annotations.Comment;
import com.teamresourceful.resourcefulconfig.common.annotations.Config;
import com.teamresourceful.resourcefulconfig.common.annotations.ConfigEntry;
import com.teamresourceful.resourcefulconfig.common.config.EntryType;
import com.teamresourceful.resourcefulconfig.web.annotations.Gradient;
import com.teamresourceful.resourcefulconfig.web.annotations.Link;
import com.teamresourceful.resourcefulconfig.web.annotations.WebInfo;

@Config("endermanoverhaul")
@WebInfo(
    title = "Enderman Overhaul",
    description = "Enderman overhaul adds biome-specific Enderman!",

    icon = "circle",
    gradient = @Gradient(value = "45deg", first = "#e8bff2", second = "#181a23"),

    links = {
        @Link(value = "https://discord.gg/sGwxnFV", icon = "gamepad-2", title = "Discord"),
        @Link(value = "https://github.com/bonsaistudi0s/enderman-overhaul", icon = "github", title = "GitHub"),

        @Link(value = "https://www.curseforge.com/minecraft/mc-mods/endermanoverhaul", icon = "curseforge", title = "CurseForge"),
        @Link(value = "https://modrinth.com/mod/enderman-overhaul", icon = "modrinth", title = "Modrinth"),
    }
)
public final class EndermanOverhaulConfig {

    @ConfigEntry(
        id = "allowPickingUpBlocks",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.allowPickingUpBlocks"
    )
    public static boolean allowPickingUpBlocks = true;

    @ConfigEntry(
        id = "friendlyEndermanTeleport",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.friendlyEndermanTeleport"
    )
    public static boolean friendlyEndermanTeleport = true;

    @ConfigEntry(
        id = "friendlyEndermanDespawn",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.friendlyEndermanDespawn"
    )
    public static boolean friendlyEndermanDespawn = true;

    @ConfigEntry(
        id = "allowSpawning",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.allowSpawning"
    )
    public static boolean allowSpawning = true;

    @ConfigEntry(
        id = "spawnBadlandsEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnBadlandsEnderman"
    )
    public static boolean spawnBadlandsEnderman = true;

    @ConfigEntry(
        id = "spawnCaveEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnCaveEnderman"
    )
    public static boolean spawnCaveEnderman = true;

    @ConfigEntry(
        id = "spawnCrimsonForestEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnCrimsonForestEnderman"
    )
    public static boolean spawnCrimsonForestEnderman = true;

    @ConfigEntry(
        id = "spawnDarkOakEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnDarkOakEnderman"
    )
    public static boolean spawnDarkOakEnderman = true;

    @ConfigEntry(
        id = "spawnDesertEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnDesertEnderman"
    )
    public static boolean spawnDesertEnderman = true;

    @ConfigEntry(
        id = "spawnEndEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnEndEnderman"
    )
    public static boolean spawnEndEnderman = true;

    @ConfigEntry(
        id = "spawnEndIslandsEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnEndIslandsEnderman"
    )
    public static boolean spawnEndIslandsEnderman = true;

    @ConfigEntry(
        id = "spawnFlowerFieldsEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnFlowerFieldsEnderman"
    )
    public static boolean spawnFlowerFieldsEnderman = true;

    @ConfigEntry(
        id = "spawnIceSpikesEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnIceSpikesEnderman"
    )
    public static boolean spawnIceSpikesEnderman = true;

    @ConfigEntry(
        id = "spawnMushroomFieldsEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnMushroomFieldsEnderman"
    )
    public static boolean spawnMushroomFieldsEnderman = true;

    @ConfigEntry(
        id = "spawnNetherWastesEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnNetherWastesEnderman"
    )
    public static boolean spawnNetherWastesEnderman = true;

    @ConfigEntry(
        id = "spawnCoralEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnCoralEnderman"
    )
    public static boolean spawnCoralEnderman = true;

    @ConfigEntry(
        id = "spawnSavannaEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnSavannaEnderman"
    )
    public static boolean spawnSavannaEnderman = true;

    @ConfigEntry(
        id = "spawnSnowyEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnSnowyEnderman"
    )
    public static boolean spawnSnowyEnderman = true;

    @ConfigEntry(
        id = "spawnSoulsandValleyEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnSoulsandValleyEnderman"
    )
    public static boolean spawnSoulsandValleyEnderman = true;

    @ConfigEntry(
        id = "spawnSwampEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnSwampEnderman"
    )
    public static boolean spawnSwampEnderman = true;

    @ConfigEntry(
        id = "spawnWarpedForestEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnWarpedForestEnderman"
    )
    public static boolean spawnWarpedForestEnderman = true;

    @ConfigEntry(
        id = "spawnWindsweptHillsEnderman",
        type = EntryType.BOOLEAN,
        translation = "config.endermanoverhaul.spawnWindsweptHillsEnderman"
    )
    public static boolean spawnWindsweptHillsEnderman = true;

    @ConfigEntry(
        id = "endEndermanTeleportChance",
        type = EntryType.FLOAT,
        translation = "config.endermanoverhaul.endEndermanTeleportChance"
    )
    @Comment("The chance that an End Enderman will teleport you when it hits you")
    public static float endEndermanTeleportChance = 0.5f;
}
