package dev.jodevnull.toolworking.content.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.kinetics.belt.behaviour.DirectBeltInputBehaviour;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.item.ItemHelper;
import dev.jodevnull.toolworking.Toolworking;
import dev.jodevnull.toolworking.registry.TWTags;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BloomeryBlockEntity extends SmartBlockEntity
{
    public static final int MAX_HEAT = 5000;
    public static final int FUEL_THRESHOLD = 500;
    public static final int MAX_EMPOWERED_TIME = 120;

    protected int remainingBurnTime;
    protected int remainingEmpoweredTime;

    BloomeryItemHandler itemHandler;
    public ItemStackHandler inputInv;
    public LazyOptional<IItemHandler> capability;

    public BloomeryBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        inputInv = new ItemStackHandler(BloomeryItemHandler.MAX_SLOTS);
        itemHandler = new BloomeryItemHandler();
        capability = LazyOptional.of(() -> itemHandler);
        remainingBurnTime = 0;
        remainingEmpoweredTime = 0;
    }

    @Override
    public void tick() {
        super.tick();

        tickFuel();

        if (!getLitFromBlock())
            return;

        if (level == null)
            return;

        if (level.isClientSide) {
            if (!isVirtual())
                spawnParticles(getHeatLevelFromBlock(), 1);

            return;
        }


        if (remainingBurnTime > 0) {
            if (remainingEmpoweredTime > 0 && !getEmpoweredFromBlock())
                setEmpowered(true);

            if (remainingEmpoweredTime == 0 && getEmpoweredFromBlock())
                setEmpowered(false);

            remainingEmpoweredTime--;

            if (remainingEmpoweredTime < 0)
                remainingEmpoweredTime = 0;
        }

        if (remainingBurnTime == 0 && getLitFromBlock()) {
            level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(BloomeryBlock.LIT, false));

            notifyUpdate();
        }

        updateBlockState();

        Toolworking.logger.info("remainingBurnTime: {}", remainingBurnTime);
    }

    public void tickFuel() {
        if (level == null)
            return;

        if (remainingBurnTime > 0 && !getBlockState().getValue(BloomeryBlock.FUELED)) {
            level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(BloomeryBlock.FUELED, true));

            notifyUpdate();
        }

        if (remainingBurnTime == 0) {
            level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(BloomeryBlock.FUELED, false)
                .setValue(BloomeryBlock.LIT, false));

            notifyUpdate();
        }

        remainingBurnTime--;
        if (remainingBurnTime < 0)
            remainingBurnTime = 0;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        behaviours.add(new DirectBeltInputBehaviour(this));
    }

    @Override
    public void invalidate() {
        super.invalidate();
        capability.invalidate();
    }

    @Override
    public void destroy() {
        super.destroy();
        ItemHelper.dropContents(level, worldPosition, inputInv);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.put("InputInventory", inputInv.serializeNBT());
        compound.putInt("BurnTimeRemaining", remainingBurnTime);
        compound.putInt("EmpoweredTimeRemaining", remainingEmpoweredTime);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        inputInv.deserializeNBT(compound.getCompound("InputInventory"));
        remainingBurnTime = compound.getInt("BurnTimeRemaining");
        remainingEmpoweredTime = compound.getInt("EmpoweredTimeRemaining");
        super.read(compound, clientPacket);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        if (isItemHandlerCap(cap)) return capability.cast();
        return super.getCapability(cap, side);
    }

    public boolean isFuelValid(ItemStack stack) {
        int burnTime = ForgeHooks.getBurnTime(stack, null);
        boolean tagged = stack.is(TWTags.Items.FUEL);
        return burnTime > 0 && tagged && inputInv.isItemValid(0, stack);
    }

    public boolean isValidBlockAbove() {
        if (isVirtual())
            return false;

        BlockState blockState = level.getBlockState(worldPosition.above());
        return AllBlocks.BASIN.has(blockState) || blockState.getBlock() instanceof FluidTankBlock;
    }

    protected void playSound() {
        level.playSound(null, worldPosition, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
            .125f + level.random.nextFloat() * .125f, .75f - level.random.nextFloat() * .25f);
    }

    protected void spawnParticles(HeatLevel heatLevel, double burstMult) {
        if (level == null) return;
        if (heatLevel == HeatLevel.NONE) return;

        RandomSource r = level.getRandom();

        Vec3 c = VecHelper.getCenterOf(worldPosition);
        Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .125f).multiply(1, 0, 1));

        if (r.nextInt(4) != 0) return;

        boolean empty = level.getBlockState(worldPosition.above()).getCollisionShape(level, worldPosition.above()).isEmpty();

        if (empty || r.nextInt(8) == 0) level.addParticle(ParticleTypes.SMOKE, v.x, v.y, v.z, 0, 0, 0);

        double yMotion = empty ? .0325f : r.nextDouble() * .0125f;
        Vec3 v2 = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .5f).multiply(1, .25f, 1).normalize()
            .scale((empty ? .25f : .5) + r.nextDouble() * .125f)).add(0, .5, 0);

        double yExtra = getEmpoweredFromBlock() ? .02f : 0;
        if (!heatLevel.equals(HeatLevel.NONE)) {
            level.addParticle(ParticleTypes.FLAME, v2.x, v2.y, v2.z, 0, yMotion + yExtra, 0);
            if (getEmpoweredFromBlock()) level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, v2.x, v2.y, v2.z, 0, yMotion + yExtra, 0);
        }
    }

    public void spawnParticleBurst() {
        Vec3 c = VecHelper.getCenterOf(worldPosition);
        RandomSource r = level.random;
        for (int i = 0; i < 20; i++) {
            Vec3 offset = VecHelper.offsetRandomly(Vec3.ZERO, r, .5f).multiply(1, .25f, 1).normalize();
            Vec3 v = c.add(offset.scale(.5 + r.nextDouble() * .125f)).add(0, .125, 0);
            Vec3 m = offset.scale(1 / 129f);
            level.addParticle(ParticleTypes.ASH , v.x, v.y, v.z, m.x, m.y, m.z);
        }
    }

    public void setEmpowered(boolean value) {
        if (getEmpoweredFromBlock() == value)
            return;

        if (level != null) {
            remainingEmpoweredTime = value ? MAX_EMPOWERED_TIME : 0;
            level.setBlockAndUpdate(worldPosition, getBlockState().setValue(BloomeryBlock.EMPOWERED, value));
            notifyUpdate();
        }
    }

    public boolean isEmpty() {
        for (int i = 0; i < BloomeryItemHandler.MAX_SLOTS; i++) {
            if (!inputInv.getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public int currentSlot(ItemStack stack) {
        for (int i = 0; i < BloomeryItemHandler.MAX_SLOTS; i++) {
            ItemStack slot = inputInv.getStackInSlot(i);

            if (slot.is(stack.getItem()) || slot.isEmpty())
                return i;
        }

        return 0;
    }

    public ItemStack addIngredient(ItemStack stack, boolean simulate) {
        return inputInv.insertItem(currentSlot(stack), stack, simulate);
    }

    public ItemStack[] removeIngredients(boolean simulate) {
        ItemStack[] stacks = new ItemStack[BloomeryItemHandler.MAX_SLOTS];

        for (int slot = 0; slot < BloomeryItemHandler.MAX_SLOTS; slot++) {
            stacks[slot] = inputInv.extractItem(slot, 9000, simulate);
            inputInv.setStackInSlot(slot, ItemStack.EMPTY);
        }

        return stacks;
    }

    public void addFuel(ItemStack fuel) {
        // needs to be lit before adding fuel
        if (!isFuelValid(fuel) || !getLitFromBlock())
            return;

        int newBurnTime = remainingBurnTime;

        if (newBurnTime <= MAX_HEAT) {
            fuel.shrink(1);

            newBurnTime += ForgeHooks.getBurnTime(fuel, null);

            if (newBurnTime > MAX_HEAT)
                newBurnTime = MAX_HEAT;

            if (level != null && level.isClientSide) {
                spawnParticleBurst();
            }

            remainingBurnTime = newBurnTime;
        }
    }

    public void onLit() {
        remainingBurnTime += 200;
    }

    public HeatLevel getHeatLevelFromBlock() {
        return BloomeryBlock.getHeatLevelOf(getBlockState());
    }

    public boolean getLitFromBlock() {
        return BloomeryBlock.getLitOf(getBlockState());
    }

    public boolean getEmpoweredFromBlock() {
        return BloomeryBlock.getEmpoweredOf(getBlockState());
    }

    public void updateBlockState() {
        setBlockHeat(getHeatLevel());
    }

    protected void setBlockHeat(HeatLevel heat) {
        HeatLevel inBlockState = getHeatLevelFromBlock();
        if (level == null || inBlockState == heat)
            return;

        if (remainingBurnTime == 0)
            level.setBlockAndUpdate(worldPosition, getBlockState()
                .setValue(BloomeryBlock.HEAT, heat)
                .setValue(BloomeryBlock.LIT, false)
                .setValue(BloomeryBlock.FUELED, false));

        else level.setBlockAndUpdate(worldPosition, getBlockState()
            .setValue(BloomeryBlock.HEAT, heat));

        notifyUpdate();
    }

    protected HeatLevel getHeatLevel() {
        HeatLevel level = HeatLevel.NONE;

        if (!getLitFromBlock())
            return level;
        else if (getEmpoweredFromBlock())
            return HeatLevel.KINDLED;

        return level;
    }

    public class BloomeryItemHandler implements IItemHandler
    {
        public static final int MAX_SLOTS = 2;
        public static final int MAIN_SLOT = 0;

        public BloomeryItemHandler() {}

        @Override
        public int getSlots() {
            return MAX_SLOTS;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return inputInv.getStackInSlot(slot);
        }

        @Override
        public int getSlotLimit(int slot) {
            return inputInv.getSlotLimit(slot);
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return inputInv.isItemValid(slot, stack);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot >= MAX_SLOTS)
                return stack;

            if (
                (!inputInv.getStackInSlot(0).isEmpty() && !stack.is(inputInv.getStackInSlot(0).getItem())) &&
                (!inputInv.getStackInSlot(1).isEmpty() && !stack.is(inputInv.getStackInSlot(1).getItem()))
            )
                return stack;

            if (!isItemValid(slot, stack))
                return stack;

            ItemStack remainder = inputInv.insertItem(slot, stack, simulate);

            if (!simulate && remainder != stack)
                notifyUpdate();

            return remainder;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            ItemStack held = inputInv.getStackInSlot(slot);

            if (held == null)
                return ItemStack.EMPTY;

            ItemStack stack = held.copy();
            ItemStack extracted = stack.split(amount);

            if (!simulate) {
                inputInv.setStackInSlot(slot, stack);
                notifyUpdate();
            }

            return extracted;
        }
    }
}
