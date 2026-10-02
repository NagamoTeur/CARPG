package daripher.skilltree.item.gem.loot;

import daripher.skilltree.SkillTreeMod;
import daripher.skilltree.config.Config;
import daripher.skilltree.skill.bonus.SkillBonusHandler;
import daripher.skilltree.skill.bonus.player.LootDuplicationBonus;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags.Blocks;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(
   modid = "skilltree"
)
public class GemLootHandler {
   @SubscribeEvent
   public static void dropGemFromOre(BreakEvent event) {
      Player player = event.getPlayer();
      if (canDropGem(event, player)) {
         ServerLevel level = (ServerLevel)player.f_19853_;
         LootTable lootTable = getGemsLootTable(level);
         LootContext lootContext = createGemsLootContext(event, level, player);
         float multiplier = getGemLootMultiplier(player);
         if (player.m_217043_().m_188501_() < multiplier % 1.0F) {
            multiplier++;
         }

         List<ItemStack> foundGems = lootTable.m_230922_(lootContext);

         for (int i = 0; i < (int)multiplier; i++) {
            foundGems.stream().<ItemStack>map(ItemStack::m_41777_).forEach(s -> Block.m_49840_(level, event.getPos(), s));
         }
      }
   }

   private static float getGemLootMultiplier(Player player) {
      return 1.0F + SkillBonusHandler.getLootMultiplier(player, LootDuplicationBonus.LootType.GEMS);
   }

   public static int getGemLootWeight(ResourceLocation gemId) {
      if (gemId.m_135815_().contains("vacucite")) {
         return 200;
      } else {
         int tier = Integer.parseInt(gemId.m_135815_().substring(gemId.m_135815_().length() - 1));

         return switch (tier) {
            case 0 -> 1000;
            case 1 -> 350;
            case 2 -> 100;
            case 3 -> 10;
            default -> 0;
         };
      }
   }

   public static int getGemLootQuality(ResourceLocation gemId) {
      if (gemId.m_135815_().contains("vacucite")) {
         return 1;
      } else {
         int tier = Integer.parseInt(gemId.m_135815_().substring(gemId.m_135815_().length() - 1));

         return switch (tier) {
            case 0 -> -50;
            case 1 -> -10;
            case 2 -> -1;
            default -> 0;
            case 4 -> 5;
            case 5 -> 15;
         };
      }
   }

   private static boolean canDropGem(BreakEvent event, Player player) {
      if (player.m_7500_()) {
         return false;
      } else if (player.m_9236_().f_46443_) {
         return false;
      } else if (Config.gem_drop_chance == 0.0) {
         return false;
      } else if (!player.m_9236_().m_8055_(event.getPos()).m_204336_(Blocks.ORES)) {
         return false;
      } else if ((double)player.m_217043_().m_188501_() >= Config.gem_drop_chance) {
         return false;
      } else {
         return !ForgeHooks.isCorrectToolForDrops(event.getState(), player) ? false : player.m_21205_().getEnchantmentLevel(Enchantments.f_44985_) == 0;
      }
   }

   @NotNull
   private static LootTable getGemsLootTable(ServerLevel serverLevel) {
      String name = SkillTreeMod.apotheosisEnabled() ? "apotheosis_gems" : "gems";
      ResourceLocation id = new ResourceLocation("skilltree", name);
      return serverLevel.m_7654_().m_129898_().m_79217_(id);
   }

   @NotNull
   private static LootContext createGemsLootContext(BreakEvent event, ServerLevel serverLevel, Player player) {
      return new Builder(serverLevel)
         .m_78972_(LootContextParams.f_81461_, event.getState())
         .m_78972_(LootContextParams.f_81455_, player)
         .m_78972_(
            LootContextParams.f_81460_, new Vec3((double)event.getPos().m_123341_(), (double)event.getPos().m_123342_(), (double)event.getPos().m_123343_())
         )
         .m_78972_(LootContextParams.f_81463_, player.m_21205_())
         .m_78963_(player.m_36336_())
         .m_78975_(LootContextParamSets.f_81421_);
   }
}
