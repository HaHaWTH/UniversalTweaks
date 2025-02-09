package mod.acgaming.universaltweaks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import mod.acgaming.universaltweaks.bugfixes.blocks.blockoverlay.UTBlockOverlayLists;
import mod.acgaming.universaltweaks.bugfixes.misc.help.UTHelp;
import mod.acgaming.universaltweaks.config.UTConfig;
import mod.acgaming.universaltweaks.core.UTLoadingPlugin;
import mod.acgaming.universaltweaks.mods.abyssalcraft.worlddata.UTWorldDataCapability;
import mod.acgaming.universaltweaks.mods.arcanearchives.UTArcaneArchivesEvents;
import mod.acgaming.universaltweaks.mods.bloodmagic.UTBloodMagicEvents;
import mod.acgaming.universaltweaks.mods.botania.UTBotaniaFancySkybox;
import mod.acgaming.universaltweaks.mods.mekanism.UTMekanismFixes;
import mod.acgaming.universaltweaks.mods.projectred.UTProjectRedWorldEvents;
import mod.acgaming.universaltweaks.mods.tconstruct.UTTConstructEvents;
import mod.acgaming.universaltweaks.mods.tconstruct.oredictcache.UTOreDictCache;
import mod.acgaming.universaltweaks.mods.thaumcraft.UTThaumcraftEvents;
import mod.acgaming.universaltweaks.tweaks.blocks.betterplacement.UTBetterPlacement;
import mod.acgaming.universaltweaks.tweaks.blocks.breakablebedrock.UTBreakableBedrock;
import mod.acgaming.universaltweaks.tweaks.blocks.dispenser.UTBlockDispenser;
import mod.acgaming.universaltweaks.tweaks.entities.attributes.UTAttributes;
import mod.acgaming.universaltweaks.tweaks.items.dragonbreath.UTLeftoverDragonBreath;
import mod.acgaming.universaltweaks.tweaks.items.parry.UTParry;
import mod.acgaming.universaltweaks.tweaks.items.rarity.UTCustomRarity;
import mod.acgaming.universaltweaks.tweaks.items.useduration.UTCustomUseDuration;
import mod.acgaming.universaltweaks.tweaks.misc.endportal.UTEndPortalParallax;
import mod.acgaming.universaltweaks.tweaks.misc.incurablepotions.UTIncurablePotions;
import mod.acgaming.universaltweaks.tweaks.misc.loadsound.UTLoadSound;
import mod.acgaming.universaltweaks.tweaks.misc.pickupnotification.UTPickupNotificationOverlay;
import mod.acgaming.universaltweaks.tweaks.misc.swingthroughgrass.UTSwingThroughGrassLists;
import mod.acgaming.universaltweaks.tweaks.misc.toastcontrol.UTTutorialToast;
import mod.acgaming.universaltweaks.tweaks.performance.autosave.UTAutoSaveOFCompat;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.UTStronghold;
import mod.acgaming.universaltweaks.tweaks.world.stronghold.worldgen.SafeStrongholdWorldGenerator;
import mod.acgaming.universaltweaks.util.UTPacketHandler;
import mod.acgaming.universaltweaks.util.compat.UTObsoleteModsHandler;

