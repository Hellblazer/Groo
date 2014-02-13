/** 
 * (C) Copyright 2013 Hal Hildebrand, All Rights Reserved
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

package com.hellblazer.groo;

import com.hellblazer.slp.InvalidSyntaxException;

/**
 * @author hhildebrand
 * 
 */
public interface ChakallMBean {

    void listenFor(String query) throws InvalidSyntaxException;

    void listenForService(String service) throws InvalidSyntaxException;

    void listenForService(String service, String q)
                                                   throws InvalidSyntaxException;

    void removeQuery(String query) throws InvalidSyntaxException;

    void removeServiceNameQuery(String serviceName)
                                                   throws InvalidSyntaxException;

    void removeServiceNameQuery(String serviceName, String query)
                                                                 throws InvalidSyntaxException;

}