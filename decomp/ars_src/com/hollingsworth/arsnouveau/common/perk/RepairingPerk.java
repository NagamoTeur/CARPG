package com.hollingsworth.arsnouveau.common.perk;

import com.hollingsworth.arsnouveau.api.perk.Perk;
import com.hollingsworth.arsnouveau.api.util.PerkUtil;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class RepairingPerk extends Perk {
   public static final RepairingPerk INSTANCE = new RepairingPerk(new ResourceLocation("ars_nouveau", "thread_repairing"));
   public static final UUID PERK_UUID = UUID.fromString("e2a7e5bc-ab34-4ea2-b3b6-ef23d352fa47");

   public RepairingPerk(ResourceLocation key) {
      super(key);
   }

   public static void attemptRepair(ItemStack stack, Player entity) {
      if (entity.f_19853_.m_46467_() % 200L == 0L && stack.m_41773_() > 0) {
         double repairLevel = (double)PerkUtil.countForPerk(INSTANCE, entity);
         CapabilityRegistry.getMana(entity).ifPresent(mana -> {
            if (mana.getCurrentMana() > 20.0) {
               mana.removeMana(20.0);
               stack.m_41721_(stack.m_41773_() - Math.min(stack.m_41773_(), (int)repairLevel + 1));
            }
         });
      }
   }

   @Override
   public String getLangName() {
      return "Repairing";
   }

   @Override
   public String getLangDescription() {
      return "Allows the wearer to repair ANY magical armor or enchanters item by consuming Mana over time. Additional levels increase the speed at which the items repair. This perk applies to all relevant items, not only the item with this perk.";
   }
}
