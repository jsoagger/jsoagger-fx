/*-
 * ========================LICENSE_START=================================
 * JSoagger 
 * %%
 * Copyright (C) 2019 JSOAGGER
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */

package io.github.jsoagger.jfxcore.engine.client.utils;




import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ExecutorService for asynch task executing.
 *
 * @author Administrator
 *
 */
public class VLExecutorService {

  private static final ExecutorService poolledTasksExecutor = Executors.newSingleThreadExecutor(r -> {
    final Thread t = new Thread(r);
    t.setDaemon(true); // allows app to exit if tasks are running
    return t;
  });

  // Use the following if you want the tasks to run concurrently, instead of
  // consecutively:
  private static final ExecutorService concurrentTaskExecutor = Executors.newCachedThreadPool(r -> {
    final Thread t = new Thread(r);
    t.setDaemon(true);
    return t;
  });

  private static VLExecutorService instance = null;


  /**
   * Avoid external instanciation.
   */
  private VLExecutorService() {}


  public static void shutDown() {
    poolledTasksExecutor.shutdownNow();
    concurrentTaskExecutor.shutdownNow();
  }


  /**
   * Get the unique instance of {@link VLExecutorService}.
   *
   * @return
   */
  public static VLExecutorService instance() {
    if (instance == null) {
      instance = new VLExecutorService();
    }

    return instance;
  }
}
