package com.hollingsworth.arsnouveau.common.mob_jar;

import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.api.nbt.AbstractData;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class FrogBehavior extends JarBehavior<Frog> {
   @Override
   public void tick(MobJarTile tile) {
      if (!tile.m_58904_().f_46443_) {
         FrogBehavior.ExtraData data = new FrogBehavior.ExtraData(tile.getExtraDataTag());
         if (data.isEating) {
            if (data.tickCounter >= 8) {
               if (tile.m_58904_().m_6815_(data.entityId) instanceof LivingEntity livingEntity) {
                  Frog frog = this.entityFromJar(tile);
                  livingEntity.m_5496_(SoundEvents.f_215692_, 2.0F, 1.0F);
                  if (livingEntity.m_6084_()) {
                     frog.m_7327_(livingEntity);
                     if (!livingEntity.m_6084_()) {
                        livingEntity.m_142687_(RemovalReason.KILLED);
                     }
                  }
               }

               data.isEating = false;
            }

            data.tickCounter++;
            this.writeData(tile, data);
         } else {
            if (tile.m_58904_().m_213780_().m_188503_(60) == 0) {
               Frog frog = this.entityFromJar(tile);
               List<LivingEntity> livingEntities = frog.f_19853_.m_6443_(LivingEntity.class, new AABB(tile.m_58899_()).m_82400_(2.0), Frog::m_218532_);
               if (livingEntities.isEmpty()) {
                  return;
               }

               LivingEntity entity = livingEntities.get(tile.m_58904_().m_213780_().m_188503_(livingEntities.size()));
               entity.f_19853_.m_6269_(null, entity, SoundEvents.f_215697_, SoundSource.NEUTRAL, 2.0F, 1.0F);
               entity.m_20256_(entity.m_20182_().m_82505_(frog.m_20182_()).m_82541_().m_82490_(0.75));
               entity.f_19812_ = true;
               data.position = entity.m_20182_();
               data.tickCounter = 0;
               data.isEating = true;
               data.entityId = entity.m_19879_();
               this.writeData(tile, data);
            }
         }
      }
   }

   public void writeData(MobJarTile tile, FrogBehavior.ExtraData data) {
      CompoundTag tag = new CompoundTag();
      data.writeToNBT(tag);
      tile.setExtraDataTag(tag);
   }

   public static class ExtraData extends AbstractData {
      public Vec3 position;
      public int tickCounter;
      public boolean isEating;
      public int entityId;

      public ExtraData(CompoundTag tag) {
         super(tag);
         this.position = NBTUtil.getVec(tag, "position");
         this.tickCounter = tag.m_128451_("tickCounter");
         this.isEating = tag.m_128471_("isEating");
         this.entityId = tag.m_128451_("entityId");
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         NBTUtil.storeVec(tag, "position", this.position);
         tag.m_128405_("tickCounter", this.tickCounter);
         tag.m_128379_("isEating", this.isEating);
         tag.m_128405_("entityId", this.entityId);
      }
   }
}
