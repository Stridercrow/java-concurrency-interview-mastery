package com.concurrency.mastery.interviewexercises.paymentvalidation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.BufferedWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for Payment Validation and Resource Management.
 *
 * Run these tests against your skeleton implementation to validate correctness.
 * All tests should pass when your implementation is complete.
 */
class PaymentValidationTest {

    private StringWriter stringWriter;
    private BufferedWriter bufferedWriter;

    @BeforeEach
    void setUp() {
        stringWriter = new StringWriter();
        bufferedWriter = new BufferedWriter(stringWriter);
    }

    // ============================================================
    // PaymentReaderService Tests
    // ============================================================

    @Test
    void testReadValidInput() throws Exception {
        List<String> inputs = Arrays.asList(
            "12-07-2024", "250.00", "1234567890", "0987654321", "Rent Payment"
        );

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter)) {
            PaymentInfo info = reader.read(inputs);

            assertEquals(LocalDate.of(2024, 7, 12), info.getDate());
            assertEquals(250.00, info.getAmount(), 0.001);
            assertEquals("1234567890", info.getPayerAccount());
            assertEquals("0987654321", info.getReceiverAccount());
            assertEquals("Rent Payment", info.getPurpose());
        }
    }

    @Test
    void testReadTooFewInputs() {
        List<String> inputs = Arrays.asList("12-07-2024", "250.00");

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter)) {
            Exception exception = assertThrows(MissingPaymentDetailException.class,
                () -> reader.read(inputs));
            assertTrue(exception.getMessage().contains("Not enough input details provided"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testReadInvalidDateFormat() {
        List<String> inputs = Arrays.asList(
            "2023/07/31", "100.00", "1234567890", "0987654321", "Payment"
        );

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter)) {
            Exception exception = assertThrows(InvalidDateFormatException.class,
                () -> reader.read(inputs));
            assertTrue(exception.getMessage().contains("2023/07/31"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testReadInvalidAmountFormat() {
        List<String> inputs = Arrays.asList(
            "31-07-2023", "abc", "1234567890", "0987654321", "Payment"
        );

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter)) {
            Exception exception = assertThrows(InvalidAmountFormatException.class,
                () -> reader.read(inputs));
            assertTrue(exception.getMessage().contains("abc"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testReaderCloseMessage() throws Exception {
        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter)) {
            // do nothing
        }
        bufferedWriter.flush();
        assertTrue(stringWriter.toString().contains("PaymentReaderService. Resource closed"));
    }

    // ============================================================
    // PaymentValidationService Tests
    // ============================================================

    @Test
    void testValidateValidPayment() throws Exception {
        PaymentInfo payment = new PaymentInfo(
            LocalDate.of(2024, 7, 12), 250.00,
            "1234567890", "0987654321", "Rent Payment"
        );

        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            assertDoesNotThrow(() -> validator.validate(payment));
        }
    }

    @Test
    void testValidateNegativeAmount() {
        PaymentInfo payment = new PaymentInfo(
            LocalDate.of(2024, 7, 12), -100.00,
            "1234567890", "0987654321", "Payment"
        );

        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            Exception exception = assertThrows(InvalidPaymentDetailsException.class,
                () -> validator.validate(payment));
            assertTrue(exception.getMessage().contains("Amount must be greater than zero"));
            assertTrue(exception.getMessage().contains("-100.0"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testValidateZeroAmount() {
        PaymentInfo payment = new PaymentInfo(
            LocalDate.of(2024, 7, 12), 0.0,
            "1234567890", "0987654321", "Payment"
        );

        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            assertThrows(InvalidPaymentDetailsException.class,
                () -> validator.validate(payment));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testValidateSamePayerAndReceiver() {
        PaymentInfo payment = new PaymentInfo(
            LocalDate.of(2024, 7, 12), 250.00,
            "1234567890", "1234567890", "Payment"
        );

        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            Exception exception = assertThrows(InvalidPaymentDetailsException.class,
                () -> validator.validate(payment));
            assertTrue(exception.getMessage().contains("Payer and receiver accounts are invalid"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testValidateEmptyPurpose() {
        PaymentInfo payment = new PaymentInfo(
            LocalDate.of(2024, 7, 12), 250.00,
            "1234567890", "0987654321", ""
        );

        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            Exception exception = assertThrows(InvalidPaymentDetailsException.class,
                () -> validator.validate(payment));
            assertTrue(exception.getMessage().contains("Purpose of payment is missing"));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }

    @Test
    void testValidatorCloseMessage() throws Exception {
        try (PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {
            // do nothing
        }
        bufferedWriter.flush();
        assertTrue(stringWriter.toString().contains("PaymentValidationService. Resource closed"));
    }

    // ============================================================
    // Integration Test — Full Flow
    // ============================================================

    @Test
    void testFullFlowValidPayment() throws Exception {
        List<String> inputs = Arrays.asList(
            "12-07-2024", "250.00", "1234567890", "0987654321", "Rent Payment"
        );

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter);
             PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {

            PaymentInfo info = reader.read(inputs);
            assertDoesNotThrow(() -> validator.validate(info));
        }

        bufferedWriter.flush();
        String output = stringWriter.toString();
        assertTrue(output.contains("PaymentReaderService. Resource closed"));
        assertTrue(output.contains("PaymentValidationService. Resource closed"));
    }

    @Test
    void testFullFlowInvalidDate() {
        List<String> inputs = Arrays.asList(
            "2023/07/31", "100.00", "1234567890", "0987654321", "Payment"
        );

        try (PaymentReaderService reader = new PaymentReaderService(bufferedWriter);
             PaymentValidationService validator = new PaymentValidationService(bufferedWriter)) {

            assertThrows(InvalidDateFormatException.class, () -> reader.read(inputs));
        } catch (Exception e) {
            fail("Unexpected exception: " + e.getMessage());
        }
    }
}
