package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.entity.AfterImageEntity;
import net.cisco.entity.CiscoEntity;
import net.cisco.entity.DragonSeekerMissileEntity;
import net.cisco.init.CiscoModModEntities;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class AfterImageMovesProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity instanceof AfterImageEntity) {
            if ((entity instanceof Mob _mobEnt ? _mobEnt.m_5448_() : null) != null) {
               entity.getPersistentData().m_128347_("TPAF", entity.getPersistentData().m_128459_("TPAF") + 2.0);
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 20.0) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 260, 1));
               }

               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new DragonSeekerMissileEntity(
                     (EntityType<DragonSeekerMissileEntity>)CiscoModModEntities.DRAGON_SEEKER_MISSILE.get(), _level
                  );
                  entityToSpawn.m_7678_(x, y, z, 0.0F, 0.0F);
                  entityToSpawn.m_5618_(0.0F);
                  entityToSpawn.m_5616_(0.0F);
                  entityToSpawn.m_20334_(0.0, 0.0, 0.0);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 160.0 && entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
               _entity.m_7292_(new MobEffectInstance(MobEffects.f_19601_, 60, 1));
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 260.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(10.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiterator instanceof Player
                     && (double)(entityiterator instanceof LivingEntity _livEntx ? _livEntx.m_21223_() : -1.0F)
                        > (double)(entityiterator instanceof LivingEntity _livEnt ? _livEnt.m_21233_() : -1.0F) / 4.8) {
                     if (entityiterator instanceof LivingEntity _entity) {
                        _entity.m_21153_(
                           (entityiterator instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21223_() : -1.0F)
                              - (entityiterator instanceof LivingEntity _livEntxx ? _livEntxx.m_21233_() : -1.0F) / 10.0F
                        );
                     }

                     if (world instanceof ServerLevel _level) {
                        LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_level);
                        entityToSpawn.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                        entityToSpawn.m_20874_(true);
                        _level.m_7967_(entityToSpawn);
                     }
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 400.0) {
               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(10.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiteratorx instanceof CiscoEntity && entity instanceof LivingEntity) {
                     LivingEntity _entity = (LivingEntity)entity;
                     if (!_entity.f_19853_.m_5776_()) {
                        _entity.m_7292_(new MobEffectInstance(MobEffects.f_19601_, 60, 3));
                     }
                  }
               }
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 680.0) {
               if (entity instanceof LivingEntity _entity && !_entity.f_19853_.m_5776_()) {
                  _entity.m_7292_(new MobEffectInstance(MobEffects.f_19597_, 260, 1));
               }

               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new DragonSeekerMissileEntity(
                     (EntityType<DragonSeekerMissileEntity>)CiscoModModEntities.DRAGON_SEEKER_MISSILE.get(), _level
                  );
                  entityToSpawn.m_7678_(x, y, z, 0.0F, 0.0F);
                  entityToSpawn.m_5618_(0.0F);
                  entityToSpawn.m_5616_(0.0F);
                  entityToSpawn.m_20334_(0.0, 0.0, 0.0);
                  if (entityToSpawn instanceof Mob _mobToSpawn) {
                     _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
                  }

                  world.m_7967_(entityToSpawn);
               }
            }

            if (entity.getPersistentData().m_128459_("TPAF") == 800.0) {
               entity.getPersistentData().m_128347_("TPAF", 0.0);
            }
         }

         if (!CiscoModModVariables.MapVariables.get(world).IsBossAliveAndInBattle && !entity.f_19853_.m_5776_()) {
            entity.m_146870_();
         }
      }
   }
}
