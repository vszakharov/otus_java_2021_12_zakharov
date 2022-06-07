package ru.otus.processor.homework;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProcessorThrowExceptionAtEvenSecondTest {

    @Test
    @DisplayName("Кейс с чётной секундой")
    void processTest_throwExceptionAtEvenSecond() {
        var dateTimeProvider = mock(DateTimeProvider.class);
        var processor = new ProcessorThrowExceptionAtEvenSecond(dateTimeProvider);
        var message = new Message.Builder(1L).field11("field11").build();

        var date = LocalDateTime.of(2000, 1, 1, 1, 1, 2);
        when(dateTimeProvider.getDate()).thenReturn(date);

        assertThatExceptionOfType(RuntimeException.class).isThrownBy(() -> processor.process(message));
    }

    @Test
    @DisplayName("Кейс с нечётной секундой")
    void processTest_OKAtOddSecond() {
        var dateTimeProvider = mock(DateTimeProvider.class);
        var processor = new ProcessorThrowExceptionAtEvenSecond(dateTimeProvider);
        var message = new Message.Builder(1L).field12("field12").build();

        var date = LocalDateTime.of(2000, 1, 1, 1, 1, 1);
        when(dateTimeProvider.getDate()).thenReturn(date);

        var result = processor.process(message);
        assertThat(result).isEqualTo(message);
    }
}