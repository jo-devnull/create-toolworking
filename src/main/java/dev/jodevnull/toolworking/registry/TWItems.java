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
        CLAY_INGOT_MOLD = REGISTRATE.item("clay_ingot_mold", Item::new).register(),
        CLAY_AXE_MOLD = REGISTRATE.item("clay_axe_mold", Item::new).register(),
        CLAY_HOE_MOLD = REGISTRATE.item("clay_hoe_mold", Item::new).register(),
        CLAY_SHOVEL_MOLD = REGISTRATE.item("clay_shovel_mold", Item::new).register(),
        CLAY_PICKAXE_MOLD = REGISTRATE.item("clay_pickaxe_mold", Item::new).register(),
        CLAY_SWORD_MOLD = REGISTRATE.item("clay_sword_mold", Item::new).register(),
        CLAY_HAMMER_MOLD = REGISTRATE.item("clay_hammer_mold", Item::new).register(),
        CLAY_PAXEL_MOLD = REGISTRATE.item("clay_paxel_mold", Item::new).register();

    public static final ItemEntry<Item>
        WOODEN_AXE_PART = simpleItem("wooden_axe_part"),
        IRON_AXE_PART = simpleItem("iron_axe_part"),
        GOLD_AXE_PART = simpleItem("golden_axe_part"),
        COPPER_AXE_PART = simpleItem("copper_axe_part"),
        BRASS_AXE_PART = simpleItem("brass_axe_part"),
        BRONZE_AXE_PART = simpleItem("bronze_axe_part"),
        STEEL_AXE_PART = simpleItem("steel_axe_part");

    public static final ItemEntry<Item>
        WOODEN_HOE_PART = simpleItem("wooden_hoe_part"),
        IRON_HOE_PART = simpleItem("iron_hoe_part"),
        GOLD_HOE_PART = simpleItem("golden_hoe_part"),
        COPPER_HOE_PART = simpleItem("copper_hoe_part"),
        BRASS_HOE_PART = simpleItem("brass_hoe_part"),
        BRONZE_HOE_PART = simpleItem("bronze_hoe_part"),
        STEEL_HOE_PART = simpleItem("steel_hoe_part");

    public static final ItemEntry<Item>
        WOODEN_SHOVEL_PART = simpleItem("wooden_shovel_part"),
        IRON_SHOVEL_PART = simpleItem("iron_shovel_part"),
        GOLD_SHOVEL_PART = simpleItem("golden_shovel_part"),
        COPPER_SHOVEL_PART = simpleItem("copper_shovel_part"),
        BRASS_SHOVEL_PART = simpleItem("brass_shovel_part"),
        BRONZE_SHOVEL_PART = simpleItem("bronze_shovel_part"),
        STEEL_SHOVEL_PART = simpleItem("steel_shovel_part");

    public static final ItemEntry<Item>
        WOODEN_PICKAXE_PART = simpleItem("wooden_pickaxe_part"),
        IRON_PICKAXE_PART = simpleItem("iron_pickaxe_part"),
        GOLD_PICKAXE_PART = simpleItem("golden_pickaxe_part"),
        COPPER_PICKAXE_PART = simpleItem("copper_pickaxe_part"),
        BRASS_PICKAXE_PART = simpleItem("brass_pickaxe_part"),
        BRONZE_PICKAXE_PART = simpleItem("bronze_pickaxe_part"),
        STEEL_PICKAXE_PART = simpleItem("steel_pickaxe_part");

    public static final ItemEntry<Item>
        WOODEN_SWORD_PART = simpleItem("wooden_sword_part"),
        IRON_SWORD_PART = simpleItem("iron_sword_part"),
        GOLD_SWORD_PART = simpleItem("golden_sword_part"),
        COPPER_SWORD_PART = simpleItem("copper_sword_part"),
        BRASS_SWORD_PART = simpleItem("brass_sword_part"),
        BRONZE_SWORD_PART = simpleItem("bronze_sword_part"),
        STEEL_SWORD_PART = simpleItem("steel_sword_part");

    public static final ItemEntry<Item>
        IRON_HAMMER_PART = simpleItem("iron_hammer_part"),
        GOLD_HAMMER_PART = simpleItem("golden_hammer_part"),
        COPPER_HAMMER_PART = simpleItem("copper_hammer_part"),
        BRASS_HAMMER_PART = simpleItem("brass_hammer_part"),
        BRONZE_HAMMER_PART = simpleItem("bronze_hammer_part"),
        STEEL_HAMMER_PART = simpleItem("steel_hammer_part");

    public static final ItemEntry<Item>
        IRON_PAXEL_PART = simpleItem("iron_paxel_part"),
        GOLD_PAXEL_PART = simpleItem("golden_paxel_part"),
        COPPER_PAXEL_PART = simpleItem("copper_paxel_part"),
        BRASS_PAXEL_PART = simpleItem("brass_paxel_part"),
        BRONZE_PAXEL_PART = simpleItem("bronze_paxel_part"),
        STEEL_PAXEL_PART = simpleItem("steel_paxel_part");

    public static void register() {}

    public static ItemEntry<Item> simpleItem(String name)
    {
        return REGISTRATE.item(name, Item::new).register();
    }
}
