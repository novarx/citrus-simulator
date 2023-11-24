package org.citrusframework.simulator.http;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.citrusframework.exceptions.CitrusRuntimeException;
import org.citrusframework.http.actions.HttpActionBuilder;
import org.citrusframework.http.actions.HttpServerActionBuilder;
import org.citrusframework.http.actions.HttpServerResponseActionBuilder.HttpMessageBuilderSupport;
import org.citrusframework.simulator.scenario.ScenarioEndpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

/**
 * @author Christoph Deppisch
 */
public class HttpScenarioActionBuilder extends HttpActionBuilder {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().enable(INDENT_OUTPUT);

    /** Scenario endpoint */
    private final ScenarioEndpoint scenarioEndpoint;

    public HttpScenarioActionBuilder(ScenarioEndpoint scenarioEndpoint) {
        this.scenarioEndpoint = scenarioEndpoint;
    }

    /**
     * Default scenario receive operation.
     * @return
     */
    public HttpServerActionBuilder.HttpServerReceiveActionBuilder receive() {
        return server(scenarioEndpoint).receive();
    }

    /**
     * Default scenario send response operation.
     * @return
     */
    public HttpServerActionBuilder.HttpServerSendActionBuilder send() {
        return server(scenarioEndpoint).send();
    }

    /**
     * Send scenario {@code application/json} response operation.
     * @return
     */
    public HttpMessageBuilderSupport sendOkJson(String json) {
        return server(scenarioEndpoint)
            .send()
            .response(HttpStatus.OK)
            .message()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(json);
    }

    /**
     * Send scenario {@code application/json} response operation from serialized {@link Object}.
     * @return
     */
    public HttpMessageBuilderSupport sendOkJson(Object jsonObject) {
        HttpMessageBuilderSupport httpMessageBuilderSupport = server(scenarioEndpoint).send()
            .response(HttpStatus.OK)
            .message()
            .contentType(MediaType.APPLICATION_JSON_VALUE);

        try {
            httpMessageBuilderSupport.body(OBJECT_MAPPER.writeValueAsString(jsonObject));
        } catch (JsonProcessingException e) {
            throw new CitrusRuntimeException(e);
        }

        return httpMessageBuilderSupport;
    }
}
