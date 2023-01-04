package com.microservices.products.Controllers;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.handler.SpanHandler;
import brave.propagation.B3Propagation;
import brave.propagation.StrictCurrentTraceContext;
import brave.sampler.Sampler;
import com.microservices.products.Models.Product;
import com.microservices.products.DAOs.ProductDaoImpl;
import com.microservices.products.Requests.CreateProductRequest;
import com.microservices.products.Requests.PatchProductRequest;
import com.microservices.products.Services.ProductService;
import io.micrometer.tracing.CurrentTraceContext;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.brave.bridge.BraveBaggageManager;
import io.micrometer.tracing.brave.bridge.BraveCurrentTraceContext;
import io.micrometer.tracing.brave.bridge.BraveTracer;
import jakarta.ws.rs.QueryParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.brave.ZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("product")
public class ProductsController {
    private Tracer tracer;
    private ProductService service;

    @Autowired
    public ProductsController(ProductService service){

        this.service = service;

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

    @GetMapping
    public ResponseEntity<Object> GetProducts(){
        log.info("Product controller -> getting product");

        Span span = this.tracer.nextSpan().name("getProducts");

        try (Tracer.SpanInScope ws = this.tracer.withSpan(span.start())) {
            if (span != null) {
               log.info("Span ID {}", span.context().spanId());
               log.info("Trace ID {}", span.context().traceId());
               log.info("Parent ID {}", span.context().parentId());
            }
            span.event("getProducts");
        }finally {
            span.end();
        }

        var result = service.GetProducts();

        return ResponseEntity.ok(result);
    }

    @GetMapping("/getById")
    public ResponseEntity<Object> GetById(@QueryParam("id") Integer id){
        if(id == 0 || id == null)
            return new ResponseEntity ("You have to pass a valid id", HttpStatus.BAD_REQUEST);

        var result = service.GetProductById(id);

        if(result==null)
            return new ResponseEntity ("Product with the given id was not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public ResponseEntity<Object> Delete(@QueryParam("id") Integer id){
        if(id == 0 || id == null)
            return new ResponseEntity ("You have to pass a valid id", HttpStatus.BAD_REQUEST);

        var result = service.DeleteProduct(id);

        if(result==0)
            return new ResponseEntity ("Product with the given id was not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(result);
    }

    @PatchMapping
    public ResponseEntity<Object> PatchProduct(@RequestBody PatchProductRequest request){
        if(request == null)
            return new ResponseEntity ("You have to pass a valid request", HttpStatus.BAD_REQUEST);

        var result = service.PatchProduct(request);

        if(result==null)
            return new ResponseEntity ("Product with the given id was not found", HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Object> CreateProduct(@RequestBody CreateProductRequest request){

        if(request == null)
            return new ResponseEntity ("You have to pass a valid request", HttpStatus.BAD_REQUEST);

        log.info("Product controller -> creating product : {}", request);

        try {
            var result = service.CreateProduct(request);
            return ResponseEntity.ok(result);

        }catch (Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
