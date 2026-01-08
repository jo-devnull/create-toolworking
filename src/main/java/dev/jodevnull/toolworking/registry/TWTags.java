package dev.jodevnull.toolworking.registry;

import dev.jodevnull.toolworking.Toolworking;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class TWTags
{
//    public static class Items
//    {
//        public static final TagKey<Item>
//            FUEL = itemTag(Toolworking.MODID, "fuel"),
//            FIRE_STARTER = itemTag(Toolworking.MODID, "fire_starter");
//    }

    private static TagKey<Item> itemTag(String modid, String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(modid, name));
    }
}
