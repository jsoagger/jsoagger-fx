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

package io.github.jsoagger.cloud.stub.operations;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.function.Consumer;

import io.github.jsoagger.core.bridge.operation.IOperation;
import io.github.jsoagger.core.bridge.operation.IOperationResult;
import io.github.jsoagger.core.bridge.result.MultipleResult;
import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.utils.FileUtils;
import io.github.jsoagger.core.utils.StringUtils;
import com.google.gson.JsonObject;

/**
 * @author Ramilafananana  VONJISOA
 *
 */
public class StubGetIllustrationOperation implements IOperation {

  private static String DATASET_IMAGE_LOCATION = "io.github.jsoagger.demoapp.dataset.image.location";
  @SuppressWarnings("unused")
  private static String DATASET = "demo.data.set.key";

  Properties platformProperties;
  IOperation getPreferenceValueOperation;
  private String imagesLocation;


  /**
   * {@inheritDoc}
   */
  @Override
  public void doOperation(JsonObject params, Consumer<IOperationResult> resultHandler, Consumer<Throwable> exHandler) {

    loadImagesLocation();

    MultipleResult multipleResult = new MultipleResult();
    List<OperationData> datas = new ArrayList<>();
    multipleResult.setData(datas);

    if (StringUtils.isNotBlank(imagesLocation)) {

      try {
        byte[] one = FileUtils.toByteArray(new File(imagesLocation + "/12.png"));
        OperationData dataone = new OperationData();
        dataone.getAttributes().put("illustration", Base64.getEncoder().encode(one));
        datas.add(dataone);

        byte[] two = FileUtils.toByteArray(new File(imagesLocation + "/13.png"));
        OperationData datatwo = new OperationData();
        datatwo.getAttributes().put("illustration", Base64.getEncoder().encode(two));
        datas.add(datatwo);

        byte[] three = FileUtils.toByteArray(new File(imagesLocation + "/14.png"));
        OperationData datathree = new OperationData();
        datathree.getAttributes().put("illustration", Base64.getEncoder().encode(three));
        datas.add(datathree);

        byte[] four = FileUtils.toByteArray(new File(imagesLocation + "/15.png"));
        OperationData datafour = new OperationData();
        datafour.getAttributes().put("illustration", Base64.getEncoder().encode(four));
        datas.add(datafour);

        byte[] five = FileUtils.toByteArray(new File(imagesLocation + "/16.png"));
        OperationData datafive = new OperationData();
        datafive.getAttributes().put("illustration", Base64.getEncoder().encode(five));
        datas.add(datafive);
      } catch (Exception e) {
        // e.printStackTrace();
      }
    }
    else {
      try {
        byte[] one = getBytesFromInputStream(StubGetIllustrationOperation.class.getResourceAsStream("/defaultimages" + "/12.png"));
        OperationData dataone = new OperationData();
        dataone.getAttributes().put("illustration", Base64.getEncoder().encode(one));
        datas.add(dataone);

        byte[] two = getBytesFromInputStream(StubGetIllustrationOperation.class.getResourceAsStream("/defaultimages" + "/13.png"));
        OperationData datatwo = new OperationData();
        datatwo.getAttributes().put("illustration", Base64.getEncoder().encode(two));
        datas.add(datatwo);

        byte[] three = getBytesFromInputStream(StubGetIllustrationOperation.class.getResourceAsStream("/defaultimages" + "/14.png"));
        OperationData datathree = new OperationData();
        datathree.getAttributes().put("illustration", Base64.getEncoder().encode(three));
        datas.add(datathree);

        byte[] four = getBytesFromInputStream(StubGetIllustrationOperation.class.getResourceAsStream("/defaultimages" + "/15.png"));
        OperationData datafour = new OperationData();
        datafour.getAttributes().put("illustration", Base64.getEncoder().encode(four));
        datas.add(datafour);

        byte[] five = getBytesFromInputStream(StubGetIllustrationOperation.class.getResourceAsStream("/defaultimages" + "/16.png"));
        OperationData datafive = new OperationData();
        datafive.getAttributes().put("illustration", Base64.getEncoder().encode(five));
        datas.add(datafive);
      } catch (Exception e) {
        // e.printStackTrace();
      }
    }

    resultHandler.accept(multipleResult);
  }

  public static byte[] getBytesFromInputStream(InputStream is)  {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    byte[] buffer = new byte[0xFFFF];
    try {
      for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
        os.write(buffer, 0, len);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    byte[] b = os.toByteArray();
    try {
      is.close();
      os.close();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return b;
  }

  private String getDatasetKey() {
    StubPaginatedTableDataOperation.Key key = new StubPaginatedTableDataOperation.Key("foods");
    if (getPreferenceValueOperation != null) {
      JsonObject query = new JsonObject();
      query.addProperty("key", "io.github.jsoagger.demoapp.dataset");
      getPreferenceValueOperation.doOperation(query, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0) {
          String vo = (String) r.getData().get(0).getAttributes().get("savedValue");
          key.setValue(vo);
        }
      });
    }

    return key.value;
  }


  private void loadImagesLocation() {
    if (getPreferenceValueOperation != null) {
      com.google.gson.JsonObject jsonObject = new JsonObject();
      jsonObject.addProperty("key", DATASET_IMAGE_LOCATION);
      getPreferenceValueOperation.doOperation(jsonObject, res -> {
        MultipleResult r = (MultipleResult) res;
        if (r.getData().size() > 0)
          setImagesLocation((String) r.getData().get(0).getAttributes().get("savedValue"));
      });
    }
  }

  private void setImagesLocation(String location) {
    this.imagesLocation = location;
  }

  public Properties getPlatformProperties() {
    return platformProperties;
  }

  public void setPlatformProperties(Properties platformProperties) {
    this.platformProperties = platformProperties;
  }

  public IOperation getGetPreferenceValueOperation() {
    return getPreferenceValueOperation;
  }

  public void setGetPreferenceValueOperation(IOperation getPreferenceValueOperation) {
    this.getPreferenceValueOperation = getPreferenceValueOperation;
  }

  public String getImagesLocation() {
    return imagesLocation;
  }
}
