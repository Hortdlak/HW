package HW;

import java.io.*;
import java.util.StringTokenizer;

class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }
}

class UserData {
    private String lastName;
    private String firstName;
    private String middleName;
    private String birthDate;
    private long phoneNumber;
    private char gender;

    public UserData(String data) throws InvalidDataException {
        StringTokenizer tokenizer = new StringTokenizer(data, " ");
        if (tokenizer.countTokens() != 6) {
            throw new InvalidDataException("Неправильное количество элементов данных.");
        }

        this.lastName = tokenizer.nextToken();
        this.firstName = tokenizer.nextToken();
        this.middleName = tokenizer.nextToken();
        this.birthDate = tokenizer.nextToken();
        this.phoneNumber = Long.parseLong(tokenizer.nextToken());
        this.gender = tokenizer.nextToken().charAt(0);

        // Проверка формата данных
        if (!birthDate.matches("\\d{2}.\\d{2}.\\d{4}")) {
            throw new InvalidDataException("Неверный формат даты. Используйте dd.mm.yyyy");
        }
        if (gender != 'f' && gender != 'm') {
            throw new InvalidDataException("Неверный пол. Используйте 'f' или 'm'.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public char getGender() {
        return gender;
    }
}

public class Main {
    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите данные пользователя в данном формате (Фамилию Имя Отчество Дата рождения Номер телефона Пол):");
            String line = bufferedReader.readLine();
            try {
                UserData userData = new UserData(line);
                writeFile(userData);
            } catch (InvalidDataException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeFile(UserData userData) {
        String fileName = userData.getLastName() + ".txt";
        try (FileWriter fileWriter = new FileWriter(fileName, true)) { 
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(userData.getLastName() + " " + userData.getFirstName() + " " +
                    userData.getMiddleName() + " " + userData.getBirthDate() + " " +
                    userData.getPhoneNumber() + " " + userData.getGender() + "\n");
            bufferedWriter.close(); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}