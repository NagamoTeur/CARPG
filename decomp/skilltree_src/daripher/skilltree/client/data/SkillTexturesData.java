package daripher.skilltree.client.data;

import java.util.Set;
import java.util.TreeSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   modid = "skilltree",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public class SkillTexturesData implements ResourceManagerReloadListener {
   public static final Set<ResourceLocation> TOOLTIP_BACKGROUNDS = new TreeSet<>();
   public static final Set<ResourceLocation> BORDERS = new TreeSet<>();
   public static final Set<ResourceLocation> ICONS = new TreeSet<>();

   @SubscribeEvent
   public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener(new SkillTexturesData());
   }

   public void m_6213_(@NotNull ResourceManager resourceManager) {
      this.reloadTextures(TOOLTIP_BACKGROUNDS, "tooltip", resourceManager);
      this.reloadTextures(BORDERS, "icons/background", resourceManager);
      this.reloadTextures(ICONS, "icons", resourceManager);
      ICONS.removeAll(BORDERS);
   }

   private void reloadTextures(Set<ResourceLocation> storage, String folder, ResourceManager resourceManager) {
      storage.clear();
      storage.addAll(resourceManager.m_214159_("textures/" + folder, l -> l.m_135815_().endsWith(".png")).keySet());
   }
}
