practice-war
================================

# 1. 介绍说明

# 2. 开发practice-war
## 2.1. 开发环境
* JDK 8
* Gradle 6，建议使用根目录下的`./gradlew`，自带的版本是6.5
* 新版的Eclipse或IDEA，建议使用至少是2020年的版本
* MySQL 5.7

## 2.2. 在Eclipse下开发
首先，在根目录下执行以下命令
```
./gradlew cleanEclipse eclipse
```


如果是首次开发tutorial，在执行完上面的命令后，打开Eclipse，新建工作空间，启动Eclipse。  
启动完后，打开`git repositories`视图，把git库添加进去。展开git库，选中`Working Tree`，选择**右键菜单**中的`Import Projects...`，把所有的工程都导入工作空间中。

如果不是首次开发tutorial，当gradle设置发生变化时，需要手动做处理。在执行完gradle命令后，刷新`Pakcage Explorer`视图下的工程即可。  
如果有新增了工程，可以通过`git repositories`视图的`Import Projects...`向导把新增的工程导入进来。  
如果删除了工程，需要在Eclipse手动删除相应的工程，删除工程时勾选删除本地文件。本地的git库中可能也会存在残留的文件（如Eclipse的资源文件），这些残留文件一般是不会提交到git库中的，可以手动删除。  
注意：有时候在Eclipse中删除工程后，仍然能在`Pakcage Explorer`视图中看到被删除的工程。这应该是Eclipse的bug，重启Eclipse就好了。

## 2.3. 在IDEA下开发
如果是首次在IDEA下开发，可以打开`practice-backend/`目录，通过鼠标右键打开IDEA。也可以在欢迎界面中点击`Open or Import`，选中`practice-backend/`目录。如果是旧版的IDEA，在导入时可能还会让选择导入的类型，选择Gradle即可。之后就是等待IDEA完成初始化即可，时间长短跟网络、Maven服务器速度等因素有关。

如果不是首次在IDEA下开发，打开IDEA后，自动或者手动刷新文件。如果Gradle设置有变化，可能会提示进行重新做导入的操作。也可以手动重新加载Gradle设置。

## 2.4. 在开发环境中启动服务
把`/practice.war/application.yml-template`复制到`/practice.war/src/main/resources/`目录下，改名为`application.yml`，根据情况修改里面的设置，比如服务端口号、数据库连接、redis连接等。

把`/practice.war/log4j2-sample.xml`复制到`/practice.war/src/main/resources/`目录下，改名为`log4j2.xml`，根据情况修改里面的设置。
