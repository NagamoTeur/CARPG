package com.github.L_Ender.cataclysm.client.model.block;

import net.minecraft.client.model.Model;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class Cataclysm_Skull_Model_Base extends Model {
   public Cataclysm_Skull_Model_Base() {
      super(RenderType::m_110473_);
   }

   public abstract void setupAnim(float var1, float var2, float var3);
}
