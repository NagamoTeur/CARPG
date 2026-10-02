package shadows.apotheosis.adventure.affix;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
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
import shadows.apotheosis.adventure.loot.LootRarity;

public record AffixInstance(Affix affix, ItemStack stack, LootRarity rarity, float level) {
   public void addModifiers(EquipmentSlot type, BiConsumer<Attribute, AttributeModifier> map) {
      this.affix.addModifiers(this.stack, this.rarity, this.level, type, map);
   }

   public void addInformation(Consumer<Component> list) {
      this.affix.addInformation(this.stack, this.rarity, this.level, list);
   }

   public Component getName(boolean prefix) {
      return this.affix.getName(this.stack, this.rarity, this.level, prefix);
   }

   public int getDamageProtection(DamageSource source) {
      return this.affix.getDamageProtection(this.stack, this.rarity, this.level, source);
   }

   public float getDamageBonus(MobType creatureType) {
      return this.affix.getDamageBonus(this.stack, this.rarity, this.level, creatureType);
   }

   public void doPostAttack(LivingEntity user, @Nullable Entity target) {
      this.affix.doPostAttack(this.stack, this.rarity, this.level, user, target);
   }

   public void doPostHurt(LivingEntity user, @Nullable Entity attacker) {
      this.affix.doPostHurt(this.stack, this.rarity, this.level, user, attacker);
   }

   public void onArrowFired(LivingEntity user, AbstractArrow arrow) {
      this.affix.onArrowFired(this.stack, this.rarity, this.level, user, arrow);
   }

   @Nullable
   public InteractionResult onItemUse(UseOnContext ctx) {
      return this.affix.onItemUse(this.stack, this.rarity, this.level, ctx);
   }

   public float onShieldBlock(LivingEntity entity, DamageSource source, float amount) {
      return this.affix.onShieldBlock(this.stack, this.rarity, this.level, entity, source, amount);
   }

   public void onBlockBreak(Player player, LevelAccessor world, BlockPos pos, BlockState state) {
      this.affix.onBlockBreak(this.stack, this.rarity, this.level, player, world, pos, state);
   }

   public float getDurabilityBonusPercentage(@Nullable ServerPlayer user) {
      return this.affix.getDurabilityBonusPercentage(this.stack, this.rarity, this.level, user);
   }

   public void onArrowImpact(AbstractArrow arrow, HitResult res, Type type) {
      this.affix.onArrowImpact(arrow, this.rarity, this.level, res, type);
   }

   public boolean enablesTelepathy() {
      return this.affix.enablesTelepathy();
   }

   public float onHurt(DamageSource src, LivingEntity ent, float amount) {
      return this.affix.onHurt(this.stack, this.rarity, this.level, src, ent, amount);
   }

   public void getEnchantmentLevels(Map<Enchantment, Integer> enchantments) {
      this.affix.getEnchantmentLevels(this.stack, this.rarity, this.level, enchantments);
   }

   public void modifyLoot(ObjectArrayList<ItemStack> loot, LootContext ctx) {
      this.affix.modifyLoot(this.stack, this.rarity, this.level, loot, ctx);
   }
}
