public class TestEtudiant {
    public static void main(String[] args) {

        
        Etudiant e = new Etudiant("Marcus");
        e.ajouterEnBase();

        
        e.ajouterNotation(12, 2);
        e.ajouterNotation(15, 3);
        e.ajouterNotation(9, 1);

        e.chargerNotations();

       
        e.calculerMoyenne();

      
        e.mettreAJourEnBase();

        
        e.afficher();
    }
}
