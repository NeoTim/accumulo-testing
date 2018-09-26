/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.accumulo.testing.core.randomwalk.concurrent;

import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import org.apache.accumulo.core.client.AccumuloClient;
import org.apache.accumulo.core.client.AccumuloSecurityException;
import org.apache.accumulo.core.client.TableNotFoundException;
import org.apache.accumulo.testing.core.randomwalk.RandWalkEnv;
import org.apache.accumulo.testing.core.randomwalk.State;
import org.apache.accumulo.testing.core.randomwalk.Test;
import org.apache.hadoop.io.Text;

public class ListSplits extends Test {

  @Override
  public void visit(State state, RandWalkEnv env, Properties props) throws Exception {
    AccumuloClient client = env.getAccumuloClient();

    Random rand = (Random) state.get("rand");

    @SuppressWarnings("unchecked")
    List<String> tableNames = (List<String>) state.get("tables");

    String tableName = tableNames.get(rand.nextInt(tableNames.size()));

    try {
      Collection<Text> splits = client.tableOperations().listSplits(tableName);
      log.debug("Table " + tableName + " had " + splits.size() + " splits");
    } catch (TableNotFoundException e) {
      log.debug("listSplits " + tableName + " failed, doesnt exist");
    } catch (AccumuloSecurityException ase) {
      log.debug("listSplits " + tableName + " failed, " + ase.getMessage());
    }
  }
}
