package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Strategy interface for section creation
interface SectionBuilder {
    JComponent createSection(String label, Map<String, JComponent> components);
}

// Concrete SectionBuilder for text fields
class TextFieldSectionBuilder implements SectionBuilder {
    @Override
    public JComponent createSection(String label, Map<String, JComponent> components) {
        JTextField textField = new JTextField();
        components.put(label, textField);
        return textField;
    }
}

// Concrete SectionBuilder for combo boxes
class ComboBoxSectionBuilder implements SectionBuilder {
    private final String[] items;

    public ComboBoxSectionBuilder(String[] items) {
        this.items = items;
    }

    @Override
    public JComponent createSection(String label, Map<String, JComponent> components) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        components.put(label, comboBox);
        return comboBox;
    }
}

// Concrete SectionBuilder for text areas
class TextAreaSectionBuilder implements SectionBuilder {
    @Override
    public JComponent createSection(String label, Map<String, JComponent> components) {
        JTextArea textArea = new JTextArea(5, 20);
        components.put(label, textArea);
        return new JScrollPane(textArea);
    }
}

// Strategy interface for handling category and subcategory
interface CategoryProvider {
    Map<String, List<String>> getCategories();
}

// Concrete CategoryProvider for "Харківський кур’єр"
class CourierCategoryProvider implements CategoryProvider {
    @Override
    public Map<String, List<String>> getCategories() {
        Map<String, List<String>> categories = new HashMap<>();
        categories.put("Нерухомість", List.of("Квартири", "Дома"));
        categories.put("Транспорт", List.of("Автомобілі", "Мотоцикли"));
        categories.put("Послуги", List.of("Ремонт", "Перевезення"));
        return categories;
    }
}

// Concrete CategoryProvider for "Бесплатка"
class BesplatkaCategoryProvider implements CategoryProvider {
    @Override
    public Map<String, List<String>> getCategories() {
        Map<String, List<String>> categories = new HashMap<>();
        categories.put("Нерухомість", List.of("Квартири"));
        categories.put("Транспорт", List.of("Автомобілі"));
        categories.put("Послуги", List.of("Ремонт"));
        return categories;
    }
}

// Strategy interface for handling console output formatting
interface ConsoleFormatter {
    String format(Map<String, JComponent> components);
}

// Concrete ConsoleFormatter for "Харківський кур’єр"
class CourierConsoleFormatter implements ConsoleFormatter {
    @Override
    public String format(Map<String, JComponent> components) {
        return """
                ===== Для видання “Харківський кур’єр” =====
                Рубрика: %s
                Персональні дані: ім’я - %s, вік - %s
                Основна інформація: %s
                Контактні дані: телефон - %s
                """.formatted(
                ((JComboBox<?>) components.get("category")).getSelectedItem(),
                ((JTextField) components.get("name")).getText(),
                ((JTextField) components.get("age")).getText(),
                ((JTextField) components.get("description")).getText(),
                ((JTextField) components.get("phone")).getText()
        );
    }
}

// Concrete ConsoleFormatter for "Бесплатка"
class BesplatkaConsoleFormatter implements ConsoleFormatter {
    @Override
    public String format(Map<String, JComponent> components) {
        return """
                ===== Для видання “Бесплатка” =====
                Рубрика: %s -> %s
                Персональні дані: ім’я - %s, адреса - %s
                Основна інформація: %s
                Контактні дані: телефон - %s, email - %s
                """.formatted(
                ((JComboBox<?>) components.get("category")).getSelectedItem(),
                ((JComboBox<?>) components.get("subcategory")).getSelectedItem(),
                ((JTextField) components.get("name")).getText(),
                ((JTextField) components.get("address")).getText(),
                ((JTextArea) components.get("description")).getText(),
                ((JTextField) components.get("phone")).getText(),
                ((JTextField) components.get("email")).getText()
        );
    }
}

// Strategy interface for ad window setup
interface AdWindowStrategy {
    void setupSections(JFrame frame, Map<String, JComponent> components);

    String getPublicationName();

    ConsoleFormatter getConsoleFormatter();

    CategoryProvider getCategoryProvider();
}

// Concrete strategy for "Харківський кур’єр"
class CourierAdStrategy implements AdWindowStrategy {
    private final CategoryProvider categoryProvider = new CourierCategoryProvider();

    @Override
    public void setupSections(JFrame frame, Map<String, JComponent> components) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Рубрика:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Нерухомість", "Транспорт", "Послуги"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        components.put("category", categoryBox);
        frame.add(categoryBox, gbc);

