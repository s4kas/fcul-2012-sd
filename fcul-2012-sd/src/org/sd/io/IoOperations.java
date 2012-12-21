package org.sd.io;

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
     * Loads Agenda from file 
     * @param fileName 
     * @return List<evento> list of eventos (agenda).
     */ 
    @SuppressWarnings({ "finally", "resource" })
    public static List<Evento> loadAgendaFromFile(File fileName) {

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
     * commit eventos list (agenda) to file
     * @param list<evento> list of eventos composing an Agenda
     * @param filename to record data on
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
