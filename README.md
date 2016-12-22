# Sensor Data Service#

安卓手机传感器数据收集后台服务

## 使用方法 ##

安装并运行，给读写外部存储与传感器数据的权限。

点击`启动服务`开启数据采集，之后可将程序退入后台或退出，数据采集将在后台持续，直到点击`停止服务`。

在`启动服务`前，可以决定是否使用某个传感器。`启动服务`后，修改将不再生效。

数据文件记录在SD卡下`SensorData`文件夹中，每次数据采集服务的数据单独存放于一个命名的文件中，并以服务启动的日期与时间命名。

## 文件格式 ##

样例文件：

	1482317593608
	0 0 3 0.3014984 7.771881 4.2520447
	1 0 3 0.006866455 -0.0056610107 0.026748657
	3 0 3 0.33355638 8.598256 4.7041597
	0 4 3 0.3014984 7.771881 4.2520447
	3 4 3 0.33355638 8.598256 4.7041597
	1 4 3 0.006866455 -0.0056610107 0.026748657
	0 11 3 0.22253418 5.8504486 4.713867
	1 11 3 0.006866455 -0.0056610107 0.026748657
	2 2 3 -23.561096 -50.273132 27.070618
	3 11 3 0.3256415 8.398801 5.0521755
	......

第一行为数据开始收集的时间，为`System.currentTimeMillis()`的结果，可使用`Date`直接换算为现实时间。

以下每行为一个传感器数据`(Sensorevent)`：

* 第一列表示传感器类型
	* 0: TYPE_ACCELEROMETER
	* 1: TYPE_GYROSCOPE
	* 2: TYPE\_MAGNETIC\_FIELD
	* 3: TYPE_GRAVITY
	* 4: TYPE_PROXIMITY
	* 5: TYPE_LIGHT
	* 待添加，如有需求请提出
* 第二列为该条数据距数据开始收集的时间，单位`ms`（不同传感器间不保证时间序）。
* 第三列为该`Sensorevent`的`accuracy`
* 第四之后的列为该`Sensorevent`的`values`，含义见`http://www.android-doc.com/reference/android/hardware/SensorEvent.html#values`

## 文件大小 ##

采集频率为`SensorManager.SENSOR_DELAY_FASTEST`。

以`小V`手机为例，加速计和陀螺仪每秒各采集约100条数据，持续运行1小时的文件大小约为`30MB`。