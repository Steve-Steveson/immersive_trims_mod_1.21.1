package net.steveson.immersivetrims.datagen;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.common.register.IEItems;
import com.google.common.base.Preconditions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;
import net.steveson.immersivetrims.ImmersiveTrimsMod;
import net.steveson.immersivetrims.trim.ModTrimMaterials;

import java.util.LinkedHashMap;

public class IEAddonItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(ModTrimMaterials.CONSTANTAN, 0.11F);
        trimMaterials.put(ModTrimMaterials.ELECTRUM, 0.12F);
        trimMaterials.put(ModTrimMaterials.ALUMINUM, 0.13F);
        trimMaterials.put(ModTrimMaterials.URANIUM, 0.14F);
        trimMaterials.put(ModTrimMaterials.LEAD, 0.15F);
        trimMaterials.put(ModTrimMaterials.STEEL, 0.16F);
        trimMaterials.put(ModTrimMaterials.SILVER, 0.17F);
        trimMaterials.put(ModTrimMaterials.NICKEL, 0.18F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public IEAddonItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ImmersiveEngineering.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        trimmedSteelArmorItem(IEItems.Tools.STEEL_ARMOR.get(ArmorItem.Type.HELMET).get());
        trimmedSteelArmorItem(IEItems.Tools.STEEL_ARMOR.get(ArmorItem.Type.CHESTPLATE).get());
        trimmedSteelArmorItem(IEItems.Tools.STEEL_ARMOR.get(ArmorItem.Type.LEGGINGS).get());
        trimmedSteelArmorItem(IEItems.Tools.STEEL_ARMOR.get(ArmorItem.Type.BOOTS).get());

        trimmedFaradayArmorItem(IEItems.Misc.FARADAY_SUIT.get(ArmorItem.Type.HELMET).get());
        trimmedFaradayArmorItem(IEItems.Misc.FARADAY_SUIT.get(ArmorItem.Type.CHESTPLATE).get());
        trimmedFaradayArmorItem(IEItems.Misc.FARADAY_SUIT.get(ArmorItem.Type.LEGGINGS).get());
        trimmedFaradayArmorItem(IEItems.Misc.FARADAY_SUIT.get(ArmorItem.Type.BOOTS).get());
    }



    // Shoutout to El_Redstoniano for making this
    private void trimmedFaradayArmorItem(Item itemCurrentArmor) {
        final String MOD_ID = ImmersiveTrimsMod.MOD_ID; // Change this to your mod id

        if(itemCurrentArmor instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                boolean isVanilla = (trimValue * 1000) % 100 == 0;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";


//                String[] armorMatNameSplit = armorItem.getMaterial().getRegisteredName().split(":");
//                boolean overrideDarker = armorMatNameSplit.length == 2 && armorMatNameSplit[1].equalsIgnoreCase(trimMaterial.location().getPath());
//
//                if (overrideDarker){
//                    trimPath = trimPath + "_darker";
//                    currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_darker_trim";
//                }

                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                if (!isVanilla) {
                    trimResLoc = ResourceLocation.parse(trimPath.replaceFirst("trim_", "trim_immersiveengineering_"));
                }



                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                ResourceLocation outputLoc = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/immersiveengineering/" + ResourceLocation.parse(currentTrimName).getPath());



                // Trimmed armorItem files

                String[] trimNameSplit = currentTrimName.split(":");
                if(trimNameSplit.length == 2) {
                    currentTrimName = MOD_ID + ":" + trimNameSplit[1];
                }

                getBuilder(currentTrimName, outputLoc)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);



                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(armorItemResLoc.getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(outputLoc.toString()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID,
                                        "item/" + armorItemResLoc.getPath()));
            });
        }
    }




    // Shoutout to El_Redstoniano for making this
    private void trimmedSteelArmorItem(Item itemCurrentArmor) {
        final String MOD_ID = ImmersiveTrimsMod.MOD_ID; // Change this to your mod id

        if(itemCurrentArmor instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                boolean isVanilla = (trimValue * 1000) % 100 == 0;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";


//                String[] armorMatNameSplit = armorItem.getMaterial().getRegisteredName().split(":");
//                boolean overrideDarker = armorMatNameSplit.length == 2 && armorMatNameSplit[1].equalsIgnoreCase(trimMaterial.location().getPath());
//
//                if (overrideDarker){
//                    trimPath = trimPath + "_darker";
//                    currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_darker_trim";
//                }

                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath.replaceFirst("trim_", "trim_immersiveengineering_")); // minecraft namespace




                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                ResourceLocation outputLoc = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/immersiveengineering/" + ResourceLocation.parse(currentTrimName).getPath());



                // Trimmed armorItem files
                if (!isVanilla) {
                    String[] trimNameSplit = currentTrimName.split(":");
                    if(trimNameSplit.length == 2) {
                        currentTrimName = MOD_ID + ":" + trimNameSplit[1];
                    }

                    getBuilder(currentTrimName, outputLoc)
                            .parent(new ModelFile.UncheckedModelFile("item/generated"))
                            .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                            .texture("layer1", trimResLoc);
                }


                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(armorItemResLoc.getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(isVanilla? trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath() : outputLoc.toString()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID,
                                        "item/" + armorItemResLoc.getPath()));
            });
        }
    }




    public ItemModelBuilder getBuilder(String path, ResourceLocation outputLoc) {
        Preconditions.checkNotNull(path, "Path must not be null");

        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return (ItemModelBuilder)(this.generatedModels.computeIfAbsent(outputLoc, this.factory));
    }
}
