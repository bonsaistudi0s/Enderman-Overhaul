package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSet;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootContextParamSet>> getTables() {
        return List.of(Pair.of(EntityLootTables::new, LootContextParamSets.ENTITY));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationContext validationTracker) {
    }

    private static class EntityLootTables extends EntityLoot {
        @Override
        protected void addTables() {
            add(getEntity(ModEntityTypes.BADLANDS_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.TINY_SKULL.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f)))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.BADLANDS_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            add(getEntity(ModEntityTypes.CAVE_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.COAL).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.RAW_IRON).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            add(getEntity(ModEntityTypes.CRIMSON_FOREST_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.CRIMSON_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.CRIMSON_FUNGUS).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.DARK_OAK_ENDERMAN.get()), getDefaultEndermanLootTable());

            add(getEntity(ModEntityTypes.DESERT_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.025f))));

            add(getEntity(ModEntityTypes.END_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.CORRUPTED_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.ENDERMAN_TOOTH.get()).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.04f, 0.025f))));

            add(getEntity(ModEntityTypes.END_ISLANDS_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ANCIENT_PEARL));

            add(getEntity(ModEntityTypes.FLOWER_FIELDS_ENDERMAN.get()), getDefaultEndermanLootTable());

            add(getEntity(ModEntityTypes.ICE_SPIKES_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ICY_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.PACKED_ICE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.RED_MUSHROOM).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.NETHER_WASTES_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.OCEAN_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.BUBBLE_PEARL));

            add(getEntity(ModEntityTypes.SAVANNA_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.ACACIA_LOG).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 4.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SAVANNAS_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            add(getEntity(ModEntityTypes.SNOWY_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ICY_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SNOWBALL).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SNOWY_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            add(getEntity(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.SOUL_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.SWAMP_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.SUMMONER_PEARL));

            add(getEntity(ModEntityTypes.WARPED_FOREST_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.WARPED_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.WARPED_FUNGUS).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            add(getEntity(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.SUMMONER_PEARL));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return List.of();
        }
    }

    private static LootTable.Builder getDefaultEndermanLootTable() {
        return LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction
                    .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                    .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))));
    }

    private static LootTable.Builder getDefaultEndermanLootTable(Supplier<Item> additionalPearl) {
        return LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction
                    .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                    .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                .add(LootItem.lootTableItem(additionalPearl.get()).apply(SetItemCountFunction
                    .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                    .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))));
    }

    private static ResourceLocation getEntity(EntityType<?> entity) {
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "entities/%s".formatted(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity)).getPath()));
    }
}
