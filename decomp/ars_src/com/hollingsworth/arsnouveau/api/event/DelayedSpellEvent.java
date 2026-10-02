package com.hollingsworth.arsnouveau.api.event;

import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import javax.annotation.Nullable;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class DelayedSpellEvent implements ITimedEvent {
   private int duration;
   private final Spell spell;
   private final SpellContext context;
   private final HitResult result;
   private final Level world;
   @Nullable
   private final LivingEntity shooter;

   public DelayedSpellEvent(int delay, Spell spell, HitResult result, Level world, @Nullable LivingEntity shooter, SpellContext context) {
      this.duration = delay;
      this.spell = spell;
      this.result = result;
      this.world = world;
      this.shooter = shooter;
      this.context = context;
   }

   @Override
   public void tick(boolean serverSide) {
      this.duration--;
      if (this.duration <= 0 && serverSide) {
         this.resolveSpell();
      } else if (!serverSide && this.result != null) {
         BlockPos hitVec = this.result instanceof EntityHitResult ? ((EntityHitResult)this.result).m_82443_().m_20183_() : new BlockPos(this.result.m_82450_());
         ParticleUtil.spawnTouch((ClientLevel)this.world, hitVec, this.context.getColors());
      }
   }

   public void resolveSpell() {
      if (this.world != null) {
         HitResult newResult = this.result;
         if (this.result instanceof EntityHitResult ehr && ehr.m_82443_().m_213877_()) {
            newResult = new BlockHitResult(ehr.m_82450_(), Direction.UP, ehr.m_82443_().m_20097_(), true);
         }

         SpellResolver resolver = new SpellResolver(this.context);
         resolver.onResolveEffect(this.world, newResult);
      }
   }

   @Override
   public boolean isExpired() {
      return this.duration <= 0 || this.world == null;
   }
}
