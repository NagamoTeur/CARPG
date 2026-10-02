package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehavior;
import com.hollingsworth.arsnouveau.api.mob_jar.JarBehaviorRegistry;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.MobJar;
import com.hollingsworth.arsnouveau.common.lib.EntityTags;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

public class MobJarTile extends ModdedTile implements ITickable, IDispellable, ITooltipProvider {
   @Nullable
   public Entity cachedEntity;
   private CompoundTag entityTag;
   private CompoundTag extraDataTag;

   public MobJarTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.MOB_JAR_TILE, pos, state);
   }

   @Override
   public void tick() {
      try {
         if (this.f_58857_.f_46443_ && this.cachedEntity != null && this.cachedEntity instanceof Mob mob && !(mob instanceof Bee)) {
            mob.m_21563_().m_8128_();
         }

         this.dispatchBehavior(behavior -> behavior.tick(this));
         this.m_6596_();
      } catch (Exception var3) {
         var3.printStackTrace();
      }
   }

   public boolean setEntityData(@NotNull Entity entity) {
      CompoundTag tag = new CompoundTag();
      if (entity.m_142391_() && entity.m_20223_(tag)) {
         this.cachedEntity = EntityType.m_20645_(tag, this.f_58857_, Function.identity());
         this.cachedEntity.m_20011_(new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
         this.cachedEntity.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5);
         this.extraDataTag = null;
         this.entityTag = tag;
         if (!this.f_58857_.f_46443_) {
            this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_(MobJar.LIGHT_LEVEL, this.calculateLight()));
            this.updateBlock();
         }

         return true;
      } else {
         try {
            this.writeSimple(entity);
            return true;
         } catch (Exception var4) {
            var4.printStackTrace();
            return false;
         }
      }
   }

   public void writeSimple(Entity e) {
      CompoundTag tag = new CompoundTag();
      tag.m_128359_("id", EntityType.m_20613_(e.m_6095_()).toString());
      this.cachedEntity = e.m_6095_().m_20615_(this.f_58857_);
      this.cachedEntity.m_20011_(new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
      this.cachedEntity.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5);
      this.extraDataTag = null;
      this.entityTag = tag;
      if (!this.f_58857_.f_46443_) {
         this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.m_58900_().m_61124_(MobJar.LIGHT_LEVEL, this.calculateLight()));
         this.updateBlock();
      }
   }

   public int calculateLight() {
      if (this.getEntity() == null) {
         return 0;
      } else if (this.getEntity().m_6060_()) {
         return 15;
      } else if (this.getEntity() instanceof LightningBolt) {
         return 15;
      } else {
         AtomicInteger light = new AtomicInteger();
         JarBehaviorRegistry.forEach(this.getEntity(), behavior -> light.set(Math.max(light.get(), behavior.lightLevel(this))));
         return light.get();
      }
   }

   @Nullable
   public Entity getEntity() {
      if (this.entityTag != null && this.cachedEntity == null) {
         this.cachedEntity = loadEntityFromTag(this.f_58857_, this.entityTag);
         if (this.cachedEntity == null) {
            return null;
         }

         this.cachedEntity.m_20011_(new AABB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
         this.cachedEntity.m_6034_((double)this.f_58858_.m_123341_() + 0.5, (double)this.f_58858_.m_123342_() + 0.5, (double)this.f_58858_.m_123343_() + 0.5);
      }

      return this.cachedEntity;
   }

   @Override
   public boolean onDispel(@NotNull LivingEntity caster) {
      if (this.entityTag == null) {
         return false;
      } else {
         Entity entity = loadEntityFromTag(this.f_58857_, this.entityTag);
         if (entity != null && !entity.m_6095_().m_204039_(EntityTags.JAR_RELEASE_BLACKLIST)) {
            entity.m_6034_((double)this.m_58899_().m_123341_() + 0.5, (double)this.m_58899_().m_123342_() + 1.0, (double)this.m_58899_().m_123343_() + 0.5);
            this.f_58857_.m_7967_(entity);
            this.removeEntity();
            return true;
         } else {
            return false;
         }
      }
   }

   public void removeEntity() {
      this.entityTag = null;
      this.cachedEntity = null;
      this.extraDataTag = null;
      this.updateBlock();
   }

   public CompoundTag getExtraDataTag() {
      return this.extraDataTag == null ? new CompoundTag() : this.extraDataTag;
   }

   public void setExtraDataTag(CompoundTag tag) {
      this.extraDataTag = tag;
      this.m_6596_();
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.entityTag != null || this.cachedEntity != null) {
         this.cachedEntity = this.getEntity();
         if (this.cachedEntity != null) {
            tag.m_128365_("entityTag", this.saveEntityToTag(this.cachedEntity));
            if (tag.m_128469_("entityTag").m_128441_("id")) {
               tag.m_128359_("entityId", tag.m_128469_("entityTag").m_128461_("id"));
            }
         }
      }

      if (this.extraDataTag != null) {
         tag.m_128365_("extraMobData", this.extraDataTag);
      }
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      if (pTag.m_128441_("entityTag")) {
         this.entityTag = pTag.m_128469_("entityTag");
         this.cachedEntity = null;
      }

      this.extraDataTag = pTag.m_128469_("extraMobData");
   }

   @Override
   public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
      this.cachedEntity = null;
      this.entityTag = null;
      super.onDataPacket(net, pkt);
   }

   public void dispatchBehavior(Consumer<JarBehavior<? extends Entity>> consumer) {
      Entity entity = this.getEntity();
      if (entity != null) {
         JarBehaviorRegistry.forEach(entity, consumer);
      }
   }

   public static Entity loadEntityFromTag(Level level, CompoundTag tag) {
      Entity entity = EntityType.m_20645_(tag, level, Function.identity());
      if (entity == null) {
         String id = tag.m_128461_("id");
         Optional<EntityType<?>> type = EntityType.m_20632_(id);
         if (type.isPresent()) {
            entity = type.get().m_20615_(level);
         }
      }

      return entity;
   }

   public CompoundTag saveEntityToTag(Entity entity) {
      CompoundTag tag = new CompoundTag();
      if (entity != null) {
         entity.m_20223_(tag);
         if (tag.m_128456_()) {
            tag.m_128359_("id", EntityType.m_20613_(entity.m_6095_()).toString());
         }
      }

      return tag;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      Entity entity = this.getEntity();
      if (entity != null) {
         JarBehaviorRegistry.forEach(entity, behavior -> behavior.getTooltip(this, tooltip));
      }
   }
}
