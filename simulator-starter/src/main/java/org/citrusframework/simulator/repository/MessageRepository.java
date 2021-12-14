/*
 * Copyright 2006-2017 the original author or authors.
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

package org.citrusframework.simulator.repository;

import org.citrusframework.simulator.model.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * JPA repository for {@link Message}
 */
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findByDateBetweenAndDirectionIn(Date fromDate, Date toDate, Collection<Message.Direction> directions, Pageable pageable);

    List<Message> findByDateBetweenAndDirectionInAndPayloadContainingIgnoreCase(Date fromDate, Date toDate, Collection<Message.Direction> directions, String containingText, Pageable pageable);
}