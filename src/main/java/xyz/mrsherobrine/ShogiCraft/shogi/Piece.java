package xyz.mrsherobrine.ShogiCraft.shogi;

import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class Piece {

    private String type;
    private UUID owner;
    private ArmorStand armorStand;
    private boolean promotion = false;

    public Piece(UUID owner, ArmorStand armorStand) {
        this.armorStand = armorStand;
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String newType) {
        this.type = newType;
    }

    public UUID getOwner() {
        return owner;
    }

    public boolean isPromoted() {
        return promotion;
    }

    public void setPromoted(boolean promote) {
        promotion = promote;
        if (promote) {
            type = "!" + type;
            //TODO handle textures for promotion
        }
    }

    public ArmorStand getEntity() {
        return armorStand;
    }

    public void setEntity(ArmorStand as) {
        this.armorStand = as;
    }

}