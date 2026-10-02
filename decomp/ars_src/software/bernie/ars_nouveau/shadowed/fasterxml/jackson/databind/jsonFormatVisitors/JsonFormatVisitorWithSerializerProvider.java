package software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.jsonFormatVisitors;

import software.bernie.ars_nouveau.shadowed.fasterxml.jackson.databind.SerializerProvider;

public interface JsonFormatVisitorWithSerializerProvider {
   SerializerProvider getProvider();

   void setProvider(SerializerProvider var1);
}
