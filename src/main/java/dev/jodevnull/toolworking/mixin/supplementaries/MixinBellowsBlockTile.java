package dev.jodevnull.toolworking.mixin.supplementaries;

import dev.jodevnull.toolworking.content.blocks.BloomeryBlockEntity;
import dev.jodevnull.toolworking.registry.TWBlocks;
import net.mehvahdjukaar.supplementaries.common.block.tiles.BellowsBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BellowsBlockTile.class, remap = false)
public class MixinBellowsBlockTile
{
    @Inject(at = @At("TAIL"), method = "tickFurnaces(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)V")
    public void tw$tickFurnaces(BlockPos pos, Level level, CallbackInfo ci) {
        BlockState state = level.getBlockState(pos);

        if (state.is(TWBlocks.BLOOMERY_BLOCK.get())) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof BloomeryBlockEntity bloomery) {
                bloomery.setEmpowered(true);
            }
        }
    }
}
