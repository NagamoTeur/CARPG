package com.hollingsworth.arsnouveau.common.network;

import com.hollingsworth.arsnouveau.common.block.tile.ScribesTile;
import com.hollingsworth.arsnouveau.common.crafting.recipes.GlyphRecipe;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.network.NetworkEvent.Context;

public class PacketSetScribeRecipe {
   BlockPos scribePos;
   ResourceLocation recipeID;

   public PacketSetScribeRecipe(FriendlyByteBuf buf) {
      this.scribePos = buf.m_130135_();
      this.recipeID = buf.m_130281_();
   }

   public void toBytes(FriendlyByteBuf buf) {
      buf.m_130064_(this.scribePos);
      buf.m_130085_(this.recipeID);
   }

   public PacketSetScribeRecipe(BlockPos scribesPos, ResourceLocation resourceLocation) {
      this.scribePos = scribesPos;
      this.recipeID = resourceLocation;
   }

   public void handle(Supplier<Context> ctx) {
      ctx.get().enqueueWork(() -> {
         ServerPlayer player = ctx.get().getSender();
         if (player != null) {
            if (player.f_19853_.m_7702_(this.scribePos) instanceof ScribesTile scribesTile) {
               Recipe recipe = (Recipe)player.f_19853_.m_7465_().m_44043_(this.recipeID).orElse(null);
               if (recipe instanceof GlyphRecipe glyphRecipe) {
                  scribesTile.setRecipe(glyphRecipe, player);
               }
            }
         }
      });
      ctx.get().setPacketHandled(true);
   }
}
