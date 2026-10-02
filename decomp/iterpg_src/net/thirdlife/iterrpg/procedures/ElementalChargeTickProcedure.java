package net.thirdlife.iterrpg.procedures;

import com.mojang.util.UUIDTypeAdapter;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class ElementalChargeTickProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         double pitch = 0.0;
         double yaw = 0.0;
         double pitch_off = 0.0;
         double yaw_off = 0.0;
         double xdec = 0.0;
         double ydec = 0.0;
         double zdec = 0.0;
         double repeat = 0.0;
         double dist = 0.0;
         double splashdmg = 0.0;
         double decide = 0.0;
         if (!(entity.getPersistentData().m_128459_("age") >= entity.getPersistentData().m_128459_("maxAge"))
            && (entity instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F) == (entity instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F)
            && !entity.getPersistentData().m_128471_("die")) {
            entity.getPersistentData().m_128347_("age", entity.getPersistentData().m_128459_("age") + 1.0);
            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_PARTICLE.get(),
                  entity.m_20185_(),
                  entity.m_20186_(),
                  entity.m_20189_(),
                  4,
                  0.01,
                  0.01,
                  0.01,
                  0.02
               );
            }

            entity.m_6021_(
               entity.m_20185_() + entity.getPersistentData().m_128459_("vx") * entity.getPersistentData().m_128459_("velocity"),
               entity.m_20186_() + entity.getPersistentData().m_128459_("vy") * entity.getPersistentData().m_128459_("velocity") - 0.0016,
               entity.m_20189_() + entity.getPersistentData().m_128459_("vz") * entity.getPersistentData().m_128459_("velocity")
            );
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_
                  .m_9774_(
                     entity.m_20185_() + entity.getPersistentData().m_128459_("vx") * entity.getPersistentData().m_128459_("velocity"),
                     entity.m_20186_() + entity.getPersistentData().m_128459_("vy") * entity.getPersistentData().m_128459_("velocity") - 0.0016,
                     entity.m_20189_() + entity.getPersistentData().m_128459_("vz") * entity.getPersistentData().m_128459_("velocity"),
                     entity.m_146908_(),
                     entity.m_146909_()
                  );
            }

            Vec3 _center = new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.32), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if ((!(entityiterator instanceof TamableAnimal _tamEnt) || !_tamEnt.m_21824_())
                  && !entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && entityiterator instanceof LivingEntity
                  && entity != entityiterator
                  && !entityiterator.m_20149_().equals(entity.getPersistentData().m_128461_("owner"))) {
                  entityiterator.m_6469_(
                     new EntityDamageSource("elemental.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
                        public Entity apply(String _uuidForEntity) {
                           Entity _entityFromUUID = null;

                           try {
                              _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
                           } catch (Exception var4) {
                              _entityFromUUID = null;
                           }

                           return _entityFromUUID;
                        }
                     }).apply(entity.getPersistentData().m_128461_("owner")) : null), (float)entity.getPersistentData().m_128459_("damage")
                  );
                  if (!entity.getPersistentData().m_128471_("pierce")) {
                     entity.getPersistentData().m_128379_("die", true);
                  }
               }
            }
         } else {
            Vec3 _center = new Vec3(entity.m_20185_(), entity.m_20186_(), entity.m_20189_());

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(1.6), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if ((!(entityiteratorx instanceof TamableAnimal _tamEntx) || !_tamEntx.m_21824_())
                  && !entityiteratorx.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && entityiteratorx instanceof LivingEntity
                  && entity != entityiteratorx
                  && !entityiteratorx.m_20149_().equals(entity.getPersistentData().m_128461_("owner"))) {
                  entityiteratorx.m_6469_(
                     new EntityDamageSource("generic.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
                        public Entity apply(String _uuidForEntity) {
                           Entity _entityFromUUID = null;

                           try {
                              _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
                           } catch (Exception var4) {
                              _entityFromUUID = null;
                           }

                           return _entityFromUUID;
                        }
                     }).apply(entity.getPersistentData().m_128461_("owner")) : null), (float)(entity.getPersistentData().m_128459_("damage") / 2.0)
                  );
                  if (world instanceof ServerLevel _level) {
                     _level.m_8767_(
                        (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_PARTICLE.get(),
                        entityiteratorx.m_20185_(),
                        entityiteratorx.m_20186_(),
                        entityiteratorx.m_20189_(),
                        8,
                        (double)entityiteratorx.m_20205_() / 3.5,
                        (double)entityiteratorx.m_20206_() / 3.5,
                        (double)entityiteratorx.m_20205_() / 3.5,
                        0.08
                     );
                  }

                  decide = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 4);
                  if (decide == 1.0) {
                     if (entityiteratorx instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19614_, 64, 0, false, false));
                     }
                  } else if (decide == 2.0) {
                     if (entityiteratorx instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 64, 0, false, false));
                     }
                  } else if (decide == 3.0) {
                     if (entityiteratorx instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19613_, 64, 0, false, false));
                     }
                  } else if (decide == 4.0 && entityiteratorx instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19610_, 64, 0, false, false));
                  }
               }
            }

            if (world instanceof ServerLevel _level) {
               _level.m_8767_(
                  (SimpleParticleType)IterRpgModParticleTypes.ELEMENTAL_PARTICLE.get(),
                  entity.m_20185_(),
                  entity.m_20186_(),
                  entity.m_20189_(),
                  16,
                  0.08,
                  0.08,
                  0.08,
                  0.12
               );
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         }
      }
   }
}
