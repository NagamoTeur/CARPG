package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.api.generic.SubscribeConfig;
import com.aizistral.enigmaticlegacy.api.items.ISpellstone;
import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemSpellstoneCurio;
import com.aizistral.omniconfig.wrappers.Omniconfig;
import com.aizistral.omniconfig.wrappers.OmniconfigWrapper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;

public class GolemHeart extends ItemSpellstoneCurio implements ISpellstone {
   public static final List<Item> EXCLUDED_ARMOR = new ArrayList<>();
   private static String[] excludedArmorRaw = new String[0];
   public static Omniconfig.IntParameter spellstoneCooldown;
   public static Omniconfig.DoubleParameter defaultArmorBonus;
   public static Omniconfig.DoubleParameter superArmorBonus;
   public static Omniconfig.DoubleParameter superArmorToughnessBonus;
   public static Omniconfig.PerhapsParameter knockbackResistance;
   public static Omniconfig.PerhapsParameter meleeResistance;
   public static Omniconfig.PerhapsParameter explosionResistance;
   public static Omniconfig.DoubleParameter vulnerabilityModifier;
   public Object model;
   public Multimap<Attribute, AttributeModifier> attributesDefault = HashMultimap.create();
   public Multimap<Attribute, AttributeModifier> attributesNoArmor = HashMultimap.create();

   @SubscribeConfig
   public static void onConfig(OmniconfigWrapper builder) {
      builder.pushPrefix("GolemHeart");
      spellstoneCooldown = builder.comment("Active ability cooldown for Heart of the Golem. Measured in ticks. 20 ticks equal to 1 second.")
         .getInt("Cooldown", 0);
      defaultArmorBonus = builder.comment("Default amount of armor points provided by Heart of the Golem.").max(256.0).getDouble("DefaultArmor", 4.0);
      superArmorBonus = builder.comment("The amount of armor points provided by Heart of the Golem when it's bearer has no armor equipped.")
         .max(256.0)
         .getDouble("SuperArmor", 16.0);
      superArmorToughnessBonus = builder.comment("The amount of armor toughness provided by Heart of the Golem when it's bearer has no armor equipped.")
         .max(256.0)
         .getDouble("SuperArmorToughness", 4.0);
      meleeResistance = builder.comment("Resistance to melee attacks provided by Heart of the Golem. Defined as percentage.")
         .max(100.0)
         .getPerhaps("MeleeResistance", 25);
      explosionResistance = builder.comment("Resistance to explosion damage provided by Heart of the Golem. Defined as percentage.")
         .max(100.0)
         .getPerhaps("ExplosionResistance", 40);
      knockbackResistance = builder.comment("Resistance to knockback provided by Heart of the Golem. Defined as percentage.")
         .max(100.0)
         .getPerhaps("KnockbackResistance", 100);
      vulnerabilityModifier = builder.comment(
            "Modifier for Magic Damage vulnerability applied by Heart of the Golem. Default value of 2.0 means that player will receive twice as much damage from magic."
         )
         .min(1.0)
         .max(256.0)
         .getDouble("VulnerabilityModifier", 2.0);
      builder.popPrefix();
      excludedArmorRaw = builder.config
         .getStringList(
            "GolemHeartExcludedArmor",
            builder.getCurrentCategory(),
            new String[]{"minecraft:elytra", "enigmaticlegacy:enigmatic_elytra"},
            "List of items that should not be counted as armor by Heart of the Golem, even when equipped in armor slots."
         );
   }

