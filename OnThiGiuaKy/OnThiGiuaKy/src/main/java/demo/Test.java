package demo;

import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.reactivestreams.client.MongoDatabase;

import dao.ZipDAO;
import entities.Location;
import entities.Zips;
import utils.ConnectDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Test {
	public static void main(String[] args) {
		ConnectDB conn = new ConnectDB();
		MongoDatabase db = conn.getDataBDatabase();
		
//		Zips zip = new Zips();
//		zip.setId(ObjectId.get());
//		zip.setCity("New York");
//		zip.setPop(123);
//		zip.setState("Hau");
//		Location loc = new Location(11.12d,12.13d);
//		zip.setLoc(loc);
		
		ZipDAO zipDao = new ZipDAO(db);
		
//		System.out.println(zipDao.insert(zip));
//		
//		System.out.println(zipDao.findAll());
		
		
		
		
		
//		Tim
//		List<Zips> zipsInNewYork = zipDao.findByCity("New York");
//        System.out.println("Các vùng trong thành phố New York:");
//        for (Zips z : zipsInNewYork) {
//            System.out.println(z);
//        }
        
        
        
        
        
        
        
//      update
//         Tạo một zip mới để cập nhật
        Zips updatedZip = new Zips();
        updatedZip.setId(new ObjectId("66065d32fbe08c4962d89e00")); // Sử dụng id của zip cần cập nhật
        updatedZip.setCity("Updated City");
        updatedZip.setPop(457);
        updatedZip.setState("Updated State");
        Location newLoc = new Location(21.34d, 22.45d);
        updatedZip.setLoc(newLoc);

        // Thực hiện cập nhật và hiển thị kết quả
        boolean updateResult = zipDao.updateById(updatedZip.getId().toString(), updatedZip);
        if (updateResult) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật không thành công!");
        }

        // Hiển thị danh sách zip sau khi cập nhật
        System.out.println("Danh sách zip sau khi cập nhật:");
        List<Zips> updatedZips = zipDao.findAll();
        for (Zips z : updatedZips) {
            System.out.println(z);
        }
//        Tim kiem pop lon nhat tu 1000-5000
		// Tìm kiếm các mã Zip có dân số lớn nhất trong khoảng từ 1000 đến 5000
//        int minPopulation = 1000;
//        int maxPopulation = 5000;
//        List<Zips> zipsInRange = zipDao.findZipsWithPopulationInRange(minPopulation, maxPopulation);
//        
//        System.out.println("Các mã Zip có dân số lớn nhất trong khoảng từ " + minPopulation + " đến " + maxPopulation + ":");
//        for (Zips zip : zipsInRange) {
//            System.out.println(zip);
//        }
//        
		
		
//		Logger logger = LoggerFactory.getLogger("MyApp");
//		logger.error("Logging an Error");
	}
}
