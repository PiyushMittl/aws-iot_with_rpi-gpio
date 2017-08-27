import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class asdasd {

	public static void main(String[] args) {
		 try {
		        final URL url = new URL("http://www.google.com");
		        final URLConnection conn = url.openConnection();
		        conn.connect();
		       System.out.println("true");
		    } catch (MalformedURLException e) {
		        throw new RuntimeException(e);
		    } catch (IOException e) {
		    	System.out.println("false");
		    }

	}

}