        // Персональні дані
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Ім'я:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField();
        components.put("name", nameField);
        frame.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Вік:"), gbc);
        gbc.gridx = 1;
        JTextField ageField = new JTextField();
        components.put("age", ageField);
        frame.add(ageField, gbc);

        // Основна інформація
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Основна інформація:"), gbc);
        gbc.gridx = 1;
        JTextField descriptionField = new JTextField();
        components.put("description", descriptionField);
        frame.add(descriptionField, gbc);

        // Контактні дані
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Телефон:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField();
        components.put("phone", phoneField);
        frame.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7; // розташування кнопки після інших компонентів
        JButton postButton = new JButton("Запостити оголошення");
        postButton.addActionListener(e -> {
            // Логіка публікації оголошення (виведення в консоль)
            System.out.println("Оголошення запощено:\n" + getConsoleFormatter().format(components));
        });
        frame.add(postButton, gbc);
    }

    @Override
    public String getPublicationName() {
        return "Харківський кур’єр";
    }

    @Override
    public ConsoleFormatter getConsoleFormatter() {
        return new CourierConsoleFormatter();
    }

    @Override
    public CategoryProvider getCategoryProvider() {
        return categoryProvider;
    }
}

// Concrete strategy for "Бесплатка"
class BesplatkaAdStrategy implements AdWindowStrategy {
    private final CategoryProvider categoryProvider = new BesplatkaCategoryProvider();

    @Override
    public void setupSections(JFrame frame, Map<String, JComponent> components) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Рубрика:"), gbc);
        gbc.gridx = 1;
        String[] categories = {"Нерухомість", "Транспорт", "Послуги"};
        JComboBox<String> categoryBox = new JComboBox<>(categories);
        components.put("category", categoryBox);
        frame.add(categoryBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Підкатегорія:"), gbc);
        gbc.gridx = 1;
        JComboBox<String> subcategoryBox = new JComboBox<>();
        categoryBox.addActionListener(e -> {
            subcategoryBox.removeAllItems();
            switch ((String) categoryBox.getSelectedItem()) {
                case "Нерухомість" -> subcategoryBox.addItem("Квартири");
                case "Транспорт" -> subcategoryBox.addItem("Автомобілі");
                case "Послуги" -> subcategoryBox.addItem("Ремонт");
            }
        });
        components.put("subcategory", subcategoryBox);
        frame.add(subcategoryBox, gbc);

        // Персональні дані
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(new JLabel("Ім'я:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField();
        components.put("name", nameField);
        frame.add(nameField, gbc);

        // Інші поля
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(new JLabel("Адреса:"), gbc);
        gbc.gridx = 1;
        JTextField addressField = new JTextField();
        components.put("address", addressField);
        frame.add(addressField, gbc);

        // Основна інформація
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(new JLabel("Опис:"), gbc);
        gbc.gridx = 1;
        JTextArea descriptionField = new JTextArea(5, 20);
        components.put("description", descriptionField);
        frame.add(new JScrollPane(descriptionField), gbc);

        // Контактні дані
        gbc.gridx = 0;
        gbc.gridy = 5;
        frame.add(new JLabel("Телефон:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField();
        components.put("phone", phoneField);
        frame.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        frame.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField();
        components.put("email", emailField);
        frame.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7; // розташування кнопки після інших компонентів
        JButton postButton = new JButton("Запостити оголошення");
        postButton.addActionListener(e -> {
            // Логіка публікації оголошення (виведення в консоль)
            System.out.println("Оголошення запощено:\n" + getConsoleFormatter().format(components));
        });
        frame.add(postButton, gbc);
    }

    @Override
    public String getPublicationName() {
        return "Бесплатка";
    }

    @Override
    public ConsoleFormatter getConsoleFormatter() {
        return new BesplatkaConsoleFormatter();
    }

    @Override
    public CategoryProvider getCategoryProvider() {
        return categoryProvider;
    }
}

// Main Application
public class AdWindow {
    private AdWindowStrategy adWindowStrategy;

    public AdWindow(AdWindowStrategy adWindowStrategy) {
        this.adWindowStrategy = adWindowStrategy;
    }

    public void setupAdWindow() {
        JFrame frame = new JFrame(adWindowStrategy.getPublicationName());
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Map<String, JComponent> components = new HashMap<>();
        adWindowStrategy.setupSections(frame, components);
        frame.pack();
        frame.setVisible(true);
    }

    public void displayFormattedOutput(Map<String, JComponent> components) {
        System.out.println(adWindowStrategy.getConsoleFormatter().format(components));
    }

    public static void main(String[] args) {
        AdWindow adWindow = new AdWindow(new BesplatkaAdStrategy());
        adWindow.setupAdWindow();
    }
}
