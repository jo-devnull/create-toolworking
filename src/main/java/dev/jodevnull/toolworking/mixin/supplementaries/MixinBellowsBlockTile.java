package dev.jodevnull.toolworking.mixin.supplementaries;

import net.mehvahdjukaar.supplementaries.common.block.tiles.BellowsBlockTile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zeh.createlowheated.AllBlocks;
import zeh.createlowheated.content.processing.basicburner.BasicBurnerBlockEntity;

@Mixin(value = BellowsBlockTile.class, remap = false)
public class MixinBellowsBlockTile
{
    @Inject(at = @At("HEAD"), method = "tickFurnaces(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/Level;)V")
    public void toolworking$powerBurnerWithBellow(BlockPos pos, Level level, CallbackInfo ci)
    {
        BlockState state = level.getBlockState(pos);

        if (state.is(AllBlocks.BASIC_BURNER.get())) {
            BlockEntity entity = level.getBlockEntity(pos);

            if (entity instanceof BasicBurnerBlockEntity burner) {
                burner.setEmpowered(true);
            }
        }
    }
}