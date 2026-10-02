package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.ArsNouveau;
import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.setup.Config;
import com.mojang.blaze3d.platform.InputConstants;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class Glyph extends ModItem {
   public AbstractSpellPart spellPart;

   public Glyph(AbstractSpellPart part) {
      super(new Properties().m_41491_(ArsNouveau.glyphGroup));
      this.spellPart = part;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      if (worldIn.f_46443_) {
         return super.m_7203_(worldIn, playerIn, handIn);
      } else if (!Config.isGlyphEnabled(this.spellPart.getRegistryName())) {
         playerIn.m_213846_(Component.m_237115_("ars_nouveau.spell.disabled"));
         return super.m_7203_(worldIn, playerIn, handIn);
      } else {
         IPlayerCap playerDataCap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(playerIn).orElse(null);
         if (playerDataCap != null) {
            if (playerDataCap.knowsGlyph(this.spellPart) || ArsNouveauAPI.getInstance().getDefaultStartingSpells().contains(this.spellPart)) {
               playerIn.m_213846_(Component.m_237113_("You already know this spell!"));
               return super.m_7203_(worldIn, playerIn, handIn);
            }

            if (playerDataCap.unlockGlyph(this.spellPart)) {
               CapabilityRegistry.EventHandler.syncPlayerCap(playerIn);
               playerIn.m_21120_(handIn).m_41774_(1);
               playerIn.m_213846_(Component.m_237113_("Unlocked " + this.spellPart.getName()));
            }
         }

         return super.m_7203_(worldIn, playerIn, handIn);
      }
   }

   public Component m_7626_(ItemStack pStack) {
      return Component.m_237110_("ars_nouveau.glyph_of", new Object[]{this.spellPart.getLocaleName()});
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      if (this.spellPart != null) {
         if (!Config.isGlyphEnabled(this.spellPart.getRegistryName())) {
            tooltip2.add(Component.m_237115_("tooltip.ars_nouveau.glyph_disabled"));
         } else if (this.spellPart != null) {
            tooltip2.add(
               Component.m_237110_("tooltip.ars_nouveau.glyph_level", new Object[]{this.spellPart.getConfigTier().value})
                  .m_6270_(Style.f_131099_.m_131140_(ChatFormatting.BLUE))
            );
            tooltip2.add(Component.m_237115_("ars_nouveau.schools"));

            for (SpellSchool s : this.spellPart.spellSchools) {
               tooltip2.add(s.getTextComponent());
            }
         }

         if (Minecraft.m_91087_().f_91074_ != null) {
            IPlayerCap playerDataCap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(Minecraft.m_91087_().f_91074_).orElse(null);
            if (playerDataCap != null) {
               if (!playerDataCap.knowsGlyph(this.spellPart) && !ArsNouveauAPI.getInstance().getDefaultStartingSpells().contains(this.spellPart)) {
                  tooltip2.add(Component.m_237115_("tooltip.ars_nouveau.glyph_unknown").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.DARK_RED)));
               } else {
                  tooltip2.add(Component.m_237115_("tooltip.ars_nouveau.glyph_known").m_6270_(Style.f_131099_.m_131140_(ChatFormatting.DARK_GREEN)));
               }
            }

            tooltip2.add(Component.m_237115_(" "));
            if (InputConstants.m_84830_(Minecraft.m_91087_().m_91268_().m_85439_(), Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84873_())) {
               tooltip2.add(this.spellPart.getBookDescLang());
            } else {
               tooltip2.add(Component.m_237110_("tooltip.ars_nouveau.hold_shift", new Object[]{Minecraft.m_91087_().f_91066_.f_92090_.getKey().m_84875_()}));
            }
         }
      }
   }
}
