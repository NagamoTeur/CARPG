package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ISpellCasterProvider;
import com.hollingsworth.arsnouveau.api.spell.TurretSpellCaster;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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

public class BasicSpellTurretTile extends ModdedTile implements ITooltipProvider, IAnimatable, IAnimationListener, ITickable, ISpellCasterProvider {
   boolean playRecoil;
   public TurretSpellCaster spellCaster = new TurretSpellCaster(new CompoundTag());
   AnimationController castController;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public BasicSpellTurretTile(BlockEntityType<?> p_i48289_1_, BlockPos pos, BlockState state) {
      super(p_i48289_1_, pos, state);
   }

   public BasicSpellTurretTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.BASIC_SPELL_TURRET_TILE, pos, state);
   }

   public int getManaCost() {
      return this.spellCaster.getSpell().getDiscountedCost();
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      this.spellCaster.serializeOnTag(tag);
   }

   public void m_142466_(CompoundTag tag) {
      this.spellCaster = new TurretSpellCaster(tag);
      super.m_142466_(tag);
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      tooltip.add(Component.m_237115_("ars_nouveau.spell_turret.casting"));
      if (!this.spellCaster.getSpellName().isEmpty()) {
         tooltip.add(Component.m_237113_(this.spellCaster.getSpellName()));
      }

      tooltip.add(Component.m_237113_(this.spellCaster.getSpell().getDisplayString()));
   }

   public PlayState walkPredicate(AnimationEvent<?> event) {
      if (this.playRecoil) {
         event.getController().clearAnimationCache();
         event.getController().setAnimation(new AnimationBuilder().addAnimation("recoil"));
         this.playRecoil = false;
      }

      return PlayState.CONTINUE;
   }

   @Override
   public void registerControllers(AnimationData data) {
      this.castController = new AnimationController<>(this, "castController", 0.0F, this::walkPredicate);
      data.addAnimationController(this.castController);
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void startAnimation(int arg) {
      this.playRecoil = true;
   }

   @Override
   public ISpellCaster getSpellCaster() {
      return this.spellCaster;
   }

   @Override
   public ISpellCaster getSpellCaster(CompoundTag tag) {
      return this.spellCaster;
   }
}
