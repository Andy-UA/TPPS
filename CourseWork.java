package work;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//admin (read = true, execute = true, write = true)
//user  (read = true, execute = false, write = false)

public class CourseWork {
	
	public static File file = null;
	
	public static void findRole(String role, File file) {
		try {
			switch (role) {
			case "admin":
				file.setExecutable(true);
				file.setReadable(true);
				file.setWritable(true);
				System.out.println("After change!");
				System.out.println("Чтение разрешено: " + file.canRead());
				System.out.println("Изменение разрешено: " + file.canWrite());
				System.out.println("Запуск разрешен: " + file.canExecute());
				break;
			case "user":
				file.setExecutable(false);
				file.setReadable(true);
				file.setWritable(false);
				System.out.println("After change!");
				System.out.println("Чтение разрешено: " + file.canRead());
				System.out.println("Изменение разрешено: " + file.canWrite());
				System.out.println("Запуск разрешен: " + file.canExecute());
				break;
			default:
				System.out.println("Wrong second parametr!");
				break;
			}
		} catch (NullPointerException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void findFile(String path, File file) {	
		if (file.exists()) {
			if (file.isDirectory()) {
				System.out.println("Directory of " + path + " is:\n");
				String[] dirNames = file.list();
				File f = null;
				for (int i = 0; i < dirNames.length; i++) {
					f = new File(path + "/" + dirNames[i]);
					if (f.isDirectory()) {
						System.out.println(dirNames[i] + " is a directory");
						
					} else {
						System.out.println(dirNames[i] + " is a file");
					}
				}
			} else
				System.out.println(file + " is a file!");
		} else {
			System.out.println(file + " doesn't exists! Сheck it on accuracy!");
			return;
		}

	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter [path],[permission of files and folders (admin or user)]:");
		String S = sc.nextLine();
		String[] parts = S.split(",");
		if (parts.length == 2) {
			parts[0] = parts[0].trim();
			parts[1] = parts[1].trim().toLowerCase();
			file = new File(parts[0]);
			findFile(parts[0], file);
			if (file.exists()) {
				System.out.println("Before change!");
				System.out.println("Чтение разрешено: " + file.canRead());
				System.out.println("Изменение разрешено: " + file.canWrite());
				System.out.println("Запуск разрешен: " + file.canExecute());
			}
			System.out.println();
			findRole(parts[1], file);

		} else
			System.out.println("Wrong amount of parameters");
	}
}