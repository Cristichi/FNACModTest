package es.cristichi.fnacmodtest;

import es.cristichi.fnac.cnight.CustomNightRegistry;
import es.cristichi.fnac.exception.NightException;
import es.cristichi.fnac.exception.ResourceException;
import es.cristichi.fnac.gui.MenuJC;
import es.cristichi.fnac.gui.NightJC;
import es.cristichi.fnac.io.Resources;
import es.cristichi.fnac.io.Settings;
import es.cristichi.fnac.obj.Jumpscare;
import es.cristichi.fnac.obj.anim.*;
import es.cristichi.fnac.obj.cams.CrisRestaurantMap;
import es.cristichi.fnac.obj.nights.NightFactory;
import es.cristichi.fnac.obj.nights.NightRegistry;
import es.cristichi.fnacmodtest.anims.Spyro;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        CustomNightRegistry.registerPackage("es.cristichi.fnacmodtest.anims");
        
        /* This makes custom Night available instead of the Tutorial. Since the Custom Night also saves itself
         * as a completed Night, this still allows the rest of the Nights to occur.
         */
        NightRegistry.registerNight(0, null);
        // Night 1.
        NightRegistry.registerNight(1, new NightFactory() {
            @Override
            public MenuJC.Item getItem() {
                return new MenuJC.Item("night1", "Let's play with Spyro", "Night 1", null);
            }
            
            @Override
            public NightJC createNight(Settings settings, Jumpscare powerOutage,
                                       Random rng) throws IOException, NightException {
                CrisRestaurantMap nightMap = new CrisRestaurantMap();
                nightMap.addCamAnimatronics("kitchen", new Spyro("spyro", Map.of(0,10), List.of(
                        List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                        List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                ), rng));
                return new NightJC("Spyro's Night", settings.getFps(), nightMap,
                        Resources.loadImage("night/n1/paper.png"), powerOutage, rng, 20,
                        .45f, Resources.loadSound("night/general/completed.wav"));
            }
        });
        // I copied Night 2 of this version of FNAC from its source files and added Spyro to the kitchen.
        NightRegistry.registerNight(2, new NightFactory() {
            
            @Override
            public MenuJC.Item getItem() throws ResourceException {
                return new MenuJC.Item("n2", "Continue", "Night 2",
                        Resources.loadImage("night/n2/loading.jpg"));
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
                
                CrisRestaurantMap nightMap = new CrisRestaurantMap();
                nightMap.addCamAnimatronics("kitchen", paco, spyro);
                nightMap.addCamAnimatronics("storage", bob);
                nightMap.addCamAnimatronics("offices", maria);
                nightMap.addCamAnimatronics("staff lounge", crisIsClose);
                
                return new NightJC("Night 2", settings.getFps(), nightMap,
                        Resources.loadImage("night/n2/paper.png"), powerOutage, rng, 90, 0.45f,
                        Resources.loadSound("night/general/completed.wav"));
            }
        });
        // All other Nights would be the same.
        
        new es.cristichi.fnac.FnacMain().run();
    }
}