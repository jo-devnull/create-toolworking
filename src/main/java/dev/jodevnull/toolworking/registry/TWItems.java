package dev.jodevnull.toolworking.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static dev.jodevnull.toolworking.Toolworking.REGISTRATE;

public class TWItems
{
    static {
        REGISTRATE.setCreativeTab(TWCreativeTabs.MAIN_CREATIVE_TAB);
    }

    public static final ItemEntry<Item>
        INGOT_CLAY_MOLD = REGISTRATE.item("ingot_clay_mold", Item::new).defaultModel().register();

//    public static final ItemEntry<Item>
//        HEATED_COPPER_INGOT = simpleItem("heated_copper_ingot"),
//        HEATED_BRONZE_INGOT = simpleItem("heated_bronze_ingot"),
//        HEATED_BRASS_INGOT  = simpleItem("heated_brass_ingot"),
//        HEATED_GOLD_INGOT   = simpleItem("heated_gold_ingot"),
//        HEATED_IRON_INGOT   = simpleItem("heated_iron_ingot"),
//        HEATED_STEEL_INGOT  = simpleItem("heated_steel_ingot");

    public static void register() {}

    public static ItemEntry<Item> simpleItem(String name) {
        return REGISTRATE.item(name, Item::new)
            .defaultModel()
            .register();
    }
}
