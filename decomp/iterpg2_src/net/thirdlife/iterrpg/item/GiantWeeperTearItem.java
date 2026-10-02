package net.thirdlife.iterrpg.item;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.thirdlife.iterrpg.entity.GiantWeeperTearEntity;

public class GiantWeeperTearItem extends Item {
   public GiantWeeperTearItem() {
      super(new Properties().m_41491_(null).m_41503_(6));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player entity, InteractionHand hand) {
      entity.m_6672_(hand);
      return new InteractionResultHolder(InteractionResult.SUCCESS, entity.m_21120_(hand));
   }

   public UseAnim m_6164_(ItemStack itemstack) {
      return UseAnim.NONE;
   }

   public int m_8105_(ItemStack itemstack) {
      return 72000;
   }

   public void onUsingTick(ItemStack itemstack, LivingEntity entityLiving, int count) {
      Level world = entityLiving.f_19853_;
      if (!world.m_5776_() && entityLiving instanceof ServerPlayer entity) {
         double x = entity.m_20185_();
         double y = entity.m_20186_();
         double z = entity.m_20189_();
         GiantWeeperTearEntity entityarrow = GiantWeeperTearEntity.shoot(world, entity, world.m_213780_(), 2.0F, 5.0, 1);
         itemstack.m_41622_(1, entity, e -> e.m_21190_(entity.m_7655_()));
         entityarrow.f_36705_ = Pickup.DISALLOWED;
         entity.m_21253_();
      }
   }
}
