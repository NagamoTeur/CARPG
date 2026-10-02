package shadows.apotheosis.adventure.affix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceLocation.Serializer;
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
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.JsonUtil;
import shadows.placebo.json.NBTAdapter;
import shadows.placebo.json.TypeKeyed.TypeKeyedBase;

public abstract class Affix extends TypeKeyedBase<Affix> {
   protected static final Gson GSON = new GsonBuilder()
      .registerTypeAdapter(LootRarity.class, JsonUtil.makeSerializer((json, type, ctx) -> LootRarity.byId(json.getAsString()), null))
      .registerTypeAdapter(ItemStack.class, ItemAdapter.INSTANCE)
      .registerTypeAdapter(CompoundTag.class, NBTAdapter.INSTANCE)
      .registerTypeAdapter(ResourceLocation.class, new Serializer())
      .create();
   protected final AffixType type;

   public Affix(AffixType type) {
      this.type = type;
   }

   public void addModifiers(ItemStack stack, LootRarity rarity, float level, EquipmentSlot type, BiConsumer<Attribute, AttributeModifier> map) {
   }

   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{fmt(level)}));
   }

   public Component getName(ItemStack stack, LootRarity rarity, float level, boolean prefix) {
      return prefix ? Component.m_237115_("affix." + this.getId()) : Component.m_237115_("affix." + this.getId() + ".suffix");
   }

   public int getDamageProtection(ItemStack stack, LootRarity rarity, float level, DamageSource source) {
      return 0;
   }

   public float getDamageBonus(ItemStack stack, LootRarity rarity, float level, MobType creatureType) {
      return 0.0F;
   }

   public void doPostAttack(ItemStack stack, LootRarity rarity, float level, LivingEntity user, @Nullable Entity target) {
   }

   public void doPostHurt(ItemStack stack, LootRarity rarity, float level, LivingEntity user, @Nullable Entity attacker) {
   }

   public void onArrowFired(ItemStack stack, LootRarity rarity, float level, LivingEntity user, AbstractArrow arrow) {
   }

   @Nullable
   public InteractionResult onItemUse(ItemStack stack, LootRarity rarity, float level, UseOnContext ctx) {
      return null;
   }

   public void onArrowImpact(AbstractArrow arrow, LootRarity rarity, float level, HitResult res, Type type) {
   }

   public float onShieldBlock(ItemStack stack, LootRarity rarity, float level, LivingEntity entity, DamageSource source, float amount) {
      return amount;
   }

   public void onBlockBreak(ItemStack stack, LootRarity rarity, float level, Player player, LevelAccessor world, BlockPos pos, BlockState state) {
   }

   public float getDurabilityBonusPercentage(ItemStack stack, LootRarity rarity, float level, @Nullable ServerPlayer user) {
      return 0.0F;
   }

   public float onHurt(ItemStack stack, LootRarity rarity, float level, DamageSource src, LivingEntity ent, float amount) {
      return amount;
   }

   public boolean enablesTelepathy() {
      return false;
   }

   public void getEnchantmentLevels(ItemStack stack, LootRarity rarity, float level, Map<Enchantment, Integer> enchantments) {
   }

   public void modifyLoot(ItemStack stack, LootRarity rarity, float level, ObjectArrayList<ItemStack> loot, LootContext ctx) {
   }

   public String toString() {
      return String.format("Affix: %s", this.getId());
   }

   public AffixType getType() {
      return this.type;
   }

   public abstract boolean canApplyTo(ItemStack var1, LootCategory var2, LootRarity var3);

   public static boolean isOnCooldown(ResourceLocation id, int cooldown, LivingEntity entity) {
      long lastApplied = entity.getPersistentData().m_128454_("apoth.affix_cooldown." + id.toString());
      return lastApplied != 0L && lastApplied + (long)cooldown >= entity.f_19853_.m_46467_();
   }

   public static void startCooldown(ResourceLocation id, LivingEntity entity) {
      entity.getPersistentData().m_128356_("apoth.affix_cooldown." + id.toString(), entity.f_19853_.m_46467_());
   }

   public static String fmt(float f) {
      return f == (float)((long)f) ? String.format("%d", (long)f) : ItemStack.f_41584_.format((double)f);
   }

   public boolean equals(Object obj) {
      if (obj instanceof Affix afx && afx.getId().equals(this.getId())) {
         return true;
      }

      return false;
   }

   public int hashCode() {
      return this.getId().hashCode();
   }
}
