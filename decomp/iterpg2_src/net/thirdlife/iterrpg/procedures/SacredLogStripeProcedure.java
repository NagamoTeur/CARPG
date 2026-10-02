package net.thirdlife.iterrpg.procedures;

import com.google.common.collect.UnmodifiableIterator;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.thirdlife.iterrpg.init.IterRpgModBlocks;

@EventBusSubscriber
public class SacredLogStripeProcedure {
   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == event.getEntity().m_7655_()) {
         execute(
            event,
            event.getLevel(),
            (double)event.getPos().m_123341_(),
            (double)event.getPos().m_123342_(),
            (double)event.getPos().m_123343_(),
            event.getEntity()
         );
      }
   }

   public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
      execute(null, world, x, y, z, entity);
   }

   private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity) {
      if (entity != null) {
         if ((entity instanceof LivingEntity _livEnt ? _livEnt.m_21205_() : ItemStack.f_41583_).m_41720_() instanceof AxeItem
            && world.m_8055_(new BlockPos(x, y, z)).m_60734_() == IterRpgModBlocks.SACRED_LOG.get()) {
            BlockPos _bp = new BlockPos(x, y, z);
            BlockState _bs = ((Block)IterRpgModBlocks.STRIPED_SACRED_LOG.get()).m_49966_();
            BlockState _bso = world.m_8055_(_bp);
            UnmodifiableIterator var13 = _bso.m_61148_().entrySet().iterator();

            while (var13.hasNext()) {
               Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var13.next();
               Property _property = _bs.m_60734_().m_49965_().m_61081_(entry.getKey().m_61708_());
               if (_property != null && _bs.m_61143_(_property) != null) {
                  try {
                     _bs = (BlockState)_bs.m_61124_(_property, entry.getValue());
                  } catch (Exception var17) {
                  }
               }
            }

            world.m_7731_(_bp, _bs, 3);
            if (world instanceof Level _level) {
               if (!_level.m_5776_()) {
                  _level.m_5594_(
                     null,
                     new BlockPos(x, y, z),
                     (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")),
                     SoundSource.PLAYERS,
                     1.0F,
                     1.0F
                  );
               } else {
                  _level.m_7785_(
                     x, y, z, (SoundEvent)ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.axe.strip")), SoundSource.PLAYERS, 1.0F, 1.0F, false
                  );
               }
            }

            ItemStack _ist = entity instanceof LivingEntity _livEntx ? _livEntx.m_21205_() : ItemStack.f_41583_;
            if (_ist.m_220157_(1, RandomSource.m_216327_(), null)) {
               _ist.m_41774_(1);
               _ist.m_41721_(0);
            }
         }
      }
   }
}
