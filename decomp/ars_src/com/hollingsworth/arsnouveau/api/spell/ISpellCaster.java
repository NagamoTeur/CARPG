package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.ANFakePlayer;
import com.hollingsworth.arsnouveau.api.sound.ConfiguredSpellSound;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.IWrappedCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.LivingCaster;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.api.util.SpellUtil;
import com.hollingsworth.arsnouveau.client.particle.ParticleColor;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.datagen.BlockTagProvider;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentSensitive;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.jetbrains.annotations.NotNull;

public interface ISpellCaster {
   @NotNull
   Spell getSpell();

   @NotNull
   Spell getSpell(int var1);

   int getMaxSlots();

   int getCurrentSlot();

   void setCurrentSlot(int var1);

   default void setNextSlot() {
      int slot = this.getCurrentSlot() + 1;
      if (slot >= this.getMaxSlots()) {
         slot = 0;
      }

      this.setCurrentSlot(slot);
   }

   default void setPreviousSlot() {
      int slot = this.getCurrentSlot() - 1;
      if (slot < 0) {
         slot = this.getMaxSlots() - 1;
      }

      this.setCurrentSlot(slot);
   }

   void setSpell(Spell var1, int var2);

   void setSpell(Spell var1);

   void setSpellRecipe(List<AbstractSpellPart> var1, int var2);

   @NotNull
   ParticleColor getColor(int var1);

   @NotNull
   ParticleColor getColor();

   void setColor(ParticleColor var1);

   void setColor(ParticleColor var1, int var2);

   @NotNull
   ConfiguredSpellSound getSound(int var1);

   void setSound(ConfiguredSpellSound var1);

   void setSound(ConfiguredSpellSound var1, int var2);

   default ConfiguredSpellSound getCurrentSound() {
      return this.getSound(this.getCurrentSlot());
   }

   void setFlavorText(String var1);

   String getSpellName(int var1);

   String getSpellName();

   void setSpellName(String var1);

   void setSpellName(String var1, int var2);

   void setSpellHidden(boolean var1);

   boolean isSpellHidden();

   void setHiddenRecipe(String var1);

   String getHiddenRecipe();

   String getFlavorText();

   Map<Integer, Spell> getSpells();

   @Deprecated(
      forRemoval = true
   )
   @NotNull
   default Spell getSpell(Level world, Player playerEntity, InteractionHand hand, ISpellCaster caster) {
      return caster.getSpell();
   }

   @NotNull
   default Spell getSpell(Level world, LivingEntity playerEntity, InteractionHand hand, ISpellCaster caster) {
      return caster.getSpell();
   }

   default Spell modifySpellBeforeCasting(Level worldIn, @Nullable Entity playerIn, @Nullable InteractionHand handIn, Spell spell) {
      return spell;
   }