   public static void buildArmorExclusions() {
      Arrays.stream(excludedArmorRaw).forEach(entry -> {
         Item item = (Item)ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry));
         if (item != null) {
            EXCLUDED_ARMOR.add(item);
         } else {
            EnigmaticLegacy.LOGGER.error("Could not find excluded item when parsing config: " + entry);
         }
      });
   }

   public GolemHeart() {
      super(ItemSpellstoneCurio.getDefaultProperties().m_41497_(Rarity.UNCOMMON));
      this.immunityList.add(DamageSource.f_19314_.f_19326_);
      this.immunityList.add(DamageSource.f_19311_.f_19326_);
      this.immunityList.add(DamageSource.f_19310_.f_19326_);
      this.immunityList.add(DamageSource.f_19322_.f_19326_);
      this.immunityList.add(DamageSource.f_19325_.f_19326_);
      Supplier<Float> meleeResistanceSupplier = () -> meleeResistance.getValue().asModifierInverted();
      Supplier<Float> explosionResistanceSupplier = () -> explosionResistance.getValue().asModifierInverted();
      Supplier<Float> magicVulnerabilitySupplier = () -> (float)vulnerabilityModifier.getValue();
      this.resistanceList.put(DamageSource.f_19318_.f_19326_, meleeResistanceSupplier);
      this.resistanceList.put("mob", meleeResistanceSupplier);
      this.resistanceList.put("player", meleeResistanceSupplier);
      this.resistanceList.put("explosion", explosionResistanceSupplier);
      this.resistanceList.put("explosion.player", explosionResistanceSupplier);
      this.resistanceList.put(DamageSource.f_19319_.f_19326_, magicVulnerabilitySupplier);
      this.resistanceList.put(DamageSource.f_19320_.f_19326_, magicVulnerabilitySupplier);
      this.resistanceList.put(DamageSource.f_19323_.f_19326_, magicVulnerabilitySupplier);
      this.initAttributes();
   }

   private void initAttributes() {
      this.attributesDefault
         .put(
            Attributes.f_22284_,
            new AttributeModifier(UUID.fromString("15faf191-bf21-4654-b359-cc1f4f1243bf"), "GolemHeart DAB", defaultArmorBonus.getValue(), Operation.ADDITION)
         );
      this.attributesDefault
         .put(
            Attributes.f_22278_,
            new AttributeModifier(
               UUID.fromString("10faf191-bf21-4554-b359-cc1f4f1233bf"),
               "GolemHeart KR",
               (double)knockbackResistance.getValue().asModifier(false),
               Operation.ADDITION
            )
         );
      this.attributesNoArmor
         .put(
            Attributes.f_22284_,
            new AttributeModifier(UUID.fromString("14faf191-bf23-4654-b359-cc1f4f1243bf"), "GolemHeart SAB", superArmorBonus.getValue(), Operation.ADDITION)
         );
      this.attributesNoArmor
         .put(
            Attributes.f_22285_,
            new AttributeModifier(
               UUID.fromString("11faf181-bf23-4354-b359-cc1f5f1253bf"), "GolemHeart STB", superArmorToughnessBonus.getValue(), Operation.ADDITION
            )
         );
      this.attributesNoArmor
         .put(
            Attributes.f_22278_,
            new AttributeModifier(
               UUID.fromString("12faf181-bf21-4554-b359-cc1f4f1254bf"),
               "GolemHeart KR",
               (double)knockbackResistance.getValue().asModifier(false),
               Operation.ADDITION
            )
         );
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart2");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.golemHeartCooldown", ChatFormatting.GOLD, (float)spellstoneCooldown.getValue() / 20.0F
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart3");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart4", ChatFormatting.GOLD, (int)defaultArmorBonus.getValue());
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart5");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.golemHeart6", ChatFormatting.GOLD, (int)superArmorBonus.getValue(), (int)superArmorToughnessBonus.getValue()
         );
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.golemHeart7", ChatFormatting.GOLD, explosionResistance.getValue().asPercentage() + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart8", ChatFormatting.GOLD, meleeResistance.getValue().asPercentage() + "%");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.golemHeart9", ChatFormatting.GOLD, knockbackResistance.getValue().asPercentage() + "%"
         );
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart10");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.golemHeart11");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      try {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list,
            "tooltip.enigmaticlegacy.currentKeybind",
            ChatFormatting.LIGHT_PURPLE,
            ((Component)KeyMapping.m_90842_("key.spellstoneAbility").get()).getString().toUpperCase()
         );
      } catch (NullPointerException var6) {
      }
   }

   @Override
   public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
      if (context.entity() instanceof ServerPlayer player) {
         AttributeMap map = player.m_21204_();
         map.m_22161_(this.attributesDefault);
         map.m_22161_(this.attributesNoArmor);
      }
   }

   @Override
   public void curioTick(SlotContext context, ItemStack stack) {
      if (context.entity() instanceof ServerPlayer player) {
         AttributeMap map = player.m_21204_();
         if (SuperpositionHandler.hasAnyArmor(player)) {
            map.m_22161_(this.attributesDefault);
            map.m_22161_(this.attributesNoArmor);
            map.m_22178_(this.attributesDefault);
         } else {
            map.m_22161_(this.attributesDefault);
            map.m_22161_(this.attributesNoArmor);
            map.m_22178_(this.attributesNoArmor);
         }
      }
   }
}
