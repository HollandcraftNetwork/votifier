import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;
import com.vexsoftware.votifier.Votifier;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VoteListener;


/**
 * A VoteListener that rewards via EssEco.
 * 
 * @author Blake Beaupain
 * @author KHobbits
 */
public class essEcoListener implements VoteListener
{
	/** The logger instance. */
	private static Logger logger = Logger.getLogger("essEcoListener");
	/** The amount to reward. */
	private String broadcast = "&4{player} just voted on {site}";

	/**
	 * Instantiates a new iConomy listener.
	 */
	public essEcoListener()
	{
		final Properties props = new Properties();
		try
		{
			// Create the file if it doesn't exist.
			final File configFile = new File("./plugins/Votifier/essEcoListener.ini");
			if (!configFile.exists())
			{
				configFile.createNewFile();

				// Load the configuration.
				props.load(new FileReader(configFile));

				// Write the default configuration.
				props.setProperty("msg-broadcast", broadcast);
				props.store(new FileWriter(configFile), "essEco Listener Configuration");
			}
			else
			{
				// Load the configuration.
				props.load(new FileReader(configFile));
			}

			broadcast = props.getProperty("msg-broadcast", broadcast);
		}
		catch (Exception ex)
		{
			logger.log(Level.WARNING, "Unable to load essEcoListener.ini, using default reward value of");
		}
	}

	@Override
	public void voteMade(final Vote vote)
	{
		final String username = vote.getUsername();
				Votifier.getInstance().getServer().broadcastMessage(format(broadcast, username, amount, vote));

	}

	private String format(String message, String player, Integer amount, Vote site)
	{
		return message.replace('&', '§').replace("§§", "&").replace("{player}", player).replace("{amount}", amount.toString()).replace("{site}", site.getServiceName());
	}
}
