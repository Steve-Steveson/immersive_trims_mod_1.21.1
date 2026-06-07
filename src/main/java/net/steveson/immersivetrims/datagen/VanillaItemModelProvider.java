package net.steveson.immersivetrims.datagen;

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
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.steveson.immersivetrims.ImmersiveTrimsMod;


import java.util.LinkedHashMap;

public class VanillaItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
//        trimMaterials.put(ModTrimMaterials.OPAL, 0.91F);
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
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace




                // This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                // avoid an IllegalArgumentException
                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                if (!isVanilla) {
                    String[] trimNameSplit = currentTrimName.split(":");
                    if(trimNameSplit.length == 2) {
                        currentTrimName = MOD_ID + ":" + trimNameSplit[1];
                    }

                    if (armorItem.getMaterial() == ArmorMaterials.LEATHER) {
                        getBuilder(currentTrimName)
                                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                                .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                                .texture("layer1", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath() + "_overlay")
                                .texture("layer2", trimResLoc);
                    }
                    else {
                        getBuilder(currentTrimName)
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
                            .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
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
                            .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace() + ":item/" + trimNameResLoc.getPath()))
                            .predicate(mcLoc("trim_type"), trimValue).end()
                            .texture("layer0",
                                    ResourceLocation.fromNamespaceAndPath("minecraft",
                                            "item/" + armorItemPath));
                }
            });
        }
    }
}
