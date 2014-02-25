package tk.R3creat3.MCP.handlers;

import tk.R3creat3.MCP.MCP;
import tk.R3creat3.MCP.Type;
import tk.R3creat3.MCP.db.MySQL;
import tk.R3creat3.MCP.object.Move;
import tk.R3creat3.MCP.object.Pokemon;
import tk.R3creat3.MCP.object.Trainer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLHandler {

    MCP plugin = MCP.getInstance();

    public static synchronized void createTables() {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        if (!mysql.isTable(MCP.storageDatabase + "_users")) {
            try {
                mysql.query("CREATE TABLE `" + MCP.storageDatabase + "_users` (" +
                        "`id` INT(10) UNSIGNED NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`title` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "PRIMARY KEY (`id`))");
            } catch (SQLException e) {
                e.printStackTrace();  //Table creation failed.
            }
        }

        if (!mysql.isTable(MCP.storageDatabase + "_pokemon")) {
            try {
                mysql.query("CREATE TABLE `" + MCP.storageDatabase + "_pokemon` (" +
                        "`id` INT(10) UNSIGNED NULL AUTO_INCREMENT," +
                        "`name` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`type1` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`type2` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`move1` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`move2` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`move3` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`move4` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`owner` VARCHAR(255) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "`pos` INT(10) NULL DEFAULT NULL COLLATE 'utf8_general_ci'," +
                        "PRIMARY KEY (`id`))");
            } catch (SQLException e) {
                e.printStackTrace();  //Table creation failed.
            }
        }

        if (!mysql.isTable(MCP.storageDatabase + "_pokemon_statistics")) {
            try {
                mysql.query("CREATE TABLE `" + MCP.storageDatabase + "_pokemon_statistics` (" +
                        "`id` INT(10) UNSIGNED NULL," +
                        "`hp` INT(10) UNSIGNED NULL," +
                        "`atk` INT(10) UNSIGNED NULL," +
                        "`def` INT(10) UNSIGNED NULL," +
                        "`spatk` INT(10) UNSIGNED NULL," +
                        "`spdef` INT(10) UNSIGNED NULL," +
                        "`speed` INT(10) UNSIGNED NULL," +
                        "PRIMARY KEY (`id`))");
            } catch (SQLException e) {
                e.printStackTrace();  //Table creation failed.
            }
        }

        mysql.close();
    }

    public static synchronized void autoHandlePokemon(Trainer t) throws SQLException {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        ResultSet rs = mysql.query("SELECT * FROM " + MCP.storageDatabase + "_pokemon");
        while (rs.next()) {
            if (rs.getString("owner").equals(t.getName())) {
                long uniqueID = rs.getLong("id");
                String name = rs.getString("name");
                Type type1 = TypeHandler.parseType(rs.getString("type1"));
                Type type2 = Type.NONE;
                if (rs.getString("type2") != null) type2 = TypeHandler.parseType(rs.getString("type2"));
                Move move1 = MCP.getMoves().get(rs.getString("move1"));
                Move move2 = MCP.getMoves().get(rs.getString("move2"));
                Move move3 = MCP.getMoves().get(rs.getString("move3"));
                Move move4 = MCP.getMoves().get(rs.getString("move4"));

                if (rs.getInt("pos") == 1) t.p1 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 1);
                if (rs.getInt("pos") == 2) t.p2 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 2);
                if (rs.getInt("pos") == 3) t.p3 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 3);
                if (rs.getInt("pos") == 4) t.p4 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 4);
                if (rs.getInt("pos") == 5) t.p5 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 5);
                if (rs.getInt("pos") == 6) t.p6 = new Pokemon(name, type1, type2, move1, move2, move3, move4, uniqueID, 6);
            }
        }

        mysql.close();
    }

    public static synchronized void refreshStats(Pokemon p, long ID) throws SQLException {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        ResultSet rs = mysql.query("SELECT * FROM " + MCP.storageDatabase + "_pokemon_statistics");
        while (rs.next()) {
            if (rs.getInt("ID") == ID) {
                p.setStats(rs.getInt("hp"), rs.getInt("atk"), rs.getInt("def"), rs.getInt("spatk"), rs.getInt("spdef"), rs.getInt("speed"));
            }
        }

        mysql.close();
    }

    public static synchronized String getTitle(String p) throws SQLException {
        String title = "Unidentified";
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        ResultSet rs = mysql.query("SELECT * FROM " + MCP.storageDatabase + "_users");
        while (rs.next()) {
            if (rs.getString("name").equals(p)) title = rs.getString("title");
        }

        mysql.close();

        return title;
    }

    public static synchronized void createNewUser(String p) throws SQLException {
        if (userExists(p)) return;
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        mysql.query("INSERT INTO `" + MCP.storageDatabase + "_users` (`id`, `name` , `title`) VALUES (NULL, '" + p + "' , 'New Trainer')");

        mysql.close();
    }

    private static synchronized boolean userExists(String p) throws SQLException {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        ResultSet rs = mysql.query("SELECT * FROM  `" + MCP.storageDatabase + "_users` WHERE  `name` =  '" + p + "'");
        while (rs.next()) {
            if (rs.getString("name").equals(p)) {
                mysql.close();
                return true;
            }
        }

        mysql.close();

        return false;
    }

    private static synchronized boolean hasAtLeastOnePokemon(String p) throws SQLException {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        ResultSet rs = mysql.query("SELECT * FROM  `" + MCP.storageDatabase + "_pokemon` WHERE  `owner` =  '" + p + "'");
        while (rs.next()) {
            if (rs.getString("name").equals(p)) {
                mysql.close();
                return true;
            }
        }

        mysql.close();

        return false;
    }

    public static void changeOrder(long ID, int pos) {
        MySQL mysql = new MySQL(MCP.logger,
                "[MCP-DB] ",
                MCP.storageHostname,
                MCP.storagePort,
                MCP.storageDatabase,
                MCP.storageUsername,
                MCP.storagePassword);
        mysql.open();

        try {
            System.out.println("changing ID " + ID + " to position " + pos);
            mysql.query("UPDATE `" + MCP.storageDatabase + "_pokemon` SET  `pos` =  '" + pos + "' WHERE  `id` =" + ID + "");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mysql.close();

    }

}
