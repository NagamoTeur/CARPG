package com.github.L_Ender.cataclysm.mixin;

import com.min01.archaeology.misc.StructureBlockInfoAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({StructureBlockInfo.class})
public class StructureBlockInfoMixin implements StructureBlockInfoAccess {
   @Final
   @Shadow
   @Mutable
   public CompoundTag f_74677_;

   @Override
   public void archeology$addLootTable(String tag, String value) {
      if (this.f_74677_ == null) {
         this.f_74677_ = new CompoundTag();
      }

      this.f_74677_.m_128359_(tag, value);
   }
}
