package techreborn.compat.minetweaker;

import minetweaker.IUndoableAction;
import minetweaker.MineTweakerAPI;
import minetweaker.api.item.IIngredient;
import minetweaker.api.item.IItemStack;
import net.minecraft.item.ItemStack;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import techreborn.api.recipe.IBaseRecipeType;
import techreborn.api.recipe.RecipeHandler;
import techreborn.api.recipe.machines.ImplosionCompressorRecipe;
import techreborn.lib.Reference;
import techreborn.util.ItemUtils;

import java.util.List;

@ZenClass("mods.techreborn.implosionCompressor")
public class MTImplosionCompressor {
    @ZenMethod
    public static void addRecipe(IItemStack output1, IItemStack output2, IIngredient input1, IIngredient input2, int ticktime, int euTick) {
        ItemStack oInput1 = (ItemStack) MinetweakerCompat.toObject(input1);
        if (oInput1 == null)
            return;

        ItemStack oInput2 = (ItemStack) MinetweakerCompat.toObject(input2);
        if (oInput2 == null)
            return;

        ImplosionCompressorRecipe r = new ImplosionCompressorRecipe(oInput1, oInput2, MinetweakerCompat.toStack(output1), MinetweakerCompat.toStack(output2), ticktime, euTick);

        MineTweakerAPI.apply(new Add(r));
    }

    private static class Add implements IUndoableAction {
        private final ImplosionCompressorRecipe recipe;

        public Add(ImplosionCompressorRecipe recipe) {
            this.recipe = recipe;
        }

        @Override
        public void apply() {
            RecipeHandler.addRecipe(recipe);
        }

        @Override
        public boolean canUndo() {
            return true;
        }

        @Override
        public void undo() {
            RecipeHandler.recipeList.remove(recipe);
        }

        @Override
        public String describe() {
            return "Adding Implosion Recipe for " + recipe.getOutput(0).getDisplayName();
        }

        @Override
        public String describeUndo() {
            return "Removing Implosion Recipe for " + recipe.getOutput(0).getDisplayName();
        }

        @Override
        public Object getOverrideKey() {
            return null;
        }
    }

    @ZenMethod
    public static void removeRecipe(IItemStack output)
    {
        MineTweakerAPI.apply(new Remove(MinetweakerCompat.toStack(output)));
    }
    private static class Remove implements IUndoableAction
    {
        private final ItemStack output;
        List<ImplosionCompressorRecipe> removedRecipes;
        public Remove(ItemStack output)
        {
            this.output = output;
        }
        @Override
        public void apply()
        {
            for(IBaseRecipeType recipeType : RecipeHandler.getRecipeClassFromName(Reference.implosionCompressorRecipe)){
                for(ItemStack stack : recipeType.getOutputs()){
                    if(ItemUtils.isItemEqual(stack, output, true, false)){
                        removedRecipes.add((ImplosionCompressorRecipe) recipeType);
                        RecipeHandler.recipeList.remove(recipeType);
                        break;
                    }
                }
            }
        }
        @Override
        public void undo()
        {
            if(removedRecipes!=null){
                for(ImplosionCompressorRecipe recipe : removedRecipes){
                    if(recipe!=null){
                        RecipeHandler.addRecipe(recipe);
                    }
                }
            }

        }
        @Override
        public String describe()
        {
            return "Removing Implosion Recipe for " + output.getDisplayName();
        }
        @Override
        public String describeUndo()
        {
            return "Re-Adding Implosion Recipe for " + output.getDisplayName();
        }
        @Override
        public Object getOverrideKey()
        {
            return null;
        }
        @Override
        public boolean canUndo()
        {
            return true;
        }
    }
}
