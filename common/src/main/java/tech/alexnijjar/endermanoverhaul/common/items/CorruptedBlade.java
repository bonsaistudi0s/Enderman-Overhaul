package tech.alexnijjar.endermanoverhaul.common.items;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.ModUtils;

public class CorruptedBlade extends SwordItem {
    public CorruptedBlade(Tier tier, int attackDamageModifier, float attackSpeedModifier, Properties properties) {
        super(tier, attackDamageModifier, attackSpeedModifier, properties);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        if (target.level().random.nextInt(4) == 0) {
            ModUtils.teleportTarget(target.level(), target, 16);
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
