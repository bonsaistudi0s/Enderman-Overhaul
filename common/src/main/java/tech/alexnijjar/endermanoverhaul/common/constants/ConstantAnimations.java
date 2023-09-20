package tech.alexnijjar.endermanoverhaul.common.constants;

import software.bernie.geckolib.core.animation.RawAnimation;

public class ConstantAnimations {
    public static final RawAnimation IDLE = RawAnimation.begin().thenLoop("animation.enderman.idle");
    public static final RawAnimation WALK = RawAnimation.begin().thenLoop("animation.enderman.walk");
    public static final RawAnimation RUN = RawAnimation.begin().thenLoop("animation.enderman.run");
    public static final RawAnimation ANGRY = RawAnimation.begin().thenLoop("animation.enderman.angry");
    public static final RawAnimation BITE = RawAnimation.begin().thenLoop("animation.enderman.bite");
}
