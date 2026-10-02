package dev.latvian.mods.kubejs.client;

import dev.architectury.event.events.client.ClientGuiEvent;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.client.ClientTextureStitchEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTooltipEvent;
import dev.architectury.hooks.client.screen.ScreenAccess;
import dev.architectury.hooks.fluid.FluidBucketHooks;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.KubeJSPaths;
import dev.latvian.mods.kubejs.bindings.event.ClientEvents;
import dev.latvian.mods.kubejs.bindings.event.ItemEvents;
import dev.latvian.mods.kubejs.client.painter.Painter;
import dev.latvian.mods.kubejs.core.ImageButtonKJS;
import dev.latvian.mods.kubejs.item.ItemTooltipEventJS;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.util.ConsoleJS;
import dev.latvian.mods.kubejs.util.Tags;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class KubeJSClientEventHandler {
   private static final ResourceLocation RECIPE_BUTTON_TEXTURE = new ResourceLocation("textures/gui/recipe_button.png");
   public static Map<Item, List<ItemTooltipEventJS.StaticTooltipHandler>> staticItemTooltips = null;
   private final Map<ResourceLocation, TagInstance> tempTagNames = new LinkedHashMap<>();

   public void init() {
      ClientGuiEvent.DEBUG_TEXT_LEFT.register(this::debugInfoLeft);
      ClientGuiEvent.DEBUG_TEXT_RIGHT.register(this::debugInfoRight);
      ClientTooltipEvent.ITEM.register(this::itemTooltip);
      ClientTickEvent.CLIENT_POST.register(this::clientTick);
      ClientPlayerEvent.CLIENT_PLAYER_JOIN.register(this::loggedIn);
      ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(this::loggedOut);
      ClientPlayerEvent.CLIENT_PLAYER_RESPAWN.register(this::respawn);
      ClientGuiEvent.RENDER_HUD.register(Painter.INSTANCE::inGameScreenDraw);
      ClientGuiEvent.RENDER_POST.register(Painter.INSTANCE::guiScreenDraw);
      ClientGuiEvent.INIT_POST.register(this::guiPostInit);
      ClientTextureStitchEvent.POST.register(this::postAtlasStitch);
   }

   private void debugInfoLeft(List<String> lines) {
      if (Minecraft.m_91087_().f_91074_ != null && ClientEvents.DEBUG_LEFT.hasListeners()) {
         ClientEvents.DEBUG_LEFT.post(ScriptType.CLIENT, new DebugInfoEventJS(lines));
      }
   }

   private void debugInfoRight(List<String> lines) {
      if (Minecraft.m_91087_().f_91074_ != null && ClientEvents.DEBUG_RIGHT.hasListeners()) {
         ClientEvents.DEBUG_RIGHT.post(ScriptType.CLIENT, new DebugInfoEventJS(lines));
      }
   }

   private void itemTooltip(ItemStack stack, List<Component> lines, TooltipFlag flag) {
      if (!stack.m_41619_()) {
         boolean advanced = flag.m_7050_();
         if (advanced && ClientProperties.get().getShowTagNames() && Screen.m_96638_()) {
            Consumer<TagKey<?>> addToTempTags = tag -> this.tempTagNames.computeIfAbsent(tag.f_203868_(), TagInstance::new).registries.add(tag.f_203867_());
            Tags.byItemStack(stack).forEach(addToTempTags);
            if (stack.m_41720_() instanceof BlockItem item) {
               Tags.byBlock(item.m_40614_()).forEach(addToTempTags);
            }

            if (stack.m_41720_() instanceof BucketItem bucket) {
               Fluid fluid = FluidBucketHooks.getFluid(bucket);
               if (fluid != Fluids.f_76191_) {
                  Tags.byFluid(fluid).forEach(addToTempTags);
               }
            }

            if (stack.m_41720_() instanceof SpawnEggItem item) {
               Tags.byEntityType(item.m_43228_(stack.m_41783_())).forEach(addToTempTags);
            }

            for (TagInstance instance : this.tempTagNames.values()) {
               lines.add(instance.toText());
            }

            this.tempTagNames.clear();
         }

         if (staticItemTooltips == null) {
            staticItemTooltips = new HashMap<>();
            ItemEvents.TOOLTIP.post(ScriptType.CLIENT, new ItemTooltipEventJS(staticItemTooltips));
         }

         try {
            for (ItemTooltipEventJS.StaticTooltipHandler handler : staticItemTooltips.getOrDefault(Items.f_41852_, List.of())) {
               handler.tooltip(stack, advanced, lines);
            }
         } catch (Exception var9) {
            ConsoleJS.CLIENT.error("Error while gathering tooltip for " + stack, var9);
         }

         try {
            for (ItemTooltipEventJS.StaticTooltipHandler handler : staticItemTooltips.getOrDefault(stack.m_41720_(), List.of())) {
               handler.tooltip(stack, advanced, lines);
            }
         } catch (Exception var8) {
            ConsoleJS.CLIENT.error("Error while gathering tooltip for " + stack, var8);
         }
      }
   }

   private void clientTick(Minecraft minecraft) {
      if (Minecraft.m_91087_().f_91074_ != null && ClientEvents.TICK.hasListeners()) {
         ClientEvents.TICK.post(ScriptType.CLIENT, new ClientEventJS());
      }
   }

   private void loggedIn(LocalPlayer player) {
      ClientEvents.LOGGED_IN.post(ScriptType.CLIENT, new ClientEventJS());
   }

   private void loggedOut(LocalPlayer player) {
      ClientEvents.LOGGED_OUT.post(ScriptType.CLIENT, new ClientEventJS());
      Painter.INSTANCE.clear();
   }

   private void respawn(LocalPlayer oldPlayer, LocalPlayer newPlayer) {
   }

   @Nullable
   public static Screen setScreen(Screen screen) {
      return (Screen)(screen instanceof TitleScreen && !ScriptType.STARTUP.errors.isEmpty() && CommonProperties.get().startupErrorGUI
         ? new KubeJSErrorScreen(ScriptType.STARTUP)
         : screen);
   }

   private void guiPostInit(Screen screen, ScreenAccess access) {
      if (ClientProperties.get().getDisableRecipeBook() && screen instanceof RecipeUpdateListener) {
         Iterator<? extends GuiEventListener> iterator = screen.m_6702_().iterator();

         while (iterator.hasNext()) {
            GuiEventListener listener = iterator.next();
            if (listener instanceof AbstractWidget
               && listener instanceof ImageButtonKJS buttonKJS
               && RECIPE_BUTTON_TEXTURE.equals(buttonKJS.kjs$getButtonTexture())) {
               access.getRenderables().remove(listener);
               access.getNarratables().remove(listener);
               iterator.remove();
               return;
            }
         }
      }
   }

   private void postAtlasStitch(TextureAtlas atlas) {
      if (ClientProperties.get().getExportAtlases()) {
         GL11.glBindTexture(3553, atlas.m_117963_());
         int w = GL11.glGetTexLevelParameteri(3553, 0, 4096);
         int h = GL11.glGetTexLevelParameteri(3553, 0, 4097);
         if (w > 0 && h > 0) {
            BufferedImage image = new BufferedImage(w, h, 2);
            int[] pixels = new int[w * h];
            IntBuffer result = BufferUtils.createIntBuffer(w * h);
            GL11.glGetTexImage(3553, 0, 32993, 33639, result);
            result.get(pixels);
            image.setRGB(0, 0, w, h, pixels, 0, w);
            Path path = KubeJSPaths.EXPORT.resolve(atlas.m_118330_().m_135827_() + "/" + atlas.m_118330_().m_135815_());
            if (!Files.exists(path.getParent())) {
               try {
                  Files.createDirectories(path.getParent());
               } catch (Exception var13) {
                  var13.printStackTrace();
                  return;
               }
            }

            if (!Files.exists(path)) {
               try {
                  Files.createFile(path);
               } catch (Exception var12) {
                  var12.printStackTrace();
                  return;
               }
            }

            try (OutputStream stream = Files.newOutputStream(path)) {
               ImageIO.write(image, "PNG", stream);
            } catch (Exception var15) {
               var15.printStackTrace();
            }
         }
      }
   }
}
