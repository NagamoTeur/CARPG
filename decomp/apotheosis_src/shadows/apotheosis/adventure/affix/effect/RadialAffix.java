package shadows.apotheosis.adventure.affix.effect;

import com.google.common.base.Predicate;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.level.BlockEvent.BreakEvent;
import shadows.apotheosis.Apotheosis;
import shadows.apotheosis.adventure.affix.Affix;
import shadows.apotheosis.adventure.affix.AffixHelper;
import shadows.apotheosis.adventure.affix.AffixInstance;
import shadows.apotheosis.adventure.affix.AffixType;
import shadows.apotheosis.adventure.loot.LootCategory;
import shadows.apotheosis.adventure.loot.LootRarity;
import shadows.placebo.json.PSerializer;
import shadows.placebo.util.PlaceboUtil;

public class RadialAffix extends Affix {
   public static final Codec<RadialAffix> CODEC = RecordCodecBuilder.create(
      inst -> inst.group(LootRarity.mapCodec(Codec.list(RadialAffix.RadialData.CODEC)).fieldOf("values").forGetter(a -> a.values))
            .apply(inst, RadialAffix::new)
   );
   public static final PSerializer<RadialAffix> SERIALIZER = PSerializer.fromCodec("Radial Affix", CODEC);
   private static Set<UUID> breakers = new HashSet<>();
   protected final Map<LootRarity, List<RadialAffix.RadialData>> values;

   public RadialAffix(Map<LootRarity, List<RadialAffix.RadialData>> values) {
      super(AffixType.ABILITY);
      this.values = values;
   }

   @Override
   public boolean canApplyTo(ItemStack stack, LootCategory cat, LootRarity rarity) {
      return cat.isBreaker() && this.values.containsKey(rarity);
   }

   @Override
   public void addInformation(ItemStack stack, LootRarity rarity, float level, Consumer<Component> list) {
      RadialAffix.RadialData data = this.getTrueLevel(rarity, level);
      list.accept(Component.m_237110_("affix." + this.getId() + ".desc", new Object[]{data.x, data.y}));
   }

   public void onBreak(BreakEvent e) {
      Player player = e.getPlayer();
      ItemStack tool = player.m_21205_();
      Level world = player.f_19853_;
      if (!world.f_46443_ && tool.m_41782_()) {
         AffixInstance inst = AffixHelper.getAffixes(tool).get(this);
         if (inst != null && RadialAffix.RadialState.getState(player).isRadialMiningEnabled(player)) {
            float hardness = e.getState().m_60800_(e.getLevel(), e.getPos());
            breakExtraBlocks((ServerPlayer)player, e.getPos(), tool, this.getTrueLevel(inst.rarity(), inst.level()), hardness);
         }
      }
   }

   private RadialAffix.RadialData getTrueLevel(LootRarity rarity, float level) {
      List<RadialAffix.RadialData> list = this.values.get(rarity);
      return list.get(Math.min(list.size() - 1, (int)Mth.m_14179_(level, 0.0F, (float)list.size())));
   }

   public PSerializer<? extends Affix> getSerializer() {
      return SERIALIZER;
   }

   public static void toggleRadialState(Player player) {
      RadialAffix.RadialState state = RadialAffix.RadialState.getState(player);
      RadialAffix.RadialState next = state.next();
      RadialAffix.RadialState.setState(player, next);
      player.m_213846_(
         Apotheosis.sysMessageHeader()
            .m_7220_(
               Component.m_237110_("misc.apotheosis.radial_state_updated", new Object[]{next.toComponent(), state.toComponent()})
                  .m_130940_(ChatFormatting.YELLOW)
            )
      );
   }

   public static void breakExtraBlocks(ServerPlayer player, BlockPos pos, ItemStack tool, RadialAffix.RadialData level, float hardness) {
      if (breakers.add(player.m_20148_())) {
         try {
            breakBlockRadius(player, pos, level.x, level.y, level.xOff, level.yOff, hardness);
         } catch (Exception var6) {
            var6.printStackTrace();
         }

         breakers.remove(player.m_20148_());
      }
   }

