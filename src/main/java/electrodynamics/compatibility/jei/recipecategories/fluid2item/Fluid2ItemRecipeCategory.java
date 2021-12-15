package electrodynamics.compatibility.jei.recipecategories.fluid2item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mojang.blaze3d.vertex.PoseStack;

import electrodynamics.api.capability.CapabilityUtils;
import electrodynamics.common.recipe.categories.fluid2item.Fluid2ItemRecipe;
import electrodynamics.common.recipe.recipeutils.FluidIngredient;
import electrodynamics.common.recipe.recipeutils.ProbableFluid;
import electrodynamics.compatibility.jei.recipecategories.ElectrodynamicsRecipeCategory;
import electrodynamics.compatibility.jei.utils.gui.backgroud.BackgroundWrapper;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiFluidStackGroup;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public abstract class Fluid2ItemRecipeCategory extends ElectrodynamicsRecipeCategory<Fluid2ItemRecipe> {

    /*
     * DOCUMENTATION NOTES:
     * 
     * > Items supercede bucket slots in position > All biproducts will be included
     * with the outputSlots field > All fluid bucket output slots will be incled
     * with the outputSlots field
     */

    protected Fluid2ItemRecipeCategory(IGuiHelper guiHelper, String modID, String recipeGroup, ItemStack inputMachine, BackgroundWrapper bWrap,
	    int animTime) {

	super(guiHelper, modID, recipeGroup, inputMachine, bWrap, Fluid2ItemRecipe.class, animTime);
    }

    @Override
    public void setIngredients(Fluid2ItemRecipe recipe, IIngredients ingredients) {
	ingredients.setInputLists(VanillaTypes.ITEM, getItemInputs(recipe));
	ingredients.setInputLists(VanillaTypes.FLUID, getFluidInputs(recipe));
	ingredients.setOutputs(VanillaTypes.ITEM, getItemOutputs(recipe));
	if (recipe.hasFluidBiproducts()) {
	    ingredients.setOutputs(VanillaTypes.FLUID, Arrays.asList(recipe.getFullFluidBiStacks()));
	}
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, Fluid2ItemRecipe recipe, IIngredients ingredients) {
	IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();
	IGuiFluidStackGroup guiFluidStacks = recipeLayout.getFluidStacks();

	setItemInputs(guiItemStacks);
	setFluidInputs(guiFluidStacks, recipe.getFluidIngredients());
	setItemOutputs(guiItemStacks);
	if (recipe.hasFluidBiproducts()) {
	    setFluidOutputs(guiFluidStacks, recipe, 0, null);
	}

	guiItemStacks.set(ingredients);
	guiFluidStacks.set(ingredients);
    }

    @Override
    public void draw(Fluid2ItemRecipe recipe, PoseStack matrixStack, double mouseX, double mouseY) {
	drawInputSlots(matrixStack);
	drawOutputSlots(matrixStack);
	drawStaticArrows(matrixStack);
	drawFluidInputs(matrixStack);
	if (recipe.hasFluidBiproducts()) {
	    drawFluidOutputs(matrixStack);
	}
	drawAnimatedArrows(matrixStack);

	addDescriptions(matrixStack, recipe);

    }

    private static List<List<FluidStack>> getFluidInputs(Fluid2ItemRecipe recipe) {
	List<List<FluidStack>> ingredients = new ArrayList<>();
	for (FluidIngredient ing : recipe.getFluidIngredients()) {
	    List<FluidStack> fluids = new ArrayList<>();
	    for (FluidStack stack : ing.getMatchingFluids()) {
		if (!stack.getFluid().getRegistryName().toString().toLowerCase().contains("flow")) {
		    fluids.add(stack);
		}
	    }
	    ingredients.add(fluids);
	}
	return ingredients;
    }

    private static List<List<ItemStack>> getItemInputs(Fluid2ItemRecipe recipe) {
	List<FluidIngredient> ings = recipe.getFluidIngredients();
	List<List<ItemStack>> totalBuckets = new ArrayList<>();
	for (FluidIngredient ing : ings) {
	    List<ItemStack> buckets = new ArrayList<>();
	    for (FluidStack stack : ing.getMatchingFluids()) {
		ItemStack bucket = new ItemStack(stack.getFluid().getBucket(), 1);
		CapabilityUtils.fill(bucket, stack);
		buckets.add(bucket);
	    }
	    totalBuckets.add(buckets);
	}
	return totalBuckets;
    }

    private static List<ItemStack> getItemOutputs(Fluid2ItemRecipe recipe) {

	List<ItemStack> outputItems = new ArrayList<>();

	outputItems.add(recipe.getResultItem());

	if (recipe.hasItemBiproducts()) {
	    outputItems.addAll(Arrays.asList(recipe.getFullItemBiStacks()));
	}

	if (recipe.hasFluidBiproducts()) {
	    for (ProbableFluid stack : recipe.getFluidBiproducts()) {
		ItemStack temp = new ItemStack(stack.getFullStack().getFluid().getBucket(), 1);
		CapabilityUtils.fill(temp, stack.getFullStack());
		outputItems.add(temp);
	    }
	}
	return outputItems;
    }

}
