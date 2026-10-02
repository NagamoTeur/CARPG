package shadows.apotheosis.ench.table;

import it.unimi.dsi.fastutil.floats.Float2FloatMap;
import it.unimi.dsi.fastutil.floats.Float2FloatOpenHashMap;
import it.unimi.dsi.fastutil.floats.Float2FloatMap.Entry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EnchantmentTableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags.Items;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.SlotItemHandler;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.advancements.EnchantedTrigger;
import shadows.apotheosis.util.ApothMiscUtil;
import shadows.apotheosis.util.FloatReferenceHolder;
import shadows.placebo.network.PacketDistro;
import shadows.placebo.util.EnchantmentUtils;

public class ApothEnchantmentMenu extends EnchantmentMenu {
   protected final FloatReferenceHolder eterna = new FloatReferenceHolder(0.0F, 0.0F, EnchantingStatManager.getAbsoluteMaxEterna());
   protected final FloatReferenceHolder quanta = new FloatReferenceHolder(0.0F, 0.0F, 100.0F);
   protected final FloatReferenceHolder arcana = new FloatReferenceHolder(0.0F, 0.0F, 100.0F);
   protected final FloatReferenceHolder rectification = new FloatReferenceHolder(0.0F, -100.0F, 100.0F);
   protected final DataSlot clues = DataSlot.m_39401_();
   protected final Player player;

   public ApothEnchantmentMenu(int id, Inventory inv) {
      super(id, inv, ContainerLevelAccess.f_39287_);
      this.player = inv.f_35978_;
      this.f_38839_.clear();
      this.addSecretSlot(new Slot(this.f_39449_, 0, 15, 47) {
         public boolean m_5857_(ItemStack stack) {
            return true;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.addSecretSlot(new Slot(this.f_39449_, 1, 35, 47) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_204117_(Items.ENCHANTING_FUELS);
         }
      });
      this.initCommon(inv);
   }

   public ApothEnchantmentMenu(int id, Inventory inv, ContainerLevelAccess wPos, ApothEnchantTile te) {
      super(id, inv, wPos);
      this.player = inv.f_35978_;
      this.f_38839_.clear();
      this.addSecretSlot(new Slot(this.f_39449_, 0, 15, 47) {
         public boolean m_5857_(ItemStack stack) {
            return true;
         }

         public int m_6641_() {
            return 1;
         }
      });
      this.addSecretSlot(new SlotItemHandler(te.inv, 0, 35, 47) {
         public boolean m_5857_(ItemStack stack) {
            return stack.m_204117_(Items.ENCHANTING_FUELS);
         }
      });
      this.initCommon(inv);
   }

   protected Slot addSecretSlot(Slot pSlot) {
      pSlot.f_40219_ = this.f_38839_.size();
      this.f_38839_.add(pSlot);
      return pSlot;
   }

   private void initCommon(Inventory inv) {
      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 9; j++) {
            this.addSecretSlot(new Slot(inv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18 + 31));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.addSecretSlot(new Slot(inv, k, 8 + k * 18, 173));
      }

      this.m_38884_(this.eterna.getArray());
      this.m_38884_(this.quanta.getArray());
      this.m_38884_(this.arcana.getArray());
      this.m_38884_(this.rectification.getArray());
      this.clues.m_6422_(1);
      this.m_38895_(this.clues);
   }

   public boolean m_6366_(Player player, int id) {
      int level = this.f_39446_[id];
      ItemStack toEnchant = this.f_39449_.m_8020_(0);
      ItemStack lapis = this.m_38853_(1).m_7993_();
      int cost = id + 1;
      if ((lapis.m_41619_() || lapis.m_41613_() < cost) && !player.m_150110_().f_35937_) {
         return false;
      } else if (this.f_39446_[id] > 0
         && !toEnchant.m_41619_()
         && (player.f_36078_ >= cost && player.f_36078_ >= this.f_39446_[id] || player.m_150110_().f_35937_)) {
         this.f_39450_.m_39292_((world, pos) -> {
            float eterna = this.eterna.get();
            float quanta = this.quanta.get();
            float arcana = this.arcana.get();
            float rectification = this.rectification.get();
            List<EnchantmentInstance> list = this.m_39471_(toEnchant, id, this.f_39446_[id]);
            if (!list.isEmpty() && EnchantmentUtils.chargeExperience(player, ApothMiscUtil.getExpCostForSlot(level, id))) {
               player.m_7408_(toEnchant, 0);
               if (list.get(0).f_44947_ == Apoth.Enchantments.INFUSION.get()) {
                  EnchantingRecipe match = EnchantingRecipe.findMatch(world, toEnchant, eterna, quanta, arcana);
                  if (match == null) {
                     return;
                  }

                  this.f_39449_.m_6836_(0, match.assemble(toEnchant, eterna, quanta, arcana));
               } else {
                  this.f_39449_.m_6836_(0, ((IEnchantableItem)toEnchant.m_41720_()).onEnchantment(toEnchant, list));
               }

               if (!player.m_150110_().f_35937_) {
                  lapis.m_41774_(cost);
                  if (lapis.m_41619_()) {
                     this.f_39449_.m_6836_(1, ItemStack.f_41583_);
                  }
               }

               player.m_36220_(Stats.f_12964_);
               if (player instanceof ServerPlayer) {
                  ((EnchantedTrigger)CriteriaTriggers.f_10575_).trigger((ServerPlayer)player, toEnchant, level, eterna, quanta, arcana, rectification);
               }

               this.f_39449_.m_6596_();
               this.f_39452_.m_6422_(player.m_36322_());
               this.m_6199_(this.f_39449_);
               world.m_5594_((Player)null, pos, SoundEvents.f_11887_, SoundSource.BLOCKS, 1.0F, world.f_46441_.m_188501_() * 0.1F + 0.9F);
            }
         });
         return true;
      } else {
         return false;
      }
   }

