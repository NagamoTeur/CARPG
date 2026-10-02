package immersive_armors.util;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public class FlowingText {
   public static List<Component> wrap(Component text, int maxWidth) {
      Minecraft client = Minecraft.m_91087_();
      return client.f_91073_ != null && client.m_18695_() ? client.f_91062_.m_92865_().m_92414_(text, maxWidth, Style.f_131099_).stream().map(line -> {
         MutableComponent compiled = Component.m_237113_("");
         line.m_7451_((s, t) -> {
            compiled.m_7220_(Component.m_237113_(t).m_6270_(s));
            return Optional.empty();
         }, text.m_7383_());
         return compiled;
      }).collect(Collectors.toList()) : List.of(text);
   }
}
