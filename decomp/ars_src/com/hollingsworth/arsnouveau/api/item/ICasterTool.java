package com.hollingsworth.arsnouveau.api.item;

import com.hollingsworth.arsnouveau.api.client.IDisplayMana;
import com.hollingsworth.arsnouveau.api.spell.ISpellCaster;
import com.hollingsworth.arsnouveau.api.spell.ISpellCasterProvider;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellCaster;
import com.hollingsworth.arsnouveau.api.util.CasterUtil;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.common.items.SpellParchment;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public interface ICasterTool extends IScribeable, IDisplayMana, ISpellHotkeyListener, ISpellCasterProvider {
   @Override
   default boolean onScribe(Level world, BlockPos pos, Player player, InteractionHand handIn, ItemStack tableStack) {
      ItemStack heldStack = player.m_21120_(handIn);
      ISpellCaster thisCaster = CasterUtil.getCaster(tableStack);
      if (!(heldStack.m_41720_() instanceof SpellBook)
         && !(heldStack.m_41720_() instanceof SpellParchment)
         && heldStack.m_41720_() != ItemsRegistry.MANIPULATION_ESSENCE.m_5456_()) {
         return false;
      } else {
         Spell spell = new Spell();
         if (heldStack.m_41720_() instanceof ICasterTool) {
            ISpellCaster heldCaster = CasterUtil.getCaster(heldStack);
            spell = heldCaster.getSpell();
            thisCaster.setColor(heldCaster.getColor());
            thisCaster.setFlavorText(heldCaster.getFlavorText());
            thisCaster.setSpellName(heldCaster.getSpellName());
            thisCaster.setSound(heldCaster.getCurrentSound());
         } else if (heldStack.m_41720_() == ItemsRegistry.MANIPULATION_ESSENCE.m_5456_()) {
            String[] words = new String[]{
               "the",
               "elder",
               "scrolls",
               "klaatu",
               "berata",
               "niktu",
               "xyzzy",
               "bless",
               "curse",
               "light",
               "darkness",
               "fire",
               "air",
               "earth",
               "water",
               "hot",
               "dry",
               "cold",
               "wet",
               "ignite",
               "snuff",
               "embiggen",
               "twist",
               "shorten",
               "stretch",
               "fiddle",
               "destroy",
               "imbue",
               "galvanize",
               "enchant",
               "free",
               "limited",
               "range",
               "of",
               "towards",
               "inside",
               "sphere",
               "cube",
               "self",
               "other",
               "ball",
               "mental",
               "physical",
               "grow",
               "shrink",
               "demon",
               "elemental",
               "spirit",
               "animal",
               "creature",
               "beast",
               "humanoid",
               "undead",
               "fresh",
               "stale",
               "phnglui",
               "mglwnafh",
               "cthulhu",
               "rlyeh",
               "wgahnagl",
               "fhtagn",
               "baguette"
            };
            int numWords = world.f_46441_.m_188503_(3) + 3;
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < numWords; i++) {
               sb.append(words[world.f_46441_.m_188503_(words.length)]).append(" ");
            }

            thisCaster.setSpellHidden(true);
            thisCaster.setHiddenRecipe(sb.toString());
            PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.spell_hidden"));
            return true;
         }

         if (this.isScribedSpellValid(thisCaster, player, handIn, tableStack, spell)) {
            boolean success = this.setSpell(thisCaster, player, handIn, tableStack, spell);
            if (success) {
               this.sendSetMessage(player);
               return true;
            }
         } else {
            this.sendInvalidMessage(player);
         }

         return false;
      }
   }

   default void sendSetMessage(Player player) {
      PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.set_spell"));
   }

   default void sendInvalidMessage(Player player) {
      PortUtil.sendMessageNoSpam(player, Component.m_237115_("ars_nouveau.invalid_spell"));
   }

   @NotNull
   @Override
   default ISpellCaster getSpellCaster(ItemStack stack) {
      return new SpellCaster(stack);
   }

   @Override
   default ISpellCaster getSpellCaster() {
      return this.getSpellCaster(new CompoundTag());
   }

   @Override
   default ISpellCaster getSpellCaster(CompoundTag tag) {
      return new SpellCaster(tag);
   }

   default boolean setSpell(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      caster.setSpell(spell);
      return true;
   }

   default boolean isScribedSpellValid(ISpellCaster caster, Player player, InteractionHand hand, ItemStack stack, Spell spell) {
      return spell.isValid();
   }

   @Override
   default boolean shouldDisplay(ItemStack stack) {
      return true;
   }

   default void getInformation(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (worldIn != null) {
         ISpellCaster caster = this.getSpellCaster(stack);
         if (caster.getSpell().isEmpty()) {
            tooltip2.add(Component.m_237115_("ars_nouveau.tooltip.can_inscribe"));
         } else {
            if (!caster.getSpellName().isEmpty()) {
               tooltip2.add(Component.m_237113_(caster.getSpellName()));
            }

            if (caster.isSpellHidden()) {
               tooltip2.add(
                  Component.m_237113_(caster.getHiddenRecipe())
                     .m_130948_(Style.f_131099_.m_131150_(new ResourceLocation("minecraft", "alt")).m_131140_(ChatFormatting.GOLD))
               );
            } else {
               Spell spell = caster.getSpell();
               tooltip2.add(Component.m_237113_(spell.getDisplayString()));
            }

            if (!caster.getFlavorText().isEmpty()) {
               tooltip2.add(Component.m_237113_(caster.getFlavorText()).m_130948_(Style.f_131099_.m_131155_(true).m_131140_(ChatFormatting.BLUE)));
            }
         }
      }
   }
}
