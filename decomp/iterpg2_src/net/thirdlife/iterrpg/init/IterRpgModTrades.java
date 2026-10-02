package net.thirdlife.iterrpg.init;

import java.util.List;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.BasicItemListing;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(
   bus = Bus.FORGE
)
public class IterRpgModTrades {
   @SubscribeEvent
   public static void registerWanderingTrades(WandererTradesEvent event) {
      event.getGenericTrades().add(new BasicItemListing(new ItemStack((ItemLike)IterRpgModItems.COIN.get(), 6), new ItemStack(Items.f_42616_), 10, 5, 0.05F));
      event.getGenericTrades()
         .add(new BasicItemListing(new ItemStack(Items.f_42616_, 16), new ItemStack((ItemLike)IterRpgModBlocks.TWIFFLE.get()), 2, 10, 0.3F));
      event.getGenericTrades()
         .add(new BasicItemListing(new ItemStack(Items.f_42616_, 12), new ItemStack((ItemLike)IterRpgModItems.EMPTY_RUNE.get()), 2, 5, 0.3F));
      event.getGenericTrades()
         .add(new BasicItemListing(new ItemStack(Items.f_42616_, 24), new ItemStack((ItemLike)IterRpgModBlocks.ARCANE_FLOWER.get()), 6, 5, 0.05F));
      event.getGenericTrades()
         .add(new BasicItemListing(new ItemStack(Items.f_42616_, 4), new ItemStack((ItemLike)IterRpgModItems.COIN.get(), 4), 10, 4, 0.05F));
   }

   @SubscribeEvent
   public static void registerTrades(VillagerTradesEvent event) {
      if (event.getType() == VillagerProfession.f_35591_) {
         ((List)event.getTrades().get(4))
            .add(new BasicItemListing(new ItemStack((ItemLike)IterRpgModItems.STARFISH.get()), new ItemStack(Items.f_42616_, 8), 2, 10, 0.05F));
         ((List)event.getTrades().get(1))
            .add(new BasicItemListing(new ItemStack((ItemLike)IterRpgModItems.PEARL.get()), new ItemStack(Items.f_42616_, 4), 10, 5, 0.05F));
      }

      if (event.getType() == VillagerProfession.f_35589_) {
         ((List)event.getTrades().get(3))
            .add(new BasicItemListing(new ItemStack(Items.f_42616_, 4), new ItemStack((ItemLike)IterRpgModItems.ARCANE_POWDER.get()), 10, 5, 0.05F));
         ((List)event.getTrades().get(3))
            .add(new BasicItemListing(new ItemStack(Items.f_42616_, 4), new ItemStack((ItemLike)IterRpgModItems.ABYSSQUARTZ_SHARD.get(), 2), 10, 5, 0.05F));
      }

      if (event.getType() == VillagerProfession.f_35595_) {
         ((List)event.getTrades().get(2))
            .add(new BasicItemListing(new ItemStack(Items.f_42616_, 2), new ItemStack((ItemLike)IterRpgModItems.POTSHERD.get()), 10, 4, 0.1F));
         ((List)event.getTrades().get(1))
            .add(new BasicItemListing(new ItemStack((ItemLike)IterRpgModItems.POTSHERD.get(), 4), new ItemStack(Items.f_42616_), 10, 5, 0.05F));
      }
   }
}
