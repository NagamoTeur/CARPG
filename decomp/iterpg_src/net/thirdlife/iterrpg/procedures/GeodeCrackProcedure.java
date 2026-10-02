package net.thirdlife.iterrpg.procedures;

import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModItems;

public class GeodeCrackProcedure {
   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
      if (entity != null) {
         if (itemstack.m_204117_(ItemTags.create(new ResourceLocation("forge:geodes")))
            && (entity instanceof LivingEntity _livEnt ? _livEnt.m_21206_() : ItemStack.f_41583_).m_41720_() instanceof PickaxeItem) {
            if (entity instanceof Player _player) {
               _player.m_36335_().m_41524_(itemstack.m_41720_(), 2);
            }

            if (itemstack.m_41720_() == IterRpgModItems.STONE_GEODE.get()) {
               if (world instanceof ServerLevel _level) {
                  _level.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~ ~0.5 ~ loot iter_rpg:gameplay/geode_stone"
                     );
               }
            } else if (itemstack.m_41720_() == IterRpgModItems.DEEPSLATE_GEODE.get()) {
               if (world instanceof ServerLevel _level) {
                  _level.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~ ~0.5 ~ loot iter_rpg:geode_deepslate"
                     );
               }
            } else if (itemstack.m_41720_() == IterRpgModItems.NETHERRACK_GEODE.get()) {
               if (world instanceof ServerLevel _level) {
                  _level.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~ ~0.5 ~ loot iter_rpg:geode_netherrack"
                     );
               }
            } else if (itemstack.m_41720_() == IterRpgModItems.BLACKSTONE_GEODE.get()) {
               if (world instanceof ServerLevel _level) {
                  _level.m_7654_()
                     .m_129892_()
                     .m_230957_(
                        new CommandSourceStack(
                              CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                           )
                           .m_81324_(),
                        "loot spawn ~ ~0.5 ~ loot iter_rpg:geode_blackstone"
                     );
               }
            } else if (itemstack.m_41720_() == IterRpgModItems.ENDSTONE_GEODE.get() && world instanceof ServerLevel _level) {
               _level.m_7654_()
                  .m_129892_()
                  .m_230957_(
                     new CommandSourceStack(
                           CommandSource.f_80164_, new Vec3(x, y, z), Vec2.f_82462_, _level, 4, "", Component.m_237113_(""), _level.m_7654_(), null
                        )
                        .m_81324_(),
                     "loot spawn ~ ~0.5 ~ loot iter_rpg:geode_endstone"
                  );
            }

            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.8, 1.2)
                  );
               } else {
                  _level.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.basalt.break")),
                     SoundSource.PLAYERS,
                     1.0F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.8, 1.2),
                     false
                  );
               }
            }

            if (world instanceof Level _levelx) {
               if (!_levelx.m_5776_()) {
                  _levelx.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")),
                     SoundSource.PLAYERS,
                     0.5F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.8, 1.2)
                  );
               } else {
                  _levelx.m_7785_(
                     x,
                     y,
                     z,
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.iron_golem.damage")),
                     SoundSource.PLAYERS,
                     0.5F,
                     (float)Mth.m_216263_(RandomSource.m_216327_(), 0.8, 1.2),
                     false
                  );
               }
            }

            if (!(new Object() {
                  public boolean checkGamemode(Entity _ent) {
                     if (_ent instanceof ServerPlayer _serverPlayer) {
                        return _serverPlayer.f_8941_.m_9290_() == GameType.CREATIVE;
                     } else {
                        return _ent.f_19853_.m_5776_() && _ent instanceof Player _player
                           ? Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()) != null
                              && Minecraft.m_91087_().m_91403_().m_104949_(_player.m_36316_().getId()).m_105325_() == GameType.CREATIVE
                           : false;
                     }
                  }
               })
               .checkGamemode(entity)) {
               itemstack.m_41774_(1);
               if (1
                  > Mth.m_216271_(
                     RandomSource.m_216327_(),
                     0,
                     1 + EnchantmentHelper.m_44843_(Enchantments.f_44986_, entity instanceof LivingEntity _livEntx ? _livEntx.m_21206_() : ItemStack.f_41583_)
                  )) {
                  ItemStack _ist = entity instanceof LivingEntity _livEntxx ? _livEntxx.m_21206_() : ItemStack.f_41583_;
                  if (_ist.m_220157_(1, RandomSource.m_216327_(), null)) {
                     _ist.m_41774_(1);
                     _ist.m_41721_(0);
                  }
               }
            }
         }
      }
   }
}
