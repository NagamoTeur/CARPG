package io.redspace.ironsspellbooks.item;

import com.mojang.datafixers.util.Pair;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet.Direct;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.minecraft.world.level.saveddata.maps.MapDecoration.Type;

public class FurledMapItem extends Item {
   public static String FURLED_MAP_NBT = "furledMapData";
   public static String FURLED_MAP_LOCATION = "destination";
   public static String FURLED_MAP_DESCRIPTION = "description";

   public FurledMapItem() {
      super(ItemPropertiesHelper.material().m_41487_(1));
   }

   public InteractionResultHolder<ItemStack> m_7203_(Level level, Player player, InteractionHand hand) {
      if (level instanceof ServerLevel serverlevel) {
         ItemStack itemStack = player.m_21120_(hand);
         CompoundTag tag = itemStack.m_41783_();
         level.m_6269_(null, player, SoundEvents.f_12493_, player.m_5720_(), 1.0F, 1.0F);
         player.m_36335_().m_41524_((Item)ItemRegistry.FURLED_MAP.get(), 50);
         if (tag != null && tag.m_128425_(FURLED_MAP_NBT, 10) && tag.m_128469_(FURLED_MAP_NBT).m_128441_(FURLED_MAP_LOCATION)) {
            ResourceLocation destinationResource = new ResourceLocation(tag.m_128469_(FURLED_MAP_NBT).m_128461_(FURLED_MAP_LOCATION));
            ResourceKey<Structure> structureResourceKey = ResourceKey.m_135785_(Registry.f_235725_, destinationResource);
            Optional<Direct<Structure>> holder = serverlevel.m_5962_()
               .m_175515_(Registry.f_235725_)
               .m_203636_(structureResourceKey)
               .map(xva$0 -> HolderSet.m_205809_(new Holder[]{xva$0}));
            if (holder.isPresent()) {
               Pair<BlockPos, Holder<Structure>> pair = serverlevel.m_7726_()
                  .m_8481_()
                  .m_223037_(serverlevel, (HolderSet)holder.get(), player.m_20183_(), 100, (Boolean)ServerConfigs.FURLED_MAPS_SKIP_CHUNKS.get());
               if (pair != null) {
                  BlockPos blockpos = (BlockPos)pair.getFirst();
                  ItemStack mapStack = MapItem.m_42886_(serverlevel, blockpos.m_123341_(), blockpos.m_123343_(), (byte)2, true, true);
                  MapItem.m_42850_(serverlevel, mapStack);
                  MapItemSavedData.m_77925_(mapStack, blockpos, "x", Type.RED_X);
                  if (tag.m_128469_(FURLED_MAP_NBT).m_128441_(FURLED_MAP_DESCRIPTION)) {
                     Component mapTitle = Serializer.m_130701_(tag.m_128469_(FURLED_MAP_NBT).m_128461_(FURLED_MAP_DESCRIPTION));
                     mapStack.m_41714_(mapTitle);
                  }

                  replaceItem(player, mapStack, hand);
                  return InteractionResultHolder.m_19092_(itemStack, level.f_46443_);
               }
            }
         }

         replaceItem(player, new ItemStack(Items.f_42676_), hand);
      }

      return super.m_7203_(level, player, hand);
   }

   private static void replaceItem(Player player, ItemStack itemStack, InteractionHand hand) {
      boolean flag = player.m_150110_().f_35937_;
      if (!flag) {
         player.m_21008_(hand, itemStack);
      } else {
         player.m_150109_().m_36054_(itemStack);
      }
   }

   public static ItemStack of(ResourceLocation structure, MutableComponent descriptor) {
      ItemStack itemStack = new ItemStack((ItemLike)ItemRegistry.FURLED_MAP.get());
      itemStack.m_41698_(FURLED_MAP_NBT).m_128359_(FURLED_MAP_LOCATION, structure.toString());
      itemStack.m_41698_(FURLED_MAP_NBT).m_128359_(FURLED_MAP_DESCRIPTION, Serializer.m_130703_(descriptor));
      ListTag lore = new ListTag();
      lore.add(
         StringTag.m_129297_(
            Serializer.m_130703_(
               Component.m_237110_("item.irons_spellbooks.furled_map_descriptor_framing", new Object[]{descriptor})
                  .m_6270_(Style.f_131099_.m_131140_(ChatFormatting.GRAY))
            )
         )
      );
      itemStack.m_41698_("display").m_128365_("Lore", lore);
      return itemStack;
   }
}
