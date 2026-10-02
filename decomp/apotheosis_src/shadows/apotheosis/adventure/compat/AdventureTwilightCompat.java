package shadows.apotheosis.adventure.compat;

import com.google.common.base.Preconditions;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.StringUtil;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegistryObject;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.socket.gem.GemClass;
import shadows.apotheosis.adventure.affix.socket.gem.bonus.GemBonus;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.color.GradientColor;
import shadows.placebo.util.StepFunction;
import twilightforest.capabilities.CapabilityList;
import twilightforest.entity.monster.Redcap;

public class AdventureTwilightCompat {
   protected static final RegistryObject<Item> ORE_MAGNET = RegistryObject.create(
      new ResourceLocation("twilightforest", "ore_magnet"), Registry.f_122904_, "apotheosis"
   );
   protected static final RegistryObject<EntityType<Redcap>> REDCAP = RegistryObject.create(
      new ResourceLocation("twilightforest", "redcap"), Registry.f_122903_, "apotheosis"
   );

   public static void register() {
      GemBonus.CODECS.put(Apotheosis.loc("twilight_ore_magnet"), AdventureTwilightCompat.OreMagnetBonus.CODEC);
      GemBonus.CODECS.put(Apotheosis.loc("twilight_treasure_goblin"), AdventureTwilightCompat.TreasureGoblinBonus.CODEC);
      GemBonus.CODECS.put(Apotheosis.loc("twilight_fortification"), AdventureTwilightCompat.FortificationBonus.CODEC);
      MinecraftForge.EVENT_BUS.addListener(AdventureTwilightCompat::doGoblins);
   }

   @SubscribeEvent
   public static void doGoblins(EntityJoinLevelEvent e) {
      if (e.getEntity() instanceof Redcap r && r.getPersistentData().m_128441_("apoth.treasure_goblin")) {
         r.f_21346_.m_148096_();
         r.f_21345_.m_148096_();
         r.f_21345_.m_25352_(10, new AvoidEntityGoal(r, Player.class, 6.0F, 1.0, 1.25));
      }
   }

   public static class FortificationBonus extends GemBonus {
      public static final Codec<AdventureTwilightCompat.FortificationBonus> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(gemClass(), LootRarity.mapCodec(AdventureTwilightCompat.FortificationBonus.Data.CODEC).fieldOf("values").forGetter(a -> a.values))
               .apply(inst, AdventureTwilightCompat.FortificationBonus::new)
      );
      protected final Map<LootRarity, AdventureTwilightCompat.FortificationBonus.Data> values;

      public FortificationBonus(GemClass gemClass, Map<LootRarity, AdventureTwilightCompat.FortificationBonus.Data> values) {
         super(Apotheosis.loc("twilight_fortification"), gemClass);
         this.values = values;
      }

      @Override
      public void doPostHurt(ItemStack gem, LootRarity rarity, LivingEntity user, Entity attacker) {
         AdventureTwilightCompat.FortificationBonus.Data d = this.values.get(rarity);
         if (!Affix.isOnCooldown(this.getCooldownId(gem), d.cooldown, user)) {
            if (user.f_19796_.m_188501_() <= d.chance) {
               user.getCapability(CapabilityList.SHIELDS).ifPresent(cap -> cap.replenishShields());
               Affix.startCooldown(this.getCooldownId(gem), user);
            }
         }
      }

      public Codec<? extends GemBonus> getCodec() {
         return CODEC;
      }

      @Override
      public GemBonus validate() {
         Preconditions.checkArgument(!this.values.isEmpty(), "No values provided!");
         return this;
      }

      @Override
      public boolean supports(LootRarity rarity) {
         return this.values.containsKey(rarity);
      }

      @Override
      public int getNumberOfUUIDs() {
         return 0;
      }

