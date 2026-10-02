package daripher.skilltree.network;

import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.client.data.SkillTreeClientData;
import daripher.skilltree.config.Config;
import daripher.skilltree.init.PSTRegistries;
import daripher.skilltree.item.gem.GemType;
import daripher.skilltree.item.gem.bonus.GemBonusProvider;
import daripher.skilltree.skill.PassiveSkill;
import daripher.skilltree.skill.PassiveSkillTree;
import daripher.skilltree.skill.bonus.SkillBonus;
import daripher.skilltree.skill.bonus.condition.damage.DamageCondition;
import daripher.skilltree.skill.bonus.condition.enchantment.EnchantmentCondition;
import daripher.skilltree.skill.bonus.condition.item.ItemCondition;
import daripher.skilltree.skill.bonus.condition.living.LivingCondition;
import daripher.skilltree.skill.bonus.event.SkillEventListener;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import daripher.skilltree.skill.bonus.multiplier.LivingMultiplier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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

public class NetworkHelper {
   public static void writePassiveSkill(FriendlyByteBuf buf, PassiveSkill skill) {
      buf.m_130070_(skill.getId().toString());
      buf.writeInt(skill.getSkillSize());
      buf.m_130070_(skill.getFrameTexture().toString());
      buf.m_130070_(skill.getIconTexture().toString());
      buf.m_130070_(skill.getTooltipFrameTexture().toString());
      buf.writeBoolean(skill.isStartingPoint());
      buf.writeFloat(skill.getPositionX());
      buf.writeFloat(skill.getPositionY());
      buf.m_130070_(skill.getTitle());
      buf.m_130070_(skill.getTitleColor());
      writeResourceLocations(buf, skill.getDirectConnections());
      writeNullableResourceLocation(buf, skill.getConnectedTreeId());
      writeSkillBonuses(buf, skill.getBonuses());
      writeResourceLocations(buf, skill.getLongConnections());
      writeResourceLocations(buf, skill.getOneWayConnections());
      writeTags(buf, skill.getTags());
      writeDescription(buf, skill.getDescription());
   }

   public static PassiveSkill readPassiveSkill(FriendlyByteBuf buf) {
      ResourceLocation id = new ResourceLocation(buf.m_130277_());
      int size = buf.readInt();
      ResourceLocation background = new ResourceLocation(buf.m_130277_());
      ResourceLocation icon = new ResourceLocation(buf.m_130277_());
      ResourceLocation border = new ResourceLocation(buf.m_130277_());
      boolean startingPoint = buf.readBoolean();
      PassiveSkill skill = new PassiveSkill(id, size, background, icon, border, startingPoint);
      skill.setPosition(buf.readFloat(), buf.readFloat());
      skill.setTitle(buf.m_130277_());
      skill.setTitleColor(buf.m_130277_());
      skill.getDirectConnections().addAll(readResourceLocations(buf));
      skill.setConnectedTree(readNullableResourceLocation(buf));
      skill.getBonuses().addAll(readSkillBonuses(buf));
      skill.getLongConnections().addAll(readResourceLocations(buf));
      skill.getOneWayConnections().addAll(readResourceLocations(buf));
      skill.getTags().addAll(readTags(buf));
      skill.setDescription(readDescription(buf));
      return skill;
   }

   public static void writeAttribute(FriendlyByteBuf buf, Attribute attribute) {
      String attributeId;
      if (attribute instanceof SlotAttributeWrapper wrapper) {
         attributeId = "curios:" + wrapper.identifier;
      } else {
         attributeId = Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getKey(attribute)).toString();
      }

