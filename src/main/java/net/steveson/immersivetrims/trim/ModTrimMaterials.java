package net.steveson.immersivetrims.trim;

import blusunrize.immersiveengineering.ImmersiveEngineering;
import blusunrize.immersiveengineering.api.EnumMetals;
import blusunrize.immersiveengineering.common.register.IEItems;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;

import java.util.Map;

public class ModTrimMaterials {
    public static final ResourceKey<TrimMaterial> CONSTANTAN =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "constantan"));
    public static final ResourceKey<TrimMaterial> ELECTRUM =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "electrum"));
    public static final ResourceKey<TrimMaterial> ALUMINUM =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "aluminum"));
    public static final ResourceKey<TrimMaterial> URANIUM =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "uranium"));
    public static final ResourceKey<TrimMaterial> LEAD =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "lead"));
    public static final ResourceKey<TrimMaterial> STEEL =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "steel"));
    public static final ResourceKey<TrimMaterial> SILVER =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "silver"));
    public static final ResourceKey<TrimMaterial> NICKEL =
            ResourceKey.create(Registries.TRIM_MATERIAL, ResourceLocation.fromNamespaceAndPath(ImmersiveEngineering.MODID, "nickel"));

    public static void bootstrap(BootstrapContext<TrimMaterial> pContext) {
//        register(pContext, OPAL, ModItems.OPAL.get(), Style.EMPTY.withColor(14071543), 0.91F, Map.of(ModArmorMaterials.OPAL_ARMOR_MATERIAL, "opal_darker"));
        register(pContext, CONSTANTAN, IEItems.Metals.INGOTS.get(EnumMetals.CONSTANTAN).get(), Style.EMPTY.withColor(16682608), 0.11F);
        register(pContext, ELECTRUM, IEItems.Metals.INGOTS.get(EnumMetals.ELECTRUM).get(), Style.EMPTY.withColor(16765009), 0.12F);
        register(pContext, ALUMINUM, IEItems.Metals.INGOTS.get(EnumMetals.ALUMINUM).get(), Style.EMPTY.withColor(12173507), 0.13F);
        register(pContext, URANIUM, IEItems.Metals.INGOTS.get(EnumMetals.URANIUM).get(), Style.EMPTY.withColor(6253909), 0.14F);
        register(pContext, LEAD, IEItems.Metals.INGOTS.get(EnumMetals.LEAD).get(), Style.EMPTY.withColor(7436690), 0.15F);
        register(pContext, STEEL, IEItems.Metals.INGOTS.get(EnumMetals.STEEL).get(), Style.EMPTY.withColor(8947845), 0.16F);
        register(pContext, SILVER, IEItems.Metals.INGOTS.get(EnumMetals.SILVER).get(), Style.EMPTY.withColor(14609396), 0.17F);
        register(pContext, NICKEL, IEItems.Metals.INGOTS.get(EnumMetals.NICKEL).get(), Style.EMPTY.withColor(12370607), 0.18F);
    }

//    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
//                                 Style style, float itemModelIndex, Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
//        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
//                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style));
//        context.register(trimKey, trimmaterial);
//    }

//    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
//                                 Style style, float itemModelIndex, Map<Holder<ArmorMaterial>, String> overrideArmorMaterials) {
//        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
//                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), overrideArmorMaterials);
//        context.register(trimKey, trimmaterial);


    private static void register(BootstrapContext<TrimMaterial> context, ResourceKey<TrimMaterial> trimKey, Item item,
            Style style, float itemModelIndex) {
        TrimMaterial trimmaterial = TrimMaterial.create(trimKey.location().getPath(), item, itemModelIndex,
                Component.translatable(Util.makeDescriptionId("trim_material", trimKey.location())).withStyle(style), Map.of());
        context.register(trimKey, trimmaterial);
    }
}
