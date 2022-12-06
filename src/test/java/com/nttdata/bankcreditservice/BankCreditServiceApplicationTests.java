package com.nttdata.bankcreditservice;

import com.nttdata.bankcreditservice.document.BankCredit;
import com.nttdata.bankcreditservice.service.BankCreditService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BankCreditServiceApplicationTests {

	@Autowired
	private WebTestClient client;

	@Autowired
	private BankCreditService bankCreditService;

	@Test
	void findAllTest() {
		client.get()
				.uri("/bankCredit")
				.exchange()
				.expectStatus().isOk()
				.expectBodyList(BankCredit.class)
				.consumeWith(response -> {
					List<BankCredit> bc = response.getResponseBody();
					bc.forEach(b -> {
						System.out.println(b.getNumberCredit());
						Assertions.assertThat(bc.size() > 0).isTrue();
					});

				});
	}

	@Test
	void findByIdTest() {
		BankCredit b = bankCreditService.findByCustomerId("63728b3f4cc7cc12bdc038da").blockFirst();
		client.get()
				.uri("/bankCredit/{id}", Collections.singletonMap("id", b.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody(BankCredit.class)
				.consumeWith(response -> {
					BankCredit bc = response.getResponseBody();
					Assertions.assertThat(bc.getNumberCredit().length() > 0).isTrue();
				});
	}

	@Test
	void registerTest() {
		BankCredit ba = new BankCredit(null, "6545487845", Float.parseFloat("1200"), Float.parseFloat("1200"), "20","19","05",
				"636eea6e33ec63cafaf72fd2", "1", "2022-12-01");

		client.post()
				.uri("/bankCredit/register")
				.body(Mono.just(ba), BankCredit.class)
				.exchange()
				.expectStatus().isCreated()
				.expectBody(BankCredit.class)
				.consumeWith(response -> {
					BankCredit b = response.getResponseBody();
					Assertions.assertThat(b.getNumberCredit().equals("6545487845")).isTrue();
				});
	}

	@Test
	void updateTest() {
		BankCredit bc = bankCreditService.findByNumberCredit("6545487845").block();
		BankCredit bcm = bc;
		bcm.setCredit(Float.parseFloat("2000"));
		client.put()
				.uri("/bankCredit/update")
				.body(Mono.just(bcm), BankCredit.class)
				.exchange()
				.expectStatus().isOk()
				.expectBody(BankCredit.class)
				.consumeWith(response -> {
					BankCredit b = response.getResponseBody();
					Assertions.assertThat(b.getNumberCredit().equals("6545487845")).isTrue();
					Assertions.assertThat(b.getCredit()==2000).isTrue();
				});
	}

	@Test
	void deleteTest() {
		BankCredit ba = bankCreditService.findByNumberCredit("6545487845").block();

		client.delete()
				.uri("/bankCredit/{id}", Collections.singletonMap("id", ba.getId()))
				.exchange()
				.expectStatus().isOk()
				.expectBody();
	}


}
