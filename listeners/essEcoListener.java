import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VoteListener;

/**
 * A VoteListener that rewards via iConomy.
 * 
 * @author Blake Beaupain
 */
public class essEcoListener implements VoteListener {

	/** The logger instance. */
	private static Logger logger = Logger.getLogger("essEcoListener");

	/** The amount to reward. */
	private int amount = 100;

	/**
	 * Instantiates a new iConomy listener.
	 */
	public essEcoListener() {
		final Properties props = new Properties();
		try {
			// Create the file if it doesn't exist.
			final File configFile = new File("./plugins/Votifier/essEcoListener.ini");
			if (!configFile.exists()) {
				configFile.createNewFile();

				// Load the configuration.
				props.load(new FileReader(configFile));

				// Write the default configuration.
				props.setProperty("reward_amount", Integer.toString(amount));
				props.store(new FileWriter(configFile), "essEco Listener Configuration");
			} else {
				// Load the configuration.
				props.load(new FileReader(configFile));
			}

			amount = Integer.parseInt(props.getProperty("reward_amount", "100"));
		} catch (Exception ex) {
			logger.log(Level.WARNING, "Unable to load essEcoListener.ini, using default reward value of: " + amount);
		}
	}

	@Override
	public void voteMade(final Vote vote) {
		final String username = vote.getUsername();
		if (Economy.playerExists(username)) {
			
			try {
				Economy.add(username,amount);
			}
			catch (Exception ex)
			{
				System.out.println("[Votifier] essEconomy error: " + ex.getMessage());
			}
			
			// Tell the player how awesome they are.
			final Player player = Bukkit.getServer().getPlayer(username);
			if (player != null) {
				player.sendMessage("Thanks for voting on " + vote.getServiceName() + "!");
				player.sendMessage(amount + " has been added to your balance.");
			}
		}
	}

}
