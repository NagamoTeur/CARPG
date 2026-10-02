package com.bobmowzie.mowziesmobs.server.loot;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.functions.LootItemFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class LootTableHandler {
   public static final ResourceLocation FERROUS_WROUGHTNAUT = register("entities/ferrous_wroughtnaut");
   public static final ResourceLocation LANTERN = register("entities/lantern");
   public static final ResourceLocation NAGA = register("entities/naga");
   public static final ResourceLocation FOLIAATH = register("entities/foliaath");
   public static final ResourceLocation GROTTOL = register("entities/grottol");
   public static final ResourceLocation FROSTMAW = register("entities/frostmaw");
   public static final ResourceLocation UMVUTHANA_FURY = register("entities/umvuthana_fury");
   public static final ResourceLocation UMVUTHANA_MISERY = register("entities/umvuthana_misery");
   public static final ResourceLocation UMVUTHANA_BLISS = register("entities/umvuthana_bliss");
   public static final ResourceLocation UMVUTHANA_RAGE = register("entities/umvuthana_rage");
   public static final ResourceLocation UMVUTHANA_FEAR = register("entities/umvuthana_fear");
   public static final ResourceLocation UMVUTHANA_FAITH = register("entities/umvuthana_faith");
   public static final ResourceLocation UMVUTHI = register("entities/umvuthi");
   public static final ResourceLocation UMVUTHANA_GROVE_CHEST = register("chests/umvuthana_grove_chest");
   public static LootItemFunctionType CHECK_FROSTMAW_CRYSTAL;
   public static LootItemFunctionType GROTTOL_DEATH_TYPE;
   public static LootItemConditionType FROSTMAW_HAS_CRYSTAL;

   public static void init() {
      GROTTOL_DEATH_TYPE = registerFunction("mowziesmobs:grottol_death_type", new LootFunctionGrottolDeathType.FunctionSerializer());
      FROSTMAW_HAS_CRYSTAL = registerCondition("mowziesmobs:has_crystal", new LootConditionFrostmawHasCrystal.ConditionSerializer());
   }

   private static ResourceLocation register(String id) {
      return new ResourceLocation("mowziesmobs", id);
   }

   private static LootItemFunctionType registerFunction(String name, Serializer<? extends LootItemFunction> serializer) {
      return (LootItemFunctionType)Registry.m_122965_(Registry.f_122876_, new ResourceLocation(name), new LootItemFunctionType(serializer));
   }

   private static LootItemConditionType registerCondition(String registryName, Serializer<? extends LootItemCondition> serializer) {
      return (LootItemConditionType)Registry.m_122965_(Registry.f_122877_, new ResourceLocation(registryName), new LootItemConditionType(serializer));
   }
}
