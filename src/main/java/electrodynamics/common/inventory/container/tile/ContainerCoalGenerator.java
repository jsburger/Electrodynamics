package electrodynamics.common.inventory.container.tile;

import electrodynamics.common.reloadlistener.CoalGeneratorFuelRegister;
import electrodynamics.common.tile.TileCoalGenerator;
import electrodynamics.prefab.inventory.container.GenericContainerBlockEntity;
import electrodynamics.prefab.inventory.container.slot.item.type.SlotRestricted;
import electrodynamics.registers.ElectrodynamicsMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;

public class ContainerCoalGenerator extends GenericContainerBlockEntity<TileCoalGenerator> {

	public ContainerCoalGenerator(int id, Inventory playerinv) {
		this(id, playerinv, new SimpleContainer(1));
	}

	public ContainerCoalGenerator(int id, Inventory playerinv, Container inventory) {
		this(id, playerinv, inventory, new SimpleContainerData(3));
	}

	public ContainerCoalGenerator(int id, Inventory playerinv, Container inventory, ContainerData inventorydata) {
		super(ElectrodynamicsMenuTypes.CONTAINER_COALGENERATOR.get(), id, playerinv, inventory, inventorydata);
	}

	@Override
	public void addInventorySlots(Container inv, Inventory playerinv) {
		addSlot(new SlotRestricted(inv, nextIndex(), 25, 42).setRestriction(stack -> {
			return CoalGeneratorFuelRegister.INSTANCE.isFuel(stack.getItem());
		}));
	}

}
