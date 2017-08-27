//package com.piyush.listener;
//
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_CERTIFICATE_FILENAME;
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_CLIENT_ID;
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_HOST;
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_PORT;
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_PRIVATE_KEY_FILENAME;
//import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_ROOT_CA_FILENAME;
//
//import javax.net.ssl.SSLSocketFactory;
//import javax.servlet.ServletContextEvent;
//import javax.servlet.ServletContextListener;
//
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.piyush.callback.ExampleCallback;
//
//import de.ozzc.iot.util.IoTConfig;
//import de.ozzc.iot.util.SslUtil;
//
//public class MyAppServletContextListener implements ServletContextListener {
//
//	@Override
//	public void contextDestroyed(ServletContextEvent arg0) {
//		System.out.println("ServletContextListener destroyed");
//	}
//
//	// Run this before web application is started
//	@Override
//	public void contextInitialized(ServletContextEvent arg0) {
//
//		final Logger LOGGER = LoggerFactory.getLogger(MyAppServletContextListener.class);
//
//		final int QOS_LEVEL = 0;
//		final String TOPIC = "$aws/things/thing2/shadow/update";
//		final String MESSAGE = "{\n" + "    \"state\" : {\n" + "        \"desired\" : {\n"
//				+ "            \"welcome\" : \"aws-iot-changed44\",\n" + "\"test\":\"wel\"\n" + "\n" + "         }\n"
//				+ "     }\n" + "} ";
//		final long QUIESCE_TIMEOUT = 5000;
//
//		try {
//			IoTConfig config = new IoTConfig("C:\\workspaces\\AWS_POC\\aws-iot_with_rpi-gpio\\config-example.properties");
//			SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(config.get(AWS_IOT_ROOT_CA_FILENAME),
//					config.get(AWS_IOT_CERTIFICATE_FILENAME), config.get(AWS_IOT_PRIVATE_KEY_FILENAME));
//			MqttConnectOptions options = new MqttConnectOptions();
//			options.setSocketFactory(sslSocketFactory);
//			options.setCleanSession(true);
//
//			final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) + ":" + config.get(AWS_IOT_MQTT_PORT);
//			final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);
//
//			MqttClient client = new MqttClient(serverUrl, clientId);
//			client.setCallback(new ExampleCallback());
//			client.connect(options);
//			while (true) {
//				client.subscribe("$aws/things/thing2/shadow/update", QOS_LEVEL);
//			}
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage(), e);
//			System.exit(-1);
//		}
//
//	}
//}