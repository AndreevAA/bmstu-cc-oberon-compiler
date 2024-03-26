# bmstu-cc-oberon-compiler
Курсовая работа по предмету "Конструирование компиляторов""

## Установка на MACOS

### Java 17

Вот как вы можете установить Java 17 на macOS:

1. **Скачайте и установите JDK 17 с официального сайта Oracle:**
   - Перейдите на страницу загрузки JDK 17 на [официальном сайте Oracle](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html).
   - Согласитесь с лицензионным соглашением и выберите версию JDK для вашей операционной системы macOS.
   - Скачайте установщик и следуйте инструкциям для установки JDK 17 на вашем компьютере.

2. **Используйте установщик от AdoptOpenJDK:**
   - Вы также можете использовать установщик от [AdoptOpenJDK](https://adoptopenjdk.net/), который предоставляет установщики для различных версий JDK, включая Java 17.
   - Перейдите на сайт AdoptOpenJDK, выберите версию JDK 17 и скачайте установщик для macOS.
   - Запустите установщик и следуйте инструкциям для установки JDK 17 на вашем компьютере.

После установки Java 17, убедитесь, что переменная окружения JAVA_HOME указывает на установленную версию JDK 17. Вы также можете проверить версию Java с помощью команды `java -version` в терминале.

### llvm и antlr4

Для установки необходимо запустить скрипт, работающий с помощью brew:

```
sh install.sh
```

## Запуск программы

Для запуска программы необходимо воспользоваться готовым скриптом из файла `run.sh`:

```
sh run.sh
```
## Тестирование 
<img width="1232" alt="image" src="https://github.com/AndreevAA/bmstu-cc-oberon-compiler/assets/58621780/fe216124-f196-4d92-b9f6-018b04391f15">

## Общая информация
[Документация Oberon-2](https://pm.vogu35.ru/oberon/o2rus.htm)

[Документация Oberon ](https://online.oberon.org/oberon#sec96)

[Примеры программ на Oberon](https://github.com/vishaps/oberonbyexample)

[Онлайн-компилятор Oberon](http://oberspace.org/oberonjs.html)

[Исходная грамматика для Oberon](https://github.com/antlr/grammars-v4/blob/master/oberon/oberon.g4)


