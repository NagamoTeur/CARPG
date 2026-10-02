package com.github.L_Ender.cataclysm.client.render.blockentity;

import com.github.L_Ender.cataclysm.blockentities.Door_Of_Seal_BlockEntity;
import com.github.L_Ender.cataclysm.blocks.Door_of_Seal_Block;
import com.github.L_Ender.cataclysm.client.model.block.Door_Of_Seal_Model;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider.Context;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class Door_Of_Seal_Renderer implements BlockEntityRenderer<Door_Of_Seal_BlockEntity> {
   private static final ResourceLocation TEXTURE = new ResourceLocation("cataclysm", "textures/block/door_of_seal.png");
   private static final Door_Of_Seal_Model MODEL = new Door_Of_Seal_Model();

   public Door_Of_Seal_Renderer(Context rendererDispatcherIn) {
   }

   public boolean shouldRenderOffScreen(Door_Of_Seal_BlockEntity p_112138_) {
      return true;
   }

   public int m_142163_() {
      return 256;
   }

   public boolean shouldRender(Door_Of_Seal_BlockEntity entity, Vec3 p_173532_) {
      return Vec3.m_82512_(entity.m_58899_()).m_82542_(1.0, 0.0, 1.0).m_82509_(p_173532_.m_82542_(1.0, 0.0, 1.0), (double)this.m_142163_());
   }

   public void render(Door_Of_Seal_BlockEntity entity, float delta, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int overlay) {
      poseStack.m_85836_();
      Direction dir = (Direction)entity.m_58900_().m_61143_(Door_of_Seal_Block.FACING);
      if (dir == Direction.NORTH) {
         poseStack.m_85837_(0.5, 1.501F, 0.5);
      } else if (dir == Direction.EAST) {
         poseStack.m_85837_(0.5, 1.501F, 0.5);
      } else if (dir == Direction.SOUTH) {
         poseStack.m_85837_(0.5, 1.501F, 0.5);
      } else if (dir == Direction.WEST) {
         poseStack.m_85837_(0.5, 1.501F, 0.5);
      }

      poseStack.m_85845_(dir.m_122424_().m_122406_());
      poseStack.m_85845_(Vector3f.f_122223_.m_122240_(90.0F));
      MODEL.animate(entity, delta);
      MODEL.m_7695_(poseStack, buffer.m_6299_(RenderType.m_110458_(TEXTURE)), packedLight, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
      poseStack.m_85849_();
   }
}
