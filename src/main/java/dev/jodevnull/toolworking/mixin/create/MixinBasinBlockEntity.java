package dev.jodevnull.toolworking.mixin.create;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import dev.jodevnull.toolworking.content.blocks.BloomeryBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BasinBlockEntity.class, remap = false)
public class MixinBasinBlockEntity
{
    @Inject(at = @At("RETURN"), method = "getHeatLevelOf", cancellable = true)
    private static void tw$getHeatLevel(BlockState state, CallbackInfoReturnable<BlazeBurnerBlock.HeatLevel> cir) {
        if (state.hasProperty(BloomeryBlock.HEAT)) {
            BlazeBurnerBlock.HeatLevel heat = state.getValue(BloomeryBlock.HEAT);
            cir.setReturnValue(heat);
        }
    }
}
