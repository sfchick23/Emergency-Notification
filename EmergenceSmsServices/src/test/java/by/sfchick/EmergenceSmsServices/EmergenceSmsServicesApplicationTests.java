package by.sfchick.EmergenceSmsServices;

import by.sfchick.EmergenceSmsServices.models.Sms;
import by.sfchick.EmergenceSmsServices.repostitories.SmsRepository;
import by.sfchick.EmergenceSmsServices.services.SmsServices;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.ApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EmergenceSmsServicesApplicationTests {



	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private SmsServices smsServices;
	@Test
	public void testCaching() {
		List<String> firstCall = smsServices.getAllPhoneNumbers();
		List<String> secondCall = smsServices.getAllPhoneNumbers();

		assertSame(firstCall, secondCall); // Проверяем, что оба вызова возвращают один и тот же объект
	}

}
