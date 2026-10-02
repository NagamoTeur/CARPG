package daripher.skilltree.skill.bonus.player;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import daripher.skilltree.client.tooltip.TooltipHelper;
import daripher.skilltree.client.widget.SelectionList;
import daripher.skilltree.client.widget.editor.SkillTreeEditor;
import daripher.skilltree.data.serializers.SerializationHelper;
import daripher.skilltree.init.PSTSkillBonuses;
import daripher.skilltree.skill.bonus.SkillBonus;
import java.util.Objects;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;

public final class LootDuplicationBonus implements SkillBonus<LootDuplicationBonus> {
   private LootDuplicationBonus.LootType lootType;
   private float multiplier;
   private float chance;

   public LootDuplicationBonus(float chance, float multiplier, LootDuplicationBonus.LootType lootType) {
      this.chance = chance;
      this.multiplier = multiplier;
      this.lootType = lootType;
   }

   @Override
   public SkillBonus.Serializer getSerializer() {
      return (SkillBonus.Serializer)PSTSkillBonuses.LOOT_DUPLICATION.get();
   }

   public LootDuplicationBonus copy() {
      return new LootDuplicationBonus(this.chance, this.multiplier, this.lootType);
   }

   public LootDuplicationBonus multiply(double multiplier) {
      this.chance = (float)((double)this.chance * multiplier);
      return this;
   }

   @Override
   public boolean canMerge(SkillBonus<?> other) {
      if (other instanceof LootDuplicationBonus otherBonus) {
         return otherBonus.multiplier != this.multiplier ? false : Objects.equals(otherBonus.lootType, this.lootType);
      } else {
         return false;
      }
   }

   @Override
   public SkillBonus<LootDuplicationBonus> merge(SkillBonus<?> other) {
      if (other instanceof LootDuplicationBonus otherBonus) {
         return new LootDuplicationBonus(otherBonus.chance + this.chance, this.multiplier, this.lootType);
      } else {
         throw new IllegalArgumentException();
      }
   }

   @Override
   public MutableComponent getTooltip() {
      MutableComponent lootDescription = Component.m_237115_(this.lootType.getDescriptionId());
      String multiplierDescription = ItemStack.f_41584_.format((double)(this.multiplier * 100.0F));
      MutableComponent bonusDescription = Component.m_237110_(this.getDescriptionId(), new Object[]{multiplierDescription, lootDescription});
      return TooltipHelper.getSkillBonusTooltip(bonusDescription, (double)this.chance, Operation.MULTIPLY_BASE)
         .m_130948_(TooltipHelper.getSkillBonusStyle(this.isPositive()));
   }

   @Override
   public boolean isPositive() {
      return this.chance > 0.0F;
   }

