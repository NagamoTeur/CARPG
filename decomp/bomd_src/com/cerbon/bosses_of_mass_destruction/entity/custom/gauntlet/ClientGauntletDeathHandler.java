package com.cerbon.bosses_of_mass_destruction.entity.custom.gauntlet;

import com.cerbon.bosses_of_mass_destruction.entity.util.IEntityTick;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.level.Level;

public class ClientGauntletDeathHandler implements IEntityTick<Level> {
   private final GauntletEntity entity;

   public ClientGauntletDeathHandler(GauntletEntity entity) {
      this.entity = entity;
   }

   @Override
   public void tick(Level level) {
      this.entity.f_20919_++;
      if (this.entity.f_20919_ == 50) {
         this.entity.m_142687_(RemovalReason.KILLED);
      }
   }
}
