package shadows.apotheosis.adventure.affix.socket.gem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;

public class GemItem extends Item {
   public static final String HAS_REFRESHED = "has_refreshed";
   public static final String UUID_ARRAY = "uuids";
   public static final String GEM = "gem";

   public GemItem(Properties pProperties) {
      super(pProperties);
   }

   public void m_7373_(ItemStack pStack, Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
      GemInstance inst = GemInstance.unsocketed(pStack);
      if (!inst.isValidUnsocketed()) {
         tooltip.add(Component.m_237113_("Errored gem with no bonus!").m_130940_(ChatFormatting.GRAY));
      } else {
         inst.gem().addInformation(pStack, getLootRarity(pStack), tooltip::add);
      }
   }

   public Component m_7626_(ItemStack pStack) {
      GemInstance inst = GemInstance.unsocketed(pStack);
      if (!inst.isValidUnsocketed()) {
         return super.m_7626_(pStack);
      } else {
         MutableComponent comp = Component.m_237115_(this.m_5671_(pStack));
         comp = Component.m_237110_("item.apotheosis.gem." + inst.rarity().id(), new Object[]{comp});
         return comp.m_130948_(Style.f_131099_.m_131148_(inst.rarity().color()));
      }
   }

   public String m_5671_(ItemStack pStack) {
      Gem gem = getGem(pStack);
      return gem == null ? super.m_5524_() : super.m_5671_(pStack) + "." + gem.getId();
   }

   public boolean m_5812_(ItemStack pStack) {
      GemInstance inst = GemInstance.unsocketed(pStack);
      return !inst.isValidUnsocketed() ? super.m_5812_(pStack) : inst.isMaxRarity();
   }

   public boolean m_41386_(DamageSource src) {
      return super.m_41386_(src) && src != DamageSource.f_19321_;
   }

   public void m_6787_(CreativeModeTab group, NonNullList<ItemStack> items) {
      if (group == CreativeModeTab.f_40754_) {
         GemManager.INSTANCE.getValues().stream().sorted(Comparator.comparing(TypeKeyedBase::getId)).forEach(gem -> {
            for (LootRarity rarity : LootRarity.values()) {
               if (gem.clamp(rarity) == rarity) {
                  ItemStack stack = new ItemStack(this);
                  setGem(stack, gem);
                  setLootRarity(stack, rarity);
                  items.add(stack);
               }
            }
         });
      }
   }

   public void m_6883_(ItemStack stack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
      CompoundTag tag = stack.m_41783_();
      if (tag != null) {
         tag.m_128473_("uuids");
         tag.m_128473_("facets");
      }
   }

   @Nullable
   public String getCreatorModId(ItemStack stack) {
      GemInstance inst = GemInstance.unsocketed(stack);
      return inst.isValidUnsocketed() ? inst.gem().getId().m_135827_() : super.getCreatorModId(stack);
   }

   public static List<UUID> getUUIDs(ItemStack gemStack) {
      Gem gem = getGem(gemStack);
      return gem == null ? Collections.emptyList() : getOrCreateUUIDs(gemStack.m_41784_(), gem.getNumberOfUUIDs());
   }

   public static List<UUID> getOrCreateUUIDs(CompoundTag tag, int numUUIDs) {
      if (numUUIDs == 0) {
         return Collections.emptyList();
      } else if (!tag.m_128441_("uuids")) {
         return generateAndSave(new ArrayList<>(numUUIDs), numUUIDs, tag);
      } else {
         ListTag list = tag.m_128437_("uuids", 11);
         List<UUID> ret = new ArrayList<>(list.size());

         for (Tag t : list) {
            ret.add(NbtUtils.m_129233_(t));
         }

         return ret.size() < numUUIDs ? generateAndSave(ret, numUUIDs, tag) : ret;
      }
   }

   private static List<UUID> generateAndSave(List<UUID> base, int amount, CompoundTag tag) {
      int needed = amount - base.size();

      for (int i = 0; i < needed; i++) {
         base.add(UUID.randomUUID());
      }

      ListTag list = new ListTag();

      for (UUID id : base) {
         list.add(NbtUtils.m_129226_(id));
      }

      tag.m_128365_("uuids", list);
      return base;
   }

   public static void setGem(ItemStack gemStack, Gem gem) {
      gemStack.m_41784_().m_128359_("gem", gem.getId().toString());
   }

   @Nullable
   public static Gem getGem(ItemStack gem) {
      if (gem.m_41720_() == Apoth.Items.GEM.get() && gem.m_41782_()) {
         CompoundTag tag = gem.m_41783_();
         return tag.m_128441_("gem") ? (Gem)GemManager.INSTANCE.getValue(new ResourceLocation(tag.m_128461_("gem"))) : null;
      } else {
         return null;
      }
   }

   public static void setLootRarity(ItemStack stack, LootRarity rarity) {
      stack.m_41784_().m_128359_("rarity", rarity.id());
   }

   @Nullable
   public static LootRarity getLootRarity(ItemStack stack) {
      Gem gem = getGem(stack);
      return gem == null ? null : gem.clamp(AffixHelper.getRarity(stack.m_41783_()));
   }
}
