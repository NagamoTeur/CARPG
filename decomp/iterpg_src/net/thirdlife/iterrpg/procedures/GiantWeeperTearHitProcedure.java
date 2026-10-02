package net.thirdlife.iterrpg.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.WeeperTearEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class GiantWeeperTearHitProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity immediatesourceentity) {
      if (immediatesourceentity != null) {
         if (world instanceof ServerLevel _level) {
            _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.WEEPER_TEAR_PARTICLE.get(), x, y, z, 16, 0.16, 0.16, 0.16, 0.16);
         }

         for (int index0 = 0; index0 < 12; index0++) {
            if (world instanceof ServerLevel projectileLevel) {
               Projectile _entityToSpawn = (new Object() {
                  public Projectile getArrow(Level level, float damage, int knockback, byte piercing) {
                     AbstractArrow entityToSpawn = new WeeperTearEntity((EntityType<? extends WeeperTearEntity>)IterRpgModEntities.WEEPER_TEAR.get(), level);
                     entityToSpawn.m_36781_((double)damage);
                     entityToSpawn.m_36735_(knockback);
                     entityToSpawn.m_20225_(true);
                     entityToSpawn.m_36767_(piercing);
                     return entityToSpawn;
                  }
               }).getArrow(projectileLevel, 2.0F, 0, (byte)5);
               _entityToSpawn.m_6034_(x, y, z);
               _entityToSpawn.m_6686_(
                  Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                  Mth.m_216263_(RandomSource.m_216327_(), -0.5, 1.0),
                  Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0),
                  (float)Mth.m_216263_(RandomSource.m_216327_(), 0.16, 1.0),
                  0.0F
               );
               projectileLevel.m_7967_(_entityToSpawn);
            }
         }

         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(3.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator instanceof LivingEntity) {
               if (entityiterator instanceof TamableAnimal) {
                  TamableAnimal _tamEnt = (TamableAnimal)entityiterator;
                  if (_tamEnt.m_21824_()) {
                     continue;
                  }
               }

               if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                  entityiterator.m_6469_(DamageSource.f_19318_, 4.0F);
               }
            }
         }

         if (!immediatesourceentity.f_19853_.m_5776_()) {
            immediatesourceentity.m_146870_();
         }
      }
   }
}
