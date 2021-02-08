import javax.swing.*;
import java.util.Random;

public class Primero {
    public static void main(String[] args) {

        boolean controlVar = true;

        do {
            int DICE_1 = RollDice();
            int DICE_2 = RollDice();

            String message = VerifyGameRules(DICE_1, DICE_2);
            controlVar = GenerateAlert(DICE_1, DICE_2, message);

        } while (controlVar);

    }

    /**
     * Función que tira un dado y devuelve el valor del mismo.
     * @return valor aleatorio en el rango [1-6]
     */
    static int RollDice() {
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }

    /**
     * Función que muestra el resultado del juego en una ventana y
     * regresa si el usuario presiona "ok" o salir.
     * @param DICE_1 Valor del primer dado
     * @param DICE_2 Valor del segundo dado
     * @param message Mensaje a mostrar en el alert.
     * @return valor del botón presionado
     */
    static boolean GenerateAlert(int DICE_1, int DICE_2, String message){
        String composedMessage = String.format("Lanzaste: %d %d\n %s", DICE_1, DICE_2, message);
        int optionType = JOptionPane.OK_OPTION;
        int result = JOptionPane.showConfirmDialog(null, composedMessage,"Mensaje", optionType );

        return result == JOptionPane.OK_OPTION;
    }

    /**
     * Función que valida las reglas del juego y regresa un mensaje correspondiente a
     * cada caso.
     * @param DICE_1 valor del primer dado.
     * @param DICE_2 valor del segundo dado.
     * @return mensaje correspondiente a la entrada.
     */
    static String VerifyGameRules(int DICE_1, int DICE_2) {
        int sum = DICE_1 + DICE_2;

        if( sum == 7 || sum == 11 ) {
            return "Ganas la apuesta.";
        }
        else if( sum == 2 || sum == 3 || sum == 12 ){
            return "Pierdes la apuesta";
        } else {
            return "Ganas un punto";
        }

    }
}
