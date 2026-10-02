package net.cisco.procedures;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class SkysplitterRightclickedProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (world instanceof Level _level) {
            if (!_level.m_5776_()) {
               _level.m_5594_(
                  null,
                  new BlockPos(x, y, z),
                  (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:wind")),
                  SoundSource.NEUTRAL,
                  0.7F,
                  1.0F
               );
            } else {
               _level.m_7785_(
                  x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("cisco_mod:wind")), SoundSource.NEUTRAL, 0.7F, 1.0F, false
               );
            }
         }

         entity.m_20256_(new Vec3(entity.m_20184_().m_7096_() * 20.0, 2.0, entity.m_20184_().m_7094_() * 20.0));
         if (entity instanceof Player _player) {
            _player.m_36335_().m_41524_(itemstack.m_41720_(), 160);
         }
      }
   }
}
