package testjdbc;

import java.sql.*;

public class Banque {
	
	static final String CONN_URL = "jdbc:oracle:thin:@im2ag-oracle.e.ujf-grenoble.fr:1521:ufrima";
	
	static final String USER = "gauchya";
	static final String PASSWD = "bd2014";
        public static Connection conn;
	private static void menu() {
		System.out.println("*** Choisir une action a effectuer : ***");
		System.out.println("0 : Quitter");
		System.out.println("1 : Selection (consultation des comptes)");
		System.out.println("2 : Insertion (creation d'un compte)");
		System.out.println("3 : Debit");
		System.out.println("4 : Credit");
		System.out.println("5 : Commit");
		System.out.println("6 : Rollback");
	}

	private static void selection() throws SQLException {
            Statement requete = Banque.conn.createStatement();
            ResultSet resultat = requete.executeQuery("select * from Comptes");
            while(resultat.next()) {
                System.out.println("Nc = " + resultat.getInt("Nc") + ","
                        + "Nom = " + resultat.getString("Nom") + ","
                        + " Solde = " + resultat.getInt("Solde")); 
            }
	}

	private static void insertion() throws SQLException {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Comptes VALUES (?, ?, ?)");
            stmt.setInt(1, LectureClavier.lireEntier("Nc du compte à inserer :"));
            System.out.println("Nom du proprio :");
            stmt.setString(2,LectureClavier.lireChaine());
            stmt.setInt(3, LectureClavier.lireEntier("Solde du compte à inserer :"));
            stmt.executeUpdate();
	}	

	private static void debit() throws SQLException {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Comptes Set Solde = SOLDE - ? Where NC = ?");
            stmt.setInt(2, LectureClavier.lireEntier("Nc du compte à débiter :"));
            stmt.setInt(1, LectureClavier.lireEntier("Somme à débiter :"));
            stmt.executeUpdate();
	}		

	private static void credit() throws SQLException {
            PreparedStatement stmt = conn.prepareStatement("UPDATE Comptes Set Solde = SOLDE + ? Where NC = ?");
            stmt.setInt(2, LectureClavier.lireEntier("Nc du compte à débiter :"));
            stmt.setInt(1, LectureClavier.lireEntier("Somme à créditer :"));
            stmt.executeUpdate();
	}			

	private static void commit() throws SQLException {
		// A COMPLETER
	}				

	private static void rollback() throws SQLException {
		// A COMPLETER
	}		
	
	private static void getIsolation() throws SQLException {
		// A COMPLETER
	}

	private static void setIsolation() throws SQLException {
		// A COMPLETER
	}	
	
    public static void main(String args[]) {

        try {
        
        int action;
        boolean exit = false;

  	    // Enregistrement du driver Oracle
  	    System.out.print("Loading Oracle driver... "); 
  	    DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
  	    System.out.println("loaded");
  	    
  	    // Etablissement de la connection
  	    System.out.print("Connecting to the database... "); 
            Banque.conn = DriverManager.getConnection(CONN_URL, USER, PASSWD);
  	    System.out.println("connected");
  	    
  	    // Desactivation de l'autocommit
  	    conn.setAutoCommit (false);
  	    System.out.println("Autocommit disabled");

  	    while(!exit) {
  	    	menu();
  	    	action = LectureClavier.lireEntier("votre choix ?");
  	    	switch(action) {
  	    		case 0 : exit = true; break;
  	    		case 1 : selection(); break;
  	    		case 2 : insertion(); break;
  	    		case 3 : debit(); break;
  	    		case 4 : credit(); break;
  	    		case 5 : commit(); break;
  	    		case 6 : rollback(); break;
  	    		case 7 : getIsolation(); break;
  	    		case 8 : setIsolation(); break;
  	    		default : System.out.println("=> choix incorrect"); menu();
  	    	}
  	    } 	    

  	    // Liberation des ressources et fermeture de la connexion...
            Banque.conn.close();
  	    
  	    System.out.println("au revoir");
  	    
  	    // traitement d'exception
          } catch (SQLException e) {
              System.err.println("failed");
              System.out.println("Affichage de la pile d'erreur");
  	          e.printStackTrace(System.err);
              System.out.println("Affichage du message d'erreur");
              System.out.println(e.getMessage());
              System.out.println("Affichage du code d'erreur");
  	          System.out.println(e.getErrorCode());	    

          }
     }
	

}