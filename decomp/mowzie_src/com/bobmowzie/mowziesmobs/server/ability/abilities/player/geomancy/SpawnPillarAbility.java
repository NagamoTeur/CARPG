package com.bobmowzie.mowziesmobs.server.ability.abilities.player.geomancy;

import com.bobmowzie.mowziesmobs.server.ability.Ability;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

public class SpawnPillarAbility extends PlayerAbility {
   private static int MAX_DURATION = 120;
   private static int MAX_RANGE_TO_GROUND = 12;
   private BlockPos spawnPillarPos;
   private BlockState spawnPillarBlock;
   private EntityPillar pillar;

   public SpawnPillarAbility(AbilityType<Player, ? extends Ability> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.STARTUP, 2),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.ACTIVE, MAX_DURATION)
         }
      );
   }

   @Override
   public void start() {
      super.start();
      this.playAnimation("pillar_spawn", false);
      this.getUser().m_20256_(this.getUser().m_20184_().m_82520_(0.0, -2.0, 0.0));
   }

   @Override
   public boolean tryAbility() {
      Vec3 from = this.getUser().m_20182_();
      Vec3 to = from.m_82492_(0.0, (double)MAX_RANGE_TO_GROUND, 0.0);
      BlockHitResult result = this.getUser().f_19853_.m_45547_(new ClipContext(from, to, Block.COLLIDER, Fluid.NONE, this.getUser()));
      if (result.m_6662_() == Type.MISS) {
         return false;
      } else {
         this.spawnPillarPos = result.m_82425_();
         this.spawnPillarBlock = this.getUser().f_19853_.m_8055_(this.spawnPillarPos);
         if (result.m_82434_() != Direction.UP) {
            BlockState blockAbove = this.getUser().f_19853_.m_8055_(this.spawnPillarPos.m_7494_());
            if (blockAbove.m_60828_(this.getUser().f_19853_, this.spawnPillarPos.m_7494_()) || blockAbove.m_60795_()) {
               return false;
            }
         }

         return EffectGeomancy.isBlockDiggable(this.spawnPillarBlock);
      }
   }

   @Override
   public void tickUsing() {
      super.tickUsing();
   }

   @Override
   protected void beginSection(AbilitySection section) {
      if (section.sectionType == AbilitySection.AbilitySectionType.ACTIVE) {
         this.spawnPillar();
      }
   }

   private void spawnPillar() {
      this.pillar = new EntityPillar(
         (EntityType<? extends EntityPillar>)EntityHandler.PILLAR.get(), this.getUser().f_19853_, this.getUser(), this.spawnPillarBlock, this.spawnPillarPos
      );
      this.pillar
         .m_6034_(
            (double)((float)this.spawnPillarPos.m_123341_() + 0.5F),
            (double)(this.spawnPillarPos.m_123342_() + 1),
            (double)((float)this.spawnPillarPos.m_123343_() + 0.5F)
         );
      if (!this.getUser().f_19853_.f_46443_ && this.pillar.checkCanSpawn()) {
         this.getUser().f_19853_.m_7967_(this.pillar);
      }
   }

   @Override
   public void end() {
      super.end();
      if (this.pillar != null) {
         this.pillar.stopRising();
      }

      this.pillar = null;
   }

   @Override
   public boolean canUse() {
      return EffectGeomancy.canUse(this.getUser()) && super.canUse();
   }

   @Override
   public void onJump(LivingJumpEvent event) {
      super.onJump(event);
      if (this.getUser().m_6047_() && !event.getEntity().m_9236_().m_5776_()) {
         AbilityHandler.INSTANCE.sendAbilityMessage(event.getEntity(), AbilityHandler.SPAWN_PILLAR_ABILITY);
      }
   }

   @Override
   public void onSneakUp(Player player) {
      super.onSneakUp(player);
      if (this.getCurrentSection().sectionType == AbilitySection.AbilitySectionType.ACTIVE && this.isUsing()) {
         if (this.pillar != null) {
            this.pillar.stopRising();
         }

         this.nextSection();
      }
   }
}
