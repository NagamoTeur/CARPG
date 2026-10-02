package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.CiscoModMod;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class AzureThunderRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         Vec3 _center = new Vec3(x, y, z);

         for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(10.0), e -> true)
            .stream()
            .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
            .collect(Collectors.toList())) {
            if (entityiterator != entity) {
               if (world instanceof Level) {
                  Level _level = (Level)world;
                  if (!_level.m_5776_()) {
                     _level.m_5594_(
                        null,
                        new BlockPos(x, y, z),
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:thunder")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F
                     );
                  } else {
                     _level.m_7785_(
                        x,
                        y,
                        z,
                        (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:thunder")),
                        SoundSource.NEUTRAL,
                        1.0F,
                        1.0F,
                        false
                     );
                  }
               }

               if (world instanceof ServerLevel _level) {
                  LightningBolt entityToSpawn = (LightningBolt)EntityType.f_20465_.m_20615_(_level);
                  entityToSpawn.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                  entityToSpawn.m_20874_(true);
                  _level.m_7967_(entityToSpawn);
               }

               entityiterator.m_6469_(DamageSource.f_19306_, 9.0F);
               if (entity instanceof LivingEntity) {
                  LivingEntity _entity = (LivingEntity)entity;
                  if (!_entity.f_19853_.m_5776_()) {
                     _entity.m_7292_(new MobEffectInstance(MobEffects.f_19596_, 150, 2, false, false));
                  }
               }

               CiscoModMod.queueServerWork(40, () -> {
                  if (world instanceof ServerLevel _level) {
                     LightningBolt entityToSpawnx = (LightningBolt)EntityType.f_20465_.m_20615_(_level);
                     entityToSpawnx.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                     entityToSpawnx.m_20874_(true);
                     _level.m_7967_(entityToSpawnx);
                  }

                  entityiterator.m_6469_(DamageSource.f_19306_, 9.0F);
                  CiscoModMod.queueServerWork(40, () -> {
                     if (world instanceof ServerLevel _levelx) {
                        LightningBolt entityToSpawnxx = (LightningBolt)EntityType.f_20465_.m_20615_(_levelx);
                        entityToSpawnxx.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                        entityToSpawnxx.m_20874_(true);
                        _levelx.m_7967_(entityToSpawnxx);
                     }

                     entityiterator.m_6469_(DamageSource.f_19306_, 9.0F);
                  });
                  CiscoModMod.queueServerWork(40, () -> {
                     if (world instanceof ServerLevel _levelx) {
                        LightningBolt entityToSpawnxx = (LightningBolt)EntityType.f_20465_.m_20615_(_levelx);
                        entityToSpawnxx.m_20219_(Vec3.m_82539_(new BlockPos(entityiterator.m_20185_(), entityiterator.m_20186_(), entityiterator.m_20189_())));
                        entityToSpawnxx.m_20874_(true);
                        _levelx.m_7967_(entityToSpawnxx);
                     }

                     entityiterator.m_6469_(DamageSource.f_19306_, 9.0F);
                  });
               });
               if (entity instanceof Player _player) {
                  _player.m_36335_().m_41524_(itemstack.m_41720_(), 260);
               }
            }
         }
      }
   }
}
