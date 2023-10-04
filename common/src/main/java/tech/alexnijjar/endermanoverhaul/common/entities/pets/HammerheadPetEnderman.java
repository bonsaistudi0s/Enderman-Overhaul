package tech.alexnijjar.endermanoverhaul.common.entities.pets;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class HammerheadPetEnderman extends BasePetEnderman {
    public HammerheadPetEnderman(EntityType<? extends BaseEnderman> entityType, Level level) {
        super(entityType, level);
    }

    public HammerheadPetEnderman(Level level, Player owner) {
        super(ModEntityTypes.HAMMERHEAD_PET_ENDERMAN.get(), level, owner);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 50.0)
            .add(Attributes.MOVEMENT_SPEED, 0.24)
            .add(Attributes.ATTACK_DAMAGE, 6.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }

    @Override
    public boolean playRunAnimWhenAngry() {
        return false;
    }
}
