package iamn5.shops.datagen;

import iamn5.shops.datagen.base.loot.BaseLootTableProvider;
import iamn5.shops.datagen.base.loot.table.BaseBlockLootTables;
import iamn5.shops.datagen.base.loot.table.NShopsBlockLootTables;
import net.minecraft.data.DataGenerator;

public class LootTables extends BaseLootTableProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public void registerLootTables() {  }

    @Override
    public BaseBlockLootTables getBlockLootTables() {
        return new NShopsBlockLootTables();
    }
}
