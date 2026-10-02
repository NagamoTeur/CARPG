package net.thirdlife.iterrpg.procedures;

import javax.annotation.Nullable;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.thirdlife.iterrpg.init.IterRpgModItems;

@EventBusSubscriber
public class GeodeDropProcedure {
   @SubscribeEvent
   public static void onEntityDeath(LivingDeathEvent event) {
      if (event != null && event.getEntity() != null) {
         execute(
            event,
            event.getEntity().f_19853_,
            event.getEntity().m_20185_(),
            event.getEntity().m_20186_(),
            event.getEntity().m_20189_(),
            event.getEntity(),
            event.getSource().m_7639_()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      execute(null, world, x, y, z, entity, sourceentity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
      if (entity != null && sourceentity != null) {
         double luck = 0.0;
         if (sourceentity instanceof Player && entity instanceof Monster && GeodesConfigConditionProcedure.execute()) {
            luck = (double)Mth.m_216271_(RandomSource.m_216327_(), 1, 512);
            if (luck == 64.0) {
               if ((world instanceof Level _lvl ? _lvl.m_46472_() : Level.f_46428_) == Level.f_46428_) {
                  if (Mth.m_216271_(RandomSource.m_216327_(), 1, 3) == 2) {
                     if (world instanceof Level _level && !_level.m_5776_()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.DEEPSLATE_GEODE.get()));
                        entityToSpawn.m_32010_(10);
                        _level.m_7967_(entityToSpawn);
                     }
                  } else if (world instanceof Level _level && !_level.m_5776_()) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.STONE_GEODE.get()));
                     entityToSpawn.m_32010_(10);
                     _level.m_7967_(entityToSpawn);
                  }
               } else if ((world instanceof Level _lvlx ? _lvlx.m_46472_() : Level.f_46428_) == Level.f_46429_) {
                  if (Mth.m_216271_(RandomSource.m_216327_(), 1, 3) == 2) {
                     if (world instanceof Level _level && !_level.m_5776_()) {
                        ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.BLACKSTONE_GEODE.get()));
                        entityToSpawn.m_32010_(10);
                        _level.m_7967_(entityToSpawn);
                     }
                  } else if (world instanceof Level _level && !_level.m_5776_()) {
                     ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.NETHERRACK_GEODE.get()));
                     entityToSpawn.m_32010_(10);
                     _level.m_7967_(entityToSpawn);
                  }
               } else if ((world instanceof Level _lvlxx ? _lvlxx.m_46472_() : Level.f_46428_) == Level.f_46430_
                  && world instanceof Level _level
                  && !_level.m_5776_()) {
                  ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack((ItemLike)IterRpgModItems.ENDSTONE_GEODE.get()));
                  entityToSpawn.m_32010_(10);
                  _level.m_7967_(entityToSpawn);
               }
            }
         }
      }
   }
}
