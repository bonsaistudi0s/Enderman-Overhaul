package tech.alexnijjar.endermanoverhaul.datagen.provider.server;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithLootingCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;

import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

public class ModLootTableProvider extends LootTableProvider {
    public static final ResourceLocation CAVE_ENDERMAN = new ResourceLocation(EndermanOverhaul.MOD_ID, "entities/cave_enderman");

    public ModLootTableProvider(PackOutput output) {
        super(output, Set.of(), List.of(new SubProviderEntry(EntityLootTables::new, LootContextParamSets.ENTITY)));
    }

    private static class EntityLootTables implements LootTableSubProvider {
        @Override
        public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> output) {
            output.accept(CAVE_ENDERMAN, LootTable.lootTable()
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.COAL)).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 3))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.RAW_IRON)).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 3))))
                .withPool(LootPool.lootPool()
                    .add(LootItem.lootTableItem(Items.EMERALD)).apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))
                    ).when(LootItemRandomChanceWithLootingCondition.randomChanceAndLootingBoost(0.1f, 0.025f)))
            );
        }
    }
}
