package daripher.skilltree.item.gem.bonus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.init.PSTGemBonuses;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GemRemovalBonusProvider implements GemBonusProvider {
   @Nullable
   @Override
   public ItemBonus<?> getBonus(Player player, ItemStack itemStack, ItemStack gemStack) {
      return null;
   }

   @Override
   public boolean canApply(Player player, ItemStack itemStack, ItemStack gemStack) {
      return GemItem.hasGem(itemStack, 0);
   }

   @Override
   public void addGemBonus(Player player, ItemStack itemStack, ItemStack gemStack) {
      GemItem.removeGemBonuses(itemStack);
   }

   @Override
   public MutableComponent getTooltip(ItemStack gemStack) {
      MutableComponent tooltip = Component.m_237115_("gem_bonus.removal");
      return tooltip.m_130948_(TooltipHelper.getSkillBonusStyle(true));
   }

   @Override
   public GemBonusProvider.Serializer getSerializer() {
      return (GemBonusProvider.Serializer)PSTGemBonuses.GEM_REMOVAL.get();
   }

   public static class Serializer implements GemBonusProvider.Serializer {
      public GemBonusProvider deserialize(JsonObject json) throws JsonParseException {
         return new GemRemovalBonusProvider();
      }

      public void serialize(JsonObject json, GemBonusProvider provider) {
         if (!(provider instanceof GemRemovalBonusProvider)) {
            throw new IllegalArgumentException();
         }
      }

      public GemBonusProvider deserialize(CompoundTag tag) {
         return new GemRemovalBonusProvider();
      }

      public CompoundTag serialize(GemBonusProvider provider) {
         if (!(provider instanceof GemRemovalBonusProvider)) {
            throw new IllegalArgumentException();
         } else {
            return new CompoundTag();
         }
      }

      public GemBonusProvider deserialize(FriendlyByteBuf buf) {
         return new GemRemovalBonusProvider();
      }

      public void serialize(FriendlyByteBuf buf, GemBonusProvider provider) {
         if (!(provider instanceof GemRemovalBonusProvider)) {
            throw new IllegalArgumentException();
         }
      }
   }
}
