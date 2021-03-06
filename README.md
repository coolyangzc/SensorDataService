# Sensor Data Service #

安卓手机传感器数据收集后台服务

## 使用方法 ##

安装并运行，给读写外部存储与传感器数据的权限。

点击`启动服务`开启数据采集，之后可将程序退入后台或退出，数据采集将在后台持续，直到点击`停止服务`。

在`启动服务`前，可以决定是否使用某个传感器。`启动服务`后，修改将不再生效。

数据文件记录在SD卡下`SensorData`文件夹中，每次数据采集服务的数据单独存放于一个命名的文件中，并以服务启动的日期与时间命名。

## 文件格式 ##

样例文件：

	1482400918516
	115686474446925
	2 0 3 -73.513794 37.342834 -57.56836
	0 6 3 -2.074585 6.8195496 5.8384857
	1 6 3 0.36213684 -0.9032593 -0.010528564
	3 6 3 -2.20802 7.2581754 6.2140102
	5 -120 3 108.0 5741.0 0.0
	0 11 3 -2.074585 6.8195496 5.8384857
	1 11 3 0.36213684 -0.9032593 -0.010528564
	3 11 3 -2.20802 7.2581754 6.2140102
	3 18 3 -2.1656702 7.036425 6.4783473
	0 18 3 -1.6319122 4.754532 5.6781616
	1 18 3 0.36213684 -0.9032593 -0.010528564
	2 18 3 -41.65802 -1.109314 -29.04663
	0 23 3 -1.9118652 6.1088715 7.281357
	3 23 3 -2.098913 6.833072 6.713723
	......

第一行为数据开始收集的时间，为`System.currentTimeMillis()`的结果，可使用`Date`直接换算为现实时间。

第二行为数据开始收集时距设备开机的时间，单位`ns`，为`SystemClock.elapsedRealtimeNanos()`的结果。

以下每行为一个传感器数据`(Sensorevent)`：

* 第一列表示传感器类型
	* 0: TYPE_ACCELEROMETER `(3路数据)`
	* 1: TYPE_GYROSCOPE `(3路数据)`
	* 2: TYPE\_MAGNETIC\_FIELD `(3路数据)`
	* 3: TYPE_GRAVITY `(3路数据)`
	* 4: TYPE_PROXIMITY `(1路数据)`
	* 5: TYPE_LIGHT  `(1路数据)`
	* 6: TYPE\_ROTATION\_VECTOR `(5路数据)`
	* 7: TYPE\_LINEAR\_ACCELERATION  `(3路数据)`
	* 待添加，如有需求请提出
* 第二列为该条数据距数据开始收集的时间，单位`ms`（不同传感器间不保证时间序）。
* 第三列为该`Sensorevent`的`accuracy`
* 第四之后的列为该`Sensorevent`的`values`，含义见`http://www.android-doc.com/reference/android/hardware/SensorEvent.html#values`

## 文件大小 ##

采集频率为`SensorManager.SENSOR_DELAY_FASTEST`。

以`小V`手机为例，加速计和陀螺仪每秒各采集约100条数据，持续运行1小时的文件大小约为`30MB`。
