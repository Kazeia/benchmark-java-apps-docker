package com.demo.rest;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import co.elastic.apm.api.ElasticApm;
import co.elastic.apm.api.Transaction;
import io.opentracing.contrib.elasticsearch.common.SpanDecorator;
import io.opentracing.contrib.elasticsearch.common.TracingHttpClientConfigCallback;
import io.opentracing.mock.MockSpan;
import io.opentracing.mock.MockTracer;
import io.opentracing.tag.Tags;
import io.opentracing.util.ThreadLocalScopeManager;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.ResponseListener;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder.HttpClientConfigCallback;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.env.Environment;
import org.elasticsearch.node.InternalSettingsPreparer;
import org.elasticsearch.node.Node;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.transport.Netty4Plugin;

@RestController
@CrossOrigin
class HomeControler {
  private static final int HTTP_PORT = 9200;
  private static final String HTTP_TRANSPORT_PORT = "9300";
  private static final String ES_WORKING_DIR = "target/es";
  private static String clusterName = "demo-cluster";
  // private static Node node;
  private final MockTracer mockTracer = new MockTracer(new ThreadLocalScopeManager(), MockTracer.Propagator.TEXT_MAP);

  public void zxc() {

    System.out.println("1");
    Settings settings = Settings.builder().put("path.home", ES_WORKING_DIR).put("path.data", ES_WORKING_DIR + "/data")
        .put("path.logs", ES_WORKING_DIR + "/logs").put("transport.type", "netty4").put("http.type", "netty4")
        .put("cluster.name", clusterName).put("http.port", HTTP_PORT).put("transport.tcp.port", HTTP_TRANSPORT_PORT)
        .put("network.host", "127.0.0.1").build();
    System.out.println("2");
    Collection plugins = Collections.singletonList(Netty4Plugin.class);
    System.out.println("3");
    // node = new PluginConfigurableNode(settings, plugins);
    System.out.println("4");
    try {
      // node.start();
    } catch (Exception e) {
      System.out.println(e);
    }
    System.out.println("5");
  }

  @GetMapping("/")
  public String home() {
    // Transaction transaction = ElasticApm.startTransaction();
    // ElasticApm.currentTransaction();

    zxc();

    RestClient restClient = RestClient.builder(new HttpHost("localhost", HTTP_PORT, "http"))
        .setHttpClientConfigCallback(new TracingHttpClientConfigCallback(mockTracer)).build();

    HttpEntity entity = new NStringEntity("{\n" + "    \"user\" : \"kimchy\",\n"
        + "    \"post_date\" : \"2009-11-15T14:12:12\",\n" + "    \"message\" : \"trying out Elasticsearch\"\n" + "}",
        ContentType.APPLICATION_JSON);

    Request request = new Request("PUT", "/twitter/tweet/10");
    request.setEntity(entity);

    try {
      Response indexResponse = restClient.performRequest(request);
    } catch (Exception e) {
      System.out.println(e);
    }

    Request request2 = new Request("PUT", "/twitter/tweet/11");
    request2.setEntity(entity);

    final CountDownLatch latch = new CountDownLatch(1);
    restClient.performRequestAsync(request2, new ResponseListener() {
      @Override
      public void onSuccess(Response response) {
        System.out.println(response.toString());
        latch.countDown();
      }

      @Override
      public void onFailure(Exception exception) {
        System.out.println(exception.toString());
        latch.countDown();
      }
    });

    // latch.await(30, TimeUnit.SECONDS);
    try {
      restClient.close();
    } catch (Exception e) {
      System.out.println(e);
    }

    List<MockSpan> finishedSpans = mockTracer.finishedSpans();
    // transaction.end();

    // System.out.println(node);
    System.out.println(finishedSpans);

    try {
      // node.close();
    } catch (Exception e) {
      System.out.println(e);
    }
    return "helloWorld";
  }

  // private static class PluginConfigurableNode extends Node {

  // public PluginConfigurableNode(Settings settings, Collection<Class<? extends
  // Plugin>> classpathPlugins) {
  // super(InternalSettingsPreparer.prepareEnvironment(settings, new HashMap<>(),
  // null, () -> "local"),
  // classpathPlugins, false);
  // }
  // }

}