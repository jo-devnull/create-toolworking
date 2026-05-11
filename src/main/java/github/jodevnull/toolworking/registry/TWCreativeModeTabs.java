package github.jodevnull.toolworking.registry;

import com.simibubi.create.foundation.data.CreateRegistrate;
import github.jodevnull.toolworking.Toolworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTab.ItemDisplayParameters;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;
import java.util.function.Predicate;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TWCreativeModeTabs
{
    private static final DeferredRegister<CreativeModeTab> REGISTER =
        DeferredRegister.create(CREATIVE_MODE_TAB, Toolworking.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_CREATIVE_TAB =
        REGISTER.register(Toolworking.MODID,
            () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup.toolworking.main_group"))
                .icon(TWItems.INGOT_CLAY_MOLD::asStack)
                .displayItems(TWCreativeModeTabs::displayItemsGenerator)
                .build()
        );

    private static void displayItemsGenerator(ItemDisplayParameters params, CreativeModeTab.Output output) {
        Toolworking.REGISTRATE.getAll(Registries.ITEM).forEach(entry -> {
            if (!CreateRegistrate.isInCreativeTab(entry, TWCreativeModeTabs.MAIN_CREATIVE_TAB))
                return;

            output.accept(entry.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        });

        output.accept(TWItems.INGOT_CLAY_MOLD);
    }

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }
}