   public void m_6199_(Container inventoryIn) {
      this.f_39450_.m_6721_((world, pos) -> {
         if (inventoryIn == this.f_39449_) {
            ItemStack toEnchant = inventoryIn.m_8020_(0);
            this.gatherStats();
            EnchantingRecipe match = EnchantingRecipe.findItemMatch(world, toEnchant);
            if (toEnchant.m_41613_() == 1 && (match != null || toEnchant.m_41720_().m_8120_(toEnchant) && isEnchantableEnough(toEnchant))) {
               float eterna = this.eterna.get();
               if ((double)eterna < 1.5) {
                  eterna = 1.5F;
               }

               this.f_39451_.m_188584_((long)this.f_39452_.m_6501_());

               for (int slot = 0; slot < 3; slot++) {
                  this.f_39446_[slot] = RealEnchantmentHelper.getEnchantmentCost(this.f_39451_, slot, eterna, toEnchant);
                  this.f_39447_[slot] = -1;
                  this.f_39448_[slot] = -1;
                  if (this.f_39446_[slot] < slot + 1) {
                     this.f_39446_[slot]++;
                  }

                  this.f_39446_[slot] = ForgeEventFactory.onEnchantmentLevelSet(world, pos, slot, Math.round(eterna), toEnchant, this.f_39446_[slot]);
               }

               for (int slot = 0; slot < 3; slot++) {
                  if (this.f_39446_[slot] > 0) {
                     List<EnchantmentInstance> list = this.m_39471_(toEnchant, slot, this.f_39446_[slot]);
                     if (list != null && !list.isEmpty()) {
                        EnchantmentInstance enchantmentdata = list.remove(this.f_39451_.m_188503_(list.size()));
                        this.f_39447_[slot] = Registry.f_122825_.m_7447_(enchantmentdata.f_44947_);
                        this.f_39448_[slot] = enchantmentdata.f_44948_;
                        int clues = this.clues.m_6501_();
                        List<EnchantmentInstance> clueList = new ArrayList<>();
                        if (clues-- > 0) {
                           clueList.add(enchantmentdata);
                        }

                        while (clues-- > 0 && !list.isEmpty()) {
                           clueList.add(list.remove(this.f_39451_.m_188503_(list.size())));
                        }

                        PacketDistro.sendTo(Apotheosis.CHANNEL, new ClueMessage(slot, clueList, list.isEmpty()), this.player);
                     }
                  }
               }

               this.m_38946_();
            } else {
               for (int i = 0; i < 3; i++) {
                  this.f_39446_[i] = 0;
                  this.f_39447_[i] = -1;
                  this.f_39448_[i] = -1;
               }

               this.eterna.set(0.0F);
               this.quanta.set(0.0F);
               this.arcana.set(0.0F);
            }
         }

         return this;
      });
   }

   private List<EnchantmentInstance> m_39471_(ItemStack stack, int enchantSlot, int level) {
      this.f_39451_.m_188584_((long)(this.f_39452_.m_6501_() + enchantSlot));
      List<EnchantmentInstance> list = RealEnchantmentHelper.selectEnchantment(
         this.f_39451_, stack, level, this.quanta.get(), this.arcana.get(), this.rectification.get(), false
      );
      EnchantingRecipe match = (EnchantingRecipe)((Optional)this.f_39450_
            .m_6721_((world, pos) -> Optional.ofNullable(EnchantingRecipe.findMatch(world, stack, this.eterna.get(), this.quanta.get(), this.arcana.get())))
            .get())
         .orElse(null);
      if (enchantSlot == 2 && match != null) {
         list.clear();
         list.add(new EnchantmentInstance((Enchantment)Apoth.Enchantments.INFUSION.get(), 1));
      }

      return list;
   }

