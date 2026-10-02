package shadows.apotheosis.adventure.affix.salvaging;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.client.GhostVertexBuilder;
import shadows.apotheosis.adventure.client.SimpleTexButton;
import shadows.placebo.screen.PlaceboContainerScreen;

public class SalvagingScreen extends PlaceboContainerScreen<SalvagingMenu> {
   public static final Component TITLE = Component.m_237115_("container.apotheosis.salvage");
   public static final ResourceLocation TEXTURE = new ResourceLocation("apotheosis", "textures/gui/salvage.png");
   protected List<SalvagingRecipe.OutputData> results = new ArrayList<>();
   protected SimpleTexButton salvageBtn;

   public SalvagingScreen(SalvagingMenu menu, Inventory inv, Component title) {
      super(menu, inv, TITLE);
      ((SalvagingMenu)this.f_97732_).addSlotListener((id, stack) -> this.computeResults());
      this.f_97728_--;
      this.f_97730_--;
      this.f_97731_++;
   }

   protected void m_7856_() {
      super.m_7856_();
      int left = this.getGuiLeft();
      int top = this.getGuiTop();
      this.salvageBtn = (SimpleTexButton)this.m_142416_(
         new SimpleTexButton(
               left + 105,
               top + 33,
               20,
               20,
               196,
               0,
               TEXTURE,
               256,
               256,
               btn -> this.f_96541_.f_91072_.m_105208_(((SalvagingMenu)this.f_97732_).f_38840_, 0),
               Component.m_237115_("button.apotheosis.salvage")
            )
            .setInactiveMessage(Component.m_237115_("button.apotheosis.no_salvage").m_130940_(ChatFormatting.RED))
      );
      this.computeResults();
   }

   public void computeResults() {
      if (this.salvageBtn != null) {
         ArrayList<SalvagingRecipe.OutputData> matches = new ArrayList<>();

         for (int i = 0; i < 15; i++) {
            Slot s = ((SalvagingMenu)this.f_97732_).m_38853_(i);
            ItemStack stack = s.m_7993_();
            SalvagingRecipe recipe = SalvagingMenu.findMatch(Minecraft.m_91087_().f_91073_, stack);
            if (recipe != null) {
               for (SalvagingRecipe.OutputData d : recipe.getOutputs()) {
                  int[] counts = SalvagingMenu.getSalvageCounts(d, stack);
                  matches.add(new SalvagingRecipe.OutputData(d.stack, counts[0], counts[1]));
               }
            }
         }

         List<SalvagingRecipe.OutputData> compressed = new ArrayList<>();

         for (SalvagingRecipe.OutputData data : matches) {
            if (data != null) {
               boolean success = false;

               for (SalvagingRecipe.OutputData existing : compressed) {
                  if (ItemStack.m_150942_(data.stack, existing.stack)) {
                     existing.min = existing.min + data.min;
                     existing.max = existing.max + data.max;
                     success = true;
                     break;
                  }
               }

               if (!success) {
                  compressed.add(data);
               }
            }
         }

         this.results = compressed;
         this.salvageBtn.f_93623_ = !this.results.isEmpty();
      }
   }

   public void m_6305_(PoseStack stack, int pMouseX, int pMouseY, float pPartialTick) {
      this.m_7333_(stack);
      super.m_6305_(stack, pMouseX, pMouseY, pPartialTick);
      int left = this.getGuiLeft();
      int top = this.getGuiTop();
      RenderSystem.m_157456_(0, InventoryMenu.f_39692_);
      RenderSystem.m_69478_();
      int maxDisplay = Math.min(6, this.results.size());
      IntSet skipSlots = new IntOpenHashSet();

      for (int i = 0; i < maxDisplay; i++) {
         ItemStack display = this.results.get(i).stack;
         int displaySlot = -1;

         for (int slot = 0; slot < 6; slot++) {
            if (!skipSlots.contains(slot)) {
               ItemStack outStack = ((Slot)((SalvagingMenu)this.f_97732_).f_38839_.get(15 + slot)).m_7993_();
               if (outStack.m_41619_()) {
                  displaySlot = slot;
                  skipSlots.add(slot);
                  break;
               }

               if (outStack.m_150930_(display.m_41720_())) {
                  break;
               }
            }
         }

         if (displaySlot != -1) {
            BakedModel model = this.f_96542_.m_174264_(display, null, null, 0);
            renderGuiItem(display, left + 134 + displaySlot % 2 * 18, top + 17 + displaySlot / 2 * 18, model, GhostVertexBuilder.GhostBufferSource::new);
         }
      }

      this.m_7025_(stack, pMouseX, pMouseY);
   }

