package univ.annaba.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import univ.annaba.Control.MainInterfaceController;

import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField ontologyTextField;
	private JTextField enrichOntologyTextField;
	private JTextField sourceCodeField;
	private JEditorPane conceptsEditorPane;
	private JScrollPane sourceCodeScrollPane;
	private MainInterfaceController controller;
	private String sourceCodeFilePath = "";
	private String codeOntologyOutputPath = "";
	private String badSmellOntologyOutputPath = "";
	private String chooserPath = "C:\\Users\\Administrateur\\Documents\\ONTIROO\\example\\";
	private JScrollPane metricsScrollPane;
	private JEditorPane metricsEditorPane;
	private JTextField badSmellOntologyTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainInterface frame = new MainInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainInterface() {
		setTitle("ONTIROO");
		Image image = null;
		try {
			image = ImageIO.read(new File("ressources/BadSmell.png"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		setIconImage(image);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 961, 443);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				loadCode();
			}
		});
		mnFile.add(mntmNew);

		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadCode();
			}
		});
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Generate Code Ontology");
		mntmSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateCodeOntology();
			}
		});
		mnFile.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Generate Bad Smell Ontology");
		mntmSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateBadSmellOntology();
			}
		});
		mnFile.add(mntmSaveAs);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		mnFile.add(mntmClose);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmEditConcepts = new JMenuItem("Generate Concepts");
		mntmEditConcepts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateConcepts();
			}
		});
		mnEdit.add(mntmEditConcepts);

		JMenuItem mntmEditMetrics = new JMenuItem("Generate Metrics");
		mntmEditMetrics.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateMetrics();
			}
		});
		mnEdit.add(mntmEditMetrics);

		JMenuItem mntmEditCodeOntology = new JMenuItem("Generate Code Ontology");
		mntmEditCodeOntology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateCodeOntology();
			}
		});
		mnEdit.add(mntmEditCodeOntology);
		
		JMenuItem mntEnrichCodeOntology = new JMenuItem("Enrich Code Ontology");
		mntmEditCodeOntology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enrichCodeOntology();
			}
		});
		mnEdit.add(mntEnrichCodeOntology);

		JMenuItem mntmEditBadsmellsOntology = new JMenuItem("Generate badsmells Ontology");
		mntmEditBadsmellsOntology.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				generateBadSmellOntology();
			}
		});
		mnEdit.add(mntmEditBadsmellsOntology);

		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("About ONTIROO");
		mnHelp.add(mntmAbout);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(
				new ColumnSpec[] { com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC, ColumnSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC, },
				new RowSpec[] { com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC, RowSpec.decode("default:grow"),
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
						com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		JLabel lblDetectingRefactoringOpportunities = new JLabel("Detecting Bad Smells in Objct Oriented Code");
		lblDetectingRefactoringOpportunities.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		contentPane.add(lblDetectingRefactoringOpportunities, "18, 2");

		sourceCodeField = new JTextField();
		contentPane.add(sourceCodeField, "16, 6, 5, 1, fill, default");
		sourceCodeField.setColumns(10);

		JButton btnLoadCode = new JButton("Load Code");
		btnLoadCode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				loadCode();
			}
		});
		contentPane.add(btnLoadCode, "26, 6");

		metricsScrollPane = new JScrollPane();
		metricsEditorPane = new JEditorPane();
		metricsScrollPane.setViewportView(metricsEditorPane);
		contentPane.add(metricsScrollPane, "16, 12, 2, 1, fill, fill");

		JButton btnGenerateMetrics = new JButton("Generate Metrics");
		btnGenerateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateMetrics();
			}
		});
		contentPane.add(btnGenerateMetrics, "16, 10");

		sourceCodeScrollPane = new JScrollPane();
		conceptsEditorPane = new JEditorPane();
		sourceCodeScrollPane.setViewportView(conceptsEditorPane);
		contentPane.add(sourceCodeScrollPane, "20, 12, fill, fill");

		JButton btnGenerateConcepts = new JButton("Generate Concepts");
		btnGenerateConcepts.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateConcepts();
			}
		});

		contentPane.add(btnGenerateConcepts, "20, 10");

		ontologyTextField = new JTextField();
		contentPane.add(ontologyTextField, "16, 14, 5, 1, fill, default");
		ontologyTextField.setColumns(10);

		JButton btnDetectLongMethod = new JButton("Generate Code Ontology");
		btnDetectLongMethod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateCodeOntology();
			}
		});
		contentPane.add(btnDetectLongMethod, "26, 14");

		enrichOntologyTextField = new JTextField();
		contentPane.add(enrichOntologyTextField, "16, 16, 5, 1, fill, default");
		enrichOntologyTextField.setColumns(10);

		JButton btnEnrichCodeOntology = new JButton("Enrich Code Ontology");
		btnEnrichCodeOntology.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				enrichCodeOntology();
			}
		});
		contentPane.add(btnEnrichCodeOntology, "26, 16");
		ontologyTextField.setColumns(10);
		badSmellOntologyTextField = new JTextField();
		contentPane.add(badSmellOntologyTextField, "16, 18, 5, 1, fill, default");
		

		JButton btnGenerateBadSmells = new JButton("Generate Bad smells Ontology");
		btnGenerateBadSmells.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				generateBadSmellOntology();
				new Viewer(badSmellOntologyOutputPath);
			}
		});
		contentPane.add(btnGenerateBadSmells, "26, 18");
	}
	
	/**
	 * the button open handler 
	 */
	public void loadCode(){
		JFileChooser sourceCodeChooser = new JFileChooser(new File(chooserPath));
		FileFilter filter = new FileNameExtensionFilter("Java File", "java");
		sourceCodeChooser.setFileFilter(filter);
		int rVal = sourceCodeChooser.showOpenDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = sourceCodeChooser.getSelectedFile();
			sourceCodeFilePath = file.getAbsolutePath();
			sourceCodeField.setText(sourceCodeFilePath);
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
		}
	}
	/**
	 * generates the code Ontology.
	 */
	public void generateCodeOntology(){
		JFileChooser chooser = new JFileChooser(new File(chooserPath));
		FileFilter filter = new FileNameExtensionFilter("OWL File", "owl");
		chooser.setFileFilter(filter);
		int rVal = chooser.showSaveDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file.getName().endsWith(".owl")) {
				codeOntologyOutputPath = file.getPath();
			} else {
				codeOntologyOutputPath = file.getPath() + ".owl";
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
		}
		controller.generateOntology(codeOntologyOutputPath);
		ontologyTextField.setText(codeOntologyOutputPath);
	}
	/**
	 * generate Bad Smell Ontology.
	 */
	public void generateBadSmellOntology() {
		JFileChooser chooser = new JFileChooser(new File(chooserPath));
		FileFilter filter = new FileNameExtensionFilter("OWL File", "owl");
		chooser.setFileFilter(filter);
		int rVal = chooser.showSaveDialog(null);
		if (rVal == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			if (file.getName().endsWith(".owl")) {
				badSmellOntologyOutputPath = file.getPath();
			} else {
				badSmellOntologyOutputPath = file.getPath() + ".owl";
			}
		}
		if (rVal == JFileChooser.CANCEL_OPTION) {
		}
		controller.generateBadSmellOntology(badSmellOntologyOutputPath);
		badSmellOntologyTextField.setText("Done! "+ badSmellOntologyOutputPath);
	}
	
	public void generateMetrics() {
		String report = controller.generateMetricsReport();
		metricsEditorPane.setText(report);
	}
	
	public void generateConcepts() {
		controller = new MainInterfaceController();
		String text = "";
		text = controller.generateConceptsReport(sourceCodeFilePath);
		conceptsEditorPane.setText(text);
	}
	
	public void enrichCodeOntology() {
		controller.enrichOntology(codeOntologyOutputPath);
		enrichOntologyTextField.setText("Done! " + codeOntologyOutputPath);
	}

}