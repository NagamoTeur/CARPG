package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.client.particle.ParticleHandler;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleBase;
import com.bobmowzie.mowziesmobs.client.particle.util.AdvancedParticleData;
import com.bobmowzie.mowziesmobs.client.particle.util.ParticleComponent;
import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityGeomancyBase;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleType;
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

public class RockSlingAbility extends PlayerAbility {
   public static final double SPAWN_BOULDER_REACH = 5.0;
   public BlockPos spawnBoulderPos = new BlockPos(0, 0, 0);
   public Vec3 lookPos = new Vec3(0.0, 0.0, 0.0);
   private BlockState spawnBoulderBlock = Blocks.f_50493_.m_49966_();
   private int damage = 3;

   public RockSlingAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 5),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, 10),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 5)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      Vec3 from = this.getUser().m_20299_(1.0F);
      Vec3 to = from.m_82549_(this.getUser().m_20154_().m_82490_(5.0));
      BlockHitResult result = this.getUser().f_19853_.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.m_6662_() == Type.BLOCK) {
         this.lookPos = result.m_82450_();
      }

      this.spawnBoulderPos = result.m_82425_();
      this.spawnBoulderBlock = this.getUser().f_19853_.m_8055_(this.spawnBoulderPos);
      this.playAnimation("rock_sling_right", false);
      if (this.getUser().f_19853_.m_5776_()) {
         AdvancedParticleBase.spawnParticle(
            this.getUser().f_19853_,
            (ParticleType<AdvancedParticleData>)ParticleHandler.RING2.get(),
            (double)((float)this.getUser().m_20185_()),
            (double)((float)this.getUser().m_20186_() + 0.01F),
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
                  ParticleComponent.PropertyControl.EnumParticleProperty.SCALE, ParticleComponent.KeyTrack.startAndEnd(0.0F, 17.0F), false
               )
            }
         );
      } else {
         for (int i = 0; i < 3; i++) {
            Vec3 spawnPos = new Vec3(0.0, -1.0, 2.0)
               .m_82524_((float)Math.toRadians((double)(-this.getUser().m_146908_())))
               .m_82524_((float)Math.toRadians((double)(-90 + i * 80)))
               .m_82549_(this.getUser().m_20182_());
            EntityRockSling boulder = new EntityRockSling(
               (EntityType<? extends EntityBoulderProjectile>)EntityHandler.ROCK_SLING.get(),
               this.getUser().f_19853_,
               this.getUser(),
               this.spawnBoulderBlock,
               this.spawnBoulderPos,
               EntityGeomancyBase.GeomancyTier.values()[1]
            );
            boulder.m_6034_(spawnPos.m_7096_() + 0.5, spawnPos.m_7098_() + 2.0, spawnPos.m_7094_() + 0.5);
            boulder.setLaunchVec(this.getUser().m_20252_(1.0F).m_82542_(1.0, 0.9F, 1.0));
            boulder.setTravelling(true);
            boulder.setDamage(4);
            if (!this.getUser().f_19853_.f_46443_ && boulder.checkCanSpawn()) {
               this.getUser().f_19853_.m_7967_(boulder);
            }
         }
      }
   }
}
