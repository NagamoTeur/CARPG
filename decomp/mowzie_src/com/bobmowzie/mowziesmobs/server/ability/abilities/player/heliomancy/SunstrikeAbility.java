package com.bobmowzie.mowziesmobs.server.ability.abilities.player.heliomancy;

import com.bobmowzie.mowziesmobs.server.ability.AbilitySection;
import com.bobmowzie.mowziesmobs.server.ability.AbilityType;
import com.bobmowzie.mowziesmobs.server.ability.PlayerAbility;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.potion.EffectHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class SunstrikeAbility extends PlayerAbility {
   private static final double REACH = 15.0;
   private static final int SUNSTRIKE_RECOVERY = 15;
   protected BlockHitResult rayTrace;

   public SunstrikeAbility(AbilityType<Player, SunstrikeAbility> abilityType, Player user) {
      super(
         abilityType,
         user,
         new AbilitySection[]{
            new AbilitySection.AbilitySectionInstant(AbilitySection.AbilitySectionType.ACTIVE),
            new AbilitySection.AbilitySectionDuration(AbilitySection.AbilitySectionType.RECOVERY, 15)
         }
      );
   }

   private static BlockHitResult rayTrace(LivingEntity entity, double reach) {
      Vec3 pos = entity.m_20299_(0.0F);
      Vec3 segment = entity.m_20154_();
      segment = pos.m_82520_(segment.f_82479_ * reach, segment.f_82480_ * reach, segment.f_82481_ * reach);
      return entity.f_19853_.m_45547_(new ClipContext(pos, segment, Block.COLLIDER, Fluid.NONE, entity));
   }

   @Override
   public boolean tryAbility() {
      super.tryAbility();
      LivingEntity user = this.getUser();
      BlockHitResult raytrace = rayTrace(user, 15.0);
      if (raytrace.m_6662_() == Type.BLOCK && raytrace.m_82434_() == Direction.UP) {
         this.rayTrace = raytrace;
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void start() {
      super.start();
      LivingEntity user = this.getUser();
      if (!user.f_19853_.m_5776_()) {
         BlockPos hit = this.rayTrace.m_82425_();
         EntitySunstrike sunstrike = new EntitySunstrike(
            (EntityType<? extends EntitySunstrike>)EntityHandler.SUNSTRIKE.get(), user.f_19853_, user, hit.m_123341_(), hit.m_123342_(), hit.m_123343_()
         );
         sunstrike.onSummon();
         user.f_19853_.m_7967_(sunstrike);
      }

      this.playAnimation("sunstrike", false);
   }

   @Override
   public boolean canUse() {
      return this.getUser() instanceof Player && !this.getUser().m_150109_().m_36056_().m_41619_()
         ? false
         : this.getUser().m_21023_((MobEffect)EffectHandler.SUNS_BLESSING.get()) && super.canUse();
   }

   @Override
   public boolean preventsBlockBreakingBuilding() {
      return false;
   }

   @Override
   public boolean preventsAttacking() {
      return false;
   }

   @Override
   public boolean preventsInteracting() {
      return false;
   }
}
