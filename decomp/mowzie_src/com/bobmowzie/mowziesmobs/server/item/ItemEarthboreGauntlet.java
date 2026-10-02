package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderEarthboreGauntlet;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
import com.bobmowzie.mowziesmobs.server.capability.AbilityCapability;
import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ItemEarthboreGauntlet extends MowzieToolItem implements IAnimatable, ISyncable {
   public static final int ANIM_REST = 0;
   public static final int ANIM_OPEN = 1;
   public static final int ANIM_FIST = 2;
   public String controllerName = "controller";
   public String controllerIdleName = "controller_idle";
   public AnimationFactory factory = new AnimationFactory(this);

   public ItemEarthboreGauntlet(Properties properties) {
      super(
         -2.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.toolConfig.attackDamageValue,
         -4.0F + ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.toolConfig.attackSpeedValue,
         Tiers.STONE,
         BlockTags.f_144282_,
         properties
      );
      GeckoLibNetwork.registerSyncable(this);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new RenderEarthboreGauntlet();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   public boolean m_41465_() {
      return true;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.m_21120_(handIn);
      AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(playerIn);
      if (abilityCapability != null) {
         playerIn.m_6672_(handIn);
         if (stack.m_41773_() + 5 < stack.m_41776_() || (Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
            if (!worldIn.m_5776_()) {
               AbilityHandler.INSTANCE.sendAbilityMessage(playerIn, AbilityHandler.TUNNELING_ABILITY);
            }

            playerIn.m_6672_(handIn);
            return new InteractionResultHolder(InteractionResult.SUCCESS, playerIn.m_21120_(handIn));
         }

         abilityCapability.getAbilityMap().get(AbilityHandler.TUNNELING_ABILITY).end();
      }

      return super.m_7203_(worldIn, playerIn, handIn);
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public int getMaxDamage(ItemStack stack) {
      return (Integer)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.durability.get();
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.0").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.1").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.2").m_6270_(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.m_237115_(this.m_5524_() + ".text.3").m_6270_(ItemHandler.TOOLTIP_STYLE));
      if (!(Boolean)ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.breakable.get()) {
         tooltip.add(Component.m_237115_(this.m_5524_() + ".text.4").m_6270_(ItemHandler.TOOLTIP_STYLE));
      }
   }

   public void registerControllers(AnimationData animationData) {
      animationData.addAnimationController(new AnimationController(this, this.controllerIdleName, 3.0F, this::predicateIdle));
      animationData.addAnimationController(new AnimationController(this, this.controllerName, 3.0F, this::predicate));
   }

   public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      return PlayState.CONTINUE;
   }

   public <P extends Item & IAnimatable> PlayState predicateIdle(AnimationEvent<P> event) {
      event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
      return PlayState.CONTINUE;
   }

   public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
      AbilityCapability.IAbilityCapability abilityCapability = AbilityHandler.INSTANCE.getAbilityCapability(entity);
      if (abilityCapability != null && abilityCapability.getActiveAbility() == null && entity.m_21211_() != stack) {
         this.playAnimation(entity, stack, 2);
      }

      return super.onEntitySwing(stack, entity);
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void onAnimationSync(int id, int state) {
      AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id, this.controllerName);
      controller.markNeedsReload();
      if (state == 0) {
         controller.clearAnimationCache();
         controller.setAnimation(new AnimationBuilder().addAnimation("idle", true));
      } else if (state == 1) {
         controller.clearAnimationCache();
         controller.setAnimation(new AnimationBuilder().addAnimation("open", true));
      } else if (state == 2) {
         controller.clearAnimationCache();
         controller.setAnimation(new AnimationBuilder().addAnimation("attack", false));
      }
   }

   public void playAnimation(LivingEntity entity, InteractionHand hand, int state) {
      ItemStack stack = entity.m_21120_(hand);
      this.playAnimation(entity, stack, state);
   }

   public void playAnimation(LivingEntity entity, ItemStack stack, int state) {
      if (!entity.f_19853_.f_46443_) {
         int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerLevel)entity.f_19853_);
         PacketTarget target = PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity);
         GeckoLibNetwork.syncAnimation(target, this, id, state);
      }
   }

   @Override
   public ConfigHandler.ToolConfig getConfig() {
      return ConfigHandler.COMMON.TOOLS_AND_ABILITIES.EARTHBORE_GAUNTLET.toolConfig;
   }
}
