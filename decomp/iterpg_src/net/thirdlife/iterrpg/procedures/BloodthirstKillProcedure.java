package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.init.IterRpgModItems;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

@EventBusSubscriber
public class BloodthirstKillProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().f_19853_,
            event.getEntity().m_20185_(),
            event.getEntity().m_20186_(),
            event.getEntity().m_20189_(),
            event.getEntity(),
            event.getSource().m_7639_()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      execute(null, world, x, y, z, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double splashdmg = 0.0;
         if (sourceentity instanceof Player
            && (sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() == IterRpgModItems.BLOODTHIRST.get()) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(),
                  x,
                  y + (double)(entity.m_20206_() / 2.0F),
                  z,
                  20,
                  (double)(entity.m_20205_() / 2.0F),
                  (double)(entity.m_20206_() / 2.0F),
                  (double)(entity.m_20205_() / 2.0F),
                  0.0
               );
            }

            Vec3 _center = new Vec3(x, y + (double)(entity.m_20206_() / 2.0F), z);

            for (Entity entityiterator : world.m_6443_(
                  Entity.class, new AABB(_center, _center).m_82400_((double)(2.0F * (entity.m_20205_() + entity.m_20206_())) / 2.0), e -> true
               )
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && entityiterator != sourceentity
                  && entityiterator instanceof LivingEntity) {
                  entityiterator.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }

            label130: {
               if (sourceentity instanceof LivingEntity _livEntxx
                  && _livEntxx.m_21023_(MobEffects.f_19600_)
                  && sourceentity instanceof LivingEntity _livEntx
                  && _livEntx.m_21023_(MobEffects.f_19598_)) {
                  int var30;
                  label103: {
                     if (sourceentity instanceof LivingEntity _livEntxxx && _livEntxxx.m_21023_(MobEffects.f_19598_)) {
                        var30 = _livEntxxx.m_21124_(MobEffects.f_19598_).m_19557_();
                        break label103;
                     }

                     var30 = 0;
                  }

                  if (var30 <= 1080 && sourceentity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     MobEffectInstance var10001;
                     int var10004;
                     label94: {
                        var10001 = new MobEffectInstance;
                        if (sourceentity instanceof LivingEntity _livEntxxx && _livEntxxx.m_21023_(MobEffects.f_19598_)) {
                           var10004 = _livEntxxx.m_21124_(MobEffects.f_19598_).m_19557_();
                           break label94;
                        }

                        var10004 = 0;
                     }

                     var10001./* $VF: Unable to resugar constructor */<init>(MobEffects.f_19598_, var10004 + 120, 0, false, true);
                     _entity.m_7292_(var10001);
                  }

                  label89: {
                     if (sourceentity instanceof LivingEntity _livEntxxx && _livEntxxx.m_21023_(MobEffects.f_19600_)) {
                        var30 = _livEntxxx.m_21124_(MobEffects.f_19600_).m_19557_();
                        break label89;
                     }

                     var30 = 0;
                  }

                  if (var30 > 1080 || !(sourceentity instanceof LivingEntity _entity) || _entity.f_19853_.m_5776_()) {
                     break label130;
                  }

                  MobEffectInstance var32;
                  int var33;
                  label83: {
                     var32 = new MobEffectInstance;
                     if (sourceentity instanceof LivingEntity _livEntxxx && _livEntxxx.m_21023_(MobEffects.f_19600_)) {
                        var33 = _livEntxxx.m_21124_(MobEffects.f_19600_).m_19557_();
                        break label83;
                     }

                     var33 = 0;
                  }

                  var32./* $VF: Unable to resugar constructor */<init>(MobEffects.f_19600_, var33 + 120, 0, false, true);
                  _entity.m_7292_(var32);
                  break label130;
               }

               if (sourceentity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19598_, 100, 0, true, false));
               }

               if (sourceentity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19600_, 100, 0, true, false));
               }
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(),
                  sourceentity.m_20185_(),
                  sourceentity.m_20186_() + 0.9,
                  sourceentity.m_20189_(),
                  8,
                  0.16,
                  0.3,
                  0.16,
                  0.0
               );
            }
         }
      }
   }
}
