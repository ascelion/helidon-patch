/*
 * Copyright (c) 2019 Oracle and/or its affiliates. All rights reserved.
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

/**
 * Aggregator module for microprofile 3.1.
 */
module io.helidon.microprofile.v3_1 {
    requires transitive io.helidon.microprofile.config.cdi;
    requires transitive io.helidon.microprofile.config;
    requires transitive io.helidon.microprofile.server;
    requires transitive io.helidon.microprofile.health;
    requires transitive io.helidon.microprofile.metrics;
    requires transitive io.helidon.microprofile.faulttolerance;
    requires transitive io.helidon.microprofile.jwt.auth.cdi;
    requires transitive io.helidon.microprofile.tracing;
    requires transitive io.helidon.microprofile.restclient;
    requires transitive io.helidon.microprofile.openapi;

    requires io.helidon.health.checks;
}
