package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorThrowExceptionAtEvenSecond implements Processor {
    private final DateTimeProvider dateTimeProvider;

    public ProcessorThrowExceptionAtEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var second = dateTimeProvider.getDate().getSecond();
        if (second % 2 == 0) {
            throw new RuntimeException("It's an even second now!");
        }

        return message;
    }
}
