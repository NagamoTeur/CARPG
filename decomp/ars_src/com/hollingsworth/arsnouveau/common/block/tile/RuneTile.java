package com.hollingsworth.arsnouveau.common.block.tile;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.client.ITooltipProvider;
import com.hollingsworth.arsnouveau.api.source.ISpecialSourceProvider;
import com.hollingsworth.arsnouveau.api.spell.EntitySpellResolver;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.RuneCaster;
import com.hollingsworth.arsnouveau.api.util.SourceUtil;
import com.hollingsworth.arsnouveau.common.block.ITickable;
import com.hollingsworth.arsnouveau.common.block.RuneBlock;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import com.hollingsworth.arsnouveau.common.spell.method.MethodTouch;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import java.util.List;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class RuneTile extends ModdedTile implements IAnimatable, ITickable, ITooltipProvider {
   public Spell spell = new Spell();
   public boolean isTemporary;
   public boolean disabled;
   public boolean isCharged;
   public boolean isSensitive;
   public int ticksUntilCharge;
   public UUID uuid;
   public Entity touchedEntity;
   AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public RuneTile(BlockPos pos, BlockState state) {
      super(BlockRegistry.RUNE_TILE, pos, state);
      this.isCharged = true;
      this.isTemporary = false;
      this.disabled = false;
      this.ticksUntilCharge = 0;
   }

   public void setSpell(Spell spell) {
      this.spell = spell;
      this.updateBlock();
   }

   public void castSpell(Entity entity) {
      if (entity != null) {
         if (this.isCharged && !this.spell.isEmpty() && this.f_58857_ instanceof ServerLevel && this.spell.recipe.get(0) instanceof MethodTouch) {
            if (this.isTemporary || !this.disabled) {
               try {
                  Player playerEntity = (Player)(this.uuid != null ? this.f_58857_.m_46003_(this.uuid) : ANFakePlayer.getPlayer((ServerLevel)this.f_58857_));
                  playerEntity = (Player)(this.isSensitive && playerEntity != null ? playerEntity : ANFakePlayer.getPlayer((ServerLevel)this.f_58857_));
                  EntitySpellResolver resolver = new EntitySpellResolver(
                     new SpellContext(entity.f_19853_, this.spell, playerEntity, new RuneCaster(this, SpellContext.CasterType.RUNE))
                  );
                  resolver.onCastOnEntity(ItemStack.f_41583_, entity, InteractionHand.MAIN_HAND);
                  if (this.isTemporary) {
                     this.f_58857_.m_46961_(this.f_58858_, false);
                     return;
                  }

                  this.isCharged = false;
                  this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61122_(RuneBlock.POWERED));
                  this.ticksUntilCharge = 40;
                  this.updateBlock();
               } catch (Exception var4) {
                  PortUtil.sendMessage(entity, Component.m_237115_("ars_nouveau.rune.error"));
                  var4.printStackTrace();
                  this.f_58857_.m_46961_(this.f_58858_, false);
               }
            }
         }
      }
   }

   @Override
   public void m_183515_(CompoundTag tag) {
      super.m_183515_(tag);
      tag.m_128365_("spell", this.spell.serialize());
      tag.m_128379_("charged", this.isCharged);
      tag.m_128379_("redstone", this.disabled);
      tag.m_128379_("temp", this.isTemporary);
      tag.m_128405_("cooldown", this.ticksUntilCharge);
      if (this.uuid != null) {
         tag.m_128362_("uuid", this.uuid);
      }

      tag.m_128379_("sensitive", this.isSensitive);
   }

   public void m_142466_(CompoundTag tag) {
      this.spell = Spell.fromTag(tag.m_128469_("spell"));
      this.isCharged = tag.m_128471_("charged");
      this.disabled = tag.m_128471_("redstone");
      this.isTemporary = tag.m_128471_("temp");
      this.ticksUntilCharge = tag.m_128451_("cooldown");
      if (tag.m_128441_("uuid")) {
         this.uuid = tag.m_128342_("uuid");
      }

      this.isSensitive = tag.m_128471_("sensitive");
      super.m_142466_(tag);
   }

   @Override
   public void tick() {
      if (this.f_58857_ != null) {
         if (!this.f_58857_.f_46443_ && this.ticksUntilCharge > 0) {
            this.ticksUntilCharge--;
         } else if (!this.isCharged) {
            if (!this.f_58857_.f_46443_ && this.isTemporary) {
               this.f_58857_.m_46961_(this.f_58858_, false);
            }

            if (!this.f_58857_.f_46443_) {
               ISpecialSourceProvider provider = SourceUtil.takeSourceWithParticles(this.f_58858_, this.f_58857_, 10, 100);
               if (provider != null) {
                  this.isCharged = true;
                  this.f_58857_.m_46597_(this.f_58858_, (BlockState)this.f_58857_.m_8055_(this.f_58858_).m_61122_(RuneBlock.POWERED));
                  this.updateBlock();
               } else {
                  this.ticksUntilCharge = 60;
                  this.updateBlock();
               }
            }
         }
      }
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void getTooltip(List<Component> tooltip) {
      if (ArsNouveau.proxy.getPlayer().m_21023_((MobEffect)ModPotions.MAGIC_FIND_EFFECT.get())) {
         tooltip.add(Component.m_237113_(this.spell.getDisplayString()));
      }
   }
}
