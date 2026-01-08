package dev.jodevnull.toolworking.mixin.notreepunching;

import com.alcatrazescapee.notreepunching.common.items.FireStarterItem;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import zeh.createlowheated.AllBlocks;

@Mixin(FireStarterItem.class)
public class MixinFireStarterItem
{
    @ModifyExpressionValue(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/CampfireBlock;canLight(Lnet/minecraft/world/level/block/state/BlockState;)Z"))
    private boolean toolworking$lightBurner(boolean original, @Local(name = "stateAt") BlockState stateAt) {
        return original || stateAt.is(AllBlocks.BASIC_BURNER.get());
    }
}
