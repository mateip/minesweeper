
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GraphicsInterface extends JButton implements MouseListener, ActionListener {

	private MinesweeperTable ms;
	private int numRows, numCols;
	private	MyButton buttons[][];
	private JFrame frame;
	private JLabel statusLabel;
	private JRadioButton easy;
	private JRadioButton medium;
	private JRadioButton hard;
	private int difficulty;
	private JButton newGame;
	private JPanel pane;

	public GraphicsInterface(int numRows, int numCols) {

		this.numRows = numRows;
		this.numCols = numCols;
		difficulty = 17;
		ms = new MinesweeperTable(numRows, numCols, difficulty);
		ms.setBombs();
		ms.computeNumberOfBombs();

		buttons = new MyButton[numRows][numCols];
	}

	public void setStateForAllButtons(){
		for (int i = 0; i < numRows; i ++) {
			for (int j = 0; j < numCols; j ++) {
				buttons[i][j].setText("");
				buttons[i][j].setEnabled(true);
				buttons[i][j].setIcon(null);
				if (ms.getVisited(i, j)) {
					buttons[i][j].setEnabled(false);
					if(ms.isBombAt(i, j) == 1) {
						ImageIcon bomb = new ImageIcon("bomb.png");
						Image img = bomb.getImage() ;  
						Image newimg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
						bomb = new ImageIcon(newimg);
						buttons[i][j].setIcon(bomb);
					} 
					else if (ms.getNumberOfBombsAround(i, j) >= 1) {
						String numbOfBombs = ms.getNumberOfBombsAround(i, j) + "";
						buttons[i][j].setText(numbOfBombs);
					}
				}
				else if (ms.isFlagged(i, j)) {
					ImageIcon flag = new ImageIcon("flag.png");
					Image img = flag.getImage() ;  
					Image newimg = img.getScaledInstance(20, 20,  java.awt.Image.SCALE_SMOOTH ) ;  
					flag = new ImageIcon(newimg);

					buttons[i][j].setIcon(flag);
				}
			}
		}
	}

	public void resetTable() {
		ms = new MinesweeperTable(numRows, numCols, difficulty);
		ms.setBombs();
		ms.computeNumberOfBombs();
		statusLabel.setText(" ");
	}


	public void makeGraphicsInterface() {
		frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//adds parent panel
		JPanel frameContainer = new JPanel();
		frame.add(frameContainer);
		frameContainer.setLayout(new BoxLayout(frameContainer, BoxLayout.Y_AXIS));

		//adds game selection panel with radio buttons and "new game" button
		JPanel gameSelection = new JPanel();
		gameSelection.setLayout(new GridLayout(1, 4));
		ButtonGroup radioButtonGroup = new ButtonGroup();
		easy = new JRadioButton("Easy", true);
		easy.addActionListener(this);
		medium = new JRadioButton("Medium");
		medium.addActionListener(this);
		hard = new JRadioButton("Hard");
		hard.addActionListener(this);
		radioButtonGroup.add(easy);
		radioButtonGroup.add(medium);
		radioButtonGroup.add(hard);
		gameSelection.add(easy);
		gameSelection.add(medium);
		gameSelection.add(hard);

		newGame = new JButton();
		newGame.setText("New Game ");
		gameSelection.add(newGame);
		newGame.addActionListener(this);
		frameContainer.add(gameSelection);

		//adds status panel: displays game over or game win
		JPanel status = new JPanel();
		statusLabel = new JLabel (" ");
		status.add(statusLabel);
		frameContainer.add(status);

		//adds panel that holds table
		pane = new JPanel();
		GridLayout layout = new GridLayout(numRows, numCols);
		layout.setHgap(0);
		layout.setVgap(0);
		pane.setLayout(layout);
		frame.setMaximumSize(new Dimension(300, 400));
		frame.setResizable(false);
		frameContainer.add(pane);

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j< numCols; j++) {
				MyButton button = new MyButton(ms, i, j);
				button.setPreferredSize(new Dimension(20, 20));
				pane.add(button);
				buttons[i][j] = button;
				button.addMouseListener(this);
			}
		}

		frame.pack();
		frame.setVisible(true);	
		ms.print();
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void mousePressed(MouseEvent e) {

		MyButton button = (MyButton)e.getSource();

		int row = button.row;
		int col = button.col;


		if (e.getButton() == e.BUTTON1) {
			if (ms.isFlagged(row, col)) {
				return;
			}
			ms.click(row, col);
			setStateForAllButtons();

			System.out.println(row + "," + col);
			ms.print();

			if (!ms.getGameOver()) {
				return;
			}

			if (ms.getGameWon()) {
				statusLabel.setText("You win! ");
				pane.setEnabled(false);
			}
			else {
				statusLabel.setText("You lose! ");
				for (int i = 0; i < numRows; i++) {
					for (int j = 0; j< numCols; j++) {
						ms.setVisited(i, j, true);
					}
				}
				setStateForAllButtons();

			}
		}
		else if(e.getButton() == e.BUTTON3){
			if (ms.isFlagged(row, col)) {
				ms.unFlag(row, col);
			} else {
				ms.flag(row, col);
			}
			setStateForAllButtons();
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == easy) {
			difficulty = 17;
		}
		else if (e.getSource() == medium) {
			difficulty = 20;
		}
		else if (e.getSource() == hard) {
			difficulty = 25;
		}
		else if(e.getSource() == newGame) {
			resetTable();
			setStateForAllButtons();

		}

	}	

}
