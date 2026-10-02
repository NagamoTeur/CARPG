package shadows.apotheosis.adventure.affix.socket.gem;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;

public record GemInstance(Gem gem, LootCategory cat, ItemStack gemStack, LootRarity rarity) {
   public static GemInstance socketed(ItemStack socketed, ItemStack gemStack) {
      return socketed(LootCategory.forItem(socketed), gemStack);
   }

   public static GemInstance socketed(LootCategory category, ItemStack gemStack) {
      Gem gem = GemItem.getGem(gemStack);
      LootRarity rarity = AffixHelper.getRarity(gemStack.m_41783_());
      if (gem != null && rarity != null) {
         rarity = gem.clamp(rarity);
      }

      return new GemInstance(gem, category, gemStack, rarity);
   }

   public static GemInstance unsocketed(ItemStack gemStack) {
      Gem gem = GemItem.getGem(gemStack);
      LootRarity rarity = AffixHelper.getRarity(gemStack.m_41783_());
      if (gem != null && rarity != null) {
         rarity = gem.clamp(rarity);
      }

      return new GemInstance(gem, LootCategory.NONE, gemStack, rarity);
   }

   public boolean isValidUnsocketed() {
      return this.gem != null && this.rarity != null;
   }

   public boolean isValid() {
      return this.isValidUnsocketed() && this.gem.getBonus(this.cat, this.rarity).isPresent();
   }

   public boolean isMaxRarity() {
      return this.rarity() == this.gem.getMaxRarity();
   }

   public void addModifiers(EquipmentSlot slot, BiConsumer<Attribute, AttributeModifier> map) {
      for (EquipmentSlot itemSlot : this.cat.getSlots()) {
         if (itemSlot == slot) {
            this.ifPresent(b -> b.addModifiers(this.gemStack, this.rarity, map));
         }
      }
   }

   public Component getSocketBonusTooltip() {
      return this.<Component>map(b -> b.getSocketBonusTooltip(this.gemStack, this.rarity)).orElse(Component.m_237113_("Invalid Gem Category"));
   }

   public int getDamageProtection(DamageSource source) {
      return this.<Integer>map(b -> b.getDamageProtection(this.gemStack, this.rarity, source)).orElse(0);
   }

   public float getDamageBonus(MobType creatureType) {
      return this.<Float>map(b -> b.getDamageBonus(this.gemStack, this.rarity, creatureType)).orElse(0.0F);
   }

   public void doPostAttack(LivingEntity user, @Nullable Entity target) {
      this.ifPresent(b -> b.doPostAttack(this.gemStack, this.rarity, user, target));
   }

   public void doPostHurt(LivingEntity user, @Nullable Entity attacker) {
      this.ifPresent(b -> b.doPostHurt(this.gemStack, this.rarity, user, attacker));
   }

   public void onArrowFired(LivingEntity user, AbstractArrow arrow) {
      this.ifPresent(b -> b.onArrowFired(this.gemStack, this.rarity, user, arrow));
   }

   @Nullable
   public InteractionResult onItemUse(UseOnContext ctx) {
      return this.<InteractionResult>map(b -> b.onItemUse(this.gemStack, this.rarity, ctx)).orElse(null);
   }

   public void onArrowImpact(AbstractArrow arrow, ItemStack gem, LootRarity rarity, HitResult res, Type type) {
      this.ifPresent(b -> b.onArrowImpact(this.gemStack, this.rarity, arrow, res, type));
   }

   public float onShieldBlock(LivingEntity entity, DamageSource source, float amount) {
      return this.<Float>map(b -> b.onShieldBlock(this.gemStack, this.rarity, entity, source, amount)).orElse(amount);
   }

   public void onBlockBreak(Player player, LevelAccessor world, BlockPos pos, BlockState state) {
      this.ifPresent(b -> b.onBlockBreak(this.gemStack, this.rarity, player, world, pos, state));
   }

   public float getDurabilityBonusPercentage(ServerPlayer user) {
      return this.<Float>map(b -> b.getDurabilityBonusPercentage(this.gemStack, this.rarity, user)).orElse(0.0F);
   }

   public float onHurt(DamageSource src, LivingEntity ent, float amount) {
      return this.<Float>map(b -> b.onHurt(this.gemStack, this.rarity, src, ent, amount)).orElse(amount);
   }

   public void getEnchantmentLevels(Map<Enchantment, Integer> enchantments) {
      this.ifPresent(b -> b.getEnchantmentLevels(this.gemStack, this.rarity, enchantments));
   }

   public void modifyLoot(ObjectArrayList<ItemStack> loot, LootContext ctx) {
      this.ifPresent(b -> b.modifyLoot(this.gemStack, this.rarity, loot, ctx));
   }

   private <T> Optional<T> map(Function<GemBonus, T> function) {
      return this.gem.getBonus(this.cat, this.rarity).map(function);
   }

   private void ifPresent(Consumer<GemBonus> function) {
      this.gem.getBonus(this.cat, this.rarity).ifPresent(function);
   }
}
