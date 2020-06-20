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

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

public abstract class HttpCallTracer extends BasicAdvice {

    @Pointcut("@target(org.springframework.stereotype.Controller)")
    public void isController(){}

    @Pointcut("within(me.janeve.oss.o3ppm.controllers.*)")
    public void withinApplicationControllers(){}

    @Before(value = "isController() && withinApplicationControllers() && executingAnnotatedMethod()")
    public void traceControllerCalls(JoinPoint joinPoint){
        logger.info("Executing  " +  joinPoint.getSignature() + " with args: " + Arrays.toString(joinPoint.getArgs()));
    }

}
