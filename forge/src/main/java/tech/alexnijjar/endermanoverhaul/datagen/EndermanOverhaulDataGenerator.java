package tech.alexnijjar.endermanoverhaul.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tech.alexnijjar.endermanoverhaul.EndermanOverhaul;
import tech.alexnijjar.endermanoverhaul.datagen.provider.client.ModItemModelProvider;
import tech.alexnijjar.endermanoverhaul.datagen.provider.client.ModLangProvider;
import tech.alexnijjar.endermanoverhaul.datagen.provider.server.ModBlockTagProvider;
import tech.alexnijjar.endermanoverhaul.datagen.provider.server.ModEntityTypeTagProvider;
import tech.alexnijjar.endermanoverhaul.datagen.provider.server.ModLootTableProvider;

@Mod.EventBusSubscriber(modid = EndermanOverhaul.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class EndermanOverhaulDataGenerator {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new ModLangProvider(generator));
        generator.addProvider(event.includeClient(), new ModItemModelProvider(generator, existingFileHelper));

        generator.addProvider(event.includeServer(), new ModLootTableProvider(generator));

        generator.addProvider(event.includeServer(), new ModBlockTagProvider(generator, existingFileHelper));
        generator.addProvider(event.includeServer(), new ModEntityTypeTagProvider(generator, existingFileHelper));
    }
}
