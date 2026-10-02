package com.hollingsworth.arsnouveau.api.source;

public interface ISourceTile {
   int getTransferRate();

   boolean canAcceptSource();

   int getSource();

   int getMaxSource();

   void setMaxSource(int var1);

   int setSource(int var1);

   int addSource(int var1);

   int removeSource(int var1);
}
