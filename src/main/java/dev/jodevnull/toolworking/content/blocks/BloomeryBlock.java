package dev.jodevnull.toolworking.content.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.block.IBE;
import dev.jodevnull.toolworking.registry.TWBlockEntityTypes;
import dev.jodevnull.toolworking.registry.TWTags;
import net.createmod.catnip.data.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

public class BloomeryBlock extends HorizontalDirectionalBlock implements IBE<BloomeryBlockEntity>, IWrenchable
{
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final BooleanProperty FUELED = BooleanProperty.create("fueled");
    public static final BooleanProperty EMPOWERED = BooleanProperty.create("empowered");
    public static final EnumProperty<HeatLevel> HEAT = EnumProperty.create("heat", HeatLevel.class);

    public BloomeryBlock(Properties props) {
        super(props);
        registerDefaultState(defaultBlockState()
            .setValue(LIT, false)
            .setValue(FUELED, false)
            .setValue(EMPOWERED, false)
            .setValue(HEAT, HeatLevel.NONE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HEAT, LIT, FUELED, EMPOWERED, FACING);
        super.createBlockStateDefinition(builder);
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
        if (world.isClientSide)
            return;

        BlockEntity blockEntity = world.getBlockEntity(pos.above());

        if (blockEntity instanceof BasinBlockEntity basin)
            basin.notifyChangeOfContents();
    }

    @Override
    public Class<BloomeryBlockEntity> getBlockEntityClass() {
        return BloomeryBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends BloomeryBlockEntity> getBlockEntityType() {
        return TWBlockEntityTypes.BLOOMERY.get();
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return IBE.super.newBlockEntity(pos, state);
    }

    @Override
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult)
    {
        ItemStack itemInHand = player.getItemInHand(hand);
        boolean wasEmptyHanded = itemInHand.isEmpty() && hand == InteractionHand.MAIN_HAND;
        boolean shouldntPlaceItem = AllBlocks.MECHANICAL_ARM.isIn(itemInHand);

        if (!state.hasBlockEntity())
            return InteractionResult.PASS;

        BlockEntity be = world.getBlockEntity(pos);

        if (!(be instanceof BloomeryBlockEntity bloomery))
            return InteractionResult.PASS;

        if (!state.getValue(LIT) && itemInHand.is(TWTags.Items.FIRE_STARTER)) {
            world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F,
                world.random.nextFloat() * 0.4F + 0.8F);

            if (world.isClientSide)
                return InteractionResult.SUCCESS;

            itemInHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand));
            world.setBlockAndUpdate(pos, state.setValue(LIT, true));
            bloomery.onLit();
            bloomery.notifyUpdate();

            return InteractionResult.SUCCESS;
        }

        if (wasEmptyHanded && !bloomery.isEmpty()) {
            for (ItemStack stack : bloomery.removeIngredients(false)) {
                if (!stack.isEmpty())
                    player.getInventory().placeItemBackInInventory(stack);
            }

            world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, .2f, 1f + world.random.nextFloat());
        }

        if (!wasEmptyHanded && !shouldntPlaceItem) {
            if (bloomery.isFuelValid(itemInHand)) {
                bloomery.addFuel(itemInHand);
            } else {
                ItemStack remainder = bloomery.addIngredient(itemInHand, false);

                if (remainder.getCount() == itemInHand.getCount())
                    return InteractionResult.PASS;

                player.setItemInHand(hand, remainder);
                AllSoundEvents.DEPOT_SLIDE.playOnServer(world, pos);
            }
        }

        bloomery.notifyUpdate();
        return InteractionResult.SUCCESS;
    }

    @Override
    public @org.jetbrains.annotations.Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context)
            .setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 16, 16);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return getShape(state, reader, pos, context);
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return Math.max(0, state.getValue(HEAT).ordinal() - 1);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (random.nextInt(10) != 0)
            return;

        if (!state.getValue(HEAT).equals(HeatLevel.NONE))
            return;

        world.playLocalSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F,
            (float) pos.getZ() + 0.5F, SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
            0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
    }

    public static HeatLevel getHeatLevelOf(BlockState blockState) {
        return blockState.hasProperty(HEAT) ? blockState.getValue(HEAT)
            : HeatLevel.NONE;
    }

    public static boolean getLitOf(BlockState blockState) { return blockState.getValue(LIT); }

    public static boolean getEmpoweredOf(BlockState blockState) { return blockState.getValue(EMPOWERED); }

    public static int getLight(BlockState state) {
        return state.getValue(HEAT) == HeatLevel.NONE ? 0 : 15;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        IBE.onRemove(state, level, pos, newState);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
        super.updateEntityAfterFallOn(worldIn, entityIn);

        if (!(entityIn instanceof ItemEntity itemEntity))
            return;
        if (!entityIn.isAlive())
            return;
        if (entityIn.level().isClientSide)
            return;

        BloomeryBlockEntity bloomery = null;

        for (BlockPos pos : Iterate.hereAndBelow(entityIn.blockPosition()))
            if (bloomery == null) bloomery = getBlockEntity(worldIn, pos);

        if (bloomery == null) return;

        LazyOptional<IItemHandler> capability = bloomery.getCapability(ForgeCapabilities.ITEM_HANDLER);

        if (!capability.isPresent())
            return;

        ItemStack remainder = capability.orElse(new ItemStackHandler())
            .insertItem(0, itemEntity.getItem(), false);

        if (remainder.isEmpty())
            itemEntity.discard();

        if (remainder.getCount() < itemEntity.getItem().getCount())
            itemEntity.setItem(remainder);
    }
}
