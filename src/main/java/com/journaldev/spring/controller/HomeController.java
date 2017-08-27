package com.journaldev.spring.controller;

import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_CERTIFICATE_FILENAME;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_CLIENT_ID;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_HOST;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_MQTT_PORT;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_PRIVATE_KEY_FILENAME;
import static de.ozzc.iot.util.IoTConfig.ConfigFields.AWS_IOT_ROOT_CA_FILENAME;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.journaldev.spring.model.User;
import com.piyush.callback.ExampleCallback;

import de.ozzc.iot.util.IoTConfig;
import de.ozzc.iot.util.SslUtil;

@Controller
public class HomeController {
	
	MqttClient client = null;
	int QOS_LEVEL = 0;
	String TOPIC = "$aws/things/thing2/shadow/update";
	String MESSAGE = "{\r\n" + "\"test\":\"updated\"\r\n" + "\r\n" + "}";
	long QUIESCE_TIMEOUT = 5000;

	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		
		
		System.out.println("Home Page Requested, locale = " + locale);
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String user(@Validated User user, Model model) throws Exception {
		System.out.println("User Page Requested");
		model.addAttribute("userName", user.getUserName());

		// final Logger LOGGER = LoggerFactory.getLogger(Main.class);

		// final int QOS_LEVEL = 0;
		// final String TOPIC = "$aws/things/thing2/shadow/update";
		// final String MESSAGE = "{\r\n" + "\"test\":\"updated\"\r\n" + "\r\n"
		// + "}";
		// final long QUIESCE_TIMEOUT = 5000;
		//
		// IoTConfig config = new
		// IoTConfig("C:\\workspaces\\AWS_POC\\aws-iot_with_rpi-gpio\\config-example.properties");
		// SSLSocketFactory sslSocketFactory =
		// SslUtil.getSocketFactory(config.get(AWS_IOT_ROOT_CA_FILENAME),
		// config.get(AWS_IOT_CERTIFICATE_FILENAME),
		// config.get(AWS_IOT_PRIVATE_KEY_FILENAME));
		// MqttConnectOptions options = new MqttConnectOptions();
		// options.setSocketFactory(sslSocketFactory);
		// options.setCleanSession(true);
		//
		// final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) +
		// ":" + config.get(AWS_IOT_MQTT_PORT);
		// final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);
		//
		// MqttClient client = new MqttClient(serverUrl, clientId);
		// client.setCallback(new ExampleCallback());
		// client.connect(options);

		client.publish(TOPIC, new MqttMessage(MESSAGE.getBytes()));

		// client.disconnect(QUIESCE_TIMEOUT);
		// client.close();

		return "user";
	}

	HomeController() throws Exception {

		
		
//		ClassPathResource resource = new ClassPathResource("config-example.properties");
//		ClassPathResource cert = new ClassPathResource("6e1e0dfd65-certificate.pem.crt");
//		ClassPathResource pvtkey = new ClassPathResource("private-key.key");
//		ClassPathResource pubkey = new ClassPathResource("VeriSign-Class 3-Public-Primary-Certification-Authority-G5.pem");
//	        
//		
//		
//		
//        Properties props = new Properties();
//        props.load(resource.getInputStream());
//
//        System.out.println(props.getProperty("AWS_IOT_PRIVATE_KEY_FILENAME"));

		
//        getClass().getClassLoader().getResource("config-example.properties");
//		File file = new File(getClass().getClassLoader().getResource("config-example.properties").getFile());
//		
		URL location = HomeController.class.getProtectionDomain().getCodeSource().getLocation();

		//IoTConfig config = new IoTConfig(getClass().getClassLoader().getResource("config-example.properties").toString().replace("file:", ""));
		IoTConfig config = new IoTConfig("C:\\workspaces\\AWS_POC\\aws-iot_with_rpi-gpio\\src\\main\\resources\\config-example.properties");
		//IoTConfig config = new IoTConfig("/var/lib/tomcat8/webapps/ROOT/WEB-INF/classes/config-example.properties");
		
		///var/lib/tomcat8/webapps/ROOT/WEB-INF/classes/
		//C:\\workspaces\\AWS_POC\\aws-iot_with_rpi-gpio\\src\\main\\resources\\
		SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(config.get(AWS_IOT_ROOT_CA_FILENAME),
				config.get(AWS_IOT_CERTIFICATE_FILENAME), config.get(AWS_IOT_PRIVATE_KEY_FILENAME));
		
		System.out.println("created sslSocketFactory");
		
		//IoTConfig config = new IoTConfig("https://s3-us-west-2.amazonaws.com/pm31121/config-example.properties");
//		SSLSocketFactory sslSocketFactory = SslUtil.getSocketFactory(pubkey.getInputStream(),
//				cert.getInputStream(), pvtkey.getInputStream());
				
		
		
		MqttConnectOptions options = new MqttConnectOptions();
		options.setSocketFactory(sslSocketFactory);
		options.setCleanSession(true);

		final String serverUrl = "ssl://" + config.get(AWS_IOT_MQTT_HOST) + ":" + config.get(AWS_IOT_MQTT_PORT);
		final String clientId = config.get(AWS_IOT_MQTT_CLIENT_ID);

		System.out.println("server url: "+serverUrl);
		System.out.println("client id: "+clientId);
		
		
		
//		final String serverUrl = "ssl://" + props.get(AWS_IOT_MQTT_HOST) + ":" + props.get(AWS_IOT_MQTT_PORT);
//		final String clientId = ""+props.get("AWS_IOT_MQTT_CLIENT_ID");

		
		client = new MqttClient(serverUrl, clientId);
		
		
		client.setCallback(new ExampleCallback());
		client.connect(options);
	}

}