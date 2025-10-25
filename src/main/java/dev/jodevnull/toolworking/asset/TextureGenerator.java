package dev.jodevnull.toolworking.asset;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import dev.jodevnull.toolworking.Toolworking;
import dev.jodevnull.toolworking.utils.ImageUtils;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.util.List;

public class TextureGenerator
{
    private static final ResourceLocation
        CLAY_MOLD = ResourceLocation.fromNamespaceAndPath("createmetallurgy", "block/refractory_mortar");

    private static final ResourceLocation
        INGOT_MASK   = ResourceLocation.fromNamespaceAndPath("minecraft", "item/iron_ingot"),
        AXE_MASK     = Toolworking.res("item/axe_mask"),
        HOE_MASK     = Toolworking.res("item/hoe_mask"),
        SHOVEL_MASK  = Toolworking.res("item/shovel_mask"),
        PICKAXE_MASK = Toolworking.res("item/pickaxe_mask"),
        SWORD_MASK   = Toolworking.res("item/sword_mask"),
        HAMMER_MASK  = Toolworking.res("item/hammer_mask"),
        PAXEL_MASK   = Toolworking.res("item/paxel_mask");

    private static final List<ResourceLocation> ALL_AXES = List.of(
        ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_axe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "iron_axe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "golden_axe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_axe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_axe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_axe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_axe")
    );

    private static final List<ResourceLocation> ALL_HOES = List.of(
        ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_hoe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "iron_hoe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "golden_hoe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_hoe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_hoe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_hoe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_hoe")
    );

    private static final List<ResourceLocation> ALL_SHOVELS = List.of(
        ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_shovel"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "iron_shovel"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "golden_shovel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_shovel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_shovel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_shovel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_shovel")
    );

    private static final List<ResourceLocation> ALL_PICKAXES = List.of(
        ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "iron_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "golden_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_pickaxe"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_pickaxe")
    );

    private static final List<ResourceLocation> ALL_SWORDS = List.of(
        ResourceLocation.fromNamespaceAndPath("minecraft", "wooden_sword"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "iron_sword"),
        ResourceLocation.fromNamespaceAndPath("minecraft", "golden_sword"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_sword"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_sword"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_sword"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_sword")
    );

    private static final List<ResourceLocation> ALL_HAMMERS = List.of(
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "iron_hammer"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "gold_hammer"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_hammer"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_hammer"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_hammer"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_hammer")
    );

    private static final List<ResourceLocation> ALL_PAXELS = List.of(
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "iron_paxel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "gold_paxel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "copper_paxel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "brass_paxel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "bronze_paxel"),
        ResourceLocation.fromNamespaceAndPath("create_ironworks", "steel_paxel")
    );

    public static void genAssets(ResourceManager manager, ResourceSink sink)
    {
        genAssetGroup(manager, sink, AXE_MASK, ALL_AXES);
        genAssetGroup(manager, sink, HOE_MASK, ALL_HOES);
        genAssetGroup(manager, sink, SHOVEL_MASK, ALL_SHOVELS);
        genAssetGroup(manager, sink, PICKAXE_MASK, ALL_PICKAXES);
        genAssetGroup(manager, sink, SWORD_MASK, ALL_SWORDS);
        genAssetGroup(manager, sink, HAMMER_MASK, ALL_HAMMERS);
        genAssetGroup(manager, sink, PAXEL_MASK, ALL_PAXELS);

        genMoldAssets(manager, sink, "clay_ingot_mold", INGOT_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_axe_mold", AXE_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_hoe_mold", HOE_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_shovel_mold", SHOVEL_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_pickaxe_mold", PICKAXE_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_sword_mold", SWORD_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_hammer_mold", HAMMER_MASK, CLAY_MOLD);
        genMoldAssets(manager, sink, "clay_paxel_mold", PAXEL_MASK, CLAY_MOLD);
    }

    public static void genMoldAssets(ResourceManager manager, ResourceSink sink, String name, ResourceLocation maskLoc, ResourceLocation material)
    {
        ResourceLocation location = Toolworking.res("item/" + name);
        JsonElement itemModel = JsonParser.parseString("""
        {
            "parent": "minecraft:item/generated",
            "textures": {
                "layer0": "%s"
            }
        }
        """.formatted(location.toString()));

        try (TextureImage mask = TextureImage.open(manager, maskLoc); TextureImage target = TextureImage.open(manager, material))
        {
            TextureImage texture = ImageUtils.applyMask(maskLoc, mask, target, true);
            sink.addTexture(location, texture);
            sink.addItemModel(Toolworking.res(name), itemModel);
        }
        catch (Exception e)
        {
            Toolworking.logger.error(e);
        }
    }

    public static void genAssetGroup(ResourceManager manager, ResourceSink sink, ResourceLocation maskLoc, List<ResourceLocation> group)
    {
        try (TextureImage mask = TextureImage.open(manager, maskLoc))
        {
            for (ResourceLocation targetId : group)
            {
                genAssetsFor(manager, sink, mask, targetId);
            }
        }
        catch (Exception e)
        {
            Toolworking.logger.error(e);
        }
    }

    public static void genAssetsFor(ResourceManager manager, ResourceSink sink, TextureImage mask, ResourceLocation targetTool)
    {
        String path = targetTool.getPath();
        ResourceLocation maskLoc = Toolworking.res("item/" + path.split("_")[1] + "_mask");
        ResourceLocation targetLoc = targetTool.withPath("item/" + path);

        path = path.replace("gold_", "golden_");
        ResourceLocation outputLoc = Toolworking.res("item/" + path + "_part");
        JsonElement itemModel = JsonParser.parseString("""
        {
            "parent": "minecraft:item/generated",
            "textures": {
                "layer0": "%s"
            }
        }
        """.formatted(outputLoc.toString()));

        try (TextureImage target = TextureImage.open(manager, targetLoc))
        {
            TextureImage texture = ImageUtils.applyMask(maskLoc, mask, target, false);
            sink.addTexture(outputLoc, texture);
            sink.addItemModel(Toolworking.res(path + "_part"), itemModel);
        }
        catch(Exception e)
        {
            Toolworking.logger.error("Exception while generating texture for: {}", targetTool);
            Toolworking.logger.error(e);
        }
    }
}
