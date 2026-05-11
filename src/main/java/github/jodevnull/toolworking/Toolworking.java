package github.jodevnull.toolworking;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import github.jodevnull.toolworking.registry.TWCreativeModeTabs;
import github.jodevnull.toolworking.registry.TWFluids;
import github.jodevnull.toolworking.registry.TWItems;
import net.createmod.catnip.lang.FontHelper;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(Toolworking.MODID)
public class Toolworking
{
    public static final String MODID = "toolworking";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MODID);

    static {
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
            .andThen(TooltipModifier.mapNull(KineticStats.create(item))))
            .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    }

    public Toolworking(IEventBus modEventBus, ModContainer modContainer)
    {
        REGISTRATE.registerEventListeners(modEventBus);
        TWCreativeModeTabs.register(modEventBus);
        TWItems.register();
        TWFluids.register();
    }

    public static ResourceLocation res(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
