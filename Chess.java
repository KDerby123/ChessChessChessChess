//This will be the driver class. It also should utlize the ChessGame class as an individual game, and replay by creating another ChessGame.
//Replays will be achieved by inputing the players that were used last game into a new Chessgame such as Chessgame(Player, Player).
import javax.swing.*; 


public class Chess {
  private static void createAndShowGUI() {
       
        JFrame frame = new JFrame("CHESS CLONE");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel("Welcome to Chess");
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);
    }
    /*
    public Player playerSetup(Color color, String name) {
    	//Setups a player, and returns it. (input the name)
    }
    */

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
  

