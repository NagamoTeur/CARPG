package io.redspace.ironsspellbooks.gui.arcane_anvil;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.UpgradeData;
import io.redspace.ironsspellbooks.config.ServerConfigs;
import io.redspace.ironsspellbooks.item.InkItem;
import io.redspace.ironsspellbooks.item.Scroll;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.SpellSlotUpgradeItem;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import io.redspace.ironsspellbooks.item.curios.AffinityRing;
import io.redspace.ironsspellbooks.registries.BlockRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.registries.MenuRegistry;
import io.redspace.ironsspellbooks.util.UpgradeUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ItemCombinerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ArcaneAnvilMenu extends ItemCombinerMenu {
   private final List<ItemStack> additionalDrops = new ArrayList<>();

   public ArcaneAnvilMenu(int pContainerId, Inventory inventory, ContainerLevelAccess containerLevelAccess) {
      super((MenuType)MenuRegistry.ARCANE_ANVIL_MENU.get(), pContainerId, inventory, containerLevelAccess);
   }

   public ArcaneAnvilMenu(int pContainerId, Inventory inventory, FriendlyByteBuf extraData) {
      this(pContainerId, inventory, ContainerLevelAccess.f_39287_);
   }

   protected boolean m_6560_(Player pPlayer, boolean pHasStack) {
      return true;
   }

   protected void m_142365_(Player p_150601_, ItemStack p_150602_) {
      this.f_39769_.m_8020_(0).m_41774_(1);
      this.f_39769_.m_8020_(1).m_41774_(1);
      this.f_39770_.m_39292_((level, pos) -> {
         level.m_5594_(null, pos, SoundEvents.f_11671_, SoundSource.BLOCKS, 0.8F, 1.1F);
         level.m_5594_(null, pos, SoundEvents.f_144242_, SoundSource.BLOCKS, 1.0F, 1.0F);
         this.additionalDrops.forEach(stack -> {
            if (!stack.m_41619_()) {
               level.m_7967_(new ItemEntity(level, (double)pos.m_123341_() + 0.5, (double)(pos.m_123342_() + 1), (double)pos.m_123343_() + 0.5, stack));
            }
         });
         this.additionalDrops.clear();
      });
      this.m_6640_();
   }

   protected boolean m_8039_(BlockState pState) {
      return pState.m_60713_((Block)BlockRegistry.ARCANE_ANVIL_BLOCK.get());
   }

   public void m_6640_() {
      ItemStack result = ItemStack.f_41583_;
      this.additionalDrops.clear();
      ItemStack baseItemStack = this.f_39769_.m_8020_(0);
      ItemStack modifierItemStack = this.f_39769_.m_8020_(1);
      if (!baseItemStack.m_41619_() && !modifierItemStack.m_41619_()) {
         if (baseItemStack.m_41720_() instanceof Scroll && modifierItemStack.m_41720_() instanceof InkItem inkItem) {
            SpellData spell1 = ISpellContainer.get(baseItemStack).getSpellAtIndex(0);
            if (spell1.getLevel() < spell1.getSpell().getMaxLevel()) {
               SpellRarity baseRarity = spell1.getRarity();
               SpellRarity nextRarity = spell1.getSpell().getRarity(spell1.getLevel() + 1);
               if (nextRarity.equals(inkItem.getRarity())) {
                  result = baseItemStack.m_41777_();
                  result.m_41764_(1);
                  ISpellContainer.createScrollContainer(spell1.getSpell(), spell1.getLevel() + 1, result);
               }
            }
         } else if (baseItemStack.m_41720_() instanceof UniqueItem && modifierItemStack.m_41720_() instanceof Scroll scroll) {
            SpellData scrollSlot = ISpellContainer.get(modifierItemStack).getSpellAtIndex(0);
            if (ISpellContainer.isSpellContainer(baseItemStack)) {
               ISpellContainer spellContainer = ISpellContainer.get(baseItemStack);
               int matchIndex = spellContainer.getIndexForSpell(scrollSlot.getSpell());
               if (matchIndex >= 0) {
                  SpellData spellData = spellContainer.getSpellAtIndex(matchIndex);
                  if (spellData.getLevel() < scrollSlot.getLevel() && spellData.isLocked()) {
                     result = baseItemStack.m_41777_();
                     spellContainer.removeSpellAtIndex(matchIndex, null);
                     spellContainer.addSpellAtIndex(scrollSlot.getSpell(), scrollSlot.getLevel(), matchIndex, true, null);
                     spellContainer.save(result);
                     result.m_41784_().m_128379_("Improved", true);
                  }
               }
            }
         } else if (Utils.canImbue(baseItemStack) && modifierItemStack.m_41720_() instanceof Scroll scrollx) {
            result = baseItemStack.m_41777_();
            ISpellContainer spellContainer = ISpellContainer.getOrCreate(baseItemStack);
            SpellData scrollSlot = ISpellContainer.get(modifierItemStack).getSpellAtIndex(0);
            int nextSlotIndex = spellContainer.getNextAvailableIndex();
            if (nextSlotIndex == -1) {
               nextSlotIndex = 0;
            }

            spellContainer.removeSpellAtIndex(0, null);
            spellContainer.addSpellAtIndex(scrollSlot.getSpell(), scrollSlot.getLevel(), nextSlotIndex, false, null);
            spellContainer.save(result);
         } else if (Utils.canBeUpgraded(baseItemStack)
            && UpgradeData.getUpgradeData(baseItemStack).getCount() < (Integer)ServerConfigs.MAX_UPGRADES.get()
            && modifierItemStack.m_41720_() instanceof UpgradeOrbItem upgradeOrb) {
            result = baseItemStack.m_41777_();
            String slot = UpgradeUtils.getRelevantEquipmentSlot(result);
            UpgradeData.getUpgradeData(result).addUpgrade(result, upgradeOrb.getUpgradeType(), slot);
         } else if (modifierItemStack.m_150930_((Item)ItemRegistry.SHRIVING_STONE.get())) {
            result = Utils.handleShriving(baseItemStack);
            UpgradeData upgradeData = UpgradeData.getUpgradeData(baseItemStack);
            upgradeData.getUpgrades().forEach((upgrade, count) -> this.additionalDrops.add(this.upgradeOrbFromType(upgrade, count)));
         } else if (modifierItemStack.m_41720_() instanceof SpellSlotUpgradeItem spellSlotUpgradeItem) {
            if (baseItemStack.m_41720_() instanceof SpellBook) {
               ISpellContainer spellBookContainer = ISpellContainer.get(baseItemStack);
               int max = spellSlotUpgradeItem.maxSlots();
               if (spellBookContainer.getMaxSpellCount() < max) {
                  result = baseItemStack.m_41777_();
                  ISpellContainer upgradedContainer = ISpellContainer.get(result);
                  upgradedContainer.setMaxSpellCount(upgradedContainer.getMaxSpellCount() + 1);
                  upgradedContainer.save(result);
               }
            }
         } else if (baseItemStack.m_41720_() instanceof AffinityRing affinityRing && modifierItemStack.m_41720_() instanceof Scroll scrollx) {
            result = baseItemStack.m_41777_();
            SpellData scrollSlot = ISpellContainer.get(modifierItemStack).getSpellAtIndex(0);
            AffinityData.setAffinityData(result, scrollSlot.getSpell());
         }
      }

      this.f_39768_.m_6836_(0, result);
   }

   private ItemStack upgradeOrbFromType(UpgradeType type, int count) {
      return type.getContainerItem().map(item -> {
         ItemStack stack = new ItemStack((ItemLike)item.get());
         stack.m_41764_(count);
         return stack;
      }).orElse(ItemStack.f_41583_);
   }

   public boolean m_5882_(ItemStack pStack, Slot pSlot) {
      return pSlot.f_40218_ != this.f_39768_ && super.m_5882_(pStack, pSlot);
   }
}
