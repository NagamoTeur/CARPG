package net.cisco.procedures;

import net.cisco.entity.DescendedCiscoEntity;
import net.cisco.init.CiscoModModEntities;
import net.cisco.init.CiscoModModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

public class TalismanOfBetrayalRightclickedOnBlockProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if (entity.f_19853_.m_46472_() == Level.f_46428_ && world instanceof ServerLevel _level) {
            Entity entityToSpawn = new DescendedCiscoEntity((EntityType<DescendedCiscoEntity>)CiscoModModEntities.DESCENDED_CISCO.get(), _level);
            entityToSpawn.m_7678_(x, y + 1.0, z, world.m_213780_().m_188501_() * 360.0F, 0.0F);
            if (entityToSpawn instanceof Mob _mobToSpawn) {
               _mobToSpawn.m_6518_(_level, world.m_6436_(entityToSpawn.m_20183_()), MobSpawnType.MOB_SUMMONED, null, null);
            }

            world.m_7967_(entityToSpawn);
         }

         if ((entity instanceof Player _plr ? !_plr.m_150110_().f_35937_ : true) && entity instanceof Player _player) {
            ItemStack _stktoremove = new ItemStack((ItemLike)CiscoModModItems.TALISMAN_OF_BETRAYAL.get());
            _player.m_150109_().m_36022_(p -> _stktoremove.m_41720_() == p.m_41720_(), 1, _player.f_36095_.m_39730_());
         }
      }
   }
}
