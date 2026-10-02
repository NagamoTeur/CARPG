package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.config.OmniconfigHandler;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.CosmicHeart;
import com.aizistral.enigmaticlegacy.registries.EnigmaticItems;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class HiddenRecipe extends CustomRecipe {
   public static final SimpleRecipeSerializer<HiddenRecipe> SERIALIZER = new SimpleRecipeSerializer(HiddenRecipe::new);
   static final Map<ItemStack[][], ItemStack> RECIPES = new HashMap<>();
   static final Entry<ItemStack[][], ItemStack> EMPTY = new Entry<ItemStack[][], ItemStack>() {
      private final ItemStack[][] recipe = new ItemStack[][]{
         {ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_},
         {ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_},
         {ItemStack.f_41583_, ItemStack.f_41583_, ItemStack.f_41583_}
      };

      public ItemStack[][] getKey() {
         return this.recipe;
      }

      public ItemStack getValue() {
         return ItemStack.f_41583_;
      }

      public ItemStack setValue(ItemStack value) {
         throw new UnsupportedOperationException();
      }
   };

   public HiddenRecipe(ResourceLocation id) {
      super(id);
   }

   public ItemStack assemble(CraftingContainer inv) {
      ItemStack output = ItemStack.f_41583_;
      Optional<CompoundTag> amuletNBT = Optional.empty();

      label44:
      for (Entry<ItemStack[][], ItemStack> entry : RECIPES.entrySet()) {
         for (int r = 0; r < 3; r++) {
            for (int i = 0; i < 3; i++) {
               ItemStack slotStack = inv.m_8020_(3 * r + i);
               if (slotStack.m_41720_() != entry.getKey()[r][i].m_41720_()) {
                  continue label44;
               }

               if (slotStack.m_150930_(EnigmaticItems.ENIGMATIC_AMULET) || slotStack.m_150930_(EnigmaticItems.ASCENSION_AMULET)) {
                  amuletNBT = Optional.of(slotStack.m_41783_().m_6426_());
               }
            }
         }

         output = entry.getValue().m_41777_();
         amuletNBT.ifPresent(output::m_41751_);
         break;
      }

      if (!OmniconfigHandler.isItemEnabled(output.m_41720_())) {
         output = ItemStack.f_41583_;
      }

      return output;
   }

   public boolean matches(CraftingContainer inv, Level world) {
      label39:
      for (ItemStack[][] array : RECIPES.keySet()) {
         for (int r = 0; r < 3; r++) {
            for (int i = 0; i < 3; i++) {
               ItemStack slotStack = inv.m_8020_(3 * r + i);
               if (slotStack.m_41720_() != array[r][i].m_41720_()
                  || RECIPES.get(array).m_150930_(EnigmaticItems.COSMIC_SCROLL)
                     && slotStack.m_41720_() instanceof CosmicHeart
                     && !ItemNBTHelper.getBoolean(slotStack, "isBelieverBlessed", false)) {
                  continue label39;
               }
            }
         }

         return true;
      }

      return false;
   }

   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 9;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public static void addRecipe(ItemStack output, ItemStack... inputs) {
      ItemStack[][] array = new ItemStack[][]{{inputs[0], inputs[1], inputs[2]}, {inputs[3], inputs[4], inputs[5]}, {inputs[6], inputs[7], inputs[8]}};
      RECIPES.put(array, output);
   }

   public static Entry<ItemStack[][], ItemStack> getRecipe(ResourceLocation output) {
      return RECIPES.entrySet().stream().filter(entry -> ForgeRegistries.ITEMS.getKey(entry.getValue().m_41720_()).equals(output)).findFirst().orElse(EMPTY);
   }
}
