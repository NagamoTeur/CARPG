package daripher.skilltree.mixin.apotheosis;

import daripher.skilltree.compat.apotheosis.ApotheosisCompatibility;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.adventure.client.AdventureModuleClient;

@Mixin(
   value = {AdventureModuleClient.class},
   remap = false
)
public class AdventureModuleClientMixin {
   @Redirect(
      method = {"comps"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;getGems(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;"
      )
   )
   private static List<ItemStack> showPlayerSockets(ItemStack stack) {
      Minecraft minecraft = Minecraft.m_91087_();
      int sockets = ApotheosisCompatibility.INSTANCE.getSockets(stack, minecraft.f_91074_);
      return ApotheosisCompatibility.INSTANCE.getGems(stack, sockets);
   }

   @Redirect(
      method = {"comps"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;getSockets(Lnet/minecraft/world/item/ItemStack;)I"
      )
   )
   private static int addPlayerSockets(ItemStack stack) {
      return ApotheosisCompatibility.INSTANCE.getSockets(stack, Minecraft.m_91087_().f_91074_);
   }

   @Redirect(
      method = {"tooltips"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;getSockets(Lnet/minecraft/world/item/ItemStack;)I"
      )
   )
   private static int addPlayerSockets2(ItemStack stack) {
      return ApotheosisCompatibility.INSTANCE.getSockets(stack, Minecraft.m_91087_().f_91074_);
   }
}
