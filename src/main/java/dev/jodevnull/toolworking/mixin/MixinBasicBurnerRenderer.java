package dev.jodevnull.toolworking.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.engine_room.flywheel.lib.transform.TransformStack;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zeh.createlowheated.content.processing.basicburner.BasicBurnerBlockEntity;
import zeh.createlowheated.content.processing.basicburner.BasicBurnerRenderer;

@Mixin(value = BasicBurnerRenderer.class, remap = false)
public class MixinBasicBurnerRenderer
{
    @Inject(at = @At(value = "HEAD"), method = "renderSafe(Lzeh/createlowheated/content/processing/basicburner/BasicBurnerBlockEntity;FLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;II)V", cancellable = true)
    public void toolworking$tweakRenderer(BasicBurnerBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay, CallbackInfo ci)
    {
        IItemHandler inv = be.capability.orElse(new ItemStackHandler());
        ItemStack stack = inv.getStackInSlot(0);
        ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
        RandomSource r = RandomSource.create((long)be.getBlockPos().hashCode());
        ms.pushPose();
        ms.translate(0.5F, 0.1F, 0.5F);
        ms.scale(0.5F, 0.5F, 0.5F);

        for(int i = 0; i <= stack.getCount() / 8; ++i) {
            ms.pushPose();
            Vec3 vec = VecHelper.offsetRandomly(Vec3.ZERO, r, 0.125F);
            ms.translate(vec.x, Math.abs((double)i * vec.y / (double)3.0F), vec.z);
            TransformStack.of(ms).rotateYDegrees((float)((double)35.0F + (vec.x + vec.z) / (double)0.25F * (double)10.0F)).rotateXDegrees((float)((double)65.0F + (vec.y + vec.z) / (double)0.25F * (double)10.0F));
            itemRenderer.renderStatic(stack, ItemDisplayContext.FIXED, light, overlay, ms, buffer, be.getLevel(), 0);
            ms.popPose();
        }

        ms.popPose();
        ci.cancel();
    }
}
