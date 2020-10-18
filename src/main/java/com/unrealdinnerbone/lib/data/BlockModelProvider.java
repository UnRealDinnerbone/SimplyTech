package com.unrealdinnerbone.lib.data;

import com.unrealdinnerbone.lib.api.SimplyReference;
import com.unrealdinnerbone.lib.api.TransferType;
import com.unrealdinnerbone.lib.data.api.IBlockModelProvider;
import com.unrealdinnerbone.lib.util.RegistryUtils;
import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;

public class BlockModelProvider extends BlockStateProvider {

    public BlockModelProvider(net.minecraft.data.DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, SimplyReference.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        RegistryUtils.getForType(ForgeRegistries.BLOCKS).forEach(deferredRegister -> {
            deferredRegister.getEntries().stream()
                    .filter(RegistryObject::isPresent)
                    .filter(itemRegistryObject -> itemRegistryObject.get() instanceof IBlockModelProvider)
                    .forEach(blockRegistryObject ->
                            ((IBlockModelProvider) blockRegistryObject.get())
                                    .registerBlockModel((Block) blockRegistryObject.get(), this));
        });
        Arrays.stream(Direction.values())
                .forEach(value ->
                        TransferType.TEXTURES
                                .forEach(texture -> getDirectionOnlyTextureModel(mcLoc("block/orientable"), value, "block/machine/transfer/" + texture.getString())));
    }

    public ModelFile getDirectionOnlyTextureModel(ResourceLocation parent, Direction direction, String texture) {
        return this.models().getBuilder(texture + "/" + direction.getString())
                .parent(this.models().getExistingFile(parent))
                .texture(direction.getString(), texture)
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(direction)
                .texture("#" + direction.getString())
                .cullface(direction)
                .end()
                .end();
    }

    public ModelFile getDirectionOnlyTextureTwo(ResourceLocation parent, Direction direction, String texture) {
        return this.models().getBuilder(texture)
                .parent(this.models().getExistingFile(parent))
                .texture(direction.getString(), texture)
                .element()
                .from(0, 0, 0)
                .to(16, 16, 16)
                .face(direction)
                .texture("#" + direction.getString())
                .cullface(direction)
                .end()
                .end();
    }
}
