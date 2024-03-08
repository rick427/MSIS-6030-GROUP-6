import org.junit.Test;
import services.DbService;

import static org.junit.Assert.assertEquals;

public class Testing {
    DbService connection = new DbService();

    @Test
    public void userLogin(){
        boolean result = connection.login("ray@gmail.com","12345");
        assertEquals(true,result);
    }
}
