package dev.jodevnull.toolworking.mixin;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import zeh.createlowheated.content.processing.basicburner.BasicBurnerBlock;

import static net.minecraft.world.level.block.HorizontalDirectionalBlock.FACING;

@Mixin(value = BasicBurnerBlock.class)
public class MixinBasicBurnerBlock
{
    @Inject(at = @At("RETURN"), method = "getStateForPlacement", cancellable = true)
    public void tw$addFacing(BlockPlaceContext context, CallbackInfoReturnable<BlockState> cir)
    {
        cir.setReturnValue(cir.getReturnValue().setValue(FACING, context.getHorizontalDirection().getOpposite()));
    }
}
