package net.cisco.procedures;

import java.util.Comparator;
import java.util.stream.Collectors;
import net.cisco.entity.SupremeNightfallAegisModeEntity;
import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModItems;
import net.cisco.network.CiscoModModVariables;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SupremeNightfallToolInInventoryTickProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() != CiscoModModItems.SUPREME_NIGHTFALL.get()) {
            if (!((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .founddefender) {
               if (world instanceof ServerLevel _level) {
                  Entity entityToSpawn = new SupremeNightfallAegisModeEntity(
                     (EntityType<SupremeNightfallAegisModeEntity>)CiscoModModEntities.SUPREME_NIGHTFALL_AEGIS_MODE.get(), _level
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

               Vec3 _center = new Vec3(x, y, z);

               for (Entity entityiterator : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
                  .stream()
                  .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
                  .collect(Collectors.toList())) {
                  if (entityiterator instanceof SupremeNightfallAegisModeEntity) {
                     if (entityiterator instanceof TamableAnimal) {
                        TamableAnimal _toTame = (TamableAnimal)entityiterator;
                        if (entity instanceof Player _owner) {
                           _toTame.m_21828_(_owner);
                        }
                     }

                     if (world instanceof Level) {
                        Level _level = (Level)world;
                        if (!_level.m_5776_()) {
                           _level.m_5594_(
                              null,
                              new BlockPos(x, y, z),
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                              SoundSource.NEUTRAL,
                              1.0F,
                              1.0F
                           );
                        } else {
                           _level.m_7785_(
                              x,
                              y,
                              z,
                              (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                              SoundSource.NEUTRAL,
                              1.0F,
                              1.0F,
                              false
                           );
                        }
                     }
                  }
               }

               boolean _setval = true;
               entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                  capability.founddefender = _setval;
                  capability.syncPlayerVariables(entity);
               });
            }
         } else if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41720_() == CiscoModModItems.SUPREME_NIGHTFALL.get()
            && ((CiscoModModVariables.PlayerVariables)entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null)
                  .orElse(new CiscoModModVariables.PlayerVariables()))
               .founddefender) {
            Vec3 _center = new Vec3(x, y, z);

            for (Entity entityiteratorx : world.m_6443_(Entity.class, new AABB(_center, _center).m_82400_(15.0), e -> true)
               .stream()
               .sorted(Comparator.comparingDouble(_entcnd -> _entcnd.m_20238_(_center)))
               .collect(Collectors.toList())) {
               if (entityiteratorx instanceof SupremeNightfallAegisModeEntity) {
                  if (world instanceof Level) {
                     Level _level = (Level)world;
                     if (!_level.m_5776_()) {
                        _level.m_5594_(
                           null,
                           new BlockPos(x, y, z),
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F
                        );
                     } else {
                        _level.m_7785_(
                           x,
                           y,
                           z,
                           (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")),
                           SoundSource.NEUTRAL,
                           1.0F,
                           1.0F,
                           false
                        );
                     }
                  }

                  if (!entityiteratorx.f_19853_.m_5776_()) {
                     entityiteratorx.m_146870_();
                  }
               }
            }

            boolean _setval = false;
            entity.getCapability(CiscoModModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
               capability.founddefender = _setval;
               capability.syncPlayerVariables(entity);
            });
         }
      }
   }
}
