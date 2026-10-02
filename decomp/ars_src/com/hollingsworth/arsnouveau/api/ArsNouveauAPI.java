package com.hollingsworth.arsnouveau.api;

import com.hollingsworth.arsnouveau.api.enchanting_apparatus.IEnchantingRecipe;
import com.hollingsworth.arsnouveau.api.familiar.AbstractFamiliarHolder;
import com.hollingsworth.arsnouveau.api.perk.IPerk;
import com.hollingsworth.arsnouveau.api.perk.IPerkProvider;
import com.hollingsworth.arsnouveau.api.recipe.PotionIngredient;
import com.hollingsworth.arsnouveau.api.ritual.AbstractRitual;
import com.hollingsworth.arsnouveau.api.scrying.IScryer;
import com.hollingsworth.arsnouveau.api.sound.SpellSound;
import com.hollingsworth.arsnouveau.api.spell.AbstractSpellPart;
import com.hollingsworth.arsnouveau.api.spell.ISpellValidator;
import com.hollingsworth.arsnouveau.common.items.FamiliarScript;
import com.hollingsworth.arsnouveau.common.items.Glyph;
import com.hollingsworth.arsnouveau.common.items.PerkItem;
import com.hollingsworth.arsnouveau.common.items.RitualTablet;
import com.hollingsworth.arsnouveau.common.mixin.PotionRecipeMixin;
import com.hollingsworth.arsnouveau.common.spell.validation.StandardSpellValidator;
import com.hollingsworth.arsnouveau.setup.Config;
import com.hollingsworth.arsnouveau.setup.config.ANModConfig;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing.Mix;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.brewing.BrewingRecipe;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.loading.FMLPaths;
import org.jetbrains.annotations.NotNull;

public class ArsNouveauAPI {
   public static final ResourceLocation EMPTY_KEY = new ResourceLocation("ars_nouveau", "empty");
   public static boolean ENABLE_DEBUG_NUMBERS = false;
   private ConcurrentHashMap<ResourceLocation, AbstractSpellPart> spellpartMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, AbstractRitual> ritualMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, AbstractFamiliarHolder> familiarHolderMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, Supplier<Glyph>> glyphItemMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, FamiliarScript> familiarScriptMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, RitualTablet> ritualParchmentMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, IScryer> scryerMap = new ConcurrentHashMap<>();
   private Set<RecipeType<? extends IEnchantingRecipe>> enchantingRecipeTypes = ConcurrentHashMap.newKeySet();
   private ConcurrentHashMap<ResourceLocation, SpellSound> spellSoundsRegistry = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, IPerk> perkMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<ResourceLocation, PerkItem> perkItemMap = new ConcurrentHashMap<>();
   private ConcurrentHashMap<Item, IPerkProvider<ItemStack>> itemPerkProviderMap = new ConcurrentHashMap<>();
   private ISpellValidator craftingSpellValidator;
   private ISpellValidator castingSpellValidator;
   private List<IEnchantingRecipe> enchantingApparatusRecipes = new ArrayList<>();
   private List<BrewingRecipe> brewingRecipes = new ArrayList<>();
   private static final ArsNouveauAPI INSTANCE = new ArsNouveauAPI();

   public List<AbstractSpellPart> getDefaultStartingSpells() {
      return this.spellpartMap.values().stream().filter(Config::isStarterEnabled).collect(Collectors.toList());
   }

   public Item getGlyphItem(AbstractSpellPart spell) {
      return spell.glyphItem;
   }

   public Item getFamiliarItem(ResourceLocation id) {
      return this.familiarScriptMap.get(id);
   }

   public AbstractSpellPart registerSpell(AbstractSpellPart part) {
      this.glyphItemMap.put(part.getRegistryName(), part::getGlyph);
      Builder spellBuilder = new Builder();
      part.buildConfig(spellBuilder);
      ForgeConfigSpec spec = spellBuilder.build();
      part.CONFIG = spec;
      ANModConfig anModConfig = new ANModConfig(
         Type.SERVER, part.CONFIG, ModLoadingContext.get().getActiveContainer(), part.getRegistryName().m_135827_() + "/" + part.getRegistryName().m_135815_()
      );
      ModLoadingContext.get().getActiveContainer().addConfig(anModConfig);
      return this.spellpartMap.put(part.getRegistryName(), part);
   }

   public AbstractRitual registerRitual(AbstractRitual ritual) {
      return this.ritualMap.put(ritual.getRegistryName(), ritual);
   }

   public AbstractFamiliarHolder registerFamiliar(AbstractFamiliarHolder familiar) {
      return this.familiarHolderMap.put(familiar.getRegistryName(), familiar);
   }

   @Nullable
   public AbstractRitual getRitual(ResourceLocation id) {
      if (!this.ritualMap.containsKey(id)) {
         return null;
      } else {
         try {
            return (AbstractRitual)this.ritualMap.get(id).getClass().getDeclaredConstructor().newInstance();
         } catch (Exception var3) {
            var3.printStackTrace();
            return null;
         }
      }
   }

