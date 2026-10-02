package com.hollingsworth.arsnouveau.common.items;

import com.hollingsworth.arsnouveau.api.item.IRadialProvider;
import com.hollingsworth.arsnouveau.api.nbt.ItemstackData;
import com.hollingsworth.arsnouveau.api.potion.PotionData;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenuSlot;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.client.keybindings.ModKeyBindings;
import com.hollingsworth.arsnouveau.client.renderer.item.FlaskCannonRenderer;
import com.hollingsworth.arsnouveau.client.renderer.tile.GenericModel;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketSetLauncher;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.ars_nouveau.geckolib3.core.IAnimatable;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationData;
import software.bernie.ars_nouveau.geckolib3.core.manager.AnimationFactory;
import software.bernie.ars_nouveau.geckolib3.util.GeckoLibUtil;

public abstract class FlaskCannon extends ModItem implements IRadialProvider, IAnimatable {
   public AnimationFactory factory = GeckoLibUtil.createFactory(this);

   public FlaskCannon(Properties properties) {
      super(properties);
   }

   public InteractionResult m_6880_(ItemStack pStack, Player pPlayer, LivingEntity pInteractionTarget, InteractionHand pUsedHand) {
      return InteractionResult.FAIL;
   }

   public void m_6883_(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
      super.m_6883_(pStack, pLevel, pEntity, pSlotId, pIsSelected);
      if (!pLevel.f_46443_) {
         if (pEntity instanceof ServerPlayer player) {
            FlaskCannon.PotionLauncherData potionLauncherData = new FlaskCannon.PotionLauncherData(pStack);
            int lastSlot = potionLauncherData.lastSlot;
            if (lastSlot >= 0 && lastSlot < player.f_36093_.m_6643_()) {
               ItemStack item = player.f_36093_.m_8020_(lastSlot);
               if (!(item.m_41720_() instanceof PotionFlask) && !(item.m_41720_() instanceof PotionItem)) {
                  potionLauncherData.setAmountLeft(0);
               }
            }
         }
      }
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level pLevel, Player pPlayer, InteractionHand pHand) {
      ItemStack itemstack = pPlayer.m_21120_(pHand);
      FlaskCannon.PotionLauncherData potionLauncherData = new FlaskCannon.PotionLauncherData(itemstack);
      if (pLevel.f_46443_) {
         return InteractionResultHolder.m_19096_(itemstack);
      } else {
         PotionData potionData = potionLauncherData.getPotionDataFromSlot(pPlayer);
         if (potionData.isEmpty()) {
            PortUtil.sendMessage(pPlayer, Component.m_237115_("ars_nouveau.flask_cannon.no_potion"));
            return InteractionResultHolder.m_19092_(itemstack, pLevel.m_5776_());
         } else {
            ThrownPotion thrownpotion = new ThrownPotion(pLevel, pPlayer);
            ItemStack stckToThrow = this.getThrownStack(pLevel, pPlayer, pHand, itemstack);
            if (new PotionData(stckToThrow).isEmpty()) {
               return InteractionResultHolder.m_19090_(itemstack);
            } else {
               thrownpotion.m_37446_(stckToThrow);
               thrownpotion.m_37251_(pPlayer, pPlayer.m_146909_(), pPlayer.m_146908_(), -20.0F, 0.5F, 1.0F);
               pLevel.m_7967_(thrownpotion);
               pPlayer.m_36335_().m_41524_(this, 10);
               potionLauncherData.setLastDataForRender(new PotionData(stckToThrow));
               return new InteractionResultHolder(InteractionResult.CONSUME, itemstack);
            }
         }
      }
   }

