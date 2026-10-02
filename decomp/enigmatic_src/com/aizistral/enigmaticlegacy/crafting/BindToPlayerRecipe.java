package com.aizistral.enigmaticlegacy.crafting;

import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.Level;

public class BindToPlayerRecipe extends CustomRecipe {
   public static final SimpleRecipeSerializer<BindToPlayerRecipe> SERIALIZER = new SimpleRecipeSerializer(BindToPlayerRecipe::new);

   public BindToPlayerRecipe(ResourceLocation id) {
      super(id);
   }

   public ItemStack assemble(CraftingContainer inv) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack gem = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack slotStack = inv.m_8020_(i);
         if (!slotStack.m_41619_()) {
            stackList.add(slotStack);
         }
      }

      if (stackList.size() == 1
         && stackList.get(0).m_41720_() instanceof BindToPlayerRecipe.IBound
         && gem != null
         && ItemNBTHelper.verifyExistance(gem, "BoundPlayer")
         && ItemNBTHelper.containsUUID(gem, "BoundUUID")) {
         ItemStack returned = stackList.get(0).m_41777_();
         ItemNBTHelper.setString(returned, "BoundPlayer", ItemNBTHelper.getString(gem, "BoundPlayer", "Herobrine"));
         ItemNBTHelper.setUUID(returned, "BoundUUID", ItemNBTHelper.getUUID(gem, "BoundUUID", null));
         return returned;
      } else {
         return ItemStack.f_41583_;
      }
   }

   public boolean matches(CraftingContainer inv, Level world) {
      List<ItemStack> stackList = new ArrayList<>();
      ItemStack gem = null;

      for (int i = 0; i < inv.m_6643_(); i++) {
         ItemStack slotStack = inv.m_8020_(i);
         if (!slotStack.m_41619_()) {
            stackList.add(slotStack);
         }
      }

      return stackList.size() == 1
         && stackList.get(0).m_41720_() instanceof BindToPlayerRecipe.IBound
         && gem != null
         && ItemNBTHelper.verifyExistance(gem, "BoundPlayer")
         && ItemNBTHelper.containsUUID(gem, "BoundUUID");
   }

   public boolean m_8004_(int width, int height) {
      return width * height >= 2;
   }

   public RecipeSerializer<?> m_7707_() {
      return SERIALIZER;
   }

   public interface IBound {
      @Nullable
      default Player getBoundPlayer(Level world, ItemStack stack) {
         if (ItemNBTHelper.verifyExistance(stack, "BoundPlayer") && ItemNBTHelper.containsUUID(stack, "BoundUUID")) {
            UUID id = ItemNBTHelper.getUUID(stack, "BoundUUID", null);
            return world.m_46003_(id);
         } else {
            return null;
         }
      }
   }
}
