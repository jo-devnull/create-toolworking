package dev.jodevnull.toolworking.registry;

import com.tterrag.registrate.util.entry.FluidEntry;
import dev.jodevnull.toolworking.Toolworking;
import fr.lucreeper74.createmetallurgy.content.fluids.MoltenFluidSource;
import fr.lucreeper74.createmetallurgy.content.fluids.MoltenFluidType;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class TWFluids
{
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOLTEN_GLASS = makeMoltenGlass();

    public static FluidEntry<ForgeFlowingFluid.Flowing> makeMoltenGlass() {
        final var idStill = Toolworking.res("fluid/glass/still");
        final var idFlowing = Toolworking.res("fluid/glass/flowing");
        final var temperature = 1400;
        final var viscosity = 2500;
        final var density = 1400;
        final var lightLevel = 12;

        return Toolworking.REGISTRATE
            .fluid("molten_glass", idStill, idFlowing, MoltenFluidType::new)
            .properties(b -> b
                .temperature(temperature)
                .viscosity(viscosity)
                .density(density)
                .lightLevel(lightLevel)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .canHydrate(false).canSwim(false).canDrown(false))
            .fluidProperties(p -> p
                .levelDecreasePerBlock(2)
                .tickRate(25)
                .slopeFindDistance(3)
                .explosionResistance(100f))
            .source(MoltenFluidSource::new)
            .bucket()
            .build()
            .register();
    }

    public static void register() {}
}