   default InteractionResultHolder<ItemStack> castSpell(
      Level worldIn, LivingEntity entity, InteractionHand handIn, @Nullable Component invalidMessage, @NotNull Spell spell
   ) {
      ItemStack stack = entity.m_21120_(handIn);
      if (worldIn.f_46443_) {
         return InteractionResultHolder.m_19098_(entity.m_21120_(handIn));
      } else {
         spell = this.modifySpellBeforeCasting(worldIn, entity, handIn, spell);
         if (!spell.isValid() && invalidMessage != null) {
            PortUtil.sendMessageNoSpam(entity, invalidMessage);
            return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
         } else {
            Player player = (Player)(entity instanceof Player thisPlayer ? thisPlayer : ANFakePlayer.getPlayer((ServerLevel)worldIn));
            IWrappedCaster wrappedCaster = (IWrappedCaster)(entity instanceof Player pCaster ? new PlayerCaster(pCaster) : new LivingCaster(entity));
            SpellResolver resolver = this.getSpellResolver(new SpellContext(worldIn, spell, entity, wrappedCaster, stack), worldIn, player, handIn);
            boolean isSensitive = resolver.spell.getBuffsAtIndex(0, entity, AugmentSensitive.INSTANCE) > 0;
            HitResult result = SpellUtil.rayTrace(entity, 0.5 + player.getReachDistance(), 0.0F, isSensitive);
            if (result instanceof BlockHitResult blockHit) {
               BlockEntity tile = worldIn.m_7702_(blockHit.m_82425_());
               if (tile instanceof ScribesTile) {
                  return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
               }

               if (!entity.m_6144_() && tile != null && !worldIn.m_8055_(blockHit.m_82425_()).m_204336_(BlockTagProvider.IGNORE_TILE)) {
                  return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
               }
            }

            if (result instanceof EntityHitResult entityHitResult && entityHitResult.m_82443_() instanceof LivingEntity) {
               if (resolver.onCastOnEntity(stack, entityHitResult.m_82443_(), handIn)) {
                  this.playSound(entity.m_20097_(), worldIn, entity, this.getCurrentSound(), SoundSource.PLAYERS);
               }

               return new InteractionResultHolder(InteractionResult.CONSUME, stack);
            }

            if (result instanceof BlockHitResult blockHitResult && (result.m_6662_() == Type.BLOCK || isSensitive)) {
               if (entity instanceof Player) {
                  UseOnContext context = new UseOnContext(player, handIn, (BlockHitResult)result);
                  if (resolver.onCastOnBlock(context)) {
                     this.playSound(entity.m_20097_(), worldIn, entity, this.getCurrentSound(), SoundSource.PLAYERS);
                  }
               } else if (resolver.onCastOnBlock(blockHitResult)) {
                  this.playSound(entity.m_20097_(), worldIn, entity, this.getCurrentSound(), SoundSource.NEUTRAL);
               }

               return new InteractionResultHolder(InteractionResult.CONSUME, stack);
            }

            if (resolver.onCast(stack, worldIn)) {
               this.playSound(entity.m_20097_(), worldIn, entity, this.getCurrentSound(), SoundSource.PLAYERS);
            }

            return new InteractionResultHolder(InteractionResult.CONSUME, stack);
         }
      }
   }

   @Deprecated(
      forRemoval = true
   )
   default InteractionResultHolder<ItemStack> castSpell(
      Level worldIn, Player playerIn, InteractionHand handIn, @Nullable Component invalidMessage, @NotNull Spell spell
   ) {
      return this.castSpell(worldIn, (LivingEntity)playerIn, handIn, invalidMessage, spell);
   }

   default InteractionResultHolder<ItemStack> castSpell(Level worldIn, LivingEntity playerIn, InteractionHand handIn, Component invalidMessage) {
      return this.castSpell(worldIn, playerIn, handIn, invalidMessage, this.getSpell(worldIn, playerIn, handIn, this));
   }

   @Deprecated(
      forRemoval = true
   )
   default InteractionResultHolder<ItemStack> castSpell(Level worldIn, Player playerIn, InteractionHand handIn, Component invalidMessage) {
      return this.castSpell(worldIn, playerIn, handIn, invalidMessage, this.getSpell(worldIn, playerIn, handIn, this));
   }

   default void copyFromCaster(ISpellCaster other) {
      for (int i = 0; i < this.getMaxSlots() && i < other.getMaxSlots(); i++) {
         this.setSpell(other.getSpell(i), i);
         this.setFlavorText(other.getFlavorText());
      }
   }

   default SpellResolver getSpellResolver(SpellContext context, Level worldIn, LivingEntity playerIn, InteractionHand handIn) {
      return new SpellResolver(context);
   }

   @Deprecated(
      forRemoval = true
   )
   default SpellResolver getSpellResolver(SpellContext context, Level worldIn, Player playerIn, InteractionHand handIn) {
      return this.getSpellResolver(context, worldIn, (LivingEntity)playerIn, handIn);
   }

   default void playSound(BlockPos pos, Level worldIn, @Nullable Entity playerIn, ConfiguredSpellSound configuredSound, SoundSource source) {
      if (configuredSound != null
         && configuredSound.sound != null
         && configuredSound.sound.getSoundEvent() != null
         && !configuredSound.equals(ConfiguredSpellSound.EMPTY)) {
         worldIn.m_6263_(
            null,
            (double)pos.m_123341_() + 0.5,
            (double)pos.m_123342_() + 0.5,
            (double)pos.m_123343_() + 0.5,
            configuredSound.sound.getSoundEvent(),
            source,
            configuredSound.volume,
            configuredSound.pitch
         );
      }
   }

   ResourceLocation getTagID();
}
