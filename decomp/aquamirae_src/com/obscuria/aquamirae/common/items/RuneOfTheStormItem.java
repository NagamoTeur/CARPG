package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.obscureapi.common.items.ObscureRarity;
import com.obscuria.obscureapi.util.ItemUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber
public class RuneOfTheStormItem extends Item {
   public RuneOfTheStormItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(1).m_41486_().m_41497_(ObscureRarity.MYTHIC));
   }

   @SubscribeEvent
   public static void onLivingDamage(@NotNull LivingDamageEvent event) {
      LivingEntity source = event.getSource().m_7639_() instanceof LivingEntity living ? living : null;
      if (source != null) {
         ItemStack weapon = source.m_21205_();
         if (ItemUtils.hasPerk(weapon, new ResourceLocation("aquamirae", "rune_of_the_storm"))
            && ((Biome)source.m_9236_().m_204166_(source.m_20183_()).m_203334_()).m_47554_() * 100.0F <= 0.0F) {
            event.setAmount(event.getAmount() * 1.33F);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public boolean m_5812_(@NotNull ItemStack itemstack) {
      return true;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, Player entity, @NotNull InteractionHand hand) {
      ItemStack stack = entity.m_21120_(hand);
      ItemStack offhand = entity.m_21120_(InteractionHand.OFF_HAND);
      if (hand != InteractionHand.MAIN_HAND) {
         return InteractionResultHolder.m_19100_(stack);
      } else if (offhand.m_41720_() instanceof SwordItem && !ItemUtils.hasPerk(offhand, new ResourceLocation("aquamirae", "rune_of_the_storm"))) {
         if (world instanceof ServerLevel level) {
            level.m_5594_(null, entity.m_20183_(), SoundEvents.f_11871_, SoundSource.PLAYERS, 2.0F, 1.0F);
         }

         entity.m_6674_(hand);
         stack.m_41774_(1);
         ItemUtils.addPerk(offhand, new ResourceLocation("aquamirae", "rune_of_the_storm"), 1);
         return InteractionResultHolder.m_19090_(stack);
      } else {
         return InteractionResultHolder.m_19100_(stack);
      }
   }
}
