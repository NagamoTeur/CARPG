package com.hollingsworth.arsnouveau.client.util;

import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.Vec3;

public class ColorPos {
   public final ParticleColor color;
   public final Vec3 pos;

   public ColorPos(Vec3 pos, ParticleColor color) {
      this.pos = pos;
      this.color = color;
   }

   public ColorPos(Vec3 pos) {
      this(pos, ParticleColor.defaultParticleColor());
   }

   public static ColorPos centered(BlockPos pos, ParticleColor color) {
      return new ColorPos(new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5), color);
   }

   public static ColorPos centered(BlockPos pos) {
      return new ColorPos(new Vec3((double)pos.m_123341_() + 0.5, (double)pos.m_123342_() + 0.5, (double)pos.m_123343_() + 0.5));
   }

   public static ColorPos centeredAbove(BlockPos pos, ParticleColor color) {
      return new ColorPos(new Vec3((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5), color);
   }

   public static ColorPos centeredAbove(BlockPos pos) {
      return new ColorPos(new Vec3((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5));
   }

   public CompoundTag toTag() {
      CompoundTag tag = new CompoundTag();
      tag.m_128347_("x", this.pos.f_82479_);
      tag.m_128347_("y", this.pos.f_82480_);
      tag.m_128347_("z", this.pos.f_82481_);
      tag.m_128365_("color", this.color.serialize());
      return tag;
   }

   public static ColorPos fromTag(CompoundTag tag) {
      return new ColorPos(new Vec3(tag.m_128459_("x"), tag.m_128459_("y"), tag.m_128459_("z")), ParticleColorRegistry.from(tag.m_128469_("color")));
   }
}
