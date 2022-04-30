package resources;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyTest extends JFrame  {
	
   private JToolBar barre = new JToolBar();
   private JTextField txtField1;
   private  JTextField txtField2;
   private JScrollPane scrollTree;
   private static JTable  table;
   private JScrollPane scrollTable;
   private JTree arbre;
   private DefaultMutableTreeNode racine;
   private String username;
   private HashMap<String, File> fileInfo;
   private String selectedData = null;
   private String pathFileSelected;
   private static String pathTreeSelected;
   private JPopupMenu menu = new JPopupMenu("Popup menu");
   private String Choix;
   private String autoPathFileSelected;
   private ArrayList<File> fileFound;
   private static Icon iconFile;

   private int pageTrace = 0;
   private int currentPageNumber = 0;
   private static ArrayList<String> page = new ArrayList<String>();
   private static String lastPathWord;
   private String pathForRegex = "";
   
   private String pathTreeSelected2;
   private TableModel tm;
   
   private static int sel [];
   
   private JButton copy = new JButton("Copy", new ImageIcon("src/resources/Copy.png")),
		   paste = new JButton("Paste", new ImageIcon("src/resources/Paste.png")),
		   delete = new JButton("Delete", new ImageIcon("src/resources/Delete.png")),
		   cut = new JButton("Cut", new ImageIcon("src/resources/Cut.png")),
		   copyPath = new JButton("Copy Path", new ImageIcon("src/resources/CopyPath.png")),
		   rename = new JButton("Rename", new ImageIcon("src/resources/Rename.png")),
		   newFolder = new JButton("New Folder", new ImageIcon("src/resources/NewFolder.png")),
		   sellectAll = new JButton("Select All", new ImageIcon("src/resources/SelectAll.png")),
		   content = new JButton("Content", new ImageIcon("src/resources/Content.png")),
		   details = new JButton("Details", new ImageIcon("src/resources/Details.png")),
		   groupBy = new JButton("Group By", new ImageIcon("src/resources/GroupBy.png")),
		   sort = new JButton("Sort By", new ImageIcon("src/resources/Sort.png")),
		   refrech = new JButton(new ImageIcon("src/resources/Refrech.png")),
		   back = new JButton(new ImageIcon("src/resources/Back.png")),
		   next = new JButton(new ImageIcon("src/resources/Next.png"));


   

   final String[] colHeads = { "File Name", "Date modified", "Read Only", "SIZE" };
   String[][] data = { { "", "", "", "", "" } };
   
   final String[] colHeadsContent = { "File Name", "Date modified" };
   String[][] dataContent = { { "", "" } };
			
   public MyTest() {  
	  setLayout(new BorderLayout(0, 20));  
	  setSize(800, 600);
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      setLocationRelativeTo(null);
      setTitle("Windows Explorer");
      addKeyListener(new KeyAction() {
    	  
      });
      
      
      Image icon = Toolkit.getDefaultToolkit().getImage("src/resources/Icon.png");    
      setIconImage(icon); 
      
      
      
      Menu();
	  listRoot();
	  Display();
	  buttonAction();
//	  ToolBar.popUpGroupBy();
	  popUp();
	  ToolBar.popUpSort();
      setVisible(true);
      
   }
   
   
   // ---------------------------------------------- Menu -----------------------------------------------------  
   // -------------- Home items
   public void Menu() {
	  barre.setFloatable(false);
      copy.setVerticalTextPosition(JButton.BOTTOM);
      copy.setHorizontalTextPosition(JButton.CENTER);
      
      paste.setVerticalTextPosition(JButton.BOTTOM);
      paste.setHorizontalTextPosition(JButton.CENTER);
      
      delete.setVerticalTextPosition(JButton.BOTTOM);
      delete.setHorizontalTextPosition(JButton.CENTER);
      
      cut.setVerticalTextPosition(JButton.BOTTOM);
      cut.setHorizontalTextPosition(JButton.CENTER);
      
      copyPath.setVerticalTextPosition(JButton.BOTTOM);
      copyPath.setHorizontalTextPosition(JButton.CENTER);
      
      rename.setVerticalTextPosition(JButton.BOTTOM);
      rename.setHorizontalTextPosition(JButton.CENTER);
      
      newFolder.setVerticalTextPosition(JButton.BOTTOM);
      newFolder.setHorizontalTextPosition(JButton.CENTER);
      
      sellectAll.setVerticalTextPosition(JButton.BOTTOM);
      sellectAll.setHorizontalTextPosition(JButton.CENTER);

      barre.add(copy);
      barre.add(paste);
      barre.add(cut);
      barre.add(copyPath);
      barre.addSeparator();
      barre.add(delete);
      barre.add(rename);
      barre.add(newFolder);    
      barre.addSeparator();
      barre.add(sellectAll);
      
      content.setVerticalTextPosition(JButton.BOTTOM);
      content.setHorizontalTextPosition(JButton.CENTER);
      
      details.setVerticalTextPosition(JButton.BOTTOM);
      details.setHorizontalTextPosition(JButton.CENTER);
      
      groupBy.setVerticalTextPosition(JButton.BOTTOM);
      groupBy.setHorizontalTextPosition(JButton.CENTER);
      
      sort.setVerticalTextPosition(JButton.BOTTOM);
      sort.setHorizontalTextPosition(JButton.CENTER);
      
      barre.add(content);
      barre.add(details);
      barre.addSeparator();
      barre.add(groupBy);
      barre.add(sort);
      add(barre, BorderLayout.NORTH);

      
   }
   
        
   
   // -------------------------------------------------- Tree ----------------------------------------------
   private void listRoot() {
	   
		racine = new DefaultMutableTreeNode("This PC");

		QuickAccess.connect();
		
		DefaultMutableTreeNode quickAccess = new DefaultMutableTreeNode("QuickAccess");
		DefaultMutableTreeNode desktop = new DefaultMutableTreeNode("Desktop");
		DefaultMutableTreeNode download = new DefaultMutableTreeNode("Downloads");
		DefaultMutableTreeNode document = new DefaultMutableTreeNode("Documents");
		DefaultMutableTreeNode music = new DefaultMutableTreeNode("Music");
		DefaultMutableTreeNode pictures = new DefaultMutableTreeNode("Pictures");
		DefaultMutableTreeNode videos = new DefaultMutableTreeNode("Videos");	
		DefaultMutableTreeNode disqueLocal = new DefaultMutableTreeNode("Local Disk (C:)");

		
		username = System.getProperty("user.name");

		File Desktop = new File("C:\\Users\\"+username+"\\Desktop");
		File QuickAccess = new File("C:\\Users\\"+username+"\\QuickAccess");
		File Download = new File("C:\\Users\\"+username+"\\Downloads");
		File Documents = new File("C:\\Users\\"+username+"\\Documents");
		File Music = new File("C:\\Users\\"+username+"\\Music");
		File Pictures = new File("C:\\Users\\"+username+"\\Pictures");
		File Videos = new File("C:\\Users\\"+username+"\\Videos");
		File DisqueLocal = new File("C:\\");

		
		createNode(QuickAccess,quickAccess);
		createNode(Desktop,desktop);
		createNode(Download,download);
		createNode(Documents,document);
		createNode(Music,music);
		createNode(Pictures,pictures);
		createNode(Videos,videos);
		createNode(DisqueLocal,disqueLocal);

		arbre = new JTree(this.racine);
		getContentPane().add(new JScrollPane(arbre));
		
	}
   
   
   
   // --------------- Create Node
	private void createNode(File file,DefaultMutableTreeNode node) {
		try {
			for (File nom : file.listFiles()) {
				if(nom.isDirectory()) {
					DefaultMutableTreeNode noeud = new DefaultMutableTreeNode(nom.getName() + "\\");
					node.add(listFile(nom, noeud));
				}
				
			}
		} catch (NullPointerException e) {
		}
		this.racine.add(node);
	}
	
	
	// --------- List File
	private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node) {
		int count = 0;
			File[] list = file.listFiles();
			if (list == null && file.isDirectory())
				return new DefaultMutableTreeNode(file.getName());
			for (File nom : list) {
				count++;
				if (count < 5) {
					DefaultMutableTreeNode subNode;
					if (nom.isDirectory()) {
						subNode = new DefaultMutableTreeNode(nom.getName() + "\\");
						node.add(subNode);

					} 
				}
			}
			return node;
		}

	
	
	// ------------------------------------------- TextField and table
	private void Display() {
		txtField1 = new JTextField();
		txtField2 = new JTextField();

		scrollTree = new JScrollPane(arbre);	
		
		final String[] colHeads = {"File Name", "Date modified", "Type", "SIZE"  };
		String[][] data = { { "", "", "", "", "" } };
		table = new JTable(data, colHeads);
		
		scrollTable = new JScrollPane(table);
		
		refrech.setBounds(554, 69, 16, 16);
		add(refrech);
		
		
		back.setBounds(2, 69, 16, 16);
		add(back);
		
		next.setBounds(32, 69, 16, 16);
		add(next);
		
		txtField1.setBounds(50, 67, 504, 20);
		add(txtField1);
		
		txtField2.setBounds(584, 67, 200, 20);
		txtField2.setText("Search");
		add(txtField2);
		
		add(scrollTree, BorderLayout.WEST);
		add(scrollTable, BorderLayout.CENTER);
		
		arbre.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				try {
					doMouseClicked(me);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});	
		
		
		txtField2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				txtField2.setText("");
			}
		});	
		
		
		
		txtField1.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	           int key = e.getKeyCode();
	           if (key == KeyEvent.VK_ENTER) {
	        	   	Toolkit.getDefaultToolkit().beep();
	        	   	
	        	   	showFiles(txtField1.getText());
	           }
	        }
	     });
		
		
		
		 txtField2.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	           int key = e.getKeyCode();
				fileFound = new  ArrayList<File>();
				String fileName = null;
				
	           if (key == KeyEvent.VK_ENTER) {
	        	   	Toolkit.getDefaultToolkit().beep();
	        	   	
	        	   	if(txtField2.getText().equals("")) {
	        	   		File pathTree = new File(pathTreeSelected);
        		    	fileFound.add(pathTree);
	        	   		showFiles2(fileFound);
	        	   	}
	        	   	
	        		for(File f: new File(pathTreeSelected).listFiles()) {
	        			
	        			int pos = f.getName().lastIndexOf(".");
	        			if(pos > 0 && pos < (f.getName().length() - 1)) {
	        				fileName = f.getName().substring(0, pos);
	        			}
	        			
	        			
	        			Pattern pattern = Pattern.compile(txtField2.getText(), Pattern.CASE_INSENSITIVE);
	        		    Matcher matcher = pattern.matcher(fileName);
	        		    boolean matchFound = matcher.find();

	        		    if(matchFound) {
	        		    	fileFound.add(f);
	        		    } 
	        			
	        		}
	        		showFiles2(fileFound);


	           }

	        }
	     });
		 
		 
		 
		 
		 
		
	}
	
   
	// ------------------------------------- Tree Action
	void openFile(File file) {
		 try {
	            Desktop desktop = Desktop.getDesktop();
	            desktop.open(file);
	        } catch (IOException e) {
	        	System.out.println(e);
	        }
	}
	
	
	
	void doMouseClicked(MouseEvent me) throws IOException {
		TreePath tp = arbre.getPathForLocation(me.getX(), me.getY());

		if (tp == null)
			return;
		
		pathTreeSelected = tp.toString();
		
		String s = tp.toString();
		s = s.replace("[", "");
		s = s.replace("This PC", "");
		s = s.replace("]", "");
		s = s.replace(", ", "\\");
		
		pathTreeSelected = pathTreeSelected.replace("[", "");
		pathTreeSelected = pathTreeSelected.replace("]", "");
		pathTreeSelected = pathTreeSelected.replace(", ", "\\");
		
		if (s.length()!=0) {
			if(pathTreeSelected.contains("Local Disk (C:)")) {
				pathTreeSelected = "C:\\";
				s = "C:\\";
				
				showFiles(pathTreeSelected);
				if (s.contains(".")) {
					s = "C:\\"+ s;
					File file = new File(s);
					openFile(file);
				}
			}
	
			pathTreeSelected = pathTreeSelected.replace("This PC", "C:\\" + "Users\\" + username);
			
			String pathDesign = "";
			pathDesign = pathTreeSelected.replace("\\", "").replace("C:", "This PC > ").replace("Users", "").replace(username, "");
			txtField1.setText(pathDesign);
			
			if(pathTreeSelected.equals("C:\\Users\\"+username+"\\QuickAccess")) {
				try {
					showFiles2(QuickAccess.getListFiles());
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				showFiles(pathTreeSelected);
			}
			
			

			
			if (s.contains(".")) {
				s = "C:\\Users\\" + username + s;
				File file = new File(s);
				openFile(file);
			}
		}
		addPageToList();
	}
	
	
	
	private void DesignPath() {
		String pathDesign = "";
		
		pathDesign = pathFileSelected.replace("\\", " > ").replace("C:", "This PC");
		txtField1.setText(pathDesign);
	}
	
	
	
	//------------------------------POP UP--------------------------------	
	 private void popUp() {
				JMenuItem Open = new JMenuItem("Open");
				JMenuItem Copy = new JMenuItem("Copy");
				JMenuItem Cut = new JMenuItem("Cut");
				JMenuItem Delete = new JMenuItem("Delete");
				JMenuItem Paste = new JMenuItem("Paste");
				JMenuItem Rename = new JMenuItem("Rename");
			
			    JMenu newOption = new JMenu("New");
			    newOption.setMnemonic(KeyEvent.VK_O);
			    
				JMenuItem newFolder = new JMenuItem("New Folder");
				JMenuItem word = new JMenuItem("Microsoft Word Document");
				JMenuItem excel = new JMenuItem("Microsoft Excel Worksheet");
				JMenuItem powerPoint = new JMenuItem("Microsoft PowerPoint Presentation");
				JMenuItem access = new JMenuItem("Microsoft Access Database");
				JMenuItem text = new JMenuItem("Text Document");
				
				newOption.add(newFolder);
				newOption.addSeparator();
				newOption.add(word);
				newOption.add(excel);
				newOption.add(access);
				newOption.add(powerPoint);
				newOption.add(text);
			
				

				Open.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						File f = new File(pathFileSelected);
						
						if(f.isFile()) {
							openFile(f);

								try {
									QuickAccess.addFile(String.valueOf(f));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
							
						}
						else {
							pathTreeSelected = pathFileSelected;
							
							addPageToList();
							showFiles(pathFileSelected);
							DesignPath();
						}

					}
				});
				

				Copy.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						copy();
					}
				});

				Cut.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					cut();
					}
				});
				
				Delete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						delete();
					}
				});
				Paste.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						paste();
					}
				});
				Rename.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							renameFile();
							pathFileSelected = autoPathFileSelected;
						}catch(Exception ex) {
						}
					}
				});
				

				newFolder.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						createNewFolder();
					}
				});

				word.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String m = JOptionPane.showInputDialog("Rename", "New Microsoft Word Document");
						File word = new File(pathTreeSelected+"\\"+m+".docx");
						
						int i = 1;
						while(word.exists()) {
							word = new File(pathTreeSelected+"\\"+m+i+".docx");
						}
						try {
							word.createNewFile();
							showFiles(pathTreeSelected);
							openFile(word);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				excel.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String m = JOptionPane.showInputDialog("Rename", "New Microsoft Excel Worksheet");
						File exc = new File(pathTreeSelected+"\\"+m+".xlsx");
						int i = 1;
						while(exc.exists()) {
							exc = new File(pathTreeSelected+"\\"+m+i+".docx");
						}
						try {
							exc.createNewFile();
							showFiles(pathTreeSelected);
							openFile(exc);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				access.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String m = JOptionPane.showInputDialog("Rename", "New Access Database");
						File acc = new File(pathTreeSelected+"\\"+m+".accdb");
						int i = 1;
						while(acc.exists()) {
							acc = new File(pathTreeSelected+"\\"+m+i+".docx");
						}
						try {
							acc.createNewFile();
							showFiles(pathTreeSelected);
							openFile(acc);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				powerPoint.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String m = JOptionPane.showInputDialog("Rename", "New Microsoft  PowerPoint Presentation");
						File pow = new File(pathTreeSelected+"\\"+m+".pptx");
						int i = 1;
						while(pow.exists()) {
							pow = new File(pathTreeSelected+"\\"+m+i+".docx");
						}
						try {
							pow.createNewFile();
							showFiles(pathTreeSelected);
							openFile(pow);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});

				text.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String m = JOptionPane.showInputDialog("Rename", "New Text Document");
						File tex = new File(pathTreeSelected+"\\"+m+".txt");
						int i = 1;
						while(tex.exists()) {
							tex = new File(pathTreeSelected+"\\"+m+i+".docx");
						}
						try {
							tex.createNewFile();
							showFiles(pathTreeSelected);
							openFile(tex);

						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				menu.add(Open);
				menu.add(Cut);
				menu.add(Copy);
				menu.add(Paste);
				menu.addSeparator();
				menu.add(Delete);
				menu.add(Rename);
				menu.add(newOption);
	 }
	 
	 
	 
	 public final static class IconCellRenderer extends DefaultTableCellRenderer {
		 
			//private final static ImageIcon icon = new ImageIcon(iconFile);
	 
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				
				Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				JLabel label = (JLabel)component;
				label.setIcon(iconFile);
				
				return component;
			}
		}
	 
	 
	 
	 public static Icon getIcon(String fileName) {
		   File file = new File(fileName);
		   Icon icon = FileSystemView.getFileSystemView().getSystemIcon(file);
		    
		   return icon;
	   }
	
	
	 void showFiles(String filename) {
		 File temp = new File(filename);
		 data = new String[][] { { "", "", "", "" } };
		 remove(scrollTable);	

			
			table = new JTable(data, colHeads) {     
		    	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
		            return false;
		        }
		      };
		      
			table.setShowGrid(false);
			table.setShowHorizontalLines(false);
			table.setShowVerticalLines(false);
			
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	      table.setColumnSelectionAllowed(false);
	      table.setRowSelectionAllowed(true);
	      
			
			scrollTable = new JScrollPane(table);
			add(scrollTable, BorderLayout.CENTER);
			setVisible(true);

			if (!temp.exists())
				return;
			if (!temp.isDirectory())
				return;
			
			
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy hh:mm  a");
			fileInfo = new HashMap<String, File>();
			File[] filelist = temp.listFiles(); 
			int fileCounter = 0;
			data = new String[filelist.length][4];
			long tempi = 0;
			for (int i = 0; i < filelist.length; i++) {
				long tempo = filelist[i].lastModified();
				if (tempo>tempi) {
					tempi = tempo;
					autoPathFileSelected = filelist[i].getAbsolutePath();
				}
				iconFile = getIcon(String.valueOf(filelist[i]));
				data[fileCounter][0] = new String(filelist[i].getName());
				data[fileCounter][1] = new String(sdf.format(filelist[i].lastModified()) + "");
				data[fileCounter][2] = new String(!filelist[i].canWrite() + "");
				data[fileCounter][3] = new String(Math.round((double) filelist[i].length()/1024) + "  kb");
				
			      
				fileInfo.put(data[fileCounter][0], filelist[i]);
				fileCounter++;

			}


		
			String dataTemp[][] = new String[fileCounter][4];
			for (int k = 0; k < fileCounter; k++)
				dataTemp[k] = data[k];
			data = dataTemp;
			
			remove(scrollTable);
			
			table = new JTable(data, colHeads) {
		    	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
		            return false;
		        }
		      }; 
		      
		      tableAction();

		
		
	    

		
		add(scrollTable, BorderLayout.CENTER);
		setVisible(true);

	}
	
	// --------------------------------------- ToolBar -------------------------------------
	void buttonAction() {
		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				copy();
				
			}
		});
		
		
		cut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cut();
			}
		});
		
		
		copyPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Choix = ToolBar.copyPath(pathFileSelected);
			}
		});
		
		
		paste.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				paste();
			}
		});
			
		
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				}
		});
		

		newFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			createNewFolder();
			}
		});
		
		rename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				renameFile();
				pathFileSelected = autoPathFileSelected;

			}
		});
		
		sellectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
					ToolBar.sellectAll(table);
					sel = table.getSelectedRows();
					tm = table.getModel();
			}
		});
		
		
		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ToolBar.getSortList().show(sort, sort.getWidth()/2-26, sort.getHeight()/2+30);
								
		}
	});
		
		
		
	groupBy.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			ToolBar.getgroupList().show(groupBy, groupBy.getWidth()/2-31, groupBy.getHeight()/2+30);

		}
	});
		
	
		
		
	details.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			showFiles(pathTreeSelected);
		}
	});
	
	
	refrech.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			showFiles(pathTreeSelected);
		}
	});
	
	back.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			back();
			
		}
	});
	
	next.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if(currentPageNumber < pageTrace) {
			try {
				currentPageNumber ++;
				
				showFiles(page.get(currentPageNumber));
			}catch(Exception ex) {
				System.out.println(ex);
			}
		}
	
		}
	});
	}
	
	void back() {
		if (currentPageNumber>0) {
			try {
			currentPageNumber --;
			showFiles(page.get(currentPageNumber));
			}catch(Exception ex) {
				System.out.println(ex);
			}
		}
	}
		
	void copy() {
		sel = table.getSelectedRows();
		tm = table.getModel();
		
		pathTreeSelected2 = pathTreeSelected;
		if(sel.length == 0) {
			Choix = ToolBar.Copy(pathFileSelected);
		
		
		}else {
			Object value;
			for(int i = 0; i<sel.length; i++) {
		          TableModel tm = table.getModel();
		          value = tm.getValueAt(sel[i],0);
		          Choix = ToolBar.Copy(pathTreeSelected+"\\"+value.toString());
		}
			showFiles(pathTreeSelected);
			
	}
	}
	
	void cut() {
		sel = table.getSelectedRows();
		tm = table.getModel();
		pathTreeSelected2 = pathTreeSelected;
		if(sel.length == 0) {
			Choix = ToolBar.Cut(pathFileSelected);
		
		
		}else {
			Object value;
			for(int i = 0; i<sel.length; i++) {
		          TableModel tm = table.getModel();
		          value = tm.getValueAt(sel[i],0);
		          Choix = ToolBar.Cut(pathTreeSelected+"\\"+value.toString());
		}
			showFiles(pathTreeSelected);
	}

	}
	
	void paste() {
		if(sel.length == 0) {
			ToolBar.Paste(pathFileSelected, String.format("%s\\%s", pathTreeSelected, selectedData), Choix);

			}else {
				
				Object value;
				for(int i = 0; i<sel.length; i++) {
			         
			          value = tm.getValueAt(sel[i],0);
						ToolBar.Paste((pathTreeSelected2+"\\"+value.toString()), String.format("%s\\%s", pathTreeSelected, value.toString()), Choix);
					
				}
			}

		showFiles(pathTreeSelected);
	}
	
	void delete() {
		sel = table.getSelectedRows();
		tm = table.getModel();
		if(sel.length == 0) {
			ToolBar.Delete(pathFileSelected);
			
			}else {
				Object value;
				for(int i = 0; i<sel.length; i++) {
			   
			          value = tm.getValueAt(sel[i],0);
			          ToolBar.Delete(pathTreeSelected+"\\"+value.toString());
			}
				showFiles(pathTreeSelected);
		}
	}
	
	void createNewFolder() {
		try {
			ToolBar.newFolder(pathTreeSelected);
			showFiles(pathTreeSelected);
			pathFileSelected = autoPathFileSelected;
			renameFile();
			
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public static String getpathTreeSelected(){
		return pathTreeSelected;
	}
	
	public static JTable getTable(){
		return table;
	}
	
	void addPageToList() {
		regexHelper();
		if (page.isEmpty()){
			page.add(pathTreeSelected);
			pageTrace = 0;
			currentPageNumber = 0;
			
		}
		else if (regex(lastPathWord,pathForRegex)) {
			if(pageTrace > currentPageNumber ) {
			page.add(pageTrace, pathTreeSelected);
			currentPageNumber = pageTrace;
			}else {
				page.add(pathTreeSelected);
				pageTrace++;
				currentPageNumber ++;
			}
		}
		
		else {
			page.clear();
			page.add(pathFileSelected);
			pageTrace = 0;
			currentPageNumber = 0;
			
		}
	}
	
	//-----------------------------------REGEX------------------------------------
	static boolean regex(String word1, String word2) {
		
		Pattern pattern = Pattern.compile(word2, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(word2);
	    return matcher.find();
	   
	}
	
	
	
	void regexHelper() {
		if(pathFileSelected != null) {
			String pathFileSelected2 = pathFileSelected.replace("\\", "-");
			String file = "";
			pathForRegex ="";
			
			String tab[] = pathFileSelected2.split("-");
			for(int i =0; i<tab.length; i++) {
				file = tab[i];
			}
			lastPathWord = file;
			

	       
	        for(int i =0; i<tab.length; i++) {
	
	        	pathForRegex += tab[i];
	        	pathForRegex +=" ";
			}
		}
	}
	void renameFile() {
		if(pathFileSelected != null) {
		String pathFileSelected2 = pathFileSelected.replace("\\", "-");
		String file = "";
		String newPath = "";
		
		
		String tab[] = pathFileSelected2.split("-");
		for(int i =0; i<tab.length; i++) {
			file = tab[i];
		}
		lastPathWord = file;
		String newArr[] = Arrays.copyOf(tab, tab.length - 1);
		
        File file1 = new File(pathFileSelected);
        String m = JOptionPane.showInputDialog("Rename", file);
       
        for(int i =0; i<newArr.length; i++) {
        	newPath += newArr[i];
        	newPath +="\\";
        	
        	pathForRegex += newArr[i];
        	pathForRegex +=" ";
		}
        
        newPath = newPath+=m;
        File rename = new File(newPath);

        
        file1.renameTo(rename);
	  showFiles(pathTreeSelected);
	  table.setEnabled(true);
	  
		}
		
	}
	
	
	
	
	
void showFiles2(ArrayList<File> listFiles) {
		
		data = new String[][] { { "", "", "", "" } };
		remove(scrollTable);	

		table = new JTable(data, colHeads);
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		
		table = new JTable(data, colHeads) {
	    	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	        }
	      };
		add(scrollTable, BorderLayout.CENTER);
		setVisible(true);
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy hh:mm  a");
		fileInfo = new HashMap<String, File>();
		int fileCounter = 0;
		data = new String[listFiles.size()][4];
		long tempi = 0;

		for(File temp: listFiles) {
			long tempo = temp.lastModified();
			if (tempo>tempi) {
				tempi = tempo;
				autoPathFileSelected = temp.getAbsolutePath();
			}
			iconFile = getIcon(String.valueOf(temp));
			data[fileCounter][0] = new String(temp.getName());
			data[fileCounter][1] = new String(sdf.format(temp.lastModified()) + "");
			data[fileCounter][2] = new String(!temp.canWrite( ) + "");
			data[fileCounter][3] = new String(Math.round((double) temp.length()/1024) + "  kb");
			
		      
			fileInfo.put(data[fileCounter][0], temp);
			fileCounter++;
		}


	
	String dataTemp[][] = new String[fileCounter][4];
	for (int k = 0; k < fileCounter; k++)
		dataTemp[k] = data[k];
	data = dataTemp;
	
	remove(scrollTable);
	
	table = new JTable(data, colHeads) {
  	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
          return false;
      }
    };

    tableAction();
	
	add(scrollTable, BorderLayout.CENTER);
	setVisible(true);

}
	

	void showFilesContent(String filename) {
		
		File temp = new File(filename);
		dataContent = new String[][] { { "", "" } };
		remove(scrollTable);	

		table = new JTable(data, colHeads) {
	    	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
	            return false;
	        }
	      };
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		
		scrollTable = new JScrollPane(table);
		add(scrollTable, BorderLayout.CENTER);
		setVisible(true);

		if (!temp.exists())
			return;
		if (!temp.isDirectory())
			return;
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyy hh:mm  a");
		fileInfo = new HashMap<String, File>();
		File[] filelist = temp.listFiles(); 
		int fileCounter = 0;
		dataContent = new String[filelist.length][2];
		long tempi = 0;
		for (int i = 0; i < filelist.length; i++) {
			long tempo = filelist[i].lastModified();
			if (tempo>tempi) {
				tempi = tempo;
				autoPathFileSelected = filelist[i].getAbsolutePath();
			}
			iconFile = getIcon(String.valueOf(filelist[i]));
			dataContent[fileCounter][0] = new String(filelist[i].getName());
			dataContent[fileCounter][1] = new String(sdf.format(filelist[i].lastModified()));			
		      
			fileInfo.put(dataContent[fileCounter][0], filelist[i]);
			fileCounter++;

		}


	
	String dataTemp[][] = new String[fileCounter][2];
	for (int k = 0; k < fileCounter; k++)
		dataTemp[k] = dataContent[k];
	dataContent = dataTemp;
	
	remove(scrollTable);
	
	table = new JTable(data, colHeads) {
  	  public boolean editCellAt(int row, int column, java.util.EventObject e) {
          return false;
      }
    };

    tableAction();

}
	
	
	
	void tableAction() {
		table.addKeyListener(new KeyAction() {
	    	 public void keyTyped(KeyEvent e) {
	   		  switch(e.getKeyChar())
	   			{
	   				case '\u0008':
	   				back();
	   				break;

	   				case '\u007F':
	   				delete();
	   				break;


	   			}
	   	  }
	    	 public void keyPressed(KeyEvent ke) {
	    			if (ke.getKeyCode() == KeyEvent.VK_CONTROL && ke.getKeyCode() == KeyEvent.VK_CONTROL)
	    				System.out.println("ctrl");
	    	 }

	    });
		
		
		
		 table.addMouseListener(new MouseAdapter() {
	         public void mouseClicked(MouseEvent me) {
	            if (me.getClickCount() == 2) {     
	  
	            	File f = new File(pathFileSelected);
					
					if(f.isFile()) {
						openFile(f);

							try {
								QuickAccess.addFile(String.valueOf(f));
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
						
					}
					else {
						pathTreeSelected = pathFileSelected;
						
						addPageToList();
						showFiles(pathFileSelected);
						DesignPath();
					}
	            }
	         }
	      });
		 
		 
		 table.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
		           int key = e.getKeyCode();
					
		           if (key == KeyEvent.VK_ENTER) {
		        	   	Toolkit.getDefaultToolkit().beep();
		        	   	
		        	   	File f = new File(pathFileSelected);
						
						if(f.isFile()) {
							openFile(f);

								try {
									QuickAccess.addFile(String.valueOf(f));
								} catch (SQLException e1) {
									e1.printStackTrace();
								}
						}
						else {
							pathTreeSelected = pathFileSelected;
							
							addPageToList();
							showFiles(pathFileSelected);
							DesignPath();
						}
		           }

		        }
		     });
		
		
		table.getColumnModel().getColumn(0).setCellRenderer(new IconCellRenderer());

		
		table.setShowGrid(false);
		table.setShowHorizontalLines(false);
		table.setShowVerticalLines(false);
		table.setCellSelectionEnabled(true);  

		
		scrollTable = new JScrollPane(table);
		
		table.addMouseListener(new java.awt.event.MouseAdapter(){
			public void mouseClicked(java.awt.event.MouseEvent e){
				table.setEnabled(false);

				
				if (e.getClickCount() == 2 ||(e.getClickCount() == 1 && e.getButton() == MouseEvent.BUTTON1)) {
					table.setEnabled(true);
					int[] row = table.getSelectedRows();
					int[] col = table.getSelectedColumns();
					
					
					for (int i = 0; i < row.length; i++) {
				          for (int j = 0; j < col.length; j++) {
				            selectedData = (String) table.getValueAt(row[i], col[j]);
				          }
				        }
				     try {
				    	 File f = new File(selectedData);
					        for(String fileName: fileInfo.keySet()) {
								if(selectedData.equals(fileName)) {
									File fileSelected = fileInfo.get(fileName);
									f = new File(String.valueOf(fileSelected));
									pathFileSelected = String.valueOf(f);
									selectedData = fileName;
								}
							}
					        if (e.getClickCount() == 2) {
					        	 if(f.isFile()) {
							        	openFile(f);
							        	table.setEnabled(true);
							        }
							        else {
							        	showFiles(String.valueOf(f));
							        	table.setEnabled(true);
							        }
					        }
				     }catch(Exception ex) {
				    	 System.out.println(ex);
				     }
			        
			       
			        
				}
		
				if (e.getButton() == MouseEvent.BUTTON3){
					menu.show(e.getComponent(), e.getX(), e.getY());
					
				}
		}
	});
	}
	

	
	class KeyAction implements KeyListener {
		  public void keyTyped(KeyEvent e) {
			  switch(e.getKeyChar())
				{
					case '\u0008':
					System.out.println("Backspace");
					break;

					case '\u007F':
					System.out.println("Delete");
					break;

					default:
					System.out.println(":)");
					break;
				}
		  }

		  
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		}
	
   public static void main(String[] args) { new MyTest();}
}












