package dev.jodevnull.toolworking.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.jodevnull.toolworking.Toolworking;
import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.IOException;

public record MoldItem(String name, ResourceLocation mask, ResourceLocation material)
{
    public TextureImage getMask(ResourceManager manager) throws IOException {
        return TextureImage.open(manager, mask);
    }

    public TextureImage getMaterial(ResourceManager manager) throws IOException {
        return TextureImage.open(manager, material);
    }

    public ResourceLocation getLocation() {
        return Toolworking.res("item/" + name);
    }

    public JsonElement getModel() {
        return JsonParser.parseString("""
        {
            "parent": "minecraft:item/generated",
            "textures": {
                "layer0": "%s"
            }
        }
        """.formatted(getLocation().toString()));
    }
}
