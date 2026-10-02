package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.item.IWandable;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.common.spell.effect.EffectRedstone;
import com.hollingsworth.arsnouveau.common.spell.method.MethodProjectile;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.ars_nouveau.geckolib3.core.PlayState;
import software.bernie.ars_nouveau.geckolib3.core.builder.AnimationBuilder;
import software.bernie.ars_nouveau.geckolib3.core.controller.AnimationController;
import software.bernie.ars_nouveau.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;

public class TimerSpellTurretTile extends BasicSpellTurretTile implements IWandable {
   private int ticksPerSignal = 20;
   public boolean isLocked;
   public boolean isOff;
   public int ticksElapsed;

   public TimerSpellTurretTile(BlockEntityType<?> p_i48289_1_, BlockPos pos, BlockState state) {
      super(p_i48289_1_, pos, state);
   }

   public TimerSpellTurretTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.TIMER_SPELL_TURRET_TILE, pos, state);
   }

   @Override
   public void tick() {
      this.ticksElapsed++;
      if (!this.f_58857_.f_46443_ && this.ticksPerSignal > 0 && !this.isOff && this.ticksElapsed >= this.ticksPerSignal) {
         this.m_58900_().m_222963_((ServerLevel)this.f_58857_, this.m_58899_(), this.m_58904_().f_46441_);
         this.ticksElapsed = 0;
      }
   }

   @Override
   public int getManaCost() {
      int cost = super.getManaCost();
      Spell spell = this.getSpellCaster().getSpell();
      cost -= spell.getInstanceCount(MethodTouch.INSTANCE) * MethodTouch.INSTANCE.getCastingCost();
      cost -= spell.getInstanceCount(EffectRedstone.INSTANCE) * EffectRedstone.INSTANCE.getCastingCost();
      cost -= spell.getInstanceCount(MethodProjectile.INSTANCE) * MethodProjectile.INSTANCE.getCastingCost();
      return Math.max(0, cost);
   }

   @Override
   public void registerControllers(AnimationData data) {
      super.registerControllers(data);
      data.addAnimationController(new AnimationController<>(this, "spinController", 0.0F, this::spinPredicate));
   }

   public PlayState spinPredicate(AnimationEvent<?> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("key_rotation"));
      return PlayState.CONTINUE;
   }

   @Override
   public void onWanded(Player playerEntity) {
      this.isLocked = !this.isLocked;
      this.updateBlock();
   }

   public void addTime(int ticks) {
      this.ticksPerSignal += ticks;
      this.ticksPerSignal = Math.max(0, this.ticksPerSignal);
      this.ticksElapsed = 0;
      this.updateBlock();
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      super.getTooltip(tooltip);
      if (this.ticksPerSignal <= 0 && !this.isOff) {
         tooltip.add(Component.m_237115_("ars_nouveau.tooltip.turned_off"));
      } else {
         tooltip.add(Component.m_237110_("ars_nouveau.seconds", new Object[]{this.ticksPerSignal / 20}));
      }

      if (this.isOff) {
         tooltip.add(Component.m_237115_("ars_nouveau.tooltip.turned_off"));
      }

      if (this.isLocked) {
         tooltip.add(Component.m_237115_("ars_nouveau.locked"));
      }
   }

   @Override
   public void m_142466_(CompoundTag tag) {
      super.m_142466_(tag);
      this.isLocked = tag.m_128471_("locked");
      this.ticksPerSignal = tag.m_128451_("time");
      this.isOff = tag.m_128471_("off");
      this.ticksElapsed = tag.m_128451_("ticksElapsed");
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128379_("locked", this.isLocked);
      tag.m_128405_("time", this.ticksPerSignal);
      tag.m_128379_("off", this.isOff);
      tag.m_128405_("ticksElapsed", this.ticksElapsed);
   }
}
