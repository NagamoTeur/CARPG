package daripher.skilltree.data.serializers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.init.PSTRegistries;
import daripher.skilltree.item.gem.bonus.GemBonusProvider;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.damage.DamageCondition;
import daripher.skilltree.skill.bonus.condition.damage.NoneDamageCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.EnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.NoneEnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.item.NoneItemCondition;
import daripher.skilltree.skill.bonus.condition.item.PotionCondition;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.condition.living.NoneLivingCondition;
import daripher.skilltree.skill.bonus.event.SkillEventListener;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.multiplier.LivingMultiplier;
import daripher.skilltree.skill.bonus.multiplier.NoneLivingMultiplier;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.common.CuriosHelper;
import top.theillusivec4.curios.common.CuriosHelper.SlotAttributeWrapper;

public class SerializationHelper {
   public static SkillBonus<?> deserializeSkillBonus(JsonObject json) {
      JsonObject bonusJson = json.getAsJsonObject("skill_bonus");
      String type = bonusJson.get("type").getAsString();
      ResourceLocation serializerId = new ResourceLocation(type);
      SkillBonus.Serializer serializer = (SkillBonus.Serializer)PSTRegistries.SKILL_BONUSES.get().getValue(serializerId);
      return (SkillBonus<?>)Objects.requireNonNull(serializer).deserialize(bonusJson);
   }

   public static void serializeSkillBonus(JsonObject json, SkillBonus<?> bonus) {
      ResourceLocation serializerId = PSTRegistries.SKILL_BONUSES.get().getKey(bonus.getSerializer());
      JsonObject bonusJson = new JsonObject();
      bonus.getSerializer().serialize(bonusJson, bonus);
      bonusJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("skill_bonus", bonusJson);
   }

   public static ItemBonus<?> deserializeItemBonus(JsonObject json) {
      JsonObject bonusJson = json.getAsJsonObject("item_bonus");
      String type = bonusJson.get("type").getAsString();
      ResourceLocation serializerId = new ResourceLocation(type);
      ItemBonus.Serializer serializer = (ItemBonus.Serializer)PSTRegistries.ITEM_BONUSES.get().getValue(serializerId);
      return (ItemBonus<?>)Objects.requireNonNull(serializer).deserialize(bonusJson);
   }

   public static void serializeItemBonus(JsonObject json, ItemBonus<?> bonus) {
      ResourceLocation serializerId = PSTRegistries.ITEM_BONUSES.get().getKey(bonus.getSerializer());
      JsonObject bonusJson = new JsonObject();
      bonus.getSerializer().serialize(bonusJson, bonus);
      bonusJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("item_bonus", bonusJson);
   }

   @NotNull
   public static Attribute deserializeAttribute(JsonObject json) {
      ResourceLocation attributeId = new ResourceLocation(json.get("attribute").getAsString());
      Attribute attribute;
      if (attributeId.m_135827_().equals("curios")) {
         attribute = CuriosHelper.getOrCreateSlotAttribute(attributeId.m_135815_());
      } else {
         attribute = (Attribute)ForgeRegistries.ATTRIBUTES.getValue(attributeId);
      }

      return attribute;
   }

   public static void serializeAttribute(JsonObject json, Attribute attribute) {
      ResourceLocation attributeId;
      if (attribute instanceof SlotAttributeWrapper wrapper) {
         attributeId = new ResourceLocation("curios", wrapper.identifier);
      } else {
         attributeId = ForgeRegistries.ATTRIBUTES.getKey(attribute);
      }

      assert attributeId != null;

      json.addProperty("attribute", attributeId.toString());
   }

   @NotNull
   public static AttributeModifier deserializeAttributeModifier(JsonObject json) {
      UUID id = UUID.fromString(json.get("id").getAsString());
      String name = json.get("name").getAsString();
      double amount = json.get("amount").getAsDouble();
      Operation operation = deserializeOperation(json);
      return new AttributeModifier(id, name, amount, operation);
   }

   public static void serializeAttributeModifier(JsonObject json, AttributeModifier modifier) {
      json.addProperty("id", modifier.m_22209_().toString());
      json.addProperty("name", modifier.m_22214_());
      json.addProperty("amount", modifier.m_22218_());
      serializeOperation(json, modifier.m_22217_());
   }

   @NotNull
   public static Operation deserializeOperation(JsonObject json) {
      return Operation.m_22236_(json.get("operation").getAsInt());
   }

