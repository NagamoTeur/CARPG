package com.majruszsaccessories.accessories.components;

import com.majruszlibrary.annotation.AutoInstance;
import com.majruszlibrary.data.Reader;
import com.majruszlibrary.data.Serializables;
import com.majruszlibrary.emitter.ParticleEmitter;
import com.majruszlibrary.events.OnLootGenerated;
import com.majruszlibrary.events.OnPlayerTicked;
import com.majruszlibrary.events.base.Condition;
import com.majruszlibrary.events.base.Event;
import com.majruszlibrary.level.LevelHelper;
import com.majruszlibrary.level.LevelHelper.SpawnPoint;
import com.majruszlibrary.math.Random;
import com.majruszlibrary.math.Range;
import com.majruszlibrary.platform.Side;
import com.majruszlibrary.text.TextHelper;
import com.majruszsaccessories.MajruszsAccessories;
import com.majruszsaccessories.common.AccessoryHolder;
import com.majruszsaccessories.common.AccessoryHolders;
import com.majruszsaccessories.common.BonusComponent;
import com.majruszsaccessories.common.BonusHandler;
import com.majruszsaccessories.config.RangedFloat;
import com.majruszsaccessories.items.AccessoryItem;
import com.majruszsaccessories.tooltip.ITooltipProvider;
import com.majruszsaccessories.tooltip.TooltipHelper;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoreChestLoot extends BonusComponent<AccessoryItem> {
   static final int BLOCKS_DISTANCE = 6000;
   RangedFloat sizeMultiplier = new RangedFloat().id("multiplier").maxRange(Range.of(0.0F, 10.0F));

   public static BonusComponent.ISupplier<AccessoryItem> create(float sizeMultiplier) {
      return handler -> new MoreChestLoot(handler, sizeMultiplier);
   }

   protected MoreChestLoot(BonusHandler<AccessoryItem> handler, float sizeMultiplier) {
      super(handler);
      this.sizeMultiplier.set(sizeMultiplier, Range.of(0.0F, 10.0F));
      MoreChestLoot.OnChestOpened.listen(this::addExtraLoot);
      this.addTooltip(
         "majruszsaccessories.bonuses.more_chest_loot", new ITooltipProvider[]{this.getPerPercentInfo(), this.getPercentInfo(), this.getCurrentInfo()}
      );
      handler.getConfig().define("chest_size_bonus", this.sizeMultiplier::define);
   }

   private void addExtraLoot(OnLootGenerated data) {
      ServerPlayer player = MoreChestLoot.OnChestOpened.findPlayer(data).orElse(null);
      if (player != null) {
         AccessoryHolder holder = AccessoryHolders.get(player).get((Supplier<AccessoryItem>)(() -> this.getItem()));
         if (holder.isValid() && !holder.isBonusDisabled()) {
            float sizeMultiplier = 1.0F + holder.apply(this.sizeMultiplier) * getDistanceBonus(player);
            boolean hasIncreasedLoot = false;
            ObjectListIterator var6 = data.generatedLoot.iterator();

            while (var6.hasNext()) {
               ItemStack itemStack = (ItemStack)var6.next();
               int count = Math.min(Random.round((double)(sizeMultiplier * (float)itemStack.m_41613_())), itemStack.m_41741_());
               hasIncreasedLoot = hasIncreasedLoot || count > itemStack.m_41613_();
               itemStack.m_41764_(count);
            }

            if (hasIncreasedLoot) {
               this.spawnEffects(data, holder);
            }
         }
      }
   }

   private void spawnEffects(OnLootGenerated data, AccessoryHolder holder) {
      holder.getParticleEmitter().count(24).offset(ParticleEmitter.offset(0.4F)).position(data.origin).emit(data.getServerLevel());
   }

   private ITooltipProvider getPerPercentInfo() {
      return TooltipHelper.asPercent(this.sizeMultiplier).valueMultiplier(1.6666666E-4F).scale(4);
   }

   private ITooltipProvider getPercentInfo() {
      return TooltipHelper.asPercent(this.sizeMultiplier).scale(4);
   }

   private ITooltipProvider getCurrentInfo() {
      return holder -> TextHelper.literal(TextHelper.percent(holder.apply(this.sizeMultiplier) * MoreChestLoot.BonusInfo.CURRENT_BONUS, 4));
   }

   private static float getDistanceBonus(ServerPlayer player) {
      Optional<SpawnPoint> spawnPoint = LevelHelper.getSpawnPoint(player);
      return spawnPoint.<Float>map(point -> (float)Mth.m_14008_((double)Math.round(point.position.m_82554_(player.m_20182_())) / 6000.0, 0.0, 1.0))
         .orElse(0.0F);
   }

   public static class BonusInfo {
      static float CURRENT_BONUS = 0.0F;
      float bonus;

      public BonusInfo(float bonus) {
         this.bonus = bonus;
      }

      public BonusInfo() {
         this(0.0F);
      }

      @OnlyIn(Dist.CLIENT)
      private static void onClient(MoreChestLoot.BonusInfo data) {
         CURRENT_BONUS = data.bonus;
      }

      static {
         Serializables.get(MoreChestLoot.BonusInfo.class).define("bonus", Reader.number(), s -> s.bonus, (s, v) -> s.bonus = v);
         Side.runOnClient(() -> () -> MajruszsAccessories.MORE_CHEST_LOOT.addClientCallback(MoreChestLoot.BonusInfo::onClient));
      }
   }

   @AutoInstance
   public static class Notifier {
      public Notifier() {
         OnPlayerTicked.listen(this::sendUpdatedBonus).addCondition(Condition.isLogicalServer()).addCondition(Condition.cooldown(1.0F));
      }

      private void sendUpdatedBonus(OnPlayerTicked data) {
         ServerPlayer player = (ServerPlayer)data.player;
         MajruszsAccessories.MORE_CHEST_LOOT.sendToClient(player, new MoreChestLoot.BonusInfo(MoreChestLoot.getDistanceBonus(player)));
      }
   }

   public static class OnChestOpened {
      public static Event<OnLootGenerated> listen(Consumer<OnLootGenerated> consumer) {
         return OnLootGenerated.listen(consumer).addCondition(Condition.isLogicalServer()).addCondition(data -> data.lootId.toString().contains("chests/"));
      }

      public static Optional<ServerPlayer> findPlayer(OnLootGenerated data) {
         if (data.entity instanceof ServerPlayer player) {
            return Optional.of(player);
         } else {
            return data.origin != null
                  && data.level.m_45924_(data.origin.f_82479_, data.origin.f_82480_, data.origin.f_82481_, 5.0, true) instanceof ServerPlayer player
               ? Optional.of(player)
               : Optional.empty();
         }
      }
   }
}
