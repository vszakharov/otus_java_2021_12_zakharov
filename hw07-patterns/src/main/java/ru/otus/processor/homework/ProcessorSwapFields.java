package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

public class ProcessorSwapFields implements Processor {
    @Override
    public Message process(Message message) {
        var oldField11 = message.getField11();
        var oldField12 = message.getField12();

        return message.toBuilder().field11(oldField12).field12(oldField11).build();
    }
}
