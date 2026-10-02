package com.hollingsworth.arsnouveau.client.container;

import com.hollingsworth.arsnouveau.common.network.ClientToServerStoragePacket;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.ServerToClientStoragePacket;
import io.netty.buffer.Unpooled;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class TerminalSyncManager {
   private static final int MAX_PACKET_SIZE = 32000;
   private Object2IntMap<StoredItemStack> idMap = new Object2IntOpenHashMap();
   private Int2ObjectMap<StoredItemStack> idMap2 = new Int2ObjectArrayMap();
   private Object2LongMap<StoredItemStack> items = new Object2LongOpenHashMap();
   private Map<StoredItemStack, StoredItemStack> itemList = new HashMap<>();
   private int lastId = 1;
   private FriendlyByteBuf workBuf = new FriendlyByteBuf(Unpooled.buffer());

   private void writeStack(FriendlyByteBuf buf, StoredItemStack stack) {
      ItemStack st = stack.getStack();
      Item item = st.m_41720_();
      CompoundTag compoundtag = getSyncTag(st);
      byte flags = (byte)((stack.getQuantity() == 0L ? 1 : 0) | (compoundtag != null ? 2 : 0));
      boolean wr = true;
      int id = this.idMap.getInt(stack);
      if (id != 0) {
         flags = (byte)(flags | 4);
         wr = false;
      }

      buf.writeByte(flags);
      buf.m_130130_(this.idMap.computeIfAbsent(stack, s -> {
         int i = this.lastId++;
         this.idMap2.put(i, (StoredItemStack)s);
         return i;
      }));
      if (wr) {
         writeItemId(buf, item);
      }

      if (stack.getQuantity() != 0L) {
         buf.m_130103_(stack.getQuantity());
      }

      if (wr && compoundtag != null) {
         buf.m_130079_(compoundtag);
      }
   }

   private StoredItemStack read(FriendlyByteBuf buf) {
      byte flags = buf.readByte();
      int id = buf.m_130242_();
      boolean rd = (flags & 4) == 0;
      StoredItemStack stack;
      if (rd) {
         stack = new StoredItemStack(new ItemStack(readItemId(buf)));
      } else {
         stack = new StoredItemStack(((StoredItemStack)this.idMap2.get(id)).getStack());
      }

      long count = (flags & 1) != 0 ? 0L : buf.m_130258_();
      stack.setCount(count);
      if (rd && (flags & 2) != 0) {
         stack.getStack().m_41751_(buf.m_130260_());
      }

      this.idMap.put(stack, id);
      this.idMap2.put(id, stack);
      return stack;
   }

   public void update(Map<StoredItemStack, Long> items, ServerPlayer player, Consumer<CompoundTag> extraSync) {
      List<StoredItemStack> toWrite = new ArrayList<>();
      Set<StoredItemStack> found = new HashSet<>();
      items.forEach((sx, c) -> {
         long pc = this.items.getLong(sx);
         if (pc != 0L) {
            found.add(sx);
         }

         if (pc != c) {
            toWrite.add(new StoredItemStack(sx.getStack(), c));
         }
      });
      this.items.forEach((sx, c) -> {
         if (!found.contains(sx)) {
            toWrite.add(new StoredItemStack(sx.getStack(), 0L));
         }
      });
      this.items.clear();
      this.items.putAll(items);
      if (!toWrite.isEmpty()) {
         this.workBuf.writerIndex(0);
         int j = 0;

         for (int i = 0; i < toWrite.size(); j++) {
            StoredItemStack stack = toWrite.get(i);
            int li = this.workBuf.writerIndex();
            this.writeStack(this.workBuf, stack);
            int s = this.workBuf.writerIndex();
            if ((s > 32000 || j > 32000) && j > 1) {
               CompoundTag t = this.writeBuf("d", this.workBuf, li);
               t.m_128376_("l", (short)j);
               Networking.sendToPlayerClient(new ServerToClientStoragePacket(t), player);
               j = 0;
               this.workBuf.writerIndex(0);
               this.workBuf.writeBytes(this.workBuf, li, s - li);
            }

            i++;
         }

         if (j > 0 || extraSync != null) {
            CompoundTag t;
            if (j > 0) {
               t = this.writeBuf("d", this.workBuf, this.workBuf.writerIndex());
               t.m_128376_("l", (short)j);
            } else {
               t = new CompoundTag();
            }

            if (extraSync != null) {
               extraSync.accept(t);
            }

            Networking.sendToPlayerClient(new ServerToClientStoragePacket(t), player);
         }
      } else if (extraSync != null) {
         CompoundTag tx = new CompoundTag();
         extraSync.accept(tx);
         Networking.sendToPlayerClient(new ServerToClientStoragePacket(tx), player);
      }
   }

   public boolean receiveUpdate(CompoundTag tag) {
      if (!tag.m_128441_("d")) {
         return false;
      } else {
         FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.wrappedBuffer(tag.m_128463_("d")));
         List<StoredItemStack> in = new ArrayList<>();
         short len = tag.m_128448_("l");

         for (int i = 0; i < len; i++) {
            in.add(this.read(buf));
         }

         in.forEach(s -> {
            if (s.getQuantity() == 0L) {
               this.itemList.remove(s);
            } else {
               this.itemList.put(s, s);
            }
         });
         return true;
      }
   }

   public void sendClientInteract(StoredItemStack intStack, StorageTerminalMenu.SlotAction action, boolean pullOne) {
      CompoundTag interactTag = new CompoundTag();
      interactTag.m_128379_("pullOne", pullOne);
      interactTag.m_128405_("action", action.ordinal());
      if (intStack != null) {
         interactTag.m_128405_("id", this.idMap.getInt(intStack));
         interactTag.m_128356_("qty", intStack.getQuantity());
      }

      CompoundTag dataTag = new CompoundTag();
      dataTag.m_128365_("interaction", interactTag);
      Networking.sendToServer(new ClientToServerStoragePacket(dataTag));
   }

   private CompoundTag writeBuf(String id, FriendlyByteBuf buf, int len) {
      byte[] data = new byte[len];
      buf.getBytes(0, data);
      CompoundTag tag = new CompoundTag();
      tag.m_128382_(id, data);
      return tag;
   }

   public void receiveInteract(CompoundTag tag, StorageTerminalMenu handler) {
      if (tag.m_128441_("interaction")) {
         CompoundTag interactTag = tag.m_128469_("interaction");
         boolean pullOne = interactTag.m_128471_("pullOne");
         StoredItemStack stack = null;
         if (interactTag.m_128441_("id")) {
            stack = new StoredItemStack(((StoredItemStack)this.idMap2.get(interactTag.m_128451_("id"))).getStack());
            stack.setCount(interactTag.m_128454_("qty"));
         }

         StorageTerminalMenu.SlotAction action = StorageTerminalMenu.SlotAction.values()[interactTag.m_128451_("action")];
         handler.onInteract(stack, action, pullOne);
      }
   }

   public List<StoredItemStack> getAsList() {
      return new ArrayList<>(this.itemList.values());
   }

   public long getAmount(StoredItemStack stack) {
      StoredItemStack s = this.itemList.get(stack);
      return s != null ? s.getQuantity() : 0L;
   }

   public static ResourceLocation getItemId(Item item) {
      return ForgeRegistries.ITEMS.getKey(item);
   }

   public static void writeItemId(FriendlyByteBuf buf, Item item) {
      buf.m_236818_(Registry.f_122827_, item);
   }

   public static Item readItemId(FriendlyByteBuf buf) {
      return (Item)buf.m_236816_(Registry.f_122827_);
   }

   public static CompoundTag getSyncTag(ItemStack stack) {
      Item item = stack.m_41720_();
      CompoundTag compoundtag = null;
      if (item.isDamageable(stack) || item.m_41468_()) {
         compoundtag = stack.getShareTag();
      }

      return compoundtag;
   }
}
