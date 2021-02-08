import javax.swing.*;
import java.text.Normalizer;
import java.util.Locale;

public class Segundo {
    public static void main(String[] args) {

        generateRFC();
    }

    /**
     * Funcion que genera un RFC con la información dada en la interfaz gráfica.
     */
    static void generateRFC() {
        boolean controlVar = true;

        do {
            try{
                String firstName = showInput("Ingresa tu nombre");
                String lastNameF = showInput("Ingresa tu apellido paterno");
                String lastNameM = showInput("Ingresa tu apellido materno");

                short birthday_day = Short.parseShort(showInput("Día de nacimiento: [1-31]"));
                short birthday_month = Short.parseShort(showInput("Mes de nacimiento: [1-12]"));
                short birthday_year = Short.parseShort(showInput("Año de nacimiento: [1920-2000]"));

                short[] curatedBirthday = validateBirthday(birthday_day, birthday_month, birthday_year);

                String digits = generateDigits(curatedBirthday);
                String nameData = generateChars(firstName, lastNameF, lastNameM);

                controlVar = showDialog(String.format("El RFC de %s %s %s es: %s",
                        firstName.toUpperCase(Locale.ROOT),
                        lastNameF.toUpperCase(Locale.ROOT),
                        lastNameM.toUpperCase(Locale.ROOT),
                        nameData + digits
                        ));


            } catch (Exception err) {
                showDialog("Ocurrio un error capturando los datos, ingresalos nuevamente.");
            }

        } while (controlVar);
    }

    /**
     * Funcion que valida el cumpleaños y de ser el caso que se viole el rango de cada
     * una de las fechas se asigna el minimo de su rango.
     * @param DAY
     * @param MONTH
     * @param YEAR
     * @return Array de siempre 3 elementos de la forma [Dia, Mes, Año].
     */
    static short[] validateBirthday(short DAY, short MONTH, short YEAR) {

        if( DAY < 1 || DAY > 31 ) {
            DAY = 1;
        }

        if( MONTH < 1 || MONTH > 12 ) {
            MONTH = 1;
        }

        if( YEAR < 1920 || YEAR > 2000 ) {
            YEAR = 1920;
        }

        return new short[] {DAY, MONTH, YEAR};

    }

    /**
     * Función que muestra un Input en pantalla para capturar los datos del usuario.
     * @param MESSAGE Mensaje para desplegar en el input.
     * @return Valor introducido en el Input como string.
     */
    static String showInput(String MESSAGE) {
        return JOptionPane.showInputDialog(MESSAGE).trim();
    }

    /**
     * Funcion que muestra un JOption pane con el mensaje dado.
     * @param MESSAGE Mensaje a desplegar
     * @return retorna false si el usuario presiona el botón de salir o cancel.
     */
    static boolean showDialog(String MESSAGE) {
        int optionType = JOptionPane.OK_OPTION;
        int result = JOptionPane.showConfirmDialog(null, MESSAGE,"Mensaje", optionType );

        if( result == JOptionPane.OK_CANCEL_OPTION ) {
            System.exit(0);
        }

        return result == JOptionPane.OK_OPTION;
    }

    /**
     * Funcion que genera los digitos del RFC.
     * @param BIRTHDAY Se recibe un array de datos short de la forma [dia, mes, año]
     * @return Se regresa un string con los digitos generados
     */
    static String generateDigits( short[] BIRTHDAY ) {
        String montDigits = "";
        String dayDigits = "";
        String firstTwo = "" + BIRTHDAY[2];
        firstTwo = firstTwo.substring(Math.max(firstTwo.length() - 2, 0));

        if(BIRTHDAY[1] < 10 ) {
            montDigits = "0" + BIRTHDAY[1];
        } else {
            montDigits += BIRTHDAY[1];
        }

        if(BIRTHDAY[0] < 10 ) {
            dayDigits = "0" + BIRTHDAY[0];
        } else {
            dayDigits += BIRTHDAY[0];
        }

        return firstTwo + montDigits + dayDigits;
    }

    /**
     * Funcion que genera los caracteres a partir de un nombre
     * @param firstName Nombre del usuario
     * @param lastNameF Apellido paterno
     * @param lastNameM Apellido materno
     * @return Caracteres del RFC.
     */
    static String generateChars(String firstName, String lastNameF, String lastNameM) {

        String finalString = "";
        String temp = parseLastName(curateString(lastNameF)).substring(0,2).toUpperCase(Locale.ROOT);
        temp += parseLastName(curateString(lastNameM)).substring(0,1).toUpperCase(Locale.ROOT);
        temp += curateString(firstName).substring(0,1).toUpperCase(Locale.ROOT);

        return temp;
    }

    /**
     * Funcion que elimina palabras no permitidas en los apellidos.
     * @param lastName Apellido a parsear
     * @return Apellido curado.
     */
    static String parseLastName(String lastName) {
        lastName = lastName.toLowerCase(Locale.ROOT);
        lastName = lastName.replaceAll("(?:^|(?<= ))(de|las|la|el|los|y)(?:(?= )|$)", "");

        return lastName.trim();
    }

    /**
     * Esta funcion cambia todos los caracteres que tengan ácentos por el mismo
     * sin el ácento.
     * @param data String a curar
     * @return String curado
     */
    static String curateString(String data) {
        data = Normalizer.normalize(data, Normalizer.Form.NFD);
        data = data.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        return data;
    }

}
