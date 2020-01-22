package io.opentelemetry.auto.instrumentation.reactor.core;

import io.opentelemetry.OpenTelemetry;
import io.opentelemetry.auto.api.SpanTypes;
import io.opentelemetry.auto.decorator.BaseDecorator;
import io.opentelemetry.trace.Tracer;

public class ReactorCoreDecorator extends BaseDecorator {
  public static ReactorCoreDecorator DECORATE = new ReactorCoreDecorator();
  public static final Tracer TRACER = OpenTelemetry.getTracerFactory().get("io.opentelemetry.auto");

  @Override
  protected String[] instrumentationNames() {
    return new String[] {"reactor-core"};
  }

  @Override
  protected String spanType() {
    return SpanTypes.HTTP_CLIENT; // TODO: Is this the correct type?
  }

  @Override
  protected String component() {
    return "reactor-core";
  }
}
