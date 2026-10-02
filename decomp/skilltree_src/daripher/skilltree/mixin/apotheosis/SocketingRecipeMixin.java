package daripher.skilltree.mixin.apotheosis;

import daripher.skilltree.compat.apotheosis.ApotheosisCompatibility;
import daripher.skilltree.container.ContainerHelper;
import daripher.skilltree.entity.player.PlayerHelper;
import java.util.List;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.SocketingRecipe;

@Mixin(
   value = {SocketingRecipe.class},
   remap = false
)
public class SocketingRecipeMixin {
   @Redirect(
      method = {"matches", "m_5818_"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;hasEmptySockets(Lnet/minecraft/world/item/ItemStack;)Z"
      )
   )
   private boolean checkPlayerSockets(ItemStack stack, Container container, Level level) {
      Player player = ContainerHelper.getViewingPlayer(container);
      return player == null ? SocketHelper.hasEmptySockets(stack) : ApotheosisCompatibility.INSTANCE.hasEmptySockets(stack, player);
   }

   @Redirect(
      method = {"assemble", "m_5874_"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/List;set(ILjava/lang/Object;)Ljava/lang/Object;"
      )
   )
   private Object applyGemPower(List<Object> gems, int index, Object gem, Container container) {
      Player player = ContainerHelper.getViewingPlayer(container);
      if (player == null) {
         return gems.set(index, gem);
      } else {
         ItemStack result = container.m_8020_(0);
         float power = PlayerHelper.getGemPower(player, result);
         ((ItemStack)gem).m_41784_().m_128350_("gem_power", power);
         return gems.set(index, gem);
      }
   }

   @Redirect(
      method = {"assemble", "m_5874_"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;getFirstEmptySocket(Lnet/minecraft/world/item/ItemStack;)I"
      )
   )
   private int applyPlayerSockets(ItemStack stack, Container container) {
      Player player = ContainerHelper.getViewingPlayer(container);
      if (player == null) {
         return SocketHelper.getFirstEmptySocket(stack);
      } else {
         int sockets = ApotheosisCompatibility.INSTANCE.getSockets(stack, player);
         return ApotheosisCompatibility.INSTANCE.getFirstEmptySocket(stack, sockets);
      }
   }

   @Redirect(
      method = {"assemble", "m_5874_"},
      at = @At(
         value = "INVOKE",
         target = "Lshadows/apotheosis/adventure/affix/socket/SocketHelper;getGems(Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;"
      )
   )
   private List<ItemStack> applyPlayerSockets2(ItemStack stack, Container container) {
      Player player = ContainerHelper.getViewingPlayer(container);
      if (player == null) {
         return SocketHelper.getGems(stack);
      } else {
         int sockets = ApotheosisCompatibility.INSTANCE.getSockets(stack, player);
         return ApotheosisCompatibility.INSTANCE.getGems(stack, sockets);
      }
   }
}
