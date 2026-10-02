package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.socket.gem.GemInstance;

public class SocketTooltipRenderer implements ClientTooltipComponent {
   public static final ResourceLocation SOCKET = new ResourceLocation("apotheosis", "textures/gui/socket.png");
   private final SocketTooltipRenderer.SocketComponent comp;
   private final int spacing = 9 + 2;

   public SocketTooltipRenderer(SocketTooltipRenderer.SocketComponent comp) {
      this.comp = comp;
   }

   public int m_142103_() {
      return this.spacing * this.comp.gems.size();
   }

   public int m_142069_(Font font) {
      int maxWidth = 0;

      for (ItemStack gem : this.comp.gems) {
         maxWidth = Math.max(maxWidth, font.m_92852_(getSocketDesc(this.comp.socketed, gem)) + 12);
      }

      return maxWidth;
   }

   public void m_183452_(Font pFont, int x, int y, PoseStack stack, ItemRenderer itemRenderer, int pBlitOffset) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, SOCKET);

      for (int i = 0; i < this.comp.gems.size(); i++) {
         GuiComponent.m_93143_(stack, x, y + this.spacing * i, pBlitOffset, 0.0F, 0.0F, 9, 9, 9, 9);
      }

      for (ItemStack gem : this.comp.gems()) {
         if (!gem.m_41619_()) {
            PoseStack mvStack = RenderSystem.m_157191_();
            mvStack.m_85836_();
            mvStack.m_85841_(0.5F, 0.5F, 1.0F);
            itemRenderer.m_115218_(gem, 2 * x + 1, 2 * y + 1);
            mvStack.m_85849_();
            RenderSystem.m_157182_();
         }

         y += this.spacing;
      }
   }

   public void m_142440_(Font pFont, int pX, int pY, Matrix4f pMatrix4f, BufferSource pBufferSource) {
      for (int i = 0; i < this.comp.gems.size(); i++) {
         pFont.m_92841_(
            getSocketDesc(this.comp.socketed, this.comp.gems.get(i)),
            (float)(pX + 12),
            (float)(pY + 1 + this.spacing * i),
            11189196,
            true,
            pMatrix4f,
            pBufferSource,
            false,
            0,
            15728880
         );
      }
   }

   public static Component getSocketDesc(ItemStack socketed, ItemStack gemStack) {
      GemInstance inst = GemInstance.socketed(socketed, gemStack);
      return (Component)(!inst.isValid() ? Component.m_237115_("socket.apotheosis.empty") : inst.getSocketBonusTooltip());
   }

   public static record SocketComponent(ItemStack socketed, List<ItemStack> gems) implements TooltipComponent {
   }
}
