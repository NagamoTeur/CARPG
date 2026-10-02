package net.thirdlife.iterrpg.procedures;

import com.mojang.util.UUIDTypeAdapter;
import java.util.Comparator;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.thirdlife.iterrpg.entity.CarcassEntity;
import net.thirdlife.iterrpg.entity.DemonsoulEntity;
import net.thirdlife.iterrpg.entity.DemonspineEntity;
import net.thirdlife.iterrpg.entity.RevenantEntity;
import net.thirdlife.iterrpg.init.IterRpgModParticleTypes;

public class DemonspinetickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         double damage = 0.0;
         if (entity.getPersistentData().m_128459_("ascend") > 0.0) {
            entity.getPersistentData().m_128347_("ascend", entity.getPersistentData().m_128459_("ascend") - 0.1);
            entity.m_6021_(x, y + entity.getPersistentData().m_128459_("ascend") * 0.02, z);
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_.m_9774_(x, y + entity.getPersistentData().m_128459_("ascend") * 0.02, z, entity.m_146908_(), entity.m_146909_());
            }
         }

         if (entity.getPersistentData().m_128459_("ascend") <= 3.0) {
            Vec3 _center = new Vec3(x, y + Mth.m_216263_(RandomSource.m_216327_(), -1.0, 1.0), z);

            for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(0.375), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entity.getPersistentData().m_128471_("friendly")) {
                  if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))) {
                     if (entityiterator instanceof TamableAnimal) {
                        TamableAnimal _tamEnt = (TamableAnimal)entityiterator;
                        if (_tamEnt.m_21824_()) {
                           continue;
                        }
                     }

                     if (entity != entityiterator
                        && !(entityiterator instanceof DemonspineEntity)
                        && entityiterator instanceof LivingEntity
                        && !entityiterator.m_20149_().equals(entity.getPersistentData().m_128461_("owner"))) {
                        entityiterator.m_6469_(
                           new EntityDamageSource(
                              "demonspine.player", world instanceof ServerLevel _serverLevelForGettingEntity ? (new Function<String, Entity>() {
                                 public Entity apply(String _uuidForEntity) {
                                    Entity _entityFromUUID = null;

                                    try {
                                       _entityFromUUID = _serverLevelForGettingEntity.m_8791_(UUIDTypeAdapter.fromString(_uuidForEntity));
                                    } catch (Exception var4) {
                                       _entityFromUUID = null;
                                    }

                                    return _entityFromUUID;
                                 }
                              }).apply(entity.getPersistentData().m_128461_("owner")) : null
                           ),
                           (float)entity.getPersistentData().m_128459_("damage")
                        );
                     }
                  }
               } else if (!entityiterator.m_6095_().m_204039_(TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("iter_rpg:entity_not_damage")))
                  && !(entityiterator instanceof WitherSkeleton)
                  && !(entityiterator instanceof Skeleton)
                  && !(entityiterator instanceof DemonsoulEntity)
                  && !(entityiterator instanceof DemonspineEntity)
                  && !(entityiterator instanceof CarcassEntity)
                  && !(entityiterator instanceof RevenantEntity)
                  && entity != entityiterator) {
                  entityiterator.m_6469_(new DamageSource("demonspine"), 3.0F);
               }
            }
         }

         if (entity.getPersistentData().m_128459_("lifetime") > (double)((entity instanceof LivingEntity _livEnt ? _livEnt.m_21223_() : -1.0F) * 24.0F)) {
            if (world instanceof ServerLevel _level) {
               _level.m_8767_((SimpleParticleType)IterRpgModParticleTypes.DEMONBLOOD.get(), x, y + 0.6, z, 32, 0.16, 0.5, 0.16, 0.025);
            }

            if (!entity.f_19853_.m_5776_()) {
               entity.m_146870_();
            }
         } else {
            entity.getPersistentData().m_128347_("lifetime", entity.getPersistentData().m_128459_("lifetime") + 1.0);
         }

         if (!world.m_8055_(new BlockPos(x, y - 0.01, z)).m_60783_(world, new BlockPos(x, y - 0.01, z), Direction.UP)
            && !world.m_8055_(new BlockPos(x, y + 0.6, z)).m_60783_(world, new BlockPos(x, y + 0.6, z), Direction.UP)) {
            entity.m_6021_(x, y - 0.02, z);
            if (entity instanceof ServerPlayer _serverPlayer) {
               _serverPlayer.f_8906_.m_9774_(x, y - 0.02, z, entity.m_146908_(), entity.m_146909_());
            }
         }
      }
   }
}
