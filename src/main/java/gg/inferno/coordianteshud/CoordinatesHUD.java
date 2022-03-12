package gg.inferno.coordianteshud;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;


@Mod(value=CoordinatesHUD.MOD_ID)
@OnlyIn(Dist.CLIENT)
public class CoordinatesHUD extends Screen{

    public static final String MOD_ID = "coordinateshud";
    
    private static final KeyMapping ToggleCoordsHUDKey = new KeyMapping("key.CoordinatesHUD.ToggleCoordsHUD", KeyConflictContext.UNIVERSAL, InputConstants.Type.KEYSYM, InputConstants.KEY_M, KeyMapping.CATEGORY_INTERFACE);
    private boolean displayOverlay;

    public static Logger log = LogManager.getLogger();

    public CoordinatesHUD(){
        super(NarratorChatListener.NO_TITLE);
        ClientRegistry.registerKeyBinding(ToggleCoordsHUDKey);
        MinecraftForge.EVENT_BUS.register(this);
        this.displayOverlay = true;
    }

    // Change the overlay status when the key is pressed
    @SubscribeEvent()
    public void onTick(ClientTickEvent event){
        
        if(ToggleCoordsHUDKey.consumeClick()){

            if(this.displayOverlay){
                this.displayOverlay = false;
            }else{
                this.displayOverlay = true;
            }

        }
    }

    @SubscribeEvent()
    public void onRenderGameOverlay(RenderGameOverlayEvent event){

        if(this.displayOverlay){
            PoseStack matrix = event.getMatrixStack();
            Minecraft mc = Minecraft.getInstance();
            
            // Create a string for position
            Vec3 pos = mc.player.position();
            String pos_data = "[Position]   X: " + Math.round(pos.x * 10)/10 + "   Y: " + Math.round(pos.y * 10)/10 + "   Z: " + Math.round(pos.z * 10)/10;

            // Create a string for direction
            Direction bearing = mc.player.getDirection();
            String dir;
            switch(bearing) {
                case NORTH:
                dir = "Neg Z";
                break;
                case SOUTH:
                dir = "Pos Z";
                break;
                case EAST:
                dir = "Pos X";
                break;
                case WEST:
                dir = "Neg X";
                break;
                default:
                dir = "Invalid";
                }
                String bearing_data = "[Bearing]   " + bearing.getName().toUpperCase() + " (" + dir + ")";

            // Draw the strings
            matrix.pushPose();
            matrix.scale(0.875F, 0.875F, 0.875F);
            drawString(matrix, mc.font, pos_data, 1, 1, 16777215);
            drawString(matrix, mc.font, bearing_data, 1, 10, 16777215);            
            matrix.popPose();
        }

    }
}
