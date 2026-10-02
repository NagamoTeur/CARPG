package daripher.skilltree.compat.apotheosis.gem;

import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.item.ItemSkillBonus;
import daripher.skilltree.skill.bonus.player.AttributeBonus;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootRarity;

public class PSTGemBonus extends GemBonus {
   public static Codec<ItemBonus<?>> BONUS_CODEC = new Codec<ItemBonus<?>>() {
      public <T> DataResult<Pair<ItemBonus<?>, T>> decode(DynamicOps<T> ops, T input) {
         JsonObject json = ((JsonElement)ops.convertTo(JsonOps.INSTANCE, input)).getAsJsonObject();
         ItemBonus<?> bonus = SerializationHelper.deserializeItemBonus(json);
         return DataResult.success(Pair.of(bonus, input));
      }

      public <T> DataResult<T> encode(ItemBonus<?> input, DynamicOps<T> ops, T prefix) {
         JsonObject json = new JsonObject();
         SerializationHelper.serializeItemBonus(json, input);
         return DataResult.success(JsonOps.INSTANCE.convertTo(ops, json));
      }
   };
   public static Codec<PSTGemBonus> CODEC = RecordCodecBuilder.create(
      i -> i.group(gemClass(), BONUS_CODEC.fieldOf("bonus").forGetter(b -> b.bonus)).apply(i, PSTGemBonus::new)
   );
   private ItemBonus<?> bonus;

   public PSTGemBonus(GemClass gemClass, ItemBonus<?> bonus) {
      super(new ResourceLocation("skilltree", "gem_bonus"), gemClass);
      this.bonus = bonus;
   }

   public PSTGemBonus copy() {
      return new PSTGemBonus(this.gemClass, this.bonus.copy());
   }

   public PSTGemBonus multiply(float multiplier) {
      this.bonus = this.bonus.copy().multiply((double)multiplier);
      return this;
   }

   public ItemBonus<?> getBonus(ItemStack gemStack) {
      float gemPower = 1.0F;
      CompoundTag tag = gemStack.m_41784_();
      if (tag.m_128441_("gem_power")) {
         gemPower = tag.m_128457_("gem_power");
      }

      ItemBonus<?> bonus = this.bonus.copy().multiply((double)gemPower);
      if (bonus instanceof ItemSkillBonus aBonus && aBonus.getBonus() instanceof AttributeBonus atBonus) {
         atBonus.setUUID((UUID)GemItem.getUUIDs(gemStack).get(0));
      }

      return bonus;
   }

   public GemBonus validate() {
      Preconditions.checkNotNull(this.bonus, "Invalid PSTGemBonus with null bonus");
      return this;
   }

   public boolean supports(LootRarity lootRarity) {
      return true;
   }

   public int getNumberOfUUIDs() {
      return 1;
   }

   public Component getSocketBonusTooltip(ItemStack gemStack, LootRarity lootRarity) {
      float gemPower = 1.0F;
      CompoundTag tag = gemStack.m_41784_();
      if (tag.m_128441_("gem_power")) {
         gemPower = tag.m_128457_("gem_power");
      }

      ItemBonus<?> bonus = this.bonus.copy().multiply((double)gemPower);
      return bonus.getTooltip().m_130948_(TooltipHelper.getSkillBonusStyle(bonus.isPositive()));
   }

   public Codec<? extends GemBonus> getCodec() {
      return CODEC;
   }
}
