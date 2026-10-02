package com.aizistral.enigmaticlegacy.client.models;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import java.util.Collections;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;

public class UnseenArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
   private static ModelPart copyOf(HumanoidModel<?> base) {
      Builder<String, ModelPart> mapBuilder = ImmutableMap.builder();
      mapBuilder.put("head", base.f_102808_);
      mapBuilder.put("hat", base.f_102809_);
      mapBuilder.put("body", base.f_102810_);
      mapBuilder.put("right_arm", base.f_102811_);
      mapBuilder.put("left_arm", base.f_102812_);
      mapBuilder.put("right_leg", base.f_102813_);
      mapBuilder.put("left_leg", base.f_102814_);
      return new ModelPart(Collections.emptyList(), mapBuilder.build());
   }

   public UnseenArmorModel(HumanoidModel<T> base) {
      super(copyOf(base), base::m_103119_);
   }

   public void m_7695_(
      PoseStack p_102034_, VertexConsumer p_102035_, int p_102036_, int p_102037_, float p_102038_, float p_102039_, float p_102040_, float p_102041_
   ) {
   }
}
