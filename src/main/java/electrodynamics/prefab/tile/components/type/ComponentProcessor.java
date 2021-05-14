package electrodynamics.prefab.tile.components.type;

import java.util.HashSet;
import java.util.function.Consumer;
import java.util.function.Predicate;

import electrodynamics.common.item.ItemProcessorUpgrade;
import electrodynamics.prefab.tile.GenericTile;
import electrodynamics.prefab.tile.components.Component;
import electrodynamics.prefab.tile.components.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

public class ComponentProcessor implements Component {
    private GenericTile holder;

    @Override
    public void holder(GenericTile holder) {
	this.holder = holder;
    }

    public double operatingSpeed;
    public double operatingTicks;
    public double usage;
    public long requiredTicks;
    private Predicate<ComponentProcessor> canProcess = component -> false;
    private Consumer<ComponentProcessor> process;
    private Consumer<ComponentProcessor> failed;
    private ComponentProcessorType processorType;
    private HashSet<Integer> upgradeSlots = new HashSet<>();
    private int inputOne = 0;
    private int inputTwo = 1;
    private int output = 1;

    public ComponentProcessor(GenericTile source) {
	holder(source);
	if (!holder.hasComponent(ComponentType.Inventory)) {
	    throw new UnsupportedOperationException("You need to implement an inventory component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.Tickable)) {
	    holder.<ComponentTickable>getComponent(ComponentType.Tickable).tickServer(this::tickServer);
	} else {
	    throw new UnsupportedOperationException("You need to implement a tickable component to use the processor component!");
	}
	if (holder.hasComponent(ComponentType.PacketHandler)) {
	    ComponentPacketHandler handler = holder.getComponent(ComponentType.PacketHandler);
	    handler.guiPacketWriter(this::writeGuiPacket);
	    handler.guiPacketReader(this::readGuiPacket);
	}
    }

    private void tickServer(ComponentTickable tickable) {
	double calculatedOperatingSpeed = 1;
	ComponentInventory inv = holder.getComponent(ComponentType.Inventory);
	if (holder.hasComponent(ComponentType.PacketHandler) && holder.<ComponentTickable>getComponent(ComponentType.Tickable).getTicks() % 20 == 0) {
	    holder.<ComponentPacketHandler>getComponent(ComponentType.PacketHandler).sendGuiPacketToTracking();
	}
	for (int slot : upgradeSlots) {
	    ItemStack stack = inv.getStackInSlot(slot);
	    if (!stack.isEmpty() && stack.getItem() instanceof ItemProcessorUpgrade) {
		calculatedOperatingSpeed *= ((ItemProcessorUpgrade) stack.getItem()).subtype.speedMultiplier;
	    }
	}
	if (calculatedOperatingSpeed > 0 && calculatedOperatingSpeed != operatingSpeed) {
	    operatingSpeed = calculatedOperatingSpeed;
	}
	if (holder.hasComponent(ComponentType.Electrodynamic)) {
	    ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
	    electro.maxJoules(usage * operatingSpeed * 10);
	}
	if (canProcess.test(this)) {
	    operatingTicks += operatingSpeed;
	    if (operatingTicks >= requiredTicks) {
		if (process != null) {
		    process.accept(this);
		}
		operatingTicks = 0;
	    }
	    if (holder.hasComponent(ComponentType.Electrodynamic)) {
		ComponentElectrodynamic electro = holder.getComponent(ComponentType.Electrodynamic);
		electro.joules(electro.getJoulesStored() - usage * operatingSpeed);
	    }
	} else if (operatingTicks > 0) {
	    operatingTicks = 0;
	    if (failed != null) {
		failed.accept(this);
	    }
	}
    }

    private void writeGuiPacket(CompoundNBT nbt) {
	int offset = holder.getProcessor(0) == this ? 0 : holder.getProcessor(1) == this ? 1 : holder.getProcessor(2) == this ? 2 : 0;
	nbt.putDouble("operatingTicks" + offset, operatingTicks);
	nbt.putDouble("joulesPerTick" + offset, usage * operatingSpeed);
	nbt.putLong("requiredTicks" + offset, requiredTicks);
    }

    private void readGuiPacket(CompoundNBT nbt) {
	int offset = holder.getProcessor(0) == this ? 0 : holder.getProcessor(1) == this ? 1 : holder.getProcessor(2) == this ? 2 : 0;
	operatingTicks = nbt.getDouble("operatingTicks" + offset);
	usage = nbt.getDouble("joulesPerTick" + offset);
	requiredTicks = nbt.getLong("requiredTicks" + offset);
    }

    public ComponentProcessorType getProcessorType() {
	return processorType;
    }

    public ComponentProcessor process(Consumer<ComponentProcessor> process) {
	this.process = process;
	return this;
    }

    public ComponentProcessor failed(Consumer<ComponentProcessor> failed) {
	this.failed = failed;
	return this;
    }

    public ComponentProcessor canProcess(Predicate<ComponentProcessor> canProcess) {
	this.canProcess = canProcess;
	return this;
    }

    public ComponentProcessor type(ComponentProcessorType type) {
	processorType = type;
	inputOne = 0;
	inputTwo = 1;
	output = type == ComponentProcessorType.DoubleObjectToObject ? 2 : 1;
	return this;
    }

    public ComponentProcessor upgradeSlots(int... slot) {
	for (int i : slot) {
	    upgradeSlots.add(i);
	}
	return this;
    }

    public ComponentProcessor inputSlot(int inputOne) {
	this.inputOne = inputOne;
	return this;
    }

    public ComponentProcessor secondInputSlot(int inputTwo) {
	this.inputTwo = inputTwo;
	return this;
    }

    public ComponentProcessor outputSlot(int output) {
	this.output = output;
	return this;
    }

    public ItemStack getInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(inputOne);
    }

    public ItemStack getSecondInput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(inputTwo);
    }

    public ItemStack getOutput() {
	return holder.<ComponentInventory>getComponent(ComponentType.Inventory).getStackInSlot(output);
    }

    public ComponentProcessor output(ItemStack stack) {
	holder.<ComponentInventory>getComponent(ComponentType.Inventory).setInventorySlotContents(output, stack);
	return this;
    }

    public ComponentProcessor usage(double usage) {
	this.usage = usage;
	return this;
    }

    public double getUsage() {
	return usage;
    }

    public ComponentProcessor requiredTicks(long requiredTicks) {
	this.requiredTicks = requiredTicks;
	return this;
    }

    @Override
    public ComponentType getType() {
	return ComponentType.Processor;
    }

}
