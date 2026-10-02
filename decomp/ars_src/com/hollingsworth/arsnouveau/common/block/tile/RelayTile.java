package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.source.AbstractSourceMachine;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.items.DominionWand;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class RelayTile extends AbstractSourceMachine implements ITooltipProvider, IWandable, IAnimatable, ITickable {
   private BlockPos toPos;
   private BlockPos fromPos;
   public boolean disabled;
   String TO = "to_";
   String FROM = "from";
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public RelayTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.ARCANE_RELAY_TILE, pos, state);
   }

   public RelayTile(BlockEntityType<?> type, BlockPos pos, BlockState state) {
      super(type, pos, state);
   }

   public BlockPos getToPos() {
      return this.toPos;
   }

   public void setToPos(BlockPos toPos) {
      this.toPos = toPos;
   }

   public BlockPos getFromPos() {
      return this.fromPos;
   }

   public void setFromPos(BlockPos fromPos) {
      this.fromPos = fromPos;
   }

   public boolean setTakeFrom(BlockPos pos) {
      if (!(BlockUtil.distanceFrom(pos, this.f_58858_) > (double)this.getMaxDistance()) && !pos.equals(this.m_58899_())) {
         this.fromPos = pos;
         this.updateBlock();
         return true;
      } else {
         return false;
      }
   }

   public boolean setSendTo(BlockPos pos) {
      if (!(BlockUtil.distanceFrom(pos, this.f_58858_) > (double)this.getMaxDistance())
         && !pos.equals(this.m_58899_())
         && this.f_58857_.m_7702_(pos) instanceof AbstractSourceMachine) {
         this.toPos = pos;
         this.updateBlock();
         return true;
      } else {
         return false;
      }
   }

   public int getMaxDistance() {
      return 30;
   }

   public void clearPos() {
      this.toPos = null;
      this.fromPos = null;
      this.updateBlock();
   }

   @Override
   public int getTransferRate() {
      return 1000;
   }

   @Override
   public int getMaxSource() {
      return 1000;
   }

   public boolean closeEnough(BlockPos pos) {
      return BlockUtil.distanceFrom(pos, this.f_58858_) <= (double)this.getMaxDistance() && !pos.equals(this.m_58899_());
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null
         && !this.f_58857_.f_46443_
         && !storedPos.equals(this.m_58899_())
         && this.f_58857_.m_7702_(storedPos) instanceof AbstractSourceMachine) {
         if (this.setSendTo(storedPos.m_7949_())) {
            PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.connections.send", new Object[]{DominionWand.getPosString(storedPos)}));
            ParticleUtil.beam(storedPos, this.f_58858_, this.f_58857_);
         } else {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.fail"));
         }
      }
   }

   @Override
   public void onFinishedConnectionLast(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null
         && !storedPos.equals(this.m_58899_())
         && !(this.f_58857_.m_7702_(storedPos) instanceof RelayTile)
         && this.f_58857_.m_7702_(storedPos) instanceof AbstractSourceMachine) {
         if (this.setTakeFrom(storedPos.m_7949_())) {
            PortUtil.sendMessage(playerEntity, Component.m_237110_("ars_nouveau.connections.take", new Object[]{DominionWand.getPosString(storedPos)}));
         } else {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.fail"));
         }
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.clearPos();
      PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.cleared"));
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      if (this.toPos != null) {
         list.add(ColorPos.centered(this.toPos, ParticleColor.TO_HIGHLIGHT));
      }

      if (this.fromPos != null) {
         list.add(ColorPos.centered(this.fromPos, ParticleColor.FROM_HIGHLIGHT));
      }

      return list;
   }

   @Override
   public void tick() {
      if (!this.f_58857_.f_46443_ && !this.disabled) {
         if (this.f_58857_.m_46467_() % 20L == 0L) {
            if (this.fromPos != null && this.f_58857_.m_46749_(this.fromPos)) {
               if (!(this.f_58857_.m_7702_(this.fromPos) instanceof AbstractSourceMachine)) {
                  this.fromPos = null;
                  this.updateBlock();
                  return;
               }

               if (this.f_58857_.m_7702_(this.fromPos) instanceof AbstractSourceMachine fromTile && this.transferSource(fromTile, this) > 0) {
                  this.updateBlock();
                  ParticleUtil.spawnFollowProjectile(this.f_58857_, this.fromPos, this.f_58858_);
               }
            }

            if (this.toPos != null && this.f_58857_.m_46749_(this.toPos)) {
               if (!(this.f_58857_.m_7702_(this.toPos) instanceof AbstractSourceMachine)) {
                  this.toPos = null;
                  this.updateBlock();
                  return;
               }

               AbstractSourceMachine toTile = (AbstractSourceMachine)this.f_58857_.m_7702_(this.toPos);
               if (this.transferSource(this, toTile) > 0) {
                  ParticleUtil.spawnFollowProjectile(this.f_58857_, this.f_58858_, this.toPos);
               }
            }
         }
      }
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.toPos = null;
      this.fromPos = null;
      if (NBTUtil.hasBlockPos(tag, this.TO)) {
         this.toPos = NBTUtil.getBlockPos(tag, this.TO);
      }

      if (NBTUtil.hasBlockPos(tag, this.FROM)) {
         this.fromPos = NBTUtil.getBlockPos(tag, this.FROM);
      }

      this.disabled = tag.m_128471_("disabled");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      if (this.toPos != null) {
         NBTUtil.storeBlockPos(tag, this.TO, this.toPos.m_7949_());
      } else {
         NBTUtil.removeBlockPos(tag, this.TO);
      }

      if (this.fromPos != null) {
         NBTUtil.storeBlockPos(tag, this.FROM, this.fromPos.m_7949_());
      } else {
         NBTUtil.removeBlockPos(tag, this.FROM);
      }

      tag.m_128379_("disabled", this.disabled);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (this.toPos == null) {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_to"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.relay.one_to", new Object[]{1}));
      }

      if (this.fromPos == null) {
         tooltip.add(Component.m_237115_("ars_nouveau.relay.no_from"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.relay.one_from", new Object[]{1}));
      }

      if (this.disabled) {
         tooltip.add(Component.m_237115_("ars_nouveau.tooltip.turned_off"));
      }
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
      event.getController().setAnimation(new AnimationBuilder().addAnimation("rotation"));
      return PlayState.CONTINUE;
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }
}
