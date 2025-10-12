package dev.jodevnull.toolworking.utils;

import net.mehvahdjukaar.moonlight.api.resources.textures.TextureImage;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;

public class ImageUtils
{
    private static final HashMap<ResourceLocation, BoundingBox> BB_CACHE = new HashMap<>();

    public static TextureImage applyMask(ResourceLocation maskId, TextureImage mask, TextureImage target, boolean inverted)
    {
        TextureImage output;

        if (inverted)
            output = target.makeCopy();
        else
            output = TextureImage.createNew(target.frameWidth(), target.frameHeight());

        int w = mask.frameWidth();
        int h = mask.frameHeight();
        BoundingBox bb = getBoundingBox(maskId, mask);

        int offsetX = (w-1)/2 - bb.centerX();
        int offsetY = (h-1)/2 - bb.centerY();

        mask.forEachPixel(context -> {
            int color = context.getValue();
            int alpha = (color >> 24) & 0xFF;

            int x = context.frameX() + offsetX;
            int y = context.frameY() + offsetY;

            if (alpha > 0 && (x >= 0 && x < w) && (y >= 0 && y < h)) {
                int pixel = target.getFramePixel(0, context.frameX(), context.frameY());
                int newColor = color == 0xFF0000FF ? pixel : color;
                output.setFramePixel(0, x, y, inverted ? 0 : newColor);
            }
        });

        return output;
    }

    public static BoundingBox getBoundingBox(ResourceLocation id, TextureImage image)
    {
        if (BB_CACHE.containsKey(id)) {
            return BB_CACHE.get(id);
        }

        final int[] min = { image.frameWidth(), image.frameHeight() };
        final int[] max = { 0, 0 };

        image.forEachPixel(context -> {
            int alpha = (context.getValue() >> 24) & 0xFF;

            if (alpha > 0) {
                min[0] = Math.min(min[0], context.frameX());
                min[1] = Math.min(min[1], context.frameY());
                max[0] = Math.max(max[0], context.frameX());
                max[1] = Math.max(max[1], context.frameY());
            }
        });

        BoundingBox result = new BoundingBox(min[0], min[1], max[0], max[1]);
        BB_CACHE.put(id, result);

        return result;
    }

    public record BoundingBox(int xMin, int yMin, int xMax, int yMax)
    {
        public int centerX() { return xMin + (width() - 1) / 2; }
        public int centerY() { return yMin + (height() - 1) / 2; }

        public int width() { return xMax - xMin + 1; }
        public int height() { return yMax - yMin + 1; }
    }
}
