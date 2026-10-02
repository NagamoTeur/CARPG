package shadows.apotheosis.ench;

import java.util.Map.Entry;
import java.util.stream.Collectors;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LootingLevelEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import shadows.apotheosis.Apoth;
import shadows.apotheosis.ench.anvil.AnvilTile;
import shadows.apotheosis.ench.enchantments.NaturesBlessingEnchant;
import shadows.apotheosis.ench.enchantments.ReflectiveEnchant;
import shadows.apotheosis.ench.enchantments.SpearfishingEnchant;
import shadows.apotheosis.ench.enchantments.StableFootingEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.BerserkersFuryEnchant;
import shadows.apotheosis.ench.enchantments.corrupted.LifeMendingEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ChainsawEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.EarthsBoonEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.KnowledgeEnchant;
import shadows.apotheosis.ench.enchantments.masterwork.ScavengerEnchant;
import shadows.apotheosis.ench.enchantments.twisted.MinersFervorEnchant;
import shadows.apotheosis.ench.objects.ExtractionTomeItem;
import shadows.apotheosis.ench.objects.ImprovedScrappingTomeItem;
import shadows.apotheosis.ench.objects.ScrappingTomeItem;

public class EnchModuleEvents {
   @SubscribeEvent
   public void anvilEvent(AnvilUpdateEvent e) {
      if (e.getLeft().m_41793_()) {
         if (e.getRight().m_41720_() == Items.f_41863_) {
            ItemStack stack = e.getLeft().m_41777_();
            EnchantmentHelper.m_44865_(
               EnchantmentHelper.m_44831_(stack)
                  .entrySet()
                  .stream()
                  .filter(ent -> ((Enchantment)ent.getKey()).m_6589_())
                  .collect(Collectors.toMap(Entry::getKey, Entry::getValue)),
               stack
            );
            e.setCost(1);
            e.setMaterialCost(1);
            e.setOutput(stack);
         } else if (e.getRight().m_41720_() == Apoth.Items.PRISMATIC_WEB.get()) {
            ItemStack stack = e.getLeft().m_41777_();
            EnchantmentHelper.m_44865_(
               EnchantmentHelper.m_44831_(stack)
                  .entrySet()
                  .stream()
                  .filter(ent -> !((Enchantment)ent.getKey()).m_6589_())
                  .collect(Collectors.toMap(Entry::getKey, Entry::getValue)),
               stack
            );
            e.setCost(30);
            e.setMaterialCost(1);
            e.setOutput(stack);
            return;
         }
      }

      if ((e.getLeft().m_41720_() == Items.f_42147_ || e.getLeft().m_41720_() == Items.f_42148_)
         && e.getRight().m_204117_(net.minecraftforge.common.Tags.Items.STORAGE_BLOCKS_IRON)) {
         if (e.getLeft().m_41613_() == 1) {
            int dmg = e.getLeft().m_41720_() == Items.f_42148_ ? 2 : 1;
            ItemStack out = new ItemStack(dmg == 1 ? Items.f_42146_ : Items.f_42147_);
            EnchantmentHelper.m_44865_(EnchantmentHelper.m_44831_(e.getLeft()), out);
            out.m_41764_(1);
            e.setOutput(out);
            e.setCost(
               5
                  + e.getLeft()
                     .getAllEnchantments()
                     .entrySet()
                     .stream()
                     .mapToInt(ent -> (Integer)ent.getValue() * (((Enchantment)ent.getKey()).m_44699_().ordinal() + 1))
                     .sum()
            );
            e.setMaterialCost(1);
         }
      } else if (!ScrappingTomeItem.updateAnvil(e)) {
         if (!ImprovedScrappingTomeItem.updateAnvil(e)) {
            if (!ExtractionTomeItem.updateAnvil(e)) {
               ;
            }
         }
      }
   }

   @SubscribeEvent
   public void repairEvent(AnvilRepairEvent e) {
      if (!ExtractionTomeItem.updateRepair(e)) {
         ;
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void drops(LivingDropsEvent e) throws Throwable {
      if (e.getSource().m_7639_() instanceof Player p) {
         ((ScavengerEnchant)Apoth.Enchantments.SCAVENGER.get()).drops(p, e);
         ((SpearfishingEnchant)Apoth.Enchantments.SPEARFISHING.get()).addFishes(e);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void dropsLowest(LivingDropsEvent e) {
      if (e.getSource().m_7639_() instanceof Player p) {
         ((KnowledgeEnchant)Apoth.Enchantments.KNOWLEDGE.get()).drops(p, e);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void healing(LivingHealEvent e) {
      if (e.getEntity().m_6095_() != EntityType.f_20529_) {
         ((LifeMendingEnchant)Apoth.Enchantments.LIFE_MENDING.get()).lifeMend(e);
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void block(ShieldBlockEvent e) {
      ((ReflectiveEnchant)Apoth.Enchantments.REFLECTIVE.get()).reflect(e);
   }

   @SubscribeEvent
   public void looting(LootingLevelEvent e) {
      DamageSource src = e.getDamageSource();
      if (src != null && src.m_7640_() instanceof ThrownTrident trident) {
         ItemStack triStack = ((EnchModuleEvents.TridentGetter)trident).getTridentItem();
         e.setLootingLevel(triStack.getEnchantmentLevel(Enchantments.f_44982_));
      }
   }

   @SubscribeEvent
   public void breakSpeed(BreakSpeed e) {
      ((StableFootingEnchant)Apoth.Enchantments.STABLE_FOOTING.get()).breakSpeed(e);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void breakSpeedLow(BreakSpeed e) {
      ((MinersFervorEnchant)Apoth.Enchantments.MINERS_FERVOR.get()).breakSpeed(e);
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void breakSpeed(BreakEvent e) {
      ((EarthsBoonEnchant)Apoth.Enchantments.EARTHS_BOON.get()).provideBenefits(e);
      ((ChainsawEnchant)Apoth.Enchantments.CHAINSAW.get()).chainsaw(e);
   }

   @SubscribeEvent
   public void rightClick(RightClickBlock e) {
      ((NaturesBlessingEnchant)Apoth.Enchantments.NATURES_BLESSING.get()).rightClick(e);
   }

   @SubscribeEvent(
      priority = EventPriority.LOWEST
   )
   public void applyUnbreaking(AnvilRepairEvent e) {
      if (e.getEntity().f_36096_ instanceof AnvilMenu anvMenu) {
         anvMenu.f_39770_.m_39292_((level, pos) -> {
            if (level.m_7702_(pos) instanceof AnvilTile anvil) {
               e.setBreakChance(e.getBreakChance() / (float)(anvil.getEnchantments().getInt(Enchantments.f_44986_) + 1));
            }
         });
      }
   }

   @SubscribeEvent(
      priority = EventPriority.LOW
   )
   public void livingHurt(LivingHurtEvent e) {
      ((BerserkersFuryEnchant)Apoth.Enchantments.BERSERKERS_FURY.get()).livingHurt(e);
   }

   public interface TridentGetter {
      ItemStack getTridentItem();
   }
}
