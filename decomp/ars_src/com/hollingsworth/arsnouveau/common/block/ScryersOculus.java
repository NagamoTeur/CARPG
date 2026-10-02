package com.hollingsworth.arsnouveau.common.block;

import com.hollingsworth.arsnouveau.client.gui.radial_menu.GuiRadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenu;
import com.hollingsworth.arsnouveau.client.gui.radial_menu.RadialMenuSlot;
import com.hollingsworth.arsnouveau.client.gui.utils.RenderUtils;
import com.hollingsworth.arsnouveau.common.block.tile.ArcanePedestalTile;
import com.hollingsworth.arsnouveau.common.block.tile.ScryersOculusTile;
import com.hollingsworth.arsnouveau.common.items.ScryerScroll;
import com.hollingsworth.arsnouveau.common.network.Networking;
import com.hollingsworth.arsnouveau.common.network.PacketMountCamera;
import com.hollingsworth.arsnouveau.common.util.PortUtil;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class ScryersOculus extends TickableModBlock {
   public ScryersOculus() {
      this(defaultProperties().m_60955_());
   }

   public ScryersOculus(Properties properties) {
      super(properties);
   }

   public RenderShape m_7514_(BlockState pState) {
      return RenderShape.ENTITYBLOCK_ANIMATED;
   }

   public InteractionResult m_6227_(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
      if (pHand == InteractionHand.MAIN_HAND && pLevel.f_46443_) {
         this.openMenu(pLevel, pPos, pPlayer);
      }

      return super.m_6227_(pState, pLevel, pPos, pPlayer, pHand, pHit);
   }

   @OnlyIn(Dist.CLIENT)
   public void openMenu(Level pLevel, BlockPos pPos, Player pPlayer) {
      List<RadialMenuSlot<Item>> slots = new ArrayList<>();
      List<ItemStack> stackList = new ArrayList<>();
      int offset = 3;

      for (BlockPos b : BlockPos.m_121940_(pPos.m_7918_(offset, -offset, offset), pPos.m_7918_(-offset, offset, -offset))) {
         BlockEntity var10 = pLevel.m_7702_(b);
         if (var10 instanceof ArcanePedestalTile) {
            ArcanePedestalTile tile = (ArcanePedestalTile)var10;
            if (tile.getStack().m_150930_(ItemsRegistry.SCRYER_SCROLL.get())) {
               slots.add(new RadialMenuSlot<>(tile.getStack().m_41786_().getString(), tile.getStack().m_41720_(), new ArrayList<>()));
               stackList.add(tile.getStack());
            }
         }
      }

      if (slots.isEmpty()) {
         PortUtil.sendMessage(pPlayer, Component.m_237115_("ars_nouveau.scryers_eye.no_scrolls"));
      } else {
         Minecraft.m_91087_().m_91152_(new GuiRadialMenu<>(new RadialMenu<>(scroll -> {
            ScryerScroll.ScryerScrollData data = new ScryerScroll.ScryerScrollData(stackList.get(scroll));
            if (data.pos == null) {
               PortUtil.sendMessage(pPlayer, Component.m_237115_("ars_nouveau.scryers_eye.no_pos"));
            } else {
               Networking.INSTANCE.sendToServer(new PacketMountCamera(data.pos));
            }
         }, slots, RenderUtils::drawItemAsIcon, 3)));
      }
   }

   @Nullable
   public BlockEntity m_142194_(BlockPos pPos, BlockState pState) {
      return new ScryersOculusTile(pPos, pState);
   }

   public boolean m_7357_(BlockState pState, BlockGetter pLevel, BlockPos pPos, PathComputationType pType) {
      return false;
   }
}
