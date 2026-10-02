package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.CaltropThrownEntity;
import net.thirdlife.iterrpg.init.IterRpgModItems;

public class CaltropDamageProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity.getPersistentData().m_128459_("timer") >= (double)((entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) * 640.0F)) {
            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }

            if (Mth.m_216271_(RandomSource.m_216327_(), 1, 4) == 2) {
               if (world instanceof Level _level && !_level.m_5776_()) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.CALTROP.get()));
                  entityToSpawn.m_32010_(10);
                  _level.m_7967_(entityToSpawn);
               }
            } else if (Mth.m_216271_(RandomSource.m_216327_(), 1, 3) == 2 && world instanceof Level _level && !_level.m_5776_()) {
               ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(Items.f_42749_));
               entityToSpawn.m_32010_(10);
               _level.m_7967_(entityToSpawn);
            }
         } else {
            entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 1.0);
         }

         if (entity.getPersistentData().m_128459_("cooldown") > 0.0) {
            entity.getPersistentData().m_128347_("cooldown", entity.getPersistentData().m_128459_("cooldown") - 1.0);
         }

         if (entity.getPersistentData().m_128459_("timer") >= 32.0 && entity.getPersistentData().m_128459_("cooldown") == 0.0) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.125), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_hovering")))) {
                  entityiterator.m_6469_(
                     DamageSource.f_19314_, (float)(2.0 + 2.0 * Math.log10((double)(entityiterator.m_20205_() * entityiterator.m_20206_() + 12.0F)))
                  );
                  if (entityiterator instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entityiterator;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 24, 2, true, false));
                     }
                  }

                  entity.getPersistentData().m_128347_("timer", entity.getPersistentData().m_128459_("timer") + 640.0);
                  entity.getPersistentData().m_128347_("cooldown", 10.0);
               }
            }

            _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.1), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx != entity && entityiteratorx instanceof CaltropThrownEntity) {
                  entity.m_20256_(new Vec3((entity.m_20185_() - entityiteratorx.m_20185_()) / 8.0, 0.0, (entity.m_20189_() - entityiteratorx.m_20189_()) / 8.0));
               }
            }
         }
      }
   }
}
