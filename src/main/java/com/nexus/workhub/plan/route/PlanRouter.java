package com.nexus.workhub.plan.route;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.nexus.workhub.plan.dto.PlanCreateVO;
import com.nexus.workhub.plan.dto.PlanUpdateVO;
import com.nexus.workhub.plan.service.PlanService;

import reactor.core.publisher.Mono;

/**
 * @author xiaodong1.li
 * @date
 */
@Configuration
public class PlanRouter {

	/**
	 * 
	 */
	@Autowired
	private PlanService planService;

	/**
	 * <p>
	 * <p>
	 * 3、
	 * <p>
	 * 4、
	 * 
	 * @return
	 */
	@Bean
	RouterFunction<ServerResponse> emailerRouter() {

		//
		return route(POST("plan/upload"), this::planImport)
				//
				.andRoute(POST("plan/create"), this::planCreate)

				//
				.andRoute(PUT("plan/{projectId}"), this::planUpdate)

				//
				.andRoute(GET("plan/{projectId}"), this::planView)

		;
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Mono<ServerResponse> planImport(ServerRequest request) {

		Mono<byte[]> bodyToMono = request.bodyToMono(new ParameterizedTypeReference<byte[]>() {
		});

		return bodyToMono.flatMap(file -> {
			String result = planService.importPlan(file);
			return ok().bodyValue(result);
		});
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Mono<ServerResponse> planCreate(ServerRequest request) {

		return request.bodyToMono(PlanCreateVO.class)
				.flatMap(vo -> {
					String result = planService.createPlan(vo);
					return ok().bodyValue(result);
				});

	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Mono<ServerResponse> planUpdate(ServerRequest request) {

		String projectId = request.pathVariable("projectId");

		return request.bodyToMono(PlanUpdateVO.class)
				.flatMap(vo -> {
					String result = planService.updatePlan(projectId, vo);
					return ok().bodyValue(result);
				});
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	private Mono<ServerResponse> planView(ServerRequest request) {

		String projectId = request.pathVariable("projectId");

		return ok().body(Mono.just(planService.viewPlan(projectId)), String.class);

	}

}
