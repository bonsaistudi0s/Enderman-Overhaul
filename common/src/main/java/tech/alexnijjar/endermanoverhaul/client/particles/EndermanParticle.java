package tech.alexnijjar.endermanoverhaul.client.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.NotNull;

public class EndermanParticle extends TextureSheetParticle {
    private final double xStart;
    private final double yStart;
    private final double zStart;

    protected EndermanParticle(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, float rColor, float gColor, float bColor) {
        super(clientLevel, d, e, f);
        this.xd = g;
        this.yd = h;
        this.zd = i;
        this.x = d;
        this.y = e;
        this.z = f;
        this.xStart = this.x;
        this.yStart = this.y;
        this.zStart = this.z;
        this.quadSize = 0.1F * (this.random.nextFloat() * 0.2F + 0.5F);
        this.rCol = rColor;
        this.gCol = gColor;
        this.bCol = bColor;
        this.lifetime = (int) (Math.random() * 10.0) + 40;
    }

    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public void move(double x, double y, double z) {
        this.setBoundingBox(this.getBoundingBox().move(x, y, z));
        this.setLocationFromBoundingbox();
    }

    public float getQuadSize(float scaleFactor) {
        float f = ((float) this.age + scaleFactor) / (float) this.lifetime;
        f = 1.0F - f;
        f *= f;
        f = 1.0F - f;
        return this.quadSize * f;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            float f = (float) this.age / (float) this.lifetime;
            float g = f;
            f = -f + f * f * 2.0F;
            f = 1.0F - f;
            this.x = this.xStart + this.xd * (double) f;
            this.y = this.yStart + this.yd * (double) f + (double) (1.0F - g);
            this.z = this.zStart + this.zd * (double) f;
        }
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprite;
        private final float rColor;
        private final float gColor;
        private final float bColor;

        public Provider(SpriteSet spriteSet) {
            this(spriteSet, 1, 1, 1);
        }

        public Provider(SpriteSet spriteSet, float rColor, float gColor, float bColor) {
            this.sprite = spriteSet;
            this.rColor = rColor;
            this.gColor = gColor;
            this.bColor = bColor;
        }

        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EndermanParticle endermanParticle = new EndermanParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.rColor, this.gColor, this.bColor);
            endermanParticle.pickSprite(this.sprite);
            return endermanParticle;
        }
    }
}