   public static void serializeOperation(JsonObject json, Operation operation) {
      json.addProperty("operation", operation.m_22235_());
   }

   @Nonnull
   public static LivingMultiplier deserializeLivingMultiplier(JsonObject json, String name) {
      if (!json.has(name)) {
         return NoneLivingMultiplier.INSTANCE;
      } else {
         JsonObject multiplierJson = json.getAsJsonObject(name);
         ResourceLocation serializerId = new ResourceLocation(multiplierJson.get("type").getAsString());
         LivingMultiplier.Serializer serializer = (LivingMultiplier.Serializer)PSTRegistries.LIVING_MULTIPLIERS.get().getValue(serializerId);
         return Objects.requireNonNull(serializer).deserialize(multiplierJson);
      }
   }

   public static void serializeLivingMultiplier(JsonObject json, @Nonnull LivingMultiplier multiplier, String name) {
      JsonObject multiplierJson = new JsonObject();
      LivingMultiplier.Serializer serializer = multiplier.getSerializer();
      serializer.serialize(multiplierJson, multiplier);
      ResourceLocation serializerId = PSTRegistries.LIVING_MULTIPLIERS.get().getKey(serializer);
      multiplierJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add(name, multiplierJson);
   }

   @Nonnull
   public static LivingCondition deserializeLivingCondition(JsonObject json, String name) {
      if (!json.has(name)) {
         return NoneLivingCondition.INSTANCE;
      } else {
         JsonObject conditionJson = json.getAsJsonObject(name);
         ResourceLocation serializerId = new ResourceLocation(conditionJson.get("type").getAsString());
         LivingCondition.Serializer serializer = (LivingCondition.Serializer)PSTRegistries.LIVING_CONDITIONS.get().getValue(serializerId);
         String errorMessage = "Unknown living condition: " + serializerId;
         return deserializeObject(serializer, conditionJson, errorMessage);
      }
   }

   public static void serializeLivingCondition(JsonObject json, @Nonnull LivingCondition condition, String name) {
      JsonObject conditionJson = new JsonObject();
      LivingCondition.Serializer serializer = condition.getSerializer();
      serializer.serialize(conditionJson, condition);
      ResourceLocation serializerId = PSTRegistries.LIVING_CONDITIONS.get().getKey(serializer);

      assert serializerId != null;

      conditionJson.addProperty("type", serializerId.toString());
      json.add(name, conditionJson);
   }

   @Nonnull
   public static DamageCondition deserializeDamageCondition(JsonObject json) {
      String name = "damage_condition";
      if (!json.has(name)) {
         return NoneDamageCondition.INSTANCE;
      } else {
         JsonObject conditionJson = json.getAsJsonObject(name);
         ResourceLocation serializerId = new ResourceLocation(conditionJson.get("type").getAsString());
         DamageCondition.Serializer serializer = (DamageCondition.Serializer)PSTRegistries.DAMAGE_CONDITIONS.get().getValue(serializerId);
         String errorMessage = "Unknown damage condition: " + serializerId;
         return deserializeObject(serializer, conditionJson, errorMessage);
      }
   }

   public static void serializeDamageCondition(JsonObject json, @Nonnull DamageCondition condition) {
      JsonObject conditionJson = new JsonObject();
      DamageCondition.Serializer serializer = condition.getSerializer();
      serializer.serialize(conditionJson, condition);
      ResourceLocation serializerId = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(serializer);
      conditionJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("damage_condition", conditionJson);
   }

   @Nonnull
   public static ItemCondition deserializeItemCondition(JsonObject json) {
      String name = "item_condition";
      if (!json.has(name)) {
         return NoneItemCondition.INSTANCE;
      } else {
         JsonObject conditionJson = json.getAsJsonObject(name);
         ResourceLocation serializerId = new ResourceLocation(conditionJson.get("type").getAsString());
         ItemCondition.Serializer serializer = (ItemCondition.Serializer)PSTRegistries.ITEM_CONDITIONS.get().getValue(serializerId);
         String errorMessage = "Unknown item condition: " + serializerId;
         return deserializeObject(serializer, conditionJson, errorMessage);
      }
   }

   public static void serializeItemCondition(JsonObject json, @Nonnull ItemCondition condition) {
      JsonObject conditionJson = new JsonObject();
      ItemCondition.Serializer serializer = condition.getSerializer();
      serializer.serialize(conditionJson, condition);
      ResourceLocation serializerId = PSTRegistries.ITEM_CONDITIONS.get().getKey(serializer);
      conditionJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("item_condition", conditionJson);
   }

