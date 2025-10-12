package dev.jodevnull.toolworking.asset;

import dev.jodevnull.toolworking.Toolworking;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynClientResourcesGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.DynamicTexturePack;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceGenTask;
import net.minecraft.server.packs.repository.Pack;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public class DynamicResources
{
    // call during mod init
    public static void init() {
        ClientAssetsGenerator generator = new ClientAssetsGenerator();
        generator.register();
    }

    // Class responsible to generate assets into your dynamic pack
    public static class ClientAssetsGenerator extends DynClientResourcesGenerator
    {

        protected ClientAssetsGenerator() {
            super(new DynamicTexturePack(Toolworking.res("generated_pack"), Pack.Position.TOP, false, false));
        }

        @Override
        public void regenerateDynamicAssets(Consumer<ResourceGenTask> executor)
        {
            executor.accept((TextureGenerator::genAssets));
        }

        @Override
        public Logger getLogger() {
            return Toolworking.logger;
        }

    }
}