   public static void renderGuiItem(ItemStack pStack, int pX, int pY, BakedModel pBakedModel, Function<MultiBufferSource, MultiBufferSource> wrapper) {
      Minecraft.m_91087_().f_90987_.m_118506_(InventoryMenu.f_39692_).m_117960_(false, false);
      RenderSystem.m_157456_(0, InventoryMenu.f_39692_);
      RenderSystem.m_69478_();
      RenderSystem.m_69408_(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      PoseStack posestack = RenderSystem.m_157191_();
      posestack.m_85836_();
      posestack.m_85837_((double)pX, (double)pY, (double)(100.0F + Minecraft.m_91087_().m_91291_().f_115093_));
      posestack.m_85837_(8.0, 8.0, 0.0);
      posestack.m_85841_(1.0F, -1.0F, 1.0F);
      posestack.m_85841_(16.0F, 16.0F, 16.0F);
      RenderSystem.m_157182_();
      PoseStack posestack1 = new PoseStack();
      boolean flag = !pBakedModel.m_7547_();
      if (flag) {
         Lighting.m_84930_();
      }

      BufferSource buffer = Minecraft.m_91087_().m_91269_().m_110104_();
      Minecraft.m_91087_()
         .m_91291_()
         .m_115143_(pStack, TransformType.GUI, false, posestack1, wrapper.apply(buffer), 15728880, OverlayTexture.f_118083_, pBakedModel);
      buffer.m_109911_();
      RenderSystem.m_69482_();
      if (flag) {
         Lighting.m_84931_();
      }

      posestack.m_85849_();
      RenderSystem.m_157182_();
   }

   protected void m_7286_(PoseStack pPoseStack, float pPartialTick, int pX, int pY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      this.m_93228_(pPoseStack, this.getGuiLeft(), this.getGuiTop(), 0, 0, this.f_97726_, this.f_97727_);
   }

   protected void m_7025_(PoseStack stack, int x, int y) {
      stack.m_85836_();
      stack.m_85837_(0.0, 0.0, -100.0);
      List<Component> tooltip = new ArrayList<>();
      tooltip.add(Component.m_237115_("text.apotheosis.salvage_results").m_130944_(new ChatFormatting[]{ChatFormatting.YELLOW, ChatFormatting.UNDERLINE}));

      for (SalvagingRecipe.OutputData data : this.results) {
         tooltip.add(Component.m_237110_("%s-%s %s", new Object[]{data.min, data.max, data.stack.m_41786_()}));
      }

      if (tooltip.size() > 1) {
         this.drawOnLeft(stack, tooltip, this.getGuiTop() + 29);
      }

      stack.m_85849_();
      super.m_7025_(stack, x, y);
   }

   public void drawOnLeft(PoseStack stack, List<Component> list, int y) {
      if (!list.isEmpty()) {
         int xPos = this.getGuiLeft() - 16 - list.stream().<Integer>map(this.f_96547_::m_92852_).max(Integer::compare).get();
         int maxWidth = 9999;
         if (xPos < 0) {
            maxWidth = this.getGuiLeft() - 6;
            xPos = -8;
         }

         List<FormattedText> split = new ArrayList<>();
         int lambdastupid = maxWidth;
         list.forEach(comp -> split.addAll(this.f_96547_.m_92865_().m_92414_(comp, lambdastupid, comp.m_7383_())));
         this.renderComponentTooltip(stack, split, xPos, y, this.f_96547_);
      }
   }

   protected void m_7027_(PoseStack stack, int mouseX, int mouseY) {
      this.f_96547_.m_92889_(stack, Component.m_237115_("text.apotheosis.results"), 133.0F, (float)this.f_97729_, 4210752);
      super.m_7027_(stack, mouseX, mouseY);
   }
}