   @Nonnull
   public static SkillEventListener deserializeEventListener(JsonObject json) {
      JsonObject eventJson = json.getAsJsonObject("event_listener");
      ResourceLocation serializerId = new ResourceLocation(eventJson.get("type").getAsString());
      SkillEventListener.Serializer serializer = (SkillEventListener.Serializer)PSTRegistries.EVENT_LISTENERS.get().getValue(serializerId);
      String errorMessage = "Unknown event listener: " + serializerId;
      return deserializeObject(serializer, eventJson, errorMessage);
   }

   public static void serializeEventListener(JsonObject json, @Nonnull SkillEventListener condition) {
      JsonObject conditionJson = new JsonObject();
      SkillEventListener.Serializer serializer = condition.getSerializer();
      serializer.serialize(conditionJson, condition);
      ResourceLocation serializerId = PSTRegistries.EVENT_LISTENERS.get().getKey(serializer);
      conditionJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("event_listener", conditionJson);
   }

   @Nullable
   public static MobEffect deserializeEffect(JsonObject json) {
      if (!json.has("effect")) {
         return null;
      } else {
         ResourceLocation effectId = new ResourceLocation(json.get("effect").getAsString());
         return (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(effectId);
      }
   }

   public static void serializeEffect(JsonObject json, MobEffect effect) {
      ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
      json.addProperty("effect", Objects.requireNonNull(effectId).toString());
   }

   @Nullable
   public static PotionCondition.Type deserializePotionType(JsonObject json) {
      return PotionCondition.Type.byName(json.get("potion_type").getAsString());
   }

   public static void serializePotionType(JsonObject json, PotionCondition.Type type) {
      json.addProperty("potion_type", type.getName());
   }

   public static MobEffectInstance deserializeEffectInstance(JsonObject json) {
      MobEffect effect = deserializeEffect(json);
      int duration = json.get("duration").getAsInt();
      int amplifier = json.get("amplifier").getAsInt();
      return new MobEffectInstance(Objects.requireNonNull(effect), duration, amplifier);
   }

   public static void serializeEffectInstance(JsonObject json, MobEffectInstance effect) {
      serializeEffect(json, effect.m_19544_());
      json.addProperty("duration", effect.m_19557_());
      json.addProperty("amplifier", effect.m_19564_());
   }

   @Nonnull
   public static EnchantmentCondition deserializeEnchantmentCondition(JsonObject json) {
      String name = "enchantment_condition";
      if (!json.has(name)) {
         return NoneEnchantmentCondition.INSTANCE;
      } else {
         JsonObject conditionJson = json.getAsJsonObject(name);
         ResourceLocation serializerId = new ResourceLocation(conditionJson.get("type").getAsString());
         EnchantmentCondition.Serializer serializer = (EnchantmentCondition.Serializer)PSTRegistries.ENCHANTMENT_CONDITIONS.get().getValue(serializerId);
         String errorMessage = "Unknown enchantment condition: " + serializerId;
         return deserializeObject(serializer, conditionJson, errorMessage);
      }
   }

   public static void serializeEnchantmentCondition(JsonObject json, @Nonnull EnchantmentCondition condition) {
      JsonObject conditionJson = new JsonObject();
      EnchantmentCondition.Serializer serializer = condition.getSerializer();
      serializer.serialize(conditionJson, condition);
      ResourceLocation serializerId = PSTRegistries.ENCHANTMENT_CONDITIONS.get().getKey(serializer);
      conditionJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("enchantment_condition", conditionJson);
   }

   @Nonnull
   public static <T> List<T> deserializeObjects(JsonObject json, String elementName, Function<JsonObject, T> deserializer) {
      return StreamSupport.<Object>stream(json.getAsJsonArray(elementName).spliterator(), true).map(JsonObject.class::cast).map(deserializer).toList();
   }

   public static <T> void serializeObjects(JsonObject json, String elementName, List<T> objects, BiConsumer<JsonObject, T> serializer) {
      JsonArray objectsJson = new JsonArray();
      objects.forEach(object -> {
         JsonObject objectJson = new JsonObject();
         serializer.accept(objectJson, (T)object);
         objectsJson.add(objectJson);
      });
      json.add(elementName, objectsJson);
   }

   public static GemBonusProvider deserializeGemBonusProvider(JsonObject json) {
      JsonObject providerJson = json.getAsJsonObject("bonus_provider");
      String type = providerJson.get("type").getAsString();
      ResourceLocation serializerId = new ResourceLocation(type);
      GemBonusProvider.Serializer serializer = (GemBonusProvider.Serializer)PSTRegistries.GEM_BONUSES.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(providerJson);
   }

   public static void serializeGemBonusProvider(JsonObject json, GemBonusProvider provider) {
      ResourceLocation serializerId = PSTRegistries.GEM_BONUSES.get().getKey(provider.getSerializer());
      JsonObject bonusJson = new JsonObject();
      provider.getSerializer().serialize(bonusJson, provider);
      bonusJson.addProperty("type", Objects.requireNonNull(serializerId).toString());
      json.add("bonus_provider", bonusJson);
   }

   @Nullable
   public static Attribute deserializeAttribute(CompoundTag tag) {
      ResourceLocation attributeId = new ResourceLocation(tag.m_128461_("attribute"));
      Attribute attribute;
      if (attributeId.m_135827_().equals("curios")) {
         attribute = CuriosHelper.getOrCreateSlotAttribute(attributeId.m_135815_());
      } else {
         attribute = (Attribute)ForgeRegistries.ATTRIBUTES.getValue(attributeId);
      }

      if (attribute == null) {
         SkillTreeMod.LOGGER.error("Attribute {} doesn't exist!", attributeId);
      }

      return attribute;
   }

   public static void serializeAttribute(CompoundTag tag, Attribute attribute) {
      ResourceLocation attributeId;
      if (attribute instanceof SlotAttributeWrapper wrapper) {
         attributeId = new ResourceLocation("curios", wrapper.identifier);
      } else {
         attributeId = ForgeRegistries.ATTRIBUTES.getKey(attribute);
      }

      assert attributeId != null;

      tag.m_128359_("attribute", attributeId.toString());
   }

   @NotNull
   public static AttributeModifier deserializeAttributeModifier(CompoundTag tag) {
      UUID modifierId = UUID.fromString(tag.m_128461_("id"));
      String name = tag.m_128461_("name");
      double amount = tag.m_128459_("amount");
      Operation operation = deserializeOperation(tag);
      return new AttributeModifier(modifierId, name, amount, operation);
   }

   public static void serializeAttributeModifier(CompoundTag tag, AttributeModifier modifier) {
      tag.m_128359_("id", modifier.m_22209_().toString());
      tag.m_128359_("name", modifier.m_22214_());
      tag.m_128347_("amount", modifier.m_22218_());
      serializeOperation(tag, modifier.m_22217_());
   }

   @NotNull
   public static Operation deserializeOperation(CompoundTag tag) {
      return Operation.m_22236_(tag.m_128451_("operation"));
   }

   public static void serializeOperation(CompoundTag tag, Operation operation) {
      tag.m_128405_("operation", operation.m_22235_());
   }

   @Nonnull
   public static LivingMultiplier deserializeLivingMultiplier(CompoundTag tag, String name) {
      if (!tag.m_128441_(name)) {
         return NoneLivingMultiplier.INSTANCE;
      } else {
         CompoundTag multiplierTag = tag.m_128469_(name);
         ResourceLocation serializerId = new ResourceLocation(multiplierTag.m_128461_("type"));
         LivingMultiplier.Serializer serializer = (LivingMultiplier.Serializer)PSTRegistries.LIVING_MULTIPLIERS.get().getValue(serializerId);
         return Objects.requireNonNull(serializer).deserialize(multiplierTag);
      }
   }

   public static void serializeLivingMultiplier(CompoundTag tag, @Nonnull LivingMultiplier multiplier, String name) {
      LivingMultiplier.Serializer serializer = multiplier.getSerializer();
      CompoundTag multiplierTag = serializer.serialize(multiplier);
      ResourceLocation serializerId = PSTRegistries.LIVING_MULTIPLIERS.get().getKey(serializer);
      multiplierTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_(name, multiplierTag);
   }

   @Nonnull
   public static LivingCondition deserializeLivingCondition(CompoundTag tag, String name) {
      CompoundTag conditionTag = tag.m_128469_(name);
      ResourceLocation serializerId = new ResourceLocation(conditionTag.m_128461_("type"));
      LivingCondition.Serializer serializer = (LivingCondition.Serializer)PSTRegistries.LIVING_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(conditionTag);
   }

   public static void serializeLivingCondition(CompoundTag tag, @Nonnull LivingCondition condition, String name) {
      LivingCondition.Serializer serializer = condition.getSerializer();
      CompoundTag conditionTag = serializer.serialize(condition);
      ResourceLocation serializerId = PSTRegistries.LIVING_CONDITIONS.get().getKey(serializer);

      assert serializerId != null;

      conditionTag.m_128359_("type", serializerId.toString());
      tag.m_128365_(name, conditionTag);
   }

   @Nonnull
   public static DamageCondition deserializeDamageCondition(CompoundTag tag) {
      CompoundTag conditionTag = tag.m_128469_("damage_condition");
      ResourceLocation serializerId = new ResourceLocation(conditionTag.m_128461_("type"));
      DamageCondition.Serializer serializer = (DamageCondition.Serializer)PSTRegistries.DAMAGE_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(conditionTag);
   }

   public static void serializeDamageCondition(CompoundTag tag, @Nonnull DamageCondition condition) {
      DamageCondition.Serializer serializer = condition.getSerializer();
      CompoundTag conditionTag = serializer.serialize(condition);
      ResourceLocation serializerId = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(serializer);
      conditionTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("damage_condition", conditionTag);
   }

   @Nonnull
   public static ItemCondition deserializeItemCondition(CompoundTag tag) {
      CompoundTag conditionTag = tag.m_128469_("item_condition");
      ResourceLocation serializerId = new ResourceLocation(conditionTag.m_128461_("type"));
      ItemCondition.Serializer serializer = (ItemCondition.Serializer)PSTRegistries.ITEM_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(conditionTag);
   }

   public static void serializeItemCondition(CompoundTag tag, @Nonnull ItemCondition condition) {
      ItemCondition.Serializer serializer = condition.getSerializer();
      CompoundTag conditionTag = serializer.serialize(condition);
      ResourceLocation serializerId = PSTRegistries.ITEM_CONDITIONS.get().getKey(serializer);
      conditionTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("item_condition", conditionTag);
   }

   @Nonnull
   public static SkillEventListener deserializeEventListener(CompoundTag tag) {
      CompoundTag conditionTag = tag.m_128469_("event_listener");
      ResourceLocation serializerId = new ResourceLocation(conditionTag.m_128461_("type"));
      SkillEventListener.Serializer serializer = (SkillEventListener.Serializer)PSTRegistries.EVENT_LISTENERS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(conditionTag);
   }

   public static void serializeEventListener(CompoundTag tag, @Nonnull SkillEventListener condition) {
      SkillEventListener.Serializer serializer = condition.getSerializer();
      CompoundTag conditionTag = serializer.serialize(condition);
      ResourceLocation serializerId = PSTRegistries.EVENT_LISTENERS.get().getKey(serializer);
      conditionTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("event_listener", conditionTag);
   }

   @Nullable
   public static MobEffect deserializeEffect(CompoundTag tag) {
      if (!tag.m_128441_("effect")) {
         return null;
      } else {
         ResourceLocation effectId = new ResourceLocation(tag.m_128461_("effect"));
         return (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(effectId);
      }
   }

   public static void serializeEffect(CompoundTag tag, MobEffect effect) {
      ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
      tag.m_128359_("effect", Objects.requireNonNull(effectId).toString());
   }

   public static SkillBonus<?> deserializeSkillBonus(CompoundTag tag) {
      CompoundTag bonusTag = tag.m_128469_("skill_bonus");
      String type = bonusTag.m_128461_("type");
      ResourceLocation serializerId = new ResourceLocation(type);
      SkillBonus.Serializer serializer = (SkillBonus.Serializer)PSTRegistries.SKILL_BONUSES.get().getValue(serializerId);
      return (SkillBonus<?>)Objects.requireNonNull(serializer).deserialize(bonusTag);
   }

   public static void serializeSkillBonus(CompoundTag tag, SkillBonus<?> bonus) {
      ResourceLocation serializerId = PSTRegistries.SKILL_BONUSES.get().getKey(bonus.getSerializer());
      CompoundTag bonusTag = bonus.getSerializer().serialize(bonus);
      bonusTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("skill_bonus", bonusTag);
   }

   public static ItemBonus<?> deserializeItemBonus(CompoundTag tag) {
      CompoundTag bonusTag = tag.m_128469_("item_bonus");
      String type = bonusTag.m_128461_("type");
      ResourceLocation serializerId = new ResourceLocation(type);
      ItemBonus.Serializer serializer = (ItemBonus.Serializer)PSTRegistries.ITEM_BONUSES.get().getValue(serializerId);
      return (ItemBonus<?>)Objects.requireNonNull(serializer).deserialize(bonusTag);
   }

   public static void serializeItemBonus(CompoundTag tag, ItemBonus<?> bonus) {
      ResourceLocation serializerId = PSTRegistries.ITEM_BONUSES.get().getKey(bonus.getSerializer());
      CompoundTag bonusTag = bonus.getSerializer().serialize(bonus);
      bonusTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("item_bonus", bonusTag);
   }

   public static PotionCondition.Type deserializePotionType(CompoundTag tag) {
      return PotionCondition.Type.byName(tag.m_128461_("potion_type"));
   }

   public static void serializePotionType(CompoundTag tag, PotionCondition.Type type) {
      tag.m_128359_("category", type.getName());
   }

   public static MobEffectInstance deserializeEffectInstance(CompoundTag tag) {
      MobEffect effect = Objects.requireNonNull(deserializeEffect(tag));
      int duration = tag.m_128451_("duration");
      int amplifier = tag.m_128451_("amplifier");
      return new MobEffectInstance(effect, duration, amplifier);
   }

   public static void serializeEffectInstance(CompoundTag tag, MobEffectInstance effect) {
      serializeEffect(tag, effect.m_19544_());
      tag.m_128405_("duration", effect.m_19557_());
      tag.m_128405_("amplifier", effect.m_19564_());
   }

   @Nonnull
   public static EnchantmentCondition deserializeEnchantmentCondition(CompoundTag tag) {
      CompoundTag conditionTag = tag.m_128469_("enchantment_condition");
      ResourceLocation serializerId = new ResourceLocation(conditionTag.m_128461_("type"));
      EnchantmentCondition.Serializer serializer = (EnchantmentCondition.Serializer)PSTRegistries.ENCHANTMENT_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(conditionTag);
   }

   public static void serializeEnchantmentCondition(CompoundTag tag, @Nonnull EnchantmentCondition condition) {
      EnchantmentCondition.Serializer serializer = condition.getSerializer();
      CompoundTag conditionTag = serializer.serialize(condition);
      ResourceLocation serializerId = PSTRegistries.ENCHANTMENT_CONDITIONS.get().getKey(serializer);
      conditionTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("enchantment_condition", conditionTag);
   }

   @Nonnull
   public static <T> List<T> deserializeObjects(CompoundTag tag, String elementName, Function<CompoundTag, T> deserializer) {
      return tag.m_128437_(elementName, 10).stream().map(CompoundTag.class::cast).map(deserializer).toList();
   }

   public static <T> void serializeObjects(CompoundTag tag, String elementName, List<T> objects, BiConsumer<CompoundTag, T> serializer) {
      ListTag objectsTag = new ListTag();
      objects.forEach(o -> {
         CompoundTag objectTag = new CompoundTag();
         serializer.accept(objectTag, (T)o);
         objectsTag.add(objectTag);
      });
      tag.m_128365_(elementName, objectsTag);
   }

   public static GemBonusProvider deserializeGemBonusProvider(CompoundTag tag) {
      CompoundTag bonusTag = tag.m_128469_("bonus_provider");
      String type = bonusTag.m_128461_("type");
      ResourceLocation serializerId = new ResourceLocation(type);
      GemBonusProvider.Serializer serializer = (GemBonusProvider.Serializer)PSTRegistries.GEM_BONUSES.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(bonusTag);
   }

   public static void serializeGemBonusProvider(CompoundTag tag, GemBonusProvider provider) {
      ResourceLocation serializerId = PSTRegistries.GEM_BONUSES.get().getKey(provider.getSerializer());
      CompoundTag bonusTag = provider.getSerializer().serialize(provider);
      bonusTag.m_128359_("type", Objects.requireNonNull(serializerId).toString());
      tag.m_128365_("bonus_provider", bonusTag);
   }

   private static <T> T deserializeObject(Serializer<T> serializer, JsonObject jsonObject, String errorMessage) {
      return Objects.requireNonNull(serializer, errorMessage).deserialize(jsonObject);
   }

   public static JsonElement getElement(JsonObject json, String name) {
      JsonElement element = json.get(name);
      return Objects.requireNonNull(element, "Element not found: " + name);
   }
}
