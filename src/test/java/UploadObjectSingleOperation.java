import com.cn.hnust.service.IUploadService;
import com.cn.hnust.service.impl.UploadServiceImpl;

public class UploadObjectSingleOperation {

    public static void main(String[] args) throws Exception {
        IUploadService uploadService = new UploadServiceImpl();
        String url = uploadService.upload("downFileDir/a.png");
        System.out.println("url= " + url);
    }
}