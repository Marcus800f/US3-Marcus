import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Etudiant {
    private int id; 
    private String nom;
    private double moyenne;
    private String avis;
    private List<Notation> notations;

    public Etudiant(String nom) {
        this.nom = nom;
        this.notations = new ArrayList<>();
    }

    
    public void ajouterEnBase() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO etudiants (nom) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nom);
            stmt.executeUpdate();

            
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void ajouterNotation(double note, int coef) {
        Notation n = new Notation(note, coef);
        notations.add(n);

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO notations (id_etudiant, note, coef) VALUES (?, ?, ?)")) {
            stmt.setInt(1, this.id);
            stmt.setDouble(2, note);
            stmt.setInt(3, coef);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void chargerNotations() {
        notations.clear();
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT note, coef FROM notations WHERE id_etudiant = ?")) {
            stmt.setInt(1, this.id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                double note = rs.getDouble("note");
                int coef = rs.getInt("coef");
                notations.add(new Notation(note, coef));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void calculerMoyenne() {
        double total = 0;
        int totalCoef = 0;
        for (Notation n : notations) {
            total += n.getNote() * n.getCoef();
            totalCoef += n.getCoef();
        }
        moyenne = totalCoef > 0 ? total / totalCoef : 0;
        if (moyenne >= 10) avis = "Admis";
        else if (moyenne >= 8) avis = "Rattrapage";
        else avis = "Refusé";
    }

    
    public void mettreAJourEnBase() {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE etudiants SET moyenne = ?, avis = ? WHERE id = ?")) {
            stmt.setDouble(1, moyenne);
            stmt.setString(2, avis);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public void afficher() {
        System.out.println("Étudiant : " + nom);
        System.out.println("Notes :");
        for (Notation n : notations) {
            n.afficher();
        }
        System.out.printf("Moyenne : %.2f\n", moyenne);
        System.out.println("Avis : " + avis);
        
    }
}
