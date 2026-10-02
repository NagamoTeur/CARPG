package shadows.apotheosis.spawn.modifiers;

import com.google.common.collect.ImmutableList;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.spawn.spawner.ApothSpawnerTile;

public class SpawnerModifier implements Recipe<Container> {
   public static final SpawnerModifier.Serializer SERIALIZER = new SpawnerModifier.Serializer();
   protected final ResourceLocation id;
   protected final Ingredient mainHand;
   protected final Ingredient offHand;
   protected final boolean consumesOffhand;
   protected final List<StatModifier<?>> statChanges;

   public SpawnerModifier(ResourceLocation id, Ingredient mainHand, Ingredient offHand, boolean consumesOffhand, List<StatModifier<?>> stats) {
      this.id = id;
      this.mainHand = mainHand;
      this.offHand = offHand;
      this.consumesOffhand = consumesOffhand;
      this.statChanges = ImmutableList.copyOf(stats);
   }

   public boolean matches(ApothSpawnerTile tile, ItemStack mainhand, ItemStack offhand) {
      if (this.mainHand.test(mainhand)) {
         return this.offHand == Ingredient.f_43901_ ? true : this.offHand.test(offhand);
      } else {
         return false;
      }
   }

   public boolean apply(ApothSpawnerTile tile) {
      boolean success = false;

      for (StatModifier<?> m : this.statChanges) {
         if (m.apply(tile)) {
            success = true;
            tile.m_6596_();
         }
      }

      return success;
   }

   public boolean consumesOffhand() {
      return this.consumesOffhand;
   }

   public Ingredient getMainhandInput() {
      return this.mainHand;
   }

   public Ingredient getOffhandInput() {
      return this.offHand;
   }

   public List<StatModifier<?>> getStatModifiers() {
      return this.statChanges;
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

   @Deprecated
   public ItemStack m_8043_() {
      return ItemStack.f_41583_;
   }

   public ResourceLocation m_6423_() {
      return this.id;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public RecipeType<?> m_6671_() {
      return Apoth.RecipeTypes.MODIFIER;
   }

   @Nullable
   public static SpawnerModifier findMatch(ApothSpawnerTile tile, ItemStack mainhand, ItemStack offhand) {
      List<SpawnerModifier> recipes = new ArrayList<>(tile.m_58904_().m_7465_().m_44013_(Apoth.RecipeTypes.MODIFIER));
      recipes.sort((r1, r2) -> r1.offHand == Ingredient.f_43901_ ? (r2.offHand == Ingredient.f_43901_ ? 0 : 1) : -1);

      for (SpawnerModifier r : recipes) {
         if (r.matches(tile, mainhand, offhand)) {
            return r;
         }
      }

      return null;
   }

   public static class Serializer implements RecipeSerializer<SpawnerModifier> {
      public SpawnerModifier fromJson(ResourceLocation id, JsonObject obj) {
         Ingredient mainhand = Ingredient.m_43917_(obj.get("mainhand"));
         Ingredient offhand = obj.has("offhand") ? Ingredient.m_43917_(obj.get("offhand")) : Ingredient.f_43901_;
         JsonArray stats = obj.get("stat_changes").getAsJsonArray();
         List<StatModifier<?>> statChanges = new ArrayList<>();

         for (JsonElement e : stats) {
            statChanges.add(StatModifier.parse(e.getAsJsonObject()));
         }

         return new SpawnerModifier(id, mainhand, offhand, offhand == Ingredient.f_43901_ ? false : obj.get("consumes_offhand").getAsBoolean(), statChanges);
      }

      public SpawnerModifier fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
         Ingredient mainhand = Ingredient.m_43940_(buf);
         Ingredient offhand = buf.readBoolean() ? Ingredient.m_43940_(buf) : Ingredient.f_43901_;
         boolean consumesOffhand = buf.readBoolean();
         List<StatModifier<?>> statChanges = new ArrayList<>();
         int size = buf.readByte();

         for (int i = 0; i < size; i++) {
            SpawnerStat<?> stat = SpawnerStats.REGISTRY.get(buf.m_130136_(32));
            boolean isBool = buf.readBoolean();
            if (isBool) {
               statChanges.add(new StatModifier<>(stat, buf.readBoolean(), false, false));
            } else {
               statChanges.add(new StatModifier<>(stat, buf.readInt(), buf.readInt(), buf.readInt()));
            }
         }

         return new SpawnerModifier(id, mainhand, offhand, consumesOffhand, statChanges);
      }

      public void toNetwork(FriendlyByteBuf buf, SpawnerModifier recipe) {
         recipe.mainHand.m_43923_(buf);
         buf.writeBoolean(recipe.offHand != Ingredient.f_43901_);
         if (recipe.offHand != Ingredient.f_43901_) {
            recipe.offHand.m_43923_(buf);
         }

         buf.writeBoolean(recipe.consumesOffhand);
         buf.writeByte(recipe.statChanges.size());

         for (StatModifier<?> s : recipe.statChanges) {
            SpawnerStat<?> stat = s.stat;
            buf.m_130072_(stat.getId(), 32);
            boolean isBool = stat.getTypeClass() == Boolean.class;
            buf.writeBoolean(isBool);
            if (isBool) {
               buf.writeBoolean((Boolean)s.value);
            } else {
               buf.writeInt((Integer)s.value);
               buf.writeInt((Integer)s.min);
               buf.writeInt((Integer)s.max);
            }
         }
      }
   }
}
