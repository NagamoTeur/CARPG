package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.source.SourcelinkEventQueue;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.Event;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class SourcelinkTile extends AbstractSourceMachine implements IAnimatable, ITickable {
   int progress;
   public boolean isDisabled = false;
   public boolean registered = false;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public SourcelinkTile(BlockEntityType<?> sourceLinkTile, BlockPos pos, BlockState state) {
      super(sourceLinkTile, pos, state);
   }

   @Override
   public int getTransferRate() {
      return 1000;
   }

   @Override
   public int getMaxSource() {
      return 1000;
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_) {
         if (this.f_58857_.m_46467_() % 120L == 0L && this.usesEventQueue()) {
            SourcelinkEventQueue.addPosition(this.f_58857_, this.f_58858_);
            this.registered = true;
         }

         if (this.f_58857_.m_46467_() % 100L == 0L && this.getSource() > 0) {
            List<ISpecialSourceProvider> providers = SourceUtil.canGiveSource(this.f_58858_, this.f_58857_, 5);
            if (!providers.isEmpty()) {
               this.transferSource(this, providers.get(0).getSource());
               ParticleUtil.spawnFollowProjectile(this.f_58857_, this.f_58858_, providers.get(0).getCurrentPos());
            }
         }
      }
   }

   public List<ArcanePedestalTile> getSurroundingPedestals() {
      List<ArcanePedestalTile> inventories = new ArrayList<>();

      for (BlockPos p : BlockPos.m_121940_(this.m_58899_().m_7495_().m_122029_().m_122012_(), this.m_58899_().m_7494_().m_122024_().m_122019_())) {
         if (this.f_58857_.m_7702_(p) instanceof ArcanePedestalTile pedestal) {
            inventories.add(pedestal);
         }
      }

      return inventories;
   }

   public void getManaEvent(BlockPos sourcePos, int total) {
      this.addSource(total);
      ParticleUtil.spawnFollowProjectile(this.f_58857_, sourcePos, this.f_58858_);
   }

   public boolean eventInRange(BlockPos sourcePos, @Nullable Event event) {
      return BlockUtil.distanceFrom(this.f_58858_, sourcePos) <= 15.0;
   }

   public boolean usesEventQueue() {
      return false;
   }

   public void doRandomAction() {
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "rotate_controller", 0.0F, this::idlePredicate));
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.progress = tag.m_128451_("progress");
      this.isDisabled = tag.m_128471_("disabled");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128405_("progress", this.progress);
      tag.m_128379_("disabled", this.isDisabled);
   }

   private <E extends BlockEntity & IAnimatable> PlayState idlePredicate(AnimationEvent<E> event) {
      if (this.isDisabled) {
         return PlayState.STOP;
      } else {
         event.getController().setAnimation(new AnimationBuilder().addAnimation("rotation"));
         return PlayState.CONTINUE;
      }
   }
}
