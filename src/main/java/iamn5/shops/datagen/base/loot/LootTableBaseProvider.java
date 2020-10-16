package iamn5.shops.datagen.base.loot;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import iamn5.shops.datagen.base.loot.table.BlockLootTablesBase;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTableManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public abstract class LootTableBaseProvider extends LootTableProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    private final DataGenerator dataGenerator;

    private final Map<Block, LootTable.Builder> builders = new HashMap<>();

    public LootTableBaseProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.dataGenerator = dataGeneratorIn;
    }

    public abstract void registerLootTables();

    public BlockLootTablesBase getBlockLootTables() { return null; }

    public void addLootTable(Block block, LootTable.Builder builder) {
        builders.put(block, builder);
    }

    public void addLootTable(Map<Block, LootTable.Builder> map) {
        builders.putAll(map);
    }

    @Override
    public void act(DirectoryCache cache) {
        registerLootTables();

        BlockLootTablesBase blockLootTables = getBlockLootTables();

        if (blockLootTables != null) {
            addLootTable(blockLootTables.getTables());
        }

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        builders.forEach((block, builder) -> {
           if (tables.put(block.getLootTable(), builder.setParameterSet(LootParameterSets.BLOCK).build()) != null) {
               throw new IllegalStateException("Duplicate loot table " + block.getLootTable());
           }
        });

        saveLootTables(cache, tables);
    }

    private void saveLootTables(DirectoryCache cache, Map<ResourceLocation, LootTable> tables) {
        Path path = this.dataGenerator.getOutputFolder();

        tables.forEach((resourceLocation, lootTable) -> {
            Path path1 = getPath(path, resourceLocation);

            try {
                IDataProvider.save(GSON, cache, LootTableManager.toJson(lootTable), path1);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't save loot table {}", path1, ioexception);
            }
        });
    }

    private Path getPath(Path pathIn, ResourceLocation id) {
        return pathIn.resolve("data/" + id.getNamespace() + "/loot_tables/" + id.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "N5's Shops LootTables";
    }
}
