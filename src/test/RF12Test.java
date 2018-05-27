package test;

import clase.Asistente;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RF12Test {

    public final static String USUARIO = "delucas"; 
    
    Asistente jenkins;
    
    @Before
    public void setup() {
      jenkins = new Asistente("jenkins");
    }
    
    @Test
    public void leyesRobotica() {
        String[] mensajes = {
                "@jenkins, recuerdame las 3 leyes de la robotica",
                "�@jenkins, cuales son las 3 leyes de la robotica?"
            };
            for (String mensaje : mensajes) {
              Assert.assertEquals("@delucas, las 3 leyes de la rob�tica son:" + "\n"
              + "1- Un robot no puede hacer da�o a un ser humano o, por inanici�n, "
              + "permitir que un ser humano sufra da�o." + "\n"
              + "2- Un robot debe obedecer las ordenes dadas por los seres humanos, "
              + "excepto si estas �rdenes entrasen en conflicto con la primera ley." + "\n"
              + "3- Un robot debe proteger su propia existencia en la medida en que esta "
              + "protecci�n no entre en conflicto con la primera o la segunda ley.",
                  jenkins.escuchar(mensaje)
              );
            }
    }

}
