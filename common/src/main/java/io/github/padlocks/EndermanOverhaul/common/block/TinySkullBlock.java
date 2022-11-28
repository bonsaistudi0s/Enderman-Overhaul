package io.github.padlocks.EndermanOverhaul.common.block;

import net.minecraft.block.Material;
import net.minecraft.block.SkullBlock;
import net.minecraft.sound.BlockSoundGroup;

public class TinySkullBlock extends SkullBlock {
    public TinySkullBlock() {
        super(Type.TINY, Settings.of(Material.DECORATION).strength(0.4F).sounds(BlockSoundGroup.STONE).dynamicBounds());
    }

    public static enum Type implements SkullType {
        TINY;

        private Type() {
        }
    }
}
