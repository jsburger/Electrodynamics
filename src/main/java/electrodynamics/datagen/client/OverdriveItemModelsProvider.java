package electrodynamics.datagen.client;

import electrodynamics.api.References;
import electrodynamics.client.ClientRegister;
import electrodynamics.common.item.subtype.SubtypeCeramic;
import electrodynamics.common.item.subtype.SubtypeCircuit;
import electrodynamics.common.item.subtype.SubtypeCrystal;
import electrodynamics.common.item.subtype.SubtypeDrillHead;
import electrodynamics.common.item.subtype.SubtypeDust;
import electrodynamics.common.item.subtype.SubtypeGear;
import electrodynamics.common.item.subtype.SubtypeImpureDust;
import electrodynamics.common.item.subtype.SubtypeIngot;
import electrodynamics.common.item.subtype.SubtypeItemUpgrade;
import electrodynamics.common.item.subtype.SubtypeNugget;
import electrodynamics.common.item.subtype.SubtypeOxide;
import electrodynamics.common.item.subtype.SubtypePlate;
import electrodynamics.common.item.subtype.SubtypeRawOre;
import electrodynamics.common.item.subtype.SubtypeRod;
import electrodynamics.registers.ElectrodynamicsItems;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.loaders.ObjModelBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class OverdriveItemModelsProvider extends ItemModelProvider {

	public static final int BATTERY_MODEL_COUNT = 6;
	public static final int MATTER_CONTAINER_MODEL_COUNT = 9;

	public OverdriveItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
		super(generator, References.ID, existingFileHelper);
	}

	@Override
	protected void registerModels() {

		layeredItem(ElectrodynamicsItems.COAL_COKE, Parent.GENERATED, itemLoc("coalcoke"));
		layeredItem(ElectrodynamicsItems.ITEM_CERAMICINSULATION, Parent.GENERATED, itemLoc("insulationceramic"));
		// TODO coil
		layeredItem(ElectrodynamicsItems.ITEM_INSULATION, Parent.GENERATED, itemLoc("insulation"));
		layeredItem(ElectrodynamicsItems.ITEM_MOLYBDENUMFERTILIZER, Parent.GENERATED, itemLoc("molybdenumfertilizer"));
		layeredItem(ElectrodynamicsItems.ITEM_MOTOR, Parent.GENERATED, itemLoc("motor"));
		layeredItem(ElectrodynamicsItems.ITEM_RAWCOMPOSITEPLATING, Parent.GENERATED, itemLoc("compositeplatingraw"));
		layeredItem(ElectrodynamicsItems.ITEM_SHEETPLASTIC, Parent.GENERATED, itemLoc("sheetplastic"));
		layeredItem(ElectrodynamicsItems.SLAG, Parent.GENERATED, itemLoc("slag"));
		// TODO solar panel plate
		layeredItem(ElectrodynamicsItems.ITEM_TITANIUM_COIL, Parent.GENERATED, itemLoc("titaniumheatcoil"));

		layeredItem(ElectrodynamicsItems.ITEM_COMBATHELMET, Parent.GENERATED, itemLoc("armor/combatchestplate"));
		layeredItem(ElectrodynamicsItems.ITEM_COMBATBOOTS, Parent.GENERATED, itemLoc("armor/combatchestplate"));
		layeredItem(ElectrodynamicsItems.ITEM_COMBATLEGGINGS, Parent.GENERATED, itemLoc("armor/combatleggings"));
		layeredItem(ElectrodynamicsItems.ITEM_COMBATBOOTS, Parent.GENERATED, itemLoc("armor/combatboots"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEHELMET, Parent.GENERATED, itemLoc("armor/compositehelmet"));
		layeredItem(ElectrodynamicsItems.ITEM_COMBATCHESTPLATE, Parent.GENERATED, itemLoc("armor/compositechestplate"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITELEGGINGS, Parent.GENERATED, itemLoc("armor/compositeleggings"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEBOOTS, Parent.GENERATED, itemLoc("armor/compositeboots"));
		layeredItem(ElectrodynamicsItems.ITEM_COMPOSITEPLATING, Parent.GENERATED, itemLoc("compositeplating"));
		layeredItem(ElectrodynamicsItems.ITEM_NIGHTVISIONGOGGLES, Parent.GENERATED,
				itemLoc("armor/nightvisiongoggles"));
		layeredItem(ElectrodynamicsItems.ITEM_JETPACK, Parent.GENERATED, itemLoc("armor/jetpack"));
		layeredItem(ElectrodynamicsItems.ITEM_SERVOLEGGINGS, Parent.GENERATED, itemLoc("armor/servoleggings"));
		layeredItem(ElectrodynamicsItems.ITEM_HYDRAULICBOOTS, Parent.GENERATED, itemLoc("armor/hydraulicboots"));
		layeredItem(ElectrodynamicsItems.ITEM_RUBBERBOOTS, Parent.GENERATED, itemLoc("armor/rubberboots"));

		// TODO reinforced canister
		layeredItem(ElectrodynamicsItems.GUIDEBOOK, Parent.GENERATED, itemLoc("guidebook"));
		// TODO multimeter
		// TODO seismic scanner
		layeredItem(ElectrodynamicsItems.ITEM_WRENCH, Parent.GENERATED, itemLoc("wrench"));
		layeredItem(ElectrodynamicsItems.ITEM_BATTERY, Parent.GENERATED, itemLoc("battery"));
		layeredItem(ElectrodynamicsItems.ITEM_LITHIUMBATTERY, Parent.GENERATED, itemLoc("lithiumbattery"));
		layeredItem(ElectrodynamicsItems.ITEM_CARBYNEBATTERY, Parent.GENERATED, itemLoc("carbynebattery"));
		// TODO make this toggleable?
		layeredItem(ElectrodynamicsItems.ITEM_ELECTRICBATON, Parent.HANDHELD, itemLoc("tools/electricbaton"));
		toggleableItem(ElectrodynamicsItems.ITEM_ELECTRICCHAINSAW, "on", Parent.HANDHELD, Parent.HANDHELD,
				new ResourceLocation[] { itemLoc("tools/electricchainsaw") },
				new ResourceLocation[] { itemLoc("tools/electricchainsawon") });
		toggleableItem(ElectrodynamicsItems.ITEM_ELECTRICDRILL, "on", Parent.HANDHELD, Parent.HANDHELD,
				new ResourceLocation[] { itemLoc("tools/electricdrill") },
				new ResourceLocation[] { itemLoc("tools/electricdrillon") });

		
		for(SubtypeCeramic ceramic : SubtypeCeramic.values()) {
			layeredItem(ElectrodynamicsItems.getItem(ceramic), Parent.GENERATED, itemLoc("ceramic/" + ceramic.tag()));
		}
		
		for(SubtypeCircuit circuit : SubtypeCircuit.values()) {
			layeredItem(ElectrodynamicsItems.getItem(circuit), Parent.GENERATED, itemLoc("circuit/" + circuit.tag()));
		}
		
		for(SubtypeCrystal crystal : SubtypeCrystal.values()) {
			layeredItem(ElectrodynamicsItems.getItem(crystal), Parent.GENERATED, itemLoc("crystal/" + crystal.tag()));
		}
		
		for(SubtypeDrillHead drill : SubtypeDrillHead.values()) {
			layeredItem(ElectrodynamicsItems.getItem(drill), Parent.GENERATED, itemLoc("drillhead/" + drill.tag()));
		}
		
		for(SubtypeDust dust : SubtypeDust.values()) {
			layeredItem(ElectrodynamicsItems.getItem(dust), Parent.GENERATED, itemLoc("dust/" + dust.tag()));
		}
		
		for(SubtypeGear gear : SubtypeGear.values()) {
			layeredItem(ElectrodynamicsItems.getItem(gear), Parent.GENERATED, itemLoc("gear/" + gear.tag()));
		}
		
		for(SubtypeImpureDust impure : SubtypeImpureDust.values()) {
			layeredItem(ElectrodynamicsItems.getItem(impure), Parent.GENERATED, itemLoc("impuredust/" + impure.tag()));
		}
		
		for(SubtypeIngot ingot : SubtypeIngot.values()) {
			layeredItem(ElectrodynamicsItems.getItem(ingot), Parent.GENERATED, itemLoc("ingot/" + ingot.tag()));
		}
		
		for(SubtypeItemUpgrade upgrade : SubtypeItemUpgrade.values()) {
			//TODO
		}
		
		for(SubtypeNugget nugget : SubtypeNugget.values()) {
			layeredItem(ElectrodynamicsItems.getItem(nugget), Parent.GENERATED, itemLoc("nugget/" + nugget.tag()));
		}
		
		for(SubtypeOxide oxide : SubtypeOxide.values()) {
			layeredItem(ElectrodynamicsItems.getItem(oxide), Parent.GENERATED, itemLoc("oxide/" + oxide.tag()));
		}
		
		for(SubtypePlate plate : SubtypePlate.values()) {
			layeredItem(ElectrodynamicsItems.getItem(plate), Parent.GENERATED, itemLoc("plate/" + plate.tag()));
		}
		
		for(SubtypeRawOre raw : SubtypeRawOre.values()) {
			layeredItem(ElectrodynamicsItems.getItem(raw), Parent.GENERATED, itemLoc("rawore/" + raw.tag()));
		}
		
		for(SubtypeRod rod : SubtypeRod.values()) {
			layeredItem(ElectrodynamicsItems.getItem(rod), Parent.GENERATED, itemLoc("rod/" + rod.tag()));
		}
		
		
		
		
		
		/*
		for (UpgradeType type : UpgradeType.values()) {
			layeredItem(ItemRegistry.ITEM_UPGRADES.get(type), Parent.GENERATED,
					itemLoc("upgrade/upgrade_" + type.toString().toLowerCase()));
		}
		for (TypeIsolinearCircuit circuit : TypeIsolinearCircuit.values()) {
			layeredItem(ItemRegistry.ITEM_ISOLINEAR_CIRCUITS.get(circuit), Parent.GENERATED,
					itemLoc("isolinear_circuit/" + circuit.id()));
		}
		layeredItem(ItemRegistry.ITEM_LEAD_PLATE, Parent.GENERATED, itemLoc("lead_plate"));
		layeredItem(ItemRegistry.ITEM_PATTERN_DRIVE, Parent.GENERATED, itemLoc("pattern_drive/pattern_drive_base"),
				itemLoc("pattern_drive/bottom_light"), itemLoc("pattern_drive/middle_light"),
				itemLoc("pattern_drive/left_light"));

		toggleableItem(ItemRegistry.ITEM_MATTER_SCANNER, "_on", Parent.GENERATED, Parent.GENERATED,
				new ResourceLocation[] { itemLoc("matter_scanner/matter_scanner_off") },
				new ResourceLocation[] { itemLoc("matter_scanner/matter_scanner_on") });

		toggleableItem(ItemRegistry.ITEM_TRANSPORTER_FLASHDRIVE, "_stored", Parent.GENERATED, Parent.GENERATED,
				new ResourceLocation[] { itemLoc("flashdrive/flashdrive_transporter_empty") },
				new ResourceLocation[] { itemLoc("flashdrive/flashdrive_transporter_stored") });
		layeredItem(ItemRegistry.ITEM_ANDROID_PILL_BLUE, Parent.GENERATED, itemLoc("pill/pill_bottom"),
				itemLoc("pill/pill_top"));
		layeredItem(ItemRegistry.ITEM_ANDROID_PILL_RED, Parent.GENERATED, itemLoc("pill/pill_bottom"),
				itemLoc("pill/pill_top"));
		layeredItem(ItemRegistry.ITEM_ANDROID_PILL_YELLOW, Parent.GENERATED, itemLoc("pill/pill_bottom"),
				itemLoc("pill/pill_top"));

		layeredItem(ItemRegistry.ITEM_COMMUNICATOR, Parent.GENERATED, itemLoc("communicator"));
	*/
		// generateBatteries();
		// generateMatterContainers();
		// generateGuns();
		// generateCharger();

	}

	/*
	 * private void generateCharger() { getObjModel("charger", "block/charger",
	 * modLoc("block/charger")).parent(getExistingFile(mcLoc("block/cube")))
	 * .transforms() .transform(TransformType.GUI) .rotation(30.0F, 315.0F, 0.0F)
	 * .translation(0.0F, -5.0F, 0.0F) .scale(0.375F) .end()
	 * .transform(TransformType.GROUND) .rotation(0.0F, 90.0F, 0.0F)
	 * .translation(2.0F, 3.0F, -2.0F) .scale(0.25F) .end()
	 * .transform(TransformType.FIXED) .rotation(0.0F, 95.0F, 0.0F)
	 * .translation(4.0F, -5.0F, -7.0F) .scale(0.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(75.0F, 125.0F,
	 * 0.0F) .translation(1.0F, 6.0F, 0.0F) .scale(0.375F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(75.0F, -45.0F,
	 * 0.0F) .translation(-4.5F, 2.0F, 0.0F) .scale(0.375F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 125.0F,
	 * 0.0F) .translation(-2.0F, 0.0F, -2.0F) .scale(0.4F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 315.0F,
	 * 0.0F) .translation(-7.0F, 0.0F, -2.0F) .scale(0.4F) .end(); }
	 * 
	 * private void generateGuns() { getObjModel(name(ItemRegistry.ITEM_PHASER),
	 * "item/phaser", itemLoc("phaser")) .transforms() .transform(TransformType.GUI)
	 * .rotation(90.0F, -45.0F, 90.0F) .translation(-20.0F, 3.0F, 0.0F) .scale(2.0F)
	 * .end() .transform(TransformType.GROUND) .rotation(0.0F, 0.0F, 0.0F)
	 * .translation(12.0F, 7.5F, 9.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIXED) .rotation(90.0F, -45.0F, 90.0F)
	 * .translation(-20.0F, 4.0F, 15.0F) .scale(2.0F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-15.0F, 15.0F, -6.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(9.0F, 15.0F, -6.0F) .scale(1.5F) .end();
	 * 
	 * getObjModel(name(ItemRegistry.ITEM_ION_SNIPER), "item/ion_sniper",
	 * itemLoc("ion_sniper")) .transforms() .transform(TransformType.GUI)
	 * .rotation(90.0F, -45.0F, 90.0F) .translation(-5.0F, 4.0F, 0.0F) .scale(0.8F)
	 * .end() .transform(TransformType.GROUND) .rotation(0.0F, 0.0F, 0.0F)
	 * .translation(12.0F, 7.5F, 0.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIXED) .rotation(90.0F, -45.0F, 90.0F)
	 * .translation(-6.0F, 5.0F, 7.0F) .scale(1.0F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-15.0F, 15.0F, -6.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(9.0F, 15.0F, -6.0F) .scale(1.5F) .end();
	 * 
	 * getObjModel(name(ItemRegistry.ITEM_OMNI_TOOL), "item/omni_tool",
	 * itemLoc("omni_tool")) .transforms() .transform(TransformType.GUI)
	 * .rotation(90.0F, -45.0F, 90.0F) .translation(-15.5F, 5.5F, 0.0F) .scale(2.0F)
	 * .end() .transform(TransformType.GROUND) .rotation(0.0F, 0.0F, 0.0F)
	 * .translation(12.0F, 7.5F, 5.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIXED) .rotation(90.0F, -45.0F, 90.0F)
	 * .translation(-16.0F, 5.0F, 15.0F) .scale(2.0F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-12.0F, 12.0F, -8.0F) .scale(1.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(12.0F, 12.0F, -8.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-15.0F, 15.0F, -4.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(9.0F, 15.0F, -4.0F) .scale(1.5F) .end();
	 * 
	 * getObjModel(name(ItemRegistry.ITEM_PHASER_RIFLE), "item/phaser_rifle",
	 * itemLoc("phaser_rifle")) .transforms() .transform(TransformType.GUI)
	 * .rotation(90.0F, -45.0F, 90.0F) .translation(-10.5F, 2.0F, 0.0F)
	 * .scale(1.25F) .end() .transform(TransformType.GROUND) .rotation(0.0F, 0.0F,
	 * 0.0F) .translation(12.0F, 10.0F, 8.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIXED) .rotation(90.0F, -45.0F, 90.0F)
	 * .translation(-11.0F, 2.0F, 9.75F) .scale(1.25F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-14.0F, 14.0F, -11.6725F) .scale(1.75F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(14.0F, 14.0F, -11.6725F) .scale(1.75F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-17.0F, 17.0F, -7.0F) .scale(1.75F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(10.5F, 17.5F, -7.0F) .scale(1.75F) .end();
	 * 
	 * getObjModel(name(ItemRegistry.ITEM_PLASMA_SHOTGUN), "item/plasma_shotgun",
	 * itemLoc("plasma_shotgun")) .transforms() .transform(TransformType.GUI)
	 * .rotation(90.0F, -45.0F, 90.0F) .translation(-12.5F, 4.0F, 0.0F) .scale(1.5F)
	 * .end() .transform(TransformType.GROUND) .rotation(0.0F, 0.0F, 0.0F)
	 * .translation(12.0F, 7.5F, 5.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIXED) .rotation(90.0F, -45.0F, 90.0F)
	 * .translation(-12.5F, 4.0F, 9.75F) .scale(1.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.THIRD_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(12.0F, 12.0F, -10.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_RIGHT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(-15.0F, 15.0F, -6.0F) .scale(1.5F) .end()
	 * .transform(TransformType.FIRST_PERSON_LEFT_HAND) .rotation(0.0F, 180.0F,
	 * 0.0F) .translation(9.0F, 15.0F, -6.0F) .scale(1.5F) .end();
	 * 
	 * }
	 * 
	 * private void generateBatteries() { ResourceLocation batteryBase =
	 * itemLoc("battery/battery"); String battBarBase = "battery/battery_overlay";
	 * ItemModelBuilder[] batteries = new ItemModelBuilder[BATTERY_MODEL_COUNT];
	 * batteries[0] = layeredBuilder("item/battery/battery0", Parent.GENERATED,
	 * batteryBase); for(int i = 1; i < BATTERY_MODEL_COUNT; i++) { batteries[i] =
	 * layeredBuilder("item/battery/battery" + i, Parent.GENERATED, batteryBase,
	 * itemLoc(battBarBase + (i - 1))); } for(RegistryObject<Item> battery :
	 * ItemRegistry.ITEM_BATTERIES.getAll()) { if(((ItemBattery)battery.get()).type
	 * == BatteryType.CREATIVE) { withExistingParent(name(battery),
	 * Parent.CREATIVE_BATTERY.loc()); } else { ItemModelBuilder bat =
	 * withExistingParent(name(battery), Parent.BATTERY.loc()); for(int i = 1; i <
	 * BATTERY_MODEL_COUNT; i++) { bat =
	 * bat.override().model(batteries[i]).predicate(ClientRegister.CHARGE,
	 * (float)i).end(); } } } }
	 * 
	 * private void generateMatterContainers() { ResourceLocation containerBase =
	 * itemLoc("matter_container/container"); ResourceLocation containerStripe =
	 * itemLoc("matter_container/container_bottom_overlay"); String containerBarBase
	 * = "matter_container/container_overlay"; ItemModelBuilder[] matterContainers =
	 * new ItemModelBuilder[MATTER_CONTAINER_MODEL_COUNT]; matterContainers[0] =
	 * layeredBuilder("item/matter_container/matter_container0", Parent.GENERATED,
	 * containerBase, containerStripe); for(int i = 1; i <
	 * MATTER_CONTAINER_MODEL_COUNT; i++) { matterContainers[i] =
	 * layeredBuilder("item/matter_container/matter_container" + i,
	 * Parent.GENERATED, containerBase, containerStripe, itemLoc(containerBarBase +
	 * (i - 1))); } for(RegistryObject<Item> container :
	 * ItemRegistry.ITEM_MATTER_CONTAINERS.getAll()) {
	 * if(((ItemMatterContainer)container.get()).container ==
	 * ContainerType.CREATIVE) { withExistingParent(name(container),
	 * Parent.CREATIVE_MATTER_CONTAINER.loc()); } else { ItemModelBuilder bat =
	 * withExistingParent(name(container), Parent.MATTER_CONTAINER.loc()); for(int i
	 * = 1; i < MATTER_CONTAINER_MODEL_COUNT; i++) { bat =
	 * bat.override().model(matterContainers[i]).predicate(ClientRegister.CHARGE,
	 * (float)i).end(); } } } }
	 */
	private void layeredItem(RegistryObject<Item> item, Parent parent, ResourceLocation... textures) {
		layeredItem(name(item), parent, textures);
	}
	
	private void layeredItem(Item item, Parent parent, ResourceLocation... textures) {
		layeredItem(name(item), parent, textures);
	}

	private void layeredItem(String name, Parent parent, ResourceLocation... textures) {
		layeredBuilder(name, parent, textures);
	}

	private void toggleableItem(RegistryObject<Item> item, String toggle, Parent parentOff, Parent parentOn,
			ResourceLocation[] offText, ResourceLocation[] onText) {
		toggleableItem(name(item), toggle, parentOff, parentOn, offText, onText);
	}

	private void toggleableItem(String name, String toggle, Parent parentOff, Parent parentOn,
			ResourceLocation[] offText, ResourceLocation[] onText) {
		ItemModelBuilder off = layeredBuilder(name, parentOff, offText);
		ItemModelBuilder on = layeredBuilder(name + toggle, parentOn, onText);
		off.override().predicate(ClientRegister.ON, 1.0F).model(on).end();
	}

	private ItemModelBuilder layeredBuilder(String name, Parent parent, ResourceLocation... textures) {
		if (textures == null || textures.length == 0) {
			throw new UnsupportedOperationException("You need to provide at least one texture");
		}
		ItemModelBuilder builder = withExistingParent(name, parent.loc());
		int counter = 0;
		for (ResourceLocation location : textures) {
			builder.texture("layer" + counter, location);
			counter++;
		}
		return builder;
	}

	private ItemModelBuilder getObjModel(String name, String modelLoc, ResourceLocation texture) {
		return getBuilder("item/" + name).customLoader(ObjModelBuilder::begin)
				.modelLocation(modLoc("models/" + modelLoc + ".obj")).flipV(true).end().texture("texture0", texture);
	}

	private ResourceLocation itemLoc(String texture) {
		return modLoc("item/" + texture);
	}

	private String name(RegistryObject<Item> item) {
		return name(item.get());
	}
	
	private String name(Item item) {
		return ForgeRegistries.ITEMS.getKey(item).getPath();
	}

	private static enum Parent {

		GENERATED(true), HANDHELD(true), BATTERY("item/battery/battery0"), CREATIVE_BATTERY("item/battery/battery5"),
		MATTER_CONTAINER("item/matter_container/matter_container0"),
		CREATIVE_MATTER_CONTAINER("item/matter_container/matter_container8");

		private final boolean isVanilla;
		private final String loc;

		private Parent(boolean isVanilla) {
			this.isVanilla = isVanilla;
			loc = "";
		}

		private Parent(String loc) {
			isVanilla = false;
			this.loc = loc;
		}

		public ResourceLocation loc() {
			return isVanilla ? new ResourceLocation(toString().toLowerCase())
					: new ResourceLocation(References.ID, loc);
		}
	}

}
