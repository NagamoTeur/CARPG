package shadows.apotheosis.adventure.affix.effect;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.HarvestCheck;
import net.minecraftforge.fluids.FluidType;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.ItemAdapter;
import shadows.placebo.json.PSerializer;

public class OmneticAffix extends Affix {
   public static final Codec<OmneticAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.mapCodec(OmneticAffix.OmneticData.CODEC).fieldOf("values").forGetter(a -> a.values)).apply(inst, OmneticAffix::new)
   );
   public static final PSerializer<OmneticAffix> SERIALIZER = PSerializer.fromCodec("Omnetic Affix", CODEC);
   protected final Map<LootRarity, OmneticAffix.OmneticData> values;

   public OmneticAffix(Map<LootRarity, OmneticAffix.OmneticData> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isBreaker() && this.values.containsKey(rarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{Component.m_237115_("misc.apotheosis." + this.values.get(rarity).name)}));
   }

   public void harvest(HarvestCheck e) {
      ItemStack stack = e.getEntity().m_21205_();
      if (!stack.m_41619_()) {
         AffixInstance inst = AffixHelper.getAffixes(stack).get(this);
         if (inst != null) {
            OmneticAffix.OmneticData data = this.values.get(inst.rarity());

            for (ItemStack item : data.items()) {
               if (item.m_41735_(e.getTargetBlock())) {
                  e.setCanHarvest(true);
                  return;
               }
            }
         }
      }
   }

   public void speed(BreakSpeed e) {
      ItemStack stack = e.getEntity().m_21205_();
      if (!stack.m_41619_()) {
         AffixInstance inst = AffixHelper.getAffixes(stack).get(this);
         if (inst != null) {
            float speed = e.getOriginalSpeed();
            OmneticAffix.OmneticData data = this.values.get(inst.rarity());

            for (ItemStack item : data.items()) {
               speed = Math.max(getBaseSpeed(e.getEntity(), item, e.getState(), e.getPosition().orElse(BlockPos.f_121853_)), speed);
            }

            e.setNewSpeed(speed);
         }
      }
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   static float getBaseSpeed(Player player, ItemStack tool, BlockState state, BlockPos pos) {
      float f = tool.m_41691_(state);
      if (f > 1.0F) {
         int i = EnchantmentHelper.m_44926_(player);
         ItemStack itemstack = player.m_21205_();
         if (i > 0 && !itemstack.m_41619_()) {
            f += (float)(i * i + 1);
         }
      }

      if (MobEffectUtil.m_19584_(player)) {
         f *= 1.0F + (float)(MobEffectUtil.m_19586_(player) + 1) * 0.2F;
      }

      if (player.m_21023_(MobEffects.f_19599_)) {
         float f1 = switch (player.m_21124_(MobEffects.f_19599_).m_19564_()) {
            case 0 -> 0.3F;
            case 1 -> 0.09F;
            case 2 -> 0.0027F;
            case 3 -> 8.1E-4F;
            default -> 8.1E-4F;
         };
         f *= f1;
      }

      if (player.isEyeInFluidType((FluidType)ForgeMod.WATER_TYPE.get()) && !EnchantmentHelper.m_44934_(player)) {
         f /= 5.0F;
      }

      if (!player.m_20096_()) {
         f /= 5.0F;
      }

      return f;
   }

   static record OmneticData(String name, ItemStack[] items) {
      public static Codec<OmneticAffix.OmneticData> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.STRING.fieldOf("name").forGetter(OmneticAffix.OmneticData::name),
                  Codec.list(ItemAdapter.CODEC)
                     .xmap(l -> l.toArray(new ItemStack[0]), Arrays::asList)
                     .fieldOf("items")
                     .forGetter(OmneticAffix.OmneticData::items)
               )
               .apply(inst, OmneticAffix.OmneticData::new)
      );
   }
}
