package io.opentelemetry.auto.instrumentation.springwebflux.server;

import io.opentelemetry.auto.instrumentation.reactor.core.ReactorCoreAdviceUtils;
import io.opentelemetry.trace.Span;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;

import static io.opentelemetry.auto.instrumentation.springwebflux.server.SpringWebfluxHttpServerDecorator.DECORATE;

@Slf4j
public class AdviceUtils {

  public static final String SPAN_ATTRIBUTE =
      "io.opentelemetry.auto.instrumentation.springwebflux.Span";
  public static final String PARENT_SPAN_ATTRIBUTE =
      "io.opentelemetry.auto.instrumentation.springwebflux.ParentSpan";

  public static String parseOperationName(final Object handler) {
    final String className = DECORATE.spanNameForClass(handler.getClass());
    final String operationName;
    final int lambdaIdx = className.indexOf("$$Lambda$");

    if (lambdaIdx > -1) {
      operationName = className.substring(0, lambdaIdx) + ".lambda";
    } else {
      operationName = className + ".handle";
    }
    return operationName;
  }

  public static void finishSpanIfPresent(
      final ServerWebExchange exchange, final Throwable throwable) {
    ReactorCoreAdviceUtils.finishSpanIfPresent(
        (Span) exchange.getAttributes().remove(SPAN_ATTRIBUTE), throwable);
  }

  public static void finishSpanIfPresent(
      final ServerRequest serverRequest, final Throwable throwable) {
    ReactorCoreAdviceUtils.finishSpanIfPresent(
        (Span) serverRequest.attributes().remove(SPAN_ATTRIBUTE), throwable);
  }

  public static void finishSpanIfPresent(
      final ClientRequest clientRequest, final Throwable throwable) {
    ReactorCoreAdviceUtils.finishSpanIfPresent(
        (Span) clientRequest.attributes().remove(SPAN_ATTRIBUTE), throwable);
  }
}
