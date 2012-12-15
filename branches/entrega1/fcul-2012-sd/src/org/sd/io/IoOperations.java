package org.sd.io;

/**
 *
 * @author tpires
 */
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.EOFException;

import org.sd.data.Evento;

public class IoOperations {

    /*****************************************************************************
     * Abre o ficheiro de agenda
     * @param fileName ficheiro 
     * @return Lista<evento> Lista de eventos.
     */ 
    @SuppressWarnings({ "finally", "resource" })
    public static List loadAgendaFromFile(File fileName) {

        Object recievedData = null;
        ObjectInputStream oIn = null;
        List<Evento> temp = new ArrayList<Evento>();

        try {
            oIn = new ObjectInputStream(new FileInputStream(fileName));

            while ((recievedData = oIn.readObject()) != null) {
                if (recievedData instanceof Evento) {
                    temp.add((Evento) recievedData);
                }
            }
            return temp; //lista com todas as pictures.

        } catch (ClassNotFoundException ex) {
            System.out.println("Oops! " + ex.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("Oops! " + ex.toString());
        } catch (EOFException ex) {
        } catch (IOException ex) {
            System.out.println("Oops! " + ex.toString());
        } finally {
            if (temp != null) {
                return temp;
            } else {
                return null;
            }
        }
    }

    /*********************************************************************************
     * Grava uma lista de eventos num ficheiro.
     * @param list<evento> uma lista de eventos
     * @param filename nome do ficheiro a gravar.
     */
    public static void saveAgendaToFile(List<Evento> list, File filename) throws IOException {
        ObjectOutputStream oOut = null;
        try {
            oOut = new ObjectOutputStream(new FileOutputStream(filename));
            for (Evento ev : list) {
                oOut.writeObject(ev);
            }
            oOut.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Oops! " + ex.toString());
        } finally {
            //Fecha o Outputstream
            if (oOut != null) {
                oOut.flush();
                oOut.close();
            }
        }
    }
}
