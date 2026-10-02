package daripher.skilltree.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.item.quiver.QuiverItem;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@EventBusSubscriber(
   modid = "skilltree",
   bus = Bus.MOD,
   value = {Dist.CLIENT}
)
public enum QuiverHud implements IGuiOverlay {
   INSTANCE;

   private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/widgets.png");

   @SubscribeEvent
   public static void register(RegisterGuiOverlaysEvent event) {
      event.registerBelowAll("quiver_contents", INSTANCE);
   }

   public void render(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight) {
      LocalPlayer player = Objects.requireNonNull(Minecraft.m_91087_().f_91074_);
      Optional<SlotResult> quiverCurio = CuriosApi.getCuriosHelper().findFirstCurio(player, ItemHelper::isQuiver);
      quiverCurio.<ItemStack>map(SlotResult::stack)
         .ifPresent(quiver -> this.renderArrows(gui, poseStack, partialTick, screenWidth, screenHeight, quiver, player));
   }

   private void renderArrows(ForgeGui gui, PoseStack poseStack, float partialTick, int screenWidth, int screenHeight, ItemStack quiver, LocalPlayer player) {
      if (QuiverItem.containsArrows(quiver)) {
         RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
         RenderSystem.m_157427_(GameRenderer::m_172817_);
         RenderSystem.m_157456_(0, WIDGETS_LOCATION);
         HumanoidArm offhand = player.m_5737_().m_20828_();
         int center = screenWidth / 2;
         ItemStack arrows = QuiverItem.getArrows(quiver);
         int slotY = screenHeight - 16 - 3;
         boolean hasOffhandItem = !player.m_21206_().m_41619_();
         int arrowsCount = QuiverItem.getArrowsCount(quiver);
         if (offhand == HumanoidArm.LEFT) {
            int slotX = center - 91 - 29 - (hasOffhandItem ? 29 : 0);
            gui.m_93228_(poseStack, slotX, screenHeight - 23, 24, 22, 29, 24);
         } else {
            int slotX = center + 91 + (hasOffhandItem ? 29 : 0);
            gui.m_93228_(poseStack, slotX, screenHeight - 23, 53, 22, 29, 24);
         }

         RenderSystem.m_69478_();
         RenderSystem.m_69453_();
         if (offhand == HumanoidArm.LEFT) {
            int slotX = center - 91 - 29 - (hasOffhandItem ? 29 : 0);
            this.renderSlot(slotX + 3, slotY, partialTick, player, arrows, 1, arrowsCount);
         } else {
            int slotX = center + 91 + (hasOffhandItem ? 29 : 0);
            this.renderSlot(slotX + 10, slotY, partialTick, player, arrows, 1, arrowsCount);
         }
      }
   }

   private void renderSlot(int x, int y, float partialTick, Player player, ItemStack stack, int slot, int count) {
      if (!stack.m_41619_()) {
         PoseStack posestack = RenderSystem.m_157191_();
         float f = (float)stack.m_41612_() - partialTick;
         if (f > 0.0F) {
            float f1 = 1.0F + f / 5.0F;
            posestack.m_85836_();
            posestack.m_85837_((double)(x + 8), (double)(y + 12), 0.0);
            posestack.m_85841_(1.0F / f1, (f1 + 1.0F) / 2.0F, 1.0F);
            posestack.m_85837_((double)(-(x + 8)), (double)(-(y + 12)), 0.0);
            RenderSystem.m_157182_();
         }

         Minecraft minecraft = Minecraft.m_91087_();
         ItemRenderer itemRenderer = minecraft.m_91291_();
         itemRenderer.m_174229_(player, stack, x, y, slot);
         RenderSystem.m_157427_(GameRenderer::m_172811_);
         if (f > 0.0F) {
            posestack.m_85849_();
            RenderSystem.m_157182_();
         }

         itemRenderer.m_115174_(minecraft.f_91062_, stack, x, y, count + "");
      }
   }
}
