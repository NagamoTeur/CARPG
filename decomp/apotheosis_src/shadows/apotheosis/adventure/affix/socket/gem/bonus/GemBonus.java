package shadows.apotheosis.adventure.affix.socket.gem.bonus;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.SimpleMapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder.Mu;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Map;
import java.util.function.BiConsumer;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
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
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.special.AllStatsBonus;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.special.BloodyArrowBonus;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.special.DropTransformBonus;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.special.LeechBlockBonus;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.special.MageSlayerBonus;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.codec.PlaceboCodecs;
import shadows.placebo.codec.PlaceboCodecs.CodecProvider;
import shadows.placebo.util.StepFunction;

public abstract class GemBonus implements CodecProvider<GemBonus> {
   public static final SimpleMapCodec<LootRarity, StepFunction> VALUES_CODEC = LootRarity.mapCodec(StepFunction.CODEC);
   public static final BiMap<ResourceLocation, Codec<? extends GemBonus>> CODECS = HashBiMap.create();
   public static final Codec<GemBonus> CODEC = PlaceboCodecs.mapBacked("Gem Bonus", CODECS);
   protected final ResourceLocation id;
   protected final GemClass gemClass;

   public GemBonus(ResourceLocation id, GemClass gemClass) {
      this.id = id;
      this.gemClass = gemClass;
   }

   public abstract GemBonus validate();

   public abstract boolean supports(LootRarity var1);

   public abstract int getNumberOfUUIDs();

   public abstract Component getSocketBonusTooltip(ItemStack var1, LootRarity var2);

   public void addModifiers(ItemStack gem, LootRarity rarity, BiConsumer<Attribute, AttributeModifier> map) {
   }

   public int getDamageProtection(ItemStack gem, LootRarity rarity, DamageSource source) {
      return 0;
   }

   public float getDamageBonus(ItemStack gem, LootRarity rarity, MobType type) {
      return 0.0F;
   }

   public void doPostAttack(ItemStack gem, LootRarity rarity, LivingEntity user, @Nullable Entity target) {
   }

   public void doPostHurt(ItemStack gem, LootRarity rarity, LivingEntity user, @Nullable Entity attacker) {
   }

   public void onArrowFired(ItemStack gem, LootRarity rarity, LivingEntity user, AbstractArrow arrow) {
   }

   @Nullable
   public InteractionResult onItemUse(ItemStack gem, LootRarity rarity, UseOnContext ctx) {
      return null;
   }

   public void onArrowImpact(ItemStack gemStack, LootRarity rarity, AbstractArrow arrow, HitResult res, Type type) {
   }

   public float onShieldBlock(ItemStack gem, LootRarity rarity, LivingEntity entity, DamageSource source, float amount) {
      return amount;
   }

   public void onBlockBreak(ItemStack gem, LootRarity rarity, Player player, LevelAccessor world, BlockPos pos, BlockState state) {
   }

   public float getDurabilityBonusPercentage(ItemStack gem, LootRarity rarity, @Nullable ServerPlayer user) {
      return 0.0F;
   }

   public float onHurt(ItemStack gem, LootRarity rarity, DamageSource src, LivingEntity user, float amount) {
      return amount;
   }

   public void getEnchantmentLevels(ItemStack gem, LootRarity rarity, Map<Enchantment, Integer> enchantments) {
   }

   public void modifyLoot(ItemStack gem, LootRarity rarity, ObjectArrayList<ItemStack> loot, LootContext ctx) {
   }

   public ResourceLocation getId() {
      return this.id;
   }

   public GemClass getGemClass() {
      return this.gemClass;
   }

   protected final ResourceLocation getCooldownId(ItemStack gemStack) {
      ResourceLocation gemId = GemItem.getGem(gemStack).getId();
      return new ResourceLocation(gemId.m_135827_(), gemId.m_135815_() + "/" + this.getId().m_214298_());
   }

   protected static <T extends GemBonus> App<Mu<T>, GemClass> gemClass() {
      return GemClass.CODEC.fieldOf("gem_class").forGetter(GemBonus::getGemClass);
   }

   public static void initCodecs() {
      register("attribute", AttributeBonus.CODEC);
      register("multi_attribute", MultiAttrBonus.CODEC);
      register("durability", DurabilityBonus.CODEC);
      register("damage_reduction", DamageReductionBonus.CODEC);
      register("enchantment", EnchantmentBonus.CODEC);
      register("bloody_arrow", BloodyArrowBonus.CODEC);
      register("leech_block", LeechBlockBonus.CODEC);
      register("all_stats", AllStatsBonus.CODEC);
      register("drop_transform", DropTransformBonus.CODEC);
      register("mageslayer", MageSlayerBonus.CODEC);
      register("mob_effect", PotionBonus.CODEC);
   }

   private static void register(String id, Codec<? extends GemBonus> codec) {
      CODECS.put(Apotheosis.loc(id), codec);
   }
}
