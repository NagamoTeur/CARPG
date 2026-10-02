package com.hollingsworth.arsnouveau.setup;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ArmorUpgradeRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantingApparatusRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.EnchantmentRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.ReactiveEnchantmentRecipe;
import com.hollingsworth.arsnouveau.api.enchanting_apparatus.SpellWriteRecipe;
import com.hollingsworth.arsnouveau.api.recipe.SummonRitualRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.BookUpgradeRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.CrushRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.DyeRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.ImbuementRecipe;
import com.hollingsworth.arsnouveau.common.crafting.recipes.PotionFlaskRecipe;
import com.hollingsworth.arsnouveau.common.tomes.CasterTomeData;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;

@EventBusSubscriber(
   bus = Bus.MOD
)
public class RecipeRegistry {
   public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(Keys.RECIPE_SERIALIZERS, "ars_nouveau");
   public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Keys.RECIPE_TYPES, "ars_nouveau");
   public static final String ENCHANTING_APPARATUS_RECIPE_ID = "enchanting_apparatus";
   public static final String ENCHANTMENT_RECIPE_ID = "enchantment";
   public static final String CRUSH_RECIPE_ID = "crush";
   public static final String IMBUEMENT_RECIPE_ID = "imbuement";
   public static final String REACTIVE_RECIPE_ID = "reactive_enchantment";
   public static final String SPELL_WRITE_RECIPE_ID = "spell_write";
   public static final String GLYPH_RECIPE_ID = "glyph";
   public static final String DYE_RECIPE_ID = "dye";
   public static final String ARMOR_RECIPE_ID = "armor_upgrade";
   public static final String TOME_DATAPACK = "caster_tome";
   public static final String SUMMON_RITUAL_DATAPACK = "summon_ritual";
   public static final RegistryObject<RecipeType<EnchantingApparatusRecipe>> APPARATUS_TYPE = RECIPE_TYPES.register(
      "enchanting_apparatus", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<EnchantingApparatusRecipe>> APPARATUS_SERIALIZER = RECIPE_SERIALIZERS.register(
      "enchanting_apparatus", () -> new EnchantingApparatusRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<EnchantmentRecipe>> ENCHANTMENT_TYPE = RECIPE_TYPES.register(
      "enchantment", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<EnchantmentRecipe>> ENCHANTMENT_SERIALIZER = RECIPE_SERIALIZERS.register(
      "enchantment", () -> new EnchantmentRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<CrushRecipe>> CRUSH_TYPE = RECIPE_TYPES.register("crush", () -> new RecipeRegistry.ModRecipeType());
   public static final RegistryObject<RecipeSerializer<CrushRecipe>> CRUSH_SERIALIZER = RECIPE_SERIALIZERS.register("crush", () -> new CrushRecipe.Serializer());
   public static final RegistryObject<RecipeType<ImbuementRecipe>> IMBUEMENT_TYPE = RECIPE_TYPES.register("imbuement", () -> new RecipeRegistry.ModRecipeType());
   public static final RegistryObject<RecipeSerializer<ImbuementRecipe>> IMBUEMENT_SERIALIZER = RECIPE_SERIALIZERS.register(
      "imbuement", () -> new ImbuementRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<BookUpgradeRecipe>> BOOK_UPGRADE_TYPE = RECIPE_TYPES.register(
      "book_upgrade", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<BookUpgradeRecipe>> BOOK_UPGRADE_RECIPE = RECIPE_SERIALIZERS.register(
      "book_upgrade", () -> new BookUpgradeRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<PotionFlaskRecipe>> POTION_FLASK_TYPE = RECIPE_TYPES.register(
      "potion_flask", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<PotionFlaskRecipe>> POTION_FLASK_RECIPE = RECIPE_SERIALIZERS.register(
      "potion_flask", () -> new PotionFlaskRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<DyeRecipe>> DYE_TYPE = RECIPE_TYPES.register("dye", () -> new RecipeRegistry.ModRecipeType());
   public static final RegistryObject<RecipeSerializer<DyeRecipe>> DYE_RECIPE = RECIPE_SERIALIZERS.register("dye", () -> new DyeRecipe.Serializer());
   public static final RegistryObject<RecipeType<ReactiveEnchantmentRecipe>> REACTIVE_TYPE = RECIPE_TYPES.register(
      "reactive_enchantment", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<ReactiveEnchantmentRecipe>> REACTIVE_RECIPE = RECIPE_SERIALIZERS.register(
      "reactive_enchantment", () -> new ReactiveEnchantmentRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<SpellWriteRecipe>> SPELL_WRITE_TYPE = RECIPE_TYPES.register(
      "spell_write", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<SpellWriteRecipe>> SPELL_WRITE_RECIPE = RECIPE_SERIALIZERS.register(
      "spell_write", () -> new SpellWriteRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<GlyphRecipe>> GLYPH_TYPE = RECIPE_TYPES.register("glyph", () -> new RecipeRegistry.ModRecipeType());
   public static final RegistryObject<RecipeSerializer<GlyphRecipe>> GLYPH_SERIALIZER = RECIPE_SERIALIZERS.register("glyph", () -> new GlyphRecipe.Serializer());
   public static final RegistryObject<RecipeType<ArmorUpgradeRecipe>> ARMOR_UPGRADE_TYPE = RECIPE_TYPES.register(
      "armor_upgrade", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<ArmorUpgradeRecipe>> ARMOR_SERIALIZER = RECIPE_SERIALIZERS.register(
      "armor_upgrade", () -> new ArmorUpgradeRecipe.Serializer()
   );
   public static final RegistryObject<RecipeType<CasterTomeData>> CASTER_TOME_TYPE = RECIPE_TYPES.register(
      "caster_tome", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<CasterTomeData>> CASTER_TOME_SERIALIZER = RECIPE_SERIALIZERS.register(
      "caster_tome", () -> new CasterTomeData.Serializer()
   );
   public static final RegistryObject<RecipeType<SummonRitualRecipe>> SUMMON_RITUAL_TYPE = RECIPE_TYPES.register(
      "summon_ritual", () -> new RecipeRegistry.ModRecipeType()
   );
   public static final RegistryObject<RecipeSerializer<SummonRitualRecipe>> SUMMON_RITUAL_SERIALIZER = RECIPE_SERIALIZERS.register(
      "summon_ritual", () -> new SummonRitualRecipe.Serializer()
   );

   private static class ModRecipeType<T extends Recipe<?>> implements RecipeType<T> {
      @Override
      public String toString() {
         return ForgeRegistries.RECIPE_TYPES.getKey(this).toString();
      }
   }
}
