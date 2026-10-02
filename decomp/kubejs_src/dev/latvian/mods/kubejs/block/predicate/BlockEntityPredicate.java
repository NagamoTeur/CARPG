package dev.latvian.mods.kubejs.block.predicate;

import dev.architectury.registry.registries.Registries;
import dev.latvian.mods.kubejs.level.BlockContainerJS;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class BlockEntityPredicate implements BlockPredicate {
   private final ResourceLocation id;
   private BlockEntityPredicateDataCheck checkData;

   public BlockEntityPredicate(ResourceLocation i) {
      this.id = i;
   }

   public BlockEntityPredicate data(BlockEntityPredicateDataCheck cd) {
      this.checkData = cd;
      return this;
   }

   @Override
   public boolean check(BlockContainerJS block) {
      BlockEntity tileEntity = block.getEntity();
      return tileEntity != null
         && this.id.equals(Registries.getId(tileEntity.m_58903_(), Registry.f_122907_))
         && (this.checkData == null || this.checkData.checkData(block.getEntityData()));
   }

   @Override
   public String toString() {
      return "{entity=" + this.id + "}";
   }
}
