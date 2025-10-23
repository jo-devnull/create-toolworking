//package dev.jodevnull.toolworking.ponder;
//
//import com.tterrag.registrate.util.entry.ItemProviderEntry;
//import com.tterrag.registrate.util.entry.RegistryEntry;
//import dev.jodevnull.toolworking.Toolworking;
//import net.createmod.ponder.api.registration.PonderPlugin;
//import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
//import net.minecraft.resources.ResourceLocation;
//import org.jetbrains.annotations.NotNull;
//
//public class TWPonderPlugin implements PonderPlugin
//{
//    @Override
//    public @NotNull String getModId() {
//        return Toolworking.MODID;
//    }
//
//    @Override
//    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
//        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
//    }
//}
