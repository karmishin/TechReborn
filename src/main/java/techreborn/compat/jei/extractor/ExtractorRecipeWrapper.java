package techreborn.compat.jei.extractor;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import net.minecraft.client.Minecraft;
import techreborn.api.recipe.machines.ExtractorRecipe;
import techreborn.client.gui.GuiExtractor;
import techreborn.compat.jei.BaseRecipeWrapper;

public class ExtractorRecipeWrapper extends BaseRecipeWrapper<ExtractorRecipe>
{
	private final IDrawableAnimated progress;

	public ExtractorRecipeWrapper(@Nonnull IJeiHelpers jeiHelpers, @Nonnull ExtractorRecipe baseRecipe)
	{
		super(baseRecipe);
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		IDrawableStatic progressStatic = guiHelper.createDrawable(GuiExtractor.texture, 176, 17, 22, 11);

		int ticksPerCycle = baseRecipe.tickTime();
		this.progress = guiHelper.createAnimatedDrawable(progressStatic, ticksPerCycle,
				IDrawableAnimated.StartDirection.LEFT, false);
	}

	@Override
	public void drawAnimations(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight)
	{
		super.drawAnimations(minecraft, recipeWidth, recipeHeight);
		progress.draw(minecraft, 25, 7);
	}
}
