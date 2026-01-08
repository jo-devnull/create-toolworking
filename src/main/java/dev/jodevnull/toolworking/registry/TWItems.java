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

    public static final ItemEntry<Item>
        HEATED_COPPER_INGOT = REGISTRATE.item("heated_copper_ingot", Item::new).defaultModel().register(),
        HEATED_BRONZE_INGOT = REGISTRATE.item("heated_bronze_ingot", Item::new).defaultModel().register(),
        HEATED_BRASS_INGOT  = REGISTRATE.item("heated_brass_ingot",  Item::new).defaultModel().register(),
        HEATED_GOLD_INGOT   = REGISTRATE.item("heated_gold_ingot",   Item::new).defaultModel().register(),
        HEATED_IRON_INGOT   = REGISTRATE.item("heated_iron_ingot",   Item::new).defaultModel().register(),
        HEATED_STEEL_INGOT  = REGISTRATE.item("heated_steel_ingot",  Item::new).defaultModel().register();

    public static void register() {}
}
