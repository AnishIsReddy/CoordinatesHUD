package gg.inferno.coordianteshud;

import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.common.Mod;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;


@Mod(value=CoordinatesHUD.MOD_ID)
public class CoordinatesHUD extends Screen{

    public static final String MOD_ID = "coordinateshud";
    public static final IIngameOverlay OVERLAY = OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HUD_TEXT_ELEMENT, MOD_ID + ":overlay", CoordinatesHUD::RenderLocation);    
    
    public CoordinatesHUD(){
        super(NarratorChatListener.NO_TITLE);
    }

    public static void RenderLocation(ForgeIngameGui gui, PoseStack matrix, float partialTicks, int width, int height){
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
