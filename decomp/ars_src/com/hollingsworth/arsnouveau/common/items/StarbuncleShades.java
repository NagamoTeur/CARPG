package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.entity.IDecoratable;
import com.hollingsworth.arsnouveau.api.item.ICosmeticItem;
import com.hollingsworth.arsnouveau.client.renderer.item.GenericItemRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.entity.familiar.FamiliarStarbuncle;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class StarbuncleShades extends AnimModItem implements ICosmeticItem {
   public InteractionResult m_6880_(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
      if (pInteractionTarget instanceof IDecoratable starbuncle && this.canWear(pInteractionTarget)) {
         starbuncle.setCosmeticItem(pStack.m_41620_(1));
         return InteractionResult.SUCCESS;
      }

      return super.m_6880_(pStack, pPlayer, pInteractionTarget, pUsedHand);
   }

   @Override
   public boolean canWear(LivingEntity entity) {
      return entity instanceof Starbuncle || entity instanceof FamiliarStarbuncle;
   }

   @Override
   public Vec3 getTranslations() {
      return new Vec3(0.0, -0.259, 0.165);
   }

   @Override
   public Vec3 getScaling() {
      return new Vec3(1.0, 1.0, 1.0);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new GenericItemRenderer(new GenericModel<>("starbuncle_shades", "items")).withTranslucency();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }
}
