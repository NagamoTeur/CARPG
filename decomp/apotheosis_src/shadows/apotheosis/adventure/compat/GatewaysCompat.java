package shadows.apotheosis.adventure.compat;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.boss.BossItem;
import shadows.apotheosis.adventure.boss.BossItemManager;
import shadows.apotheosis.adventure.loot.AffixLootEntry;
import shadows.apotheosis.adventure.loot.AffixLootManager;
import shadows.apotheosis.adventure.loot.LootController;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.gateways.entity.GatewayEntity;
import shadows.gateways.gate.Reward;
import shadows.gateways.gate.WaveEntity;
import shadows.placebo.json.WeightedJsonReloadListener.IDimensional;

public class GatewaysCompat {
   public static void register() {
      WaveEntity.CODECS.put(Apotheosis.loc("boss"), GatewaysCompat.BossWaveEntity.CODEC);
      Reward.CODECS.put(Apotheosis.loc("affix"), GatewaysCompat.RarityAffixItemReward.CODEC);
   }

   public static class BossWaveEntity implements WaveEntity {
      public static Codec<GatewaysCompat.BossWaveEntity> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(ResourceLocation.f_135803_.optionalFieldOf("boss").forGetter(b -> b.bossId)).apply(inst, GatewaysCompat.BossWaveEntity::new)
      );
      private final Optional<ResourceLocation> bossId;
      private final Supplier<BossItem> boss;

      public BossWaveEntity(Optional<ResourceLocation> bossId) {
         this.bossId = bossId;
         this.boss = Suppliers.memoize(() -> bossId.<BossItem>map(BossItemManager.INSTANCE::getValue).orElse(null));
      }

      public LivingEntity createEntity(Level level) {
         BossItem realBoss = this.bossId.isEmpty() ? (BossItem)BossItemManager.INSTANCE.getRandomItem(level.f_46441_) : this.boss.get();
         return realBoss == null ? null : realBoss.createBoss((ServerLevelAccessor)level, BlockPos.f_121853_, level.f_46441_, 0.0F);
      }

      public Component getDescription() {
         if (this.bossId.isPresent()) {
            BossItem boss = this.boss.get();
            return boss != null
               ? Component.m_237110_("misc.apotheosis.boss", new Object[]{Component.m_237115_(this.boss.get().getEntity().m_20675_())})
               : Component.m_237113_("Unknown boss ID: " + this.bossId.get());
         } else {
            return Component.m_237110_("misc.apotheosis.boss", new Object[]{Component.m_237115_("misc.apotheosis.random")});
         }
      }

      public AABB getAABB(double x, double y, double z) {
         return (this.bossId.isEmpty() ? new AABB(0.0, 0.0, 0.0, 2.0, 2.0, 2.0) : this.boss.get().getSize()).m_82386_(x, y, z);
      }

      public boolean shouldFinalizeSpawn() {
         return false;
      }

      public Codec<? extends WaveEntity> getCodec() {
         return CODEC;
      }
   }

   public static record RarityAffixItemReward(LootRarity rarity) implements Reward {
      public static Codec<GatewaysCompat.RarityAffixItemReward> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(LootRarity.CODEC.fieldOf("rarity").forGetter(GatewaysCompat.RarityAffixItemReward::rarity))
               .apply(inst, GatewaysCompat.RarityAffixItemReward::new)
      );

      public void generateLoot(ServerLevel level, GatewayEntity gate, Player summoner, Consumer<ItemStack> list) {
         AffixLootEntry item = (AffixLootEntry)AffixLootManager.INSTANCE
            .getRandomItem(level.f_46441_, summoner.m_36336_(), new Predicate[]{IDimensional.matches(level), GameStagesCompat.IStaged.matches(summoner)});
         if (item == null) {
            item = (AffixLootEntry)AffixLootManager.INSTANCE.getRandomItem(level.f_46441_, summoner.m_36336_());
         }

         list.accept(LootController.createLootItem(item.getStack(), this.rarity, level.f_46441_));
      }

      public void appendHoverText(Consumer<Component> list) {
         list.accept(Component.m_237110_("reward.apotheosis.affix", new Object[]{this.rarity.toComponent()}));
      }

      public Codec<? extends Reward> getCodec() {
         return CODEC;
      }
   }
}
