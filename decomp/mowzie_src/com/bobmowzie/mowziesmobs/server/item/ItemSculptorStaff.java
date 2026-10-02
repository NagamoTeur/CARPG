package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.client.render.item.RenderSculptorStaff;
import com.bobmowzie.mowziesmobs.server.ability.AbilityHandler;
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
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class ItemSculptorStaff extends MowzieToolItem implements IAnimatable, ISyncable {
   public String controllerName = "controller";
   public AnimationFactory factory = new AnimationFactory(this);

   public ItemSculptorStaff(Properties properties) {
      super(1.0F, 2.0F, Tiers.STONE, BlockTags.f_144281_, properties);
      GeckoLibNetwork.registerSyncable(this);
   }

   public void initializeClient(Consumer<IClientItemExtensions> consumer) {
      super.initializeClient(consumer);
      consumer.accept(new IClientItemExtensions() {
         private final BlockEntityWithoutLevelRenderer renderer = new RenderSculptorStaff();

         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return this.renderer;
         }
      });
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      AbilityHandler.INSTANCE.sendAbilityMessage(player, AbilityHandler.ROCK_SLING);
      player.m_6672_(hand);
      return new InteractionResultHolder(InteractionResult.SUCCESS, player.m_21120_(hand));
   }

   public boolean m_41465_() {
      return true;
   }

   public int m_8105_(ItemStack stack) {
      return 72000;
   }

   public boolean m_8120_(ItemStack stack) {
      return false;
   }

   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
      super.m_7373_(stack, worldIn, tooltip, flagIn);
   }

   public void registerControllers(AnimationData animationData) {
   }

   public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
      return PlayState.CONTINUE;
   }

   public AnimationFactory getFactory() {
      return this.factory;
   }

   public void onAnimationSync(int id, int state) {
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
