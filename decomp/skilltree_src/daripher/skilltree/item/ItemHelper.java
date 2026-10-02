package daripher.skilltree.item;

import com.google.common.collect.ImmutableList;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.config.Config;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.init.PSTRegistries;
import daripher.skilltree.init.PSTTags;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.item.quiver.QuiverItem;
import daripher.skilltree.skill.bonus.condition.item.EquipmentCondition;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemDurabilityBonus;
import daripher.skilltree.skill.bonus.item.ItemSocketsBonus;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemHelper {
   public static boolean canInsertGem(ItemStack stack) {
      return !SkillTreeMod.apotheosisEnabled() && hasSockets(stack);
   }

   public static boolean hasSockets(ItemStack stack) {
      List<? extends String> blacklist = Config.socket_blacklist;
      if (blacklist.contains("*:*")) {
         return false;
      } else {
         ResourceLocation itemId = ForgeRegistries.ITEMS.getKey(stack.m_41720_());
         if (itemId == null) {
            return false;
         } else if (blacklist.contains(itemId.toString())) {
            return false;
         } else {
            String namespace = itemId.m_135827_();
            return blacklist.contains(namespace + ":*") ? false : EquipmentCondition.isEquipment(stack) || stack.m_204117_(PSTTags.JEWELRY);
         }
      }
   }

   public static int getFirstEmptySocket(ItemStack stack, Player player) {
      int sockets = getMaximumSockets(stack, player);
      int socket = 0;

      for (int i = 0; i < sockets; i++) {
         socket = i;
         if (!GemItem.hasGem(stack, i)) {
            break;
         }
      }

      return socket;
   }

   public static int getGemsAmount(ItemStack stack) {
      if (!stack.m_41782_() || !stack.m_41784_().m_128441_("gems_count")) {
         refreshGemsAmount(stack);
      }

      return stack.m_41784_().m_128451_("gems_count");
   }

   public static void refreshGemsAmount(ItemStack stack) {
      stack.m_41784_().m_128405_("gems_count", GemItem.getGems(stack).size());
   }

   public static void setPoisons(ItemStack result, ItemStack poisonStack) {
      List<MobEffectInstance> effects = PotionUtils.m_43547_(poisonStack);
      ListTag poisonsTag = new ListTag();

      for (MobEffectInstance effect : effects) {
         CompoundTag effectTag = effect.m_19555_(new CompoundTag());
         poisonsTag.add(effectTag);
      }

      result.m_41784_().m_128365_("Poisons", poisonsTag);
   }

   public static boolean hasPoisons(ItemStack stack) {
      CompoundTag tag = stack.m_41783_();
      return tag != null && tag.m_128441_("Poisons");
   }

   public static List<MobEffectInstance> getPoisons(ItemStack stack) {
      CompoundTag tag = stack.m_41783_();
      if (tag == null) {
         return ImmutableList.of();
      } else {
         ListTag poisonsTags = tag.m_128437_("Poisons", 10);
         List<MobEffectInstance> list = new ArrayList<>();

         for (Tag poisonsTag : poisonsTags) {
            CompoundTag compoundTag = (CompoundTag)poisonsTag;
            MobEffectInstance load = MobEffectInstance.m_19560_(compoundTag);
            list.add(load);
         }

         return list;
      }
   }

   public static int getDefaultSockets(ItemStack stack) {
      if (EquipmentCondition.isHelmet(stack)) {
         return Config.default_helmet_sockets;
      } else if (EquipmentCondition.isChestplate(stack)) {
         return Config.default_chestplate_sockets;
      } else if (EquipmentCondition.isLeggings(stack)) {
         return Config.default_leggings_sockets;
      } else if (EquipmentCondition.isBoots(stack)) {
         return Config.default_boots_sockets;
      } else if (EquipmentCondition.isWeapon(stack)) {
         return Config.default_weapon_sockets;
      } else if (EquipmentCondition.isShield(stack)) {
         return Config.default_shield_sockets;
      } else if (stack.m_204117_(PSTTags.RINGS)) {
         return Config.default_ring_sockets;
      } else {
         return stack.m_204117_(PSTTags.NECKLACES) ? Config.default_necklace_sockets : 0;
      }
   }

   public static int getAdditionalSockets(ItemStack stack) {
      int sockets = 0;

      for (ItemBonus<?> itemBonus : getItemBonusesExcludingGems(stack)) {
         if (itemBonus instanceof ItemSocketsBonus socketsBonus) {
            sockets += socketsBonus.getAmount();
         }
      }

      if (stack.m_41720_() instanceof HasAdditionalSockets bonus) {
         sockets += bonus.getAdditionalSockets();
      }

      return sockets;
   }

   public static boolean isQuiver(ItemStack stack) {
      return !stack.m_41619_() && stack.m_41720_() instanceof QuiverItem;
   }

   public static int getMaximumSockets(ItemStack stack, @Nullable Player player) {
      if (SkillTreeMod.apotheosisEnabled()) {
         return 0;
      } else {
         int sockets = getDefaultSockets(stack) + getAdditionalSockets(stack);
         if (player != null) {
            sockets += PlayerHelper.getPlayerSockets(stack, player);
         }

         return sockets;
      }
   }

   public static List<? extends ItemBonus<?>> getItemBonusesExcludingGems(ItemStack stack) {
      if (!stack.m_41782_()) {
         return ImmutableList.of();
      } else {
         List<ItemBonus<?>> list = new ArrayList<>();

         for (Tag tag : stack.m_41784_().m_128437_("SkillBonuses", 10)) {
            CompoundTag compoundTag = (CompoundTag)tag;
            ItemBonus<?> itemBonus = deserializeBonus(compoundTag);
            if (itemBonus != null) {
               list.add(itemBonus);
            }
         }

         return list;
      }
   }

   public static List<? extends ItemBonus<?>> getItemBonuses(ItemStack stack) {
      if (!stack.m_41782_()) {
         return ImmutableList.of();
      } else {
         List<ItemBonus<?>> bonuses = new ArrayList<>();
         bonuses.addAll(getItemBonusesExcludingGems(stack));
         bonuses.addAll(GemItem.getGemBonuses(stack));
         return bonuses;
      }
   }

   public static <T extends ItemBonus<?>> List<T> getItemBonuses(ItemStack stack, Class<T> aClass) {
      List<T> list = new ArrayList<>();

      for (ItemBonus<?> itemBonus : getItemBonuses(stack)) {
         if (aClass.isInstance(itemBonus)) {
            T cast = (T)aClass.cast(itemBonus);
            list.add(cast);
         }
      }

      return list;
   }

   public static void addItemBonus(ItemStack stack, ItemBonus<?> bonus) {
      ListTag bonusesTag = new ListTag();
      bonusesTag.add(serializeBonus((ItemBonus<? extends ItemBonus<?>>)bonus));

      for (ItemBonus<?> bonus2 : getItemBonuses(stack)) {
         ItemBonus<? extends ItemBonus<?>> itemBonus = mergeIfPossible(bonus, bonus2, bonusesTag);
         CompoundTag tag = serializeBonus(itemBonus);
         bonusesTag.add(tag);
      }

      stack.m_41784_().m_128365_("SkillBonuses", bonusesTag);
   }

   public static void removeItemBonus(ItemStack stack, ItemBonus<?> bonus) {
      ListTag bonusesTag = new ListTag();

      for (ItemBonus<?> itemBonus : getItemBonuses(stack)) {
         if (!bonus.equals(itemBonus)) {
            CompoundTag tag = serializeBonus((ItemBonus<? extends ItemBonus<?>>)itemBonus);
            bonusesTag.add(tag);
         }
      }

      stack.m_41784_().m_128365_("SkillBonuses", bonusesTag);
   }

   public static void removeItemBonuses(ItemStack stack) {
      if (stack.m_41782_()) {
         stack.m_41784_().m_128473_("SkillBonuses");
      }
   }

   public static void refreshDurabilityBonuses(ItemStack stack) {
      if (stack.m_41782_()) {
         stack.m_41784_().m_128473_("DurabilityBonuses");
      }

      ListTag durabilityTags = new ListTag();

      for (ItemDurabilityBonus bonus : getItemBonuses(stack, ItemDurabilityBonus.class)) {
         durabilityTags.add(serializeBonus(bonus));
      }

      if (!durabilityTags.isEmpty()) {
         stack.m_41784_().m_128365_("DurabilityBonuses", durabilityTags);
      }
   }

   public static List<ItemDurabilityBonus> getDurabilityBonuses(ItemStack stack) {
      if (!stack.m_41782_()) {
         return ImmutableList.of();
      } else {
         List<ItemDurabilityBonus> list = new ArrayList<>();

         for (Tag tag : stack.m_41784_().m_128437_("DurabilityBonuses", 10)) {
            CompoundTag compoundTag = (CompoundTag)tag;
            if (deserializeBonus(compoundTag) instanceof ItemDurabilityBonus bonus) {
               list.add(bonus);
            }
         }

         return list;
      }
   }

   private static ItemBonus<? extends ItemBonus<?>> mergeIfPossible(ItemBonus<?> bonus1, ItemBonus<?> bonus2, ListTag bonusesTag) {
      if (bonus1.canMerge(bonus2)) {
         bonusesTag.remove(0);
         return (ItemBonus<? extends ItemBonus<?>>)bonus1.merge(bonus2);
      } else {
         return (ItemBonus<? extends ItemBonus<?>>)bonus2;
      }
   }

   private static CompoundTag serializeBonus(ItemBonus<? extends ItemBonus<?>> bonus) {
      ItemBonus.Serializer serializer = bonus.getSerializer();
      CompoundTag bonusTag = serializer.serialize(bonus);
      ResourceLocation id = PSTRegistries.ITEM_BONUSES.get().getKey(serializer);
      bonusTag.m_128359_("type", Objects.requireNonNull(id).toString());
      return bonusTag;
   }

   private static ItemBonus<?> deserializeBonus(CompoundTag tag) {
      if (!tag.m_128441_("type")) {
         return null;
      } else {
         ResourceLocation id = new ResourceLocation(tag.m_128461_("type"));
         ItemBonus.Serializer serializer = (ItemBonus.Serializer)PSTRegistries.ITEM_BONUSES.get().getValue(id);
         if (serializer == null) {
            return null;
         } else {
            try {
               return (ItemBonus<?>)serializer.deserialize(tag);
            } catch (Exception var4) {
               var4.printStackTrace();
               return null;
            }
         }
      }
   }
}
