package com.hollingsworth.arsnouveau.client;

import com.google.common.collect.Maps;
import com.hollingsworth.arsnouveau.client.shader.FixedMultiTextureStateShard;
import com.hollingsworth.arsnouveau.client.shader.Texture;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.RenderStateShard.EmptyTextureStateShard;
import net.minecraft.client.renderer.RenderStateShard.ShaderStateShard;
import net.minecraft.client.renderer.RenderStateShard.TextureStateShard;
import net.minecraft.client.renderer.RenderType.CompositeState;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Triple;

@EventBusSubscriber(
   value = {Dist.CLIENT},
   modid = "ars_nouveau",
   bus = Bus.MOD
)
public class ShaderRegistry extends RenderType {
   public static final RenderType SKY_RENDER_TYPE = RenderType.m_173215_(
      "ars_nouveau_sky",
      DefaultVertexFormat.f_85814_,
      Mode.QUADS,
      256,
      false,
      false,
      CompositeState.m_110628_()
         .m_173292_(new ShaderStateShard(() -> ClientInfo.skyShader))
         .m_173290_(new EmptyTextureStateShard(() -> RenderSystem.m_157453_(0, ClientInfo.skyRenderTarget.m_83975_()), () -> {
         }))
         .m_110691_(false)
   );
   private static final TriFunction<ResourceLocation, ResourceLocation, Boolean, RenderType> RAINBOW_ENTITY_RENDER_TYPE = memoize(
      (location, mask, outline) -> {
         CompositeState rendertype$compositestate = CompositeState.m_110628_()
            .m_173292_(new ShaderStateShard(() -> ClientInfo.rainbowShader))
            .m_173290_(new FixedMultiTextureStateShard(List.of(new Texture(location), new Texture(mask))))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(outline);
         return m_173215_("ars_nouveau_rainbow_entity", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, false, rendertype$compositestate);
      }
   );
   private static final BiFunction<ResourceLocation, Boolean, RenderType> BLAMED_TYPE = Util.m_143821_(
      (p_173233_, p_173234_) -> {
         CompositeState rendertype$compositestate = CompositeState.m_110628_()
            .m_173292_(new ShaderStateShard(() -> ClientInfo.blameShader))
            .m_173290_(new TextureStateShard(p_173233_, false, false))
            .m_110685_(f_110134_)
            .m_110661_(f_110110_)
            .m_110671_(f_110152_)
            .m_110677_(f_110154_)
            .m_110691_(p_173234_);
         return m_173215_("ars_nouveau_blame", DefaultVertexFormat.f_85812_, Mode.QUADS, 256, true, false, rendertype$compositestate);
      }
   );

   private ShaderRegistry(
      String name, VertexFormat format, Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState
   ) {
      super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
   }

   public static RenderType rainbowEntity(ResourceLocation location, ResourceLocation mask, boolean outline) {
      return (RenderType)RAINBOW_ENTITY_RENDER_TYPE.apply(location, mask, outline);
   }

   public static RenderType blamed(ResourceLocation location, boolean outline) {
      return BLAMED_TYPE.apply(location, outline);
   }

   @SubscribeEvent
   public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
      event.registerShader(
         new ShaderInstance(event.getResourceManager(), new ResourceLocation("ars_nouveau", "sky"), DefaultVertexFormat.f_85814_),
         s -> ClientInfo.skyShader = s
      );
      event.registerShader(
         new ShaderInstance(event.getResourceManager(), new ResourceLocation("ars_nouveau", "rainbow_entity"), DefaultVertexFormat.f_85812_),
         s -> ClientInfo.rainbowShader = s
      );
      event.registerShader(
         new ShaderInstance(event.getResourceManager(), new ResourceLocation("ars_nouveau", "blamed_entity"), DefaultVertexFormat.f_85812_),
         s -> ClientInfo.blameShader = s
      );
   }

   private static <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> pMemoBiFunction) {
      return new TriFunction<T, U, V, R>() {
         private final Map<Triple<T, U, V>, R> cache = Maps.newHashMap();

         public R apply(T t, U u, V v) {
            return this.cache.computeIfAbsent(Triple.of(t, u, v), triple -> (R)pMemoBiFunction.apply(triple.getLeft(), triple.getMiddle(), triple.getRight()));
         }

         @Override
         public String toString() {
            return "memoize/3[function=" + pMemoBiFunction + ", size=" + this.cache.size() + "]";
         }
      };
   }
}
