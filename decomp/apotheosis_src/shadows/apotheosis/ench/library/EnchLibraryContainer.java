package shadows.apotheosis.ench.library;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import shadows.apotheosis.Apoth;
import shadows.placebo.container.BlockEntityContainer;
import shadows.placebo.packets.ButtonClickMessage.IButtonContainer;

public class EnchLibraryContainer extends BlockEntityContainer<EnchLibraryTile> implements IButtonContainer {
   protected SimpleContainer ioInv = new SimpleContainer(3);
   protected Runnable notifier = null;

   public EnchLibraryContainer(int id, Inventory inv, BlockPos pos) {
      super((MenuType)Apoth.Menus.LIBRARY.get(), id, inv, pos);
      ((EnchLibraryTile)this.tile).addListener(this);
      this.initCommon(inv);
   }

   public void m_6877_(Player player) {
      super.m_6877_(player);
      if (!this.level.f_46443_) {
         ((EnchLibraryTile)this.tile).removeListener(this);
      }

      this.m_150411_(player, this.ioInv);
   }

   void initCommon(final Inventory inv) {
      this.m_38897_(new Slot(this.ioInv, 0, 148, 93) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_41720_() == Items.f_42690_;
         }

         public int m_6641_() {
            return 1;
         }

         public void m_6654_() {
            super.m_6654_();
            if (!EnchLibraryContainer.this.level.f_46443_ && !this.m_7993_().m_41619_()) {
               ((EnchLibraryTile)EnchLibraryContainer.this.tile).depositBook(this.m_7993_());
            }

            if (!this.m_7993_().m_41619_() && EnchLibraryContainer.this.level.f_46443_) {
               inv.f_35978_.f_19853_.m_5594_(inv.f_35978_, EnchLibraryContainer.this.pos, SoundEvents.f_11887_, SoundSource.NEUTRAL, 0.5F, 0.7F);
            }

            EnchLibraryContainer.this.ioInv.m_6836_(0, ItemStack.f_41583_);
         }
      });
      this.m_38897_(new Slot(this.ioInv, 1, 148, 127) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_41720_() == Items.f_42690_;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.m_38897_(new Slot(this.ioInv, 2, 92, 61) {
         public boolean m_5857_(ItemStack stack) {
            return true;
         }

         public int m_6641_() {
            return 1;
         }

         public void m_6654_() {
            EnchLibraryContainer.this.onChanged();
         }
      });
      this.addPlayerSlots(inv, 8, 159);
      this.mover.registerRule((stack, slot) -> slot == 0, 3, 39);
      this.mover.registerRule((stack, slot) -> slot == 1, 3, 39);
      this.mover.registerRule((stack, slot) -> slot == 2, 3, 39);
      this.mover.registerRule((stack, slot) -> stack.m_150930_(Items.f_42690_), 0, 1);
      this.mover.registerRule((stack, slot) -> true, 2, 3);
      this.registerInvShuffleRules();
   }

   public boolean m_6875_(Player player) {
      return player.m_20275_((double)this.pos.m_123341_(), (double)this.pos.m_123342_(), (double)this.pos.m_123343_()) < 256.0
         && this.tile != null
         && !((EnchLibraryTile)this.tile).m_58901_();
   }

   public int getNumStoredEnchants() {
      return (int)((EnchLibraryTile)this.tile).getPointsMap().values().intStream().filter(s -> s > 0).count();
   }

   public List<Entry<Enchantment>> getPointsForDisplay() {
      return ((EnchLibraryTile)this.tile).getPointsMap().object2IntEntrySet().stream().filter(s -> s.getIntValue() > 0).toList();
   }

   public int getMaxLevel(Enchantment enchant) {
      return ((EnchLibraryTile)this.tile).getMax(enchant);
   }

   public int getPointCap() {
      return ((EnchLibraryTile)this.tile).maxPoints;
   }

   public void setNotifier(Runnable r) {
      this.notifier = r;
   }

   public void onChanged() {
      if (this.notifier != null) {
         this.notifier.run();
      }
   }

   public void onButtonClick(int id) {
      boolean shift = (id & -2147483648) == Integer.MIN_VALUE;
      if (shift) {
         id &= Integer.MAX_VALUE;
      }

      Enchantment ench = (Enchantment)((ForgeRegistry)ForgeRegistries.ENCHANTMENTS).getValue(id);
      ItemStack outSlot = this.ioInv.m_8020_(1);
      int curLvl = EnchantmentHelper.m_44831_(outSlot).getOrDefault(ench, 0);
      int targetLevel = shift
         ? Math.min(
            ((EnchLibraryTile)this.tile).getMax(ench),
            1 + (int)(Math.log((double)(((EnchLibraryTile)this.tile).getPointsMap().getInt(ench) + EnchLibraryTile.levelToPoints(curLvl))) / Math.log(2.0))
         )
         : curLvl + 1;
      if (((EnchLibraryTile)this.tile).canExtract(ench, targetLevel, curLvl)) {
         if (outSlot.m_41619_()) {
            outSlot = new ItemStack(Items.f_42690_);
         }

         ((EnchLibraryTile)this.tile).extractEnchant(outSlot, ench, targetLevel);
         this.ioInv.m_6836_(1, outSlot);
      }
   }
}
