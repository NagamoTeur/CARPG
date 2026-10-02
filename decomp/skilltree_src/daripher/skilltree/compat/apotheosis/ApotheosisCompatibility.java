package daripher.skilltree.compat.apotheosis;

import com.google.common.collect.ImmutableList;
import daripher.skilltree.compat.apotheosis.gem.PSTGemBonus;
import daripher.skilltree.compat.apotheosis.gem.PSTGemsProvider;
import daripher.skilltree.entity.player.PlayerHelper;
import daripher.skilltree.item.ItemHelper;
import daripher.skilltree.skill.bonus.item.ItemBonus;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.Apoth.Affixes;
import shadows.apotheosis.adventure.affix.socket.SocketHelper;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemInstance;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.GemManager;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.event.GetItemSocketsEvent;
import shadows.apotheosis.adventure.loot.GemLootPoolEntry;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;

public enum ApotheosisCompatibility {
   INSTANCE;

   public void addCompatibility() {
      GemBonus.CODECS.put(new ResourceLocation("skilltree", "gem_bonus"), PSTGemBonus.CODEC);
      IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
      modEventBus.addListener(this::generateDataFiles);
      IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;
      forgeEventBus.addListener(this::addItemSockets);
   }

   public List<ItemStack> getGems(ItemStack stack, int sockets) {
      if (sockets != 0 && !stack.m_41619_()) {
         List<ItemStack> gems = NonNullList.m_122780_(sockets, ItemStack.f_41583_);
         int i = 0;
         CompoundTag afxData = stack.m_41737_("affix_data");
         if (afxData != null && afxData.m_128441_("gems")) {
            for (Tag tag : afxData.m_128437_("gems", 10)) {
               ItemStack gemStack = ItemStack.m_41712_((CompoundTag)tag);
               gemStack.m_41764_(1);
               if (GemInstance.unsocketed(gemStack).isValidUnsocketed()) {
                  gems.set(i++, gemStack);
               }

               if (i >= sockets) {
                  break;
               }
            }
         }

         return ImmutableList.copyOf(gems);
      } else {
         return Collections.emptyList();
      }
   }

   public List<? extends ItemBonus<?>> getGemBonuses(ItemStack stack) {
      List<ItemBonus<?>> list = new ArrayList<>();

      for (ItemStack gemStack : SocketHelper.getGems(stack)) {
         Gem gem = GemItem.getGem(gemStack);
         if (gem != null) {
            for (GemBonus gemBonus : gem.getBonuses()) {
               Set<LootCategory> lootCategories = gemBonus.getGemClass().types();
               if (!lootCategories.stream().noneMatch(c -> c.isValid(stack)) && gemBonus instanceof PSTGemBonus aBonus) {
                  list.add(aBonus.getBonus(gemStack));
                  break;
               }
            }
         }
      }

      return list;
   }

   public List<ItemStack> getGems(ItemStack stack) {
      return this.getGems(stack, this.getSockets(stack, null));
   }

   public int getSockets(ItemStack stack, @Nullable Player player) {
      int playerSockets = player == null ? 0 : PlayerHelper.getPlayerSockets(stack, player);
      int sockets = SocketHelper.getSockets(stack);
      int gems = getActiveGems(stack).size();
      playerSockets -= gems;
      if (playerSockets < 0) {
         playerSockets = 0;
      }

      return sockets + playerSockets;
   }

   @NotNull
   private static List<Gem> getActiveGems(ItemStack stack) {
      return SocketHelper.getGems(stack).stream().<Gem>map(GemItem::getGem).filter(Objects::nonNull).toList();
   }

   public boolean hasEmptySockets(ItemStack stack, Player player) {
      return this.getGems(stack, this.getSockets(stack, player)).stream().<Gem>map(GemItem::getGem).anyMatch(Objects::isNull);
   }

   public int getFirstEmptySocket(ItemStack stack, int sockets) {
      List<ItemStack> gems = this.getGems(stack, sockets);

      for (int socket = 0; socket < sockets; socket++) {
         Gem gem = GemItem.getGem(gems.get(socket));
         if (gem == null) {
            return socket;
         }
      }

      return 0;
   }

   private void generateDataFiles(GatherDataEvent event) {
      DataGenerator dataGenerator = event.getGenerator();
      ExistingFileHelper fileHelper = event.getExistingFileHelper();
      dataGenerator.m_236039_(event.includeServer(), new PSTGemsProvider(dataGenerator, fileHelper));
   }

   public void createGemStack(Consumer<ItemStack> consumer, LootContext context, ResourceLocation gemTypeId) {
      Player player = GemLootPoolEntry.findPlayer(context);
      if (player != null) {
         ItemStack gemStack = this.getGemStack(gemTypeId);
         if (!gemStack.m_41619_()) {
            consumer.accept(gemStack);
         }
      }
   }

   public ItemStack getGemStack(ResourceLocation gemTypeId) {
      Gem gem = (Gem)GemManager.INSTANCE.getValue(gemTypeId);
      if (gem == null) {
         return ItemStack.f_41583_;
      } else {
         LootRarity rarity = gem.getMinRarity();
         return rarity == null ? ItemStack.f_41583_ : GemManager.createGemStack(gem, rarity);
      }
   }

   @Nullable
   public ResourceLocation getGemId(ItemStack stack) {
      Gem gem = GemItem.getGem(stack);
      return gem == null ? null : gem.getId();
   }

   public boolean adventureModuleEnabled() {
      return Apotheosis.enableAdventure;
   }

   private void addItemSockets(GetItemSocketsEvent event) {
      if (Affixes.SOCKET.get() != null) {
         ItemStack stack = event.getStack();
         if (ItemHelper.hasSockets(stack)) {
            int sockets = event.getSockets();
            if (event.getSockets() == 0) {
               int defaultSockets = ItemHelper.getDefaultSockets(stack);
               SocketHelper.setSockets(stack, defaultSockets);
               sockets += defaultSockets;
            }

            sockets += ItemHelper.getAdditionalSockets(stack);
            CompoundTag affixTag = stack.m_41737_("affix_data");
            if (affixTag != null && affixTag.m_128441_("gems")) {
               ListTag gemsTag = affixTag.m_128437_("gems", 10);
               if (sockets < gemsTag.size()) {
                  sockets = gemsTag.size();
               }
            }

            event.setSockets(sockets);
         }
      }
   }
}
