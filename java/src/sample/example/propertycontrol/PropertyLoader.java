package sample.example.propertycontrol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PropertyLoader {
	private static final ExecutorService executor = Executors.newFixedThreadPool(1);
	
	public void init() {
		File propertyFile = new File("properties/config.properties");
		try {
			loadProperties(propertyFile);
			executor.submit(loaderThread(propertyFile));
		} catch (FileNotFoundException e) {
			System.out.println("File not found : " + propertyFile.getAbsolutePath());
		} catch (IOException e) {
			System.out.println("failed to read file : " + propertyFile.getAbsolutePath());
		}
	}
	
	private void loadProperties(File propertyFile) throws IOException {
		if(propertyFile.exists()) {
			FileReader fr = new FileReader(propertyFile);
			BufferedReader br = new BufferedReader(fr);
			String line;
			while((line = br.readLine()) != null) {
				String[] props = line.split("=");
				System.setProperty(props[0], props[1]);
			}
			br.close();
			fr.close();
		}
	}
	
	private Runnable loaderThread(File propertyFile) {
		return new Runnable() {
			public void run() {
				try {
					while(true) {
							loadProperties(propertyFile);
							Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
				} catch (FileNotFoundException e) {
					System.out.println("File not found : " + propertyFile.getAbsolutePath());
				} catch (IOException e) {
					System.out.println("failed to read file : " + propertyFile.getAbsolutePath());
				}
			}
		};
	}
}
