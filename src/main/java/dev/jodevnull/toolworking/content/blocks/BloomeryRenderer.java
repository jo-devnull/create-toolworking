package dev.jodevnull.toolworking.content.blocks;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class BloomeryRenderer extends SmartBlockEntityRenderer<BloomeryBlockEntity>
{
    public BloomeryRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(BloomeryBlockEntity bloomery, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(bloomery, partialTicks, ms, buffer, light, overlay);

        IItemHandler inv = bloomery.capability.orElse(new ItemStackHandler());
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        RandomSource r = RandomSource.create(bloomery.getBlockPos().hashCode());

        if (bloomery.isEmpty())
            return;

        ms.pushPose();
        ms.translate(.5f, .275f, .5f);
        ms.scale(.5f, .5f, .5f);

        for (int i = 0; i < inv.getSlots(); i++) {
            ms.pushPose();

            ItemStack stack = inv.getStackInSlot(i);

            if (!stack.isEmpty()) {
                Vec3 vec = VecHelper.offsetRandomly(Vec3.ZERO, r, 1 / 8f);
                ms.translate(vec.x, Math.abs(i * vec.y/3), vec.z);

                TransformStack.of(ms)
                    .rotateYDegrees((float) (35f + (vec.x + vec.z) / (2f / 8f) * 10f))
                    .rotateXDegrees((float) (65f + (vec.y + vec.z) / (2f / 8f) * 10f));

                itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffer, bloomery.getLevel(), 0);
            }

            ms.popPose();
        }

        ms.popPose();
    }
}
