package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.ArsNouveauAPI;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.util.NBTUtil;
import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.capability.IPlayerCap;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import com.hollingsworth.arsnouveau.setup.config.ServerConfig;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AnnotatedCodex extends ModItem {
   public AnnotatedCodex(Properties properties) {
      super(properties);
   }

   public AnnotatedCodex() {
      this(ItemsRegistry.defaultItemProperties().m_41487_(1));
   }

   public int getUnlockLevelCost(Collection<AbstractSpellPart> spellParts) {
      int expPerGlyph = (Integer)ServerConfig.CODEX_COST_PER_GLYPH.get();
      return ScribesTile.getLevelsFromExp(expPerGlyph * spellParts.size());
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
      if (pPlayer.f_19853_.f_46443_) {
         return super.m_7203_(pLevel, pPlayer, pUsedHand);
      } else {
         ItemStack stack = pPlayer.m_21120_(pUsedHand);
         AnnotatedCodex.CodexData data = new AnnotatedCodex.CodexData(stack);
         IPlayerCap playerCap = (IPlayerCap)CapabilityRegistry.getPlayerDataCap(pPlayer).orElse(null);
         if (playerCap == null) {
            return super.m_7203_(pLevel, pPlayer, pUsedHand);
         } else {
            Collection<AbstractSpellPart> known = playerCap.getKnownGlyphs();
            Collection<AbstractSpellPart> storedGlyphs = data.getGlyphs();
            if (data.getPlayerID() == null) {
               int levelCost = this.getUnlockLevelCost(playerCap.getKnownGlyphs());
               int expCost = ScribesTile.getExperienceForLevel(levelCost);
               if (expCost > ScribesTile.getTotalPlayerExperience(pPlayer)) {
                  PortUtil.sendMessageNoSpam(pPlayer, Component.m_237110_("ars_nouveau.codex_not_enough_exp", new Object[]{levelCost}));
               } else {
                  data.setGlyphs(playerCap.getKnownGlyphs());
                  data.setPlayer(pPlayer);
                  PortUtil.sendMessageNoSpam(pPlayer, Component.m_237115_("ars_nouveau.recorded_codex"));
                  pPlayer.m_6756_(-expCost);
               }
            } else if (pPlayer.m_20148_().equals(data.getPlayerID())) {
               Collection<AbstractSpellPart> difference = new ArrayList<>();

               for (AbstractSpellPart spellPart : known) {
                  if (!storedGlyphs.contains(spellPart)) {
                     difference.add(spellPart);
                  }
               }

               int levelCost = this.getUnlockLevelCost(difference);
               if (!difference.isEmpty()) {
                  int expCost = ScribesTile.getExperienceForLevel(levelCost);
                  if (expCost > ScribesTile.getTotalPlayerExperience(pPlayer)) {
                     PortUtil.sendMessageNoSpam(pPlayer, Component.m_237110_("ars_nouveau.codex_not_enough_exp", new Object[]{levelCost}));
                  } else {
                     pPlayer.m_6756_(-expCost);
                     data.setGlyphs(playerCap.getKnownGlyphs());
                     PortUtil.sendMessageNoSpam(pPlayer, Component.m_237115_("ars_nouveau.updated_codex"));
                  }
               } else {
                  PortUtil.sendMessageNoSpam(pPlayer, Component.m_237115_("ars_nouveau.codex_up_to_date"));
               }
            } else {
               int numUnlocked = 0;

               for (AbstractSpellPart storedPart : storedGlyphs) {
                  if (!known.contains(storedPart)) {
                     playerCap.unlockGlyph(storedPart);
                     numUnlocked++;
                  }
               }

               if (numUnlocked > 0) {
                  stack.m_41774_(1);
                  PortUtil.sendMessageNoSpam(pPlayer, Component.m_237110_("ars_nouveau.consumed_codex", new Object[]{numUnlocked}));
                  CapabilityRegistry.EventHandler.syncPlayerCap(pPlayer);
               } else {
                  PortUtil.sendMessageNoSpam(pPlayer, Component.m_237115_("ars_nouveau.codex_no_use"));
               }
            }

            return super.m_7203_(pLevel, pPlayer, pUsedHand);
         }
      }
   }

   @Override
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip2, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip2, flagIn);
      AnnotatedCodex.CodexData data = new AnnotatedCodex.CodexData(stack);
      if (data.glyphs.isEmpty()) {
         tooltip2.add(Component.m_237115_("ars_nouveau.codex_tooltip"));
      } else {
         tooltip2.add(Component.m_237110_("ars_nouveau.contains_glyphs", new Object[]{data.glyphs.size()}));
      }

      if (data.playerName != null) {
         tooltip2.add(Component.m_237110_("ars_nouveau.recorded_by", new Object[]{data.playerName}));
      }
   }

   public static class CodexData {
      ItemStack stack;
      private List<AbstractSpellPart> glyphs = new ArrayList<>();
      private UUID playerID;
      public String playerName;

      public CodexData(ItemStack stack) {
         this.stack = stack;
         CompoundTag tag = stack.m_41784_();
         ArsNouveauAPI api = ArsNouveauAPI.getInstance();

         for (ResourceLocation s : NBTUtil.readResourceLocations(tag, "glyph_")) {
            if (api.getSpellpartMap().containsKey(s)) {
               this.glyphs.add(api.getSpellpartMap().get(s));
            }
         }

         this.playerName = tag.m_128441_("playerName") ? tag.m_128461_("playerName") : null;
         this.playerID = tag.m_128403_("player") ? tag.m_128342_("player") : null;
      }

      public void setPlayer(Player player) {
         this.playerID = player.m_20148_();
         this.playerName = player.m_7755_().getString();
         this.write();
      }

      public void setGlyphs(Collection<AbstractSpellPart> glyphs) {
         this.glyphs = new ArrayList<>(glyphs);
         this.write();
      }

      public UUID getPlayerID() {
         return this.playerID;
      }

      public List<AbstractSpellPart> getGlyphs() {
         return this.glyphs;
      }

      public void write() {
         CompoundTag tag = new CompoundTag();
         NBTUtil.writeResourceLocations(tag, "glyph_", this.glyphs.stream().map(AbstractSpellPart::getRegistryName).collect(Collectors.toList()));
         if (this.playerID != null) {
            tag.m_128362_("player", this.playerID);
         }

         if (this.playerName != null) {
            tag.m_128359_("playerName", this.playerName);
         }

         this.stack.m_41751_(tag);
      }
   }
}
