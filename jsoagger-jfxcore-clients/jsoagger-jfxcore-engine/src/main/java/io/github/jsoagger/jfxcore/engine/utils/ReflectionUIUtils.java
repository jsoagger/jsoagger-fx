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

package io.github.jsoagger.jfxcore.engine.utils;



import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

import io.github.jsoagger.core.bridge.result.OperationData;
import io.github.jsoagger.core.bridge.result.SingleResult;
import io.github.jsoagger.core.utils.DateUtils;
import io.github.jsoagger.core.utils.StringUtils;
import io.github.jsoagger.core.utils.exception.VLKnoerDoerInfoInvocationException;
import io.github.jsoagger.jfxcore.engine.controller.AbstractViewController;
import io.github.jsoagger.jfxcore.engine.model.ObjectModel;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.scene.Node;

public class ReflectionUIUtils {

  public static Insets getPadding(Node node) {
    Method method = null;
    try {
      method = node.getClass().getMethod("getPadding");
      if (method != null) {
        Object result = method.invoke(node);
        if (result != null) {
          return (Insets) result;
        }
      }
    } catch (final Exception e1) {
    }

    return new Insets(0);
  }


  public static void setPrefWidth(Node node, double value) {
    Method method = null;

    try {
      method = node.getClass().getMethod("setPrefWidth", Double.TYPE);
      if (method != null) {
        method.invoke(node, value);
      }
    } catch (final Exception e1) {
    }
  }


  public static void setMaxWidth(Node node, double value) {
    Method method = null;

    try {
      method = node.getClass().getMethod("setMaxWidth", Double.TYPE);
      if (method != null) {
        method.invoke(node, value);
      }
    } catch (final Exception e1) {
    }
  }


  public static void setMinWidth(Node node, double value) {
    Method method = null;

    try {
      method = node.getClass().getMethod("setMinWidth", Double.TYPE);
      if (method != null) {
        method.invoke(node, value);
      }
    } catch (final Exception e1) {
    }
  }


  /**
   * Call this method on the controller, the method must take an event as parameter
   *
   * @param controller
   * @param method
   */
  public static Object callControllerMethod(AbstractViewController controller, String methodName, Event event) {
    Method method = null;

    try {
      method = controller.getClass().getMethod(methodName, Event.class);
      Object result = method.invoke(controller, event);
      return result;
    } catch (final Exception e1) {
      throw new IllegalArgumentException("Error calling procedure with name : " + methodName + " on class " + controller.getClass(), e1);
    }
  }


  /**
   * Set the given property on the target from the value readen from source
   *
   * @param source
   * @param target
   * @param propertyName
   */
  public static void setPropertyValue(Object source, Object target, String propertyName) {
    Object val = invokeGetterOn(source, propertyName);
    invokeSetterOn(target, propertyName, val);
  }


  /**
   * Set the given property on the target from the value readen from source. Source and target have
   * the same data type for the property but different names.
   *
   * @param source
   * @param target
   * @param spropertyName
   * @param tpropertyName
   */
  public static void setPropertyValue(Object source, Object target, String spropertyName, String tpropertyName) {
    try {
      Object val = invokeGetterOn(source, spropertyName);
      invokeSetterOn(target, tpropertyName, val);
    } catch (IllegalArgumentException e) {
      // TODO: handle exception
    }
  }


  /**
   * Object source holds date in string value. Cibling object holds LocalDateTime.
   *
   * @param source
   * @param target
   * @param spropertyName
   * @param tpropertyName
   * @param sDatformat
   */
  public static void setDateFromStringPropertyValue(Object source, Object target, String spropertyName, String tpropertyName, String sDatformat) {
    Object val = invokeGetterOn(source, spropertyName);
    LocalDateTime ldt = DateUtils.fromString_3((String) val, sDatformat);
    invokeSetterOn(target, tpropertyName, ldt);
  }


