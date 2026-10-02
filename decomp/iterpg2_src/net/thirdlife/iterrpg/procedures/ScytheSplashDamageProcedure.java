package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class ScytheSplashDamageProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
      if (sourceentity != null) {
         double splashdmg = 0.0;
         if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_wood")))) {
            splashdmg = 0.8;
         } else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_stone")))) {
            splashdmg = 1.0;
         } else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_iron")))) {
            splashdmg = 1.2;
         } else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_diamond")))) {
            splashdmg = 1.4;
         } else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_netherite")))) {
            splashdmg = 1.6;
         } else if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_)
            .m_204117_(ItemTags.create(new ResourceLocation("iter_rpg:bonus_damage_biome")))) {
            splashdmg = 1.25;
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(2.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
               && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
               && entityiterator instanceof LivingEntity
               && sourceentity != entityiterator) {
               entityiterator.m_6469_(DamageSource.f_19318_, (float)(splashdmg * 2.0));
               if (world instanceof ServerLevel _level) {
                  _level.m_8767_(
                     ParticleTypes.f_123766_,
                     entityiterator.m_20185_(),
                     y + (double)(entityiterator.m_20206_() / 2.0F),
                     entityiterator.m_20189_(),
                     1,
                     0.0,
                     0.0,
                     0.0,
                     0.0
                  );
               }
            }
         }
      }
   }
}
