package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.MowziesMobs;
import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickEmpty;

public class SpawnBoulderAbility extends PlayerAbility {
   private static int MAX_CHARGE = 60;
   public static final double SPAWN_BOULDER_REACH = 5.0;
   public BlockPos spawnBoulderPos = new BlockPos(0, 0, 0);
   public Vec3 lookPos = new Vec3(0.0, 0.0, 0.0);
   private BlockState spawnBoulderBlock = Blocks.f_50493_.m_49966_();
   private int spawnBoulderCharge = 0;

   public SpawnBoulderAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, MAX_CHARGE),
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 12)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      this.playAnimation("spawn_boulder_start", false);
   }

   @Override
   public boolean tryAbility() {
      Vec3 from = this.getUser().m_20299_(1.0F);
      Vec3 to = from.m_82549_(this.getUser().m_20154_().m_82490_(5.0));
      BlockHitResult result = this.getUser().f_19853_.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.m_6662_() == Type.BLOCK) {
         this.lookPos = result.m_82450_();
      }

      this.spawnBoulderPos = result.m_82425_();
      this.spawnBoulderBlock = this.getUser().f_19853_.m_8055_(this.spawnBoulderPos);
      if (result.m_82434_() != Direction.UP) {
         BlockState blockAbove = this.getUser().f_19853_.m_8055_(this.spawnBoulderPos.m_7494_());
         if (blockAbove.m_60828_(this.getUser().f_19853_, this.spawnBoulderPos.m_7494_()) || blockAbove.m_60795_()) {
            return false;
         }
      }

      return EffectGeomancy.isBlockDiggable(this.spawnBoulderBlock);
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.spawnBoulderCharge++;
         if (this.spawnBoulderCharge > 1) {
            this.getUser().m_7292_(new MobEffectInstance(MobEffects.f_19597_, 3, 3, false, false));
         }

         if (this.spawnBoulderCharge == 1 && this.getUser().f_19853_.f_46443_) {
            MowziesMobs.PROXY.playBoulderChargeSound(this.getUser());
         }

         if ((this.spawnBoulderCharge + 10) % 10 == 0 && this.spawnBoulderCharge < 40 && this.getUser().f_19853_.f_46443_) {
            AdvancedParticleBase.spawnParticle(
               this.getUser().f_19853_,
               (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
               (double)((float)this.getUser().m_20185_()),
               (double)((float)this.getUser().m_20186_() + this.getUser().m_20206_() / 2.0F),
               (double)((float)this.getUser().m_20189_()),
               0.0,
               0.0,
               0.0,
               false,
               0.0,
               Math.PI / 2,
               0.0,
               0.0,
               3.5,
               0.83F,
               1.0,
               0.39F,
               1.0,
               1.0,
               10.0,
               true,
               true,
               new ParticleComponent[]{
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.0F, 0.7F), false
                  ),
                  new ParticleComponent.PropertyControl(
                     ParticleComponent.PropertyControl.EnumParticleProperty.SCALE,
                     ParticleComponent.KeyTrack.startAndEnd((0.8F + 2.7F * (float)this.spawnBoulderCharge / 60.0F) * 10.0F, 0.0F),
                     false
                  )
               }
            );
         }

         if (this.spawnBoulderCharge == 50) {
            if (this.getUser().f_19853_.f_46443_) {
               AdvancedParticleBase.spawnParticle(
                  this.getUser().f_19853_,
                  (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
                  (double)((float)this.getUser().m_20185_()),
                  (double)((float)this.getUser().m_20186_() + this.getUser().m_20206_() / 2.0F),
                  (double)((float)this.getUser().m_20189_()),
                  0.0,
                  0.0,
                  0.0,
                  true,
                  0.0,
                  0.0,
                  0.0,
                  0.0,
                  3.5,
                  0.83F,
                  1.0,
                  0.39F,
                  1.0,
                  1.0,
                  20.0,
                  true,
                  true,
                  new ParticleComponent[]{
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.ALPHA, ParticleComponent.KeyTrack.startAndEnd(0.7F, 0.0F), false
                     ),
                     new ParticleComponent.PropertyControl(
                        ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 40.0F), false
                     )
                  }
               );
            }

            this.getUser().m_5496_((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.0F, 1.0F);
         }

         int size = this.getBoulderSize() + 1;
         EntityDimensions dim = EntityBoulderBase.SIZE_MAP.get(EntityGeomancyBase.GeomancyTier.values()[size + 1]);
         if (!this.getUser()
               .f_19853_
               .m_45772_(
                  dim.m_20384_(
                     (double)((float)this.spawnBoulderPos.m_123341_() + 0.5F),
                     (double)(this.spawnBoulderPos.m_123342_() + 2),
                     (double)((float)this.spawnBoulderPos.m_123343_() + 0.5F)
                  )
               )
            || this.getUser()
                  .m_20275_((double)this.spawnBoulderPos.m_123341_(), (double)this.spawnBoulderPos.m_123342_(), (double)this.spawnBoulderPos.m_123343_())
               > 36.0) {
            this.nextSection();
         }
      }
   }

   @Override
   protected void beginSection(AbilitySection section) {
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.spawnBoulder();
      }
   }

   private int getBoulderSize() {
      return (int)Math.min(Math.max(0.0, Math.floor((double)((float)this.spawnBoulderCharge / 10.0F)) - 1.0), 2.0);
   }

   private void spawnBoulder() {
      if (this.spawnBoulderCharge <= 2) {
         this.playAnimation("spawn_boulder_instant", false);
      } else {
         this.playAnimation("spawn_boulder_end", false);
      }

      int size = this.getBoulderSize();
      if (this.spawnBoulderCharge >= 60) {
         size = 3;
      }

      EntityBoulderProjectile boulder = new EntityBoulderProjectile(
         (EntityType<? extends EntityBoulderProjectile>)EntityHandler.BOULDER_PROJECTILE.get(),
         this.getUser().f_19853_,
         this.getUser(),
         this.spawnBoulderBlock,
         this.spawnBoulderPos,
         EntityGeomancyBase.GeomancyTier.values()[size + 1]
      );
      boulder.m_6034_(
         (double)((float)this.spawnBoulderPos.m_123341_() + 0.5F),
         (double)(this.spawnBoulderPos.m_123342_() + 2),
         (double)((float)this.spawnBoulderPos.m_123343_() + 0.5F)
      );
      if (!this.getUser().f_19853_.f_46443_ && boulder.checkCanSpawn()) {
         this.getUser().f_19853_.m_7967_(boulder);
      }

      if (this.spawnBoulderCharge > 2) {
         Vec3 playerEyes = this.getUser().m_20299_(1.0F);
         Vec3 vec = playerEyes.m_82546_(this.lookPos).m_82541_();
         float yaw = (float)Math.atan2(vec.f_82481_, vec.f_82479_);
         float pitch = (float)Math.asin(vec.f_82480_);
         this.getUser().m_146922_((float)((double)(yaw * 180.0F) / Math.PI + 90.0));
         this.getUser().m_146926_((float)((double)(pitch * 180.0F) / Math.PI));
      }

      this.spawnBoulderCharge = 0;
   }

   @Override
   public void onRightMouseUp(Player player) {
      super.onRightMouseUp(player);
      if (this.isUsing() && this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         if (player.m_20275_((double)this.spawnBoulderPos.m_123341_(), (double)this.spawnBoulderPos.m_123342_(), (double)this.spawnBoulderPos.m_123343_())
            < 36.0) {
            this.nextSection();
         } else {
            this.spawnBoulderCharge = 0;
         }
      }
   }

   @Override
   public boolean canUse() {
      return EffectGeomancy.canUse(this.getUser()) && super.canUse();
   }

   @Override
   public void end() {
      this.spawnBoulderCharge = 0;
      super.end();
   }

   @Override
   public void readNBT(Tag nbt) {
      super.readNBT(nbt);
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP) {
         this.spawnBoulderCharge = this.getTicksInSection();
      }
   }

   @Override
   public void onRightClickBlock(RightClickBlock event) {
      super.onRightClickBlock(event);
      if (!event.getLevel().m_5776_()) {
         AbilityHandler.INSTANCE.sendAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_BOULDER_ABILITY);
      }
   }

   @Override
   public void onRightClickEmpty(RightClickEmpty event) {
      super.onRightClickEmpty(event);
      AbilityHandler.INSTANCE.sendPlayerTryAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_BOULDER_ABILITY);
   }

   @Override
   public void onRenderTick(RenderTickEvent event) {
      super.onRenderTick(event);
      if (this.isUsing() && this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.STARTUP && this.getTicksInSection() > 1) {
         Vec3 playerEyes = this.getUser().m_20299_(Minecraft.m_91087_().m_91296_());
         Vec3 vec = playerEyes.m_82546_(this.lookPos).m_82541_();
         float yaw = (float)Math.atan2(vec.f_82481_, vec.f_82479_);
         float pitch = (float)Math.asin(vec.f_82480_);
         this.getUser().m_146922_((float)((double)(yaw * 180.0F) / Math.PI + 90.0));
         this.getUser().m_146926_((float)((double)(pitch * 180.0F) / Math.PI));
         this.getUser().f_20885_ = this.getUser().m_146908_();
         this.getUser().f_19859_ = this.getUser().m_146908_();
         this.getUser().f_19860_ = this.getUser().m_146909_();
         this.getUser().f_20886_ = this.getUser().f_20885_;
      }
   }
}
