package sample;

import java.util.IllegalFormatException;
import java.util.concurrent.TimeUnit;
import com.pi4j.io.gpio.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Controller {

    private static GpioPinDigitalOutput pin1; //Wasser
    private static GpioPinDigitalOutput pin2; //Trommel
    private static GpioPinDigitalOutput pin3; //Luft
    private static GpioPinDigitalOutput pin4; //Wasserstand

    @FXML static Button ledButton; @FXML static Button startButton; @FXML static Button phase1Button; @FXML static Button phase2Button;
    @FXML static Button phase3Button; @FXML static Button testButton; @FXML Button saveButton;
    @FXML TextArea txtArea;
    @FXML MenuItem keimsorte1; @FXML MenuItem keimsorte2;

    @FXML CheckBox checkboxP1W; @FXML CheckBox checkboxP2W; @FXML CheckBox checkboxP3W; @FXML CheckBox checkboxP1T; @FXML CheckBox checkboxP2T;
    @FXML CheckBox checkboxP3T; @FXML CheckBox checkboxP1L; @FXML CheckBox checkboxP2L; @FXML CheckBox checkboxP3L;
    @FXML CheckBox twW; @FXML CheckBox lwW;

    @FXML TextField p1wDauerF; @FXML TextField p2wDauerF; @FXML TextField p3wDauerF;
    @FXML TextField p1wAlleXSekundenF; @FXML TextField p2wAlleXSekundenF; @FXML TextField p3wAlleXSekundenF;
    @FXML TextField p1wGesDauerF; @FXML TextField p2wGesDauerF; @FXML TextField p3wGesDauerF;

    @FXML TextField p1tDauerF; @FXML TextField p2tDauerF; @FXML TextField p3tDauerF;
    @FXML TextField p1tAlleXSekundenF; @FXML TextField p2tAlleXSekundenF; @FXML TextField p3tAlleXSekundenF;
    @FXML TextField p1tGesDauerF; @FXML TextField p2tGesDauerF; @FXML TextField p3tGesDauerF;

    @FXML TextField p1lDauerF; @FXML TextField p2lDauerF; @FXML TextField p3lDauerF;
    @FXML TextField p1lAlleXSekundenF; @FXML TextField p2lAlleXSekundenF; @FXML TextField p3lAlleXSekundenF;
    @FXML TextField p1lGesDauerF; @FXML TextField p2lGesDauerF; @FXML TextField p3lGesDauerF;

    //Variablen für Phase1
    int p1wDauer = 8;           //Die Dauer, für das das Wasser engeschalten wird. (In Sekunden)
    int p1wAlleXSekunden = 1800 - p1wDauer;   //Alle x Minuten wird das Wasser eingeschalten. (In Sekunden)
    int p1wGesDauer = 300;       //Die Gesamtdauer, wie lange diese Vorgänge stattfinden sollen. (In Minuten)
    int p1wZyklen = (int) (((p1wGesDauer * 60) / (p1wAlleXSekunden + p1wDauer))); //Anzahl der Zyklen bei denen das Wasser eingeschaltet wird und diese in die Gesamtdauer passt.

    int p1tDauer = 45;          //Dauer, die sich die Trommel dreht (In Sekunden)
    int p1tAlleXSekunden = 900 - p1tDauer; //Alle X Sekunden läuft die Trommel (Dauer der Trommeldrehung abgezogen) (in Sekunden)
    int p1tGesDauer = 300;     //In Minuten
    int p1tZyklen = (int) ((p1tGesDauer * 60) / (p1tAlleXSekunden + p1tDauer));   //Automatische berechnung der Zyklen

    int p1lDauer = 1800; //Dauer, wie lange Luft reingelassen wird.
    int p1lAlleXSekunden = 3600 - p1lDauer; //Alle X Sekunden wird luft reingelassen
    int p1lGesDauer = 300; //Wie lange es insgesamt läuft (in Minuten)
    int p1lZyklen = (int) ((p1lGesDauer * 60) / (p1lAlleXSekunden + p1lDauer)); //Wartedauer UND LuftAnDauer

    //Variablen für Phase2
    int p2wDauer = 8;           //Die Dauer, für das das Wasser engeschalten wird. (In Sekunden)
    int p2wAlleXSekunden = 21600 - p2wDauer;   //Alle x Minuten wird das Wasser eingeschalten. (In Sekunden)
    int p2wGesDauer = 2880;       //Die Gesamtdauer, wie lange diese Vorgänge stattfinden sollen. (In Minuten)
    int p2wZyklen = (int) (((p2wGesDauer * 60) / (p2wAlleXSekunden + p2wDauer))); //Anzahl der Zyklen bei denen das Wasser eingeschaltet wird und diese in die Gesamtdauer passt.

    int p2tDauer = 120;          //Dauer, die sich die Trommel dreht (In Sekunden)
    int p2tAlleXSekunden = 1800 - p2tDauer; //Alle X Sekunden läuft die Trommel (Dauer der Trommeldrehung abgezogen) (in Sekunden)
    int p2tGesDauer = 2880;     //In Minuten
    int p2tZyklen = (int) ((p2tGesDauer * 60) / p2tAlleXSekunden + p2tDauer);   //Automatische berechnung der Zyklen

    int p2lDauer = 1800; //Dauer, wie lange Luft reingelassen wird.
    int p2lAlleXSekunden = 3600 - p2lDauer; //Alle X Sekunden wird luft reingelassen
    int p2lGesDauer = 2880; //Wie lange es insgesamt läuft (in Minuten)
    int p2lZyklen = (int) ((p2lGesDauer * 60) / (p2lAlleXSekunden + p2lDauer)); //Wartedauer UND LuftAnDauer

    //Variablen für Phase3
    int p3wDauer = 8;           //Die Dauer, für das das Wasser engeschalten wird. (In Sekunden)
    int p3wAlleXSekunden = 21600 - p3wDauer;   //Alle x Minuten wird das Wasser eingeschalten. (In Sekunden)
    int p3wGesDauer = 2880;       //Die Gesamtdauer, wie lange diese Vorgänge stattfinden sollen. (In Minuten)
    int p3wZyklen = (int) (((p3wGesDauer * 60) / (p3wAlleXSekunden + p3wDauer))); //Anzahl der Zyklen bei denen das Wasser eingeschaltet wird und diese in die Gesamtdauer passt.

    int p3tDauer = 120;          //Dauer, die sich die Trommel dreht (In Sekunden)
    int p3tAlleXSekunden = 1800 - p3tDauer; //Alle X Sekunden läuft die Trommel (Dauer der Trommeldrehung abgezogen) (in Sekunden)
    int p3tGesDauer = 2880;     //In Minuten
    int p3tZyklen = (int) ((p3tGesDauer * 60) / (p3tAlleXSekunden + p3tDauer));   //Automatische berechnung der Zyklen


    int p3lDauer = 1800; //Dauer, wie lange Luft reingelassen wird.
    int p3lAlleXSekunden = 3600 - p3lDauer; //Alle X Sekunden wird luft reingelassen
    int p3lGesDauer = 2880; //Wie lange es insgesamt läuft (in Minuten)
    int p3lZyklen = (int) ((p3lGesDauer * 60) / (p3lAlleXSekunden + p3lDauer)); //Wartedauer UND LuftAnDauer

    public void LedToggle() {
        if (pin1 == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Gpio_1", PinState.LOW);
            pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Gpio_2", PinState.LOW);
            pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Gpio_3", PinState.LOW);
            pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Gpio 4", PinState.LOW);
        } else if (pin1.isLow() || pin2.isLow() || pin3.isLow() || pin4.isLow()) {
            pin1.high();
            pin2.high();
            pin3.high();
            pin4.high();
            System.out.println("Gpio 1, 2, 3, 4 sind nun aus!");
        } else if (pin1.isHigh() || pin2.isHigh() || pin3.isHigh() || pin4.isHigh()) {
            pin1.low();
            pin2.low();
            pin3.low();
            pin4.low();
            System.out.println("Gpio 1, 2, 3, 4 sind nun an!");
        }
    }

    public void Initialize() {
        if (pin1 == null) {
            GpioController gpio = GpioFactory.getInstance();
            pin1 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "Gpio_1", PinState.LOW);
            pin2 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_02, "Gpio_2", PinState.LOW);
            pin3 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "Gpio_3", PinState.LOW);
            pin4 = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_04, "Gpio 4", PinState.LOW);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            pin1.high();
            pin2.high();
            pin3.high();
            pin4.high();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
            // System.out.println("Gpio 1, 2, 3, 4 wurden initialisiert und sind nun bereit benutzt zu werden");
            txtArea.appendText("Gpio 1, 2, 3 und 4 wurden initialsiert und sind nun bereit benutzt zu werden!\n");
        } else {
            pin1.high();
            pin2.high();
            pin3.high();
            pin4.high();
            txtArea.appendText("Alle Pins sind nun aus! \n");
        }
    }


    Thread Phase1WasserThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 1 Wasser läuft aktuell... \n");
            //Es werden p1wZyklen Zyklen durchlaufen mit  p1wDauer bei der das Wasser läuft | Wasser & Trommel läuft!
            for (int i = p1wZyklen; i >= 1; i--) {
                while (true) { //pin4.isHigh()
                    try {
                        pin1.low();
                        if(twW.isSelected())
                            pin2.low();
                        if(lwW.isSelected())
                            pin3.low();
                        try {
                            txtArea.appendText("Das Wasser wurde für " + p1wDauer + " Sekunden angeschalten!\n");
                            TimeUnit.SECONDS.sleep(p1wDauer);
                        } catch (InterruptedException e) {}
                        pin1.high();
                        if(twW.isSelected())
                            pin2.high();
                        if(lwW.isSelected())
                            pin3.high();
                        try {
                            txtArea.appendText("Das Wasser wurde für " + p1wAlleXSekunden + "( ca. " + p1wAlleXSekunden / 60 + " Minuten )" + " Sekunden ausgeschalten!\n");
                            TimeUnit.SECONDS.sleep(p1wAlleXSekunden);
                        } catch (InterruptedException e) {}
                    } catch (InternalError e) {
                        txtArea.appendText("Programm bricht ab...");
                        txtArea.appendText("Wasserstand ist zu niedrig! Bitte nachfüllen! \n");
                    }
                }

            }
            txtArea.appendText("Phase 1 Wasser ist abgeschlossen. \n");
        }
    };

    Thread Phase1TrommelThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 1 Trommel läuft aktuell... \n");
            for (int i = p1tZyklen; i >= 1; i--) {
                pin2.low();
                try {
                    txtArea.appendText("Die Trommel ist für " + p1tDauer + " Sekunden angeschalten \n");
                    TimeUnit.SECONDS.sleep(p1tDauer);
                } catch (InterruptedException e) {}
                pin2.high();
                try {
                    txtArea.appendText("Die Trommel ist für " + p1tAlleXSekunden + "( ca. " + p1tAlleXSekunden / 60 + " Minuten )" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p1tAlleXSekunden);
                } catch (InterruptedException e) {}
            }
            txtArea.appendText("Phase 1 Trommel ist abgeschlossen. \n");
        }
    };

    Thread Phase1LuftThread = new Thread(){
        public void run(){
            txtArea.appendText("Phase 1 Luft läuft aktuell... \n");
            for(int i = p1lZyklen; i >= 1; i--){
                pin3.low();
                try{
                    txtArea.appendText("Die Luft ist für " + p1lDauer + " Sekunden angeschalten \n");
                    TimeUnit.SECONDS.sleep(p1lDauer);

                } catch(InterruptedException e){}
                pin3.high();
                try{
                    txtArea.appendText("Die Trommel ist für " + p1lAlleXSekunden + "( ca. " + p1lAlleXSekunden / 60 + " Minuten )" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p1lAlleXSekunden);
                } catch(InterruptedException e){}
            }
            txtArea.appendText("Phase 1 Luft ist abgeschlossen. \n");
        }
    };

    Thread Phase2WasserThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 2 Wasser läuft aktuell... \n");
            for (int i = p2wZyklen; i >= 1; i--) {
                while (true) { //pin4.isHigh()
                    try {
                        pin1.low();
                        if(twW.isSelected())
                            pin2.low();
                        if(lwW.isSelected())
                            pin3.low();
                        try {
                            txtArea.appendText("Das Wasser ist für " + p2wDauer + " Sekunden angeschalten. \n");
                            TimeUnit.SECONDS.sleep((p2wDauer));
                        } catch (InterruptedException e) {}
                        pin1.high();
                        if(twW.isSelected())
                            pin2.high();
                        if(lwW.isSelected())
                            pin3.high();
                        try {
                            txtArea.appendText("Das Wasser ist für " + p2wAlleXSekunden + "( ca. " + p2wAlleXSekunden / 60 + " Minuten )" + " Sekunden ausgeschalten. \n");
                            TimeUnit.SECONDS.sleep(p2wAlleXSekunden);
                        } catch (InterruptedException e) {}
                    } catch (InternalError e) {
                        txtArea.appendText("Programm bricht ab...");
                        txtArea.appendText("Der Wasserstand ist zu niedrig! \n");
                    }
                }
            }
            txtArea.appendText("Phase 2 Wasser ist abgeschlossen. \n");
        }
    };

    Thread Phase2TrommelThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 2 Trommel läuft aktuell... \n");
            for (int i = p2tZyklen; i >= 1; i--) {
                pin2.low();
                try {
                    txtArea.appendText("Die Trommel ist für " + p2tDauer + " Sekunden angeschalten. \n");
                    TimeUnit.SECONDS.sleep(p2tDauer);
                } catch (InterruptedException e) {}
                pin2.high();
                try {
                    txtArea.appendText("Die Trommel ist für " + p2tAlleXSekunden + " (ca. " + p2tAlleXSekunden/60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p2tAlleXSekunden);
                } catch (InterruptedException e) {}
            }
            txtArea.appendText("Phase 2 Trommel ist abgeschlossen. \n");
        }
    };

    Thread Phase2LuftThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 2 Luft läuft aktuell... \n");
            for (int i = p2lZyklen; i >= 1; i--) {
                pin3.low();
                try {
                    txtArea.appendText("Die Luft ist für " + p2lAlleXSekunden + "(ca. " + p2lAlleXSekunden / 60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p2lDauer);
                } catch (InterruptedException e) {}
                pin3.high();
                try {
                    txtArea.appendText("Die Luft ist für " + p2lDauer + "(ca. " + p2lDauer / 60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p2lAlleXSekunden);
                } catch (InterruptedException e) {}
            }
            txtArea.appendText("Phase 2 Luft ist abgeschlossen. \n");

        }
    };

    Thread Phase3WasserThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 3 Wasser läuft aktuell... \n");
            for (int i = p3wZyklen; i >= 1; i--) {
                while (true) { //pin4.isHigh()
                    try {
                        pin1.low();
                        if(twW.isSelected())
                            pin2.low();
                        if(lwW.isSelected())
                            pin3.low();
                        try {
                            txtArea.appendText("Das Wasser ist für " + p3wDauer + " Sekunden angeschalten. \n");
                            TimeUnit.SECONDS.sleep((p3wDauer));
                        } catch (InterruptedException e) {}

                        pin1.high();
                        if(twW.isSelected())
                            pin2.high();
                        if(lwW.isSelected())
                            pin3.high();
                        try {
                            txtArea.appendText("Das Wasser ist für " + p3wAlleXSekunden + "(ca. " + p3wAlleXSekunden / 60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                            TimeUnit.SECONDS.sleep(p3wAlleXSekunden);
                        } catch (InterruptedException e) {}
                    } catch (InternalError e) {
                        txtArea.appendText("Programm bricht ab...");
                        txtArea.appendText("Der Wasserstand ist zu niedrig! \n");
                    }
                }
            }
            txtArea.appendText("Phase 3 Wasser ist abgeschlossen. \n");
        }
    };

    Thread Phase3TrommelThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 3 Trommel läuft aktuell... \n");
            for (int i = p3tZyklen; i >= 1; i--) {
                pin2.low();
                try {
                    txtArea.appendText("Die Trommel ist für " + p3tDauer + " Sekunden angeschalten. \n");
                    TimeUnit.SECONDS.sleep(p3tDauer);
                } catch (InterruptedException e) {}
                pin2.high();
                try {
                    txtArea.appendText("Die Trommel ist für " + p3tAlleXSekunden + "(ca. " + p3tAlleXSekunden/60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p3tAlleXSekunden);
                } catch (InterruptedException e) {}
            }
            txtArea.appendText("Phase 3 Trommel ist abgeschlossen. \n");
        }
    };

    Thread Phase3LuftThread = new Thread() {
        public void run() {
            txtArea.appendText("Phase 3 Luft läuft aktuell... \n");
            for (int i = p3lZyklen; i >= 1; i--) {
                pin3.low();
                try {
                    txtArea.appendText("Die Luft ist für " + p3lAlleXSekunden + "( ca. " + p3lAlleXSekunden / 60 + " Minuten )" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p3lDauer);
                } catch (InterruptedException e) {}
                pin3.high();
                try {
                    txtArea.appendText("Die Luft ist für " + p3lDauer + "(ca. " + p3lDauer / 60 + " Minuten)" + " Sekunden ausgeschalten. \n");
                    TimeUnit.SECONDS.sleep(p2lAlleXSekunden);
                } catch (InterruptedException e) {}
            }
            txtArea.appendText("Phase 3 Luft ist abgeschlossen. \n");
        }
    };


    public void Phase1() {
        if(checkboxP1W.isSelected()){
                Phase1WasserThread.start();
            try {
                Phase1WasserThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP1T.isSelected()){
            Phase1TrommelThread.start();
            try {
                Phase1TrommelThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP1L.isSelected()){
            Phase1LuftThread.start();
            try {
                Phase1LuftThread.join();
            } catch (InterruptedException e) {}
        }
    }

    public void Phase2() {
        if(checkboxP2W.isSelected()){
            Phase2WasserThread.start();
            try {
                Phase2WasserThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP2T.isSelected()){
            Phase2TrommelThread.start();
            try {
                Phase2TrommelThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP2L.isSelected()){
            Phase2LuftThread.start();
            try {
                Phase2LuftThread.join();
            } catch (InterruptedException e) {}
        }
    }

    public void Phase3() {
        if(checkboxP3W.isSelected()){
            Phase3WasserThread.start();
            try {
                Phase3WasserThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP3T.isSelected()){
            Phase3TrommelThread.start();
            try {
                Phase3TrommelThread.join();
            } catch (InterruptedException e) {}
        }
        if(checkboxP3L.isSelected()){
            Phase3LuftThread.start();
            try {
                Phase3LuftThread.join();
            } catch (InterruptedException e) {}
        }
    }


    public void start() {
            Initialize();
            txtArea.appendText("Phase 1 läuft aktuell... \n");
            Phase1();
            txtArea.appendText("Phase 1 wird abgeschlossen! \n");
            txtArea.appendText("Phase 2 läuft aktuell... \n");
            Phase2();
            txtArea.appendText("Phase 2 wird abgeschlossen! \n");
            txtArea.appendText("Phase 3 läuft aktuell... \n");
            Phase3();
            txtArea.appendText("Phase 3 wird abgeschlossen! \n");
            txtArea.appendText("Alle Phase fertig! Fahre Programm herunter... \n");
            aus();
        }


    public void aus() {
        pin1.high();
        pin2.high();
        pin3.high();
        pin4.high();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        txtArea.appendText("...\n");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        txtArea.appendText("Herunterfahren erfolgreich\n");
    }


    public void SaveButton() {
        try{
            //Wasser Parsen
            double p1wDauerD = Double.parseDouble(p1wDauerF.getText());
            p1wDauer = (int)p1wDauerD;
            double p2wDauerD = Double.parseDouble(p2wDauerF.getText());
            p2wDauer = (int)p2wDauerD;
            double p3wDauerD = Double.parseDouble(p3wDauerF.getText());
            p3wDauer = (int)p3wDauerD;


            double p1wAlleXSekundenD = Double.parseDouble(p1wAlleXSekundenF.getText());
            p1wAlleXSekunden = (int)p1wAlleXSekundenD - p1wDauer;
            double p2wAlleXSekundenD = Double.parseDouble(p2wAlleXSekundenF.getText());
            p2wAlleXSekunden = (int)p2wAlleXSekundenD - p2wDauer;
            double p3wAlleXSekundenD = Double.parseDouble(p3wAlleXSekundenF.getText());
            p3wAlleXSekunden = (int)p3wAlleXSekundenD - p3wDauer;

            double p1wGesDauerD = Double.parseDouble(p1wGesDauerF.getText());
            p1wGesDauer = (int)p1wGesDauerD;
            double p2wGesDauerD = Double.parseDouble(p2wGesDauerF.getText());
            p2wGesDauer = (int)p2wGesDauerD;
            double p3wGesDauerD = Double.parseDouble(p3wGesDauerF.getText());
            p3wGesDauer = (int)p3wGesDauerD;

            //Trommel Parsen
            double p1tDauerD = Double.parseDouble(p1tDauerF.getText());
            p1tDauer = (int)p1tDauerD;
            double p2tDauerD = Double.parseDouble(p2tDauerF.getText());
            p2tDauer = (int)p2tDauerD;
            double p3tDauerD = Double.parseDouble(p3tDauerF.getText());
            p3tDauer = (int)p3tDauerD;


            double p1tAlleXSekundenD = Double.parseDouble(p1tAlleXSekundenF.getText());
            p1tAlleXSekunden = (int)p1tAlleXSekundenD - p1tDauer;
            double p2tAlleXSekundenD = Double.parseDouble(p2tAlleXSekundenF.getText());
            p2tAlleXSekunden = (int)p2tAlleXSekundenD - p2tDauer;
            double p3tAlleXSekundenD = Double.parseDouble(p3tAlleXSekundenF.getText());
            p3tAlleXSekunden = (int)p3tAlleXSekundenD - p3tDauer;

            double p1tGesDauerD = Double.parseDouble(p1tGesDauerF.getText());
            p1tGesDauer = (int)p1tGesDauerD;
            double p2tGesDauerD = Double.parseDouble(p2tGesDauerF.getText());
            p2tGesDauer = (int)p2tGesDauerD;
            double p3tGesDauerD = Double.parseDouble(p3tGesDauerF.getText());
            p3tGesDauer = (int)p3tGesDauerD;

            //Luft Parsen
            double p1lDauerD = Double.parseDouble(p1lDauerF.getText());
            p1lDauer = (int)p1lDauerD;
            double p2lDauerD = Double.parseDouble(p2lDauerF.getText());
            p2lDauer = (int)p2lDauerD;
            double p3lDauerD = Double.parseDouble(p3lDauerF.getText());
            p3lDauer = (int)p3lDauerD;


            double p1lAlleXSekundenD = Double.parseDouble(p1lAlleXSekundenF.getText());
            p1lAlleXSekunden = (int)p1lAlleXSekundenD - p1lDauer;
            double p2lAlleXSekundenD = Double.parseDouble(p2lAlleXSekundenF.getText());
            p2lAlleXSekunden = (int)p2lAlleXSekundenD - p2lDauer;
            double p3lAlleXSekundenD = Double.parseDouble(p3lAlleXSekundenF.getText());
            p3lAlleXSekunden = (int)p3lAlleXSekundenD - p3lDauer;

            double p1lGesDauerD = Double.parseDouble(p1lGesDauerF.getText());
            p1lGesDauer = (int)p1lGesDauerD;
            double p2lGesDauerD = Double.parseDouble(p2lGesDauerF.getText());
            p2lGesDauer = (int)p2lGesDauerD;
            double p3lGesDauerD = Double.parseDouble(p3lGesDauerF.getText());
            p3lGesDauer = (int)p3lGesDauerD;

            if(p1wAlleXSekunden + p1wDauer != 0)
                p1wZyklen = (int) (((p1wGesDauer * 60) / (p1wAlleXSekunden + p1wDauer)));
            else p1wZyklen = 0;

            if(p1tAlleXSekunden + p1tDauer != 0)
                p1tZyklen = (int) ((p1tGesDauer * 60) / (p1tAlleXSekunden + p1tDauer));
            else p1tZyklen = 0;

            if(p1lAlleXSekunden + p1lDauer != 0)
                p1lZyklen = (int) ((p1lGesDauer * 60) / (p1lAlleXSekunden + p1lDauer));
            else p1lZyklen = 0;

            if(p2wAlleXSekunden + p2wDauer != 0)
                p2wZyklen = (int) (((p2wGesDauer * 60) / (p2wAlleXSekunden + p2wDauer)));
            else p2wZyklen = 0;

            if(p2tAlleXSekunden + p2tDauer != 0)
                p2tZyklen = (int) ((p2tGesDauer * 60) / (p2tAlleXSekunden + p2tDauer));
            else p2tZyklen = 0;

            if(p2lAlleXSekunden + p2lDauer != 0)
                p2lZyklen = (int) ((p2lGesDauer * 60) / (p2lAlleXSekunden + p2lDauer));
            else p2lZyklen = 0;

            if(p3wAlleXSekunden + p3wDauer != 0)
                p3wZyklen = (int) ((p3wGesDauer * 60) / (p3wAlleXSekunden + p3wDauer));
            else p3wZyklen = 0;

            if(p3tAlleXSekunden + p3tDauer != 0)
                p3tZyklen = (int) ((p3tGesDauer * 60) / (p3tAlleXSekunden + p3tDauer));
            else p3tZyklen = 0;

            if(p3lAlleXSekunden + p3lDauer != 0)
                p3lZyklen = (int) ((p3lGesDauer * 60) / (p3lAlleXSekunden + p3lDauer));
            else p3lZyklen = 0;


        } catch(IllegalFormatException e){
            txtArea.appendText("Error! Du musst eine gültige Zahl eingeben!");
            System.out.println("XXXXXXX");
        }
    }

    public void Laufzeiten(){
        if(checkboxP1W.isSelected())
        txtArea.appendText("Phase 1: Wasser wird alle " + p1wAlleXSekunden + " Sekunden für " + p1wDauer + " Sekunden laufen für insgesamt " + p1wGesDauer + " Minuten. Ca. " + p1wZyklen + " Zyklen.\n");
        if(checkboxP1T.isSelected())
        txtArea.appendText("Phase 1: Trommel wird alle " + p1tAlleXSekunden + " Sekunden für " + p1tDauer + " Sekunden laufen für insgesamt " + p1tGesDauer + " Minuten. Ca. " + p1tZyklen + " Zyklen. \n");
        if(checkboxP1L.isSelected())
        txtArea.appendText("Phase 1: Luft wird alle " + p1lAlleXSekunden + " Sekunden für " + p1lDauer + " Sekunden laufen für insgesamt " + p1lGesDauer + " Minuten. Ca. " + p1lZyklen + " Zyklen. \n");

        txtArea.appendText("\n");
        if(checkboxP2W.isSelected())
        txtArea.appendText("Phase 2: Wasser wird alle " + p2wAlleXSekunden + " Sekunden für " + p2wDauer + " Sekunden laufen für insgesamt " + p2wGesDauer + " Minuten. Ca. " + p2wZyklen + " Zyklen.\n");
        if(checkboxP2T.isSelected())
        txtArea.appendText("Phase 2: Trommel wird alle " + p2tAlleXSekunden + " Sekunden für " + p2tDauer + " Sekunden laufen für insgesamt " + p2tGesDauer + " Minuten. Ca. " + p2tZyklen + " Zyklen. \n");
        if(checkboxP2L.isSelected())
        txtArea.appendText("Phase 2: Luft wird alle " + p2lAlleXSekunden + " Sekunden für " + p2lDauer + " Sekunden laufen für insgesamt " + p2lGesDauer + " Minuten. Ca. " + p2lZyklen + " Zyklen.\n");

        txtArea.appendText("\n");
        if(checkboxP3W.isSelected())
        txtArea.appendText("Phase 3: Wasser wird alle " + p3wAlleXSekunden + " Sekunden für " + p3wDauer + " Sekunden laufen für insgesamt " + p3wGesDauer + " Minuten. Ca. " + p3wZyklen + " Zyklen.\n");
        if(checkboxP3T.isSelected())
        txtArea.appendText("Phase 3: Trommel wird alle " + p3tAlleXSekunden + " Sekunden für " + p3tDauer + " Sekunden laufen für insgesamt " + p3tGesDauer + " Minuten. Ca. " + p3tZyklen + " Zyklen. \n");
        if(checkboxP3L.isSelected())
        txtArea.appendText("Phase 3: Luft wird alle " + p3lAlleXSekunden + " Sekunden für " + p3lDauer + " Sekunden laufen für insgesamt " + p3lGesDauer + " Minuten. Ca. " + p3lZyklen + " Zyklen. \n");

        txtArea.appendText("\n" + "-----------------------------------------------------------------\n");
    }

    public void KeimSorte1(){
        p1wDauerF.setText("0");
        p1lAlleXSekundenF.setText("0");

    }

    public void KeimSorte2(){

    }
}
