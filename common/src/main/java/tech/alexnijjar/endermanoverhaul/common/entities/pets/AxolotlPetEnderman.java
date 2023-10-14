package tech.alexnijjar.endermanoverhaul.common.entities.pets;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.NotNull;
import tech.alexnijjar.endermanoverhaul.common.entities.base.BaseEnderman;
import tech.alexnijjar.endermanoverhaul.common.registry.ModEntityTypes;

public class AxolotlPetEnderman extends BasePetEnderman {
    public AxolotlPetEnderman(EntityType<? extends BaseEnderman> entityType, Level level) {
        super(entityType, level);
    }

    public AxolotlPetEnderman(Level level, Player owner) {
        super(ModEntityTypes.AXOLOTL_PET_ENDERMAN.get(), level, owner);
        setPathfindingMalus(BlockPathTypes.WATER, 0.0f);
    }

    @Override
    public boolean isSensitiveToWater() {
        return false;
    }

    public static @NotNull AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 30.0)
            .add(Attributes.MOVEMENT_SPEED, 0.3)
            .add(Attributes.ATTACK_DAMAGE, 8.0)
            .add(Attributes.FOLLOW_RANGE, 64.0);
    }
}
