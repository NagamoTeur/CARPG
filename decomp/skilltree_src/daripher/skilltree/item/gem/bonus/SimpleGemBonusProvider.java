package daripher.skilltree.item.gem.bonus;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTGemBonuses;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.item.gem.GemItem;
import daripher.skilltree.network.NetworkHelper;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SimpleGemBonusProvider implements GemBonusProvider {
   private final ItemBonus<?> bonus;

   public SimpleGemBonusProvider(ItemBonus<?> bonus) {
      this.bonus = bonus;
   }

   @Nullable
   @Override
   public ItemBonus<?> getBonus(Player player, ItemStack itemStack, ItemStack gemStack) {
      return this.bonus;
   }

   @Override
   public boolean canApply(Player player, ItemStack itemStack, ItemStack gemStack) {
      int socket = ItemHelper.getFirstEmptySocket(itemStack, player);
      return GemItem.hasGem(itemStack, socket) ? false : this.getBonus(player, itemStack, gemStack) != null;
   }

   @Override
   public MutableComponent getTooltip(ItemStack gemStack) {
      return this.bonus.getTooltip().m_130948_(TooltipHelper.getSkillBonusStyle(this.bonus.isPositive()));
   }

   @Override
   public GemBonusProvider.Serializer getSerializer() {
      return (GemBonusProvider.Serializer)PSTGemBonuses.SIMPLE.get();
   }

   public static class Serializer implements GemBonusProvider.Serializer {
      public GemBonusProvider deserialize(JsonObject json) throws JsonParseException {
         return new SimpleGemBonusProvider(SerializationHelper.deserializeItemBonus(json));
      }

      public void serialize(JsonObject json, GemBonusProvider provider) {
         if (provider instanceof SimpleGemBonusProvider aProvider) {
            SerializationHelper.serializeItemBonus(json, aProvider.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GemBonusProvider deserialize(CompoundTag tag) {
         return new SimpleGemBonusProvider(SerializationHelper.deserializeItemBonus(tag));
      }

      public CompoundTag serialize(GemBonusProvider provider) {
         if (provider instanceof SimpleGemBonusProvider aProvider) {
            CompoundTag tag = new CompoundTag();
            SerializationHelper.serializeItemBonus(tag, aProvider.bonus);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public GemBonusProvider deserialize(FriendlyByteBuf buf) {
         return new SimpleGemBonusProvider(NetworkHelper.readItemBonus(buf));
      }

      public void serialize(FriendlyByteBuf buf, GemBonusProvider provider) {
         if (provider instanceof SimpleGemBonusProvider aProvider) {
            NetworkHelper.writeItemBonus(buf, aProvider.bonus);
         } else {
            throw new IllegalArgumentException();
         }
      }
   }
}
