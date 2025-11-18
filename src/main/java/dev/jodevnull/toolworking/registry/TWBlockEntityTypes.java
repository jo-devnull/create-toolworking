package dev.jodevnull.toolworking.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.jodevnull.toolworking.content.blocks.BloomeryBlockEntity;
import dev.jodevnull.toolworking.content.blocks.BloomeryRenderer;

import static dev.jodevnull.toolworking.Toolworking.REGISTRATE;

public class TWBlockEntityTypes
{
    public static final BlockEntityEntry<BloomeryBlockEntity> BLOOMERY = REGISTRATE
        .blockEntity("bloomery", BloomeryBlockEntity::new)
        .validBlocks(TWBlocks.BLOOMERY_BLOCK)
        .renderer(() -> BloomeryRenderer::new)
        .register();

    public static void register() {}
}
