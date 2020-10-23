package iamn5.shops.datagen.base.loot.table;

import iamn5.shops.init.Registration;

public class NShopsBlockLootTables extends BaseBlockLootTables {

    public NShopsBlockLootTables() {
        addTables();
    }

    @Override
    protected void addTables() {
        registerLootTable(Registration.SHOP_BLOCK.get(), this::dropSelfWithContentsAndData);
    }
}
