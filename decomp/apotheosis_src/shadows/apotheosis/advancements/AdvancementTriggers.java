package shadows.apotheosis.advancements;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.EnchantedItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.resources.ResourceLocation;

public class AdvancementTriggers {
   public static final SplittingTrigger SPLIT_BOOK = new SplittingTrigger();
   public static final ModifierTrigger SPAWNER_MODIFIER = new ModifierTrigger();
   public static final GemCutTrigger GEM_CUT = new GemCutTrigger();

   public static void init() {
      CriteriaTriggers.f_10566_.remove(new ResourceLocation("inventory_changed"));
      CriteriaTriggers.f_10571_ = (InventoryChangeTrigger)CriteriaTriggers.m_10595_(new ExtendedInvTrigger());
      CriteriaTriggers.f_10566_.remove(new ResourceLocation("enchanted_item"));
      CriteriaTriggers.f_10575_ = (EnchantedItemTrigger)CriteriaTriggers.m_10595_(new EnchantedTrigger());
      CriteriaTriggers.m_10595_(SPAWNER_MODIFIER);
      CriteriaTriggers.m_10595_(SPLIT_BOOK);
      CriteriaTriggers.m_10595_(GEM_CUT);
   }
}
