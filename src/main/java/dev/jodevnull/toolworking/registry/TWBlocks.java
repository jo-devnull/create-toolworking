package dev.jodevnull.toolworking.registry;

import com.simibubi.create.AllTags;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.jodevnull.toolworking.content.blocks.BloomeryBlock;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

import static dev.jodevnull.toolworking.Toolworking.REGISTRATE;

public class TWBlocks
{
    static {
        REGISTRATE.setCreativeTab(TWCreativeTabs.MAIN_CREATIVE_TAB);
    }

    public static final BlockEntry<BloomeryBlock> BLOOMERY_BLOCK =
        REGISTRATE.block("bloomery", BloomeryBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.mapColor(MapColor.STONE))
            .properties(p -> p.sound(SoundType.STONE))
            .properties(p -> p.lightLevel(BloomeryBlock::getLight))
            .transform(pickaxeOnly())
            .addLayer(() -> RenderType::cutoutMipped)
            .tag(AllTags.AllBlockTags.FAN_TRANSPARENT.tag)
            .item()
            .transform(customItemModel("bloomery_off"))
            .register();

    public static void register() {}
}
