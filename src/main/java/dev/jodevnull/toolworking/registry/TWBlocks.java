//package dev.jodevnull.toolworking.registry;
//
//import com.tterrag.registrate.util.entry.BlockEntry;
//import net.minecraft.world.level.block.Block;
//import net.minecraft.world.level.block.Blocks;
//import net.minecraft.world.level.block.SoundType;
//import net.minecraft.world.level.material.MapColor;
//
//import static dev.jodevnull.toolworking.Toolworking.REGISTRATE;
//
//public class TWBlocks
//{
//    static {
//        REGISTRATE.setCreativeTab(TWCreativeTabs.MAIN_CREATIVE_TAB);
//    }
//
//    public static final BlockEntry<Block>
//        PROCESSED_CLAY_BLOCK = REGISTRATE.block("processed_clay", Block::new)
//            .initialProperties((() -> Blocks.CLAY))
//            .properties(p -> p.mapColor(MapColor.CLAY))
//            .properties(p -> p.sound(SoundType.GRAVEL))
//            .simpleItem()
//            .register();
//
//    public static void register() {}
//}