@Mod(modid = UniversalTweaks.MODID, name = UniversalTweaks.NAME, version = UniversalTweaks.VERSION, acceptedMinecraftVersions = "[1.12.2]", dependencies = UniversalTweaks.DEPENDENCIES)
public class UniversalTweaks
{
    public static final String MODID = "universaltweaks";
    public static final String NAME = "Universal Tweaks";
    public static final String VERSION = "1.12.2-1.8.0";
    public static final String DEPENDENCIES = "required-after:mixinbooter@[8.0,);required-after:configanytime;"
        + "after:abyssalcraft;"
        + "after:actuallyadditions;"
        + "after:aoa3;"
        + "after:arcanearchives;"
        + "after:biomesoplenty;"
        + "after:bloodmagic;"
        + "after:botania;"
        + "after:chisel;"
        + "after:cofhcore;"
        + "after:contenttweaker;"
        + "after:element;"
        + "after:elenaidodge2;"
        + "after:enderio;"
        + "after:epicsiegemod;"
        + "after:erebus;"
        + "after:extratrees;"
        + "after:extrautils2;"
        + "after:farlanders;"
        + "after:forestry;"
        + "after:ic2;"
        + "after:industrialforegoing;"
        + "after:infernalmobs;"
        + "after:ironbackpacks;"
        + "after:mekanism;"
        + "after:netherchest;"
        + "after:netherrocks;"
        + "after:nuclearcraft;"
        + "after:projectred-exploration;"
        + "after:quark;"
        + "after:roost;"
        + "after:spiceoflife;"
        + "after:storagedrawers;"
        + "after:tcomplement;"
        + "after:tconstruct;"
        + "after:thaumcraft;"
        + "after:thaumicwonders;"
        + "after:thermalexpansion;"
        + "after:tp";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        UTPacketHandler.init();
        if (UTConfig.TWEAKS_ENTITIES.ATTRIBUTES.utAttributesToggle) UTAttributes.utSetAttributes();
        UTAutoSaveOFCompat.updateOFConfig();
        if (UTConfig.TWEAKS_WORLD.utStrongholdToggle) GameRegistry.registerWorldGenerator(new SafeStrongholdWorldGenerator(), Integer.MAX_VALUE);
        if (Loader.isModLoaded("tconstruct") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.preInit();
        if (Loader.isModLoaded("abyssalcraft") && UTConfig.MOD_INTEGRATION.ABYSSALCRAFT.utOptimizedItemTransferToggle) UTWorldDataCapability.register();
        if (UTConfig.TWEAKS_MISC.utSkipRegistryScreenToggle) System.setProperty("fml.queryResult", "confirm");
        LOGGER.info(NAME + " pre-initialized");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        if (UTConfig.TWEAKS_WORLD.utStrongholdToggle) MinecraftForge.TERRAIN_GEN_BUS.register(new UTStronghold());
        if (Loader.isModLoaded("arcanearchives") && UTConfig.MOD_INTEGRATION.ARCANE_ARCHIVES.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTArcaneArchivesEvents());
        if (Loader.isModLoaded("bloodmagic") && UTConfig.MOD_INTEGRATION.BLOOD_MAGIC.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTBloodMagicEvents());
        if (Loader.isModLoaded("mekanism") && UTConfig.MOD_INTEGRATION.MEKANISM.utDuplicationFixesToggle) UTMekanismFixes.fixBinRecipes();
        if (Loader.isModLoaded("projectred-exploration") && UTConfig.MOD_INTEGRATION.PROJECTRED.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTProjectRedWorldEvents());
        if (Loader.isModLoaded("tconstruct") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTTConstructEvents());
        if (Loader.isModLoaded("thaumcraft") && UTConfig.MOD_INTEGRATION.THAUMCRAFT.utDuplicationFixesToggle) MinecraftForge.EVENT_BUS.register(new UTThaumcraftEvents());
        LOGGER.info(NAME + " initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void initClient(FMLInitializationEvent event)
    {
        if (UTConfig.BUGFIXES_BLOCKS.BLOCK_OVERLAY.utBlockOverlayToggle) UTBlockOverlayLists.initLists();
        if (UTConfig.TWEAKS_BLOCKS.BETTER_PLACEMENT.utBetterPlacementToggle) MinecraftForge.EVENT_BUS.register(UTBetterPlacement.class);
        if (UTConfig.TWEAKS_MISC.utEndPortalParallaxToggle) UTEndPortalParallax.initRenderer();
        if (UTConfig.TWEAKS_MISC.PICKUP_NOTIFICATION.utPickupNotificationToggle) UTPickupNotificationOverlay.init();
        if (Loader.isModLoaded("botania")) MinecraftForge.EVENT_BUS.register(UTBotaniaFancySkybox.class);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        if (UTConfig.TWEAKS_BLOCKS.BLOCK_DISPENSER.utBlockDispenserToggle) UTBlockDispenser.initBlockList();
        if (UTConfig.TWEAKS_BLOCKS.BREAKABLE_BEDROCK.utBreakableBedrockToggle) UTBreakableBedrock.initToolList();
        if (UTConfig.TWEAKS_MISC.SWING_THROUGH_GRASS.utSwingThroughGrassToggle) UTSwingThroughGrassLists.initLists();
        if (UTConfig.TWEAKS_MISC.INCURABLE_POTIONS.utIncurablePotionsToggle) UTIncurablePotions.initPotionList();
        if (UTConfig.TWEAKS_ITEMS.utLeftoverBreathBottleToggle) UTLeftoverDragonBreath.postInit();
        if (UTConfig.TWEAKS_ITEMS.utCustomRarities.length > 0) UTCustomRarity.initItemRarityMap();
        if (UTConfig.TWEAKS_ITEMS.utCustomUseDurations.length > 0) UTCustomUseDuration.initItemUseMaps();
        if (UTConfig.TWEAKS_ITEMS.PARRY.utParryToggle) UTParry.initProjectileList();
        LOGGER.info(NAME + " post-initialized");
    }

    @SideOnly(Side.CLIENT)
    @Mod.EventHandler
    public void postInitClient(FMLPostInitializationEvent event)
    {
        if (UTConfig.TWEAKS_MISC.LOAD_SOUNDS.utLoadSoundMode != UTConfig.TweaksMiscCategory.LoadSoundsCategory.EnumSoundModes.NOTHING) UTLoadSound.initLists();
        if (UTConfig.TWEAKS_MISC.TOAST_CONTROL.utToastControlTutorialToggle) UTTutorialToast.utTutorialToast();
        if (Loader.isModLoaded("botania")) UTBotaniaFancySkybox.initDimList();
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event)
    {
        if (UTConfig.BUGFIXES_MISC.utHelpToggle) UTHelp.onServerStarting(event);
    }

    @Mod.EventHandler
    public void onServerStarted(FMLServerStartedEvent event)
    {
        if (UTConfig.BUGFIXES_MISC.utHelpToggle) UTHelp.onServerStarted(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event)
    {
        if (Loader.isModLoaded("tconstruct") && UTConfig.MOD_INTEGRATION.TINKERS_CONSTRUCT.utTConOreDictCacheToggle) UTOreDictCache.onLoadComplete();
        if (UTConfig.DEBUG.utLoadingTimeToggle) LOGGER.info("The game loaded in approximately {} seconds", (System.currentTimeMillis() - UTLoadingPlugin.launchTime) / 1000F);
        if (UTObsoleteModsHandler.showObsoleteMods && UTObsoleteModsHandler.obsoleteModsMessage().size() > 5 && !UTConfig.DEBUG.utBypassIncompatibilityToggle)
        {
            for (String line : UTObsoleteModsHandler.obsoleteModsMessage())
            {
                if (!line.equals("")) UniversalTweaks.LOGGER.warn(line);
            }
        }
    }
}