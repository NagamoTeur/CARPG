package shadows.apotheosis.village.fletching;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class FletchingScreen extends AbstractContainerScreen<FletchingContainer> {
   public static final ResourceLocation TEXTURES = new ResourceLocation("apotheosis", "textures/gui/fletching_table.png");

   public FletchingScreen(FletchingContainer container, Inventory player, Component title) {
      super(container, player, title);
      this.f_97728_ = 47;
      this.f_97729_ = 6;
      this.f_97730_ = 8;
      this.f_97731_ = this.f_97727_ - 96 + 2;
   }

   public void m_6305_(PoseStack stack, int x, int y, float partialTicks) {
      this.m_7333_(stack);
      super.m_6305_(stack, x, y, partialTicks);
      this.m_7025_(stack, x, y);
   }

   protected void m_7286_(PoseStack stack, float partialTicks, int mouseX, int mouseY) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURES);
      int i = this.f_97735_;
      int j = (this.f_96544_ - this.f_97727_) / 2;
      this.m_93228_(stack, i, j, 0, 0, this.f_97726_, this.f_97727_);
   }
}
