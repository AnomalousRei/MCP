package tk.R3creat3.MCP.db.Factory;

import tk.R3creat3.MCP.db.Database;
import tk.R3creat3.MCP.db.Delegates.FilenameDatabase;
import tk.R3creat3.MCP.db.Delegates.FilenameDatabaseImpl;
import tk.R3creat3.MCP.db.Delegates.HostnameDatabase;
import tk.R3creat3.MCP.db.Delegates.HostnameDatabaseImpl;
import tk.R3creat3.MCP.db.MySQL;
import tk.R3creat3.MCP.db.Factory.DatabaseConfig.Parameter;

/**
 * Factory for Database objects.<br>
 * Date Created: 2012-03-11 15:07.
 *
 * @author Balor (aka Antoine Aflalo)
 */
public class DatabaseFactory {
    public static Database createDatabase(DatabaseConfig config) throws InvalidConfigurationException {
        if (!config.isValid())
            throw new InvalidConfigurationException(
                    "The configuration is invalid, you don't have enough parameters for that DB : "
                            + config.getType());
        switch (config.getType()) {
            case MySQL:
                return new MySQL(config.getLog(), config.getParameter(Parameter.PREFIX),
                        config.getParameter(DatabaseConfig.Parameter.HOSTNAME),
                        Integer.parseInt(config.getParameter(Parameter.PORTNMBR)),
                        config.getParameter(Parameter.DATABASE),
                        config.getParameter(Parameter.USERNAME),
                        config.getParameter(Parameter.PASSWORD));
            default:
                return null;
        }
    }

    public static HostnameDatabase hostname() {
        return new HostnameDatabaseImpl();
    }

    public static FilenameDatabase filename() {
        return new FilenameDatabaseImpl();
    }
}
