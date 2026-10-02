package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.entity.IDispellable;
import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.item.inv.IInvProvider;
import com.hollingsworth.arsnouveau.api.item.inv.InventoryManager;
import com.hollingsworth.arsnouveau.api.particle.ParticleColorRegistry;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.spell.ILightable;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.api.util.BlockUtil;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.client.particle.GlowParticleData;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.client.particle.ParticleUtil;
import com.hollingsworth.arsnouveau.client.util.ColorPos;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.RitualBrazierBlock;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class RitualBrazierTile extends ModdedTile implements ITooltipProvider, IAnimatable, ILightable, ITickable, IInvProvider, IDispellable, IWandable {
   public AbstractRitual ritual;
   AnimationFactory manager = GeckoLibUtil.createFactory(this);
   public boolean isDecorative;
   public ParticleColor color = ParticleColor.defaultParticleColor();
   public boolean isOff;
   public BlockPos relayPos;

   public RitualBrazierTile(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
      super(tileEntityTypeIn, pos, state);
   }

   public RitualBrazierTile(BlockPos p, BlockState s) {
      super((BlockEntityType<?>)BlockRegistry.RITUAL_TILE.get(), p, s);
   }

   @Override
   public void onFinishedConnectionFirst(@Nullable BlockPos storedPos, @Nullable LivingEntity storedEntity, Player playerEntity) {
      if (storedPos != null && this.f_58857_.m_7702_(storedPos) instanceof BrazierRelayTile relayTile) {
         if (BlockUtil.distanceFrom(this.m_58899_(), storedPos) > 16.0) {
            PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.fail"));
            return;
         }

         this.relayPos = storedPos.m_7949_();
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.brazier_relay.connected"));
         this.updateBlock();
      }
   }

   @Override
   public void onWanded(Player playerEntity) {
      if (this.relayPos != null) {
         this.relayPos = null;
         PortUtil.sendMessage(playerEntity, Component.m_237115_("ars_nouveau.connections.cleared"));
         this.updateBlock();
      }
   }

   @Override
   public List<ColorPos> getWandHighlight(List<ColorPos> list) {
      if (this.relayPos != null) {
         list.add(ColorPos.centered(this.relayPos, ParticleColor.TO_HIGHLIGHT));
      }

      return list;
   }

   public void makeParticle(ParticleColor centerColor, ParticleColor outerColor, int intensity) {
      Level world = this.m_58904_();
      BlockPos pos = this.m_58899_();
      double xzOffset = 0.25;
      boolean isWeakFire = this.ritual != null && this.ritual.needsSourceNow();
      double centerYMax = isWeakFire ? 0.1 : 0.2;
      double outerYMax = isWeakFire ? 0.5 : 0.7;
      double ySpeed = isWeakFire ? 0.02F : 0.05F;
      intensity = isWeakFire ? intensity / 2 : intensity;

      for (int i = 0; i < intensity; i++) {
         world.m_7106_(
            GlowParticleData.createData(centerColor.transition((int)this.f_58857_.m_46467_() * 10)),
            (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
            (double)(pos.m_123342_() + 1) + ParticleUtil.inRange(-0.05, centerYMax),
            (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset / 2.0, xzOffset / 2.0),
            0.0,
            ParticleUtil.inRange(0.0, ySpeed),
            0.0
         );
      }

      for (int i = 0; i < intensity; i++) {
         world.m_7106_(
            GlowParticleData.createData(outerColor.transition((int)this.f_58857_.m_46467_() * 10)),
            (double)pos.m_123341_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
            (double)(pos.m_123342_() + 1) + ParticleUtil.inRange(0.0, outerYMax),
            (double)pos.m_123343_() + 0.5 + ParticleUtil.inRange(-xzOffset, xzOffset),
            0.0,
            ParticleUtil.inRange(0.0, ySpeed),
            0.0
         );
      }

      if (this.relayPos != null && this.f_58857_.m_7702_(this.relayPos) instanceof BrazierRelayTile relayTile) {
         relayTile.makeParticle(centerColor, outerColor, intensity);
      }
   }

   @Override
   public void tick() {
      if (this.isDecorative && this.f_58857_.f_46443_) {
         this.makeParticle(this.color.transition((int)this.f_58857_.m_46467_() * 20), this.color.transition((int)this.f_58857_.m_46467_() * 20 + 200), 10);
      } else {
         if (this.f_58857_.f_46443_ && this.ritual != null) {
            this.makeParticle(this.ritual.getCenterColor(), this.ritual.getOuterColor(), this.ritual.getParticleIntensity());
         }

         if (!this.isOff) {
            if (this.ritual != null) {
               if (this.ritual.getContext().isDone) {
                  this.ritual.onEnd();
                  this.ritual = null;
                  this.m_58904_().m_5594_(null, this.m_58899_(), SoundEvents.f_11937_, SoundSource.NEUTRAL, 1.0F, 1.0F);
                  this.m_58904_().m_7731_(this.m_58899_(), (BlockState)this.m_58904_().m_8055_(this.m_58899_()).m_61124_(RitualBrazierBlock.LIT, false), 3);
                  this.updateBlock();
                  return;
               }

               if (!this.ritual.isRunning() && !this.f_58857_.f_46443_ && this.f_58857_.m_46467_() % 5L == 0L) {
                  this.f_58857_.m_45976_(ItemEntity.class, new AABB(this.m_58899_()).m_82400_(1.0)).forEach(i -> this.tryBurnStack(i.m_32055_()));
               }

               if (this.ritual.consumesSource() && this.ritual.needsSourceNow()) {
                  int cost = this.ritual.getSourceCost();
                  if (SourceUtil.takeSourceWithParticles(this.m_58899_(), this.m_58904_(), 6, cost) == null) {
                     return;
                  }

                  this.ritual.setNeedsSource(false);
                  this.updateBlock();
               }

               if (this.relayPos != null && this.f_58857_.m_46749_(this.relayPos) && this.f_58857_.m_7702_(this.relayPos) instanceof BrazierRelayTile relayTile
                  )
                {
                  this.ritual.tryTick(relayTile);
                  relayTile.ticksToLightOff = 2;
                  relayTile.isDecorative = false;
               } else {
                  this.ritual.tryTick(this);
               }
            }
         }
      }
   }

   public boolean takeSource() {
      if (this.ritual.consumesSource() && this.ritual.needsSourceNow()) {
         int cost = this.ritual.getSourceCost();
         if (SourceUtil.takeSourceWithParticles(this.m_58899_(), this.m_58904_(), 6, cost) != null) {
            this.ritual.setNeedsSource(false);
            this.updateBlock();
            return true;
         }
      }

      return false;
   }

   public boolean tryBurnStack(ItemStack stack) {
      if (this.ritual != null && !this.ritual.isRunning() && !this.f_58857_.f_46443_ && this.ritual.canConsumeItem(stack)) {
         this.ritual.onItemConsumed(stack);
         ParticleUtil.spawnPoof((ServerLevel)this.f_58857_, this.m_58899_());
         this.f_58857_.m_5594_(null, this.m_58899_(), SoundEvents.f_11874_, SoundSource.NEUTRAL, 0.3F, 1.0F);
         return true;
      } else {
         return false;
      }
   }

   public boolean isRitualDone() {
      return this.ritual != null && this.ritual.getContext().isDone;
   }

   public boolean canRitualStart() {
      return this.ritual.canStart();
   }

   public void startRitual() {
      if (this.ritual != null && this.ritual.canStart() && !this.ritual.isRunning()) {
         this.m_58904_().m_5594_(null, this.m_58899_(), SoundEvents.f_12049_, SoundSource.NEUTRAL, 1.0F, 1.0F);
         this.ritual.onStart();
      }
   }

   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      String ritualIDString = tag.m_128461_("ritualID");
      if (!ritualIDString.isEmpty()) {
         ResourceLocation ritualID = new ResourceLocation(ritualIDString);
         this.ritual = ArsNouveauAPI.getInstance().getRitual(ritualID);
         if (this.ritual != null) {
            this.ritual.tile = this;
            this.ritual.read(tag);
         }
      } else {
         this.ritual = null;
      }

      this.color = ParticleColorRegistry.from(tag.m_128469_("color"));
      this.isDecorative = tag.m_128471_("decorative");
      this.isOff = tag.m_128471_("off");
      if (tag.m_128441_("relayPos")) {
         this.relayPos = BlockPos.m_122022_(tag.m_128454_("relayPos"));
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      if (this.ritual != null) {
         tag.m_128359_("ritualID", this.ritual.getRegistryName().toString());
         this.ritual.write(tag);
      } else {
         tag.m_128473_("ritualID");
      }

      tag.m_128365_("color", this.color.serialize());
      tag.m_128379_("decorative", this.isDecorative);
      tag.m_128379_("off", this.isOff);
      if (this.relayPos != null) {
         tag.m_128356_("relayPos", this.relayPos.m_121878_());
      }
   }

   public boolean canTakeAnotherRitual() {
      return this.ritual == null || this.ritual.isRunning();
   }

   public void setRitual(ResourceLocation selectedRitual) {
      this.ritual = ArsNouveauAPI.getInstance().getRitual(selectedRitual);
      if (this.ritual != null) {
         this.ritual.tile = this;
         Level world = this.m_58904_();
         BlockState state = world.m_8055_(this.m_58899_());
         world.m_7731_(this.m_58899_(), (BlockState)state.m_61124_(RitualBrazierBlock.LIT, true), 3);
      }

      this.isDecorative = false;
      this.f_58857_.m_5594_(null, this.m_58899_(), SoundEvents.f_11942_, SoundSource.NEUTRAL, 1.0F, 1.0F);
      this.updateBlock();
   }

   @Override
   public void getTooltip(List<Component> tooltips) {
      if (this.ritual != null) {
         tooltips.add(Component.m_237113_(this.ritual.getName()));
         if (this.isOff) {
            tooltips.add(Component.m_237115_("ars_nouveau.tooltip.turned_off").m_130940_(ChatFormatting.GOLD));
            return;
         }

         if (!this.ritual.isRunning()) {
            if (!this.ritual.canStart()) {
               tooltips.add(Component.m_237115_("ars_nouveau.tooltip.conditions_unmet").m_130940_(ChatFormatting.GOLD));
            } else {
               tooltips.add(Component.m_237115_("ars_nouveau.tooltip.waiting").m_130940_(ChatFormatting.GOLD));
            }
         } else {
            tooltips.add(Component.m_237115_("ars_nouveau.tooltip.running"));
         }

         if (!this.ritual.getConsumedItems().isEmpty()) {
            tooltips.add(Component.m_237115_("ars_nouveau.tooltip.consumed"));

            for (String i : this.ritual.getFormattedConsumedItems()) {
               tooltips.add(Component.m_237113_(i));
            }
         }

         if (this.ritual.needsSourceNow()) {
            tooltips.add(Component.m_237115_("ars_nouveau.wixie.need_mana").m_130940_(ChatFormatting.GOLD));
         }
      }
   }

   @Override
   public void registerControllers(AnimationData animationData) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.manager;
   }

   @Override
   public void onLight(HitResult rayTraceResult, Level world, LivingEntity shooter, SpellStats stats, SpellContext spellContext) {
      this.color = spellContext.getColors();
      this.isDecorative = true;
      BlockState state = world.m_8055_(this.m_58899_());
      world.m_7731_(this.m_58899_(), (BlockState)state.m_61124_(RitualBrazierBlock.LIT, true), 3);
      this.updateBlock();
   }

   @Override
   public boolean onDispel(@Nullable LivingEntity caster) {
      if (!this.isDecorative) {
         return false;
      } else {
         this.isDecorative = false;
         this.f_58857_.m_7731_(this.m_58899_(), (BlockState)this.f_58857_.m_8055_(this.m_58899_()).m_61124_(RitualBrazierBlock.LIT, false), 3);
         this.updateBlock();
         return true;
      }
   }

   @Override
   public InventoryManager getInventoryManager() {
      return InventoryManager.fromTile(this);
   }
}
