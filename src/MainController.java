import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.paint.Color;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by christianbartram on 6/18/17.
 */
public class MainController {
	//FXML Objects
	@FXML private ToggleButton toggleButton;
	@FXML private Label state;

	//Constants
	private static final String PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/SkirmishScripts.scb";
	private static final String CANONICAL_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/";
	private static final String ABSOLUTE_BACKUP_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/backup/SkirmishScripts.scb";
	private static final String CANONICAL_BACKUP_PATH = "/Applications/Command & Conquer Generals Deluxe Edition.app/Contents/GameData/Zero Hour Data/Data/Scripts/backup/";

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
	}


	@FXML
	private void handleToggleButton(ActionEvent event) {

		//Advanced AI is being turned on
		if(toggleButton.isSelected()) {
			setStateOn();

			System.out.println("Swapping and turning ON advanced ai");
			swap();

		} else {
			setStateOff();

			System.out.println("Swapping and turning OFF advanced AI");
			swap();
		}
	}

	private void setStateOff() {
		state.setText("Off");
		state.setTextFill(Color.web("#fc054f"));

		toggleButton.setText("Turn Advanced AI On");
	}

	private void setStateOn() {
		state.setText("On");
		state.setTextFill(Color.web("#42f448"));

		toggleButton.setText("Turn Advanced AI Off");
	}

	private String rand() {
		return new BigInteger(130, random).toString(32) + ".scb";
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
