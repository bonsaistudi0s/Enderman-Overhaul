package tech.alexnijjar.endermanoverhaul.common.constants;

import software.bernie.geckolib3.core.builder.AnimationBuilder;

public class ConstantAnimations {
    public static final AnimationBuilder IDLE = new AnimationBuilder().loop("animation.enderman.idle");
    public static final AnimationBuilder WALK = new AnimationBuilder().loop("animation.enderman.walk");
    public static final AnimationBuilder RUN = new AnimationBuilder().loop("animation.enderman.run");
    public static final AnimationBuilder ANGRY = new AnimationBuilder().loop("animation.enderman.angry");
    public static final AnimationBuilder ATTACK = new AnimationBuilder().loop("animation.enderman.attack");
    public static final AnimationBuilder HOLDING = new AnimationBuilder().loop("animation.enderman.hold");
    public static final AnimationBuilder BITE = new AnimationBuilder().loop("animation.enderman.bite");
    public static final AnimationBuilder POSSESS = new AnimationBuilder().loop("animation.enderman.possess");
    public static final AnimationBuilder SWIM = new AnimationBuilder().loop("animation.enderman.swim");

    public static final AnimationBuilder SCARAB_IDLE = new AnimationBuilder().loop("animation.scarab.idle");
    public static final AnimationBuilder SCARAB_WALK = new AnimationBuilder().loop("animation.scarab.walk");
    public static final AnimationBuilder SPIRIT_FLY = new AnimationBuilder().loop("animation.spirit.fly");

    public static final AnimationBuilder SPIN = new AnimationBuilder().loop("animation.enderman.spin");
}
