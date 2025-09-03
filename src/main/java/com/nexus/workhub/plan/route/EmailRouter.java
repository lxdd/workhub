// package com.nss.transit.route;

// import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
// import static org.springframework.web.reactive.function.server.RouterFunctions.route;
// import static org.springframework.web.reactive.function.server.ServerResponse.ok;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.core.ParameterizedTypeReference;
// import org.springframework.web.reactive.function.server.RouterFunction;
// import org.springframework.web.reactive.function.server.ServerRequest;
// import org.springframework.web.reactive.function.server.ServerResponse;

// import com.nss.transit.service.EmailGenerateService;

// import reactor.core.publisher.Mono;

// /**
//  * @author xiaodong1.li
//  * @date
//  */
// @Configuration
// public class EmailRouter {

// 	/**
// 	 * 
// 	 */
// 	@Autowired
// 	private EmailGenerateService emailGenerateService;

// 	/**
// 	 * <p>
// 	 * <p>
// 	 * 3、
// 	 * <p>
// 	 * 4、
// 	 * 
// 	 * @return
// 	 */
// 	@Bean
// 	RouterFunction<ServerResponse> emailerRouter() {
// 		return route(POST("email/generate/marketing"), this::marketingEmail)
// 				//
// 				.andRoute(POST("email/generate/sequence"), this::sequenceEmail);
// 	}

// 	/**
// 	 * 
// 	 * @param request
// 	 * @return
// 	 */
// 	private Mono<ServerResponse> marketingEmail(ServerRequest request) {
// 		return request.bodyToMono(new ParameterizedTypeReference<List<String>>() {
// 		}).flatMap(vo -> marketingEmail(vo).flatMap(result -> ok().bodyValue(result)));
// 	}

// 	private Mono<String> marketingEmail(List<String> req) {
// 		emailGenerateService.marketingEmailGenerateAsync(req);
// 		return Mono.just("async processing....");
// 	}

// 	private Mono<ServerResponse> sequenceEmail(ServerRequest request) {
// 		return request.bodyToMono(new ParameterizedTypeReference<List<String>>() {
// 		}).flatMap(vo -> sequenceEmail(vo).flatMap(result -> ok().bodyValue(result)));
// 	}

// 	private Mono<String> sequenceEmail(List<String> req) {
// 		emailGenerateService.sequenceEmailGenerateAsync(req);
// 		return Mono.just("async processing....");
// 	}

// }
