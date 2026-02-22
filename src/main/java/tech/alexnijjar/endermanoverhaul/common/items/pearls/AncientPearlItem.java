package tech.alexnijjar.endermanoverhaul.common.items.pearls;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.EnderpearlItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantComponents;
import tech.alexnijjar.endermanoverhaul.common.entities.pets.BasePetEnderman;
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

            CustomData data = getPet(stack);
            if (!data.isEmpty()) {
                CompoundTag tag = data.copyTag();
                String id = tag.getString("id");
                if (id.isEmpty()) return InteractionResultHolder.fail(stack);
                EntityType<?> entityType = EntityType.byString(id).orElse(null);
                if (entityType == null) return InteractionResultHolder.fail(stack);
                Entity pet = entityType.create(level);
                if (pet instanceof BasePetEnderman enderman) {
                    enderman.setOwnerUUID(player.getUUID());
                }
                pet.load(tag);
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
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(ConstantComponents.ANCIENT_PEARL_TOOLTIP_1);
        tooltipComponents.add(ConstantComponents.ANCIENT_PEARL_TOOLTIP_2);

        if (hasPet(stack)) {
            CustomData data = getPet(stack);
            if (data.isEmpty()) return;
            int health = data.copyTag().getInt("Health");
            tooltipComponents.add(Component.translatable("tooltip.endermanoverhaul.ancient_pet", health).withStyle(ChatFormatting.GREEN));
        }
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotId, boolean isSelected) {
        if (level.isClientSide()) return;
        if ((entity.tickCount + entity.getId()) % HEAL_TICK_RATE != 0) return;
        if (this.hasPet(stack)) {
            CustomData data = this.getPet(stack);
            if (data.isEmpty()) return;
            data = data.update(tag -> tag.putInt("Health", Math.min(tag.getInt("Health") + 1, 30)));
            setPet(stack, data);
        }
    }

    @Override
    public boolean isFoil(@NotNull ItemStack stack) {
        return this.hasPet(stack) || super.isFoil(stack);
    }

    public boolean hasPet(ItemStack stack) {
        return !this.getPet(stack).isEmpty();
    }

    public CustomData getPet(ItemStack stack) {
        return stack.getOrDefault(DataComponents.ENTITY_DATA, CustomData.EMPTY);
    }

    public void setPet(ItemStack stack, CustomData data) {
        stack.set(DataComponents.ENTITY_DATA, data);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }
}
