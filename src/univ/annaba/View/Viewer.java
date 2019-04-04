package univ.annaba.View;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import univ.annaba.Control.ViewerConroller;

import javax.swing.JTree;

public class Viewer extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private ViewerConroller viewerConroller;


	

	/**
	 * Create the frame.
	 */
	public Viewer(String badSmellOntologyPath) {
		viewerConroller = new ViewerConroller(badSmellOntologyPath);
		setTitle("Viewer");
		Image image = null;
		try {
			image = ImageIO.read(new File("ressources/BadSmell.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		setIconImage(image);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JTree tree = new JTree();
		tree = viewerConroller.getHierarchy(tree);
		contentPane.add(tree, BorderLayout.WEST);
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Viewer frame = new Viewer("C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\BadSmellOntology.owl");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}