package com.hollingsworth.arsnouveau.common.event;

import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import com.hollingsworth.arsnouveau.common.mixin.jar.MobAccessorMixin;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.event.level.NoteBlockEvent.Play;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
   modid = "ars_nouveau"
)
public class JarEvents {
   @SubscribeEvent
   public static void onNoteblock(Play e) {
      if (!e.getLevel().m_5776_()) {
         if (e.getLevel().m_7702_(e.getPos().m_7495_()) instanceof MobJarTile mobJarTile) {
            LevelAccessor level = e.getLevel();
            RandomSource random = level.m_213780_();
            if (mobJarTile.getEntity() instanceof MobAccessorMixin mob) {
               SoundEvent soundEvent = mob.callGetAmbientSound();
               if (soundEvent == null) {
                  return;
               }

               e.getLevel().m_5594_(null, e.getPos(), soundEvent, SoundSource.BLOCKS, 1.0F, 1.0F);
               e.setCanceled(true);
            } else if (mobJarTile.getEntity() instanceof LightningBolt bolt) {
               e.getLevel().m_5594_(null, e.getPos(), SoundEvents.f_12090_, SoundSource.BLOCKS, 10000.0F, 0.8F + random.m_188501_() * 0.2F);
               e.getLevel().m_5594_(null, e.getPos(), SoundEvents.f_12089_, SoundSource.BLOCKS, 2.0F, random.m_188501_() * 0.2F);
            }
         }
      }
   }

   @SubscribeEvent
   public static void onRide(EntityMountEvent mountEvent) {
      if (!mountEvent.isDismounting()) {
         if (!mountEvent.getLevel().f_46443_) {
            if (mountEvent.getEntityMounting() instanceof Player) {
               Entity beingMounted = mountEvent.getEntityBeingMounted();
               if (mountEvent.getLevel().m_46749_(beingMounted.m_20097_()) && mountEvent.getLevel().m_7702_(beingMounted.m_20097_()) instanceof MobJarTile) {
                  mountEvent.setCanceled(true);
               }
            }
         }
      }
   }
}
