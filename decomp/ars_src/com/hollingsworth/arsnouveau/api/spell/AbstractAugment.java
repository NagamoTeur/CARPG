package com.hollingsworth.arsnouveau.api.spell;

import com.hollingsworth.arsnouveau.api.item.ISpellModifier;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAugment extends AbstractSpellPart implements ISpellModifier {
   public AbstractAugment(String tag, String description) {
      super(tag, description);
   }

   public AbstractAugment(ResourceLocation tag, String description) {
      super(tag, description);
   }

   @Override
   public Integer getTypeIndex() {
      return 5;
   }

   @NotNull
   @Override
   public Set<AbstractAugment> getCompatibleAugments() {
      return this.augmentSetOf(new AbstractAugment[0]);
   }

   @Override
   public abstract int getDefaultManaCost();

   @Override
   public SpellStats.Builder applyModifiers(
      SpellStats.Builder builder, AbstractSpellPart spellPart, HitResult rayTraceResult, Level world, LivingEntity shooter, SpellContext spellContext
   ) {
      return this.applyModifiers(builder, spellPart);
   }

   @Deprecated
   public SpellStats.Builder applyModifiers(SpellStats.Builder builder, AbstractSpellPart spellPart) {
      return builder;
   }
}
