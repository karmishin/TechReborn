package techreborn.blocks;

import me.modmuss50.jsonDestroyer.api.ITexturedBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import reborncore.common.BaseBlock;
import techreborn.client.TechRebornCreativeTabMisc;
import techreborn.init.ModBlocks;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Random;

public class BlockStorage2 extends BaseBlock implements ITexturedBlock
{

	public static final String[] types = new String[] { "tungstensteel",
			"iridium_reinforced_tungstensteel", "iridium_reinforced_stone", "ruby", "sapphire", "peridot",
			"yellowGarnet", "redGarnet", "copper", "tin", "refinedIron" };
	public PropertyInteger METADATA;

	public BlockStorage2(Material material)
	{
		super(material);
		setUnlocalizedName("techreborn.storage2");
		setCreativeTab(TechRebornCreativeTabMisc.instance);
		setHardness(2f);
		this.setDefaultState(this.getDefaultState().withProperty(METADATA, 0));
	}

	public static ItemStack getStorageBlockByName(String name, int count)
	{
		for (int i = 0; i < types.length; i++)
		{
			if (types[i].equals(name))
			{
				return new ItemStack(ModBlocks.storage2, count, i);
			}
		}
		throw new InvalidParameterException("The storage block " + name + " could not be found.");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
	{
		for (int meta = 0; meta < types.length; meta++)
		{
			list.add(new ItemStack(item, 1, meta));
		}
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}

	@Override
	public String getTextureNameFromState(IBlockState BlockStateContainer, EnumFacing facing)
	{
		return "techreborn:blocks/storage/" + types[getMetaFromState(BlockStateContainer)] + "_block";
	}

	@Override
	public int amountOfStates()
	{
		return types.length;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		if(meta > types.length){
			meta = 0;
		}
		return this.getDefaultState().withProperty(METADATA, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(METADATA);
	}

	protected BlockStateContainer createBlockState()
	{

		METADATA = PropertyInteger.create("type", 0, types.length - 1);
		return new BlockStateContainer(this, METADATA);
	}

}
