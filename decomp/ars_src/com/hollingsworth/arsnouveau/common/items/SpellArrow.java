package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.ICasterTool;
import com.hollingsworth.arsnouveau.api.mana.IManaCap;
import com.hollingsworth.arsnouveau.api.spell.AbstractAugment;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import com.hollingsworth.arsnouveau.api.spell.wrapped_caster.PlayerCaster;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.entity.EntitySpellArrow;
import com.hollingsworth.arsnouveau.common.spell.augment.AugmentPierce;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class SpellArrow extends ArrowItem {
   public AbstractSpellPart part;
   public int numParts;

   public SpellArrow(AbstractAugment augment, int numParts) {
      super(ItemsRegistry.defaultItemProperties());
      this.part = augment;
      this.numParts = numParts;
   }

   public void modifySpell(Spell spell) {
      for (int i = 0; i < this.numParts; i++) {
         spell.recipe.add(this.part);
      }
   }

   public AbstractArrow m_6394_(Level world, ItemStack stack, LivingEntity shooter) {
      IManaCap mana = (IManaCap)CapabilityRegistry.getMana(shooter).orElse(null);
      if (mana == null) {
         return new Arrow(world, shooter);
      } else {
         EntitySpellArrow spellArrow = new EntitySpellArrow(world, shooter);
         if (shooter instanceof Player entity && shooter.m_21205_().m_41720_() instanceof ICasterTool) {
            ICasterTool caster = (ICasterTool)entity.m_21205_().m_41720_();
            ISpellCaster spellCaster = caster.getSpellCaster(entity.m_21205_());
            Spell spell = spellCaster.getSpell();
            this.modifySpell(spell);
            spell.addDiscount(this.part.getCastingCost() * this.numParts);
            spellArrow.spellResolver = new SpellResolver(new SpellContext(world, spell, entity, new PlayerCaster(entity))).withSilent(true);
            spellArrow.pierceLeft = spell.getBuffsAtIndex(0, shooter, AugmentPierce.INSTANCE);
            return spellArrow;
         }

         return super.m_6394_(world, stack, shooter);
      }
   }

   public void m_7373_(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      tooltip.add(Component.m_237115_("ars_nouveau.spell_arrow.desc"));
      Spell spell = new Spell();

      for (int i = 0; i < this.numParts; i++) {
         spell.recipe.add(this.part);
      }

      tooltip.add(Component.m_237113_(spell.getDisplayString()));
   }
}
