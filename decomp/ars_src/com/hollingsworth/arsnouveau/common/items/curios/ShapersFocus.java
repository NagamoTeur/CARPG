package com.hollingsworth.arsnouveau.common.items.curios;

import com.hollingsworth.arsnouveau.api.item.ArsNouveauCurio;
import com.hollingsworth.arsnouveau.api.item.ISpellModifierItem;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.SpellStats;
import com.hollingsworth.arsnouveau.common.entity.EnchantedFallingBlock;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import javax.annotation.Nullable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class ShapersFocus extends ArsNouveauCurio implements ISpellModifierItem {
   public ShapersFocus(Properties properties) {
      super(properties);
   }

   public ShapersFocus() {
   }

   public static void tryPropagateEntitySpell(
      EnchantedFallingBlock fallingblockentity, Level level, LivingEntity shooter, SpellContext spellContext, SpellResolver resolver
   ) {
      if (resolver.hasFocus(ItemsRegistry.SHAPERS_FOCUS.get().m_7968_())) {
         SpellResolver newResolver = resolver.getNewResolver(spellContext.clone().withSpell(spellContext.getRemainingSpell()));
         newResolver.onResolveEffect(level, new EntityHitResult(fallingblockentity, fallingblockentity.f_19825_));
         spellContext.setCanceled(true);
      }
   }

   public static void tryPropagateBlockSpell(
      BlockHitResult blockHitResult, Level level, LivingEntity shooter, SpellContext spellContext, SpellResolver resolver
   ) {
      if (resolver.hasFocus(ItemsRegistry.SHAPERS_FOCUS.get().m_7968_())) {
         SpellResolver newResolver = resolver.getNewResolver(spellContext.clone().withSpell(spellContext.getRemainingSpell()));
         newResolver.onResolveEffect(level, blockHitResult);
         spellContext.setCanceled(true);
      }
   }

   @Override
   public SpellStats.Builder applyItemModifiers(
      ItemStack stack,
      SpellStats.Builder builder,
      AbstractSpellPart spellPart,
      HitResult rayTraceResult,
      Level world,
      @Nullable LivingEntity shooter,
      SpellContext spellContext
   ) {
      builder.addDamageModifier(1.0);
      return builder;
   }
}