  /**
   * Invoke setter on an objet
   *
   * @param entity The entity on which invoke the method
   * @param name The info attribute name
   * @param value The entity to info to attach
   * @throws VLKnoerDoerInfoInvocationException When invoking the method
   */
  public static void invokeSetterOn(Object entity, String name, Object value) {
    try {
      if (entity instanceof JsonObject) {
        ((JsonObject) entity).addProperty(name, (String) value);
      }
      else if (entity instanceof OperationData) {
        OperationData data = (OperationData) entity;
        data.getAttributes().put(StringUtils.substringAfter(name, "attributes."), value);
      }
      else if (entity instanceof SingleResult) {
        OperationData data = ((SingleResult) entity).getData();
        if(name.startsWith("data.attributes.")) {
          data.getAttributes().put(StringUtils.substringAfter(name, "data.attributes."), value);
        }else {
          data.getAttributes().put(name, value);
        }
      }
      else {
        //BeanUtils.setProperty(entity, name, value);
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * This method is used to invoke getter on an object
   *
   * @param entity The entity
   * @param name The name
   * @throws VLKnoerDoerInfoInvocationException When invoking the method
   */
  public static Object invokeGetterOn(Object entity, String name) {
    if(entity instanceof OperationData) {
      OperationData d =  (OperationData) entity;

      // attributesa and masterattributes have been flatten
      Object val = null;

      if(name.startsWith("container.")) {
        val = d.getContainer().get(StringUtils.substringAfter(name, "container."));
      }

      else if(name.startsWith("businessType.")) {
        val = d.getBusinessType().get(StringUtils.substringAfter(name, "businessType."));
      }
      else if(name.startsWith("masterAttributes.")) {
        val = d.getAttributes().get(name);
      }
      else if(name.startsWith("links.")) {
        val = d.getLinks().get(StringUtils.substringAfter(name, "links."));
      }
      // (name.startsWith("attributes.")) or something else
      else  {
        val = d.getAttributes().get(StringUtils.substringAfter(name, "attributes."));
      }

      if("\"null\"".equalsIgnoreCase(String.valueOf(val))) {
        val = "";
      }
      return val;
    }

    else if(entity instanceof SingleResult) {
      SingleResult sr = (SingleResult) entity;
      OperationData d = sr.getData();

      String rootToken = name;
      if(name.startsWith("data.")) {
        rootToken = StringUtils.substringAfter(name, "data.");
      }

      Object val = null;
      if(rootToken.startsWith("container.")) {
        val = d.getContainer().get(StringUtils.substringAfter(name, "container."));
      }
      else if(rootToken.startsWith("masterAttributes.")) {
        val = d.getMasterAttributes().get(StringUtils.substringAfter(name, "masterAttributes."));
      }
      else if(rootToken.startsWith("businessType.")) {
        val = d.getBusinessType().get(StringUtils.substringAfter(rootToken, "businessType."));
      }
      else if(rootToken.startsWith("links.")) {
        val = d.getLinks().get(StringUtils.substringAfter(rootToken, "links."));
      }
      else {
        //if(rootToken.startsWith("attributes.")) {
        val = d.getAttributes().get(StringUtils.substringAfter(rootToken, "attributes."));
      }

      if("\"null\"".equalsIgnoreCase(String.valueOf(val))) {
        val  = "";
      }

      return val;
    }
    else if(entity instanceof JsonObject) {
      JsonElement je = ((JsonObject)entity).get(name);
      if(je.isJsonNull()) {
        return "";
      }

      return je.getAsString();
    }

    try {
      //return PropertyUtils.getProperty(entity, name);
      return "-Xx";
    } catch (Throwable e) {
      System.out.println("######### Property : " + name + ", not found on entity " + entity.getClass().getName());
    }

    // see it in IHM, means data not readen
    return "-";
  }


  /**
   * Create a new instance of a class. Call default constructor.
   */
  public static Object newInstance(String className) {
    Class<?> entryLinkClazz;
    try {
      entryLinkClazz = Class.forName(className);
      final Constructor<?> entryLinkClazzConstructor = entryLinkClazz.getConstructors()[0];
      return entryLinkClazzConstructor.newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }


  /**
   * Create a new instance of a class.
   */
  public static Object newInstance(String className, Object[] argsValues, Class<?>... argsClass) {
    Class<?> entryLinkClazz;

    try {
      entryLinkClazz = Class.forName(className);
      final Constructor<?> entryLinkClazzConstructor = entryLinkClazz.getConstructor(argsClass);
      return entryLinkClazzConstructor.newInstance(argsValues);
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
      e.printStackTrace();
    }

    return null;
  }


  public static Object newInstance(Class<?> clazz) {
    try {
      return clazz.newInstance();
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }

    return clazz;
  }


  /**
   * Add a {@link ChangeListener} on the property with given name. Reflection call for code like
   * nameProperty.addChangeListener(handler);
   *
   * @param source
   * @param event
   * @param handler
   * @throws @throws NoSuchMethodException
   */
  public static void addPropertyChangeListener(ObjectModel source, String propname, ChangeListener handler) throws Exception {
    final Method methodForChangee = source.getClass().getMethod(propname + "Property", (Class[]) null);
    final Object changeableProp = methodForChangee.invoke(source);

    // here is the call on addChangeListener
    final Method addChangeListenerMethod = changeableProp.getClass().getMethod("addListener", ChangeListener.class);
    addChangeListenerMethod.invoke(changeableProp, handler);
  }


  /**
   * Reflection call for code like slider1.valueProperty().bindBidirectional(slider2.valueProperty());
   *
   * @param bindee Node which you want to be changed by binding
   * @param propertyName name of the property, e.g. width
   * @param bindTarget Node which you want to be updated by binding
   */
  public static void bindBidirectional(Object bindee, String propertyName, Object bindTarget) throws Exception {
    // here we get slider1.valueProperty()
    final Method methodForBindee = bindee.getClass().getMethod(propertyName + "Property", (Class[]) null);
    final Object bindableObj = methodForBindee.invoke(bindee);

    // here we get slider2.valueProperty()
    final Method methodForBindTarget = bindTarget.getClass().getMethod(propertyName + "Property", (Class[]) null);
    final Object bindTargetObj = methodForBindTarget.invoke(bindTarget);

    // here we call bindBidirectional:
    // slider1.valueProperty().bindBidirectional(slider2.valueProperty())
    final Method bindMethod = bindableObj.getClass().getMethod("bindBidirectional", Property.class);
    bindMethod.invoke(bindableObj, bindTargetObj);
  }


  /**
   *
   * @param bindee
   * @param spropertyName
   * @param bindTarget
   * @param tPropertyName
   * @throws Exception
   */
  public static void bindBidirectional(Object bindee, String spropertyName, Object bindTarget, String tPropertyName) {
    try {
      // here we get slider1.valueProperty()
      final Method methodForBindee = bindee.getClass().getMethod(spropertyName + "Property", (Class[]) null);
      final Object bindableObj = methodForBindee.invoke(bindee);

      // here we get slider2.valueProperty()
      final Method methodForBindTarget = bindTarget.getClass().getMethod(tPropertyName + "Property", (Class[]) null);
      final Object bindTargetObj = methodForBindTarget.invoke(bindTarget);

      // here we call bindBidirectional:
      final Method bindMethod = bindableObj.getClass().getMethod("bindBidirectional", Property.class);
      bindMethod.invoke(bindableObj, bindTargetObj);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  /**
   *
   * @param bindee
   * @param spropertyName
   * @param bindTarget
   * @param tPropertyName
   * @throws Exception
   */
  public static void bind(Object bindee, String spropertyName, Object bindTarget, String tPropertyName) throws Exception {
    // here we get slider1.valueProperty()
    final Method methodForBindee = bindee.getClass().getMethod(spropertyName + "Property", (Class[]) null);
    final Object bindableObj = methodForBindee.invoke(bindee);

    // here we get slider2.valueProperty()
    final Method methodForBindTarget = bindTarget.getClass().getMethod(tPropertyName + "Property", (Class[]) null);
    final Object bindTargetObj = methodForBindTarget.invoke(bindTarget);

    // here we call bindBidirectional:
    // slider1.valueProperty().bindBidirectional(slider2.valueProperty())
    final Method bindMethod = bindableObj.getClass().getMethod("bind", ObservableValue.class);
    bindMethod.invoke(bindableObj, bindTargetObj);
  }
}
