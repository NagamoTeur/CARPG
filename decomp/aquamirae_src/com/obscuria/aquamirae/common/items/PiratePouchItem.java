package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import java.util.List;
import java.util.Random;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PiratePouchItem extends Item {
   public PiratePouchItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(16).m_41497_(Rarity.COMMON));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player entity, @NotNull InteractionHand hand) {
      InteractionResultHolder<ItemStack> ar = super.m_7203_(world, entity, hand);
      ItemStack sourceStack = (ItemStack)ar.m_19095_();
      entity.m_6674_(hand);
      if (entity.m_9236_() instanceof ServerLevel level) {
         level.m_5594_(
            null,
            new BlockPos(entity.m_20185_(), entity.m_20186_() + 1.0, entity.m_20189_()),
            (SoundEvent)AquamiraeSounds.ITEM_POUCH_OPEN.get(),
            SoundSource.PLAYERS,
            1.0F,
            1.0F
         );
      }

      List<ItemStack> loot = Aquamirae.SetBuilder.common();
      entity.m_36356_(loot.get(new Random().nextInt(0, loot.size() - 1)));
      sourceStack.m_41774_(1);
      return ar;
   }
}
