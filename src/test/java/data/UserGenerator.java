package data;

import java.util.Random;

public class UserGenerator {
    //user with mandatory fields only
    public User generateUserWithMandatoryFields() {
        Random rand = new Random();
        User user = new User();
        user.setLogin(String.format("login-%s_%s", rand.nextInt(500), System.currentTimeMillis()));
        user.setPassword("testpassword");
        user.setRepeatPassword("testpassword");
        return user;
    }

    //user with optional field: full name
    public User generateUserWithFullName() {
        User user = generateUserWithMandatoryFields();
        user.setFullName("test full name" + System.currentTimeMillis());
        return user;
    }

    //user with optional field: email
    public User generateUserWithEmail() {
        User user = generateUserWithMandatoryFields();
        user.setEmail(System.currentTimeMillis() + "email@google.com");
        return user;
    }

    //user with optional field: jabber
    public User generateUserWithJabber() {
        User user = generateUserWithMandatoryFields();
        user.setJabber(System.currentTimeMillis() + "user@jabber.org");
        return user;
    }

    //user with all optional fields
    public User generateUserWithAllOptionalFields() {
        User user = generateUserWithMandatoryFields();
        user.setFullName("test full name" + System.currentTimeMillis());
        user.setEmail(System.currentTimeMillis() + "email@google.com");
        user.setJabber(System.currentTimeMillis() + "user@jabber.org");
        return user;
    }

    //user with 1 symbol in every field except login
    //why these except cases: to be able to find the user later by this field, as search by 1 symbol will return many other users
    public User generateUsersWithMinFieldLengthExceptLogin() {
        String chars = "qwertuiopasdfghjklzxcvbnm01233456789";
        Random rnd = new Random();
        String c = String.valueOf(chars.charAt(rnd.nextInt(chars.length())));
        User user = generateUserWithMandatoryFields();
        user.setPassword(c);
        user.setRepeatPassword(c);
        user.setFullName(c);
        user.setJabber(c);
        user.setEmail(c);
        return user;
    }

    //user with 1 symbol in every field except email
    public User generateUsersWithMinFieldLengthExceptEmail() {
        String chars = "qwertuiopasdfghjklzxcvbnm01233456789";
        Random rnd = new Random();
        String c = String.valueOf(chars.charAt(rnd.nextInt(chars.length())));
        User user = generateUserWithEmail();
        user.setLogin(c);
        user.setPassword(c);
        user.setRepeatPassword(c);
        user.setFullName(c);
        user.setJabber(c);
        return user;
    }

    //user with 1 symbol in every field except email
    public User generateUsersWithMinFieldLengthExceptFullName() {
        String chars = "qwertuiopasdfghjklzxcvbnm01233456789";
        Random rnd = new Random();
        String c = String.valueOf(chars.charAt(rnd.nextInt(chars.length())));
        User user = generateUserWithFullName();
        user.setLogin(c);
        user.setPassword(c);
        user.setRepeatPassword(c);
        user.setJabber(c);
        user.setEmail(c);
        return user;
    }

    //the following special symbols are allowed in all fields except for login: !±@#$%^&*()-_=+{}[];:\"'|\\<>,.?/~`;
    public User generateUserWithSpecialSymbolsInAllFieldsExceptLogin() {
        String specialSymbolsAddition = "!±@#$%^&*()-_=+{}[];:\"'|\\<>,.?/~`";
        User user = generateUserWithMandatoryFields();
        user.setPassword(specialSymbolsAddition);
        user.setRepeatPassword(specialSymbolsAddition);
        user.setFullName(specialSymbolsAddition);
        user.setEmail(specialSymbolsAddition + "email@google.com");
        user.setJabber(specialSymbolsAddition + "user@jabber.org");
        return user;
    }

    // provides user with fields of defined length
    public User generateUserWithAllFieldsOfLength(int fieldsLength) {
        User user = new User();
        user.setLogin(generateRandomStringOfLenght(fieldsLength));
        user.setPassword(generateRandomStringOfLenght(fieldsLength));
        user.setRepeatPassword(user.getPassword());
        user.setFullName(generateRandomStringOfLenght(fieldsLength));
        user.setEmail(generateRandomStringOfLenght(fieldsLength));
        user.setJabber(generateRandomStringOfLenght(fieldsLength));
        return user;
    }

    private String generateRandomStringOfLenght(int length) {
        String chars = "qwertuiopasdfghjklzxcvbnm01233456789";
        Random rnd = new Random();
        String generatedString = "";
        while (generatedString.length() < length) {
            generatedString = generatedString + chars.charAt(rnd.nextInt(chars.length()));
        }
        return generatedString;
    }

}
