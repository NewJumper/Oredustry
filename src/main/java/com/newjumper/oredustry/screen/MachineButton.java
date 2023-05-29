package com.newjumper.oredustry.screen;

import com.newjumper.oredustry.network.Messages;
import com.newjumper.oredustry.network.PacketMachineButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;

public class MachineButton {
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public MachineButton(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void onClick(double mouseX, double mouseY, BlockPos blockPos, int index, int value) {
        if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            Messages.INSTANCE.sendToServer(new PacketMachineButton(blockPos, index, value));
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 0.6f, 0.3f));
        }
    }
}
