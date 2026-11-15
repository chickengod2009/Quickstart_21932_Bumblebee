package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensors {

    public static class RGB{
        public final float Red;
        public final float Blue;
        public final float Green;

        public RGB(float r, float g, float b){
            this.Red=r;
            this.Blue=b;
            this.Green=g;
        }


        public enum SensorID{
            ONE(0),
            TWO(1),
            THREE(2),
            FOUR(3),
            FIVE(3);

            final int arrayIndex;
            SensorID(int index){
                arrayIndex= index;
            }
            public static SensorID sensorIndex(int index){
                switch (index){
                    case 0:
                        return ONE;
                    case 1:
                        return TWO;
                    case 2:
                        return THREE;
                    case 3:
                        return FOUR;
                    case 4:
                        return FIVE;
                    default:
                        throw new RuntimeException("Not an index");
                }
            }
        }




        public Color assignColor(int noise){

            if ((this.Red + this.Green + this.Blue) < noise) {
                return Color.NA;
            } else if (this.Red > 110 && this.Green > this.Blue) {
                return Color.BLUE;
            } else {
                return Color.RED;
            }
        }

    }
    public enum Color {
        RED(new RGB(255,0,0), 0),
        BLUE(new RGB(0,0,255), 1),
        YELLOW(new RGB(220,190,40), 2),
        NA(null, 3);

        final RGB rgb;
        final int index;
        Color(RGB rgb, int index){
            this.rgb = rgb; this.index = index;
        }


        public RGB getRgb(){return this.rgb;}


        public boolean equalTeamColor(){

            return this.index == Master.getTeam().index;

        }

    }
    ColorSensor firstSensor, secondSensor, thirdSensor, fourthSensor, fifthSensor;
    private Color color1, color2, color3, color4, color5;
    private RGB C1, C2, C3, C4, C5;

    public int ct, noise;
    public ColorSensors(Master master) {
        firstSensor = master.get("topSensor");
        secondSensor = master.get("middleSensor");
        thirdSensor = master.get("bottomSensor");
        fourthSensor = master.get("fourthSensor");
        fifthSensor = master.get("fifthSensor");

        noise = 200;
        //topSensor.setGain(4);
    }

    public void update() {
        C1 = new RGB( firstSensor.red(),
                firstSensor.green(),
                firstSensor.blue());

        C2 = new RGB( secondSensor.red(),
                secondSensor.green(),
                secondSensor.blue());

        C3 = new RGB( thirdSensor.red(),
                thirdSensor.green(),
                thirdSensor.blue());

        C4 = new RGB( fourthSensor.red(),
                fourthSensor.green(),
                fourthSensor.blue());

        C5 = new RGB( fifthSensor.red(),
                fifthSensor.green(),
                fifthSensor.blue());


        //TODO: find parameters for colors
        //FIRST
        color1 = C1.assignColor(noise);
        color2 = C2.assignColor(noise);
        color3 = C3.assignColor(noise);
        color4 = C4.assignColor(noise);
        color5 = C5.assignColor(noise);
    }
    public Color getColor(RGB.SensorID sensor) {

        switch (sensor){
            case ONE:
                return color1;
            case TWO:
                return color2;
            case THREE:
                return color3;
            case FOUR:
                return color4;
            case FIVE:
                return color5;
        }
        return color1;
    }
    public float getR(RGB.SensorID sensor) {
        switch (sensor){
            case ONE:
                return C1.Red;
            case TWO:
                return C2.Red;
            case THREE:
                return C3.Red;
            case FOUR:
                return C4.Red;
            case FIVE:
                return C5.Red;
        }
        return -1;
    }
    public float getG(RGB.SensorID sensor) {
        switch (sensor){
            case ONE:
                return C1.Green;
            case TWO:
                return C2.Green;
            case THREE:
                return C3.Green;
            case FOUR:
                return C4.Green;
            case FIVE:
                return C5.Green;
        }
        return -1;
    }
    public float getB(RGB.SensorID sensor) {
        switch (sensor){
            case ONE:
                return C1.Blue;
            case TWO:
                return C2.Blue;
            case THREE:
                return C3.Blue;
            case FOUR:
                return C4.Blue;
            case FIVE:
                return C5.Blue;
        }
        return -1;
    }

    public int hasBall(RGB.SensorID sensorID) {

        Color[] sensors = {color1,color2,color3};
        return (sensors[sensorID.arrayIndex] != Color.NA) ? 0 : 1;
    }
    public boolean bladderFullOfPollen() {
        int sum=0;

        for(int i = 0; i <3; ++i){
            sum += this.hasBall(RGB.SensorID.sensorIndex(i));
        }

        return sum == 3;
    }
    public boolean bladderFullOfNectar() {
        int sum=0;

        for(int i = 3; i <5; ++i){
            sum += this.hasBall(RGB.SensorID.sensorIndex(i));
        }

        return sum == 2;
    }
}