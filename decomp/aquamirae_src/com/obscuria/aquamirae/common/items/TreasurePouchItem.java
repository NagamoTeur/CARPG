package com.obscuria.aquamirae.common.items;

import com.obscuria.aquamirae.Aquamirae;
import com.obscuria.aquamirae.registry.AquamiraeSounds;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
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
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

public class TreasurePouchItem extends Item {
   public TreasurePouchItem() {
      super(new Properties().m_41491_(Aquamirae.TAB).m_41487_(16).m_41497_(Rarity.UNCOMMON));
   }

   @NotNull
   public InteractionResultHolder<ItemStack> m_7203_(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
      InteractionResultHolder<ItemStack> resultHolder = super.m_7203_(world, player, hand);
      ItemStack stack = (ItemStack)resultHolder.m_19095_();
      player.m_6674_(hand);
      if (!world.f_46443_) {
         world.m_5594_(
            player,
            new BlockPos(player.m_20185_(), player.m_20186_() + 1.0, player.m_20189_()),
            (SoundEvent)AquamiraeSounds.ITEM_TREASURE_POUCH_OPEN.get(),
            SoundSource.PLAYERS,
            1.0F,
            1.0F
         );
         List<ItemStack> loot = Aquamirae.SetBuilder.rare();
         player.m_36356_(loot.get(player.m_217043_().m_216339_(0, loot.size() - 1)));
         MinecraftServer minecraftServer = player.f_19853_.m_7654_();
         if (minecraftServer != null && player.f_19853_ instanceof ServerLevel server) {
            LootContext lootContext = new Builder(server)
               .m_230911_(player.m_217043_())
               .m_78972_(LootContextParams.f_81455_, player)
               .m_78972_(LootContextParams.f_81460_, player.m_20182_())
               .m_78975_(LootContextParamSets.f_81416_);
            LootTable treasure = minecraftServer.m_129898_().m_79217_(new ResourceLocation("aquamirae", "gameplay/treasure_pouch"));
            treasure.m_230922_(lootContext).forEach(player::m_36356_);
            if (Math.random() <= 0.1F) {
               player.m_36356_(Aquamirae.getStructureMap(player.m_217043_().m_188499_() ? Aquamirae.SHIP : Aquamirae.OUTPOST, server, player));
            }
         }
      }

      stack.m_41774_(1);
      return resultHolder;
   }
}
