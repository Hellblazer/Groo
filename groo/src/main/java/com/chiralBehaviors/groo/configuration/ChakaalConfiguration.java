/*
 * (C) Copyright 2014 Chiral Behaviors, All Rights Reserved
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

package com.chiralBehaviors.groo.configuration;

import java.util.Map;

import javax.security.auth.Subject;

import com.chiralBehaviors.groo.Chakaal;
import com.chiralBehaviors.groo.Groo;
import com.hellblazer.slp.config.ServiceScopeConfiguration;

/**
 * @author hhildebrand
 * 
 */
public class ChakaalConfiguration {
    public ServiceScopeConfiguration discovery;
    public Map<String, ?>            sourceMap;

    public Chakaal construct(Groo groo, Subject subject) throws Exception {
        return new Chakaal(groo, discovery.construct(), sourceMap, subject);
    }
}