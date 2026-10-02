package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class MageBlockTile extends ModdedTile implements ITickable, IDispellable {
   int age;
   public boolean isPermanent;
   public double lengthModifier;
   public ParticleColor color = ParticleColor.defaultParticleColor();

   public MageBlockTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.MAGE_BLOCK_TILE, pos, state);
   }

   @Override
   public void tick() {
      if (!this.isPermanent) {
         if (!this.f_58857_.f_46443_) {
            this.age++;
            if ((double)this.age > 300.0 + 100.0 * this.lengthModifier) {
               this.f_58857_.m_46961_(this.m_58899_(), false);
               this.f_58857_.m_46747_(this.m_58899_());
            }
         }
      }
   }

   public void m_142466_(CompoundTag compound) {
      super.m_142466_(compound);
      this.age = compound.m_128451_("age");
      this.color = ParticleColorRegistry.from(compound.m_128469_("lightColor"));
      this.isPermanent = compound.m_128471_("permanent");
      this.lengthModifier = compound.m_128459_("modifier");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      tag.m_128365_("age", IntTag.m_128679_(this.age));
      tag.m_128365_("lightColor", this.color.serialize());
      tag.m_128379_("permanent", this.isPermanent);
      tag.m_128347_("modifier", this.lengthModifier);
   }

   public AABB getRenderBoundingBox() {
      return INFINITE_EXTENT_AABB;
   }

   @Override
   public boolean onDispel(@NotNull LivingEntity caster) {
      this.f_58857_.m_46961_(this.m_58899_(), false);
      this.f_58857_.m_46747_(this.m_58899_());
      return true;
   }
}