   @Nullable
   public AbstractSpellPart getSpellPart(ResourceLocation id) {
      return this.spellpartMap.get(id);
   }

   public Map<ResourceLocation, AbstractSpellPart> getSpellpartMap() {
      return this.spellpartMap;
   }

   public Map<ResourceLocation, Supplier<Glyph>> getGlyphItemMap() {
      return this.glyphItemMap;
   }

   public Map<ResourceLocation, AbstractRitual> getRitualMap() {
      return this.ritualMap;
   }

   public Map<ResourceLocation, RitualTablet> getRitualItemMap() {
      return this.ritualParchmentMap;
   }

   public Map<ResourceLocation, AbstractFamiliarHolder> getFamiliarHolderMap() {
      return this.familiarHolderMap;
   }

   public Map<ResourceLocation, FamiliarScript> getFamiliarScriptMap() {
      return this.familiarScriptMap;
   }

   public Set<RecipeType<? extends IEnchantingRecipe>> getEnchantingRecipeTypes() {
      return this.enchantingRecipeTypes;
   }

   public Map<ResourceLocation, IPerk> getPerkMap() {
      return this.perkMap;
   }

   public Map<ResourceLocation, PerkItem> getPerkItemMap() {
      return this.perkItemMap;
   }

   public Map<Item, IPerkProvider<ItemStack>> getItemPerkProviderMap() {
      return this.itemPerkProviderMap;
   }

   public List<IEnchantingRecipe> getEnchantingApparatusRecipes(Level world) {
      List<IEnchantingRecipe> recipes = new ArrayList<>(this.enchantingApparatusRecipes);
      RecipeManager manager = world.m_7465_();
      List<IEnchantingRecipe> recipesByType = new ArrayList<>();

      for (RecipeType<? extends IEnchantingRecipe> type : this.enchantingRecipeTypes) {
         recipesByType.addAll(manager.m_44013_(type));
      }

      recipes.addAll(recipesByType);
      return recipes;
   }

   public List<BrewingRecipe> getAllPotionRecipes() {
      if (this.brewingRecipes.isEmpty()) {
         BrewingRecipeRegistry.getRecipes().forEach(ib -> {
            if (ib instanceof BrewingRecipe brewingRecipe) {
               this.brewingRecipes.add(brewingRecipe);
            }
         });

         for (Mix<Potion> mix : PotionRecipeMixin.mixList()) {
            this.brewingRecipes
               .add(
                  new BrewingRecipe(
                     PotionIngredient.fromPotion((Potion)mix.f_43532_.get()), mix.f_43533_, PotionIngredient.fromPotion((Potion)mix.f_43534_.get()).getStack()
                  )
               );
         }
      }

      return this.brewingRecipes;
   }

   public ISpellValidator getSpellCraftingSpellValidator() {
      if (this.craftingSpellValidator == null) {
         this.craftingSpellValidator = new StandardSpellValidator(false);
      }

      return this.craftingSpellValidator;
   }

   public ISpellValidator getSpellCastingSpellValidator() {
      if (this.castingSpellValidator == null) {
         this.castingSpellValidator = new StandardSpellValidator(true);
      }

      return this.castingSpellValidator;
   }

   @Nullable
   public IScryer getScryer(ResourceLocation id) {
      return this.scryerMap.get(id);
   }

   public boolean registerScryer(IScryer scryer) {
      this.scryerMap.put(scryer.getRegistryName(), scryer);
      return true;
   }

   public boolean registerPerk(IPerk perk) {
      this.perkMap.put(perk.getRegistryName(), perk);
      return true;
   }

   public boolean registerPerkProvider(ItemLike item, IPerkProvider<ItemStack> provider) {
      this.itemPerkProviderMap.put(item.m_5456_(), provider);
      return true;
   }

   @Nullable
   public IPerkProvider<ItemStack> getPerkProvider(Item item) {
      return this.itemPerkProviderMap.get(item);
   }

   @NotNull
   public List<Item> getPerkProviderItems() {
      List<Item> list = new ArrayList<>();
      Iterator<Item> it = this.itemPerkProviderMap.keys().asIterator();

      while (it.hasNext()) {
         Item i = it.next();
         list.add(i);
      }

      return list;
   }

   public ConcurrentHashMap<ResourceLocation, SpellSound> getSpellSoundsRegistry() {
      return this.spellSoundsRegistry;
   }

   public SpellSound registerSpellSound(SpellSound sound) {
      return this.spellSoundsRegistry.put(sound.getId(), sound);
   }

   public void onResourceReload() {
      this.brewingRecipes = new ArrayList<>();
   }

   private ArsNouveauAPI() {
   }

   public static ArsNouveauAPI getInstance() {
      return INSTANCE;
   }

   static {
      FMLPaths.getOrCreateGameRelativePath(FMLPaths.CONFIGDIR.get().resolve("ars_nouveau"), "ars_nouveau");
   }
}
