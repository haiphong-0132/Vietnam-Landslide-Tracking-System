package team08.oop.lms.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapConfig {
    public static final Map<String, String> MAP_TYPE =  new HashMap<>();
    public static final Map<String, String> MAP_CITY =  new HashMap<>();
    public static final Map<String, String> MAP_STRUCT =  new HashMap<>();

    static{
        MAP_TYPE.put("BS", "Bờ Sông");
        MAP_TYPE.put("BB", "Bờ Biển");

        MAP_CITY.put("Hà Giang", "201");
        MAP_CITY.put("Cao Bằng", "203");
        MAP_CITY.put("Lào Cai", "205");
        MAP_CITY.put("Sơn La", "303");
        MAP_CITY.put("Lai Châu", "301");
        MAP_CITY.put("Bắc Kạn", "207");
        MAP_CITY.put("Lạng Sơn", "209");
        MAP_CITY.put("Tuyên Quang", "211");
        MAP_CITY.put("Yên Bái", "213");
        MAP_CITY.put("Thái Nguyên", "215");
        MAP_CITY.put("Điện Biên", "302");
        MAP_CITY.put("Phú Thọ", "217");
        MAP_CITY.put("Vĩnh Phúc", "104");
        MAP_CITY.put("Bắc Giang", "221");
        MAP_CITY.put("Bắc Ninh", "106");
        MAP_CITY.put("TP. Hà Nội", "101");
        MAP_CITY.put("Quảng Ninh", "225");
        MAP_CITY.put("Hải Dương", "107");
        MAP_CITY.put("TP. Hải Phòng", "103");
        MAP_CITY.put("Hòa Bình", "305");
        MAP_CITY.put("Hưng Yên", "109");
        MAP_CITY.put("Hà Nam", "111");
        MAP_CITY.put("Thái Bình", "115");
        MAP_CITY.put("Nam Định", "113");
        MAP_CITY.put("Ninh Bình", "117");
        MAP_CITY.put("Thanh Hóa", "401");
        MAP_CITY.put("Nghệ An", "403");
        MAP_CITY.put("Hà Tĩnh", "405");
        MAP_CITY.put("Quảng Bình", "407");
        MAP_CITY.put("Quảng Trị", "409");
        MAP_CITY.put("Thừa Thiên-Huế", "411");
        MAP_CITY.put("TP. Đà Nẵng", "501");
        MAP_CITY.put("Quảng Nam", "503");
        MAP_CITY.put("Quảng Ngãi", "505");
        MAP_CITY.put("Kon Tum", "601");
        MAP_CITY.put("Gia Lai", "603");
        MAP_CITY.put("Bình Định", "507");
        MAP_CITY.put("Phú Yên", "509");
        MAP_CITY.put("Đắk Lắk", "605");
        MAP_CITY.put("Khánh Hòa", "511");
        MAP_CITY.put("Đắk Nông", "606");
        MAP_CITY.put("Lâm Đồng", "607");
        MAP_CITY.put("Ninh Thuận", "705");
        MAP_CITY.put("Bình Phước", "707");
        MAP_CITY.put("Tây Ninh", "709");
        MAP_CITY.put("Bình Dương", "711");
        MAP_CITY.put("Đồng Nai", "713");
        MAP_CITY.put("Bình Thuận", "715");
        MAP_CITY.put("TP. Hồ Chí Minh", "701");
        MAP_CITY.put("Long An", "801");
        MAP_CITY.put("Bà Rịa-Vũng Tàu", "717");
        MAP_CITY.put("Đồng Tháp", "803");
        MAP_CITY.put("An Giang", "805");
        MAP_CITY.put("Tiền Giang", "807");
        MAP_CITY.put("Vĩnh Long", "809");
        MAP_CITY.put("Bến Tre", "811");
        MAP_CITY.put("Cần Thơ", "815");
        MAP_CITY.put("Kiên Giang", "813");
        MAP_CITY.put("Trà Vinh", "817");
        MAP_CITY.put("Hậu Giang", "816");
        MAP_CITY.put("Sóc Trăng", "819");
        MAP_CITY.put("Bạc Liêu", "821");
        MAP_CITY.put("Cà Mau", "823");
    
        MAP_STRUCT.put("7", "SẠT LỞ NGUY HIỂM CÓ KẾ HOẠCH");
        MAP_STRUCT.put("8", "SẠT LỞ NGUY HIỂM ĐANG RÀ SOÁT");
        MAP_STRUCT.put("9", "SẠT LỞ NGUY HIỂM");
        MAP_STRUCT.put("65", "KHU VỰC BỒI");
        MAP_STRUCT.put("66", "SẠT LỞ BÌNH THƯỜNG");
        MAP_STRUCT.put("86", "KHU VỰC KHẢO SÁT XÓI SÂU");
        MAP_STRUCT.put("88", "KHU VỰC XÓI BỒI XEN KẼ");
        MAP_STRUCT.put("94", "SẠT LỞ NGUY HIỂM ĐANG TRÌNH");
    
        Collections.unmodifiableMap(MAP_TYPE);
        Collections.unmodifiableMap(MAP_CITY);
        Collections.unmodifiableMap(MAP_STRUCT);
    }
}
