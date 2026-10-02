package shadows.apotheosis.adventure.affix.salvaging;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.placebo.json.ItemAdapter;

public class SalvagingRecipe implements Recipe<Container> {
   protected final ResourceLocation id;
   protected final Ingredient input;
   protected final List<SalvagingRecipe.OutputData> outputs;

   public SalvagingRecipe(ResourceLocation id, List<SalvagingRecipe.OutputData> outputs, Ingredient input) {
      this.id = id;
      this.outputs = outputs;
      this.input = input;
   }

   public boolean matches(ItemStack stack) {
      return this.input.test(stack);
   }

   public Ingredient getInput() {
      return this.input;
   }

   public List<SalvagingRecipe.OutputData> getOutputs() {
      return this.outputs;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return SalvagingRecipe.Serializer.INSTANCE;
   }

   public RecipeType<?> m_6671_() {
      return Apoth.RecipeTypes.SALVAGING;
   }

   @Deprecated
   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   @Deprecated
   public boolean m_5818_(Container pContainer, Level pLevel) {
      return false;
   }

   @Deprecated
   public ItemStack m_5874_(Container pContainer) {
      return ItemStack.f_41583_;
   }

   @Deprecated
   public boolean m_8004_(int pWidth, int pHeight) {
      return false;
   }

   public static class OutputData {
      public static Codec<SalvagingRecipe.OutputData> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  ItemAdapter.CODEC.fieldOf("stack").forGetter(d -> d.stack),
                  Codec.intRange(0, 64).fieldOf("min_count").forGetter(d -> d.min),
                  Codec.intRange(0, 64).fieldOf("max_count").forGetter(d -> d.max)
               )
               .apply(inst, SalvagingRecipe.OutputData::new)
      );
      public static final Codec<List<SalvagingRecipe.OutputData>> LIST_CODEC = Codec.list(CODEC);
      ItemStack stack;
      int min;
      int max;

      OutputData(ItemStack stack, int min, int max) {
         this.stack = stack;
         this.min = min;
         this.max = max;
         Preconditions.checkArgument(max >= min);
         this.stack.m_41764_(1);
      }

      public ItemStack getStack() {
         return this.stack;
      }

      public int getMin() {
         return this.min;
      }

      public int getMax() {
         return this.max;
      }
   }

   public static class Serializer implements RecipeSerializer<SalvagingRecipe> {
      public static final SalvagingRecipe.Serializer INSTANCE = new SalvagingRecipe.Serializer();

      public SalvagingRecipe fromJson(ResourceLocation id, JsonObject obj) {
         List<SalvagingRecipe.OutputData> outputs = (List<SalvagingRecipe.OutputData>)((Pair)SalvagingRecipe.OutputData.LIST_CODEC
               .decode(JsonOps.INSTANCE, GsonHelper.m_13933_(obj, "outputs"))
               .result()
               .get())
            .getFirst();
         Ingredient input = Ingredient.m_43917_(obj.get("input"));
         return new SalvagingRecipe(id, outputs, input);
      }

      public SalvagingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         List<SalvagingRecipe.OutputData> outputs = (List<SalvagingRecipe.OutputData>)((Pair)SalvagingRecipe.OutputData.LIST_CODEC
               .decode(NbtOps.f_128958_, buf.m_130260_().m_128423_("outputs"))
               .result()
               .get())
            .getFirst();
         Ingredient input = Ingredient.m_43940_(buf);
         return new SalvagingRecipe(id, outputs, input);
      }

      public void toNetwork(FriendlyByteBuf buf, SalvagingRecipe recipe) {
         Tag outputs = (Tag)SalvagingRecipe.OutputData.LIST_CODEC.encodeStart(NbtOps.f_128958_, recipe.outputs).get().left().get();
         CompoundTag netWrapper = new CompoundTag();
         netWrapper.m_128365_("outputs", outputs);
         buf.m_130079_(netWrapper);
         recipe.input.m_43923_(buf);
      }
   }
}
