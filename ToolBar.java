package resources;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

	
public class ToolBar {
	
	 private static JPopupMenu sortList = new JPopupMenu("Popup Sort");
	 private static JPopupMenu groupList = new JPopupMenu("Popup Group");
	 private static JRadioButton  Ascending = new JRadioButton ("Ascending");
	 private static JRadioButton  Descending = new JRadioButton ("Descending");
	 
	 private static JRadioButton  ascending = new JRadioButton ("Ascending");
	 private static JRadioButton  descending = new JRadioButton ("Descending");
	 
	
	 
	 static JPopupMenu getSortList() {
		 return sortList;
	 }
	 
	 static JPopupMenu getgroupList() {
		 return groupList;
	 }
	
	 static String Copy(String src) {
		File source = new File(src); 
		return "Copy";
	}
	 
	 
	 static String Cut(String src) {
		 File source = new File(src); 
			return "Cut";
	 }
	 
	 
	 static String copyPath(String src) {
		 String path = src; 
			return "Copy Path";
	 }
	 
	 
	 static String copyFolder(String src) {
		 String path = src; 
			return path;
	 }
	 
	 
	 static void Paste(String src, String dest, String choix) {
		 File source = new File(src); 
		 File destination = new File(dest);
		 
		 switch(choix) {
		 	case "Copy":
		 		try {
					Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 		break;
		 		
		 	case "Cut":
		 		try {
					Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 		break;
		 		
		 	case "Copy Folder":
		 		try {
					Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 		break;
		 		
		 	case "Cut Folder":
		 		try {
					Files.move(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e) {
					e.printStackTrace();
				}
		 		break;
		 } 
	 }
	 
	 
	 static void Delete(String f){
		 File file = new File(f); 
		 
		 if(file.isFile()) {
		      file.delete();
		 }
		 else {
			 for (File subFile : file.listFiles()) {
		         if(subFile.isDirectory()) {
		        	 Delete(String.valueOf(subFile));
		         } else {
		            subFile.delete();
		         }
		      }
		 }
	      file.delete();

	      
	   }
	
	 
	 
	 static void newFolder(String dir) throws IOException {
		 dir = dir.replace("\\", "/") + "/New folder";
		 File file = new File(dir);
		 file.mkdirs();
	 }
	 
	 
	 static void sellectAll(JTable table){
		 table.selectAll();
		 
	 }
	 
	 
	 static void sortBy(JTable table, String directory, String choix1, boolean choix2) throws IOException{
		 int SortColNo = 0;
		 
		 switch(choix1) {
		 	case "Name" -> SortColNo = 0;
		 	case "Date Modified" -> SortColNo = 1;
		 	case "Size" -> SortColNo = 3;
		 }
			 
	    TableRowSorter<TableModel> ColSort = new TableRowSorter<>(table.getModel());
	    table.setRowSorter(ColSort);
	    List<RowSorter.SortKey> ColSortingKeys = new ArrayList<>();
	    
	    switch(String.valueOf(choix2)) {
	    	case "true" -> ColSortingKeys.add(new RowSorter.SortKey(SortColNo, SortOrder.DESCENDING));
	    	default -> ColSortingKeys.add(new RowSorter.SortKey(SortColNo, SortOrder.ASCENDING));
	    }
	  
	    ColSort.setSortKeys(ColSortingKeys);
	    ColSort.sort();

	    Descending.setSelected(false);
	    Ascending.setSelected(false);
	 
	 }
	 
	 static void popUpSort() {		
		//------------------------------ POP UP Button Sort --------------------------------	
			JMenuItem Name = new JMenuItem("Name");
			JMenuItem DateModified = new JMenuItem("Date Modified");
			JMenuItem Size = new JMenuItem("Size");
		
			ButtonGroup g = new ButtonGroup();
			
			g.add(Ascending);
			g.add(Descending);
			
			Name.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						sortBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Name", Descending.isSelected());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});

			
			DateModified.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						sortBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Date Modified", Descending.isSelected());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
		
			
			Size.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						sortBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Size", Descending.isSelected());
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			});
			

			sortList.add(Name);
			sortList.add(DateModified);
			sortList.add(Size);
			sortList.addSeparator();
			sortList.add(Ascending);
			sortList.add(Descending);
				
	 }
	 
	 
	 static void groupBy(JTable table, String directory, String choix1) {
		 File dir = new File(directory);
		 
	        
		 for(File f:dir.listFiles()) {
			 if((int) f.getName().toLowerCase().charAt(0) > (int) 'h') {
				 System.out.println("----------------");
			 }
			 System.out.println(f.getName());

		 }
		 
	 }

	 
	 
	 static void popUpGroupBy() {		
			//------------------------------ POP UP Button Sort --------------------------------	
				JMenuItem name = new JMenuItem("Name");
				JMenuItem dateModified = new JMenuItem("Date Modified");
				JMenuItem size = new JMenuItem("Size");
			
				ButtonGroup Gr = new ButtonGroup();
				
				Gr.add(Ascending);
				Gr.add(Descending);
				
				name.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						groupBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Name");
					}
				});

				
				dateModified.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							sortBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Date Modified", Descending.isSelected());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
			
				
				size.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try {
							sortBy(MyTest.getTable(), MyTest.getpathTreeSelected(), "Size", Descending.isSelected());
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				

				groupList.add(name);
				groupList.add(dateModified);
				groupList.add(size);
				groupList.addSeparator();
				groupList.add(ascending);
				groupList.add(descending);
					
		 }
	 

	 
	 
}