   public static void breakBlockRadius(ServerPlayer player, BlockPos pos, int x, int y, int xOff, int yOff, float hardness) {
      Level world = player.f_19853_;
      if (x >= 2 || y >= 2) {
         int lowerY = (int)Math.ceil((double)(-y) / 2.0);
         int upperY = (int)Math.round((double)y / 2.0);
         int lowerX = (int)Math.ceil((double)(-x) / 2.0);
         int upperX = (int)Math.round((double)x / 2.0);
         Vec3 base = player.m_20299_(0.0F);
         Vec3 look = player.m_20154_();
         double reach = player.m_21133_((Attribute)ForgeMod.REACH_DISTANCE.get());
         Vec3 target = base.m_82520_(look.f_82479_ * reach, look.f_82480_ * reach, look.f_82481_ * reach);
         HitResult trace = world.m_45547_(new ClipContext(base, target, Block.OUTLINE, Fluid.NONE, player));
         if (trace != null && trace.m_6662_() == Type.BLOCK) {
            BlockHitResult res = (BlockHitResult)trace;
            Direction face = res.m_82434_();

            for (int iy = lowerY; iy < upperY; iy++) {
               for (int ix = lowerX; ix < upperX; ix++) {
                  BlockPos genPos = new BlockPos(pos.m_123341_() + ix + xOff, pos.m_123342_() + iy + yOff, pos.m_123343_());
                  if (player.m_6350_().m_122434_() == Axis.X) {
                     genPos = new BlockPos(genPos.m_123341_() - (ix + xOff), genPos.m_123342_(), genPos.m_123343_() + ix + xOff);
                  }

                  if (face.m_122434_().m_122478_()) {
                     genPos = rotateDown(genPos, iy + yOff, player.m_6350_());
                  }

                  if (!genPos.equals(pos)) {
                     BlockState state = world.m_8055_(genPos);
                     float stateHardness = state.m_60800_(world, genPos);
                     if (!state.m_60795_() && stateHardness != -1.0F && stateHardness <= hardness * 3.0F && isEffective(state, player)) {
                        PlaceboUtil.tryHarvestBlock(player, genPos);
                     }
                  }
               }
            }
         }
      }
   }

   static BlockPos rotateDown(BlockPos pos, int y, Direction horizontal) {
      Vec3i vec = horizontal.m_122436_();
      return new BlockPos(pos.m_123341_() + vec.m_123341_() * y, pos.m_123342_() - y, pos.m_123343_() + vec.m_123343_() * y);
   }

   static boolean isEffective(BlockState state, Player player) {
      return player.m_36298_(state);
   }

   static record RadialData(int x, int y, int xOff, int yOff) {
      public static Codec<RadialAffix.RadialData> CODEC = RecordCodecBuilder.create(
         inst -> inst.group(
                  Codec.INT.fieldOf("x").forGetter(RadialAffix.RadialData::x),
                  Codec.INT.fieldOf("y").forGetter(RadialAffix.RadialData::y),
                  Codec.INT.fieldOf("xOff").forGetter(RadialAffix.RadialData::xOff),
                  Codec.INT.fieldOf("yOff").forGetter(RadialAffix.RadialData::yOff)
               )
               .apply(inst, RadialAffix.RadialData::new)
      );
   }

   static enum RadialState {
      REQUIRE_NOT_SNEAKING(p -> !p.m_6144_()),
      REQUIRE_SNEAKING(p -> p.m_6144_()),
      ENABLED(p -> true),
      DISABLED(p -> false);

      private Predicate<Player> condition;

      private RadialState(Predicate<Player> condition) {
         this.condition = condition;
      }

      public boolean isRadialMiningEnabled(Player input) {
         return this.condition.apply(input);
      }

      public RadialAffix.RadialState next() {
         return switch (this) {
            case REQUIRE_NOT_SNEAKING -> REQUIRE_SNEAKING;
            case REQUIRE_SNEAKING -> ENABLED;
            case ENABLED -> DISABLED;
            case DISABLED -> REQUIRE_NOT_SNEAKING;
         };
      }

      public Component toComponent() {
         return Component.m_237115_("misc.apotheosis.radial_state." + this.name().toLowerCase(Locale.ROOT));
      }

      public static RadialAffix.RadialState getState(Player player) {
         String str = player.getPersistentData().m_128461_("apoth.radial_state");

         try {
            return valueOf(str);
         } catch (Exception var3) {
            setState(player, REQUIRE_NOT_SNEAKING);
            return REQUIRE_NOT_SNEAKING;
         }
      }

      public static void setState(Player player, RadialAffix.RadialState state) {
         player.getPersistentData().m_128359_("apoth.radial_state", state.name());
      }
   }
}
