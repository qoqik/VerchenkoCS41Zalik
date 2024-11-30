# VerchenkoCS41Zalik
 Зміст опису : 
- Демонстрація роботи
- UML-діаграма

------
Демонстрація роботи

![image](https://github.com/user-attachments/assets/67344f6a-bd86-4413-a492-46ec0ad9d600)
![image](https://github.com/user-attachments/assets/0a40d5e7-4273-497c-8c89-1ad6ca2a9eb8)
------
UML-діаграма

![strategy_pattern_diagram](https://github.com/user-attachments/assets/c9e350cd-19a1-4016-8f3d-6a9b1a8e3a71)


Шаблон Strategy використовується для організації чотирьох важливих частин:

- Побудова вікна (AdWindowStrategy).
- Логіка створення секцій (SectionBuilder).
- Обробка категорій (CategoryProvider).
- Форматування виводу (ConsoleFormatter).

 1. Загальна стратегія створення вікна оголошення
Клас AdWindow приймає реалізацію інтерфейсу AdWindowStrategy, що визначає специфіку для кожного видання:

CourierAdStrategy — для "Харківський кур’єр".
BesplatkaAdStrategy — для "Бесплатка".
Це головне використання шаблону Strategy в коді, яке дозволяє визначати, як будувати вікно для кожного видання.

 2. Стратегії для секцій (створення полів вводу)
Інтерфейс SectionBuilder абстрагує логіку створення різних типів секцій:

TextFieldSectionBuilder — для текстових полів.
ComboBoxSectionBuilder — для випадаючих списків.
TextAreaSectionBuilder — для багаторядкових текстових полів.
Це також реалізація шаблону Strategy, де кожен тип секції може мати свою специфіку.

 3. Стратегія для категорій і підкатегорій
Інтерфейс CategoryProvider дозволяє визначати категорії та підкатегорії для кожного видання:

CourierCategoryProvider — для "Харківський кур’єр".
BesplatkaCategoryProvider — для "Бесплатка".
Ця стратегія забезпечує динамічну зміну логіки обробки категорій.

 4. Стратегія форматування виводу в консоль
Інтерфейс ConsoleFormatter задає формат виводу:

CourierConsoleFormatter — для "Харківський кур’єр".
BesplatkaConsoleFormatter — для "Бесплатка".
Ця реалізація дозволяє змінювати формат виводу без впливу на інші частини системи.