      buf.m_130070_(attributeId);
   }

   @Nullable
   public static Attribute readAttribute(FriendlyByteBuf buf) {
      String attributeId = buf.m_130277_();
      Attribute attribute;
      if (attributeId.startsWith("curios:")) {
         attributeId = attributeId.replace("curios:", "");
         attribute = CuriosHelper.getOrCreateSlotAttribute(attributeId);
      } else {
         attribute = (Attribute)ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attributeId));
      }

      if (attribute == null) {
         SkillTreeMod.LOGGER.error("Attribute {} does not exist", attributeId);
      }

      return attribute;
   }

   public static void writeAttributeModifier(FriendlyByteBuf buf, AttributeModifier modifier) {
      buf.writeLong(modifier.m_22209_().getMostSignificantBits());
      buf.writeLong(modifier.m_22209_().getLeastSignificantBits());
      buf.m_130070_(modifier.m_22214_());
      buf.writeDouble(modifier.m_22218_());
      writeOperation(buf, modifier.m_22217_());
   }

   @Nonnull
   public static AttributeModifier readAttributeModifier(FriendlyByteBuf buf) {
      UUID id = new UUID(buf.readLong(), buf.readLong());
      String name = buf.m_130277_();
      double amount = buf.readDouble();
      Operation operation = readOperation(buf);
      return new AttributeModifier(id, name, amount, operation);
   }

   public static void writeNullableResourceLocation(FriendlyByteBuf buf, @Nullable ResourceLocation location) {
      buf.writeBoolean(location != null);
      if (location != null) {
         buf.m_130070_(location.toString());
      }
   }

   @Nullable
   public static ResourceLocation readNullableResourceLocation(FriendlyByteBuf buf) {
      return buf.readBoolean() ? new ResourceLocation(buf.m_130277_()) : null;
   }

   public static void writeResourceLocations(FriendlyByteBuf buf, List<ResourceLocation> locations) {
      buf.writeInt(locations.size());
      locations.forEach(location -> buf.m_130070_(location.toString()));
   }

   public static List<ResourceLocation> readResourceLocations(FriendlyByteBuf buf) {
      int count = buf.readInt();
      List<ResourceLocation> locations = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         locations.add(new ResourceLocation(buf.m_130277_()));
      }

      return locations;
   }

   private static void writeTags(FriendlyByteBuf buf, List<String> tags) {
      buf.writeInt(tags.size());

      for (String tag : tags) {
         buf.m_130070_(tag);
      }
   }

   private static List<String> readTags(FriendlyByteBuf buf) {
      List<String> tags = new ArrayList<>();
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         tags.add(buf.m_130277_());
      }

      return tags;
   }

   public static void writeSkillBonuses(FriendlyByteBuf buf, List<SkillBonus<?>> bonuses) {
      buf.writeInt(bonuses.size());
      bonuses.forEach(bonus -> writeSkillBonus(buf, (SkillBonus<?>)bonus));
   }

   public static List<SkillBonus<?>> readSkillBonuses(FriendlyByteBuf buf) {
      int count = buf.readInt();
      List<SkillBonus<?>> bonuses = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         bonuses.add(readSkillBonus(buf));
      }

      return bonuses;
   }

   public static void writePassiveSkills(FriendlyByteBuf buf, Collection<PassiveSkill> skills) {
      buf.writeInt(skills.size());
      skills.forEach(skill -> writePassiveSkill(buf, skill));
   }

   public static List<PassiveSkill> readPassiveSkills(FriendlyByteBuf buf) {
      int count = buf.readInt();
      List<PassiveSkill> skills = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         skills.add(readPassiveSkill(buf));
      }

      return skills;
   }

   public static void writeSkillBonus(FriendlyByteBuf buf, SkillBonus<?> bonus) {
      SkillBonus.Serializer serializer = bonus.getSerializer();
      ResourceLocation serializerId = PSTRegistries.SKILL_BONUSES.get().getKey(serializer);
      Objects.requireNonNull(serializerId);
      buf.m_130070_(serializerId.toString());
      serializer.serialize(buf, bonus);
   }

   public static SkillBonus<?> readSkillBonus(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      SkillBonus.Serializer serializer = (SkillBonus.Serializer)PSTRegistries.SKILL_BONUSES.get().getValue(serializerId);
      Objects.requireNonNull(serializer);
      return (SkillBonus<?>)serializer.deserialize(buf);
   }

   public static void writeDescription(FriendlyByteBuf buf, @Nullable List<MutableComponent> description) {
      buf.writeBoolean(description != null);
      if (description != null) {
         buf.writeInt(description.size());

         for (MutableComponent component : description) {
            writeChatComponent(buf, component);
         }
      }
   }

   @Nullable
   public static List<MutableComponent> readDescription(FriendlyByteBuf buf) {
      if (!buf.readBoolean()) {
         return null;
      } else {
         int size = buf.readInt();
         List<MutableComponent> description = new ArrayList<>();

         for (int i = 0; i < size; i++) {
            description.add(readChatComponent(buf));
         }

         return description;
      }
   }

   public static void writeChatComponent(FriendlyByteBuf buf, MutableComponent component) {
      buf.m_130070_(component.getString());
      Style style = component.m_7383_();
      buf.writeBoolean(style.m_131154_());
      buf.writeBoolean(style.m_131161_());
      buf.writeBoolean(style.m_131171_());
      buf.writeBoolean(style.m_131168_());
      buf.writeBoolean(style.m_131176_());
      TextColor textColor = style.m_131135_();
      buf.writeInt(textColor == null ? -1 : textColor.m_131265_());
   }

   public static MutableComponent readChatComponent(FriendlyByteBuf buf) {
      String text = buf.m_130277_();
      Style style = Style.f_131099_
         .m_131136_(buf.readBoolean())
         .m_131155_(buf.readBoolean())
         .m_131162_(buf.readBoolean())
         .m_178522_(buf.readBoolean())
         .m_178524_(buf.readBoolean());
      int color = buf.readInt();
      if (color != -1) {
         style = style.m_178520_(color);
      }

      return Component.m_237113_(text).m_130948_(style);
   }

   public static void writePassiveSkillTrees(FriendlyByteBuf buf, Collection<PassiveSkillTree> skillTrees) {
      buf.writeInt(skillTrees.size());
      skillTrees.forEach(skillTree -> writePassiveSkillTree(buf, skillTree));
   }

   public static List<PassiveSkillTree> readPassiveSkillTrees(FriendlyByteBuf buf) {
      int count = buf.readInt();
      List<PassiveSkillTree> skillTrees = new ArrayList<>();

      for (int i = 0; i < count; i++) {
         skillTrees.add(readPassiveSkillTree(buf));
      }

      return skillTrees;
   }

   public static void writePassiveSkillTree(FriendlyByteBuf buf, PassiveSkillTree skillTree) {
      buf.m_130070_(skillTree.getId().toString());
      writeResourceLocations(buf, skillTree.getSkillIds());
      writeTagLimits(buf, skillTree.getSkillLimitations());
   }

   public static PassiveSkillTree readPassiveSkillTree(FriendlyByteBuf buf) {
      ResourceLocation id = new ResourceLocation(buf.m_130277_());
      PassiveSkillTree skillTree = new PassiveSkillTree(id);
      readResourceLocations(buf).forEach(skillTree.getSkillIds()::add);
      readTagLimits(buf).forEach(skillTree.getSkillLimitations()::put);
      return skillTree;
   }

   private static void writeTagLimits(FriendlyByteBuf buf, Map<String, Integer> limits) {
      buf.writeInt(limits.size());

      for (Entry<String, Integer> entry : limits.entrySet()) {
         buf.m_130070_(entry.getKey());
         buf.writeInt(entry.getValue());
      }
   }

   private static Map<String, Integer> readTagLimits(FriendlyByteBuf buf) {
      Map<String, Integer> limits = new HashMap<>();
      int size = buf.readInt();

      for (int i = 0; i < size; i++) {
         limits.put(buf.m_130277_(), buf.readInt());
      }

      return limits;
   }

   public static void writeLivingMultiplier(FriendlyByteBuf buf, @Nonnull LivingMultiplier multiplier) {
      LivingMultiplier.Serializer serializer = multiplier.getSerializer();
      ResourceLocation serializerId = PSTRegistries.LIVING_MULTIPLIERS.get().getKey(serializer);
      buf.m_130070_(Objects.requireNonNull(serializerId).toString());
      serializer.serialize(buf, multiplier);
   }

   @Nonnull
   public static LivingMultiplier readLivingMultiplier(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      LivingMultiplier.Serializer serializer = (LivingMultiplier.Serializer)PSTRegistries.LIVING_MULTIPLIERS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeLivingCondition(FriendlyByteBuf buf, @Nonnull LivingCondition condition) {
      LivingCondition.Serializer serializer = condition.getSerializer();
      ResourceLocation serializerId = PSTRegistries.LIVING_CONDITIONS.get().getKey(serializer);
      buf.m_130070_(Objects.requireNonNull(serializerId).toString());
      serializer.serialize(buf, condition);
   }

   @Nonnull
   public static LivingCondition readLivingCondition(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      LivingCondition.Serializer serializer = (LivingCondition.Serializer)PSTRegistries.LIVING_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeDamageCondition(FriendlyByteBuf buf, @Nonnull DamageCondition condition) {
      DamageCondition.Serializer serializer = condition.getSerializer();
      ResourceLocation serializerId = PSTRegistries.DAMAGE_CONDITIONS.get().getKey(serializer);
      Objects.requireNonNull(serializerId);
      buf.m_130070_(serializerId.toString());
      serializer.serialize(buf, condition);
   }

   @Nonnull
   public static DamageCondition readDamageCondition(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      DamageCondition.Serializer serializer = (DamageCondition.Serializer)PSTRegistries.DAMAGE_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeItemCondition(FriendlyByteBuf buf, @Nonnull ItemCondition condition) {
      ItemCondition.Serializer serializer = condition.getSerializer();
      ResourceLocation serializerId = PSTRegistries.ITEM_CONDITIONS.get().getKey(serializer);
      buf.m_130070_(Objects.requireNonNull(serializerId).toString());
      serializer.serialize(buf, condition);
   }

   @Nonnull
   public static ItemCondition readItemCondition(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      ItemCondition.Serializer serializer = (ItemCondition.Serializer)PSTRegistries.ITEM_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeEventListener(FriendlyByteBuf buf, @Nonnull SkillEventListener condition) {
      SkillEventListener.Serializer serializer = condition.getSerializer();
      ResourceLocation serializerId = PSTRegistries.EVENT_LISTENERS.get().getKey(serializer);
      buf.m_130070_(Objects.requireNonNull(serializerId).toString());
      serializer.serialize(buf, condition);
   }

   @Nonnull
   public static SkillEventListener readEventListener(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      SkillEventListener.Serializer serializer = (SkillEventListener.Serializer)PSTRegistries.EVENT_LISTENERS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeEnchantmentCondition(FriendlyByteBuf buf, @Nonnull EnchantmentCondition condition) {
      EnchantmentCondition.Serializer serializer = condition.getSerializer();
      ResourceLocation serializerId = PSTRegistries.ENCHANTMENT_CONDITIONS.get().getKey(serializer);
      buf.m_130070_(Objects.requireNonNull(serializerId).toString());
      serializer.serialize(buf, condition);
   }

   @Nonnull
   public static EnchantmentCondition readEnchantmentCondition(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      EnchantmentCondition.Serializer serializer = (EnchantmentCondition.Serializer)PSTRegistries.ENCHANTMENT_CONDITIONS.get().getValue(serializerId);
      return Objects.requireNonNull(serializer).deserialize(buf);
   }

   public static void writeEffect(FriendlyByteBuf buf, MobEffect effect) {
      ResourceLocation effectId = ForgeRegistries.MOB_EFFECTS.getKey(effect);
      buf.m_130070_(Objects.requireNonNull(effectId).toString());
   }

   public static MobEffect readEffect(FriendlyByteBuf buf) {
      ResourceLocation effectId = new ResourceLocation(buf.m_130277_());
      return (MobEffect)ForgeRegistries.MOB_EFFECTS.getValue(effectId);
   }

   public static <T extends Enum<T>> void writeEnum(FriendlyByteBuf buf, T anEnum) {
      buf.writeInt(anEnum.ordinal());
   }

   @Nullable
   public static <T extends Enum<T>> T readEnum(FriendlyByteBuf buf, Class<T> type) {
      return type.getEnumConstants()[buf.readInt()];
   }

   public static void writeItemBonus(FriendlyByteBuf buf, ItemBonus<?> bonus) {
      ItemBonus.Serializer serializer = bonus.getSerializer();
      ResourceLocation serializerId = PSTRegistries.ITEM_BONUSES.get().getKey(serializer);
      Objects.requireNonNull(serializerId);
      buf.m_130070_(serializerId.toString());
      serializer.serialize(buf, bonus);
   }

   public static ItemBonus<?> readItemBonus(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      ItemBonus.Serializer serializer = (ItemBonus.Serializer)PSTRegistries.ITEM_BONUSES.get().getValue(serializerId);
      Objects.requireNonNull(serializer);
      return (ItemBonus<?>)serializer.deserialize(buf);
   }

   public static void writeOperation(FriendlyByteBuf buf, Operation operation) {
      buf.writeInt(operation.m_22235_());
   }

   @NotNull
   public static Operation readOperation(FriendlyByteBuf buf) {
      return Operation.m_22236_(buf.readInt());
   }

   public static void writeEffectInstance(FriendlyByteBuf buf, MobEffectInstance effect) {
      writeEffect(buf, effect.m_19544_());
      buf.writeInt(effect.m_19557_());
      buf.writeInt(effect.m_19564_());
   }

   @NotNull
   public static MobEffectInstance readEffectInstance(FriendlyByteBuf buf) {
      return new MobEffectInstance(readEffect(buf), buf.readInt(), buf.readInt());
   }

   public static void writeGemBonusProvider(FriendlyByteBuf buf, GemBonusProvider provider) {
      GemBonusProvider.Serializer serializer = provider.getSerializer();
      ResourceLocation serializerId = PSTRegistries.GEM_BONUSES.get().getKey(serializer);
      Objects.requireNonNull(serializerId);
      buf.m_130070_(serializerId.toString());
      serializer.serialize(buf, provider);
   }

   public static GemBonusProvider readGemBonusProvider(FriendlyByteBuf buf) {
      ResourceLocation serializerId = new ResourceLocation(buf.m_130277_());
      GemBonusProvider.Serializer serializer = (GemBonusProvider.Serializer)PSTRegistries.GEM_BONUSES.get().getValue(serializerId);
      Objects.requireNonNull(serializer);
      return serializer.deserialize(buf);
   }

   public static void writeGemTypes(FriendlyByteBuf buf, Collection<GemType> types) {
      buf.writeInt(types.size());
      types.forEach(t -> writeGemType(buf, t));
   }

   public static List<GemType> readGemTypes(FriendlyByteBuf buf) {
      int size = buf.readInt();
      List<GemType> list = new ArrayList<>();

      for (int i = 0; i < size; i++) {
         list.add(readGemType(buf));
      }

      return list;
   }

   private static void writeGemType(FriendlyByteBuf buf, GemType type) {
      buf.writeInt(type.bonuses().size());
      type.bonuses().forEach((c, p) -> {
         writeItemCondition(buf, c);
         writeGemBonusProvider(buf, p);
      });
      buf.m_130070_(type.id().toString());
   }

   public static GemType readGemType(FriendlyByteBuf buf) {
      int bonuses = buf.readInt();
      Map<ItemCondition, GemBonusProvider> bonusProviders = new HashMap<>();

      for (int i = 0; i < bonuses; i++) {
         bonusProviders.put(readItemCondition(buf), readGemBonusProvider(buf));
      }

      ResourceLocation id = new ResourceLocation(buf.m_130277_());
      return new GemType(id, bonusProviders);
   }

   public static void writeSkillTreeConfig(FriendlyByteBuf buf) {
      buf.writeBoolean(Config.use_skill_points_array);
      if (Config.use_skill_points_array) {
         buf.writeInt(Config.skill_points_costs.size());
         Config.skill_points_costs.forEach(buf::writeInt);
      }

      buf.writeInt(Config.max_skill_points);
      buf.writeInt(Config.first_skill_cost);
      buf.writeInt(Config.last_skill_cost);
      buf.writeBoolean(Config.enable_exp_exchange);
   }

   public static void loadSkillTreeConfig(FriendlyByteBuf buf) {
      SkillTreeClientData.use_skill_cost_array = buf.readBoolean();
      if (SkillTreeClientData.use_skill_cost_array) {
         SkillTreeClientData.skill_points_costs = new int[buf.readInt()];

         for (int i = 0; i < SkillTreeClientData.skill_points_costs.length; i++) {
            SkillTreeClientData.skill_points_costs[i] = buf.readInt();
         }
      }

      SkillTreeClientData.max_skill_points = buf.readInt();
      SkillTreeClientData.first_skill_cost = buf.readInt();
      SkillTreeClientData.last_skill_cost = buf.readInt();
      SkillTreeClientData.enable_exp_exchange = buf.readBoolean();
   }
}
