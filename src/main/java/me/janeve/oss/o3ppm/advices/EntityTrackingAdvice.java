/*!
 * Open3PPManager (https://github.com/janevegeorge/Open3PPManager)
 * Copyright 2020 Janeve.Me (http://www.janeve.me)
 *
 * This file is part of "Open3PPManager".
 *
 * Open3PPManager is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Open3PPManager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Open3PPManager. If not, see <http://www.gnu.org/licenses/>.
 */
package me.janeve.oss.o3ppm.advices;

import me.janeve.oss.o3ppm.entities.TrackedEntity;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Optional;

@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Component
public class EntityTrackingAdvice extends BasicAdvice {

    @Before(value = "target(org.springframework.data.mongodb.repository.MongoRepository) && execution(* *.save(..)) && args(me.janeve.oss.o3ppm.entities.TrackedEntity)")
    private void updateTrackingInformation(JoinPoint joinPoint){
        for(Object obj : joinPoint.getArgs()) {
            TrackedEntity entity = (TrackedEntity) joinPoint.getArgs()[0];
            MongoRepository<TrackedEntity, Object> repository = null;
            if(joinPoint.getTarget() instanceof MongoRepository) {
                repository = (MongoRepository<TrackedEntity, Object>) joinPoint.getTarget();
            }

            logger.info("Saving called for " + entity);

            updateCreatedTime(repository, entity);
            updateCreatedBy(repository, entity);

            updateLastUpdatedTime(entity);
            updateLastUpdatedBy(entity);

            logger.info("Saving " + entity);
        }
    }

    private void updateCreatedTime(MongoRepository repository, TrackedEntity entity) {
        if(entity.getId() == null || entity.getId().trim().isEmpty()) {
            entity.setCreatedAt(ZonedDateTime.now(ZoneOffset.UTC));
        } else {
            if (repository != null) {
                Optional<TrackedEntity> dbEntry = repository.findById(entity.getId());
                dbEntry.ifPresent(trackedEntity -> entity.setCreatedAt(trackedEntity.getCreatedAt()));
            }
        }
    }

    private void updateCreatedBy(MongoRepository repository, TrackedEntity entity) {
        if(entity.getId() == null || entity.getId().trim().isEmpty()) {
            entity.setCreatedBy(getUser());
        } else {
            if (repository != null) {
                Optional<TrackedEntity> dbEntry = repository.findById(entity.getId());
                dbEntry.ifPresent(trackedEntity -> entity.setCreatedBy(trackedEntity.getCreatedBy()));
            }
        }
    }

    private void updateLastUpdatedBy(TrackedEntity entity) {
        entity.setLastUpdatedBy(getUser());
    }

    private void updateLastUpdatedTime(TrackedEntity entity) {
        entity.setLastUpdatedAt(ZonedDateTime.now(ZoneOffset.UTC));
    }

}