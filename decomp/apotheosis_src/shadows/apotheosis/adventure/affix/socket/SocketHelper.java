package shadows.apotheosis.adventure.affix.socket;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemInstance;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.event.GetItemSocketsEvent;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.util.CachedObject;
import shadows.placebo.util.CachedObject.CachedObjectSource;

public class SocketHelper {
   public static final ResourceLocation GEMS_CACHED_OBJECT = Apotheosis.loc("gems");
   public static final String AFFIX_DATA = "affix_data";
   public static final String GEMS = "gems";
   public static final String SOCKETS = "sockets";

   public static List<ItemStack> getGems(ItemStack stack) {
      return (List<ItemStack>)CachedObjectSource.getOrCreate(stack, GEMS_CACHED_OBJECT, SocketHelper::getGemsImpl, CachedObject.hashSubkey("affix_data"));
   }

   private static List<ItemStack> getGemsImpl(ItemStack stack) {
      int size = getSockets(stack);
      if (size > 0 && !stack.m_41619_()) {
         List<ItemStack> gems = NonNullList.m_122780_(size, ItemStack.f_41583_);
         int i = 0;
         CompoundTag afxData = stack.m_41737_("affix_data");
         if (afxData != null && afxData.m_128441_("gems")) {
            for (Tag tag : afxData.m_128437_("gems", 10)) {
               ItemStack gemStack = ItemStack.m_41712_((CompoundTag)tag);
               gemStack.m_41764_(1);
               if (GemInstance.unsocketed(gemStack).isValidUnsocketed()) {
                  gems.set(i++, gemStack);
               }

               if (i >= size) {
                  break;
               }
            }
         }

         return ImmutableList.copyOf(gems);
      } else {
         return Collections.emptyList();
      }
   }

   public static Stream<GemInstance> getGemInstances(ItemStack stack) {
      return getGems(stack).stream().map(gemStack -> GemInstance.socketed(stack, gemStack)).filter(GemInstance::isValid);
   }

   public static void setGems(ItemStack stack, List<ItemStack> gems) {
      CompoundTag afxData = stack.m_41698_("affix_data");
      ListTag gemData = new ListTag();

      for (ItemStack s : gems) {
         gemData.add(s.m_41739_(new CompoundTag()));
      }

      afxData.m_128365_("gems", gemData);
   }

   public static int getSockets(ItemStack stack) {
      AffixInstance socketAffix = AffixHelper.getAffixes(stack).get(Apoth.Affixes.SOCKET.get());
      int sockets = socketAffix != null ? (int)socketAffix.level() : 0;
      GetItemSocketsEvent event = new GetItemSocketsEvent(stack, sockets);
      MinecraftForge.EVENT_BUS.post(event);
      return event.getSockets();
   }

   public static void setSockets(ItemStack stack, int sockets) {
      Map<Affix, AffixInstance> affixes = AffixHelper.getAffixes(stack);
      affixes.put((Affix)Apoth.Affixes.SOCKET.get(), new AffixInstance((Affix)Apoth.Affixes.SOCKET.get(), stack, LootRarity.COMMON, (float)sockets));
      AffixHelper.setAffixes(stack, affixes);
   }

   public static boolean hasEmptySockets(ItemStack stack) {
      return getGems(stack).stream().map(GemItem::getGem).anyMatch(Objects::isNull);
   }

   public static int getFirstEmptySocket(ItemStack stack) {
      List<ItemStack> gems = getGems(stack);

      for (int socket = 0; socket < gems.size(); socket++) {
         Gem gem = GemItem.getGem(gems.get(socket));
         if (gem == null) {
            return socket;
         }
      }

      return 0;
   }

   public static void loadSocketAffix(ItemStack stack, Map<Affix, AffixInstance> affixes) {
      int sockets = getSockets(stack);
      if (sockets > 0) {
         affixes.put((Affix)Apoth.Affixes.SOCKET.get(), new AffixInstance((Affix)Apoth.Affixes.SOCKET.get(), stack, LootRarity.UNCOMMON, (float)sockets));
      }
   }

   public static void loadSocketAffix(AbstractArrow arrow, Map<Affix, AffixInstance> affixes) {
      CompoundTag afxData = arrow.getPersistentData().m_128469_("affix_data");
      int sockets = afxData != null ? afxData.m_128451_("sockets") : 0;
      if (sockets > 0) {
         affixes.put(
            (Affix)Apoth.Affixes.SOCKET.get(), new AffixInstance((Affix)Apoth.Affixes.SOCKET.get(), ItemStack.f_41583_, LootRarity.UNCOMMON, (float)sockets)
         );
      }
   }

   private static List<ItemStack> getGems(AbstractArrow arrow) {
      CompoundTag afxData = arrow.getPersistentData().m_128469_("affix_data");
      int sockets = afxData != null ? afxData.m_128451_("sockets") : 0;
      if (sockets <= 0) {
         return Collections.emptyList();
      } else {
         List<ItemStack> gems = NonNullList.m_122780_(sockets, ItemStack.f_41583_);
         int i = 0;
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

         return gems;
      }
   }

   public static Stream<GemInstance> getGemInstances(AbstractArrow arrow) {
      LootCategory cat = AffixHelper.getShooterCategory(arrow);
      return cat == null ? Stream.empty() : getGems(arrow).stream().map(gemStack -> GemInstance.socketed(cat, gemStack)).filter(GemInstance::isValid);
   }
}
