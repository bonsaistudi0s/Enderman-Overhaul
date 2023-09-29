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

public class PetEnderman extends BasePetEnderman {
    public PetEnderman(EntityType<? extends BaseEnderman> entityType, Level level) {
        super(entityType, level);
    }

    public PetEnderman(Level level, Player owner) {
        super(ModEntityTypes.PET_ENDERMAN.get(), level, owner);
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 40.0)
            .add(Attributes.MOVEMENT_SPEED, 0.27)
            .add(Attributes.ATTACK_DAMAGE, 7.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }
}