      @Override
      public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
         AdventureTwilightCompat.FortificationBonus.Data d = this.values.get(rarity);
         Component cooldown = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(d.cooldown)});
         return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(d.chance * 100.0F), cooldown}).m_130940_(ChatFormatting.YELLOW);
      }

      protected static record Data(float chance, int cooldown) {
         public static final Codec<AdventureTwilightCompat.FortificationBonus.Data> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                     Codec.FLOAT.fieldOf("chance").forGetter(AdventureTwilightCompat.FortificationBonus.Data::chance),
                     Codec.INT.fieldOf("cooldown").forGetter(AdventureTwilightCompat.FortificationBonus.Data::cooldown)
                  )
                  .apply(inst, AdventureTwilightCompat.FortificationBonus.Data::new)
         );
      }
   }

   public static class OreMagnetBonus extends GemBonus {
      public static final Codec<AdventureTwilightCompat.OreMagnetBonus> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(gemClass(), GemBonus.VALUES_CODEC.fieldOf("values").forGetter(a -> a.values))
               .apply(inst, AdventureTwilightCompat.OreMagnetBonus::new)
      );
      protected final Map<LootRarity, StepFunction> values;

      public OreMagnetBonus(GemClass gemClass, Map<LootRarity, StepFunction> values) {
         super(Apotheosis.loc("twilight_ore_magnet"), gemClass);
         this.values = values;
      }

      @Override
      public InteractionResult onItemUse(ItemStack gem, LootRarity rarity, UseOnContext ctx) {
         BlockState state = ctx.m_43725_().m_8055_(ctx.m_8083_());
         if (state.m_60795_()) {
            return null;
         } else {
            Level level = ctx.m_43725_();
            Player player = ctx.m_43723_();
            player.m_6672_(ctx.m_43724_());
            ((Item)AdventureTwilightCompat.ORE_MAGNET.get()).m_5551_(gem, level, player, 0);
            player.m_5810_();
            int cost = this.values.get(rarity).getInt(0.0F);
            ctx.m_43722_().m_41622_(cost, player, user -> user.m_21190_(ctx.m_43724_()));
            return super.onItemUse(gem, rarity, ctx);
         }
      }

      public Codec<? extends GemBonus> getCodec() {
         return CODEC;
      }

      @Override
      public GemBonus validate() {
         Preconditions.checkArgument(!this.values.isEmpty(), "No values provided!");
         return this;
      }

      @Override
      public boolean supports(LootRarity rarity) {
         return this.values.containsKey(rarity);
      }

      @Override
      public int getNumberOfUUIDs() {
         return 0;
      }

      @Override
      public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
         return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{this.values.get(rarity).getInt(0.0F)}).m_130940_(ChatFormatting.YELLOW);
      }
   }

   public static class TreasureGoblinBonus extends GemBonus {
      public static final Codec<AdventureTwilightCompat.TreasureGoblinBonus> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(gemClass(), LootRarity.mapCodec(AdventureTwilightCompat.TreasureGoblinBonus.Data.CODEC).fieldOf("values").forGetter(a -> a.values))
               .apply(inst, AdventureTwilightCompat.TreasureGoblinBonus::new)
      );
      protected final Map<LootRarity, AdventureTwilightCompat.TreasureGoblinBonus.Data> values;

      public TreasureGoblinBonus(GemClass gemClass, Map<LootRarity, AdventureTwilightCompat.TreasureGoblinBonus.Data> values) {
         super(Apotheosis.loc("twilight_treasure_goblin"), gemClass);
         this.values = values;
      }

      @Override
      public void doPostAttack(ItemStack gem, LootRarity rarity, LivingEntity user, Entity target) {
         AdventureTwilightCompat.TreasureGoblinBonus.Data d = this.values.get(rarity);
         if (!Affix.isOnCooldown(this.getCooldownId(gem), d.cooldown, user)) {
            if (user.f_19796_.m_188501_() <= d.chance) {
               Redcap goblin = (Redcap)((EntityType)AdventureTwilightCompat.REDCAP.get()).m_20615_(user.f_19853_);
               CompoundTag tag = new CompoundTag();
               tag.m_128359_("DeathLootTable", "apotheosis:entity/treasure_goblin");
               goblin.m_7378_(tag);
               goblin.getPersistentData().m_128379_("apoth.treasure_goblin", true);
               goblin.m_6593_(Component.m_237115_("name.apotheosis.treasure_goblin").m_130938_(s -> s.m_131148_(GradientColor.RAINBOW)));
               goblin.m_20340_(true);
               goblin.m_21051_(Attributes.f_22279_).m_22125_(new AttributeModifier("apoth.very_fast", 0.2, Operation.ADDITION));
               goblin.m_21051_(Attributes.f_22276_).m_22125_(new AttributeModifier("apoth.healmth", 60.0, Operation.ADDITION));
               goblin.m_21153_(goblin.m_21233_());

               for (int i = 0; i < 8; i++) {
                  int x = Mth.m_216271_(goblin.f_19796_, -5, 5);
                  int y = Mth.m_216271_(goblin.f_19796_, -1, 1);
                  int z = Mth.m_216271_(goblin.f_19796_, -5, 5);
                  goblin.m_146884_(target.m_20182_().m_82520_((double)x, (double)y, (double)z));
                  if (user.f_19853_.m_45786_(goblin)) {
                     break;
                  }

                  if (i == 7) {
                     goblin.m_146884_(target.m_20182_());
                  }
               }

               goblin.m_7292_(new MobEffectInstance(MobEffects.f_19619_, 200, 0));
               user.f_19853_.m_7967_(goblin);
               Affix.startCooldown(this.getCooldownId(gem), user);
            }
         }
      }

      public Codec<? extends GemBonus> getCodec() {
         return CODEC;
      }

      @Override
      public GemBonus validate() {
         Preconditions.checkArgument(!this.values.isEmpty(), "No values provided!");
         return this;
      }

      @Override
      public boolean supports(LootRarity rarity) {
         return this.values.containsKey(rarity);
      }

      @Override
      public int getNumberOfUUIDs() {
         return 0;
      }

      @Override
      public Component getSocketBonusTooltip(ItemStack gem, LootRarity rarity) {
         AdventureTwilightCompat.TreasureGoblinBonus.Data d = this.values.get(rarity);
         Component cooldown = Component.m_237110_("affix.apotheosis.cooldown", new Object[]{StringUtil.m_14404_(d.cooldown)});
         return Component.m_237110_("bonus." + this.getId() + ".desc", new Object[]{Affix.fmt(d.chance * 100.0F), cooldown}).m_130940_(ChatFormatting.YELLOW);
      }

      protected static record Data(float chance, int cooldown) {
         public static final Codec<AdventureTwilightCompat.TreasureGoblinBonus.Data> CODEC = RecordCodecBuilder.create(
            inst -> inst.group(
                     Codec.FLOAT.fieldOf("chance").forGetter(AdventureTwilightCompat.TreasureGoblinBonus.Data::chance),
                     Codec.INT.fieldOf("cooldown").forGetter(AdventureTwilightCompat.TreasureGoblinBonus.Data::cooldown)
                  )
                  .apply(inst, AdventureTwilightCompat.TreasureGoblinBonus.Data::new)
         );
      }
   }
}
