package com.cerbon.bosses_of_mass_destruction.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.function.Consumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class LerpedPosRenderer<T extends Entity> implements IRenderer<T> {
   private final Consumer<Vec3> callback;

   public LerpedPosRenderer(Consumer<Vec3> callback) {
      this.callback = callback;
   }

   @Override
   public void render(T entity, float yaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light) {
      double x = Mth.m_14139_((double)partialTicks, entity.f_19854_, entity.m_20185_());
      double y = Mth.m_14139_((double)partialTicks, entity.f_19855_, entity.m_20186_());
      double z = Mth.m_14139_((double)partialTicks, entity.f_19856_, entity.m_20189_());
      this.callback.accept(new Vec3(x, y, z));
   }
}
