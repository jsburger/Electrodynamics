package electrodynamics.common.tile;

import electrodynamics.api.capability.ElectrodynamicsCapabilities;
import electrodynamics.api.sound.SoundAPI;
import electrodynamics.common.block.subtype.SubtypeMachine;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnace;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceDouble;
import electrodynamics.common.inventory.container.tile.ContainerElectricFurnaceTriple;
import electrodynamics.common.item.ItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.settings.Constants;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.ComponentType;
import electrodynamics.prefab.tile.components.type.ComponentContainerProvider;
import electrodynamics.prefab.tile.components.type.ComponentDirection;
import electrodynamics.prefab.tile.components.type.ComponentElectrodynamic;
import electrodynamics.prefab.tile.components.type.ComponentInventory;
import electrodynamics.prefab.tile.components.type.ComponentPacketHandler;
import electrodynamics.prefab.tile.components.type.ComponentProcessor;
import electrodynamics.prefab.tile.components.type.ComponentTickable;
import electrodynamics.prefab.utilities.InventoryUtils;
import electrodynamics.prefab.utilities.NBTUtils;
import electrodynamics.registers.ElectrodynamicsBlockTypes;
import electrodynamics.registers.ElectrodynamicsSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class TileElectricFurnace extends GenericTile {

	protected Recipe<?> cachedRecipe = null;
	protected long timeSinceChange = 0;

	public TileElectricFurnace(BlockPos worldPosition, BlockState blockState) {
		this(SubtypeMachine.electricfurnace, 0, worldPosition, blockState);
	}

	public TileElectricFurnace(SubtypeMachine machine, int extra, BlockPos worldPosition, BlockState blockState) {
		super(extra == 1 ? ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACEDOUBLE.get() : extra == 2 ? ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACETRIPLE.get() : ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACE.get(), worldPosition, blockState);

		int processorInputs = 1;
		int processorCount = extra + 1;
		int inputCount = processorInputs * (extra + 1);
		int outputCount = 1 * (extra + 1);
		int invSize = 3 + inputCount + outputCount;

		addComponent(new ComponentDirection());
		addComponent(new ComponentPacketHandler());
		addComponent(new ComponentTickable().tickServer(this::tickServer).tickClient(this::tickClient));
		addComponent(new ComponentElectrodynamic(this).relativeInput(Direction.NORTH).voltage(ElectrodynamicsCapabilities.DEFAULT_VOLTAGE * Math.pow(2, extra)).maxJoules(Constants.ELECTRICFURNACE_USAGE_PER_TICK * 20 * (extra + 1)));

		int[] ints = new int[extra + 1];
		for (int i = 0; i <= extra; i++) {
			ints[i] = i * 2;
		}

		addComponent(new ComponentInventory(this).size(invSize).inputs(inputCount).outputs(outputCount).upgrades(3).processors(processorCount).processorInputs(processorInputs).validUpgrades(ContainerElectricFurnace.VALID_UPGRADES).valid(machineValidator(ints)).setMachineSlots(extra).shouldSendInfo());
		addComponent(new ComponentContainerProvider(machine).createMenu((id, player) -> (extra == 0 ? new ContainerElectricFurnace(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 1 ? new ContainerElectricFurnaceDouble(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : extra == 2 ? new ContainerElectricFurnaceTriple(id, player, getComponent(ComponentType.Inventory), getCoordsArray()) : null)));

		for (int i = 0; i <= extra; i++) {
			addProcessor(new ComponentProcessor(this).setProcessorNumber(i).canProcess(this::canProcess).failed(component -> cachedRecipe = null).process(this::process).requiredTicks(Constants.ELECTRICFURNACE_REQUIRED_TICKS).usage(Constants.ELECTRICFURNACE_USAGE_PER_TICK));
		}
	}

	protected void tickServer(ComponentTickable tick) {
		InventoryUtils.handleExperienceUpgrade(this);
	}

	protected void process(ComponentProcessor component) {
		ComponentInventory inv = getComponent(ComponentType.Inventory);
		ItemStack output = inv.getOutputContents().get(component.getProcessorNumber());
		ItemStack result = cachedRecipe.getResultItem();
		if (!output.isEmpty()) {
			output.setCount(output.getCount() + result.getCount());
		} else {
			inv.setItem(inv.getOutputSlots().get(component.getProcessorNumber()), result.copy());
		}
		inv.getInputContents().get(component.getProcessorNumber()).get(0).shrink(1);
		for (ItemStack stack : inv.getUpgradeContents()) {
			if (!stack.isEmpty() && ((ItemUpgrade) stack.getItem()).subtype == SubtypeItemUpgrade.experience) {
				CompoundTag tag = stack.getOrCreateTag();
				tag.putDouble(NBTUtils.XP, tag.getDouble(NBTUtils.XP) + ((AbstractCookingRecipe) cachedRecipe).getExperience());
				break;
			}
		}
	}

	protected boolean canProcess(ComponentProcessor component) {
		timeSinceChange++;
		if (this.<ComponentElectrodynamic>getComponent(ComponentType.Electrodynamic).getJoulesStored() >= component.getUsage() * component.operatingSpeed.get()) {
			if (timeSinceChange > 40) {
				level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockStateProperties.LIT, true));
				timeSinceChange = 0;
			}
			ComponentInventory inv = getComponent(ComponentType.Inventory);
			if (!inv.getInputContents().get(component.getProcessorNumber()).get(0).isEmpty()) {
				if (cachedRecipe != null && !cachedRecipe.getIngredients().get(0).test(inv.getInputContents().get(component.getProcessorNumber()).get(0))) {
					cachedRecipe = null;
				}
				boolean hasRecipe = cachedRecipe != null;
				if (!hasRecipe) {
					for (Recipe<?> recipe : level.getRecipeManager().getRecipes()) {
						if (recipe.getType() == RecipeType.SMELTING && recipe.getIngredients().get(0).test(inv.getInputContents().get(component.getProcessorNumber()).get(0))) {
							hasRecipe = true;
							cachedRecipe = recipe;
						}
					}
				}
				if (hasRecipe && cachedRecipe.getIngredients().get(0).test(inv.getInputContents().get(component.getProcessorNumber()).get(0))) {
					ItemStack output = inv.getOutputContents().get(component.getProcessorNumber());
					ItemStack result = cachedRecipe.getResultItem();
					return (output.isEmpty() || ItemStack.isSame(output, result)) && output.getCount() + result.getCount() <= output.getMaxStackSize();
				}
			}
		} else if (timeSinceChange > 40) {
			timeSinceChange = 0;
			level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(BlockStateProperties.LIT, false));
		}

		return false;
	}

	protected void tickClient(ComponentTickable tickable) {
		boolean has = getType() == ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACEDOUBLE.get() ? getProcessor(0).operatingTicks.get() + getProcessor(1).operatingTicks.get() > 0 : getType() == ElectrodynamicsBlockTypes.TILE_ELECTRICFURNACETRIPLE.get() ? getProcessor(0).operatingTicks.get() + getProcessor(1).operatingTicks.get() + getProcessor(2).operatingTicks.get() > 0 : getProcessor(0).operatingTicks.get() > 0;
		if (has && level.random.nextDouble() < 0.15) {
			Direction direction = this.<ComponentDirection>getComponent(ComponentType.Direction).getDirection();
			double d4 = level.random.nextDouble();
			double d5 = direction.getAxis() == Direction.Axis.X ? direction.getStepX() * (direction.getStepX() == -1 ? 0 : 1) : d4;
			double d6 = level.random.nextDouble();
			double d7 = direction.getAxis() == Direction.Axis.Z ? direction.getStepZ() * (direction.getStepZ() == -1 ? 0 : 1) : d4;
			level.addParticle(ParticleTypes.SMOKE, worldPosition.getX() + d5, worldPosition.getY() + d6, worldPosition.getZ() + d7, 0.0D, 0.0D, 0.0D);
		}
		if (has && tickable.getTicks() % 200 == 0) {
			SoundAPI.playSound(ElectrodynamicsSounds.SOUND_HUM.get(), SoundSource.BLOCKS, 1, 1, worldPosition);
		}
	}
}
