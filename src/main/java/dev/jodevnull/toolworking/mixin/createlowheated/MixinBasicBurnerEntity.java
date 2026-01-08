package dev.jodevnull.toolworking.mixin.createlowheated;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import zeh.createlowheated.content.processing.basicburner.BasicBurnerBlockEntity;

@Mixin(value = BasicBurnerBlockEntity.class, remap = false)
public abstract class MixinBasicBurnerEntity extends SmartBlockEntity
{
    public MixinBasicBurnerEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Shadow public abstract void setEmpowered(boolean value);
    @Unique static private final int tw$maxEmpoweredTicks = 40;
    @Unique private int tw$empoweredTicks = 0;

    @Inject(at = @At("HEAD"), method = "tick")
    public void tw$tick(CallbackInfo ci)
    {
        if (level != null && !level.isClientSide) {
            tw$empoweredTicks--;

            if (tw$empoweredTicks < 0) {
                setEmpowered(false);
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "setEmpowered")
    public void tw$setEmpowered(boolean value, CallbackInfo ci)
    {
        tw$empoweredTicks = value ? tw$maxEmpoweredTicks : 0;
    }
}