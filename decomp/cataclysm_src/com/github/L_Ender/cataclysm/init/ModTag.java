package com.github.L_Ender.cataclysm.init;

import com.github.L_Ender.cataclysm.util.LazyTagLookup;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTag {
   public static final TagKey<Block> NETHERITE_MONSTROSITY_IMMUNE = registerBlockTag("netherite_monstrosity_immune");
   public static final TagKey<EntityType<?>> TRAP_BLOCK_NOT_DETECTED = registerEntityTag("trap_block_not_detected");
   public static final TagKey<EntityType<?>> SANDSTONE_TRAP_NOT_DETECTED = registerEntityTag("sandstone_trap_not_detected");
   public static final TagKey<EntityType<?>> IGNIS_CANT_POKE = registerEntityTag("ignis_cant_poke");
   public static final TagKey<EntityType<?>> LAVA_MONSTER = registerEntityTag("lava_monster");
   public static final TagKey<EntityType<?>> LEVIATHAN_TARGET = registerEntityTag("leviathan_target");
   public static final TagKey<EntityType<?>> BABY_LEVIATHAN_TARGET = registerEntityTag("baby_leviathan_target");
   public static final TagKey<EntityType<?>> ANCIENT_REMNANT_TARGET = registerEntityTag("ancient_remnant_target");
   public static final TagKey<EntityType<?>> MODERN_REMNANT_TARGET = registerEntityTag("modern_remnant_target");
   public static final TagKey<EntityType<?>> TEAM_ANCIENT_REMNANT = registerEntityTag("team_ancient_remnant");
   public static final TagKey<EntityType<?>> TEAM_ENDER_GUARDIAN = registerEntityTag("team_ender_guardian");
   public static final TagKey<EntityType<?>> TEAM_IGNIS = registerEntityTag("team_ignis");
   public static final TagKey<EntityType<?>> TEAM_MONSTROSITY = registerEntityTag("team_monstrosity");
   public static final TagKey<EntityType<?>> TEAM_THE_HARBINGER = registerEntityTag("team_the_harbinger");
   public static final TagKey<EntityType<?>> TEAM_THE_LEVIATHAN = registerEntityTag("team_the_leviathan");
   public static final TagKey<EntityType<?>> DIMENSIONAL_LIFT_IMMUNE = registerEntityTag("dimensional_lift_immune");
   public static final TagKey<EntityType<?>> TEAM_MALEDICTUS = registerEntityTag("team_maledictus");
   public static final TagKey<Block> ENDER_GOLEM_CAN_DESTROY = registerBlockTag("ender_golem_can_destroy");
   public static final TagKey<Block> CM_GLASS = registerBlockTag("cm_glass");
   public static final TagKey<Block> ENDER_GUARDIAN_CAN_DESTROY = registerBlockTag("ender_guardian_can_destroy");
   public static final TagKey<Block> ALTAR_DESTROY_IMMUNE = registerBlockTag("altar_destroy_immune");
   public static final TagKey<Block> IGNIS_CAN_DESTROY_CRACKED_BLOCK = registerBlockTag("ignis_can_destroy_cracked_block");
   public static final TagKey<Block> IGNIS_IMMUNE = registerBlockTag("ignis_immune");
   public static final TagKey<Block> HARBINGER_IMMUNE = registerBlockTag("harbinger_immune");
   public static final TagKey<Block> LEVIATHAN_IMMUNE = registerBlockTag("leviathan_immune");
   public static final TagKey<Block> REMNANT_IMMUNE = registerBlockTag("remnant_immune");
   public static final TagKey<Block> MALEDICTUS_IMMUNE = registerBlockTag("maledictus_immune");
   public static final TagKey<Block> FROSTED_PRISON_CHANDELIER = registerBlockTag("frosted_prison_chandelier");
   public static final TagKey<Block> CORALSSUS_BREAK = registerBlockTag("coralssus_break");
   public static final TagKey<Block> ENDERMAPTERA_CAN_NOT_SPAWN = registerBlockTag("endermaptera_can_not_spawn");
   public static final TagKey<Block> NETHERITE_MONSTROSITY_BREAK = registerBlockTag("netherite_monstrosity_break");
   public static final TagKey<Block> SUNKEN_CITY_MATERIAL = registerBlockTag("sunken_city_material");
   public static final TagKey<Structure> EYE_OF_MECH_LOCATED = registerStructureTag("eye_of_mech_located");
   public static final TagKey<Structure> EYE_OF_RUINED_LOCATED = registerStructureTag("eye_of_ruined_located");
   public static final TagKey<Structure> EYE_OF_FLAME_LOCATED = registerStructureTag("eye_of_flame_located");
   public static final TagKey<Structure> EYE_OF_CURSE_LOCATED = registerStructureTag("eye_of_curse_located");
   public static final TagKey<Structure> EYE_OF_MONSTROUS_LOCATED = registerStructureTag("eye_of_monstrous_located");
   public static final TagKey<Structure> EYE_OF_ABYSS_LOCATED = registerStructureTag("eye_of_abyss_located");
   public static final TagKey<Structure> EYE_OF_DESERT_LOCATED = registerStructureTag("eye_of_desert_located");
   public static final TagKey<Structure> BLOCKED_BASALT = registerStructureTag("blocked_basalt");
   public static final TagKey<Structure> BLOCKED_LAKE = registerStructureTag("blocked_lake");
   public static final TagKey<Structure> BLOCKED_GEODE = registerStructureTag("blocked_geode");
   public static final TagKey<Structure> BLOCKED_ORE = registerStructureTag("blocked_ore");
   public static final TagKey<Structure> BLOCKED_MULTIFACE = registerStructureTag("blocked_multiface");
   public static final TagKey<Structure> BLOCKED_MAGMA_BLOCK = registerStructureTag("blocked_magma_block");
   public static final TagKey<Structure> BERSERKER_SPAWN = registerStructureTag("berserker_spawn");
   public static final TagKey<Item> EXPLOSION_IMMUNE_ITEM = registerItemTag("explosion_immune_item");
   public static final TagKey<Item> STICKY_ITEM = registerItemTag("sticky_item");
   public static final TagKey<Item> BONE_ITEM = registerItemTag("bone_item");
   public static final TagKey<Item> MINISTROSITY_BLACKLIST = registerItemTag("ministrosity_blacklist");
   public static final TagKey<MobEffect> EFFECTIVE_FOR_BOSSES = registerEffectTag("effective_for_bosses");
   public static final LazyTagLookup<MobEffect> EFFECTIVE_FOR_BOSSES_LOOKUP = LazyTagLookup.create(ForgeRegistries.MOB_EFFECTS, EFFECTIVE_FOR_BOSSES);
   public static final TagKey<Biome> REQUIRED_SUNKEN_CITY_SURROUNDING = registerBiomeTag("required_sunken_city_surrounding");

   private static TagKey<EntityType<?>> registerEntityTag(String name) {
      return TagKey.m_203882_(Registry.f_122903_, new ResourceLocation("cataclysm", name));
   }

   private static TagKey<Item> registerItemTag(String name) {
      return TagKey.m_203882_(Registry.f_122904_, new ResourceLocation("cataclysm", name));
   }

   private static TagKey<Block> registerBlockTag(String name) {
      return TagKey.m_203882_(Registry.f_122901_, new ResourceLocation("cataclysm", name));
   }

   private static TagKey<MobEffect> registerEffectTag(String name) {
      return TagKey.m_203882_(Registry.f_122900_, new ResourceLocation("cataclysm", name));
   }

   private static TagKey<Structure> registerStructureTag(String name) {
      return TagKey.m_203882_(Registry.f_235725_, new ResourceLocation("cataclysm", name));
   }

   private static TagKey<Biome> registerBiomeTag(String name) {
      return TagKey.m_203882_(Registry.f_122885_, new ResourceLocation("cataclysm", name));
   }

   public static <T> boolean isInTag(T value, TagKey<T> tagKey) {
      Registry<T> registry = (Registry<T>)BuiltinRegistries.f_123858_.m_7745_(tagKey.f_203867_().m_135782_());
      return registry.m_203561_(tagKey).m_203333_(registry.m_206081_((ResourceKey)registry.m_7854_(value).orElseThrow()));
   }
}
