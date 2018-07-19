package count;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FindNCount {

	Integer totalRowCount = new Integer(0);
	Integer emptyRowCount = new Integer(0);
	Integer phisicalRowCount = new Integer(0);
	Integer logicalRowCount = new Integer(0);
	Double commentRowCount = new Double(0);
	Double ratingCommentLevel = new Double(0);
	
	public void openFile(String path, List<String> tokens){
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;
			Boolean commentEnter = false;
			Boolean quotesEnter = false;
			
			while ((sCurrentLine = br.readLine()) != null) {
				
				if(sCurrentLine.isEmpty())
					emptyRowCount++;
				
				if(sCurrentLine.contains("//"))
					commentRowCount++;
				if(sCurrentLine.contains("/*") || commentEnter == true){
					commentEnter = true;
					commentRowCount++;
				}
				if(sCurrentLine.contains("*/"))
					commentEnter = false;
				
				char[] strArr = sCurrentLine.toCharArray();
				for(int i = 0; i < strArr.length; i++){
					if(strArr[i] == '"' && quotesEnter == false){
						quotesEnter = true;
						continue;
					}
					else if(strArr[i] == '"' && quotesEnter == true){
						quotesEnter = false;
						continue;
					}
				}
				if(quotesEnter == false && commentEnter == false){
					for(int i = 0; i < tokens.size(); i++){
						if(sCurrentLine.contains(tokens.get(i)) && !sCurrentLine.contains("};") 
								&& !sCurrentLine.contains("for") && !sCurrentLine.contains("while") 
								&& !sCurrentLine.contains("if") && !sCurrentLine.contains("else")
								&& !sCurrentLine.contains("=="))
							logicalRowCount++;
					}
				}
				
				totalRowCount++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void findFiles(File file, List<String> tokens) {	
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("Directory of " + file.getPath() + " is:\n");
				String[] dirNames = file.list();
				for (int i = 0; i < dirNames.length; i++) {
					File f = new File(file.getPath() + "/" + dirNames[i]);
					if (f.isDirectory()) {
						System.out.println("-" + dirNames[i] + " is a directory");
						findFiles(f, tokens);
					} else if(f.getName().endsWith(".java")){
						System.out.println("-" + dirNames[i] + " is a file");
						//open file
						openFile(f.getPath(), tokens);
					}
				}
				//open file
				System.out.println();
			} else{
				System.out.println(file + " is a file!");
				//open file
				openFile(file.getPath(), tokens);
			}
		} else {
			System.out.println(file + " - doesn't exists! Ñheck it on accuracy!");
			return;
		}
	}
}
