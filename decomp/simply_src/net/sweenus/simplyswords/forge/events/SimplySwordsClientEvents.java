package net.sweenus.simplyswords.forge.events;

import java.io.IOException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.Pack.Position;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.forgespi.locating.IModFile;
import net.minecraftforge.resource.PathPackResources;

@EventBusSubscriber(
   modid = "simplyswords",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class SimplySwordsClientEvents {
   @SubscribeEvent
   public static void simplySwords$addPackFinder(AddPackFindersEvent event) {
      if (event.getPackType() == PackType.CLIENT_RESOURCES) {
         simplySwords$registerResourcePack(event, new ResourceLocation("simplyswords", "classic"), false);
      }
   }

   private static void simplySwords$registerResourcePack(AddPackFindersEvent event, ResourceLocation identifier, boolean alwaysEnabled) {
      event.addRepositorySource(
         (profileAdder, factory) -> {
            IModFile file = ModList.get().getModFileById(identifier.m_135827_()).getFile();

            try {
               PathPackResources packResources = new PathPackResources(
                  identifier.toString(), file.findResource(new String[]{"resourcepacks/" + identifier.m_135815_()})
               );

               try {
                  profileAdder.accept(
                     new Pack(
                        identifier.toString(),
                        alwaysEnabled,
                        () -> packResources,
                        Component.m_130674_(identifier.m_135827_() + "/" + identifier.m_135815_()),
                        ((PackMetadataSection)packResources.m_5550_(PackMetadataSection.f_10366_)).m_10373_().m_6881_().m_130946_(" §7(Classic)"),
                        PackCompatibility.COMPATIBLE,
                        Position.TOP,
                        false,
                        PackSource.f_10528_,
                        false
                     )
                  );
               } catch (Throwable var9) {
                  try {
                     packResources.close();
                  } catch (Throwable var8) {
                     var9.addSuppressed(var8);
                  }

                  throw var9;
               }

               packResources.close();
            } catch (NullPointerException | IOException var10) {
               var10.printStackTrace();
            }
         }
      );
   }
}
