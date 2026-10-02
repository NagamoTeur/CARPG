package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.client.renderer.item.FixedGeoItemRenderer;
import com.hollingsworth.arsnouveau.client.renderer.item.ShieldModel;
import com.hollingsworth.arsnouveau.common.perk.RepairingPerk;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public class EnchantersShield extends ShieldItem implements IAnimatable {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public EnchantersShield() {
      super(ItemsRegistry.defaultItemProperties().m_41503_(500));
   }

   public EnchantersShield(Properties p_i48470_1_) {
      super(p_i48470_1_);
   }

   public void m_6883_(ItemStack stack, Level world, Entity entity, int p_77663_4_, boolean p_77663_5_) {
      super.m_6883_(stack, world, entity, p_77663_4_, p_77663_5_);
      if (entity instanceof Player player) {
         RepairingPerk.attemptRepair(stack, player);
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      return super.m_7203_(p_77659_1_, p_77659_2_, p_77659_3_);
   }

   @Override
   public void registerControllers(AnimationData animationData) {
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new FixedGeoItemRenderer(new ShieldModel());

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }
}
