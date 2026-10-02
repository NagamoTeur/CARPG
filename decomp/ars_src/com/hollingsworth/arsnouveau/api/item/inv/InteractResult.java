package com.hollingsworth.arsnouveau.api.item.inv;

import com.hollingsworth.arsnouveau.common.items.ItemScroll;

public record InteractResult(ItemScroll.SortPref sortPref, boolean valid) {
}
