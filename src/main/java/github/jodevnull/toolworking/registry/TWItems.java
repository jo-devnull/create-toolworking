package github.jodevnull.toolworking.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import static github.jodevnull.toolworking.Toolworking.REGISTRATE;

public class TWItems
{
    public static final ItemEntry<Item>
        INGOT_CLAY_MOLD = REGISTRATE.item("ingot_clay_mold", Item::new).defaultModel().register();

    static {
        REGISTRATE.setCreativeTab(TWCreativeModeTabs.MAIN_CREATIVE_TAB);
    }

    public static void register() {}

    public static ItemEntry<Item> simpleItem(String name) {
        return REGISTRATE.item(name, Item::new)
            .defaultModel()
            .register();
    }
}
