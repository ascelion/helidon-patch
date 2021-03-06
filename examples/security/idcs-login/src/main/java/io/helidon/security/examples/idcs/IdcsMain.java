/*
 * Copyright (c) 2018, 2019 Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.helidon.security.examples.idcs;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.LogManager;

import io.helidon.common.http.MediaType;
import io.helidon.config.Config;
import io.helidon.microprofile.server.Server;
import io.helidon.security.Security;
import io.helidon.security.SecurityContext;
import io.helidon.security.Subject;
import io.helidon.security.integration.webserver.WebSecurity;
import io.helidon.webserver.Routing;
import io.helidon.webserver.WebServer;

import static io.helidon.config.ConfigSources.classpath;
import static io.helidon.config.ConfigSources.file;

/**
 * IDCS Login example main class using configuration .
 */
public final class IdcsMain {
    private static volatile WebServer theServer;

    private IdcsMain() {
    }

    public static WebServer getTheServer() {
        return theServer;
    }

    /**
     * Start the example.
     *
     * @param args ignored
     * @throws IOException if logging configuration fails
     */
    public static void main(String[] args) throws IOException {
        // load logging configuration
        LogManager.getLogManager().readConfiguration(IdcsMain.class.getResourceAsStream("/logging.properties"));

        Config config = buildConfig();


        Security security = Security.create(config.get("security"));

        Server.builder()
                .config(config)
                .addExtension(context -> {
                    Routing.Builder routing = context.serverRoutingBuilder();
                    routing.register(WebSecurity.create(security, config))
                            .get("/rest/profile", (req, res) -> {
                                Optional<SecurityContext> securityContext = req.context().get(SecurityContext.class);
                                res.headers().contentType(MediaType.TEXT_PLAIN.withCharset("UTF-8"));
                                res.send("Response from config based service, you are: \n" + securityContext
                                        .flatMap(SecurityContext::user)
                                        .map(Subject::toString)
                                        .orElse("Security context is null"));
                            });
                })
                .addApplication(JerseyApplication.class)
                .build()
                .start();
    }

    private static Config buildConfig() {
        return Config.builder()
                .sources(
                        // you can use this file to override the defaults built-in
                        file(System.getProperty("user.home") + "/helidon/conf/examples.yaml").optional(),
                        // in jar file (see src/main/resources/application.yaml)
                        classpath("application.yaml"))
                .build();
    }
}
