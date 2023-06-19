package donnees_bloquees;

import java.io.IOException;
import java.net.URISyntaxException;

public interface InterfaceEtablissementSup {

    public String recupererListeDetablissementsSuperieurs() throws URISyntaxException, IOException, InterruptedException ;

}
