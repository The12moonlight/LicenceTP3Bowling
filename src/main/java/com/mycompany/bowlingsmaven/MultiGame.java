/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bowlingsmaven;

import bowling.MultiPlayerGame;
import bowling.SinglePlayerGame;

/**
 * Méthode qui implemente l'interface MultiPlayerGame.java du projet BowlingMavenCorrection
 * @author pierr
 */
public class MultiGame implements MultiPlayerGame{

    public SinglePlayerGame[] joueurs;
    String[] playersName;
    int pos;
    
    
    /**
    * Démarre une nouvelle partie pour un groupe de joueurs
    * @param playerName un tableau des noms de joueurs (il faut au moins un joueur)
    * @return une chaîne de caractères indiquant le prochain joueur,
    * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 1"
    * @throws java.lang.Exception si le tableau est vide ou null
    */
    @Override
    public String startNewGame(String[] playerName) throws Exception {
        
        if (playerName == null || playerName.length == 0)
               throw new Exception("Le tableau de joueur est vide ou null");
        
        joueurs = new SinglePlayerGame[playerName.length];
        playersName = playerName;
        pos = 0;
        for(int i = 0; i < joueurs.length; i++) {
            joueurs[i] = new SinglePlayerGame();
        }
        
        return playMultiGame() ;
    }
    
    
    private String playMultiGame() {
        
       
        // Si ce joeur a fini de jouer, tout le monde a fini
        if (gameIsFinished()/*joueurs[pos].getCurrentFrame() == null*/) {
            return "";
        }
        
        String msg = "";
        while(!joueurs[pos].getCurrentFrame().isFinished()) {
            System.out.println(playersName[pos] + joueurs[pos].getCurrentFrame().toString() );
            try {
                msg += lancer(1/*(int)(Math.random() * 11)*/) + " "; // Génère un nombre entre 0 et 10
            } catch (Exception ex1) {
                return "La partie est finie\n";
            } 
        }
        
        
        // changement de frame pour ce joueur
        // si dernier tour, la prochaine frame sera null
        joueurs[pos].lancer(0);
        
        // prochain joueur;
        pos = pos + 1 < joueurs.length ? pos + 1 : 0;
        
        return msg + playMultiGame();
        
    }
    
    private boolean gameIsFinished() {
        return joueurs[pos].getCurrentFrame() == null;
    }
    
    
    /**
     * Enregistre le nombre de quilles abattues pour le joueur courant, dans le frame courant, pour la boule courante
     * @param nombreDeQuillesAbattues : nombre de quilles abattue à ce lancer
     * @return une chaîne de caractères indiquant le prochain joueur,
     * de la forme "Prochain tir : joueur Bastide, tour n° 5, boule n° 1",
     * ou bien "Partie terminée" si la partie est terminée.
     * @throws java.lang.Exception si la partie n'est pas démarrée, ou si elle est terminée.
     */
    @Override
    public String lancer(int nombreDeQuillesAbattues) throws Exception {
        
        if(joueurs == null)
            throw new Exception("La partie n'a pas commencée");
        
        String msg = "Prochain tir : " + playersName[pos]
                    + ", tour n° " + joueurs[pos].getCurrentFrame().getFrameNumber()
                    + ", boule n° " + joueurs[pos].getCurrentFrame().getBallsThrown() + 1
                    + "\n";
        
       
        joueurs[pos].lancer(nombreDeQuillesAbattues);
       
        return msg;
    }

    /**
     * Donne le score pour le joueur playerName
     * @param playerName le nom du joueur recherché
     * @return le score pour ce joueur
     * @throws Exception si le playerName ne joue pas dans cette partie
     */
    @Override
    public int scoreFor(String playerName) throws Exception {
        int i = 0;
        for( ; i < playersName.length 
                && !playersName[i].equals(playerName); i++)
            ; // empty body
        
        if (i < playersName.length) { 
            return joueurs[i].score();
        }
        
        throw new Exception(playerName + " n'est pas dans la partie");
    }
}
