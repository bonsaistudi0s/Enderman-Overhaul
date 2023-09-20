package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY)));
    }

    private static class EntityLootTables implements LootTableSubProvider {
        @Override
        public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
            output.accept(getEntity(ModEntityTypes.CAVE_ENDERMAN.get()), LootTable.lootTable()
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
                        .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))
                    .when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f))));

            output.accept(getEntity(ModEntityTypes.SAVANNA_ENDERMAN.get()), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.ACACIA_LOG).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 4.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));

            output.accept(getEntity(ModEntityTypes.NETHER_WASTES_ENDERMAN.get()), LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.ENDER_PEARL).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 1.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f)))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.BONE).apply(SetItemCountFunction
                        .setCount(UniformGenerator.between(0.0f, 3.0f))).apply(LootingEnchantFunction
                        .lootingMultiplier(UniformGenerator.between(0.0f, 1.0f))))));
        }
    }

    private static ResourceLocation getEntity(EntityType<?> entity) {
        return new ResourceLocation(EndermanOverhaul.MOD_ID, "entities/%s".formatted(Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.getKey(entity)).getPath()));
    }
}
