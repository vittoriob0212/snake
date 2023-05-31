package snake;

import javax.swing.JFrame;

public class FrameGioco extends JFrame {

	FrameGioco() {
		
		this.add(new PannelloDiGioco());
		this.setTitle("Snake");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null); //setta al centro
		
	}

}
