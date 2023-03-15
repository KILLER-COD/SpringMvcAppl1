package ru.artur.springcourse.dao;

import org.springframework.stereotype.Component;
import ru.artur.springcourse.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/test_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "159357";

    private static Connection connection;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                Person person = new Person();

                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setEmail(resultSet.getString("email"));
                person.setAge(resultSet.getInt("age"));

                people.add(person);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return people;
    }


    public Person show(int id) {
        Person person = null;
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM person WHERE id=?");
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            person = new Person();

            person.setId(resultSet.getInt("id"));
            person.setName(resultSet.getString("name"));
            person.setEmail(resultSet.getString("email"));
            person.setAge(resultSet.getInt("age"));


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return person;
    }

    public void save(Person person) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO person (name,age,email) VALUE( ?, ?, ?)");

            preparedStatement.setString(1,person.getName());
            preparedStatement.setInt(2,person.getAge());
            preparedStatement.setString(3,person.getEmail());

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(int id, Person updatePerson) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE person SET name=?, age=?, email=? WHERE id=?");

            preparedStatement.setString(1,updatePerson.getName());
            preparedStatement.setInt(2,updatePerson.getAge());
            preparedStatement.setString(3,updatePerson.getEmail());
            preparedStatement.setInt(4,id);

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(int id) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM person WHERE id=?");

            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();

            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}