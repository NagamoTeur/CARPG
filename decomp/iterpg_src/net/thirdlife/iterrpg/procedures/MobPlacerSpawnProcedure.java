package net.thirdlife.iterrpg.procedures;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.thirdlife.iterrpg.entity.DebugMobmorphEntity;
import net.thirdlife.iterrpg.init.IterRpgModEntities;

public class MobPlacerSpawnProcedure {
   public static void execute(LevelAccessor world, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 0.0) {
            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new DebugMobmorphEntity(
                  (EntityType<DebugMobmorphEntity>)IterRpgModEntities.DEBUG_MOBMORPH.get(), _serverLevelForEntitySpawn
               );
               _entityForSpawning.m_7678_(
                  (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("xcord"),
                  (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("ycord"),
                  (entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("zcord"),
                  world.m_213780_().m_188501_() * 360.0F,
                  0.0F
               );
               _entityForSpawning.getPersistentData().m_128359_("TurnInto", "goblin");
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         } else if ((entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 1.0) {
            if (world instanceof ServerLevel _serverLevelForEntitySpawn) {
               Entity _entityForSpawning = new DebugMobmorphEntity(
                  (EntityType<DebugMobmorphEntity>)IterRpgModEntities.DEBUG_MOBMORPH.get(), _serverLevelForEntitySpawn
               );
               _entityForSpawning.m_7678_(
                  (entity instanceof LivingEntity _livEntxxxx ? _livEntxxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("xcord"),
                  (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("ycord"),
                  (entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("zcord"),
                  world.m_213780_().m_188501_() * 360.0F,
                  0.0F
               );
               _entityForSpawning.getPersistentData().m_128359_("TurnInto", "goblin_warrior");
               if (_entityForSpawning instanceof Mob _mobForSpawning) {
                  _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
               }

               world.m_7967_(_entityForSpawning);
            }
         } else if ((entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("select") == 2.0
            && world instanceof ServerLevel _serverLevelForEntitySpawn) {
            Entity _entityForSpawning = new DebugMobmorphEntity(
               (EntityType<DebugMobmorphEntity>)IterRpgModEntities.DEBUG_MOBMORPH.get(), _serverLevelForEntitySpawn
            );
            _entityForSpawning.m_7678_(
               (entity instanceof LivingEntity _livEntxxxxx ? _livEntxxxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("xcord"),
               (entity instanceof LivingEntity _livEntxxxx ? _livEntxxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("ycord"),
               (entity instanceof LivingEntity _livEntxxx ? _livEntxxx.m_21205_() : ItemStack.f_41583_).m_41784_().m_128459_("zcord"),
               world.m_213780_().m_188501_() * 360.0F,
               0.0F
            );
            _entityForSpawning.getPersistentData().m_128359_("TurnInto", "hobgoblin");
            if (_entityForSpawning instanceof Mob _mobForSpawning) {
               _mobForSpawning.m_6518_(_serverLevelForEntitySpawn, world.m_6436_(_entityForSpawning.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(_entityForSpawning);
         }

         if (entity instanceof Player _player) {
            _player.m_6915_();
         }
      }
   }
}
