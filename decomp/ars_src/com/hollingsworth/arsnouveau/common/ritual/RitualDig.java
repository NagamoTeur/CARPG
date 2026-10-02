package com.hollingsworth.arsnouveau.common.ritual;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.entity.EntityRitualProjectile;
import com.hollingsworth.arsnouveau.common.lib.RitualLib;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class RitualDig extends AbstractRitual {
   @Override
   public void onStart() {
      super.onStart();
      if (this.tile != null) {
         EntityRitualProjectile ritualProjectile = new EntityRitualProjectile(this.getWorld(), this.getPos().m_7494_());
         ritualProjectile.m_6034_(ritualProjectile.m_20185_() + 0.5, ritualProjectile.m_20186_(), ritualProjectile.m_20189_() + 0.5);
         ritualProjectile.tilePos = this.getPos();
         this.getWorld().m_7967_(ritualProjectile);
      }
   }

   public boolean canBlockBeHarvested(BlockPos pos) {
      return this.getWorld().m_8055_(pos).m_60800_(this.getWorld(), pos) >= 0.0F && SpellUtil.isCorrectHarvestLevel(5, this.getWorld().m_8055_(pos));
   }

   public void breakBlock(BlockPos pos) {
      if (this.canBlockBeHarvested(pos) && BlockUtil.destroyRespectsClaim(ANFakePlayer.getPlayer((ServerLevel)this.getWorld()), this.getWorld(), pos)) {
         BlockState state = this.getWorld().m_8055_(pos);
         ItemStack stack = new ItemStack(Items.f_42390_);
         state.m_60734_()
            .m_6240_(
               this.getWorld(), ANFakePlayer.getPlayer((ServerLevel)this.getWorld()), pos, this.getWorld().m_8055_(pos), this.getWorld().m_7702_(pos), stack
            );
         BlockUtil.destroyBlockSafely(this.getWorld(), pos, false, ANFakePlayer.getPlayer((ServerLevel)this.getWorld()));
      }
   }

   @Override
   public void tick() {
      Level world = this.tile.m_58904_();
      if (world.m_46467_() % 20L == 0L && !world.f_46443_) {
         BlockPos pos = this.tile.m_58899_().m_122012_().m_6625_(this.getContext().progress);
         if (world.m_151570_(pos)) {
            this.onEnd();
            return;
         }

         this.breakBlock(pos);
         this.breakBlock(pos.m_122019_().m_122019_());
         this.breakBlock(pos.m_122019_().m_122029_());
         this.breakBlock(pos.m_122019_().m_122024_());
         this.getContext().progress++;
      }
   }

   @Override
   public ParticleColor getCenterColor() {
      return new ParticleColor(this.rand.m_188503_(50), this.rand.m_188503_(255), this.rand.m_188503_(20));
   }

   @Override
   public String getLangDescription() {
      return "Digs four adjacent holes to bedrock, dropping any blocks.";
   }

   @Override
   public String getLangName() {
      return "Burrowing";
   }

   @Override
   public ResourceLocation getRegistryName() {
      return new ResourceLocation("ars_nouveau", RitualLib.DIG);
   }
}
