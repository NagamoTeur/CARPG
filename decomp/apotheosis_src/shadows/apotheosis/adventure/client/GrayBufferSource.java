package shadows.apotheosis.adventure.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.inventory.InventoryMenu;

public class GrayBufferSource implements MultiBufferSource {
   private final MultiBufferSource wrapped;

   public GrayBufferSource(MultiBufferSource wrapped) {
      this.wrapped = wrapped;
   }

   public VertexConsumer m_6299_(RenderType type) {
      return type.m_110508_() == DefaultVertexFormat.f_85812_
         ? this.wrapped.m_6299_(AdventureModuleClient.gray(InventoryMenu.f_39692_))
         : this.wrapped.m_6299_(type);
   }
}
