package dev.jodevnull.toolworking;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import dev.jodevnull.toolworking.registry.TWCreativeTabs;
import dev.jodevnull.toolworking.registry.TWFluids;
import dev.jodevnull.toolworking.registry.TWItems;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Toolworking.MODID)
public class Toolworking
{
    public static final String MODID = "toolworking";
    public static final Logger LOGGER = LogManager.getLogger("Toolworking");
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    public static ResourceLocation res(String path) { return ResourceLocation.fromNamespaceAndPath(MODID, path); }

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
            .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public Toolworking(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        REGISTRATE.registerEventListeners(modEventBus);

        TWCreativeTabs.register(modEventBus);
        TWItems.register();
        TWFluids.register();
        MinecraftForge.EVENT_BUS.register(this);
    }
}
