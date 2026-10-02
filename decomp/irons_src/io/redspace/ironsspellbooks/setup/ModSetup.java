package io.redspace.ironsspellbooks.setup;

import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronInteraction;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronTile;
import io.redspace.ironsspellbooks.capabilities.magic.MagicEvents;
import io.redspace.ironsspellbooks.compat.CompatHandler;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
   public static void setup() {
      IEventBus bus = MinecraftForge.EVENT_BUS;
      bus.addGenericListener(Entity.class, MagicEvents::onAttachCapabilitiesPlayer);
      bus.addListener(MagicEvents::onRegisterCapabilities);
      bus.addListener(MagicEvents::onWorldTick);
   }

   public static void init(FMLCommonSetupEvent event) {
      Messages.register();
      CompatHandler.init();
      event.enqueueWork(
         () -> {
            ObjectIterator var0x = AlchemistCauldronTile.INTERACTIONS.entrySet().iterator();

            while (var0x.hasNext()) {
               Entry<Item, AlchemistCauldronInteraction> entry = (Entry<Item, AlchemistCauldronInteraction>)var0x.next();
               final Item item = entry.getKey();
               DispenserBlock.m_52672_(
                  item,
                  new OptionalDispenseItemBehavior() {
                     private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();
                     final Optional<DispenseItemBehavior> oldBehavior = DispenserBlock.f_52661_.containsKey(item)
                        ? Optional.of((DispenseItemBehavior)DispenserBlock.f_52661_.get(item))
                        : Optional.empty();

                     private ItemStack takeLiquid(BlockSource p_123447_, ItemStack p_123448_, ItemStack p_123449_) {
                        p_123448_.m_41774_(1);
                        if (p_123448_.m_41619_()) {
                           p_123447_.m_7727_().m_142346_((Entity)null, GameEvent.f_157816_, p_123447_.m_7961_());
                           return p_123449_.m_41777_();
                        } else {
                           if (((DispenserBlockEntity)p_123447_.m_8118_()).m_59237_(p_123449_.m_41777_()) < 0) {
                              this.defaultDispenseItemBehavior.m_6115_(p_123447_, p_123449_.m_41777_());
                           }

                           return p_123448_;
                        }
                     }

                     public ItemStack m_7498_(BlockSource blockSource, ItemStack itemStack) {
                        this.m_123573_(false);
                        ServerLevel serverlevel = blockSource.m_7727_();
                        BlockPos blockpos = blockSource.m_7961_().m_121945_((Direction)blockSource.m_6414_().m_61143_(DispenserBlock.f_52659_));
                        BlockState blockstate = serverlevel.m_8055_(blockpos);
                        if (serverlevel.m_7702_(blockpos) instanceof AlchemistCauldronTile cauldron) {
                           ItemStack resultStack = ((AlchemistCauldronInteraction)AlchemistCauldronTile.INTERACTIONS.get(itemStack.m_41720_()))
                              .interact(cauldron, blockstate, serverlevel, blockpos, itemStack);
                           if (resultStack != null) {
                              this.m_123573_(true);
                              cauldron.m_6596_();
                              return this.takeLiquid(blockSource, itemStack, resultStack);
                           }
                        }

                        return this.oldBehavior.<ItemStack>map(behavior -> behavior.m_6115_(blockSource, itemStack)).orElse(itemStack);
                     }
                  }
               );
            }
         }
      );
   }
}
