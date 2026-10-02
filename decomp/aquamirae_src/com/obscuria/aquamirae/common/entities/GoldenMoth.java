package com.obscuria.aquamirae.common.entities;

import com.obscuria.aquamirae.registry.AquamiraeBlocks;
import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeParticleTypes;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnPlacements.SpawnPredicate;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.PlayMessages.SpawnEntity;
import org.jetbrains.annotations.NotNull;

@ShipGraveyardEntity
public class GoldenMoth extends PathfinderMob {
   public GoldenMoth(SpawnEntity packet, Level world) {
      this((EntityType<GoldenMoth>)AquamiraeEntities.GOLDEN_MOTH.get(), world);
   }

   public GoldenMoth(EntityType<GoldenMoth> type, Level world) {
      super(type, world);
      this.f_21364_ = 10;
      this.f_21342_ = new FlyingMoveControl(this, 10, true);
   }

   @NotNull
   protected PathNavigation m_6037_(@NotNull Level world) {
      return new FlyingPathNavigation(this, world);
   }

   protected void m_8099_() {
      super.m_8099_();
      this.f_21345_.m_25352_(1, new RandomStrollGoal(this, 1.0, 20) {
         protected Vec3 m_7037_() {
            RandomSource random = GoldenMoth.this.m_217043_();
            double dir_x = GoldenMoth.this.m_20185_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_y = GoldenMoth.this.m_20186_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            double dir_z = GoldenMoth.this.m_20189_() + (double)((random.m_188501_() * 2.0F - 1.0F) * 16.0F);
            return new Vec3(dir_x, dir_y, dir_z);
         }
      });
   }

   @NotNull
   public MobType m_6336_() {
      return MobType.f_21640_;
   }

   public SoundEvent m_7515_() {
      return (SoundEvent)AquamiraeSounds.ENTITY_GOLDEN_MOTH_AMBIENT.get();
   }

   public SoundEvent m_7975_(@NotNull DamageSource source) {
      return SoundEvents.f_11915_;
   }

   public SoundEvent m_5592_() {
      return SoundEvents.f_11910_;
   }

   public boolean m_142535_(float l, float d, @NotNull DamageSource source) {
      return false;
   }

   public boolean m_6469_(DamageSource source, float amount) {
      if (source.m_7640_() instanceof ThrownPotion || source.m_7640_() instanceof AreaEffectCloud) {
         return false;
      } else if (source == DamageSource.f_19315_) {
         return false;
      } else if (source == DamageSource.f_19314_) {
         return false;
      } else if (source == DamageSource.f_19312_) {
         return false;
      } else if (source == DamageSource.f_19306_) {
         return false;
      } else {
         if (this.f_19853_ instanceof ServerLevel server) {
            server.m_8767_((SimpleParticleType)AquamiraeParticleTypes.SHINE.get(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 6, 0.05, 0.05, 0.05, 0.8);
         }

         return super.m_6469_(source, amount);
      }
   }

   @NotNull
   public InteractionResult m_6071_(@NotNull Player player, @NotNull InteractionHand hand) {
      super.m_6071_(player, hand);
      ItemStack stack = player.m_21120_(hand);
      if (stack.m_41720_() == Items.f_42590_) {
         stack.m_41774_(1);
         if (!this.f_19853_.m_5776_()) {
            this.f_19853_
               .m_5594_(
                  null,
                  new BlockPos(this.m_20185_(), this.m_20186_(), this.m_20189_()),
                  (SoundEvent)AquamiraeSounds.ENTITY_GOLDEN_MOTH_CATCH.get(),
                  SoundSource.AMBIENT,
                  1.0F,
                  1.0F
               );
            ItemEntity item = new ItemEntity(
               this.f_19853_, this.m_20185_(), this.m_20186_(), this.m_20189_(), new ItemStack((ItemLike)AquamiraeBlocks.GOLDEN_MOTH_IN_A_JAR.get())
            );
            item.m_32010_(10);
            this.f_19853_.m_7967_(item);
            this.m_146870_();
         }
      }

      return InteractionResult.m_19078_(this.f_19853_.m_5776_());
   }

   public void m_6075_() {
      this.getPersistentData().m_128347_("shine", this.getPersistentData().m_128459_("shine") + 1.0);
      if (this.getPersistentData().m_128459_("shine") > 2.0) {
         this.getPersistentData().m_128347_("shine", 0.0);
         if (this.f_19853_ instanceof ServerLevel server) {
            server.m_8767_((SimpleParticleType)AquamiraeParticleTypes.SHINE.get(), this.m_20185_(), this.m_20186_(), this.m_20189_(), 1, 0.1, 0.1, 0.1, 0.1);
         }
      }

      if (this.m_20072_()) {
         this.m_20256_(this.m_20184_().m_82520_(0.0, 0.05F, 0.0));
      }

      super.m_6075_();
   }

   protected void m_7840_(double y, boolean onGroundIn, @NotNull BlockState state, @NotNull BlockPos pos) {
   }

   public void m_20242_(boolean ignored) {
      super.m_20242_(true);
   }

   public void m_8107_() {
      super.m_8107_();
      this.m_20242_(true);
   }

   public static SpawnPredicate<GoldenMoth> getSpawnRules() {
      return (entityType, level, spawnType, pos, random) -> {
         if (level instanceof Level world && !world.m_46461_()) {
            return true;
         }

         return false;
      };
   }

   @NotNull
   public static Builder createAttributes() {
      return Mob.m_21552_().m_22268_(Attributes.f_22280_, 0.4).m_22268_(Attributes.f_22279_, 0.4).m_22268_(Attributes.f_22276_, 3.0);
   }
}
