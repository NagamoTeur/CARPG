package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.RedstoneRelay;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class RedstoneRelayTile extends ModdedTile implements IWandable, ITooltipProvider, ITickable, IAnimatable {
   public List<BlockPos> poweredFrom = new ArrayList<>();
   public List<BlockPos> powering = new ArrayList<>();
   private int localPower;
   private int powerFromParentRelays;
   private int currentPower;
   @Nullable
   private BlockPos currentParent;
   boolean updateListeners;
   AnimationFactory animationFactory = GeckoLibUtil.createFactory(this);

   public RedstoneRelayTile(BlockPos pos, BlockState state) {
      super((BlockEntityType<?>)BlockRegistry.REDSTONE_RELAY_TILE.get(), pos, state);
   }

   public int getOutputPower() {
      return this.currentPower;
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_ && this.updateListeners) {
         this.calculateNewPower();
         this.updateListeners = false;
      }
   }

   public void onParentPowerChange(BlockPos pos, int newParentPower) {
      if (!this.poweredFrom.contains(pos)) {
         this.f_58857_.m_46672_(this.f_58858_, BlockRegistry.REDSTONE_RELAY.get());
      } else {
         if (pos.equals(this.currentParent)) {
            this.powerFromParentRelays = newParentPower;
            this.calculateNewPower();
         } else if (newParentPower > this.powerFromParentRelays) {
            this.currentParent = pos.m_7949_();
            this.powerFromParentRelays = newParentPower;
            this.calculateNewPower();
         }

         this.f_58857_.m_46672_(this.f_58858_, BlockRegistry.REDSTONE_RELAY.get());
      }
   }

   public void calculateNewPower() {
      if (!this.f_58857_.f_46443_) {
         int oldPower = this.currentPower;
         int newPower = this.localPower;

         for (BlockPos pos : this.poweredFrom) {
            if (this.f_58857_.m_46749_(pos)) {
               BlockEntity var6 = this.f_58857_.m_7702_(pos);
               if (var6 instanceof RedstoneRelayTile) {
                  RedstoneRelayTile redstoneRelayTile = (RedstoneRelayTile)var6;
                  if (redstoneRelayTile.getOutputPower() > this.localPower) {
                     newPower = redstoneRelayTile.getOutputPower();
                     this.currentParent = pos.m_7949_();
                     this.powerFromParentRelays = redstoneRelayTile.getOutputPower();
                  }
               }
            }
         }

         if (newPower != oldPower) {
            this.setNewPower(newPower);
         }
      }
   }

   protected void setNewPower(int power) {
      this.currentPower = power;
      BlockState var10000 = this.f_58857_.m_8055_(this.f_58858_);
      RedstoneRelay var10001 = BlockRegistry.REDSTONE_RELAY.get();
      if (var10000.m_61138_(RedstoneRelay.POWER)) {
         Level var2 = this.f_58857_;
         BlockPos var3 = this.f_58858_;
         BlockState var10002 = this.f_58857_.m_8055_(this.f_58858_);
         RedstoneRelay var10003 = BlockRegistry.REDSTONE_RELAY.get();
         var2.m_7731_(var3, (BlockState)var10002.m_61124_(RedstoneRelay.POWER, power), 3);
         this.updateBlock();
         this.f_58857_.m_46672_(this.f_58858_, BlockRegistry.REDSTONE_RELAY.get());
         this.updateListeners();
      }
   }

   @Override
   public boolean updateBlock() {
      return super.updateBlock();
   }

   public void updateListeners() {
      for (BlockPos pos : this.powering) {
         if (this.f_58857_.m_46749_(pos) && this.f_58857_.m_7702_(pos) instanceof RedstoneRelayTile redstoneRelayTile) {
            redstoneRelayTile.onParentPowerChange(this.f_58858_, this.currentPower);
         }
      }
   }

   public void onParentRemoved(BlockPos pos) {
      this.poweredFrom.remove(pos);
      this.updateBlock();
      if (this.currentParent != null && this.currentParent.equals(pos)) {
         this.calculateNewPower();
      }
   }

   public void m_7651_() {
      super.m_7651_();
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null && !this.f_58857_.f_46443_ && !storedPos.equals(this.m_58899_()) && this.f_58857_.m_7702_(storedPos) instanceof RedstoneRelayTile) {
         if (BlockUtil.distanceFrom(storedPos, this.f_58858_) <= (double)this.getMaxDistance()) {
            storedPos = storedPos.m_7949_();
            if (this.poweredFrom.contains(storedPos)) {
               this.poweredFrom.remove(storedPos);
            } else {
               this.poweredFrom.add(storedPos);
            }

            this.updateListeners = true;
            this.updateBlock();
         }
      }
   }

   public int getMaxDistance() {
      return 30;
   }

   @Override
   public void onFinishedConnectionFirst(
      @javax.annotation.Nullable BlockPos storedPos, @javax.annotation.Nullable LivingEntity storedEntity, Player playerEntity
   ) {
      if (storedPos != null && !this.f_58857_.f_46443_ && !storedPos.equals(this.m_58899_()) && this.f_58857_.m_7702_(storedPos) instanceof RedstoneRelayTile) {
         if (BlockUtil.distanceFrom(storedPos, this.f_58858_) <= (double)this.getMaxDistance()) {
            storedPos = storedPos.m_7949_();
            if (this.powering.contains(storedPos)) {
               PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.connections.remove", new Object[]{DominionWand.getPosString(storedPos)}));
               this.powering.remove(storedPos);
            } else {
               PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.connections.send", new Object[]{DominionWand.getPosString(storedPos)}));
               this.powering.add(storedPos);
               ParticleUtil.beam(storedPos, this.f_58858_, this.f_58857_);
            }

            this.updateListeners = true;
            this.updateBlock();
         } else {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.fail"));
         }
      }
   }

   public void m_142466_(CompoundTag pTag) {
      super.m_142466_(pTag);
      this.poweredFrom = new ArrayList<>();
      this.powering = new ArrayList<>();
      this.currentParent = null;
      ListTag listTag = pTag.m_128437_("poweredFrom", 10);

      for (int i = 0; i < listTag.size(); i++) {
         CompoundTag tag = listTag.m_128728_(i);
         this.poweredFrom.add(BlockPos.m_122022_(tag.m_128454_("pos")));
      }

      ListTag poweringTag = pTag.m_128437_("powering", 10);

      for (int i = 0; i < poweringTag.size(); i++) {
         CompoundTag tag = poweringTag.m_128728_(i);
         this.powering.add(BlockPos.m_122022_(tag.m_128454_("pos")));
      }

      this.localPower = pTag.m_128451_("localPower");
      this.currentPower = pTag.m_128451_("currentPower");
      this.powerFromParentRelays = pTag.m_128451_("powerFromParentRelays");
      if (pTag.m_128441_("currentParent")) {
         this.currentParent = BlockPos.m_122022_(pTag.m_128454_("currentParent"));
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      ListTag listTag = new ListTag();

      for (BlockPos pos : this.poweredFrom) {
         CompoundTag posTag = new CompoundTag();
         posTag.m_128356_("pos", pos.m_121878_());
         listTag.add(posTag);
      }

      tag.m_128365_("poweredFrom", listTag);
      ListTag poweringTag = new ListTag();

      for (BlockPos pos : this.powering) {
         CompoundTag posTag = new CompoundTag();
         posTag.m_128356_("pos", pos.m_121878_());
         poweringTag.add(posTag);
      }

      tag.m_128365_("powering", poweringTag);
      tag.m_128405_("localPower", this.localPower);
      tag.m_128405_("currentPower", this.currentPower);
      tag.m_128405_("powerFromParentRelays", this.powerFromParentRelays);
      if (this.currentParent != null) {
         tag.m_128356_("currentParent", this.currentParent.m_121878_());
      }
   }

   public int getLocalPower() {
      return this.localPower;
   }

   public void setLocalPower(int newLocalPower) {
      if (!this.f_58857_.f_46443_) {
         if (newLocalPower != this.localPower) {
            this.localPower = newLocalPower;
            this.calculateNewPower();
         }
      }
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      tooltip.add(Component.m_237113_("current power: " + this.currentPower));
      if (this.powering.isEmpty()) {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_to"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.relay.one_to", new Object[]{this.powering.size()}));
      }

      if (this.poweredFrom.isEmpty()) {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_from"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.powered_from", new Object[]{this.poweredFrom.size()}));
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.animationFactory;
   }

   @Override
   public void registerControllers(AnimationData data) {
      data.addAnimationController(new AnimationController<>(this, "rotate_controller", 0.0F, this::idlePredicate));
      data.addAnimationController(new AnimationController<>(this, "float_controller", 0.0F, this::floatPredicate));
   }

   private <P extends IAnimatable> PlayState idlePredicate(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("floating"));
      return PlayState.CONTINUE;
   }

   private <P extends IAnimatable> PlayState floatPredicate(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("rotating"));
      return PlayState.CONTINUE;
   }
}
