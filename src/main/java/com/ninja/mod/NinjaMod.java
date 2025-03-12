package com.ninja.mod;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = NinjaMod.MODID, version = NinjaMod.VERSION)
public class NinjaMod
{
    public static final String MODID = "ninjamod";
    public static final String VERSION = "1.0";
    private static double lastHitPosX = 0;
    private static double lastHitPosY = 0;
    private static double lastHitPosZ = 0;

    // Definindo a tecla SHIFT
    private static KeyBinding shiftKeyBinding;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // Registrando os eventos para que eles funcionem
        MinecraftForge.EVENT_BUS.register(this);

        // Registrando a tecla SHIFT
        shiftKeyBinding = new KeyBinding("key.shift", Keyboard.KEY_LSHIFT, "key.categories.misc");

        // Registrando o KeyBinding no ClientRegistry
        ClientRegistry.registerKeyBinding(shiftKeyBinding);

        System.out.println("Mod inicializado");
    }

    // Detecta quando um jogador acerta outro jogador
    @SubscribeEvent
    public void onPlayerAttack(AttackEntityEvent event)
    {
        if (event.target instanceof EntityPlayer)
        {
            EntityPlayer attackedPlayer = (EntityPlayer) event.target;
            // Capturando a posição usando posX, posY, posZ
            lastHitPosX = attackedPlayer.posX;
            lastHitPosY = attackedPlayer.posY;
            lastHitPosZ = attackedPlayer.posZ;
            System.out.println("Última posição do jogador atingido: " + lastHitPosX + ", " + lastHitPosY + ", " + lastHitPosZ);
        }
    }

    // Detecta os ticks do cliente
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event)
    {
        if (shiftKeyBinding.isPressed()) // Verifica se a tecla SHIFT foi pressionada
        {
            teleportToLastHitPlayer();
        }
    }

    // Teleporta o jogador para a posição do último jogador atingido
    private void teleportToLastHitPlayer()
    {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;  // Usando 'thePlayer' para acessar o jogador
        if (player != null)
        {
            // Teleportando usando posX, posY, posZ
            player.setPositionAndUpdate(lastHitPosX, lastHitPosY, lastHitPosZ);
            System.out.println("Teletransportado para: " + lastHitPosX + ", " + lastHitPosY + ", " + lastHitPosZ);
        }
    }
}
