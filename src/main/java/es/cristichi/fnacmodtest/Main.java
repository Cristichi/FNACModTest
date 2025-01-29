package es.cristichi.fnacmodtest;

import es.cristichi.fnac.FnacMain;
import es.cristichi.fnac.anim.*;
import es.cristichi.fnac.cams.CameraMap;
import es.cristichi.fnac.cams.RestaurantCamMapFactory;
import es.cristichi.fnac.cnight.CustomNightAnimData;
import es.cristichi.fnac.cnight.CustomNightAnimFactory;
import es.cristichi.fnac.cnight.CustomNightAnimRegistry;
import es.cristichi.fnac.exception.NightException;
import es.cristichi.fnac.exception.ResourceException;
import es.cristichi.fnac.gui.MenuJC;
import es.cristichi.fnac.gui.NightJC;
import es.cristichi.fnac.io.NightProgress;
import es.cristichi.fnac.io.Resources;
import es.cristichi.fnac.io.Settings;
import es.cristichi.fnac.loading.LoadRunnable;
import es.cristichi.fnac.nights.NightFactory;
import es.cristichi.fnac.nights.NightRegistry;
import es.cristichi.fnacmodtest.anims.Spyro;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // We should not change these values during loading, we do it right away.
        FnacMain.GAME_TITLE = "FNAC: Official modded example (by Cristichi)";
        //FnacMain.DEBUG_ALLNIGHTS = false;
        
        final RestaurantCamMapFactory restaurantCamMapFactory = new RestaurantCamMapFactory();
        
        /* We place each thing we need to load in an individual LoadRunnable instance, so that each of them
        * load while the player is seeing the loading screen, and also so that they load in individual Threads.*/
        LoadRunnable[] loadingRunnables = new LoadRunnable[]{
                () -> CustomNightAnimRegistry.registerAnimatronic(new CustomNightAnimFactory<Spyro>("Spyro", """
                        Spyro goes to the right door. If he is at "bathrooms" or "cam3", he teleports \
                        directly to the right door to scare you.""", 20, Resources.loadImage("anim/spyro/portrait.png"),
                        new String[]{"storage", "cam1"}) {
                    @Override
                    public Spyro generate(CustomNightAnimData data, Random rng) throws ResourceException {
                        try {
                            return new Spyro("Spyro", Map.of(0, data.ai()),
                                    List.of(List.of("cam1", "cam3", "rightDoor"), List.of("cam2", "cam4", "leftDoor"),
                                            List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                                            List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")),
                                    rng);
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }),
                /* This replaces the Tutorial, because the Tutorial is usually loaded on 0 comlpeted Nights. */
                () -> NightRegistry.registerNight(new NightFactory(new MenuJC.ItemInfo("n0_spyro", "Tutorial with Spyro", "Tutorial", null)) {
                    
                    @Override
                    public Availability getAvailability(NightProgress.SaveFile saveFile) {
                        return new Availability(saveFile.completedNights().isEmpty(), false);
                    }
                    
                    @Override
                    public NightJC createNight(Settings settings, Jumpscare powerOutage,
                                               Random rng) throws IOException, NightException {
                        CameraMap nightMap = restaurantCamMapFactory.generate();
                        nightMap.addCamAnimatronics("kitchen", new Spyro("spyro", Map.of(0,10), List.of(
                                List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                                List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                        ), rng));
                        return new NightJC("Spyro's Night", settings.getFps(), nightMap,
                                Resources.loadImage("night/n1/paper.png"), powerOutage, rng, 90, 6,
                                .45f, Resources.loadSound("night/general/completed.wav"), null, null);
                    }
                }),
                // Night 1.
                () -> NightRegistry.registerNight(new NightFactory(new MenuJC.ItemInfo("n1_spyro", "Continue with Spyro", "Night 1 with Spyro", null)) {
                    
                    @Override
                    public Availability getAvailability(NightProgress.SaveFile saveFile) {
                        return new Availability(saveFile.completedNights().size()==1, false);
                    }
                    
                    @Override
                    public NightJC createNight(Settings settings, Jumpscare powerOutage,
                                               Random rng) throws IOException, NightException {
                        CameraMap nightMap = restaurantCamMapFactory.generate();
                        nightMap.addCamAnimatronics("kitchen", new Spyro("spyro", Map.of(0,10), List.of(
                                List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                                List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                        ), rng));
                        return new NightJC("Spyro's Night 1", settings.getFps(), nightMap,
                                Resources.loadImage("night/n1/paper.png"), powerOutage, rng, 90, 6,
                                .45f, Resources.loadSound("night/general/completed.wav"), null, null);
                    }
                }),
                // Night 2.
                () -> NightRegistry.registerNight(new NightFactory(new MenuJC.ItemInfo("n2_spyro", "Continue with Spyro", "Night 2 with Spyro",
                                Resources.loadImage("night/n2/loading.jpg"))) {
                    
                    @Override
                    public Availability getAvailability(NightProgress.SaveFile saveFile) {
                        return new Availability(saveFile.completedNights().size()==2, false);
                    }
                    
                    @Override
                    public NightJC createNight(Settings settings, Jumpscare powerOutage,
                                               Random rng) throws IOException, NightException {
                        AnimatronicDrawing bob = new RoamingBob("Bob", Map.of(0,4), false, false,
                                List.of("corridor 2", "corridor 4", "bathrooms", "offices"), rng);
                        
                        AnimatronicDrawing maria = new RoamingMaria("Maria", Map.of(1,1, 4,2), false, false,
                                List.of("corridor 1", "corridor 3", "staff lounge"), rng);
                        
                        AnimatronicDrawing paco = new Paco("Paco", Map.of(0,4, 4,5), false, true,
                                List.of(
                                        List.of("kitchen", "dining area", "corridor 1", "corridor 3", "leftDoor"),
                                        List.of("kitchen", "dining area", "corridor 2", "corridor 4", "rightDoor")
                                ), rng);
                        
                        AnimatronicDrawing crisIsClose = new RoamingCris("Cris", Map.of(0,1, 4,2, 5,3), true, false,
                                List.of("kitchen", "storage", "main stage", "staff lounge", "bathrooms"), rng);
                        
                        Spyro spyro = new Spyro("spyro", Map.of(0,2, 3,5),
                                List.of(
                                        List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                                        List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                                ), rng);
                        
                        CameraMap nightMap = restaurantCamMapFactory.generate();
                        nightMap.addCamAnimatronics("kitchen", paco, spyro);
                        nightMap.addCamAnimatronics("storage", bob);
                        nightMap.addCamAnimatronics("offices", maria);
                        nightMap.addCamAnimatronics("staff lounge", crisIsClose);
                        
                        return new NightJC("Spyro's Night 2", settings.getFps(), nightMap,
                                Resources.loadImage("night/n2/paper.png"), powerOutage, rng, 90, 6,
                                0.45f, Resources.loadSound("night/general/completed.wav"), null, null);
                    }
                })
        };
        
        // We start the game, specifying the methods that must be run during loading.
        new es.cristichi.fnac.FnacMain().run("FNAC with Spyro", loadingRunnables, null, null);
    }
}