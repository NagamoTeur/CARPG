package com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom;

import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventScheduler;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.EventSeries;
import com.cerbon.bosses_of_mass_destruction.api.maelstrom.general.event.TimedEvent;
import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.entity.ai.action.IActionWithCooldown;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.HitboxId;
import com.cerbon.bosses_of_mass_destruction.entity.custom.void_blossom.hitbox.NetworkedHitboxManager;
import com.cerbon.bosses_of_mass_destruction.entity.util.ProjectileThrower;
import com.cerbon.bosses_of_mass_destruction.projectile.SporeBallProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import com.cerbon.bosses_of_mass_destruction.sound.BMDSounds;
import com.cerbon.bosses_of_mass_destruction.util.BMDUtils;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class SporeAction implements IActionWithCooldown {
   private final VoidBlossomEntity entity;
   private final EventScheduler eventScheduler;
   private final Supplier<Boolean> shouldCancel;

   public SporeAction(VoidBlossomEntity entity, EventScheduler eventScheduler, Supplier<Boolean> shouldCancel) {
      this.entity = entity;
      this.eventScheduler = eventScheduler;
      this.shouldCancel = shouldCancel;
   }

   @Override
   public int perform() {
      LivingEntity target = this.entity.m_5448_();
      if (!(target instanceof ServerPlayer)) {
         return 80;
      } else {
         this.eventScheduler
            .addEvent(
               new EventSeries(
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Spore.getId()), 20, 1, this.shouldCancel),
                  new TimedEvent(() -> this.entity.m_20088_().m_135381_(NetworkedHitboxManager.hitbox, HitboxId.Idle.getId()), 27, 1, () -> false)
               )
            );
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> BMDUtils.playSound(
                        ((ServerPlayer)target).m_9236_(),
                        this.entity.m_20182_(),
                        (SoundEvent)BMDSounds.SPORE_PREPARE.get(),
                        SoundSource.HOSTILE,
                        1.5F,
                        32.0,
                        null
                     ),
                  26
               )
            );
         this.eventScheduler
            .addEvent(
               new TimedEvent(
                  () -> new ProjectileThrower(
                           () -> {
                              SporeBallProjectile projectile = new SporeBallProjectile(
                                 this.entity, this.entity.f_19853_, new ExemptEntities(List.of((EntityType<?>)BMDEntities.VOID_BLOSSOM.get()))
                              );
                              projectile.m_146884_(this.entity.m_146892_());
                              return new ProjectileThrower.ProjectileData(projectile, 0.75F, 0.0F, 0.2);
                           }
                        )
                        .throwProjectile(target.m_146892_()),
                  45,
                  1,
                  this.shouldCancel
               )
            );
         return 100;
      }
   }
}
