package net.steveson.immersivetrims.datagen;

import com.google.common.base.Preconditions;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.steveson.immersivetrims.ImmersiveTrimsMod;
import net.steveson.immersivetrims.trim.ModTrimMaterials;


import java.util.LinkedHashMap;

public class VanillaItemModelProvider extends ItemModelProvider {
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

    public VanillaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, "minecraft", existingFileHelper);
    }

    @Override
    protected void registerModels() {
        trimmedVanillaArmorItem(Items.TURTLE_HELMET);

        trimmedVanillaArmorItem(Items.LEATHER_HELMET);
        trimmedVanillaArmorItem(Items.LEATHER_CHESTPLATE);
        trimmedVanillaArmorItem(Items.LEATHER_LEGGINGS);
        trimmedVanillaArmorItem(Items.LEATHER_BOOTS);
        trimmedVanillaArmorItem(Items.CHAINMAIL_HELMET);
        trimmedVanillaArmorItem(Items.CHAINMAIL_CHESTPLATE);
        trimmedVanillaArmorItem(Items.CHAINMAIL_LEGGINGS);
        trimmedVanillaArmorItem(Items.CHAINMAIL_BOOTS);
        trimmedVanillaArmorItem(Items.IRON_HELMET);
        trimmedVanillaArmorItem(Items.IRON_CHESTPLATE);
        trimmedVanillaArmorItem(Items.IRON_LEGGINGS);
        trimmedVanillaArmorItem(Items.IRON_BOOTS);
        trimmedVanillaArmorItem(Items.GOLDEN_HELMET);
        trimmedVanillaArmorItem(Items.GOLDEN_CHESTPLATE);
        trimmedVanillaArmorItem(Items.GOLDEN_LEGGINGS);
        trimmedVanillaArmorItem(Items.GOLDEN_BOOTS);
        trimmedVanillaArmorItem(Items.DIAMOND_HELMET);
        trimmedVanillaArmorItem(Items.DIAMOND_CHESTPLATE);
        trimmedVanillaArmorItem(Items.DIAMOND_LEGGINGS);
        trimmedVanillaArmorItem(Items.DIAMOND_BOOTS);
        trimmedVanillaArmorItem(Items.NETHERITE_HELMET);
        trimmedVanillaArmorItem(Items.NETHERITE_CHESTPLATE);
        trimmedVanillaArmorItem(Items.NETHERITE_LEGGINGS);
        trimmedVanillaArmorItem(Items.NETHERITE_BOOTS);
    }




    // Shoutout to El_Redstoniano for making this
    private void trimmedVanillaArmorItem(Item itemCurrentArmor) {
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


                String[] armorMatNameSplit = armorItem.getMaterial().getRegisteredName().split(":");
                boolean overrideDarker = armorMatNameSplit.length == 2 && armorMatNameSplit[1].equalsIgnoreCase(trimMaterial.location().getPath());

                if (overrideDarker){
                    trimPath = trimPath + "_darker";
                    currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_darker_trim";
                }

                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath.replaceFirst("trim_", "trim_immersiveengineering_")); // minecraft namespace


                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");


//                String targetResourcePath = currentTrimName.replaceFirst("item/", "item/minecraft/");
//                targetResourcePath.replaceFirst("item/", "item/minecraft/");
//                ResourceLocation outputLoc = this.extendWithFolder(currentTrimName.contains(":") ? ResourceLocation.parse(currentTrimName) : ResourceLocation.fromNamespaceAndPath(this.modid, currentTrimName
//                        //                + "/minecraft"
//                ));

                ResourceLocation outputLoc = ResourceLocation.fromNamespaceAndPath(MOD_ID, "item/minecraft/" + ResourceLocation.parse(currentTrimName).getPath()
                        //                + "/minecraft"
                );

                // Trimmed armorItem files
                if (!isVanilla) {
                    String[] trimNameSplit = currentTrimName.split(":");
                    if(trimNameSplit.length == 2) {
                        currentTrimName = MOD_ID + ":" + trimNameSplit[1];
                    }

                    System.out.println(currentTrimName);
                    System.out.println(outputLoc);

                    if (armorItem.getMaterial() == ArmorMaterials.LEATHER) {
                        getBuilder(currentTrimName, outputLoc)
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                                .texture("layer1", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath() + "_overlay")
                                .texture("layer2", trimResLoc);
                    }
                    else {
                        getBuilder(currentTrimName, outputLoc)
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                                .texture("layer1", trimResLoc);
                    }
                }

                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);


                // Non-trimmed armorItem file (normal variant)
                String[] armorPathSplit = armorItemPath.split(":");
//                String armorPathOnly = armorItemPath;
                if (armorPathSplit.length == 2) {
                    armorItemPath = armorPathSplit[1];
                }

                if (armorItem.getMaterial() == ArmorMaterials.LEATHER) {
                    this.withExistingParent(armorItemPath,
                                    mcLoc("item/generated"))
                            .override()
                            .model(new ModelFile.UncheckedModelFile(isVanilla? trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath() : outputLoc.toString()))
                            .predicate(mcLoc("trim_type"), trimValue).end()
                            .texture("layer0",
                                    ResourceLocation.fromNamespaceAndPath("minecraft",
                                            "item/" + armorItemPath))
                            .texture("layer1",
                                    ResourceLocation.fromNamespaceAndPath("minecraft",
                                            "item/" + armorItemPath + "_overlay"));
                }
                else {
                    this.withExistingParent(armorItemPath,
                                    mcLoc("item/generated"))
                            .override()
                            .model(new ModelFile.UncheckedModelFile(isVanilla? trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath() : outputLoc.toString()))
                            .predicate(mcLoc("trim_type"), trimValue).end()
                            .texture("layer0",
                                    ResourceLocation.fromNamespaceAndPath("minecraft",
                                            "item/" + armorItemPath));
                }
            });
        }
    }

//    private ResourceLocation extendWithFolder(ResourceLocation rl) {
//        if (rl.getPath().contains("/")) {
//            return rl;
//        } else {
//            String var10000 = rl.getNamespace();
//            String var10001 = this.folder;
//            return ResourceLocation.fromNamespaceAndPath(var10000, var10001 + "/" + rl.getPath());
//        }
//    }


//    @Override
    public ItemModelBuilder getBuilder(String path, ResourceLocation outputLoc) {
        Preconditions.checkNotNull(path, "Path must not be null");
//        ResourceLocation outputLoc = this.extendWithFolder(path.contains(":") ? ResourceLocation.parse(path) : ResourceLocation.fromNamespaceAndPath(this.modid, path
//                + "/minecraft"
//        ));
        this.existingFileHelper.trackGenerated(outputLoc, MODEL);
        return (ItemModelBuilder)(this.generatedModels.computeIfAbsent(outputLoc, this.factory));
//        return super.getBuilder(path);
    }
}
