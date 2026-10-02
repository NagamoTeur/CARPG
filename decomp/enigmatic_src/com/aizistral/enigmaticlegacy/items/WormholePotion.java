package com.aizistral.enigmaticlegacy.items;

import com.aizistral.enigmaticlegacy.EnigmaticLegacy;
import com.aizistral.enigmaticlegacy.crafting.BindToPlayerRecipe;
import com.aizistral.enigmaticlegacy.helpers.ItemLoreHelper;
import com.aizistral.enigmaticlegacy.helpers.ItemNBTHelper;
import com.aizistral.enigmaticlegacy.items.generic.ItemBase;
import com.aizistral.enigmaticlegacy.packets.clients.PacketPortalParticles;
import com.aizistral.enigmaticlegacy.packets.clients.PacketRecallParticles;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.TargetPoint;

public class WormholePotion extends ItemBase implements BindToPlayerRecipe.IBound {
   public WormholePotion() {
      super(ItemBase.getDefaultProperties().m_41487_(1).m_41497_(Rarity.RARE));
   }

   @OnlyIn(Dist.CLIENT)
   public void m_7373_(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag flagIn) {
      if (Screen.m_96638_()) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.wormholePotion1");
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.wormholePotion2");
      } else {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.holdShift");
      }

      if (ItemNBTHelper.verifyExistance(stack, "BoundPlayer")) {
         ItemLoreHelper.addLocalizedString(list, "tooltip.enigmaticlegacy.void");
         ItemLoreHelper.addLocalizedString(
            list, "tooltip.enigmaticlegacy.boundToPlayer", ChatFormatting.DARK_RED, ItemNBTHelper.getString(stack, "BoundPlayer", "Herobrine")
         );
      }
   }

   public ItemStack m_5922_(ItemStack stack, Level worldIn, LivingEntity entityLiving) {
      if (!(entityLiving instanceof Player player)) {
         return stack;
      } else {
         if (player instanceof ServerPlayer) {
            CriteriaTriggers.f_10592_.m_23682_((ServerPlayer)player, stack);
         }

         Player receiver = this.getBoundPlayer(worldIn, stack);
         if (!worldIn.f_46443_ && receiver != null) {
            Vec3 vec = receiver.m_20182_();

            while (vec.m_82554_(receiver.m_20182_()) < 1.0) {
               vec = receiver.m_20182_().m_82520_((random.nextDouble() - 0.5) * 4.0, 0.0, (random.nextDouble() - 0.5) * 4.0);
            }

            worldIn.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketPortalParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 100, 1.25, false)
               );
            player.m_6021_(vec.f_82479_, vec.f_82480_ + 0.25, vec.f_82481_);
            worldIn.m_5594_(null, player.m_20183_(), SoundEvents.f_11852_, SoundSource.PLAYERS, 1.0F, (float)(0.8F + Math.random() * 0.2));
            EnigmaticLegacy.packetInstance
               .send(
                  PacketDistributor.NEAR
                     .with(() -> new TargetPoint(player.m_20185_(), player.m_20186_(), player.m_20189_(), 128.0, player.f_19853_.m_46472_())),
                  new PacketRecallParticles(player.m_20185_(), player.m_20186_() + (double)(player.m_20206_() / 2.0F), player.m_20189_(), 48, false)
               );
         }

         if (!player.m_150110_().f_35937_) {
            stack.m_41774_(1);
            if (stack.m_41619_()) {
               return new ItemStack(Items.f_42590_);
            }

            player.m_150109_().m_36054_(new ItemStack(Items.f_42590_));
         }

         return stack;
      }
   }

   public int m_8105_(ItemStack stack) {
      return 32;
   }

   public UseAnim m_6164_(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
      ItemStack stack = player.m_21120_(hand);
      Player receiver = this.getBoundPlayer(world, stack);
      if (receiver != null) {
         player.m_6672_(hand);
         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.FAIL, stack);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(ItemStack stack) {
      return true;
   }
}
