//package vn.fs.api;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import vn.fs.entity.Category;
//import vn.fs.objectstorageservice.MinioService;
//import vn.fs.repository.CategoryDAO;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.List;
//
//@CrossOrigin("*")
//@RestController
//@RequestMapping("/api/files")
//public class FilesService {
//    @Autowired
//    private MinioService minioService;
//
//    @Autowired
//    private ExportService exportService;
//
//    @Autowired
//    private CategoryService categoryService;
//
//    @Autowired
//    CategoryDAO repo;
//
//    @Value("${minio.bucket.name}")
//    private String bucketName;
//
//    // API endpoint để tải lên file
//    // API endpoint để tải lên file
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
//        if (file.isEmpty()) {
//            return new ResponseEntity<>("File must not be empty", HttpStatus.BAD_REQUEST);
//        }
//
//        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
//
//        try {
//            // Nhận contentType từ file
//            String contentType = file.getContentType() != null ? file.getContentType() : "application/octet-stream";
//
//            // Tải lên file sử dụng MinioService
//            minioService.uploadFile(bucketName, fileName, file.getInputStream(), contentType);
//
//            // Trả về URL để truy cập file, bạn có thể cần chỉnh sửa nếu cần URL cụ thể
//            String fileUrl = "URL để truy cập file tải lên";
//            return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    // API endpoint để tải xuống file
//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
//        try {
//            byte[] data = minioService.getFile(fileName);
//            return ResponseEntity.ok()
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
//                    .body(data);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @PostMapping("/categories/export")
//    public ResponseEntity<String> exportCategories() {
//        try {
//            List<Category> categories = repo.findAll(); // Phương thức giả định để lấy danh sách
//
//            ByteArrayOutputStream baos = exportService.createExcelFile(categories); // BaseService là service của bạn để tạo file Excel
//
//            String fileName = "Categories_" + System.currentTimeMillis() + ".xlsx";
//            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//
//            // Giả sử `uploadFile` là phương thức trong `MinioService` để tải lên MinIO
//            String contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
//            // Tải lên file
//            minioService.uploadFile(bucketName, fileName, bais, contentType);
//
//            // Tạo URL truy cập file. Giả định bạn có một phương thức để lấy base URL của MinIO hoặc bạn có thể cấu hình trực tiếp nó.
//            String baseUrl = minioService.getBaseUrl();
//            String accessPath = bucketName + "/" + fileName;
//            String fileUrl = baseUrl + "/" + accessPath; // Đường dẫn đây đủ để truy cập file
//
//            return ResponseEntity.ok(fileUrl);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
//        }
//    }
//
//
//}
