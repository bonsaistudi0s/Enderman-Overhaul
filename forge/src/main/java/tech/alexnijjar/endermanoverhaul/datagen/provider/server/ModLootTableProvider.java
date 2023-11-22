package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY)));
    }

    private static class EntityLootTables implements LootTableSubProvider {
        @Override
        public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
            output.accept(getEntity(ModEntityTypes.BADLANDS_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.TINY_SKULL.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f)))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.BADLANDS_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.CAVE_ENDERMAN.get()), getDefaultEndermanLootTable()
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

            output.accept(getEntity(ModEntityTypes.CRIMSON_FOREST_ENDERMAN.get()), getEmptyLootTable(ModItems.CRIMSON_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.CRIMSON_FUNGUS).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.DARK_OAK_ENDERMAN.get()), getDefaultEndermanLootTable());

            output.accept(getEntity(ModEntityTypes.DESERT_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EMERALD).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.05f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.END_ENDERMAN.get()), getEmptyLootTable(ModItems.CORRUPTED_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.ENDERMAN_TOOTH.get()).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 2.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.04f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.END_ISLANDS_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ANCIENT_PEARL));

            output.accept(getEntity(ModEntityTypes.FLOWER_FIELDS_ENDERMAN.get()), getDefaultEndermanLootTable());

            output.accept(getEntity(ModEntityTypes.ICE_SPIKES_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ICY_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.PACKED_ICE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.MUSHROOM_FIELDS_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.RED_MUSHROOM).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.NETHER_WASTES_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.CORAL_ENDERMAN.get()), getEmptyLootTable(ModItems.BUBBLE_PEARL));

            output.accept(getEntity(ModEntityTypes.SAVANNA_ENDERMAN.get()), getDefaultEndermanLootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.ACACIA_LOG).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 4.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SAVANNAS_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.SNOWY_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.ICY_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.SNOWBALL).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(ModItems.SNOWY_HOOD.get()).apply(SetItemCountFunction
                        .setCount(ConstantValue.exactly(1))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.SOULSAND_VALLEY_ENDERMAN.get()), getEmptyLootTable(ModItems.SOUL_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.SWAMP_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.SUMMONER_PEARL));

            output.accept(getEntity(ModEntityTypes.WARPED_FOREST_ENDERMAN.get()), getEmptyLootTable(ModItems.WARPED_PEARL)
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.WARPED_FUNGUS).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.WINDSWEPT_HILLS_ENDERMAN.get()), getDefaultEndermanLootTable(ModItems.SUMMONER_PEARL));
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

    private static LootTable.Builder getEmptyLootTable(Supplier<Item> additionalPearl) {
        return LootTable.lootTable()
            .withPool(LootPool.lootPool()
                .add(LootItem.lootTableItem(additionalPearl.get()).apply(SetItemCountFunction
                    .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                    .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))));
    }

    private static ResourceLocation getEntity(EntityType<?> entity) {
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "entities/%s".formatted(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity)).getPath()));
    }
}
