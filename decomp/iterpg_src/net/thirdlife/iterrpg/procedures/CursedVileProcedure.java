package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.init.IterRpgModMobEffects;

@EventBusSubscriber
public class CursedVileProcedure {
   @SubscribeEvent
   public static void onEntityAttacked(LivingAttackEvent event) {
      Entity entity = event.getEntity();
      if (event != null && entity != null) {
         execute(event, entity, event.getSource().m_7639_());
      }
   }

   public static void execute(Entity entity, Entity sourceentity) {
      execute(null, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double effectdecide = 0.0;
         if (entity instanceof LivingEntity _livEnt
            && _livEnt.m_21023_((MobEffect)IterRpgModMobEffects.CURSED.get())
            && !entity.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:mourners")))
            && sourceentity instanceof LivingEntity) {
            effectdecide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 3);
            if (effectdecide == 1.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 50, 0, false, true));
            }

            if (effectdecide == 2.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 50, 0, false, true));
            }

            if (effectdecide == 3.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 50, 0, false, true));
            }
         }
      }
   }
}
