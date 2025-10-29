package ru.yandex.practicum.quiz.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.quiz.model.QuizLog;
import org.springframework.beans.factory.annotation.Value;

import java.io.PrintWriter;
import java.util.List;

@Component
public class ReportGenerator {

    @Value("${spring-quiz.title:}")
    private String quizTitle;

    public void generate(QuizLog quizLog) {
        try (PrintWriter writer = new PrintWriter(System.out)) {
            write(quizLog, writer);
        } catch (Exception exception) {
            System.out.println("При генерации отчёта произошла ошибка: " + exception.getMessage());
        }
    }

    private void write(QuizLog quizLog, PrintWriter writer) {
        writer.println("Отчет о прохождении теста \"Тест по Spring Framework\".\n");
        for (QuizLog.Entry entry : quizLog) {
            // Записываем номер вопроса и текст вопроса
            writer.println("Отчет о прохождении теста \"" + quizTitle + "\".\n");

            // Записываем варианты ответов
            List<String> options = entry.getQuestion().getOptions();
            for (int i = 0; i < options.size(); i++) {
                writer.println((i + 1) + ") " + options.get(i));
            }

            // Записываем ответы пользователя
            writer.print("Ответы пользователя: ");
            List<Integer> answers = entry.getAnswers();
            for (Integer answer : answers) {
                writer.print(answer + " ");
            }
            writer.println();

        //Записываем флаг успешности ответа
        String successFlag = entry.isSuccessful() ? "да" : "нет";
        writer.println("Содержит правильный ответ: " + successFlag);

            // Добавляем пустую строку между записями
            writer.println();
        }
        writer.printf("Всего вопросов: %d\nОтвечено правильно: %d\n", quizLog.total(), quizLog.successful());
    }
}
