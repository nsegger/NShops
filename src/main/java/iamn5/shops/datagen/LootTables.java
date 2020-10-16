package iamn5.shops.datagen;

import iamn5.shops.datagen.base.loot.LootTableBaseProvider;
import iamn5.shops.datagen.base.loot.table.BlockLootTablesBase;
import iamn5.shops.datagen.base.loot.table.NShopsBlockLootTables;
import net.minecraft.data.DataGenerator;

public class LootTables extends LootTableBaseProvider {
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    public void registerLootTables() {  }

    @Override
    public BlockLootTablesBase getBlockLootTables() {
        return new NShopsBlockLootTables();
    }
}
