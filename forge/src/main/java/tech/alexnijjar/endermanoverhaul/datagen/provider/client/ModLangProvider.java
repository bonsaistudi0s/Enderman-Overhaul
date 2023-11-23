package tech.alexnijjar.endermanoverhaul.datagen.provider.client;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.data.LanguageProvider;
import org.codehaus.plexus.util.StringUtils;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantComponents;
import tech.alexnijjar.endermanoverhaul.common.registry.ModBlocks;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, EndermanOverhaul.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        ModBlocks.BLOCKS.stream()
            .forEach(entry -> addBlock(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModItems.ITEMS.stream()
            .filter(i -> !(i.get() instanceof BlockItem))
            .forEach(entry -> addItem(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        ModEntityTypes.ENTITY_TYPES.stream()
            .forEach(entry -> addEntityType(entry,
                StringUtils.capitaliseAllWords(entry
                    .getId()
                    .getPath()
                    .replace("_", " "))));

        add("itemGroup.endermanoverhaul.main", "Enderman Overhaul");

        add(ConstantComponents.CORRUPTED_PEARL_TOOLTIP.getString(), "Teleports targets to a random location");
        add(ConstantComponents.SOUL_PEARL_TOOLTIP_1.getString(), "Shift-right click to bind an entity");
        add(ConstantComponents.SOUL_PEARL_TOOLTIP_2.getString(), "Teleports the bound entity to the target location");
        add(ConstantComponents.ANCIENT_PEARL_TOOLTIP.getString(), "Summons a friendly enderman to fight for you");
        add(ConstantComponents.BUBBLE_PEARL_TOOLTIP.getString(), "Accurate Ender Pearl with no gravity");
        add(ConstantComponents.ICY_PEARL_TOOLTIP.getString(), "Freezes nearby targets on hit");
        add(ConstantComponents.SUMMONER_PEARL_TOOLTIP.getString(), "Teleports nearby targets on hit");
        add(ConstantComponents.CRIMSON_PEARL_TOOLTIP.getString(), "Applies Strength II when teleporting");
        add(ConstantComponents.WARPED_PEARL_TOOLTIP.getString(), "Applies Resistance II when teleporting");

        add(ConstantComponents.HOOD_TOOLTIP.getString(), "Allows you to look at endermen without angering them");

        add(ConstantComponents.CORRUPTED_BLADE_TOOLTIP.getString(), "Teleports targets randomly");
        add(ConstantComponents.CORRUPTED_SHIELD_TOOLTIP.getString(), "Teleports attackers randomly");

        add("tooltip.endermanoverhaul.bound_to", "Bound to: %s");
        add("tooltip.endermanoverhaul.ancient_pet", "Pet health: %.1f");
        add(ConstantComponents.NOT_BOUND.getString(), "Not bound");

        add("tag.item.endermanoverhaul.ender_pearls", "Ender Pearls");

        add("subtitles.endermanoverhaul.entity.cave_enderman.ambient", "Cave Enderman vwoops");
        add("subtitles.endermanoverhaul.entity.cave_enderman.hurt", "Cave Enderman hurts");
        add("subtitles.endermanoverhaul.entity.plant_enderman.ambient", "Plant Enderman vwoops");
        add("subtitles.endermanoverhaul.entity.plant_enderman.hurt", "Plant Enderman hurts");
        add("subtitles.endermanoverhaul.entity.tall_enderman.ambient", "Tall Enderman vwoops");
        add("subtitles.endermanoverhaul.entity.tall_enderman.death", "Tall Enderman dies");
        add("subtitles.endermanoverhaul.entity.tall_enderman.stare", "Tall Enderman cries out");

        add("subtitles.endermanoverhaul.entity.dark_oak_enderman.ambient", "Dark Oak Enderman vwoops");
        add("subtitles.endermanoverhaul.entity.dark_oak_enderman.darkness", "Dark Oak Enderman darkens");
        add("subtitles.endermanoverhaul.entity.dark_oak_enderman.stare", "Dark Oak Enderman cries out");

        add("subtitles.endermanoverhaul.entity.ancient_pearl.hit", "Ancient Pearl Hits");
        add("subtitles.endermanoverhaul.entity.bubble_pearl.hit", "Bubble Pearl Pops");
        add("subtitles.endermanoverhaul.entity.bubble_pearl.thrown", "Bubble Pearl Thrown");
        add("subtitles.endermanoverhaul.entity.corrupted_pearl.hit", "Corrupted Pearl Hits");
        add("subtitles.endermanoverhaul.entity.soul_pearl.hit", "Soul Pearl Hits");
        add("subtitles.endermanoverhaul.entity.summoner_pearl.hit", "Summoner Pearl Hits");
        add("subtitles.endermanoverhaul.entity.icy_pearl.hit", "Icy Pearl Hits");

        add("config.endermanoverhaul.allowPickingUpBlocks", "Allow Picking Up Blocks");
        add("config.endermanoverhaul.friendlyEndermanTeleport", "Friendly Enderman Teleport");
        add("config.endermanoverhaul.friendlyEndermanDespawn", "Friendly Enderman Despawn");

        add("config.endermanoverhaul.replaceDefaultEnderman", "Replace Default Enderman");
        add("config.endermanoverhaul.allowSpawning", "Allow Spawning");
        add("config.endermanoverhaul.spawnBadlandsEnderman", "Spawn Badlands Enderman");
        add("config.endermanoverhaul.spawnCaveEnderman", "Spawn Cave Enderman");
        add("config.endermanoverhaul.spawnCrimsonForestEnderman", "Spawn Crimson Forest Enderman");
        add("config.endermanoverhaul.spawnDarkOakEnderman", "Spawn Dark Oak Enderman");
        add("config.endermanoverhaul.spawnDesertEnderman", "Spawn Desert Enderman");
        add("config.endermanoverhaul.spawnEndEnderman", "Spawn End Enderman");
        add("config.endermanoverhaul.spawnEndIslandsEnderman", "Spawn End Islands Enderman");
        add("config.endermanoverhaul.spawnFlowerFieldsEnderman", "Spawn Flower Fields Enderman");
        add("config.endermanoverhaul.spawnIceSpikesEnderman", "Spawn Ice Spikes Enderman");
        add("config.endermanoverhaul.spawnMushroomFieldsEnderman", "Spawn Mushroom Fields Enderman");
        add("config.endermanoverhaul.spawnNetherWastesEnderman", "Spawn Nether Wastes Enderman");
        add("config.endermanoverhaul.spawnCoralEnderman", "Spawn Coral Enderman");
        add("config.endermanoverhaul.spawnSavannaEnderman", "Spawn Savanna Enderman");
        add("config.endermanoverhaul.spawnSnowyEnderman", "Spawn Snowy Enderman");
        add("config.endermanoverhaul.spawnSoulsandValleyEnderman", "Spawn Soulsand Valley Enderman");
        add("config.endermanoverhaul.spawnSwampEnderman", "Spawn Swamp Enderman");
        add("config.endermanoverhaul.spawnWarpedForestEnderman", "Spawn Warped Forest Enderman");
        add("config.endermanoverhaul.spawnWindsweptHillsEnderman", "Spawn Windswept Hills Enderman");
    }
}
