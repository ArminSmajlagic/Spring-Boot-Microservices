package com.microservices.products.Controllers;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.handler.SpanHandler;
import brave.propagation.B3Propagation;
import brave.propagation.StrictCurrentTraceContext;
import brave.sampler.Sampler;
import com.microservices.products.Services.CategoryService;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.brave.ZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    private Tracer tracer;
    @Autowired
    private CategoryService service;
    public CategoryController(){


        SpanHandler spanHandler = ZipkinSpanHandler
                .create(AsyncReporter.create(URLConnectionSender.create("http://localhost:9411/api/v2/spans")));

        StrictCurrentTraceContext braveCurrentTraceContext = StrictCurrentTraceContext.create();


        CurrentTraceContext bridgeContext = new BraveCurrentTraceContext(braveCurrentTraceContext);

        Tracing tracing = Tracing.newBuilder().currentTraceContext(braveCurrentTraceContext).supportsJoin(false)
                .traceId128Bit(true)
                // For Baggage to work you need to provide a list of fields to propagate
                .propagationFactory(BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY)
                        .add(BaggagePropagationConfig.SingleBaggageField
                                .remote(BaggageField.create("from_span_in_scope 1")))
                        .add(BaggagePropagationConfig.SingleBaggageField
                                .remote(BaggageField.create("from_span_in_scope 2")))
                        .add(BaggagePropagationConfig.SingleBaggageField.remote(BaggageField.create("from_span")))
                        .build())
                .sampler(Sampler.ALWAYS_SAMPLE).localServiceName("product-service").addSpanHandler(spanHandler).build();

        brave.Tracer braveTracer = tracing.tracer();

        this.tracer = new BraveTracer(braveTracer, bridgeContext, new BraveBaggageManager());

    }
}
