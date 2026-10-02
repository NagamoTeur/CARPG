package shadows.apotheosis.core.attributeslib.client;

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
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.Nullable;
import shadows.apotheosis.Apotheosis;

public class AttributeModifierComponent implements ClientTooltipComponent {
   public static final ResourceLocation TEXTURE = Apotheosis.loc("textures/gui/attribute_component.png");
   @Nullable
   private final ModifierSource<?> source;
   private final List<FormattedCharSequence> text;

   public AttributeModifierComponent(@Nullable ModifierSource<?> source, FormattedText text, Font font, int maxWidth) {
      this.source = source;
      this.text = font.m_92923_(text, maxWidth);
   }

   public int m_142103_() {
      return this.text.size() * 10;
   }

   public int m_142069_(Font font) {
      return this.text.stream().<Integer>map(font::m_92724_).map(w -> w + 12).max(Integer::compareTo).get();
   }

   public void m_183452_(Font font, int x, int y, PoseStack stack, ItemRenderer itemRenderer, int pBlitOffset) {
      RenderSystem.m_157427_(GameRenderer::m_172817_);
      RenderSystem.m_157429_(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.m_157456_(0, TEXTURE);
      GuiComponent.m_93143_(stack, x, y, pBlitOffset, this.source == null ? 9.0F : 0.0F, 0.0F, 9, 9, 18, 9);
      if (this.source != null) {
         this.source.render(font, x, y, stack, itemRenderer, pBlitOffset);
      }
   }

   public void m_142440_(Font font, int pX, int pY, Matrix4f pMatrix4f, BufferSource pBufferSource) {
      FormattedCharSequence line = this.text.get(0);
      font.m_92733_(line, (float)(pX + 12), (float)pY, -1, true, pMatrix4f, pBufferSource, false, 0, 15728880);

      for (int i = 1; i < this.text.size(); i++) {
         line = this.text.get(i);
         font.m_92733_(line, (float)pX, (float)(pY + i * (9 + 1)), -1, true, pMatrix4f, pBufferSource, false, 0, 15728880);
      }
   }
}
