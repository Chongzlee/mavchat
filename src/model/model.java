package model;

import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class model {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/chatcsifelse";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public model() {
    }

    public String checkUsername(String userName) {
        String result = "";
        try {
            // Establish a database connection
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if the user exists in the database
            String query = "SELECT username FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();
            int i =1;
            boolean check = false;

            while(resultSet.next()){

                String usr = resultSet.getString(i);
                if (usr.equals(userName)) {
                   check = true;
                } else {
                    // User is not found in the database, return a welcome message

                }
                i++;
            }
            if (check) {
                // User exists in the database, return a welcome-back message
                result = haveid(userName);
                System.out.println(result);
                preparedStatement.close();
                resultSet.close();
                connection.close();
                return result;


            } else {
                // User is not found in the database, return a welcome message
                String insertQuery = "INSERT INTO users (username) VALUES (?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setString(1, userName);
                insertStatement.executeUpdate();

                result = donthaveid(userName);
                System.out.println(result);
                preparedStatement.close();
                resultSet.close();
                connection.close();
                return result;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String haveid(String userName) {
        return "Welcome again " + userName + "! Anything else today?";
    }

    private String donthaveid(String userName) {
        return "Welcome " + userName + " to ChatCSIfElse, the best chat AI in the world! What can I help you with?";
    }

    public String checkInput(String username, String input) {
        Connection connection = null;

        try {
            // Get the current date and time
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            LocalDateTime currentTime = LocalDateTime.now();

            // Check if the current time is within the allowed range
            if ((currentTime.getHour() >= 17 && currentTime.getHour() < 18) &&
                    currentTime.getDayOfWeek() != DayOfWeek.SATURDAY &&
                    currentTime.getDayOfWeek() != DayOfWeek.SUNDAY) {
                System.out.println("Sorry, we are out of service at this moment.");
                return "Sorry, we are out of service at this moment.";
            } else {
                // Insert the user's message into the conversation table
                int usr_id = getUserId(username);
                String insertQuery = "INSERT INTO conversations ( user_id, message) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                insertStatement.setInt(1, usr_id);
                insertStatement.setString(2, input);
                insertStatement.executeUpdate();

                // Retrieve the last message from the conversation table
                String lastMessageQuery = "SELECT message FROM conversations\n" +
                        "ORDER BY timestamp DESC\n" +
                        "LIMIT 1;";
                try (Statement statement = connection.createStatement();
                     ResultSet resultSet = statement.executeQuery(lastMessageQuery)) {

                    if (resultSet.next()) {
                        String lastchat = resultSet.getString(1);
                        String result = botchat(username, lastchat);
                        System.out.println(result);
                        return result;
                    } else {
                        System.out.println("No messages found in the conversation table.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "";
    }



    private int getUserId(String username) throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        String selectQuery = "SELECT id FROM users WHERE username = ? LIMIT 1;";
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        selectStatement.setString(1, username);

        ResultSet resultSet = selectStatement.executeQuery();

        int userId = -1; // Initialize with a default value, indicating not found

        if (resultSet.next()) {
            userId = resultSet.getInt("id");
        }

        // Close resources
        resultSet.close();
        selectStatement.close();
        connection.close();

        return userId;
    }


    private String botchat(String userName,String lastchat) {
        return "That is interesting " + userName + " that you said "+lastchat+" I will send this message to someone \n" +
                "else very soon. Anything else I can help?”";
    }
}
