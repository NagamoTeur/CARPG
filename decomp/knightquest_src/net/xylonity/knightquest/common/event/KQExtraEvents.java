package net.xylonity.knightquest.common.event;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.level.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.xylonity.knightquest.common.entity.entities.SamhainEntity;
import net.xylonity.knightquest.registry.KnightQuestEntities;

@EventBusSubscriber(
   modid = "knightquest"
)
public class KQExtraEvents {
   @SubscribeEvent
   public static void entitySamhainSpawnHandler(EntityPlaceEvent event) {
      Level level = (Level)event.getLevel();
      BlockPos pos = event.getPos();
      Block block = event.getPlacedBlock().m_60734_();
      if (block == Blocks.f_50144_ && level.m_8055_(pos.m_7495_()).m_60734_() == Blocks.f_50074_) {
         SamhainEntity samhain = new SamhainEntity((EntityType<? extends TamableAnimal>)KnightQuestEntities.SAMHAIN.get(), level);
         samhain.m_7678_((double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() - 1), (double)pos.m_123343_() + 0.5, level.f_46441_.m_188501_() * 360.0F, 0.0F);
         level.m_46961_(pos, false);
         level.m_46961_(pos.m_7495_(), false);
         level.m_5594_(null, pos, SoundEvents.f_215671_, SoundSource.BLOCKS, 1.0F, 1.0F);
         level.m_7967_(samhain);
      }
   }
}
