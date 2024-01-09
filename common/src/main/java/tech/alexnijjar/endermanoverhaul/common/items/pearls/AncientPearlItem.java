package tech.alexnijjar.endermanoverhaul.common.items.pearls;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantComponents;
import tech.alexnijjar.endermanoverhaul.common.entities.projectiles.ThrownAncientPearl;

import java.util.List;

public class AncientPearlItem extends EnderpearlItem {
    public static final int HEAL_TICK_RATE = 2500;

    public AncientPearlItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_PEARL_THROW, SoundSource.NEUTRAL, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        player.getCooldowns().addCooldown(this, 20);
        if (!level.isClientSide()) {
            ThrownAncientPearl pearl;

            CompoundTag tag = stack.getOrCreateTag();
            if (tag.contains("PetEntity")) {
                var pet = ThrownAncientPearl.createPet(level, player, switch (tag.getString("PetType")) {
                    case "hammerhead_pet_enderman" -> 1;
                    case "axolotl_pet_enderman" -> 2;
                    default -> 0;
                });
                pet.load(tag.getCompound("PetEntity"));

                pearl = new ThrownAncientPearl(level, player, pet);
            } else {
                pearl = new ThrownAncientPearl(level, player);
            }

            pearl.setItem(stack);
            pearl.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.5f, 1.0f);
            level.addFreshEntity(pearl);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(ConstantComponents.ANCIENT_PEARL_TOOLTIP);

        if (level == null) return;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("PetEntity")) {
            CompoundTag petTag = tag.getCompound("PetEntity");
            int health = petTag.getInt("Health");
            tooltipComponents.add(Component.translatable("tooltip.endermanoverhaul.ancient_pet", health).withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) return;
        if (entity.tickCount % HEAL_TICK_RATE != 0) return;
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("PetEntity")) {
            CompoundTag petTag = tag.getCompound("PetEntity");
            petTag.putInt("Health", Math.min(petTag.getInt("Health") + 1, 30));
            tag.put("PetEntity", petTag);
            stack.setTag(tag);
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return stack.hasTag() && stack.getOrCreateTag().contains("PetEntity");
    }
}
