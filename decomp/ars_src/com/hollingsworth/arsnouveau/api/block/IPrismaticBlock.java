package com.hollingsworth.arsnouveau.api.block;

import com.hollingsworth.arsnouveau.common.entity.EntityProjectileSpell;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

public interface IPrismaticBlock {
   void onHit(ServerLevel var1, BlockPos var2, EntityProjectileSpell var3);
}
