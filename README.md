
<p align="center">
<img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/efx.png" width="540" height="320">
</p>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

# Concept

**« EmaginFX »** concept is to **build** JavaFX **IHM from « XML » or « JSON » configuration file**.

**Each node on the scene is a reusable, configurable and injectable component, described by a configuration file**.

The visual IHM is a group of independent components, integrated  at runtime by a dependency injection Framework. By default we use « Spring Framework » to assemble the view at runtime. But application framework have been configured to support custom IOC framework.

Builded UI is **Responsive, Adaptative and material designed**. It can be easily connected to remote cloud server.


## View Processor
With EmaginFX, views are described by XML/JSON files and processed by EmaginJFX processor. 

That's why « EmaginFX » upon JavaFX gives developers ability to quickly realise multiplatform application and reuse components as library through mutiple projects.


<p align="center">
<a href="url"><img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/jsoagger-jfx-top-overview.jpg" align="center" width="540" height="538"></a>
</p>


## Bean Instances management and IOC
EmaginFX uses a container to manage bean instance. Best practice is to not intanciate manually a component but get an instance from bean manager.


```java
IComponent component = Services.getBean("MyTableViewIdentifier");
component.build(controller, configuration);
```


EmaginFX can be launched with Spring framework as IOC container and bean instances manager. All spring XML configuration files are already packaged inside framework.

```java
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
    context.setConfigLocation("classpath:get-started-application-context.xml");
    context.refresh();
    context.registerShutdownHook();

    this.viewStructure = (ViewStructure) Services.getBean("platformViewStructure");
    this.viewStructure.buildStructure();
```


In fact Spring is a **RUNTIME ONLY DEPENDENCY**, i.e, there is no spring annotation nor spring class used inside framework, except the application launcher.


<p align="center">
<a href="url"><img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/jsoagger-jfx-ioc-overview.jpg" align="center" width="740" height="538"></a>
</p>


## Components configuration overriding
EmaginFX uses XMLCombiner to manage component XML configuration. XMLCombiner gives us ability to merge multiple XML files while removing/adding nodes in output file.

It gives us the ability te reuse and by the way override some attributes of a component, while keeping the source code clean and maintenable.

Example, display the same view without primary menu

1. The view With primary menu
```
<view id="Root" combine.keys="id">
	<component id="Content" combine.keys="id">
		<properties combine.keys="name">
			<property name="headerView" value="PrimaryHeaderToolbarView" />
			<property name="primaryMenuView" value="PrimaryMenuView" />
			<property name="contentRootStructure" value="GetStartedRSContentView" />
		</properties>
	</component>
</view> 
```

2. Same view Without primary menu
```
<view id="Root" combine.keys="id">
	<component id="Content" combine.keys="id">
		<properties combine.keys="name">
			<property name="primaryMenuView" combine.self="remove" />
		</properties>
	</component>
</view> 
```

## Responsive and adaptative

Each component can be made responsive and adaptative by defining a responsive matrix. Responsive matrix describes the component behaviour according to its parent width.

```
<util:list id="NavigationBarResponsiveMatrixDefinition" list-class="java.util.ArrayList" value-type="java.lang.String">
	<value>0:830#0.50:fixed|500:0.50#:minimize:</value>
	<value>830:1000#0.30:0.30:0.40#minimize::</value>
	<value>1000#0.40:0.20:0.40#::</value>
</util:list>
```

This examples configure header component toolbar to behave like following :
 - minimize the component on its center and set its width to 500
 - set the left node size to 0.50% of remaining
 - set the right node size to 0.50% of remaining

# Build

Javafx libraries are not included with JDK11, JavaFX runtime must be configured as maven dependencies.

```
> java -version
java version "1.8.0_65"
Java(TM) SE Runtime Environment (build 1.8.0_65-b17)
Java HotSpot(TM) 64-Bit Server VM (build 25.65-b01, mixed mode)
```


```xml
mvn package 
```

# Running the demo application

Demo application shows part of EmaginFX capabilities.

* Download a version of the demo for your OS from following links:

&nbsp;

| Platform   | Status | Download  Demo|
| ------- | --- | --- |
| MacOs   |[![Build Status](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_apis/build/status/Nexitia.jsoagger-platform?branchName=develop)](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_build/latest?definitionId=1?branchName=develop)|[Download](https://github.com/Nexitia/emaginfx/releases)||
| Linux|[![Build Status](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_apis/build/status/Nexitia.jsoagger-platform?branchName=develop)](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_build/latest?definitionId=1?branchName=develop)|[Download](https://github.com/Nexitia/emaginfx/releases)||
| Windows |[![Build Status](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_apis/build/status/Nexitia.jsoagger-platform?branchName=develop)](https://dev.azure.com/yvonjisoa0885/Emagin%20Platform/_build/latest?definitionId=1?branchName=develop)|[Download](https://github.com/Nexitia/emaginfx/releases)||

&nbsp;

* Unzip it

```
> unzip jsoagger-jfxcore-demoapp-macos-11.0.3.zip

> cd jsoagger-jfxcore-demoapp-macos-11.0.3
```

* Run 

```
> run.sh desktop

> run.sh mobile

> run.sh xpad
```

DESKTOP            |  PAD
:-------------------------:|:-------------------------:
<img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/desktop01.png" align="center" width="350" height="300"/>|  <img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/xpad01.png" align="center" width="400" height="300"/>


&nbsp;

MOBILE            |
:-------------------------:|
<img src="https://github.com/rmvonji/jsoagger-screenshots/blob/master/mobile01.png" align="center" width="280" height="350"/>|




# Links

[EmaginFX Wiki](https://github.com/Nexitia/emaginfx/wiki)  
 
[EmaginFX components library](https://github.com/Nexitia/emaginfx/wiki/EmaginFx-header-Toolbar)
 
[Creating cross platform Java application](https://nexitia.com/creating-javafx-11-application-with-emaginfx/)

[Creating mobile application with EmaginFX](https://nexitia.com/creating-mobile-javafx-application-with-emaginfx/)


# Project

## Contributing
[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)

Please read [CONTRIBUTING.md] for details on our code of conduct, and the process for submitting pull requests to us.

## Authors
* **rmvonji** - *Initial work* - [JSoagger ](https://github.com/rmvonji/)
* **Contact** - *yvonjisoa@nexitia.com*

## Licence
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
This project is licensed under Apache Licence V2