   @Override
   public void addEditorWidgets(SkillTreeEditor editor, int row, Consumer<LootDuplicationBonus> consumer) {
      editor.addLabel(0, 0, "Chance", ChatFormatting.GOLD);
      editor.addLabel(110, 0, "Multiplier", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      editor.addNumericTextField(0, 0, 90, 14, (double)this.chance).setNumericResponder(value -> this.selectChance(consumer, value));
      editor.addNumericTextField(110, 0, 90, 14, (double)this.multiplier).setNumericResponder(value -> this.selectMultiplier(consumer, value));
      editor.increaseHeight(19);
      editor.addLabel(0, 0, "Loot Type", ChatFormatting.GOLD);
      editor.increaseHeight(19);
      SelectionList<LootDuplicationBonus.LootType> lootTypeSelection = editor.addSelection(0, 0, 200, 3, this.lootType)
         .setNameGetter(LootDuplicationBonus.LootType::getFormattedName)
         .setResponder(lootType -> this.selectLootType(consumer, lootType));
      editor.increaseHeight(lootTypeSelection.getMaxDisplayed() * 14 + 5);
   }

   private void selectLootType(Consumer<LootDuplicationBonus> consumer, LootDuplicationBonus.LootType lootType) {
      this.setLootType(lootType);
      consumer.accept(this.copy());
   }

   private void selectMultiplier(Consumer<LootDuplicationBonus> consumer, Double value) {
      this.setMultiplier(value.floatValue());
      consumer.accept(this.copy());
   }

   private void selectChance(Consumer<LootDuplicationBonus> consumer, Double value) {
      this.setChance(value.floatValue());
      consumer.accept(this.copy());
   }

   public void setChance(float chance) {
      this.chance = chance;
   }

   public void setMultiplier(float multiplier) {
      this.multiplier = multiplier;
   }

   public void setLootType(LootDuplicationBonus.LootType lootType) {
      this.lootType = lootType;
   }

   public float getChance() {
      return this.chance;
   }

   public float getMultiplier() {
      return this.multiplier;
   }

   public LootDuplicationBonus.LootType getLootType() {
      return this.lootType;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         LootDuplicationBonus that = (LootDuplicationBonus)o;
         if (Float.compare(this.multiplier, that.multiplier) != 0) {
            return false;
         } else {
            return Float.compare(this.chance, that.chance) != 0 ? false : this.lootType == that.lootType;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.lootType, this.multiplier, this.chance);
   }

   public static enum LootType {
      MOBS("mobs"),
      FISHING("fishing"),
      GEMS("gems");

      final String name;

      private LootType(String name) {
         this.name = name;
      }

      public String getName() {
         return this.name;
      }

      public Component getFormattedName() {
         return Component.m_237113_(this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1));
      }

      public static LootDuplicationBonus.LootType byName(String name) {
         for (LootDuplicationBonus.LootType type : values()) {
            if (type.name.equals(name)) {
               return type;
            }
         }

         return MOBS;
      }

      public String getDescriptionId() {
         return "loot.type." + this.getName();
      }
   }

   public static class Serializer implements SkillBonus.Serializer {
      public LootDuplicationBonus deserialize(JsonObject json) throws JsonParseException {
         float chance = SerializationHelper.getElement(json, "chance").getAsFloat();
         float multiplier = SerializationHelper.getElement(json, "multiplier").getAsFloat();
         LootDuplicationBonus.LootType lootType = LootDuplicationBonus.LootType.byName(json.get("loot_type").getAsString());
         return new LootDuplicationBonus(chance, multiplier, lootType);
      }

      public void serialize(JsonObject json, SkillBonus<?> bonus) {
         if (bonus instanceof LootDuplicationBonus aBonus) {
            json.addProperty("chance", aBonus.chance);
            json.addProperty("multiplier", aBonus.multiplier);
            json.addProperty("loot_type", aBonus.lootType.name);
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LootDuplicationBonus deserialize(CompoundTag tag) {
         float chance = tag.m_128457_("chance");
         float multiplier = tag.m_128457_("multiplier");
         LootDuplicationBonus.LootType lootType = LootDuplicationBonus.LootType.byName(tag.m_128461_("loot_type"));
         return new LootDuplicationBonus(chance, multiplier, lootType);
      }

      public CompoundTag serialize(SkillBonus<?> bonus) {
         if (bonus instanceof LootDuplicationBonus aBonus) {
            CompoundTag tag = new CompoundTag();
            tag.m_128350_("chance", aBonus.chance);
            tag.m_128350_("multiplier", aBonus.multiplier);
            tag.m_128359_("loot_type", aBonus.lootType.name);
            return tag;
         } else {
            throw new IllegalArgumentException();
         }
      }

      public LootDuplicationBonus deserialize(FriendlyByteBuf buf) {
         return new LootDuplicationBonus(buf.readFloat(), buf.readFloat(), LootDuplicationBonus.LootType.byName(buf.m_130277_()));
      }

      public void serialize(FriendlyByteBuf buf, SkillBonus<?> bonus) {
         if (bonus instanceof LootDuplicationBonus aBonus) {
            buf.writeFloat(aBonus.chance);
            buf.writeFloat(aBonus.multiplier);
            buf.m_130070_(aBonus.lootType.name);
         } else {
            throw new IllegalArgumentException();
         }
      }

      @Override
      public SkillBonus<?> createDefaultInstance() {
         return new LootDuplicationBonus(0.05F, 1.0F, LootDuplicationBonus.LootType.MOBS);
      }
   }
}
