package univ.annaba.View;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;



import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import univ.annaba.Control.MainInterfaceController;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;


public class MainInterface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField sourceCodeField;
	private JEditorPane conceptsEditorPane;
	private JScrollPane sourceCodeScrollPane;
	private MainInterfaceController controller;
	private String sourceCodeFilePath ="";
	private String chooserPath = "/home/moise/Documents/example/";
	private JScrollPane metricsScrollPane;
	private JEditorPane metricsEditorPane;
	protected String ontologyPath = "/home/moise/Documents/example/codeOntology.owl";
	protected String ontologyURI = "http://www.semanticweb.org/toshiba/ontologies/2017/4/untitled-ontology-77#";
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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 961, 443);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mnFile.add(mntmClose);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmEditConcepts = new JMenuItem("Edit Concepts");
		mnEdit.add(mntmEditConcepts);
		
		JMenuItem mntmEditMetrics = new JMenuItem("Edit Metrics");
		mnEdit.add(mntmEditMetrics);
		
		JMenuItem mntmEditCodeOntology = new JMenuItem("Edit Code Ontology");
		mnEdit.add(mntmEditCodeOntology);
		
		JMenuItem mntmEditBadsmellsOntology = new JMenuItem("Edit badsmells Ontology");
		mnEdit.add(mntmEditBadsmellsOntology);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmHelpContents = new JMenuItem("Help contents");
		mnHelp.add(mntmHelpContents);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.DEFAULT_ROWSPEC,
				com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblDetectingRefactoringOpportunities = new JLabel("Detecting Refactoring Opportunities");
		lblDetectingRefactoringOpportunities.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		contentPane.add(lblDetectingRefactoringOpportunities, "18, 2");
		
		sourceCodeField = new JTextField();
		contentPane.add(sourceCodeField, "16, 6, 5, 1, fill, default");
		sourceCodeField.setColumns(10);
		
		
		
		JButton btnLoadCode = new JButton("Load Code");
		btnLoadCode.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				
			JFileChooser sourceCodeChooser = new JFileChooser(new File(chooserPath));
			sourceCodeChooser.showOpenDialog(null);
			File file = sourceCodeChooser.getSelectedFile();
			if(file != null) {
			sourceCodeFilePath = file.getAbsolutePath();
			sourceCodeField.setText(sourceCodeFilePath);
			}
			}
		});
		contentPane.add(btnLoadCode, "26, 6");
		
		metricsScrollPane = new JScrollPane();
		metricsEditorPane = new JEditorPane();
		metricsScrollPane.setViewportView(metricsEditorPane);
		contentPane.add(metricsScrollPane, "16, 12, fill, fill");
		
		JButton btnGenerateMetrics = new JButton("Generate Metrics");
		btnGenerateMetrics.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

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
				controller = new MainInterfaceController();
				String text="";
						text = controller.generateConceptsReport(sourceCodeFilePath);
						conceptsEditorPane.setText(text);
			
			}

		
		});
		
		contentPane.add(btnGenerateConcepts, "20, 10");
		
		textField_1 = new JTextField();
		contentPane.add(textField_1, "16, 14, 5, 1, fill, default");
		textField_1.setColumns(10);
		
		
		JButton btnDetectLongMethod = new JButton("Generate Code Ontology");
		contentPane.add(btnDetectLongMethod, "26, 14");
		
		textField_2 = new JTextField();
		contentPane.add(textField_2, "16, 16, 5, 1, fill, default");
		textField_2.setColumns(10);
		
		JButton btnDetectDeadCode = new JButton("Enrich Code Ontology");
		contentPane.add(btnDetectDeadCode, "26, 16");
		
		JButton btnGenerateBadSmells = new JButton("Generate Bad smells Ontology");
		contentPane.add(btnGenerateBadSmells, "18, 18");
	}

}