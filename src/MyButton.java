import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;


public class MyButton extends JButton  {


	MinesweeperTable ms;
	int row;
	int col;

	public MyButton (MinesweeperTable ms, int row, int col ){
		this.ms = ms;
		this.row = row;
		this.col = col;
	}

}
