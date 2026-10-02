package shadows.apotheosis.adventure.client;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import shadows.apotheosis.adventure.affix.socket.gem.Gem;
import shadows.apotheosis.adventure.affix.socket.gem.GemItem;

public class GemModel implements BakedModel {
   private final BakedModel original;
   private final ItemOverrides itemHandler;

   public GemModel(BakedModel original, ModelBakery loader) {
      this.original = original;
      BlockModel missing = (BlockModel)loader.m_119341_(ModelBakery.f_119230_);
      this.itemHandler = new ItemOverrides(loader, missing, id -> missing, Collections.emptyList()) {
         public BakedModel m_173464_(BakedModel original, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
            return GemModel.this.resolve(original, stack, world, entity, seed);
         }
      };
   }

   public BakedModel resolve(BakedModel original, ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity entity, int seed) {
      Gem gem = GemItem.getGem(stack);
      return gem != null ? Minecraft.m_91087_().m_91304_().getModel(new ResourceLocation("apotheosis", "item/gems/" + gem.getId().m_135815_())) : original;
   }

   public ItemOverrides m_7343_() {
      return this.itemHandler;
   }

   @Deprecated
   public List<BakedQuad> m_213637_(BlockState pState, Direction pDirection, RandomSource pRandom) {
      return this.original.m_213637_(pState, pDirection, pRandom);
   }

   public boolean m_7541_() {
      return this.original.m_7541_();
   }

   public boolean m_7539_() {
      return this.original.m_7539_();
   }

   public boolean m_7547_() {
      return this.original.m_7547_();
   }

   public boolean m_7521_() {
      return this.original.m_7521_();
   }

   @Deprecated
   public TextureAtlasSprite m_6160_() {
      return this.original.m_6160_();
   }

   @Deprecated
   public ItemTransforms m_7442_() {
      return this.original.m_7442_();
   }
}
