package net.thirdlife.iterrpg.procedures;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.horse.Donkey;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.animal.horse.Llama;
import net.minecraft.world.entity.animal.horse.Mule;
import net.minecraft.world.entity.animal.horse.TraderLlama;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;

public class SpiderSaturationProcedure {
   public static void execute(Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double nutrition = 0.0;
         if (entity instanceof Villager || entity instanceof Player) {
            nutrition = 6.0;
         } else if (entity instanceof Horse || entity instanceof Mule || entity instanceof TraderLlama || entity instanceof Donkey || entity instanceof Llama) {
            nutrition = 8.0;
         } else if (entity instanceof Sheep) {
            nutrition = 4.0;
         } else if (entity instanceof Cow) {
            nutrition = 4.0;
         } else if (entity instanceof Pig) {
            nutrition = 3.0;
         } else if (entity instanceof Chicken) {
            nutrition = 2.0;
         } else if (entity instanceof Rabbit) {
            nutrition = 2.0;
         } else {
            nutrition = (double)Mth.m_216271_(RandomSource.m_216327_(), 2, 4);
         }

         sourceentity.getPersistentData()
            .m_128347_(
               "cocooncharge", sourceentity.getPersistentData().m_128459_("cocooncharge") + nutrition * (double)Mth.m_216271_(RandomSource.m_216327_(), 2, 3)
            );
         if (sourceentity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
            _entity.m_7292_(new MobEffectInstance(MobEffects.f_19605_, 100, 1));
         }
      }
   }
}
