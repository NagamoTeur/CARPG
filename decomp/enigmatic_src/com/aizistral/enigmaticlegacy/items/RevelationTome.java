package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ExperienceHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.registries.EnigmaticSounds;
import com.aizistral.enigmaticlegacy.triggers.RevelationGainTrigger;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RevelationTome extends ItemBase implements Vanishable {
   public static final String revelationPointsTag = "revelationPoints";
   public static final String xpPointsTag = "xpPoints";
   public static final String formerReadersTag = "formerReaders";
   public static final String lastHolderTag = "lastHolder";
   public final RevelationTome.TomeType theType;
   public final String persistantPointsTag;

   public RevelationTome(Rarity rarity, RevelationTome.TomeType type) {
      super(ItemBase.getDefaultProperties().m_41497_(rarity).m_41487_(1));
      this.theType = type;
      this.persistantPointsTag = "enigmaticlegacy.revelation_points_" + this.theType.typeName;
   }

   public void m_6883_(ItemStack stack, Level worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (entityIn instanceof ServerPlayer) {
         ItemNBTHelper.setUUID(stack, "lastHolder", entityIn.m_20148_());
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      player.m_6672_(hand);
      if (!havePlayerRead(player, stack)) {
         markRead(player, stack);
         int xp = ItemNBTHelper.getInt(stack, "xpPoints", random.nextInt(1000));
         int revelation = ItemNBTHelper.getInt(stack, "revelationPoints", 1);
         if (player instanceof ServerPlayer) {
            int currentPoints = SuperpositionHandler.getPersistentInteger(player, this.persistantPointsTag, 0);
            ExperienceHelper.addPlayerXP(player, xp);
            SuperpositionHandler.setPersistentInteger(player, this.persistantPointsTag, currentPoints + revelation);
            world.m_5594_(null, new BlockPos(player.m_20182_()), EnigmaticSounds.LEARN, SoundSource.PLAYERS, 0.75F, 1.0F);
            RevelationGainTrigger.INSTANCE.trigger((ServerPlayer)player, this.theType, currentPoints + revelation);
            RevelationGainTrigger.INSTANCE.trigger((ServerPlayer)player, RevelationTome.TomeType.GENERIC, getGenericPoints(player));
         } else {
            EnigmaticLegacy.PROXY.pushRevelationToast(stack, xp, revelation);
         }

         player.m_6674_(hand);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTome1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTome2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTome3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTome4");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (!havePlayerRead(Minecraft.m_91087_().f_91074_, stack)) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTomeClick");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.revelationTomeMarkRead");
      }
   }

   public static int getGenericPoints(Player player) {
      int overworldPoints = SuperpositionHandler.getPersistentInteger(
         player, "enigmaticlegacy.revelation_points_" + RevelationTome.TomeType.OVERWORLD.typeName, 0
      );
      int netherPoints = SuperpositionHandler.getPersistentInteger(player, "enigmaticlegacy.revelation_points_" + RevelationTome.TomeType.NETHER.typeName, 0);
      int endPoints = SuperpositionHandler.getPersistentInteger(player, "enigmaticlegacy.revelation_points_" + RevelationTome.TomeType.END.typeName, 0);
      return overworldPoints + netherPoints + endPoints;
   }

   public ItemStack createTome(int revelationPoints, int experiencePoints) {
      ItemStack theTome = new ItemStack(this);
      ItemNBTHelper.setInt(theTome, "revelationPoints", revelationPoints);
      ItemNBTHelper.setInt(theTome, "xpPoints", experiencePoints);
      return theTome;
   }

   public static boolean havePlayerRead(Player player, ItemStack tome) {
      boolean haveReadBefore = false;
      CompoundTag nbt = ItemNBTHelper.getNBT(tome);
      Tag uncheckedList = (Tag)(nbt.m_128441_("formerReaders") ? nbt.m_128423_("formerReaders") : new ListTag());
      if (uncheckedList instanceof ListTag) {
         for (Tag entry : (ListTag)uncheckedList) {
            if (entry.m_7916_().equals(player.m_36316_().getName())) {
               haveReadBefore = true;
               break;
            }
         }
      }

      return haveReadBefore;
   }

   public static void markRead(Player player, ItemStack tome) {
      CompoundTag nbt = ItemNBTHelper.getNBT(tome);
      ListTag list = nbt.m_128423_("formerReaders") instanceof ListTag ? (ListTag)nbt.m_128423_("formerReaders") : new ListTag();
      list.add(StringTag.m_129297_(player.m_36316_().getName()));
      nbt.m_128365_("formerReaders", list);
      tome.m_41751_(nbt);
   }

   public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
      return 400;
   }

   public static enum TomeType {
      OVERWORLD("overworld"),
      NETHER("nether"),
      END("end"),
      GENERIC("generic");

      public final String typeName;

      private TomeType(String name) {
         this.typeName = name;
      }

      public static RevelationTome.TomeType resolveType(@Nonnull String type) {
         if (type.equals("overworld")) {
            return OVERWORLD;
         } else if (type.equals("nether")) {
            return NETHER;
         } else {
            return type.equals("end") ? END : GENERIC;
         }
      }
   }
}
