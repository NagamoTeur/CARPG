package com.hollingsworth.arsnouveau.client.patchouli;

import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class GlyphProcessor implements IComponentProcessor {
   GlyphRecipe recipe;

   public void setup(IVariableProvider variables) {
      RecipeManager manager = Minecraft.m_91087_().f_91073_.m_7465_();
      String recipeID = variables.get("recipe").asString();

      try {
         this.recipe = (GlyphRecipe)manager.m_44043_(new ResourceLocation(recipeID)).orElse(null);
      } catch (Exception var5) {
      }
   }

   public IVariable process(String s) {
      if (this.recipe == null) {
         return null;
      } else if (s.equals("tier")) {
         return IVariable.wrap(
            Component.m_237115_("ars_nouveau.tier").getString()
               + ": "
               + Component.m_237115_("ars_nouveau.spell_tier." + this.recipe.getSpellPart().getConfigTier().value).getString()
         );
      } else if (!s.equals("schools")) {
         if (s.equals("mana_cost")) {
            if (this.recipe.output.m_41720_() instanceof Glyph) {
               int cost = ((Glyph)this.recipe.output.m_41720_()).spellPart.getCastingCost();
               String costLang = "";
               if (cost == 0) {
                  costLang = Component.m_237115_("ars_nouveau.mana_cost.none").getString();
               }

               if (cost < 20) {
                  costLang = Component.m_237115_("ars_nouveau.mana_cost.low").getString();
               }

               if (cost < 50) {
                  costLang = Component.m_237115_("ars_nouveau.mana_cost.medium").getString();
               }

               if (cost >= 50) {
                  costLang = Component.m_237115_("ars_nouveau.mana_cost.high").getString();
               }

               return IVariable.wrap(Component.m_237115_("ars_nouveau.casting_cost").getString() + ": " + costLang);
            } else {
               return IVariable.wrap("");
            }
         } else {
            return null;
         }
      } else {
         AbstractSpellPart part = ((Glyph)this.recipe.output.m_41720_()).spellPart;
         StringBuilder str = new StringBuilder(Component.m_237115_("ars_nouveau.spell_schools").getString() + ": ");

         for (SpellSchool spellSchool : part.spellSchools) {
            str.append(spellSchool.getTextComponent().getString()).append(",");
         }

         if (!part.spellSchools.isEmpty()) {
            str = new StringBuilder(str.substring(0, str.length() - 1));
         }

         return IVariable.wrap(str.toString());
      }
   }
}
