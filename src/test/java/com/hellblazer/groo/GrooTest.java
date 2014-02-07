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

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;

import org.junit.Before;
import org.junit.Test;

import com.hellblazer.utils.Condition;
import com.hellblazer.utils.Utils;

import static org.junit.Assert.*;

/**
 * @author hhildebrand
 * 
 */
public class GrooTest {
    private Node        intermediate;
    private NodeMBean   leaf1;
    private NodeMBean   leaf2;
    private ObjectName  leaf1Name;
    private ObjectName  leaf2Name;
    private ObjectName  intermediateName;
    private ObjectName  test1a;
    private ObjectName  test2a;
    private ObjectName  multiTest1;
    private ObjectName  multiTest2;
    private ObjectName  test1b;
    private ObjectName  test2b;
    private MBeanServer intermediateMbs;
    private MBeanServer leaf1Mbs;
    private MBeanServer leaf2Mbs;

    @Before
    public void initialize() throws Exception {
        leaf1Mbs = MBeanServerFactory.newMBeanServer();
        leaf2Mbs = MBeanServerFactory.newMBeanServer();
        intermediateMbs = MBeanServerFactory.newMBeanServer();
        test1a = ObjectName.getInstance("MyDomain", "test1", "a");
        test1b = ObjectName.getInstance("MyDomain", "test1", "b");
        test2a = ObjectName.getInstance("MyDomain", "test2", "a");
        test2b = ObjectName.getInstance("MyDomain", "test2", "b");
        multiTest1 = ObjectName.getInstance("MyDomain", "test1", "*");
        multiTest2 = ObjectName.getInstance("MyDomain", "test2", "*");
        leaf1Mbs.registerMBean(new Test1(), test1a);
        leaf2Mbs.registerMBean(new Test1(), test1b);
        leaf1Mbs.registerMBean(new Test2(), test2a);
        leaf2Mbs.registerMBean(new Test2(), test2b);
        leaf1Name = ObjectName.getInstance("leaf-domain", "id", "1");
        leaf2Name = ObjectName.getInstance("leaf-domain", "id", "2");
        intermediateName = ObjectName.getInstance("intermediate-domain", "id",
                                                  "1");
        leaf1 = new Node();
        leaf2 = new Node();
        intermediate = new Node(
                                ObjectName.getInstance("leaf-domain", "id", "*"),
                                null);
        intermediateMbs.registerMBean(intermediate, intermediateName);
    }

    @Test
    public void testNotifications() throws Exception {
        Groo groo = new Groo("Groo the wanderer");
        intermediateMbs.registerMBean(groo,
                                      ObjectName.getInstance("groo", "id", "1"));
        groo.addParent(intermediate);
        assertEquals(0, intermediate.getChildren().size());
        groo.addConnection(new LocalMbscFactory(groo, leaf1Mbs, "Leaf 1 MBS"));
        assertEquals(0, intermediate.getChildren().size());
        leaf1Mbs.registerMBean(leaf1, leaf1Name);
        Utils.waitForCondition(1000, new Condition() {
            @Override
            public boolean isTrue() {
                return intermediate.getChildren().size() > 0;
            }
        });
        assertEquals(1, intermediate.getChildren().size());
        leaf2Mbs.registerMBean(leaf2, leaf2Name);
        groo.addConnection(new LocalMbscFactory(groo, leaf2Mbs, "Leaf 2 MBS"));
        assertEquals(2, intermediate.getChildren().size());
    }
}