   public abstract ItemStack getThrownStack(Level var1, Player var2, InteractionHand var3, ItemStack var4);

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.BOW;
   }

   public boolean doesSneakBypassUse(ItemStack stack, LevelReader world, BlockPos pos, Player player) {
      return true;
   }

   public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
      return false;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public int forKey() {
      return ModKeyBindings.OPEN_RADIAL_HUD.getKey().m_84873_();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onRadialKeyPressed(ItemStack stack, Player player) {
      List<RadialMenuSlot<AlchemistsCrown.SlotData>> slots = new ArrayList<>();

      for (int i = 0; i < player.f_36093_.m_6643_() && slots.size() < 9; i++) {
         ItemStack item = player.f_36093_.m_8020_(i);
         PotionData potionData = new PotionData(item);
         if (!potionData.isEmpty() && !(item.m_41720_() instanceof ArrowItem)) {
            slots.add(new RadialMenuSlot<>(item.m_41786_().getString(), new AlchemistsCrown.SlotData(i, item)));
         }
      }

      if (slots.isEmpty()) {
         PortUtil.sendMessage(Minecraft.m_91087_().f_91074_, Component.m_237115_("ars_nouveau.alchemists_crown.no_flasks"));
      } else {
         Minecraft.m_91087_()
            .m_91152_(
               new GuiRadialMenu<>(
                  new RadialMenu<>(
                     index -> Networking.INSTANCE.sendToServer(new PacketSetLauncher(slots.get(index).primarySlotIcon().getSlot())),
                     slots,
                     (slotData, posestack, positionx, posy, size, transparent) -> RenderUtils.drawItemAsIcon(
                           slotData.getStack(), posestack, positionx, posy, size, transparent
                        ),
                     3
                  )
               )
            );
      }
   }

   @Override
   public AnimationFactory getFactory() {
      return this.factory;
   }

   @Override
   public void registerControllers(AnimationData data) {
   }

   public static class LingeringLauncher extends FlaskCannon {
      public LingeringLauncher(Properties properties) {
         super(properties);
      }

      @Override
      public ItemStack getThrownStack(Level pLevel, Player pPlayer, InteractionHand pHand, ItemStack launcherStack) {
         FlaskCannon.PotionLauncherData data = new FlaskCannon.PotionLauncherData(launcherStack);
         ItemStack splashStack = new ItemStack(Items.f_42739_);
         PotionData potionData = data.expendPotion(pPlayer);
         PotionUtils.m_43549_(splashStack, potionData.getPotion());
         PotionUtils.m_43552_(splashStack, potionData.getCustomEffects());
         return splashStack;
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         super.initializeClient(consumer);
         consumer.accept(
            new IClientItemExtensions() {
               private final BlockEntityWithoutLevelRenderer renderer = new FlaskCannonRenderer(
                  new GenericModel("lingering_flask_cannon", "items").withEmptyAnim()
               );

               public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                  return this.renderer;
               }
            }
         );
      }
   }

   public static class PotionLauncherData extends ItemstackData {
      private PotionData lastDataForRender;
      private int lastSlot;
      public int amountLeft;

      public PotionLauncherData(ItemStack stack) {
         super(stack);
         CompoundTag tag = this.getItemTag(stack);
         if (tag != null) {
            this.lastDataForRender = PotionData.fromTag(tag.m_128469_("lastDataForRender"));
            this.lastSlot = tag.m_128451_("lastSlot");
            this.amountLeft = tag.m_128451_("amountLeft");
         }
      }

      public PotionData getPotionDataFromSlot(Player player) {
         if (this.lastSlot >= 0 && this.lastSlot < player.f_36093_.m_6643_()) {
            ItemStack stack = player.f_36093_.m_8020_(this.lastSlot);
            return new PotionData(stack);
         } else {
            return new PotionData();
         }
      }

      public PotionData expendPotion(Player player) {
         if (this.lastSlot >= player.f_36093_.m_6643_()) {
            return new PotionData();
         } else {
            ItemStack item = player.f_36093_.m_8020_(this.lastSlot);
            if (item.m_41720_() instanceof PotionFlask) {
               PotionFlask.FlaskData flaskData = new PotionFlask.FlaskData(item);
               if (flaskData.getCount() > 0 && !flaskData.getPotion().isEmpty()) {
                  PotionData data = flaskData.getPotion().clone();
                  flaskData.setCount(flaskData.getCount() - 1);
                  this.setAmountLeft(flaskData.getCount());
                  return data;
               } else {
                  return new PotionData();
               }
            } else if (item.m_41720_() instanceof PotionItem) {
               PotionData data = new PotionData(item).clone();
               if (data.isEmpty()) {
                  return new PotionData();
               } else {
                  item.m_41774_(1);
                  player.f_36093_.m_36054_(new ItemStack(Items.f_42590_));
                  this.setAmountLeft(0);
                  return data;
               }
            } else {
               return new PotionData();
            }
         }
      }

      public void setAmountLeft(int amount) {
         this.amountLeft = amount;
         this.writeItem();
      }

      public void setLastSlot(int lastSlot) {
         this.lastSlot = lastSlot;
         this.writeItem();
      }

      public void setLastDataForRender(PotionData lastDataForRender) {
         this.lastDataForRender = lastDataForRender;
         this.writeItem();
      }

      @Override
      public void writeToNBT(CompoundTag tag) {
         tag.m_128405_("lastSlot", this.lastSlot);
         tag.m_128365_("lastDataForRender", this.lastDataForRender.toTag());
         tag.m_128405_("amountLeft", this.amountLeft);
      }

      public PotionData getLastDataForRender() {
         return this.lastDataForRender;
      }

      @Override
      public String getTagString() {
         return "potion_launcher";
      }
   }

   public static class SplashLauncher extends FlaskCannon {
      public SplashLauncher(Properties properties) {
         super(properties);
      }

      @Override
      public ItemStack getThrownStack(Level pLevel, Player pPlayer, InteractionHand pHand, ItemStack launcherStack) {
         FlaskCannon.PotionLauncherData data = new FlaskCannon.PotionLauncherData(launcherStack);
         ItemStack splashStack = new ItemStack(Items.f_42736_);
         PotionData potionData = data.expendPotion(pPlayer);
         PotionUtils.m_43549_(splashStack, potionData.getPotion());
         PotionUtils.m_43552_(splashStack, potionData.getCustomEffects());
         return splashStack;
      }

      public void initializeClient(Consumer<IClientItemExtensions> consumer) {
         super.initializeClient(consumer);
         consumer.accept(new IClientItemExtensions() {
            private final BlockEntityWithoutLevelRenderer renderer = new FlaskCannonRenderer(new GenericModel("splash_flask_cannon", "items").withEmptyAnim());

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return this.renderer;
            }
         });
      }
   }
}
