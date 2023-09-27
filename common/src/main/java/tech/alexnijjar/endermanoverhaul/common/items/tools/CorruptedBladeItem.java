package tech.alexnijjar.endermanoverhaul.common.items.tools;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;
import tech.alexnijjar.endermanoverhaul.common.constants.ConstantComponents;
import tech.alexnijjar.endermanoverhaul.common.registry.ModItems;
import tech.alexnijjar.endermanoverhaul.common.tags.ModEntityTypeTags;

import java.util.List;

public class CorruptedBladeItem extends SwordItem {
    public CorruptedBladeItem(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (attacker.getType().is(ModEntityTypeTags.CANT_BE_TELEPORTED)) return false;
        if (attacker.level().random.nextInt(4) != 0) {
            ModUtils.teleportTarget(target.level(), target, 32);
        }
        return super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public int getEnchantmentValue() {
        return 22;
    }

    @Override
    public boolean isValidRepairItem(@NotNull ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(ModItems.ENDERMAN_TOOTH.get()) || super.isValidRepairItem(stack, repairCandidate);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        tooltipComponents.add(ConstantComponents.CORRUPTED_BLADE_TOOLTIP);
    }
}
