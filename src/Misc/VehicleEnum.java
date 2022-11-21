package Misc;

public enum VehicleEnum {
    PLANE, HELI, BOAT;

    public static VehicleEnum getRandom(){
        return values()[(int)(Math.random()* values().length)];
    }
}
