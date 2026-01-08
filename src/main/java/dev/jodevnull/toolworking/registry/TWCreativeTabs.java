package dev.jodevnull.toolworking.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.jodevnull.toolworking.Toolworking;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

import static net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB;

public class TWCreativeTabs
{
    private static final DeferredRegister<CreativeModeTab> REGISTER =
        DeferredRegister.create(CREATIVE_MODE_TAB, Toolworking.MODID);

    public static final RegistryObject<CreativeModeTab> MAIN_CREATIVE_TAB = REGISTER.register("main_group",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup." + Toolworking.MODID + ".main_group"))
            .icon(TWItems.INGOT_CLAY_MOLD::asStack)
            .displayItems(new RegistrateDisplayItemsGenerator())
            .build()
        );

    public static void register(IEventBus modEventBus) {
        REGISTER.register(modEventBus);
    }

    public static class RegistrateDisplayItemsGenerator implements CreativeModeTab.DisplayItemsGenerator
    {
        private List<Item> collectBlocks(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();
            for (RegistryEntry<Block> entry : Toolworking.REGISTRATE.getAll(Registries.BLOCK)) {
                Item item = entry.get()
                    .asItem();
                if (item == Items.AIR)
                    continue;
                if (!exclusionPredicate.test(item))
                    items.add(item);
            }
            items = new ReferenceArrayList<>(new ReferenceLinkedOpenHashSet<>(items));
            return items;
        }

        private List<Item> collectItems(Predicate<Item> exclusionPredicate) {
            List<Item> items = new ReferenceArrayList<>();

            for (RegistryEntry<Item> entry : Toolworking.REGISTRATE.getAll(Registries.ITEM)) {
                Item item = entry.get();
                if (item instanceof BlockItem)
                    continue;
                if (!exclusionPredicate.test(item))
                    items.add(item);
            }
            return items;
        }

        private static void outputAll(CreativeModeTab.Output output, List<Item> items, Function<Item, CreativeModeTab.TabVisibility> visibilityFunc) {
            for (Item item : items) {
                output.accept(item, visibilityFunc.apply(item));
            }
        }

        private static Function<Item, CreativeModeTab.TabVisibility> makeVisibilityFunc() {
            Map<Item, CreativeModeTab.TabVisibility> visibilities = new Reference2ObjectOpenHashMap<>();

            return item -> {
                CreativeModeTab.TabVisibility visibility = visibilities.get(item);

                if (visibility != null)
                    return visibility;

                return CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS;
            };
        }

        private static Predicate<Item> makeExclusionPredicate() {
            Set<Item> exclusions = new ReferenceOpenHashSet<>();
            return exclusions::contains;
        }

        @Override
        public void accept(CreativeModeTab.ItemDisplayParameters params, CreativeModeTab.Output output) {
            Function<Item, CreativeModeTab.TabVisibility> visibilityFunc = makeVisibilityFunc();
            Predicate<Item> exclusionPredicate = makeExclusionPredicate();

            List<Item> items = new LinkedList<>();
            items.addAll(collectBlocks(exclusionPredicate));
            items.addAll(collectItems(exclusionPredicate));

            outputAll(output, items, visibilityFunc);
        }
    }
}
