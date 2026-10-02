package com.hollingsworth.arsnouveau.client.waila;

import com.hollingsworth.arsnouveau.common.block.tile.MobJarTile;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.Identifiers;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.impl.EntityAccessorImpl.Builder;

public enum MobJarComponent implements IBlockComponentProvider {
   INSTANCE;

   public void appendTooltip(ITooltip tooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
      if (blockAccessor.getBlockEntity() instanceof MobJarTile tile) {
         new Builder()
            .entity(tile.getEntity())
            .level(blockAccessor.getLevel())
            .serverConnected(blockAccessor.isServerConnected())
            .showDetails(blockAccessor.showDetails())
            .build()
            ._gatherComponents($ -> tooltip);
         tooltip.remove(Identifiers.CORE_MOD_NAME);
      }
   }

   public ResourceLocation getUid() {
      return new ResourceLocation("ars_nouveau", "mob_jar");
   }
}
