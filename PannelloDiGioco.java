package snake;

import java.awt.*; // se metto asterisco calla tutto
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class PannelloDiGioco extends JPanel implements ActionListener {

	static final int LARGHEZZA_SCHERMO = 600;
	static final int ALTEZZA_SCHERMO = 600;
	static final int GRANDEZZA_OGGETTI = 25; // grandezza oggetti di gioco sotto ho fatto una griglia per regolarmi
												// metodo draw()
	static final int OGGETTI_GIOCO = (LARGHEZZA_SCHERMO * ALTEZZA_SCHERMO) / GRANDEZZA_OGGETTI;
	static final int RITARDO = 75;
	final int x[] = new int[OGGETTI_GIOCO]; // coordinate x
	final int y[] = new int[OGGETTI_GIOCO]; // coordinate y
	int corpo = 6; // corpo iniziale del serpentozzo
	int meleMangiate;
	int melaX; // dove si trova la mela coor x
	int melaY; // dove si trova la mela coor y
	char direzione = 'R'; // va verso destra
	boolean corsa = false;
	Timer timer;
	Random random;

	PannelloDiGioco() {
		random = new Random();
		this.setPreferredSize(new Dimension(LARGHEZZA_SCHERMO, ALTEZZA_SCHERMO));
		this.setBackground(Color.black); // ero indeciso tra nero e rosa ahahah
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
	}

	public void startGame() {
		newApple();
		corsa = true;
		timer = new Timer(RITARDO, this);
		timer.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	public void draw(Graphics g) {

		// MELA
		if (corsa) {

			/*
			 * //ATTENZIONE!!! GRIGLIA RIMOSSA CANCELLARE QUI PER ABILITARE
			 * 
			 * for (int i = 0; i < ALTEZZA_SCHERMO / GRANDEZZA_OGGETTI; i++) { g.drawLine(i
			 * * GRANDEZZA_OGGETTI, 0, i * GRANDEZZA_OGGETTI, ALTEZZA_SCHERMO);
			 * g.drawLine(0, i * GRANDEZZA_OGGETTI, LARGHEZZA_SCHERMO, i *
			 * GRANDEZZA_OGGETTI);
			 * 
			 * }
			 */ // ATTENZIONE!!! GRIGLIA RIMOSSA CANCELLARE QUI PER ABILITARE
			
			g.setColor(Color.red);
			g.fillOval(melaX, melaY, GRANDEZZA_OGGETTI, GRANDEZZA_OGGETTI); // hey mela
			// MELA

			for (int i = 0; i < corpo; i++) {
				if (i == 0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], GRANDEZZA_OGGETTI, GRANDEZZA_OGGETTI);
				} else {
					g.setColor(new Color(45, 180, 0));
					g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))); // ATTENZIONE
																											// MULTICOLOR
																											// ATTIVATO
																											// ESCLUDERE
																											// QUESTA
																											// RIGA PER
																											// DISATTIVARE
					g.fillRect(x[i], y[i], GRANDEZZA_OGGETTI, GRANDEZZA_OGGETTI);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free", Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Punteggio: " + meleMangiate,
					(LARGHEZZA_SCHERMO - metrics.stringWidth("Punteggio: " + meleMangiate)) / 2, g.getFont().getSize());
		} else {
			gameOver(g);
		}
	}

	public void newApple() {

		melaX = random.nextInt((int) (LARGHEZZA_SCHERMO / GRANDEZZA_OGGETTI)) * GRANDEZZA_OGGETTI;
		melaY = random.nextInt((int) (ALTEZZA_SCHERMO / GRANDEZZA_OGGETTI)) * GRANDEZZA_OGGETTI;

	}

	public void move() {

		for (int i = corpo; i > 0; i--) {
			x[i] = x[i - 1];
			y[i] = y[i - 1];
		}

		switch (direzione) {

		case 'U': // su
			y[0] = y[0] - GRANDEZZA_OGGETTI;
			break;
		case 'D': // giu
			y[0] = y[0] + GRANDEZZA_OGGETTI;
			break;
		case 'L': // sinistra
			x[0] = x[0] - GRANDEZZA_OGGETTI;
			break;
		case 'R': // destra
			x[0] = x[0] + GRANDEZZA_OGGETTI;
			break;
		}

	}

	public void checkPoint() {

		if ((x[0] == melaX) && (y[0] == melaY)) {
			corpo++;
			meleMangiate++;
			newApple();
		}

	}

	public void checkCollisions() {
		// vede se sbatti contro il corpo
		for (int i = corpo; i > 0; i--) {
			if ((x[0] == x[i]) && (y[0] == y[i])) {
				corsa = false;
			}
		}

		// vede se sbatti contro i bordi sinistra

		if (x[0] < 0) {
			corsa = false;
		}

		// vede se sbatti contro i bordi destra

		if (x[0] > LARGHEZZA_SCHERMO) {
			corsa = false;
		}

		// vede se sbatti contro i bordi sopra

		if (y[0] < 0) {
			corsa = false;
		}

		// vede se sbatti contro i bordi sotto

		if (y[0] > ALTEZZA_SCHERMO) {
			corsa = false;
		}

		if (!corsa) {
			timer.stop();
		}
	}

	public void gameOver(Graphics g) {
		// Punteggio
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Punteggio Finale: " + meleMangiate,
				(LARGHEZZA_SCHERMO - metrics1.stringWidth("Punteggio: " + meleMangiate)) / 2, g.getFont().getSize());

		// Testo fine gioco
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Hai Perso :(", (LARGHEZZA_SCHERMO - metrics2.stringWidth("Hai Perso :(")) / 2,
				ALTEZZA_SCHERMO / 2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (corsa) {
			move();
			checkPoint();
			checkCollisions();
		}
		repaint();
	}

	public class MyKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if (direzione != 'R') {
					direzione = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if (direzione != 'L') {
					direzione = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if (direzione != 'D') {
					direzione = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if (direzione != 'U') {
					direzione = 'D';
				}
				break;
			}

		}
	}

}
