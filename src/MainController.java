import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

/**
 * Created by christian bartram on 6/18/17.
 */
public class MainController {
	//FXML Objects
	@FXML private ToggleButton toggleButton;
	@FXML private Label state;
	@FXML private Label error;
	@FXML private Label version;

	//Constants
	private static final String PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/SkirmishScripts.scb";
	private static final String CANONICAL_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/";
	private static final String ABSOLUTE_BACKUP_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/backup/SkirmishScripts.scb";
	private static final String CANONICAL_BACKUP_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/backup/";

	private static final String ADVANCED_AI_SCRIPT = "zero_hour_scripts/Advanced/SkirmishScripts.scb";
	private static final String NORMAL_AI_SCRIPT = "zero_hour_scripts/Normal/SkirmishScripts.scb";

	private SecureRandom random = new SecureRandom();
	private boolean advancedScript = false;

	@FXML
	public void initialize() {

		//Determine if the file is currently AdvancedAI
		File scripts = new File(PATH);

		if(scripts.exists()) {
			double bytes = scripts.length();
			advancedScript = bytes >= 2726341;
		}

		toggleButton.setOnAction(this::handleToggleButton);

		if(advancedScript) {
			setStateOn();
		} else {
			setStateOff();
		}

		//Set the button's state to the AI boolean
		toggleButton.setSelected(advancedScript);

		try {
			version.setText("Running latest version 2.0.0");
			exportResource("/zero_hour_scripts/Advanced/SkirmishScripts.scb");
			setup();
		} catch(Exception e) {
			error.setTextFill(Color.web("#fc054f"));
			error.setText(e.getMessage());
		}
	}


	@FXML
	private void handleToggleButton(ActionEvent event) {

		//Advanced is being turned on
		if(toggleButton.isSelected()) {
			setStateOn();

			swap();

		} else {
			setStateOff();

			swap();
		}
	}

	/**
	 * Handles setting up the script for the first ensuring that
	 * all files are created and ready for the software to use
	 * @throws IOException
	 */
	private void setup() throws IOException {

		File scripts = new File(PATH);
		Path p = Paths.get(CANONICAL_BACKUP_PATH);
		boolean advancedScript = true;

		//Create the backups dir if it doesn't Exist
		if(Files.notExists(p)) {
			Files.createDirectory(p);

			if(scripts.exists()) {
				double size = scripts.length();

				if(size <= 2272629) {
					advancedScript = false;
				}
			}

			//The script in the /scripts in already advanced AI
			if(advancedScript) {
				//Move the Non Advanced AI into the backups folder
				FileUtils.copyFile(new File(NORMAL_AI_SCRIPT),  new File(ABSOLUTE_BACKUP_PATH));

			} else {
				//Move the Advanced AI into the backups folder
				FileUtils.copyFile(new File(ADVANCED_AI_SCRIPT), new File(ABSOLUTE_BACKUP_PATH));
			}
		}

	}

	/**
	 * Cosmetic changes to the GUI showing the script is currently disabled
	 */
	private void setStateOff() {
		state.setText("Off");
		state.setTextFill(Color.web("#fc054f"));

		toggleButton.setText("Turn Advanced AI On");
	}

	/**
	 * Cosmetic changes to the GUI showing the script is currently enabled
	 */
	private void setStateOn() {
		state.setText("On");
		state.setTextFill(Color.web("#42f448"));

		toggleButton.setText("Turn Advanced AI Off");
	}

	/**
	 * Generates a Psuedo-random temp file name when performing
	 * a file swap
	 * @return
	 */
	private String rand() {
		return new BigInteger(130, random).toString(32) + ".scb";
	}

	/**
	 * Export a resource embedded into a Jar file to the local file path.
	 *
	 * @param resourceName ie.: "/SmartLibrary.dll"
	 * @return The path to the exported resource
	 * @throws Exception
	 */
	 private String exportResource(String resourceName) throws Exception {
		System.out.println("Exporting Resource");
		InputStream stream = null;
		OutputStream resStreamOut = null;
		String jarFolder;
		try {
			stream = Main.class.getResourceAsStream(resourceName);//note that each / is a directory down in the "jar tree" been the jar the root of the tree
			if(stream == null) {
				throw new Exception("Cannot get resource \"" + resourceName + "\" from Jar file.");
			}

			int readBytes;
			byte[] buffer = new byte[4096];
			jarFolder = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getPath().replace('\\', '/');

			resStreamOut = new FileOutputStream(jarFolder + resourceName);
			while ((readBytes = stream.read(buffer)) > 0) {
				resStreamOut.write(buffer, 0, readBytes);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			stream.close();
			resStreamOut.close();
		}

		return jarFolder + resourceName;
	}

	private void swap() {
		try {
			File scriptsOriginal = new File(PATH);
			File backupOriginal = new File(ABSOLUTE_BACKUP_PATH);
			File backupCopy = new File(CANONICAL_BACKUP_PATH + rand());
			File scriptsCopy= new File(CANONICAL_PATH + rand());

			FileUtils.copyFile(scriptsOriginal, backupCopy); //SkirmishScripts.scb => /backups/abc123.scb
			FileUtils.copyFile(backupOriginal, scriptsCopy); // /backups/SkirmishScripts.scb => /scripts/abc123.scb

			//Delete the Original Files
			FileUtils.deleteQuietly(scriptsOriginal);
			FileUtils.deleteQuietly(backupOriginal);

			//Rename the files
			FileUtils.moveFile(scriptsCopy, scriptsOriginal); // /scripts/abc123.scb => /scripts/SkirmishScripts.scb
			FileUtils.moveFile(backupCopy, backupOriginal); // /backups/abc123.scb => /backups/SkirmishScripts.scb

		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
	}

}