   public void gatherStats() {
      this.f_39450_.m_6721_((world, pos) -> {
         ApothEnchantmentMenu.TableStats stats = gatherStats(world, pos);
         this.eterna.set(stats.eterna());
         this.quanta.set(stats.quanta());
         this.arcana.set(stats.arcana() + (float)this.m_38853_(0).m_7993_().getEnchantmentValue() / 2.0F);
         this.rectification.set(stats.rectification());
         this.clues.m_6422_(stats.clues());
         return this;
      }).orElse(this);
   }

   public static ApothEnchantmentMenu.TableStats gatherStats(Level level, BlockPos pos) {
      Float2FloatMap eternaMap = new Float2FloatOpenHashMap();
      float[] stats = new float[]{0.0F, 15.0F, 0.0F, 0.0F, 1.0F};

      for (BlockPos offset : EnchantmentTableBlock.f_207902_) {
         if (canReadStatsFrom(level, pos, offset)) {
            gatherStats(eternaMap, stats, level, pos.m_121955_(offset));
         }
      }

      List<Entry> entries = new ArrayList<>(eternaMap.float2FloatEntrySet());
      Collections.sort(entries, Comparator.comparing(Entry::getFloatKey));

      for (Entry e : entries) {
         if (e.getFloatKey() > 0.0F) {
            stats[0] = Math.min(e.getFloatKey(), stats[0] + e.getFloatValue());
         } else {
            stats[0] += e.getFloatValue();
         }
      }

      return new ApothEnchantmentMenu.TableStats(stats);
   }

   public static boolean canReadStatsFrom(Level level, BlockPos tablePos, BlockPos offset) {
      return level.m_8055_(tablePos.m_7918_(offset.m_123341_() / 2, offset.m_123342_(), offset.m_123343_() / 2)).m_60767_().m_76336_();
   }

   public static void gatherStats(Float2FloatMap eternaMap, float[] stats, Level world, BlockPos pos) {
      BlockState state = world.m_8055_(pos);
      if (!state.m_60795_()) {
         float max = EnchantingStatManager.getMaxEterna(state, world, pos);
         float eterna = EnchantingStatManager.getEterna(state, world, pos);
         eternaMap.put(max, eternaMap.getOrDefault(max, 0.0F) + eterna);
         stats[1] += EnchantingStatManager.getQuanta(state, world, pos);
         stats[2] += EnchantingStatManager.getArcana(state, world, pos);
         stats[3] += EnchantingStatManager.getQuantaRectification(state, world, pos);
         stats[4] += (float)EnchantingStatManager.getBonusClues(state, world, pos);
      }
   }

   public MenuType<?> m_6772_() {
      return (MenuType<?>)Apoth.Menus.ENCHANTING_TABLE.get();
   }

   public static boolean isEnchantableEnough(ItemStack stack) {
      return !stack.m_41793_() ? true : EnchantmentHelper.m_44831_(stack).keySet().stream().allMatch(Enchantment::m_6589_);
   }

   public static enum Arcana {
      EMPTY(0.0F, 10, 5, 2, 1),
      LITTLE(10.0F, 8, 5, 3, 1),
      FEW(20.0F, 7, 5, 4, 2),
      SOME(30.0F, 5, 5, 4, 2),
      LESS(40.0F, 5, 5, 4, 3),
      MEDIUM(50.0F, 5, 5, 5, 5),
      MORE(60.0F, 3, 4, 5, 5),
      VALUE(70.0F, 2, 4, 5, 5),
      EXTRA(80.0F, 2, 4, 5, 7),
      ALMOST(90.0F, 1, 3, 5, 8),
      MAX(99.0F, 1, 2, 5, 10);

      final float threshold;
      final int[] rarities;
      static ApothEnchantmentMenu.Arcana[] VALUES = values();

      private Arcana(float threshold, int... rarities) {
         this.threshold = threshold;
         this.rarities = rarities;
      }

      public int[] getRarities() {
         return this.rarities;
      }

      public static ApothEnchantmentMenu.Arcana getForThreshold(float threshold) {
         for (int i = VALUES.length - 1; i >= 0; i--) {
            if (threshold >= VALUES[i].threshold) {
               return VALUES[i];
            }
         }

         return EMPTY;
      }
   }

   public static record TableStats(float eterna, float quanta, float arcana, float rectification, int clues) {
      public TableStats(float[] data) {
         this(data[0], data[1], data[2], data[3], (int)data[4]);
      }
   }
}
